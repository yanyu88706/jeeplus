/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.caseconfig.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 用例管理Entity
 * @author yanyu
 * @version 2018-08-07
 */
public class CaseConfig extends DataEntity<CaseConfig> {
	
	private static final long serialVersionUID = 1L;
	private String interfaceName;		// 接口名称
	private String caseName;		// 用例名称
	private String isWrong;		// 用例类别
	private String httpMethod;		// 请求方法
	private String urlAddress;		// 请求路径
	private String paramter;		// 请求参数
	private String returnType;		// 返回类型
	private String statusCode;		// 返回码
	private String responseBody;		// 返回值
	private String isDelay;		// 开启延迟返回
	private String delayTime;		// 延迟时间（ms）
	
	public CaseConfig() {
		super();
	}

	public CaseConfig(String id){
		super(id);
	}

	@Length(min=1, max=65535, message="接口名称长度必须介于 1 和 65535 之间")
	@ExcelField(title="接口名称", align=2, sort=1)
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@Length(min=1, max=65535, message="用例名称长度必须介于 1 和 65535 之间")
	@ExcelField(title="用例名称", align=2, sort=2)
	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	
	@Length(min=1, max=65535, message="用例类别长度必须介于 1 和 65535 之间")
	@ExcelField(title="用例类别", dictType="isWrong", align=2, sort=3)
	public String getIsWrong() {
		return isWrong;
	}

	public void setIsWrong(String isWrong) {
		this.isWrong = isWrong;
	}
	
	@Length(min=1, max=65535, message="请求方法长度必须介于 1 和 65535 之间")
	@ExcelField(title="请求方法", dictType="httpMethod", align=2, sort=4)
	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	
	@Length(min=1, max=65535, message="请求路径长度必须介于 1 和 65535 之间")
	@ExcelField(title="请求路径", align=2, sort=5)
	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	
	@ExcelField(title="请求参数", align=2, sort=6)
	public String getParamter() {
		return paramter;
	}

	public void setParamter(String paramter) {
		this.paramter = paramter;
	}
	
	@Length(min=1, max=65535, message="返回类型长度必须介于 1 和 65535 之间")
	@ExcelField(title="返回类型", dictType="returnType", align=2, sort=7)
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	
	@Length(min=1, max=65535, message="返回码长度必须介于 1 和 65535 之间")
	@ExcelField(title="返回码", align=2, sort=8)
	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	@Length(min=1, max=65535, message="返回值长度必须介于 1 和 65535 之间")
	@ExcelField(title="返回值", align=2, sort=9)
	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	@Length(min=1, max=65535, message="开启延迟返回长度必须介于 1 和 65535 之间")
	@ExcelField(title="开启延迟返回", dictType="isDelay", align=2, sort=10)
	public String getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(String isDelay) {
		this.isDelay = isDelay;
	}
	
	@ExcelField(title="延迟时间（ms）", align=2, sort=11)
	public String getDelayTime() {
		return delayTime;
	}

	public void setDelayTime(String delayTime) {
		this.delayTime = delayTime;
	}
	
}