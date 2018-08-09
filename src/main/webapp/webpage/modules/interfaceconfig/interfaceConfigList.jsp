<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>接口管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>接口列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="interfaceConfig" action="${ctx}/interfaceconfig/interfaceConfig/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>接口名称：</span>
				<form:input path="interfaceName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>维护人：</span>
				<form:input path="manager" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>应用mock机器：</span>
				<form:select path="serverMock"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('server_mock')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="interfaceconfig:interfaceConfig:add">
				<table:addRow url="${ctx}/interfaceconfig/interfaceConfig/form" title="接口"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="interfaceconfig:interfaceConfig:edit">
			    <table:editRow url="${ctx}/interfaceconfig/interfaceConfig/form" title="接口" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="interfaceconfig:interfaceConfig:del">
				<table:delRow url="${ctx}/interfaceconfig/interfaceConfig/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="interfaceconfig:interfaceConfig:import">
				<table:importExcel url="${ctx}/interfaceconfig/interfaceConfig/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="interfaceconfig:interfaceConfig:export">
	       		<table:exportExcel url="${ctx}/interfaceconfig/interfaceConfig/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column businessName">业务名称</th>
				<th  class="sort-column projectName">项目名称</th>
				<th  class="sort-column interfaceName">接口名称</th>
				<th  class="sort-column interfaceVersion">接口版本</th>
				<th  class="sort-column manager">维护人</th>
				<th  class="sort-column serverMock">应用mock机器</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="interfaceConfig">
			<tr>
				<td> <input type="checkbox" id="${interfaceConfig.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看接口', '${ctx}/interfaceconfig/interfaceConfig/form?id=${interfaceConfig.id}','800px', '500px')">
					${interfaceConfig.businessName}
				</a></td>
				<td>
					${interfaceConfig.projectName}
				</td>
				<td>
					${interfaceConfig.interfaceName}
				</td>
				<td>
					${interfaceConfig.interfaceVersion}
				</td>
				<td>
					${interfaceConfig.manager}
				</td>
				<td>
					${fns:getDictLabel(interfaceConfig.serverMock, 'server_mock', '')}
				</td>
				<td>
					<shiro:hasPermission name="interfaceconfig:interfaceConfig:view">
						<a href="#" onclick="openDialogView('查看接口', '${ctx}/interfaceconfig/interfaceConfig/form?id=${interfaceConfig.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="interfaceconfig:interfaceConfig:edit">
    					<a href="#" onclick="openDialog('修改接口', '${ctx}/interfaceconfig/interfaceConfig/form?id=${interfaceConfig.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="interfaceconfig:interfaceConfig:del">
						<a href="${ctx}/interfaceconfig/interfaceConfig/delete?id=${interfaceConfig.id}" onclick="return confirmx('确认要删除该接口吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>