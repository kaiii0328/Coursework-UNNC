<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$sql = "SELECT UserName FROM SalesRepresentative";
	$result = $link->query($sql);
	if($result->num_rows>0){
		echo "<select class='repname' name='repname'>";
		while($row=$result->fetch_assoc()){
			echo '<option>'.$row['UserName'].'</option>';
		}
		echo "</select>";
	}
?>
