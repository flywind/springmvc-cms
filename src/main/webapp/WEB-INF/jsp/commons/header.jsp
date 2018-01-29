<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="navbar">
	<div id="navbar-container" class="boxed">
		<div class="navbar-header">
			<a href="#" class="navbar-brand"> <img
				src="${pageContext.request.contextPath}/assets/images/logo.png"
				alt="Flywind Logo" class="brand-icon" />
				<div class="brand-title">
					<span class="brand-text">Tapestry cms</span>
				</div>
			</a>
		</div>

		<div class="navbar-content clearfix">
			<ul class="nav navbar-top-links pull-left">

				<li class="tgl-menu-btn"><a class="mainnav-toggle" href="#">
						<i class="ion-navicon-round"></i>
				</a></li>

			</ul>

			<ul class="nav navbar-top-links pull-right">

				<!-- <li>
						<t:FLocaleBar/>
					</li> -->

				<li id="dropdown-user" class="dropdown"><a href="#"
					data-toggle="dropdown" class="dropdown-toggle text-right"> <span
						class="pull-right"> <i class="ion-person ic-user"></i>
					</span>
						<div class="username hidden-xs">
							${user.username}
						</div>
				</a>

					<div
						class="dropdown-menu dropdown-menu-lg dropdown-menu-right panel-default">
						<div class="panel-body text-center bg-primary">
							<img alt="${user.username}"
								class="img-lg img-circle img-border mar-btm"
								src="${user.picUrl}" />
							<h4 class="mar-no text-light">
								
						                您好, ${user.username}.
						               
							</h4>
						</div>
						<div class="list-group bg-trans">
							<a class="list-group-item" t:type="pagelink"
								t:page="admin/sys/UserUpdate" t:context="${user.id}">个人信息</a> <a
								class="list-group-item" t:type="pagelink"
								t:page="admin/sys/UpdateInitPwd" t:context="${user.id}">修改密码</a>

						</div>
						<div class="pad-all text-right">
							<t:FLoginLink cls="btn btn-default btn-flat"
								loginCls="ion-log-in" logoutCls="ion-log-out" />
						</div>
					</div></li>
			</ul>
		</div>


	</div>
</header>