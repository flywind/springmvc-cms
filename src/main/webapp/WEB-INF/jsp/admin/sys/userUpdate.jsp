<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="en">

	<head>
		<title>添加用户</title>
		<link href="${pageContext.request.contextPath}/assets/js/base/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
		<jsp:include page="../../commons/head.jsp"></jsp:include>
	</head>
<style>
.menuContent {
width: 300px;
height: 400px;
border: 1px solid #f2f2f2;
background-color: #f2f2f2;
overflow: auto;
}
</style>
	<body>
		<div id="container" class="aside-float aside-bright mainnav-sm navbar-fixed footer-fixed aside-fixed mainnav-fixed">
			<jsp:include page="../../commons/header.jsp"></jsp:include>
			<div class="boxed">
				<div id="content-container">
					<div id="page-title">
						<h1 class="page-header text-overflow">
						<span class="title">添加用户</span>
						<ol class="breadcrumb">
							<li>
								<a href="#;" t:type="pagelink" t:page="admin/Index">首页</a>
							</li>
							<li>
								<a href="#;" t:type="pagelink" t:page="admin/sys/UserList">用户列表</a>
							</li>
							<li>添加用户</li>
						</ol>
					</h1>
					</div>
					<div id="page-content">
						<div class="page-panel">

							<form class="form-horizontal clearfix" id="baseForm" method="post" action="">
								<div class="page-panel">
									
									<div id="errorMessage"></div>
									
									<div class="form-group">
										<label class="col-sm-3 control-label">用户名<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input class="form-control" type="text" placeholder="用户名" id="username" name="username" value="${user.username}"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">员工姓名<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input class="form-control" type="text" placeholder="员工姓名" id="name" name="name" value="${user.name}"/>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-3 control-label">单位名称<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input type="hidden" id="companyId" name="companyId" value="${user.companyId}"/>
											<input class="form-control b-white" readonly="readonly" type="text" placeholder="单位名称" id="companyName" name="companyName" value="${user.companyName}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">手机号码<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input class="form-control" type="text" placeholder="手机号码" id="mobile" name="mobile" value="${user.mobile}"/>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-3 control-label">邮箱<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input class="form-control" type="text" placeholder="邮箱" id="email" name="email" value="${user.email}"/>
										</div>
									</div>
									<!-- <div class="form-group">
										<label class="col-sm-3 control-label">密码通知方式<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											
											<label style="margin-right:8px;">邮箱</label>
											<input type="radio" id="sendPwdEmail" value="1" name="message" checked="checked" />
											<label style="margin-right:8px; margin-left: 40px;">手机</label>
											<input type="radio" id="sendPwdPhone" value="2" name="message"/>
											
										</div>
									</div> -->
									<div class="form-group">
										<label class="col-sm-3 control-label">角色<span class="text-danger">*</span></label>
										<div class="col-sm-6">
											<input class="form-control b-white" readonly="readonly" type="text" placeholder="角色" id="roles" name="roles" value="${user.roleNames}"/>
											<input type="hidden" id="roleIds" name="roleIds"/>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-3 col-sm-6">
											<input type="submit" class="btn btn-info btn-flat mar-pixe-r10" value="提交"/>
											<a href="${pageContext.request.contextPath}/admin/sys/userList" class="btn btn-default btn-flat">取消</a>
										</div>
									</div>

								</div>
							</form>
							
							<div class="clearfix"></div>

						</div>
					</div>
					
					<div id="menuContent" class="menuContent" style="display:none; position: absolute;">
				        <ul id="tree" class="ztree" style="margin-top:0;"></ul>
				    </div>
				    <div id="roleMenuContent" class="menuContent" style="display:none; position: absolute;">
				        <ul id="roleTree" class="ztree" style="margin-top:0;"></ul>
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
			require(['jquery', 'common', 'ztree', 'validate', 'bootstrap'], function($, Common, undefined, undefined, undefined) {
				
				var page = {};
				
				//初始化组织机构树
				page.companyTree = function(){
					
					var setting = {
		                view: {
		                    dblClickExpand: false,
		                    selectedMulti: false
		                },
		                data: {
		                    simpleData: {
		                        enable: true
		                    }
		                },
		                callback: {
		                    onClick: onClick
		                }
		            };
		
					$.ajax({
						type:"post",
						url:"${pageContext.request.contextPath}/admin/sys/organizationTree",
						async:true,
						cache:true,
						success:function(spec){
							var arr = new Array();
							spec = eval(spec);
							var openParentIds =new Array();
					          
				            $.each(spec,function(i,o){
				            	  var selectedIdObj = $("#companyId");
				                  var selectedId=selectedIdObj.val();
				                if(selectedId==o.id){
				                	var str=o.parentIds;
				                	openParentIds=str.split("/");
				                }
				            })
							$.each(spec,function(i,o){
								var has=false;
								$.each(openParentIds,function(i,parentId){
									if(o.id==parentId){
										has=true;
									}
								})
				                var item=null;
				                if(has){
				                   item = { id:o.id, pId:o.parentId, name:o.name, open:true};
				                }else{ 
				                   item = { id:o.id, pId:o.parentId, name:o.name, open:o.rootNode};  
				                }
								
								arr[i] = item;
							})
				
				            $.fn.zTree.init($("#tree"), setting, arr);
				            //$('#companyName').focus(showMenu);
						}
					});
					
					
					function onClick(e, treeId, treeNode) {
		                var zTree = $.fn.zTree.getZTreeObj("tree"),
		                        nodes = zTree.getSelectedNodes(),
		                        id = "",
		                        name = "";
		                nodes.sort(function compare(a,b){return a.id-b.id;});
		                for (var i=0, l=nodes.length; i<l; i++) {
		                    id += nodes[i].id + ",";
		                    name += nodes[i].name + ",";
		                }
		                if (id.length > 0 ) id = id.substring(0, id.length-1);
		                if (name.length > 0 ) name = name.substring(0, name.length-1);
		                $('#companyId').val(id);
		                $("#companyName").val(name);
		                hideMenu();
		            }
		
		            function showMenu() {
		            	var cityObj = $("#companyId");
		                 var organizationValue=cityObj.val();
		                 var zTree = $.fn.zTree.getZTreeObj("tree");
		                 var nodes = zTree.transformToArray(zTree.getNodes());
		                 for(var i=0;i<nodes.length;i++)
		                 { 
		                	if(nodes[i].id==organizationValue){
		                		nodes[i].checked = true;
		                		zTree.selectNode(nodes[i],false);
		                	}	
		                 }
		                var cityNameObj = $("#companyName");
		                var cityOffset = $("#companyName").offset();
		                $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityNameObj.outerHeight() + "px"}).slideDown("fast");

		                $("body").bind("mousedown", onBodyDown);
		            }
		            function hideMenu() {
		                $("#menuContent").fadeOut("fast");
		                $("body").unbind("mousedown", onBodyDown);
		            }
		            function onBodyDown(event) {
		                if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
		                    hideMenu();
		                }
		            }
						
				}
				
				//初始化角色树
				page.roleTree = function(){
					var setting = {
		                check: {
		                    enable: true ,
		                    chkboxType: { "Y": "ps", "N": "ps" }
		                },
		                view: {
		                    dblClickExpand: false
		                },
		                data: {
		                    simpleData: {
		                        enable: true
		                    }
		                },
		                callback: {
		                    onCheck: onCheck
		                }
		            };
				
					$.ajax({
						type:"post",
						url:"${pageContext.request.contextPath}/admin/sys/roleTree/"+${user.id},
						async:true,
						cache:true,
						success:function(spec){
							spec = eval(spec);
							var arr = new Array();
							
							$.each(spec,function(i,o){
								var item = { id:o.id, name:o.name, checked:false};
								arr[i] = item;
							})
				
				            $.fn.zTree.init($("#roleTree"), setting, arr);
		            		$('input[name="roles"]').click(showMenu);
						}
					});
					
		
		            function onCheck(e, treeId, treeNode) {
		                var zTree = $.fn.zTree.getZTreeObj("roleTree"),
		                        nodes = zTree.getCheckedNodes(true),
		                        id = "",
		                        name = "";
		                nodes.sort(function compare(a,b){return a.id-b.id;});
		                for (var i=0, l=nodes.length; i<l; i++) {
		                    id += nodes[i].id + ",";
		                    name += nodes[i].name + ",";
		                }
		                if (id.length > 0 ) id = id.substring(0, id.length-1);
		                if (name.length > 0 ) name = name.substring(0, name.length-1);
		                $("#roleIds").val(id);
		                $('input[name="roles"]').val(name);
		//                hideMenu();
		            }
		
		            function showMenu() {
		                var cityObj = $('input[name="roles"]');
		                var cityOffset = $('input[name="roles"]').offset();
		                $("#roleMenuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
		
		                $("body").bind("mousedown", onBodyDown);
		            }
		            function hideMenu() {
		                $("#roleMenuContent").fadeOut("fast");
		                $("body").unbind("mousedown", onBodyDown);
		            }
		            function onBodyDown(event) {
		                if (!(event.target.id == "menuBtn" || event.target.id == "roleMenuContent" || $(event.target).parents("#roleMenuContent").length>0)) {
		                    hideMenu();
		                }
		            }

				}
				
				//提交表单
				page.formSubmit = function(){
					
					var formValidate = $("#baseForm").validate({
					    rules: {
					      username: {
					        required: true,
					        maxlength: 20,
					        remote: {
							    url: "${pageContext.request.contextPath}/admin/sys/user/check",     //后台处理程序
							    type: "post",               //数据发送方式
							    dataType: "json",           //接受数据格式   
							    data: {                     //要传递的数据
							        username: function() {
							            return $("#username").val();
							        }
							    }
							}
					      },
					      name: {
					        required: true,
					        maxlength: 10
					      },
					      companyName: {
					        required: true
					      },
					      email: {
					        required: true,
					        email: true
					      },
					      mobile: {
					        required: true,
					        contact:true
					      },
					      roles: "required"
					    },
					    messages: {
					      username: {
					        required: "请输入用户名",
					        minlength: "用户名长度不能大于 20 个字母",
					        remote: '用户名已经存在'
					      },
					      name: {
					        required: "请输入员工姓名",
					        minlength: "员工姓名长度不能大于 10 个字母"
					      },
					      companyName: {
					        required: "请选择单位"
					      },
					      email: {
					      	required: "请输入邮箱",
					      	email: "请输入一个正确的邮箱"
					      },
					      mobile: {
					      	required: '请输入正确的手机号码'
					      },
					      roles: "请选择角色"
					    },
					    submitHandler: function(form) {
					      var obj = $("#baseForm").serializeObject()
					    	$.ajax({
						        url: '${pageContext.request.contextPath}/admin/sys/user/create',
						        method: 'post',
						        contentType: 'application/json', // 这句不加出现415错误:Unsupported Media Type
						        data: JSON.stringify(obj), // 以json字符串方式传递
						        success: function(data) {
						        	data = JSON.parse(data);
						        	if(data.messageCode == 0){
						        		Common.alert(data.message,$('#errorMessage'),'success');
							        	var time = setTimeout(function(){
							        		$('#errorMessage').empty();
							        		clearTimeout(time);
							        	},2000);
						        	}else if(data.messageCode == 1){
						        		Common.alert(data.message,$('#errorMessage'),'danger');
							        	var time = setTimeout(function(){
							        		$('#errorMessage').empty();
							        		clearTimeout(time);
							        	},2000);
						        	}
						        	var treeObj = $.fn.zTree.getZTreeObj("tree");
										treeObj.cancelSelectedNode();
									var treeObj2 = $.fn.zTree.getZTreeObj("roleTree");
										treeObj2.cancelSelectedNode();
										treeObj2.checkAllNodes(false);
						        	formValidate.resetForm();
						            $("#baseForm")[0].reset();
						        },
						        error: function(data) {
						            console.log("error...");
						        }
						    });
						    return false;
					    }
					});
					
					
					
					
				}
				
				//创建组织机构树
				page.companyTree();
				
				//创建角色树
				page.roleTree();
				
				//表单处理
				page.formSubmit();
				
			})
		</script>
	</body>

</html>