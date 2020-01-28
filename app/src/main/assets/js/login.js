
//账号框失去焦点时调用
function checkName(){ 
    var username = document.getElementById("username").value;
    if(username == ""){ 
      document.getElementById("usernameTip").style.display = ""; 
    }
    else{ 
     document.getElementById("usernameTip").style.display = "none";
    }
}

//密码框失去焦点时调用
function checkPassword(){ 
    var password = document.getElementById("password").value; 
    if(password == ""){ 
        document.getElementById("passwordTip").style.display = ""; 
    }
    else{ 
        document.getElementById("passwordTip").style.display = "none";
    }
}

//点击登录按钮进行的判断
function clickCheck(){
    var flag = true;
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
	
	//判空
    if(username == ""){
        flag = false;
        alert("请输入账号！");
    }

    if(password == ""){
        flag = false;
        alert("请输入密码！");
    }

	//数据库判断
	$.ajax({
		type: "POST",
        url: "loginValidate.php",
		data: "userName="+username+"&userPass="+password,
        success: function(msg){
			if(msg != null){
				var obj = JSON.parse(msg);
				if(obj.userPurview == 2){
                    $.cookie("doctorName", obj.userName);
					skipToDoctor();
				}else if(obj.userPurview == 3){
					skipToAdmin();
				}
			}else{
				alert("用户名或密码有误!");
			}
        }
    });
}

//登录成功后跳转到医生主页
function skipToDoctor(){
    window.location.href = '../doctorIndex.html';
}

//登录成功后跳转到管理员主页
function skipToAdmin(){
	window.location.href = '../adminIndex.html';
}