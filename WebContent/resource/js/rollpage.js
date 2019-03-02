function page_nav(frm,num){
	var name = $(frm).find("input[name='queryname']").val();
	var role = $(frm).find("option:selected").val();
	$.ajax({
		 url : "user/find",
         type : "post",
         async: false , // 用于解除ajax的异步实现同步.
         data : {"name" : name,"role":role,"num":num},
         dataType:"json",
         success : function(result) {
        	 alert("success!")
         },error:function(result){
        	 location.reload();
         }
         
	});
}

function jump_to(frm,num){
    //alert(num);
	//验证用户的输入
	var regexp=/^[1-9]\d*$/;
	var totalPageCount = document.getElementById("totalPageCount").value;
	//alert(totalPageCount);
	if(!regexp.test(num)){
		alert("请输入大于0的正整数！");
		return false;
	}else if((num-totalPageCount) > 0){
		alert("请输入小于总页数的页码");
		return false;
	}else{
		page_nav(frm,num);
	}
}

