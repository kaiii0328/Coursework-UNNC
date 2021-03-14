<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}

	if(isset($_POST['delete'])){
		$key = $_POST['keytodelete'];
		$sql = "SELECT * FROM OrderingInfo WHERE OrderID='$key'";		
		$check=$link->query($sql) or die("Not Found".mysqli_error());
		$row = $check->fetch_assoc();
		$currenttime=date("y-m-d h:i:s");
		$pretime=$row['Time'];
		$gap = ceil((strtotime($currenttime)-strtotime($pretime))/3600);
		if(mysqli_num_rows($check)>0 && $gap<24){
			$queryDelete = $link->query("DELETE FROM OrderingInfo WHERE OrderID='$key'") or die("Not deleted".mysql_error())
;		}else{
			echo "<script type='text/javascript'>alert('This order is completed.You can't cancel it!');location='customerorder.php';</script>";
		}

	}
	header("Location:customerorder.php");

?>
