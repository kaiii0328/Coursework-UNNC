<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$user = $_COOKIE['repname'];
	$sql = "SELECT Quota,Region FROM SalesRepresentative WHERE UserName='$user'";
	$result = $link->query($sql);
	if($result->num_rows>0){
		$row=$result->fetch_assoc();
		$quota = $row['Quota'];
		$region=$row['Region'];
		echo '<form action="regrant.php" method="post">';
		echo 'Current Quota: ';
		echo '<input type="text" value="'.$quota.'" onfocus="this.blur();"/>';
		echo '<input type="submit" class="grant" value="Ask for Regrant">';
		echo ' &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp&nbsp&nbspCuurnt Region: ';
		echo '<input type="text" value="'.$region.'" onfocus="this.blur();"/>';
		echo '</form>';
	}
?>
