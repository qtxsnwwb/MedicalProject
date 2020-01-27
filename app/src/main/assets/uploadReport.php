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

	//接受client端传来的数据
	$patientID = $_POST["patientID"];
	$patientName = $_POST["patientName"];
	$patientSex = $_POST["patientSex"];
	$patientAge = $_POST["patientAge"];
	$office = $_POST["office"];
	$doctorMain = $_POST["doctorMain"];
	$diagnose = $_POST["diagnose"];
	$medicine = $_POST["medicine"];

	//数据库操作
	$serverName = "192.168.43.31";
	$userName = "root";
	$userPass = "mysqladmin";
	$dbname = "medical";

	//创建连接
	$conn = new mysqli($serverName, $userName, $userPass, $dbname);
	if($conn->connect_error){
		die("连接失败：" . $conn->connect_error);
	}

	$sql = "INSERT INTO report (ReportID, PatientID, PatientName, PatientSex, PatientAge, Office, Doctor, Result, Medicine) VALUES ('".uuid()."','".$patientID."','".$patientName."','".$patientSex."','".$patientAge."','".$office."','".$doctorMain."','".$diagnose."','".$medicine."')";
	$flag = 0;
	
	if($conn->query($sql) == TRUE){
		$flag = 1;
	}

	//关闭数据库连接
	$conn->close();

	echo $flag;
?>