/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.interfaceconfig.entity;

import org.hibernate.validator.constraints.Length;

import com.jeeplus.common.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 接口管理Entity
 * @author yanyu
 * @version 2018-08-06
 */
public class InterfaceConfig extends DataEntity<InterfaceConfig> {
	
	private static final long serialVersionUID = 1L;
	private String businessName;		// 业务名称
	private String projectName;		// 项目名称
	private String interfaceName;		// 接口名称
	private String interfaceVersion;		// 接口版本
	private String manager;		// 维护人
	private String serverMock;		// 应用mock机器
	
	public InterfaceConfig() {
		super();
	}

	public InterfaceConfig(String id){
		super(id);
	}

	@Length(min=0, max=65535, message="业务名称长度必须介于 0 和 65535 之间")
	@ExcelField(title="业务名称", align=2, sort=1)
	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	
	@Length(min=0, max=65535, message="项目名称长度必须介于 0 和 65535 之间")
	@ExcelField(title="项目名称", align=2, sort=2)
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=65535, message="接口名称长度必须介于 0 和 65535 之间")
	@ExcelField(title="接口名称", align=2, sort=3)
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	
	@ExcelField(title="接口版本", align=2, sort=4)
	public String getInterfaceVersion() {
		return interfaceVersion;
	}

	public void setInterfaceVersion(String interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}
	
	@Length(min=0, max=65535, message="维护人长度必须介于 0 和 65535 之间")
	@ExcelField(title="维护人", align=2, sort=5)
	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}
	
	@Length(min=0, max=65535, message="应用mock机器长度必须介于 0 和 65535 之间")
	@ExcelField(title="应用mock机器", dictType="server_mock", align=2, sort=6)
	public String getServerMock() {
		return serverMock;
	}

	public void setServerMock(String serverMock) {
		this.serverMock = serverMock;
	}
	
}