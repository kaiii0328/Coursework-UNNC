<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$username = $_COOKIE['username'];
	if ($username){
		echo'<script type="text/javascript">alert(" welcome back, '.$username.'");</script>';
	}
	else{
		echo"<script type='text/javascript'>alert('PLease login first');window.top.location.href='login.html'; </script>";
	} 
	$sql= "SELECT * FROM Message WHERE Receiver='$username'";
	$result=$link->query($sql);
	if ($result->num_rows>0){
		$row=$result->fetch_assoc();
		$sender=$row['Sender'];
		$receiver=$row['Receiver'];
		if ($receiver===$username){
			echo"<script type='text/javascript'>alert(\"Anomaly Purchase is deleted by sales representative.\");</script>";
			$delete="DELETE FROM Message WHERE Receiver='$receiver'";
			mysqli_query($link,$delete);
		}
	}
	
	



?>
