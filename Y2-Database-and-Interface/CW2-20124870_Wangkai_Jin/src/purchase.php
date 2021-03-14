<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);
	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}


	$masktype =$_POST["type"];
	$quantity = $_POST["quantity"];
	$amount = 0;
	if ($masktype=="N95 respirators :$5 each"){
		$amount= 5*$quantity;
		$masktype="N95 respirators";
	}
	else if ($masktype=="surgical masks :$8 each"){
		$amount = 8*$quantity;
		$masktype="surgical masks";
	}else{
		$amount = 10*$quantity;
		$masktype="surgical N95 respirators";
	}
		

	$time = date("y-m-d h:i:s");
	$repname=$_POST['repname'];
	$repname=strtok($repname,',');
	$CusName = $_COOKIE['username'];
	$sql = "SELECT Quota FROM SalesRepresentative WHERE UserName='$repname'";
	$result = $link->query($sql);
	$quota=0;
	if ($result->num_rows>0){
		$row =$result->fetch_assoc();
		$quota = $row['Quota'];
	}
	if ($quantity<$quota){
			$sql="UPDATE SalesRepresentative SET `Quota`=($quota-$quantity) WHERE UserName='$repname'";
			mysqli_query($link,$sql);
			$sql="INSERT INTO OrderingInfo(OrderID, Masktype, Quantity, Amount, Time, RepName,CusName) VALUES('', '$masktype','$quantity','$amount','$time','$repname','$CusName')";
			if ($link->query($sql)==true){
					echo "<script type='text/javascript'>alert('Purchase Successfully ');window.top.location.href='customerorder.php';</script>";
			}
			else {
				echo "<script type='text/javascript'>alert('Purchase failed');window.top.location.href='customerpage.php
';</script>";
		}
	}
	else{
		$sql="INSERT INTO OrderingInfo(OrderID, Masktype, Quantity, Amount, Time, RepName,CusName,Status) VALUES('', '$masktype','$quantity','$amount','$time','$repname','$CusName','Anomaly')";
		mysqli_query($link,$sql);
		echo "<script type='text/javascript'>alert('Purchase quantity out of Quota.Please contact your sales representative!');window.top.location.href='customerorder.php';</script>";
	}

	
	
?>
