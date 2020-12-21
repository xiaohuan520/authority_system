$(function (){
	$('input[name="menuImg"]').on('click', function(){
		$('.icon-div').toggle();
	})
	
	layui.use('form', function() {
		var form = layui.form;
		
		form.on('radio(icon)', function(data){
	  		console.log(data.value);
	  		$('.icon-div').hide();
	  		$('input[name="menuImg"]').val(data.value);
	  		$('.layui-form-mid').empty().append("<span class='iconfont "+data.value+"'></span>");
		});

		form.on('select(sel)', function(data){
			console.log(data.elem);
              var pid = $("#pid").val();
              var menuType = $("#menuType").val();
              var optionstring = "<option value=\"1\">目录</option>";
              var optionstring2 = "<option value=\"2\">菜单</option>";
			  var optionstring3 = "<option value=\"3\">按钮</option>";


              if (pid==0){
				  // optionstring
				  obj = document.getElementById("menuType"); ///drp是select控件的ID值
				  for(i=obj.options.length-1 ; i>= 0 ; i--)
					  obj.options[i] = null;
				  $("#menuType").append(optionstring);
			  }else if (pid=='06dc37fa15024d93bb3e9641a3f733da'){
				  obj = document.getElementById("menuType"); ///drp是select控件的ID值
				  for(i=obj.options.length-1 ; i>= 0 ; i--)
					  obj.options[i] = null;
				  $("#menuType").append(optionstring2);
			  }else if(pid!=0&&pid!='06dc37fa15024d93bb3e9641a3f733da')
			{
				obj = document.getElementById("menuType"); ///drp是select控件的ID值
				for(i=obj.options.length-1 ; i>= 0 ; i--)
					obj.options[i] = null;
				$("#menuType").append(optionstring3);
			}
			form.render('select');
		});
	});
})