/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.interfaceconfig.web;

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
import com.jeeplus.modules.interfaceconfig.entity.InterfaceConfig;
import com.jeeplus.modules.interfaceconfig.service.InterfaceConfigService;

/**
 * 接口管理Controller
 * @author yanyu
 * @version 2018-08-06
 */
@Controller
@RequestMapping(value = "${adminPath}/interfaceconfig/interfaceConfig")
public class InterfaceConfigController extends BaseController {

	@Autowired
	private InterfaceConfigService interfaceConfigService;
	
	@ModelAttribute
	public InterfaceConfig get(@RequestParam(required=false) String id) {
		InterfaceConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = interfaceConfigService.get(id);
		}
		if (entity == null){
			entity = new InterfaceConfig();
		}
		return entity;
	}
	
	/**
	 * 接口列表页面
	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(InterfaceConfig interfaceConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<InterfaceConfig> page = interfaceConfigService.findPage(new Page<InterfaceConfig>(request, response), interfaceConfig); 
		model.addAttribute("page", page);
		return "modules/interfaceconfig/interfaceConfigList";
	}

	/**
	 * 查看，增加，编辑接口表单页面
	 */
	@RequiresPermissions(value={"interfaceconfig:interfaceConfig:view","interfaceconfig:interfaceConfig:add","interfaceconfig:interfaceConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(InterfaceConfig interfaceConfig, Model model) {
		model.addAttribute("interfaceConfig", interfaceConfig);
		return "modules/interfaceconfig/interfaceConfigForm";
	}

	/**
	 * 保存接口
	 */
	@RequiresPermissions(value={"interfaceconfig:interfaceConfig:add","interfaceconfig:interfaceConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(InterfaceConfig interfaceConfig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, interfaceConfig)){
			return form(interfaceConfig, model);
		}
		if(!interfaceConfig.getIsNewRecord()){//编辑表单保存
			InterfaceConfig t = interfaceConfigService.get(interfaceConfig.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(interfaceConfig, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			interfaceConfigService.save(t);//保存
		}else{//新增表单保存
			interfaceConfigService.save(interfaceConfig);//保存
		}
		addMessage(redirectAttributes, "保存接口成功");
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
	}
	
	/**
	 * 删除接口
	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:del")
	@RequestMapping(value = "delete")
	public String delete(InterfaceConfig interfaceConfig, RedirectAttributes redirectAttributes) {
		interfaceConfigService.delete(interfaceConfig);
		addMessage(redirectAttributes, "删除接口成功");
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
	}
	
	/**
	 * 批量删除接口
	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			interfaceConfigService.delete(interfaceConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除接口成功");
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(InterfaceConfig interfaceConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "接口"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<InterfaceConfig> page = interfaceConfigService.findPage(new Page<InterfaceConfig>(request, response, -1), interfaceConfig);
    		new ExportExcel("接口", InterfaceConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出接口记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<InterfaceConfig> list = ei.getDataList(InterfaceConfig.class);
			for (InterfaceConfig interfaceConfig : list){
				try{
					interfaceConfigService.save(interfaceConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条接口记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条接口记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入接口失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
    }
	
	/**
	 * 下载导入接口数据模板
	 */
	@RequiresPermissions("interfaceconfig:interfaceConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "接口数据导入模板.xlsx";
    		List<InterfaceConfig> list = Lists.newArrayList(); 
    		new ExportExcel("接口数据", InterfaceConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/interfaceconfig/interfaceConfig/?repage";
    }
	
	
	

}