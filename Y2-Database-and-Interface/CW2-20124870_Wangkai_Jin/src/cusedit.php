<?php

	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$user = $_POST['username'];
	$password = $_POST["password"];
	$region = $_POST["region"];
	$passport = $_POST["passport"];
	$telephone = $_POST["telephone"];
	$sql = "UPDATE Customer SET Password='$password',Region='$region',PassportID='$passport',Telephone='$telephone' WHERE UserName='$user'";
	$link->query($sql);
	header('Location:cusinfoedi.php');

?>
