/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.caseconfig.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.json.AjaxJson;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import com.jeeplus.modules.caseconfig.entity.CaseConfig;
import com.jeeplus.modules.caseconfig.service.CaseConfigService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 * 用例管理Controller
 * @author yanyu
 * @version 2018-08-07
 */
@Controller
@RequestMapping(value = "${adminPath}/caseconfig/caseConfig")
public class CaseConfigController extends BaseController {

	@Autowired
	private CaseConfigService caseConfigService;
	
	@ModelAttribute
	public CaseConfig get(@RequestParam(required=false) String id) {
		CaseConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = caseConfigService.get(id);
		}
		if (entity == null){
			entity = new CaseConfig();
		}
		return entity;
	}
	
	/**
	 * 用例列表页面
	 */
	@RequiresPermissions("caseconfig:caseConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(CaseConfig caseConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CaseConfig> page = caseConfigService.findPage(new Page<CaseConfig>(request, response), caseConfig); 
		model.addAttribute("page", page);
		return "modules/caseconfig/caseConfigList";
	}

	/**
	 * 查看，增加，编辑用例表单页面
	 */
	@RequiresPermissions(value={"caseconfig:caseConfig:view","caseconfig:caseConfig:add","caseconfig:caseConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(CaseConfig caseConfig, Model model) {
		model.addAttribute("caseConfig", caseConfig);
		return "modules/caseconfig/caseConfigForm";
	}

	/**
	 * 保存用例
	 */
	@RequiresPermissions(value={"caseconfig:caseConfig:add","caseconfig:caseConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(CaseConfig caseConfig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, caseConfig)){
			return form(caseConfig, model);
		}
		if(!caseConfig.getIsNewRecord()){//编辑表单保存
			CaseConfig t = caseConfigService.get(caseConfig.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(caseConfig, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			caseConfigService.save(t);//保存
		}else{//新增表单保存
			caseConfigService.save(caseConfig);//保存
		}
		addMessage(redirectAttributes, "保存用例成功");
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
	}
	
	/**
	 * 删除用例
	 */
	@RequiresPermissions("caseconfig:caseConfig:del")
	@RequestMapping(value = "delete")
	public String delete(CaseConfig caseConfig, RedirectAttributes redirectAttributes) {
		caseConfigService.delete(caseConfig);
		addMessage(redirectAttributes, "删除用例成功");
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
	}
	
	/**
	 * 批量删除用例
	 */
	@RequiresPermissions("caseconfig:caseConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			caseConfigService.delete(caseConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除用例成功");
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("caseconfig:caseConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(CaseConfig caseConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用例"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<CaseConfig> page = caseConfigService.findPage(new Page<CaseConfig>(request, response, -1), caseConfig);
    		new ExportExcel("用例", CaseConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用例记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("caseconfig:caseConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CaseConfig> list = ei.getDataList(CaseConfig.class);
			for (CaseConfig caseConfig : list){
				try{
					caseConfigService.save(caseConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用例记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用例记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用例失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
    }
	
	/**
	 * 下载导入用例数据模板
	 */
	@RequiresPermissions("caseconfig:caseConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用例数据导入模板.xlsx";
    		List<CaseConfig> list = Lists.newArrayList(); 
    		new ExportExcel("用例数据", CaseConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/caseconfig/caseConfig/?repage";
    }
	
	//yanyu 20180808  待调试
	@ResponseBody
	@RequiresPermissions("caseconfig:caseConfig:reset")
	@RequestMapping(value="resetMock")
	public AjaxJson resetMock(RedirectAttributes redirectAttributes) {
		System.out.println("begin...");
		AjaxJson j = new AjaxJson();
		try {
			//windows下

			//linux下执行shell
			String cmd="sh /home/yanyu/wiremock.sh reset";
			Process ps = Runtime.getRuntime().exec(cmd);
			ps.waitFor();
			BufferedReader br=new BufferedReader(new InputStreamReader(ps.getInputStream()));
			StringBuffer sb =new StringBuffer();
			String line=br.readLine();
			while(line != null){
				sb.append(line).append("\n");
			}
			String result=sb.toString();
			System.out.println(result);
			j.setSuccess(true);
			j.setMsg(result);
			j.setMsg("更新成功！");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("MockServer后台重启失败！失败信息："+e.getMessage());
		}
		return j;
	}

}