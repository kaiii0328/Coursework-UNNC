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

	$sqlQuery = "SELECT SUM(Quantity) AS Total,Masktype FROM OrderingInfo GROUP BY Masktype ORDER BY Total";

	$result = mysqli_query($link,$sqlQuery);

	$data = array();
	foreach ($result as $row) {
		$data[] = $row;
	}

	mysqli_close($link);

	echo json_encode($data);
?>