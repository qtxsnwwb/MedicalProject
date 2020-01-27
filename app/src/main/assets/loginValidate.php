<?php
	//接受client端传来的数据
	$loginName = $_POST["userName"];
	$loginPass = $_POST["userPass"];

	//数据库操作
	$serverName = "192.168.0.101";
	$userName = "root";
	$userPass = "mysqladmin";
	$dbname = "medical";

	//创建连接
	$conn = new mysqli($serverName, $userName, $userPass, $dbname);
	if($conn->connect_error){
		die("连接失败：" . $conn->connect_error);
	}

	$sql = "select * from user where UserName='".$loginName."' and UserPass='".$loginPass."'";
	$result = $conn->query($sql);

	$userInfo = [];
	
	if($result->num_rows > 0 && $result != null){
		//输出数据
		while($row = $result->fetch_assoc()){
			$userInfo["userID"] = $row["UserID"];
			$userInfo["userName"] = $row["UserName"];
			$userInfo["userPass"] = $row["UserPass"];
			$userInfo["userPurview"] = $row["UserPurview"];
			break;
		}
	}else{
		echo null;
	}

	//关闭数据库连接
	$conn->close();
	
	echo json_encode($userInfo);
?>