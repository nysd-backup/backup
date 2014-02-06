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
		<hr>
		<h3>facebook</h3>
		<div id="fb-root"></div>
		<script>
		
		(function(d, s, id) {
		  var js, fjs = d.getElementsByTagName(s)[0];
		  if (d.getElementById(id)) return;
		  js = d.createElement(s); js.id = id;
		  js.src = "https://connect.facebook.net/ja_JP/all.js#xfbml=1&appId=246515562187819";
		  fjs.parentNode.insertBefore(js, fjs);
		}(document, 'script', 'facebook-jssdk'));
		
		function callback(){
			FB.getLoginStatus(function(response) {
				if (response.status === 'connected') {
				 	var uid = response.authResponse.userID;
				    var accessToken = response.authResponse.accessToken;
					alert(uid);
				} else if (response.status === 'not_authorized') {
						alert("not auth");
				} else {
				    	alert("error");
				}
			});
		}
		
		</script>
		<!-- like and share-->
		<div class="fb-like" data-href="http://nao001.nao001.cloudbees.net/" data-layout="standard" data-action="like" data-show-faces="true" data-share="true"></div>		
		<br/>
		<!-- comments -->
		<div class="fb-comments" data-href="http://nao001.nao001.cloudbees.net/" data-numposts="5" data-colorscheme="light"></div>
		<br/>
		<!-- embedded post -->
		<div class="fb-post" data-href="https://www.facebook.com/FacebookDevelopers/posts/10151471074398553" data-width="500"></div>
		<br/>
		<!-- send -->
		<div class="fb-send" data-href="http://nao001.nao001.cloudbees.net/" data-colorscheme="light"></div>
		<br/>
		<!-- login -->
		<div class="fb-login-button" data-max-rows="1" onlogin="callback()" data-size="medium" data-show-faces="false" data-auto-logout-link="false"></div>
	</body>
</html>