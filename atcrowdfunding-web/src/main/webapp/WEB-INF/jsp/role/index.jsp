<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet"
	href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
<link rel="stylesheet" href="${APP_PATH}/css/main.css">
<style>
.tree li {
	list-style-type: none;
	cursor: pointer;
}

table tbody tr:nth-child(odd) {
	background: #F4F4F4;
}

table tbody td:nth-child(even) {
	color: #C00;
}
</style>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<div>
					<a class="navbar-brand" style="font-size: 32px;" href="#">众筹平台
						- 角色维护</a>
				</div>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li style="padding-top: 8px;">
						<div class="btn-group">
							<button type="button"
								class="btn btn-default btn-success dropdown-toggle"
								data-toggle="dropdown">
								<i class="glyphicon glyphicon-user"></i> ${loginUser.username} <span
									class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#"><i class="glyphicon glyphicon-cog"></i>
										个人设置</a></li>
								<li><a href="#"><i class="glyphicon glyphicon-comment"></i>
										消息</a></li>
								<li class="divider"></li>
								<li><a href="index.html"><i
										class="glyphicon glyphicon-off"></i> 退出系统</a></li>
							</ul>
						</div>
					</li>
					<li style="margin-left: 10px; padding-top: 8px;">
						<button type="button" class="btn btn-default btn-danger">
							<span class="glyphicon glyphicon-question-sign"></span> 帮助
						</button>
					</li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<div class="tree">
					<%@include file="/WEB-INF/jsp/common/menu.jsp"%>
				</div>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="glyphicon glyphicon-th"></i> 数据列表
						</h3>
					</div>
					<div class="panel-body">
						<form class="form-inline" role="form" style="float: left;">
							<div class="form-group has-feedback">
								<div class="input-group">
									<div class="input-group-addon">查询条件</div>
									<input id="queryText" class="form-control has-success"
										type="text" placeholder="请输入查询条件">
								</div>
							</div>
							<button id="queryButton" type="button" class="btn btn-warning">
								<i class="glyphicon glyphicon-search"></i> 查询
							</button>
						</form>
						<button type="button" onclick="deleteRoles()"
							class="btn btn-danger" style="float: right; margin-left: 10px;">
							<i class=" glyphicon glyphicon-remove"></i> 删除
						</button>
						<button type="button" class="btn btn-primary"
							style="float: right;" onclick="Error()">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<br>
						<hr style="clear: both;">
						<div class="table-responsive">
							<form id="roleForm">
								<table class="table  table-bordered">
									<thead>
										<tr>
											<th width="30">#</th>
											<th width="30"><input id="allSelBox" type="checkbox"></th>
											<th>名称</th>
											<th width="100">操作</th>
										</tr>
									</thead>
									<tbody id="roleData">

									</tbody>
									<tfoot>
										<tr>
											<td colspan="6" align="center">
												<ul class="pagination">


												</ul>
											</td>
										</tr>
									</tfoot>
								</table>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
	<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${APP_PATH}/script/docs.min.js"></script>
	<script type="text/javascript" src="${APP_PATH}/layer/layer.js"></script>
	<script type="text/javascript">
		var likeflg = false;
		$(function() {
			$(".list-group-item").click(function() {
				if ($(this).find("ul")) {
					$(this).toggleClass("tree-closed");
					if ($(this).hasClass("tree-closed")) {
						$("ul", this).hide("fast");
					} else {
						$("ul", this).show("fast");
					}
				}
			});
			pageQuery(1);
			$("#queryButton").click(function() {
				var queryText = $("#queryText").val();
				if (queryText == "") {
					likeflg = false;
				} else {
					likeflg = true;
				}
				pageQuery(1);
			});
		});
		$("#allSelBox").click(function() {
			var flg = this.checked;
			$("#roleData :checkbox").each(function() {
				this.checked = flg;
			});
		});
		$("tbody .btn-success").click(function() {
			window.location.href = "assignPermission.html";
		});
		function pageQuery(pageNum) {
			var loadingIndex = null;
			var jsonData = {
				"pageNum" : pageNum,
				"pageSize" : 5
			};
			if (likeflg == true) {
				jsonData.queryText = $("#queryText").val();
			}

			$
					.ajax({
						type : "POST",
						url : "${APP_PATH}/role/pageQuery",
						data : jsonData,
						beforeSend : function() {
							loadingIndex = layer.msg('查询中', {
								icon : 16
							});
						},
						success : function(result) {
							layer.close(loadingIndex);
							if (result.success) {
								//局部刷新页面数据
								var tableContent = "";
								var pageContent = "";

								var rolePage = result.data;
								var roles = rolePage.list;

								$
										.each(
												roles,
												function(i, role) {
													tableContent += '<tr>';
													tableContent += '<td>'
															+ (i + 1) + '</td>';
													tableContent += '<td><input type="checkbox" name="roleid" value="'+role.id+'"></td>';
													tableContent += '<td>'
															+ role.rolename
															+ '</td>';
													tableContent += '<td>';
													tableContent += '	<button type="button" onclick="goAssignPage('
															+ role.id
															+ ')" class="btn btn-success btn-xs">';
													tableContent += '		<i class=" glyphicon glyphicon-check"></i>';
													tableContent += '	</button>';
													tableContent += '	<button type="button" onclick="Error()" class="btn btn-primary btn-xs">';
													tableContent += '		<i class=" glyphicon glyphicon-pencil"></i>';
													tableContent += '	</button>';
													tableContent += '	<button type="button" onclick="deleteRole('
															+ role.id
															+ ',\''
															+ role.rolename
															+ '\')" class="btn btn-danger btn-xs">';
													tableContent += '		<i class=" glyphicon glyphicon-remove"></i>';
													tableContent += '	</button>';
													tableContent += '</td>';
													tableContent += '</tr>';
												});

								if (pageNum > 1) {
									pageContent += '<li><a href="#" onclick="pageQuery('
											+ (pageNum - 1) + ')">上一页</a></li>';
								}

								for (var i = 1; i <= rolePage.totalNum; i++) {
									if (i == pageNum) {
										pageContent += '<li class="active"><a href="#" onclick="pageQuery('
												+ i + ')">' + i + '</a></li>';
									} else {
										pageContent += '<li><a href="#" onclick="pageQuery('
												+ i + ')">' + i + '</a></li>';
									}
								}

								if (pageNum < rolePage.totalNum) {
									pageContent += '<li><a href="#" onclick="pageQuery('
											+ (pageNum + 1) + ')">下一页</a></li>';
								}

								$("#roleData").html(tableContent);
								$(".pagination").html(pageContent);
							} else {
								layer.msg("用户信息分页查询失败，请刷新重试", {
									time : 3000,
									icon : 5,
									shift : 6
								}, function() {

								});
							}
						}
					});
		}
		function deleteRoles() {
			var boxes = $("#roleData :checkbox");
			if (boxes.length == 0) {
				layer.msg("请选择需要删除的用户信息", {
					time : 3000,
					icon : 5,
					shift : 6
				}, function() {

				});
			} else {
				layer.confirm("删除选择的用户信息，是否继续", {
					icon : 3,
					title : '提示'
				}, function(cindex) {
					//删除选择用户信息
					$.ajax({
						type : "POST",
						url : "${APP_PATH}/role/deletes",
						data : $("#roleForm").serializeArray(),
						success : function(result) {
							if (result.success) {
								pageQuery(1);
							} else {
								layer.msg("用户信息删除失败，请刷新重试", {
									time : 3000,
									icon : 5,
									shift : 6
								}, function() {

								});
							}
						}
					});
					layer.close(cindex);
				}, function(cindex) {
					layer.close(cindex);
				});
			}
		}
		function deleteRole(id, rolename) {
			layer.confirm("删除用户信息【" + rolename + "】，是否继续", {
				icon : 3,
				title : '提示'
			}, function(cindex) {
				//删除用户信息
				$.ajax({
					type : "POST",
					url : "${APP_PATH}/role/delete",
					data : {
						id : id
					},
					success : function(result) {
						if (result.success) {
							pageQuery(1);
						} else {
							layer.msg("用户信息删除失败，请刷新重试", {
								time : 3000,
								icon : 5,
								shift : 6
							}, function() {

							});
						}
					}
				});
				layer.close(cindex);
			}, function(cindex) {
				layer.close(cindex);
			});
		}
		function Error() {
			layer.msg("操作失败，您没有该权限进行此操作。", {
				time : 3000,
				icon : 5,
				shift : 6
			}, function() {

			});
		}

		function goAssignPage(id) {
			window.location.href = "${APP_PATH}/role/assign?id=" + id;
		}
	</script>
</body>
</html>
