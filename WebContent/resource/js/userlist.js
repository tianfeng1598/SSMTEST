var userObj;

//用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj){
	$.ajax({
		type:"GET",
		url:path+"/user/del/"+ obj.attr("userid"),
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
				alert("删除用户【"+obj.attr("username")+"】成功!");
			}else if(data.delResult == "false"){//删除失败
				changeDLGContent("对不起，删除用户【"+obj.attr("username")+"】失败");
			}else if(data.delResult == "notexist"){
				changeDLGContent("对不起，用户【"+obj.attr("username")+"】不存在");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

// 用于显示删除页面
function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();   // 元素使用淡出效果进行显示
}

// 用于隐藏删除页面
function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();  // 元素使用淡出效果进行显示
}

// 用于显示删除页面上的信息
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");  
	p.html(contentStr);   // 更新删除页上显示信息
}

$(function(){
	//通过jquery的class选择器（数组）
	//对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	
	/*点击查看信息的请求*/
	$(".viewUser").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		// 通过ajax的方式查询信息进行注入,使用restful风格
		$.ajax({
			type:"GET",
			url:path+"/user/view/"+ obj.attr("userid"),
			dataType:"json",
			success:function(data){
				$("#userName").val(data.userName);
				$("#birthday").val(data.birthday);
				$("#phone").val(data.phone);
				$("#address").val(data.address);
				/*根据查询结果进行注入结果*/
				if(data.gender==1){
					$("#gender").val("男");
				}else{
					$("#gender").val("女");
				}
				if(data.userRole==1){
					$("#userRole").val("系统管理员");
				}else if(data.userRole==2){
					$("#userRole").val("经理");
				}else{
					$("#userRole").val("普通员工");
				}
				$("#userViewDiv").show();
			},
			error:function(data){
				alert("对不起,查询失败!");
			}
		});
	});
	
	// 点击修改时的请求
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/user/modify/"+ obj.attr("userid");
	});
	
	// 删除页上的按钮
	$('#no').click(function () {
		cancleBtn();
	});
	
	// 删除页上的按钮
	$('#yes').click(function () {
		deleteUser(userObj);
	});

	// 点击用户管理页面上的删除时的请求
	$(".deleteUser").on("click",function(){
		userObj = $(this);
		changeDLGContent("你确定要删除用户【"+userObj.attr("username")+"】吗？");
		openYesOrNoDLG();
	});
	
});