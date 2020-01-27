<?php
	//接受client端传来的数据
	$doctorName = $_POST["doctorName"];

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

	$sql = "select * from wait where DoctorName='".$doctorName."'";
	$result = $conn->query($sql);

	$teamInfo = [];
	
	if($result->num_rows > 0){
		//输出数据
		while($row = $result->fetch_assoc()){
			$teamInfo["patientID"] = $row["PatientID"];
			break;
		}
		$teamInfo["teamNum"] = $result->num_rows;
	}else{
		$teamInfo["patientID"] = "无";
		$teamInfo["teamNum"] = 0;
	}

	//关闭数据库连接
	$conn->close();
	
	echo json_encode($teamInfo);
?>