<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>接口管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="interfaceConfig" action="${ctx}/interfaceconfig/interfaceConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>业务名称：</label></td>
					<td class="width-35">
						<form:input path="businessName" htmlEscape="false" maxlength="65535"  minlength="0"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>项目名称：</label></td>
					<td class="width-35">
						<form:input path="projectName" htmlEscape="false" maxlength="65535"  minlength="0"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>接口名称：</label></td>
					<td class="width-35">
						<form:input path="interfaceName" htmlEscape="false" maxlength="65535"  minlength="0"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right">接口版本：</label></td>
					<td class="width-35">
						<form:input path="interfaceVersion" htmlEscape="false" maxlength="65535"  minlength="0"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>维护人：</label></td>
					<td class="width-35">
						<form:input path="manager" htmlEscape="false" maxlength="65535"  minlength="0"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>应用mock机器：</label></td>
					<td class="width-35">
						<form:select path="serverMock" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('server_mock')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>