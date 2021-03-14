<?php
    $servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
    }
    if($username == "")
	{
	 echo"<script type='text/javascript'>alert('PLease enter usename');location='login.html'; </script>";
	}

	if($password == "")
	{
	 echo"<script type='text/javascript'>alert('please enter password');location='login.html';</script>";
	}
	$username = $_POST["username"];
	$password = $_POST["password"];
    $sql = "SELECT UserName,Password FROM Admin WHERE UserName = '$username' AND Password = '$password'";
	$result = mysqli_query($link, $sql);
	$rows = mysqli_fetch_array($result);
    if ($rows>0){
        echo "<script type='text/javascript'>alert('Successfully login');location='adminpage.html';</script>";
    }
?>