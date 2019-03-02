var providerObj;

// 供应商管理页面上点击删除按钮弹出删除框(providerlist.jsp)
function deleteProvider(obj) {
	$.ajax({
		type : "GET",
		url : path + "/provider/del/" + obj.attr("proid"),
		dataType : "json",
		success : function(data) {
			if (data.delResult == "true") {// 删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			} else if (data.delResult == "false") {// 删除失败
				// alert("对不起，删除供应商【"+obj.attr("proname")+"】失败");
				changeDLGContent("对不起，删除供应商【" + obj.attr("proname") + "】失败");
			} else if (data.delResult == "notexist") {
				// alert("对不起，供应商【"+obj.attr("proname")+"】不存在");
				changeDLGContent("对不起，供应商【" + obj.attr("proname") + "】不存在");
			} else {
				// alert("对不起，该供应商【"+obj.attr("proname")+"】下有【"+data.delResult+"】条订单，不能删除");
				changeDLGContent("对不起，该供应商【" + obj.attr("proname") + "】下有【"
						+ data.delResult + "】条订单，不能删除");
			}
		},
		error : function(data) {
			// alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});

}

function openYesOrNoDLG() {
	$('.zhezhao').css('display', 'block');
	$('#removeProv').fadeIn();
}

function cancleBtn() {
	$('.zhezhao').css('display', 'none');
	$('#removeProv').fadeOut();
}
function changeDLGContent(contentStr) {
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}
$(function() {

	// 使用ajax显示查看窗口
	// $(".viewProvider").on("click",function(){
	// //将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
	// var obj = $(this);
	// // 通过ajax的方式查询信息进行注入,使用restful风格
	// $.ajax({
	// type:"GET",
	// url:path+"/provider/view/"+ obj.attr("proid"),
	// dataType:"json",
	// success:function(data){
	// $("#Procode").val(data.proCode);
	// $("#Proname").val(data.proName);
	// $("#creattime").val(data.creationDate);
	// $("#linkman").val(data.proContact);
	// $("#phone").val(data.proPhone);
	// $("#address").val(data.proAddress);
	// $("#proViewDiv").show();
	// },
	// error:function(data){
	// alert("对不起,查询失败!");
	// }
	// });
	// });

	// 使用原生jsp显示查看窗口
	$(".viewProvider").on("click", function() {
		// 将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		window.location.href = path + "/provider/view/" + obj.attr("proid");
	});

	// 修改按钮
	$(".modifyProvider").on("click", function() {
		var obj = $(this);
		window.location.href = path + "/provider/modify/" + obj.attr("proid");
	});

	// 删除选项
	$('#no').click(function() {
		cancleBtn();
	});

	// 确定选项
	$('#yes').click(function() {
		deleteProvider(providerObj);
	});

	// 显示删除界面
	$(".deleteProvider").on("click", function() {
		providerObj = $(this);
		changeDLGContent("你确定要删除供应商【" + providerObj.attr("proname") + "】吗？");
		openYesOrNoDLG();
	});

});