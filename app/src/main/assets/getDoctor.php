<?php

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

	$sql = "select * from doctor";
	$result = $conn->query($sql);

	$doctorInfo = [];
	
	if($result->num_rows > 0){
		//输出数据
		$i = 0;
		while($row = $result->fetch_assoc()){
			$doctorInfo[$i]['doctorName'] = $row["DoctorName"];
			$doctorInfo[$i]['doctorIntro'] = $row["DoctorIntro"];
			$doctorInfo[$i]['doctorOffice'] = ++$row["DoctorOffice"];
			$i++;
		}
	}else{
		echo "无结果";
	}

	//关闭数据库连接
	$conn->close();

	echo json_encode($doctorInfo);
?>