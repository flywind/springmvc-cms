<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<nav id="mainnav-container">
	<div id="mainnav">

		<div id="mainnav-menu-wrap">
			<div class="nano">
				<div class="nano-content">
					<ul id="mainnav-menu" class="list-group">
						<!--Home page-->
						<t:if test="homePage">
							<li class="active-link"><a href="#;" t:type="pagelink"
								t:page="admin/Index"> <i class="iconfont uxd-home"></i> <span
									class="menu-title"> <strong>首页</strong>
								</span>
							</a></li>
							<p:else>
								<li><a href="#;" t:type="pagelink" t:page="admin/Index">
										<i class="iconfont uxd-home"></i> <span class="menu-title"> <strong>首页</strong>
									</span>
								</a></li>
							</p:else>
						</t:if>

						<!-- Menu -->
						<c:forEach items="${menus}" var="menu">
							
								<li class="${menu.activeStyle}"><a href="#"> <i
										class="fa ${menu.ioncCls}"></i> <span class="menu-title">
											<strong>${menu.name}</strong>
									</span> <i class="arrow"></i>
								</a>
									<ul class="collapse">
										<c:forEach items="${menu.childResource}" var="imenu">
											
												<li class="${imenu.activeStyle}"><a
													href="${pageContext.request.contextPath}${imenu.url}">${imenu.name}</a></li>
												
										</c:forEach>
									</ul></li>
							
						</c:forEach>

					</ul>

				</div>
			</div>
		</div>

	</div>
</nav>