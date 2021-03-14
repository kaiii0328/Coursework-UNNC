<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	
	$sender=$_COOKIE['username'];
	$receiver = 'manager';
	$sql = "SELECT Region FROM Customer WHERE UserName='$sender'";
	$result=$link->query($sql);
	$row= $result->fetch_assoc();
	$message = 'Ask for Sales Representative from cusotmer '.$sender.' in Region '.$row['Region'];
	$sql  ="INSERT INTO Message(Sender,Receiver,Content) VALUES('$sender','$receiver','$message')";
	$link->query($sql);
	echo "<script type='text/javascript'>alert('Your request is successfully sent to manager!');window.top.location.href='customerpage.php'; </script>";


?>
