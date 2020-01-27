<?php
	//接受client端传来的数据
	$doctorName = $_POST["doctorName"];
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

	$sql = "delete from wait where PatientID='".$patientName."' and DoctorName='".$doctorName."'";
	$result = $conn->query($sql);

	$result = "";

	if($conn->query($sql)){
		$result = "success";
	}else{
		$result = "error";
	}

	//关闭数据库连接
	$conn->close();

	echo $result;
?>