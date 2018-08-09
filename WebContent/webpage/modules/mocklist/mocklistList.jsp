<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>mock功能管理</title>
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
		<h5>mock功能列表 </h5>
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
	<form:form id="searchForm" modelAttribute="mocklist" action="${ctx}/mocklist/mocklist/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>业务名称：</span>
				<form:select path="business"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('business')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<span>接口名称：</span>
				<form:input path="apiName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>请求参数：</span>
				<form:input path="requestParams" htmlEscape="false"  class=" form-control input-sm"/>
			<span>返回码：</span>
				<form:input path="returnCode" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="mocklist:mocklist:add">
				<table:addRow url="${ctx}/mocklist/mocklist/form" title="mock功能"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="mocklist:mocklist:edit">
			    <table:editRow url="${ctx}/mocklist/mocklist/form" title="mock功能" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="mocklist:mocklist:del">
				<table:delRow url="${ctx}/mocklist/mocklist/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="mocklist:mocklist:import">
				<table:importExcel url="${ctx}/mocklist/mocklist/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="mocklist:mocklist:export">
	       		<table:exportExcel url="${ctx}/mocklist/mocklist/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column business">业务名称</th>
				<th  class="sort-column apiName">接口名称</th>
				<th  class="sort-column requestType">请求方法</th>
				<th  class="sort-column requestParams">请求参数</th>
				<th  class="sort-column requestPath">请求路径</th>
				<th  class="sort-column returnCode">返回码</th>
				<th  class="sort-column returnType">返回类型</th>
				<th  class="sort-column response">返回值</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="mocklist">
			<tr>
				<td> <input type="checkbox" id="${mocklist.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看mock功能', '${ctx}/mocklist/mocklist/form?id=${mocklist.id}','800px', '500px')">
					${fns:getDictLabel(mocklist.business, 'business', '')}
				</a></td>
				<td>
					${mocklist.apiName}
				</td>
				<td>
					${fns:getDictLabel(mocklist.requestType, 'request_type', '')}
				</td>
				<td>
					${mocklist.requestParams}
				</td>
				<td>
					${mocklist.requestPath}
				</td>
				<td>
					${mocklist.returnCode}
				</td>
				<td>
					${fns:getDictLabel(mocklist.returnType, 'return_type', '')}
				</td>
				<td>
					${mocklist.response}
				</td>
				<td>
					<shiro:hasPermission name="mocklist:mocklist:view">
						<a href="#" onclick="openDialogView('查看mock功能', '${ctx}/mocklist/mocklist/form?id=${mocklist.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="mocklist:mocklist:edit">
    					<a href="#" onclick="openDialog('修改mock功能', '${ctx}/mocklist/mocklist/form?id=${mocklist.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="mocklist:mocklist:del">
						<a href="${ctx}/mocklist/mocklist/delete?id=${mocklist.id}" onclick="return confirmx('确认要删除该mock功能吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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