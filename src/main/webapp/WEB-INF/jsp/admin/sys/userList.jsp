<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>用户列表</title>
<jsp:include page="../../commons/head.jsp"></jsp:include>
</head>
<body>
	<div id="container"
		class="aside-float aside-bright mainnav-sm navbar-fixed footer-fixed aside-fixed mainnav-fixed">
		<jsp:include page="../../commons/header.jsp"></jsp:include>
		<div class="boxed">
			<div id="content-container">
				<div id="page-title">
					<h1 class="page-header text-overflow">
						<span class="title">用户列表</span>
						<ol class="breadcrumb">
							<li><a href="#;" t:type="pagelink" t:page="admin/Index">首页</a>
							</li>
							<li>用户列表</li>
						</ol>
					</h1>
				</div>
				<div id="page-content">
					<div class="page-panel">
						
								<div class="tab-pane fade active in">
									<form class="form-inline" data-search-form="true">
										
										<div class="form-group mar-rgt">
											<input placeholder="用户名" id="username" class="form-control iw120 form-control" name="username" type="text">
										</div>
										<div class="form-group mar-rgt">
											<input placeholder="员工姓名" id="name" class="form-control form-control" name="name" type="text">
										</div>
										<button type="submit" class="btn btn-primary mar-rgt" data-serach-submit="true"><i class="fa fa-search mar-pixe-r5"></i> 搜索</button>
										<button type="reset" class="btn btn-default" data-serach-reset="true"><i class="fa fa-eraser"></i> 重置</button>
									</form>
									<div class="bars" data-action="true">
										<a class="btn btn-success mar-pixe-r10" href="${pageContext.request.contextPath}/admin/sys/userCreate"><i class="fa fa-plus mar-pixe-r5"></i>添加 </a>
										<button class="btn btn-info mar-pixe-r10" data-update="true"><i class="fa fa-pencil mar-pixe-r5"></i>编辑</button>
										<button class="btn btn-danger mar-pixe-r10"><i class="fa fa-trash-o mar-pixe-r5"></i>删除</button>
										<button class="btn btn-danger"><i class="fa fa-undo mar-pixe-r5"></i>重置密码</button>
									</div>
									<div data-grid-box="true" style="height:400px;border:1px solid #f4f4f4;margin-top:10px;">
										<table class="table" data-grid="true"></table>
									</div>
									
								</div>
								<div class="clearfix"></div>
							
						
					</div>
				</div>
			</div>

			<jsp:include page="../../commons/menu.jsp"></jsp:include>

		</div>
		<jsp:include page="../../commons/footer.jsp"></jsp:include>
		<button class="scroll-top btn">
			<i class="pci-chevron chevron-up"></i>
		</button>

	</div>
	<script>
		require([ 'jquery','common'], function($,Common) {
			var page = {};
			page.postUrl = '${pageContext.request.contextPath}/admin/sys/user/list',
			page.postData = {username:'',name:''},
			page.listGrid = $('[data-grid="true"]'),
			page.searchForm = $('[data-search-form="true"]'),
			page.searchFormBtn = $('[data-serach-submit="true"]'),
			page.searchFormReset = $('[data-serach-reset="true"]'),
			page.updateData = $('[data-update="true"]');
			
			//datagrid配置
			page.setDataGrid = function(queryURL, queryData) {
				var setting = {
						url: queryURL,
						queryParams: queryData,
						sortName:"lastUpdateTime",
						sortOrder:"desc",
						columns:[[
							{ field: 'checkbox',  checkbox:true },
			                { field: 'username', title: '用户名', width: 80},
			                { field: 'name', title: '姓名', width: 150, align: 'center'},
			                { field: 'roleNames', title: '角色', width: 150, align: 'center'},
			                { field: 'companyName', title: '单位名称', width: 150, align: 'center'},
			                { field: 'lastUpdatePerson', title: '最后更新人', width: 150, align: 'center'},
			                { field: 'lastUpdateTime', title: '最后更新时间', width: 150, align: 'center'},
			            ]]
					};
					
					Common.datagrid(setting, page.listGrid);
			}
    	
			//datagrid加载列表数据
			page.setDataGrid(page.postUrl, page.postData);
			
			//搜索查询
			page.searchForm.on('submit',function(e){
				e.preventDefault();
				var data = $.extend(page.postData, page.searchForm.serializeObject());
				page.listGrid.datagrid('load',data);
			})
			
			//重置搜索
			page.searchFormReset.on('click',function(e){
				var data = {username:'',name:''};
				page.listGrid.datagrid('load',data);
			})
			
			//编辑
			page.updateData.on('click',function(){
				var row = page.listGrid.datagrid('getSelected');
				if(row){
					Common.jump('${pageContext.request.contextPath}/admin/sys/userUpdate/'+row.id);
				}
			})
			
			//datagrid宽度自适应
			$(window).resize(function(){
				page.listGrid.datagrid('resize',{
					width: 'auto'
				})
			});
			
		})
	</script>
</body>
</html>