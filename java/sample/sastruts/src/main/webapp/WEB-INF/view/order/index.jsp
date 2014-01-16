<%@page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>		
	</head>
	<body>
		<div>
			<s:form>
				<table>
					<tr>
						<th><span>受注番号</span></th>
						<td><input name="order.no" value="${order.no}"></td>
						<th><span>顧客</span></th>
						<td><input name="order.customerCd" value="${order.customerCd}"></td>					
					</tr>
				</table>
				<table>
					<tr>
						<th><span>数量</span></th>					
						<th><span>商品番号</span></th>			
					</tr>
					<c:forEach var="order.orderDetails" items="${order.orderDetails}">
						<tr>
							<td><html:text name="order.orderDetails" property="count" indexed="true"/></td>	
							<td><html:text name="order.orderDetails" property="itemNo" indexed="true"/></td>												
						</tr>
					</c:forEach>
				</table>
				<s:submit property="orderOperation" value="注文"/>
			</s:form>
		</div>
	</body>
</html>