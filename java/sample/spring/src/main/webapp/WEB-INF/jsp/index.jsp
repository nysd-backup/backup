<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
		<style>
		.error {
			color: #ff0000;
		}
		 
		.errorblock {
			color: #000;
			background-color: #ffEEEE;
			border: 3px solid #ff0000;
			padding: 8px;
			margin: 16px;
		}
		</style>
		</head>
	<body>
		<form:form modelAttribute="orderDto" action="order.do" method="POST">
			<form:hidden path="viewStatus"/>
			<form:errors path="*" cssClass="errorblock" element="div" />
			<span>検索条件</span>
			<table border="1">
				<tr>
					<th><span>No</span></th>
					<td><form:input path="orderNo"/>
						<form:errors path="orderNo" cssClass="error" /></td>					
				</tr>
				<tr>
					<th><span>CustomerCode</span></th>
					<td><form:input path="customerCd"/></td>
				</tr>
				<tr>
					<th><span>Version</span></th>
					<td><form:select items="${orderDto.versionMap}" path="version"/></td>
				</tr>
			</table>			
			<input type="submit" value="検索" name="search"/>
			<br/>
			<c:if test = "${orderDto.viewStatus == '1'}">   
				<span>検索結果</span>
				<table name="detailDto">
				
				<!-- varがformのフィールドと一致しなくてはならない -->
				<c:forEach items = "${orderDto.detailDto}" var="detailDto" varStatus="status">
		          <tr>
		              <td>
		              	<form:input path="detailDto[${status.index}].itemCode"/>
		              </td>	
		              
		              <td><form:input path="detailDto[${status.index}].count" readonly="true"/></td>		           
		              <td>
		              	<!-- チェックボックスはこの形にしないとうまくいかない。また、Modelはitemとvalueを持つObjectにした方がよい -->
		              	<c:forEach items="${detailDto.checkBoxes}" var="checkBoxes" varStatus="status2">
		              		<form:checkbox path="detailDto[${status.index}].checkBoxes[${status2.index}].values" label="${checkBoxes.items}"/>   	
		              	</c:forEach>		            
		              </td>
		              <td><form:select items="${orderDto.versionMap}" path="detailDto[${status.index}].version"/></td>
		               <td><form:radiobuttons items="${orderDto.versionMap}" path="detailDto[${status.index}].detailNo"/></td>
		          </tr>
		          </c:forEach>
		          </table>
		          <br/>		          
				<input type="submit" value="更新" name="update"/>
				<input type="submit" value="出力" name="output"/>
			</c:if>	
		</form:form>
	</body>
</html>
