<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$sender=$_COOKIE['repname'];
	$receiver='manager';
	$message='Ask for more quota from '.$sender;
	$sql="INSERT INTO Message(Sender,Receiver,Content) VALUES('$sender','$receiver','$message')";
	mysqli_query($link,$sql);
	header('Location:reppage.php');


?>
