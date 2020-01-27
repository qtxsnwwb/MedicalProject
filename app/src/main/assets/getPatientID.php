<?php
	//接受client端传来的数据
	$patientName = $_POST["patientName"];

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

	$sql = "select * from user where UserName='".$patientName."'";
	$result = $conn->query($sql);

	$userID = "";
	
	if($result->num_rows > 0){
		//输出数据
		while($row = $result->fetch_assoc()){
			$userID = $row["UserID"];
			break;
		}
	}else{
		echo "";
	}

	//关闭数据库连接
	$conn->close();
	
	echo $userID;
?>