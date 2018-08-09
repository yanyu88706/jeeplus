<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>mock功能管理</title>
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
		<form:form id="inputForm" modelAttribute="mocklist" action="${ctx}/mocklist/mocklist/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>业务名称：</label></td>
					<td class="width-35">
						<form:select path="business" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('business')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>接口名称：</label></td>
					<td class="width-35">
						<form:input path="apiName" htmlEscape="false" maxlength="65535"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>请求方法：</label></td>
					<td class="width-35">
						<form:select path="requestType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('request_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">请求参数：</label></td>
					<td class="width-35">
						<form:textarea path="requestParams" htmlEscape="false" rows="4" maxlength="65535"  minlength="1"   class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>请求路径：</label></td>
					<td class="width-35">
						<form:input path="requestPath" htmlEscape="false" maxlength="65535"  minlength="1"   class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>返回码：</label></td>
					<td class="width-35">
						<form:input path="returnCode" htmlEscape="false" maxlength="65535"  minlength="1"   class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>返回类型：</label></td>
					<td class="width-35">
						<form:select path="returnType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('return_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</td>
					<td class="width-15 active"><label class="pull-right">返回值：</label></td>
					<td class="width-35">
						<form:textarea path="response" htmlEscape="false" rows="4" maxlength="65535"  minlength="1"   class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>