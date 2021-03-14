<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$username = $_POST["username"];
	$password = $_POST["password"];
	$telephone = $_POST["telephone"];
	$email= $_POST["email"];
	$sql="UPDATE SalesRepresentative SET Password='$password',Telephone='$telephone',Email='$email' WHERE UserName='$username'";
	$link->query($sql);
	header('Location:repaccountinfo.php');
	
?>
