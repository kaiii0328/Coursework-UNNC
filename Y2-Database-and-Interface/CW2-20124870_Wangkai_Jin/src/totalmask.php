<?php
	header('Content-Type: application/json');

	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}

	$sqlQuery = "SELECT SUM(Quantity) AS Total FROM OrderingInfo WHERE Status!='Anomaly'";

	$result = mysqli_query($link,$sqlQuery);

	$row=$result->fetch_assoc();
	$total1=$row['Total'];
	
	$sqlQuery = "SELECT SUM(Quantity) AS Total FROM OrderingInfo WHERE Status='Anomaly'";

	$result = mysqli_query($link,$sqlQuery);
	$row=$result->fetch_assoc();
	$total2=$row['Total'];
	
	mysqli_close($link);

	echo json_encode(array("Total1"=>$total1,"Total2"=>$total2));
?>
