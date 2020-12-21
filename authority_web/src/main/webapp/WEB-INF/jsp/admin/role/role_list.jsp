<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>角色列表</title>
	<script type="text/javascript" src="${ctx}/static/js/hp_list.js"></script>
</head>
<body>
<form>
	<div class="demoTable">
		角色名：
		<div class="layui-inline">
			<input class="layui-input" name="role" id="roleName"
				   autocomplete="off">
		</div>
		<button class="layui-btn bt_search" data-type="reload">搜索</button>
		<button type="reset" class="layui-btn layui-btn-primary">重置</button>
	</div>
</form>
<div style="height: 10px;" />
<div>
	<button class="layui-btn bt_add" data="893px, 550px" data-url="${ctx}/role/toAddPage">新增</button>
	<button class="layui-btn layui-btn-warm bt_update" data="893px, 550px" data-url="${ctx}/role/toUpdatePage">修改</button>
	<button class="layui-btn layui-btn-danger bt_delete" data-url="${ctx}/role/delete">删除</button>
	<button class="layui-btn layui-btn-normal bt_setMenu" data="893px, 550px" data-url="${ctx}/role/toSetMenuPage">分配权限</button>
</div>



<table class="layui-hide" id="role" lay-data="{id: 'role'}"></table>
<script>
	layui.use('table', function() {
		var table = layui.table;
		var util = layui.util;

		table.render({
			elem : '#role',
			url : '${ctx}/role/list',
			parseData:function (res) {
				return{
					"code":0,
					"msg":res.message,
					"count":res.total,
					"data":res.records
				};
			}
			,page: true //开启分页
			,limit: 3

			,cellMinWidth : 80,
			page : true,

			cols : [ [ {
				type : 'checkbox'
			}, {
				field : 'id',
				width : 300,
				title : 'ID',
				sort : true
			}, {
				field : 'role',
				title : '角色名'
			}, {
				field : 'remark',
				title : '备注'
			}, {
				field : 'createTime',
				title : '创建时间'
			}, {
				field : 'updateTime',
				title : '更新时间'
			}] ]
		});

		//搜索条件
		var $ = layui.$, active = {
			reload : function() {
				table.reload($('table').attr("id"), {
					where : {
						role : $('#roleName').val()
					}
				});
			}
		};
		//触发搜索条件事件
		$('.bt_search').on('click', function (e){
			var type = $(this).data('type');
			active[type] ? active[type].call(this) : '';
			return false;
		})
		$('.bt_delete').on('click', function() {
			console.log("ok");
			var checkStatus = layui.table.checkStatus($('table').attr("id"));
			if (checkStatus.data.length<1) {
				layer.msg("至少选择一行数据", {icon: 2, time: 1500});
				return;
			};
			let url= $(this).attr("data-url");
			// 接着，进行删除确认
			layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
				$.ajax({
					type: "POST",
					url: url,
					contentType : "application/json",
					dataType: 'json',
					data: JSON.stringify(checkStatus.data),
					success: function(data){
						if (data.result==true) {
							layer.msg(data.msg, {icon: 1, time: 1500});
							layui.table.reload($('table.layui-hide').attr("id"));
						}else {
							layer.msg(data.msg, {icon: 2, time: 1500});
						}
					},
					error: function(res){
						layer.msg("未知异常", {icon: 2});
					}
				});
			});
		});
	});
</script>
</body>
</html>
