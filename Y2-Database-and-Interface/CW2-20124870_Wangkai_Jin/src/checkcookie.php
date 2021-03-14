<?php
	$username = $_COOKIE['username'];
	echo $username; 
	if ($username){
		echo"<script type='text/javascript'>alert(\" welcome back\");</script>";
	}
	else{
		echo"<script type='text/javascript'>alert('PLease login first');window.top.location.href='login.html'; </script>";
	} 
	



?>
