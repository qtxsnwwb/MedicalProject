<?php

	function uuid(){
		$chars = md5(uniqid(mt_rand(), true));
		$uuid = substr ( $chars, 0, 8 )
            . substr ( $chars, 8, 4 )
            . substr ( $chars, 12, 4 )
            . substr ( $chars, 16, 4 )
            . substr ( $chars, 20, 12 );  
		return $uuid ;  
	}

	//获取client传输的表单数据
	$doctorOffice = $_POST["doctorOffice"];
	$doctorName = $_POST["doctorName"];
	$doctorIntro = $_POST["doctorIntro"];
	
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

	$sql = "INSERT INTO doctor (DoctorID, DoctorName, DoctorIntro, DoctorOffice) VALUES ('".uuid()."','".$doctorName."','".$doctorIntro."','".$doctorOffice."')";
	$flag = 0;
	
	if($conn->query($sql) == TRUE){
		$flag = 1;
	}

	//关闭数据库连接
	$conn->close();

	echo $flag;
?>