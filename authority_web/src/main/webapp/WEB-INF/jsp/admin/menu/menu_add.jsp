<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单添加</title>
<%@ include file="/static/base/common.jspf"%>
<style type="text/css">
	.iconfont {
	    font-size : 20px !important;
	}
</style>
<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>
<script type="text/javascript" src="${ctx}/static/js/menu.js"></script>
</head>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane" action="${ctx}/menu/save">
			<div class="layui-form-item">
				<label class="layui-form-label">父级菜单</label>
				<div class="layui-input-block">
					<select name="pid"  lay-verify="required" lay-filter ="sel" id ="pid">
						<option value="0" selected>顶级目录</option>
						<c:forEach items="${list }" var="menu">
							<option value="${menu.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${menu.menuName }</option>
							<c:forEach items="${menu.nodes }" var="menu2">
								<option value="${menu2.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${menu2.menuName }</option>
<%--								<c:forEach items="${menu2.nodes }" var="menu3">--%>
<%--									<option value="${menu3.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${menu3.menuName }</option>--%>
<%--								</c:forEach>--%>
							</c:forEach>
						</c:forEach>
<%--	                        <option value="">---</option>--%>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单名称</label>
				<div class="layui-input-block">
					<input type="text" name="menuName" autocomplete="off"
						placeholder="请输入菜单名称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单类型</label>
				<div class="layui-input-block">
					<select name="menuType" lay-verify="required" id="menuType" lay-filter="changmenu">
<%--						<option value=""></option>--%>
<%--						<option value="1">目录</option>--%>
<%--						<option value="2">菜单</option>--%>
<%--						<option value="3">按钮</option>--%>
<%--	<option value="">---</option>--%>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">菜单图标</label>
				<div class="layui-input-inline">
					<input type="text" name="menuImg" autocomplete="off"
						placeholder="请输入菜单图标" class="layui-input">
				</div>
				<div class="layui-form-mid layui-word-aux"></div>
			</div>
			<div class="layui-form-item icon-div" style="display: none;">
				<div class="layui-input-block">
					<c:forEach var="icon" items="${iconFont}">
						<input type="radio" name="icon" lay-filter="icon" value="${icon}" title="<span class='iconfont ${icon}'></span>">
					</c:forEach>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">跳转地址</label>
				<div class="layui-input-block">
					<input type="text" name="url" autocomplete="off"
						placeholder="请输入跳转地址" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">回调方法</label>
				<div class="layui-input-block">
					<input type="text" name="function" autocomplete="off"
						placeholder="请输入回调方法" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">顺序</label>
				<div class="layui-input-block">
					<input type="text" name="seq" autocomplete="off"
						placeholder="请输入顺序" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">权限标识符</label>
				<div class="layui-input-block">
					<input type="text" name="permiss" autocomplete="off"
						placeholder="请输入权限标识符" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
<%--	<script type="javascript">--%>
<%--	layui.use('form', function() {--%>
<%--	var form = layui.form;--%>

<%--	//通用弹出层表单提交方法--%>
<%--	form.on('submit(demo1)', function(data){--%>
<%--	console.log(data.field);--%>
<%--	$.post($('form').attr("action"),data.field, function (e){--%>
<%--	var data = JSON.parse(e);--%>
<%--	if (data.result==true) {--%>
<%--	parent.closeLayer(data.msg);--%>
<%--	}else {--%>
<%--	layer.msg('操作失败：' + data.msg, {icon: 2, time: 2000});--%>
<%--	}--%>
<%--	})--%>
<%--	return false;--%>
<%--	})--%>

<%--	});--%>

