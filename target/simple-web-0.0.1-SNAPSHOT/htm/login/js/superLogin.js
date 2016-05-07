$(function(){
	$("#btn_login").bind("click",login);
})
//登录
function login(){
	var userName = $("#userName").val();
	var userPassword = $("#userPassword").val();
	if(!$.trim(userName)){
		alert("用户名为空");
		return false;
	}
	if(!$.trim(userPassword)){
		alert("密码为空");
		return false;
	}
	$.ajax({
		type : "post",
		url : rootPath() + "/free/adminLogin",
		dataType : "json",
		data : {"userName":userName,"userPassword":userPassword},
		success : function(data) {
			if(data.status=="0000"){
				alert(1);
				window.location.href = rootPath() + "/superWeb/index.html";
			}else{
				alert("登录失败");
			}
		},
		error : function() {
			alert("网络连接失败，请稍候再试！");
		}
	});
}