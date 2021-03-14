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
	$realname = $_POST["realname"];
	$emid = $_POST["emid"];
	$telephone = $_POST["telephone"];
	$email= $_POST["email"];
	$flag = 1;

	if($username == "")
	{
	 echo"<script type='text/javascript'>alert('PLease enter usename');window.top.location.href='repregister.html'; </script>";
	 $flag=0;
	}

	if($password == "")
	{
	 echo"<script type='text/javascript'>alert('please enter password');window.top.location.href='repregister.html';</script>";
	 $flag=0;	
	}

	if ($realname == ""){
	 echo"<script type='text/javascript'>alert('please enter realname');window.top.location.href='repregister.html';</script>";
	 $flag=0;
	}
	
	if ($emid == ""){
	 echo"<script type='text/javascript'>alert('please enter employee id');window.top.location.href='repregister.html';</script>";
	 $flag=0;
	}

	if ($telephone == ""){
	 echo"<script type='text/javascript'>alert('please enter telephone');window.top.location.href='repregister.html';</script>";
	 $flag=0;
	}

	if ($email == ""){
	 echo"<script type='text/javascript'>alert('please enter email');window.top.location.href='repregister.html';</script>";
	 $flag=0;
	}

	if ($flag==1){
		$sql = "INSERT INTO SalesRepresentative (UserName, Password, Realname, EmployeeID,Telephone, Email,Quota,Region,ManagerName) VALUES ('$username', '$password','$realname', '$emid','$telephone', '$email',null,null,'manager')";
		if ($link->query($sql)===true){
		echo "<script type='text/javascript'>alert('Register Successfully ');window.top.location.href='login.html';</script>";
		}else {
			echo $link->error;
			echo "<script type='text/javascript'>alert('Register failed. User name has been occupied');window.top.location.href='repregister.html';</script>";
		}	
	}	


?>
