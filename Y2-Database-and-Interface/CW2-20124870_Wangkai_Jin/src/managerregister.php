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
	$flag=1;
	if($username == "")
	{
	 echo"<script type='text/javascript'>alert('PLease enter usename');window.top.location.href='adminpage.html'; </script>";
	 $flag=0;
	}

	if($password == "")
	{
	 echo"<script type='text/javascript'>alert('please enter password');window.top.location.href='adminpage.html';</script>";
	 $flag=0;
	}
	
	if($flag==1){
		$sql = "INSERT INTO Manager(UserName,Password) VALUES ('$username','$password')";
		if ($link->query($sql)===true){
			echo "<script type='text/javascript'>alert('Register Successfully ');window.top.location.href='login.html';</script>";
		}else {
			echo "<script type='text/javascript'>alert('Register failed. User name has been occupied');window.top.location.href='adminpage.html';</script>";
		}	
	} 

?>
