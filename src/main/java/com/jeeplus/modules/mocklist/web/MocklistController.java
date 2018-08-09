/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.mocklist.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.MyBeanUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.mocklist.entity.Mocklist;
import com.jeeplus.modules.mocklist.service.MocklistService;

/**
 * mockController
 * @author yanyu
 * @version 2018-07-26
 */
@Controller
@RequestMapping(value = "${adminPath}/mocklist/mocklist")
public class MocklistController extends BaseController {

	@Autowired
	private MocklistService mocklistService;
	
	@ModelAttribute
	public Mocklist get(@RequestParam(required=false) String id) {
		Mocklist entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = mocklistService.get(id);
		}
		if (entity == null){
			entity = new Mocklist();
		}
		return entity;
	}
	
	/**
	 * mock功能列表页面
	 */
	@RequiresPermissions("mocklist:mocklist:list")
	@RequestMapping(value = {"list", ""})
	public String list(Mocklist mocklist, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Mocklist> page = mocklistService.findPage(new Page<Mocklist>(request, response), mocklist); 
		model.addAttribute("page", page);
		return "modules/mocklist/mocklistList";
	}

	/**
	 * 查看，增加，编辑mock功能表单页面
	 */
	@RequiresPermissions(value={"mocklist:mocklist:view","mocklist:mocklist:add","mocklist:mocklist:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Mocklist mocklist, Model model) {
		model.addAttribute("mocklist", mocklist);
		return "modules/mocklist/mocklistForm";
	}

	/**
	 * 保存mock功能
	 */
	@RequiresPermissions(value={"mocklist:mocklist:add","mocklist:mocklist:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Mocklist mocklist, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, mocklist)){
			return form(mocklist, model);

			//return  save(model,mocklist);
		}
		if(!mocklist.getIsNewRecord()){//编辑表单保存
			Mocklist t = mocklistService.get(mocklist.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(mocklist, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			mocklistService.save(t);//保存
		}else{//新增表单保存
			mocklistService.save(mocklist);//保存
		}
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
	}
	
	/**
	 * 删除mock功能
	 */
	@RequiresPermissions("mocklist:mocklist:del")
	@RequestMapping(value = "delete")
	public String delete(Mocklist mocklist, RedirectAttributes redirectAttributes) {
		mocklistService.delete(mocklist);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
	}
	
	/**
	 * 批量删除mock功能
	 */
	@RequiresPermissions("mocklist:mocklist:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			mocklistService.delete(mocklistService.get(id));
		}
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("mocklist:mocklist:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Mocklist mocklist, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "mock功能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Mocklist> page = mocklistService.findPage(new Page<Mocklist>(request, response, -1), mocklist);
    		new ExportExcel("mock功能", Mocklist.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("mocklist:mocklist:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Mocklist> list = ei.getDataList(Mocklist.class);
			for (Mocklist mocklist : list){
				try{
					mocklistService.save(mocklist);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
    }
	
	/**
	 * 下载导入mock功能数据模板
	 */
	@RequiresPermissions("mocklist:mocklist:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "导入模板.xlsx";
    		List<Mocklist> list = Lists.newArrayList(); 
    		new ExportExcel("数据", Mocklist.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/mocklist/mocklist/?repage";
    }
	
	
	

}