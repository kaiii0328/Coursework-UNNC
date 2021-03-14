<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$rep=$_COOKIE['repname'];
	if(isset($_POST['delete'])){
		$key = $_POST['keytodelete'];
		echo $key;
		$sql = "SELECT * FROM OrderingInfo WHERE OrderID='$key'";		
		$check=$link->query($sql) or die("Not Found".mysqli_error());
		
		if(mysqli_num_rows($check)>0){
			$row= $check->fetch_assoc();
			$customer=$row['CusName'];
			$currenttime=date("y-m-d h:i:s");
			$pretime=$row['Time'];
			$gap = ceil((strtotime($currenttime)-strtotime($pretime))/3600);
			if ($gap<24){
				$queryDelete = $link->query("DELETE FROM OrderingInfo WHERE OrderID='$key'") or die("Not deleted".mysql_error());
				$sql="INSERT INTO Message(Sender,Receiver,Content) VALUES('$rep','$customer','Anomaly purchase is deleted')";
				mysqli_query($link,$sql);
			}else{
				echo"<script type='text/javascript'>alert('This order is completed.You can't delete it!');location='reppage.php';</script>";
			}
;		}
	}
	header("Location:reppage.php");

?>
