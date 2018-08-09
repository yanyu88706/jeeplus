/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.mocklist.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * mockEntity
 * @author yanyu
 * @version 2018-07-24
 */
public class Mocklist extends DataEntity<Mocklist> {
	
	private static final long serialVersionUID = 1L;
	private String business;		// 业务名称
	private String ApiVersion;    //接口版本
	private String apiName;		// 接口名称
	private String requestType;		// 请求方法
	private String requestParams;		// 请求参数
	private String requestPath;		// 请求路径
	private String returnCode;		// 返回码
	private String returnType;		// 返回类型
	private String response;		// 返回值



	public Mocklist() {
		super();
	}

	public Mocklist(String id){
		super(id);
	}

	@Length(min=1, max=65535, message="业务名称长度必须介于 1 和 65535 之间")
	@ExcelField(title="业务名称", dictType="business", align=2, sort=1)
	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	@Length(min = 1,max = 65535,message = "长度必须介于 1 和 65535 之间")
	@ExcelField(title = "接口版本",dictType = "api_version",align = 1,sort = 0)
	public String getApiVersion() {
		return ApiVersion;
	}

	public void setApiVersion(String apiVersion) {
		ApiVersion = apiVersion;
	}


	@Length(min=1, max=65535, message="长度必须介于 1 和 65535 之间")
	@ExcelField(title="接口名称", align=2, sort=2)
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	
	@Length(min=1, max=65535, message="长度必须介于 1 和 65535 之间")
	@ExcelField(title="请求方法", dictType="request_type", align=2, sort=3)
	public String getRequestType() {
			return requestType;
			}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@ExcelField(title="请求参数", align=2, sort=4)
	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}
	
	@Length(min=1, max=65535, message="长度必须介于 1 和 65535 之间")
	@ExcelField(title="请求路径", align=2, sort=5)
	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	
	@Length(min=1, max=65535, message="长度必须在 1 和 65535 之间")
	@ExcelField(title="返回码", align=2, sort=6)
	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	
	@Length(min=1, max=65535, message="长度必须介于 1 和 65535 之间")
	@ExcelField(title="返回类型", dictType="return_type", align=2, sort=7)
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@ExcelField(title="返回值", align=2, sort=8)
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
}