<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$repname = $_POST['repname'];
	$region = $_POST['Region'];
	$quota = $_POST['Quota'];
	$sql="UPDATE SalesRepresentative SET Quota=$quota,Region='$region' WHERE UserName='$repname'";
	mysqli_query($link,$sql);
	$message="Please confirm your quota and region";
	$sql="INSERT INTO Message(Sender,Receiver,Content) VALUES('manager','$repname','$message')";
	mysqli_query($link,$sql);
	$url='repoverview.php';
	header('Location:'.$url);
?>
