<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$user = $_COOKIE['username'];
	$sql = "SELECT SalesRepresentative.UserName,SalesRepresentative.Quota FROM SalesRepresentative,Customer WHERE (SalesRepresentative.Region=Customer.Region AND Customer.UserName='$user')";
	$result = $link->query($sql);
	if($result->num_rows>0){
		echo "<select class='repname' name='repname'>";
		while($row=$result->fetch_assoc()){
			echo '<option>'.$row['UserName'].', Quota: '.$row['Quota'].'</option>';
		}
		echo "</select>";
	}
	else{
		echo '<a href="cusgrant.php"><input class="grant" type="button" value="Ask for Reps"/></a>';
	}
?>
