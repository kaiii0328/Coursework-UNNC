<?php
	session_start();
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$role = $_POST["role"];
	$username = $_POST["username"];
	$password = $_POST["password"];

	if($username == "")
	{
	 echo"<script type='text/javascript'>alert('PLease enter usename');location='login.html'; </script>";
	}

	if($password == "")
	{
	 echo"<script type='text/javascript'>alert('please enter password');location='login.html';</script>";
	}

	if ($role == "Customer")
		$sql = "SELECT UserName,Password FROM Customer WHERE UserName = '$username' AND Password = '$password'";
	else if ($role == "Sales Representative"){
		$sql = "SELECT UserName,Password FROM SalesRepresentative WHERE UserName = '$username' AND Password = '$password'";}
	else{
		$sql = "SELECT UserName,Password FROM Manager WHERE UserName = '$username' AND Password = '$password'";
	}
	$result = mysqli_query($link, $sql);
	$rows = mysqli_fetch_array($result);
	/*
	if($rows >0) {	
		if ($username == $rows["UserName"] && $password == $rows["Password"]) {	
		 echo "<script type='text/javascript'>alert('Successfully login');location='customerpage.html';</script>";
		 }else {
		 echo "<script type='text/javascript'>alert('Wrong username or password');location='login.html';</script>";
		}
	}*/

	if($rows >0) {
		if ($role == "Customer"){
			setcookie("username","$username", time()+3600);
			echo "<script type='text/javascript'>alert('Successfully login');location='customerpage.php';</script>";
		}else if ($role == "Sales Representative"){
			setcookie("repname","$username", time()+3600);
			echo "<script type='text/javascript'>alert('Successfully login');location='reppage.php';</script>";}
		else{
			setcookie("managername","$username", time()+3600);
			echo "<script type='text/javascript'>alert('Successfully login');location='managerpage.php';</script>";
	}
		
		
	}else {
		 echo "<script type='text/javascript'>alert('Wrong username or password');location='login.html';</script>";
	}
?>
