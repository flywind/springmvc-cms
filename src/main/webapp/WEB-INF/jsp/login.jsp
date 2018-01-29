<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title>用户登录</title>
		<link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
		<jsp:include page="commons/head.jsp"></jsp:include>
	</head>
	<body>
		<div id="container" class="cls-container">

		    <div id="bg-overlay" class="bg-img cur-img"></div>
		
		    <div class="cls-content">
		        <div class="cls-content-sm panel">
			      <div class="page-panel pR">
				      	<div class="lang-position"><t:FLocaleBar/></div>
				          <div class="mar-ver pad-btm">
				              <h3 class="h4 mar-no">用户登录</h3>
				              <p class="text-muted">您好，请使用您的账号登录</p>
				              <c:if test="${error != null}">
				               <div class="alert alert-pink alert-sign">
				                   ${error}
				               </div>
				              </c:if>
				          </div>
				          <form action="" method="post">
				              <div class="form-group">
				                  <input type="text" class="form-control" placeholder="用户名" name="username" value="<shiro:principal/>"/>
				              </div>
				              <div class="form-group">
				                  <input type="password" class="form-control" placeholder="密码" name="password"/>
				              </div>
				              <div class="checkbox pad-btm text-left">
				                  <input class="magic-checkbox" type="checkbox" name="rememberMe" value="true"/>
				                  <label>记住密码</label>
				                  <a href="#;" class="btn-link mar-rgt blue-link pull-right">忘记密码</a>
				              </div>
				              <input type="submit" class="btn btn-primary btn-lg btn-block" value="登录">
				          </form>
				      </div>
				
				      <div class="pad-all">	                
				          <span>没有账号?</span><a href="#;" class="btn-link blue-link mar-lft">马上注册</a>
				      </div>
				  </div>
		    </div>
		
		    <div class="signup-bg">
		        <div id="signup-bg-list">
		            <div class="signup-loading"><i class="psi-repeat-2"></i></div>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-1.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-2.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-3.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-4.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-5.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-6.jpg"/>
		            <img class="signup-chg-bg" src="${pageContext.request.contextPath}/assets/images/bg-img/thumbs/bg-img-7.jpg"/>
		        </div>
		    </div>
		
		
		</div>
	<script src="${pageContext.request.contextPath}/assets/js/inits/login.js"></script>
	</body>
</html>