<%--	$(function (){--%>
<%--	$('input[name="menuImg"]').on('click', function(){--%>
<%--	$('.icon-div').toggle();--%>
<%--	})--%>

<%--	layui.use('form', function() {--%>
<%--	var form = layui.form;--%>

<%--	form.on('radio(icon)', function(data){--%>
<%--	console.log(data.value);--%>
<%--	$('.icon-div').hide();--%>
<%--	$('input[name="menuImg"]').val(data.value);--%>
<%--	$('.layui-form-mid').empty().append("<span class='iconfont "+data.value+"'></span>");--%>
<%--	});--%>
<%--	});--%>
<%--	})--%>
<%--	</script>--%>
<%--	<script type="javascript">--%>
<%--	 var $pid = $("#pid");--%>
<%--	 var $menuType = $("#menuType");--%>
<%--	 $.getJSON("js/date.json",function(data) {--%>
<%--            json = data;--%>
<%--            $.each(data,function(index,object) {--%>
<%--                  var menu_name = object.pid;--%>
<%--                  $pid.append(--%>
<%--                  		'<option value="${menu_name}">${menu_name}</option>'--%>
<%--	);--%>

<%--	});--%>
<%--           $pid.change(function() {--%>
<%--              $pid.empty();--%>
<%--              $pid.append('<option value=""---</option>');--%>

<%--           var menuElementName = $(this).children("option:selected").test();--%>
<%--           $each(json,function(index,obj) {--%>
<%--            var menuType = obj.menuName;--%>
<%--            if (menuElementName === menuType){--%>
<%--            	var menuTypes = obj.menuType;--%>
<%--            	$menuType.append('<option value = "${menu_name}"${menu_name}</option>')--%>
<%--	}--%>
<%--	})--%>
<%--	})--%>
<%--	})--%>
<%--	</script>--%>
<%--	<script type="javascript">--%>
<%--	   var munuip;--%>
<%--	   window.onload=function() {--%>
<%--	}--%>
<%--	function Change_Select() {--%>
<%--          var pid = document.getElementById("pid").value;--%>
<%--          var url = "select?id="+escape(pid);--%>

<%--          if (window.XMLHttpRequest){--%>
<%--          	munuip = new XMLHttpRequest();--%>
<%--	}else if (window.ActiveXObject){--%>
<%--          	munuip = new ActiveXObject("");--%>
<%--	}--%>
<%--          if (munuip){--%>
<%--          	munuip.open("GET",url,true);--%>
<%--          	munuip.onreadystatechange = callback;--%>
<%--          	munuip.send(null)--%>
<%--	}--%>
<%--	}--%>
<%--	function callback() {--%>
<%--          if (munuip.readyState==4){--%>
<%--          	if (munuip.status==200){--%>
<%--          		parseMassage();--%>
<%--	}else {--%>
<%--          		alert("不能得到描述信息："+munuip.statusText);--%>
<%--	}--%>
<%--	}--%>
<%--	}--%>
<%--	function parseMassage() {--%>
<%--        var xmlDoc = munuip.responseXML.documentElement;--%>
<%--        var xSel = xmlDoc.getElementsByTagName('select');--%>
<%--        var select_root = document.getElementById('menuType');--%>
<%--        select_root.options.length=0;--%>
<%--        for (var i = 0;i<xSel.length;i++){--%>
<%--        	var xValue = xSel[i].childNodes[0].firstChild.nodeValue;--%>
<%--        	var xText = xSel[i].childNodes[1].firstChild.nodeValue;--%>
<%--        	var option = new Option(xText,xValue);--%>
<%--        	try {--%>
<%--	    select_root.add(option);--%>
<%--	}catch (e) {--%>

<%--	}--%>
<%--	}--%>
<%--	}--%>

<%--	</script>--%>
	<script type="text/javascript">
	//  var arr_pid = ["顶级菜单","用户管理","系统管理","菜单管理"];
	//  var arr_menuType = [
	// // <option value="1">目录</option>
	// // <option value="2">菜单</option>
	// // <option value="3">按钮</option>
	// ["目录"],
	// ["菜单"],
	// ["按钮"],
	// ];
	//  var pid = document.getElementById('pid');
	//  var menuType = document.getElementById('menuType');
	//
	//  onload = function() {
    //        pid.length = arr_pid.length;
    //        for (var i = 0 ;i <arr_pid.length;i++){
    //        	pid.options[i].text = pid.options[i].value = arr_pid[i];
	// }
    //        var index = 0;
    //        pid.index = index;
    //        menuType.length = arr_menuType[index].length;
    //        for (var  j = 0;j<arr_menuType[index].length;j++){
    //        	menuType.options[j].text = menuType.options[j].value = arr_menuType[index][j];
	// }
	// }
	//
	// function alter(index) {
    //      pid.index = index;
    //      menuType.length = arr_menuType[index].length;
    //      for(var  j= 0 ;j<arr_menuType[index].length;j++){
    //      	menuType.options[j].text = menuType.options[j].value = arr_menuType[index][j];
	// }
	// }
	function selectOnchange(obj) {
      $("#pid").removeAttr("disable");
      $("#menuType").removeAttr("disable");

      console.log(obj.options[obj.selectedIndex].value);
      if (obj.options[obj.selectedIndex].value='0'){
      	$("#menuType").value = 1;
	}else if(obj.options[obj.selectedIndex].value='${menu.id}'){
      	$("#menuType").value = 2;
	}else (obj.options[obj.selectedIndex].value='${menu2.id}')
	{
      	$("#menuType").value = 3;
	}
	}
	$(function(){
	$("select[name='pid']").change(function(){ selectOnchange(this); })
	})
	</script>
</body>
</html>