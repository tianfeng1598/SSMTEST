<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="./common/head.jsp"%>

<div class="right">
	<div class="location">
		<strong>你现在所在的位置是:</strong> <span>供应商管理页面</span>
	</div>
	<div class="search">
		<form method="get"
			action="${pageContext.request.contextPath }/provider/provider.html">
			<input name="method" value="query" type="hidden"> <span>供应商编码：</span>
			<input name="queryProCode" type="text" value="${queryProCode }">

			<span>供应商名称：</span> <input name="queryProName" type="text"
				value="${queryProName }"> <input value="查 询" type="submit"
				id="searchbutton"> <a
				href="${pageContext.request.contextPath }/provider/provideradd">添加供应商</a>
		</form>
	</div>
	<!--供应商操作表格-->
	<table class="providerTable" cellpadding="0" cellspacing="0">
		<tr class="firstTr">
			<th width="10%">供应商编码</th>
			<th width="20%">供应商名称</th>
			<th width="10%">联系人</th>
			<th width="10%">联系电话</th>
			<th width="10%">传真</th>
			<th width="10%">创建时间</th>
			<th width="30%">操作</th>
		</tr>
		<c:set var="providerList" value="${pagebean.pagelist}" />
		<c:forEach var="provider" items="${providerList }" varStatus="status">
			<tr>
				<td><span>${provider.proCode }</span></td>
				<td><span>${provider.proName }</span></td>
				<td><span>${provider.proContact}</span></td>
				<td><span>${provider.proPhone}</span></td>
				<td><span>${provider.proFax}</span></td>
				<td><span> <fmt:formatDate
							value="${provider.creationDate}" pattern="yyyy-MM-dd" />
				</span></td>
				<td><span><a class="viewProvider" href="javascript:;" proid=${provider.id } proname=${provider.proName }>
							<img src="${pageContext.request.contextPath }/resource/images/read.png" alt="查看" title="查看" /></a></span> 
					<span><a class="modifyProvider"
						    href="javascript:;" proid=${provider.id } proname=${provider.proName }><img
							src="${pageContext.request.contextPath }/resource/images/xiugai.png"
							alt="修改" title="修改" /></a></span> <span><a class="deleteProvider"
						href="javascript:;" proid=${provider.id }
						proname=${provider.proName }><img
							src="${pageContext.request.contextPath }/resource/images/schu.png"
							alt="删除" title="删除" /></a></span>
							</td>
							
					
			</tr>
		</c:forEach>
	</table>

	<input type="hidden" id="totalPageCount" value="${pagebean.pagetotal}" />
	<div class="page-bar">
		<ul class="page-num-ul clearfix">
			<li>共${pagebean.infonum }条记录&nbsp;&nbsp; ${pagebean.pageno }/${pagebean.pagetotal }页</li>
			<c:if test="${pagebean.pageno > 1}">
				<a href="javascript:page_nav(document.forms[0],1);">首页</a>
				<a
					href="javascript:page_nav(document.forms[0],${pagebean.pageno-1});">上一页</a>
			</c:if>
			<c:if test="${pagebean.pageno < pagebean.pagetotal }">
				<a href="javascript:void(0);"
					onclick="page_nav(document.forms[0],${pagebean.pageno+1})">下一页</a>
				<a
					href="javascript:page_nav(document.forms[0],${pagebean.pagetotal });">最后一页</a>
			</c:if>
			<li>&nbsp;&nbsp;</li>
		</ul>
		<span class="page-go-form"><label>跳转至</label> <input
			type="text" name="inputPage" id="inputPage" class="page-key" />页
			<button type="button" class="page-btn"
				onClick='jump_to(document.forms[0],document.getElementById("inputPage").value)'>GO</button>
		</span>
	</div>
	<div class="providerAdd" id="proViewDiv" style="display: none">
	<div>
		<label for="Procode">供应商编码：</label> <input type="text" name="Procode"
			id="Procode"> <font color="red"></font>
	</div>
	<div>
		<label for="Proname">供应商名称：</label> <input type="text" name="Proname"
			id="Proname" value="">
	</div>
	<div>
		<label for="data">创建时间：</label> <input type="text" name="creattime"
			id="creattime" value="">
	</div>

	<div>
		<label for="linkman">联系人：</label> <input type="text" name="linkman"
			id="linkman" value=""> <font color="red"></font>
	</div>
	<div>
		<label for="phone">联系电话：</label> <input type="text" name="phone"
			id="phone" value="">
	</div>
	<div>
		<label for="address">传真：</label> <input type="text" name="address"
			id="address" value="">
	</div>
</div>


<br />

	</section>
	

	<!--点击删除按钮后弹出的页面-->
	<div class="zhezhao"></div>
	<div class="remove" id="removeProv">
		<div class="removerChid">
			<h2>提示</h2>
			<div class="removeMain">
				<p>你确定要删除该供应商吗？</p>
				<a href="#" id="yes">确定</a> <a href="#" id="no">取消</a>
			</div>
		</div>
	</div>

	<%@include file="./common/foot.jsp"%>
	<script type="text/javascript"
		src="${pageContext.request.contextPath }/resource/js/providerlist.js"></script>

	<script type="text/javascript">
function page_nav(frm,num){
	var code = $(frm).find("input[name='queryProCode']").val();
	var name = $(frm).find("input[name='queryProName']").val();
	$.ajax({
		 url : "provider/selectprovider.html",
         type : "post",
         //async: false , // 用于解除ajax的异步实现同步.
         data : {"name" : name,"code":code,"num":num},
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
</script>