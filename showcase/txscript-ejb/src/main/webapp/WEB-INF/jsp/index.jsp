<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<body>
		<form:form action="index.do" method="GET">
		<form:hidden path="orderDto.orderNo"/>
			<table border="1">
				<tr>
					<th><span>No</span></th>
					<td><spring:bind path="orderDto.orderNo">${status.value}</spring:bind></td>
				</tr>
				<tr>
					<th><span>CustomerCode</span></th>
					<td><form:input path="orderDto.customerCode"/></td>
				</tr>
			</table>
			<input type="submit" value="確認"/>
		</form:form>
	</body>
</html>
