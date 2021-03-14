<?php
	
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}

	if(!isset($_GET['page'])){
		$page=1;
	}
	else{
		$page=$_GET['page'];	
	}
	$sql = "SELECT UserName,RealName,EmployeeID,Telephone,Quota,Region FROM SalesRepresentative";
	$result = $link->query($sql);
	$results_per_pages =8;
	$number_of_results = mysqli_num_rows($result);
	$number_of_pages = ceil($number_of_results/$results_per_pages);
	
	$this_page_first_result = ($page-1)*$results_per_pages;
	$sql = "SELECT UserName,RealName,EmployeeID,Telephone,Quota,Region FROM SalesRepresentative LIMIT $this_page_first_result,$results_per_pages";
	$result = $link->query($sql);	
	if ($result->num_rows >0){
		while($row = $result-> fetch_assoc()) {
			echo "<tr><td>".$row['UserName']."</td><td>".$row['RealName']."</td><td>".$row['EmployeeID']."</td><td>".$row['Telephone']."</td><td>".$row['Quota']."</td><td>".$row['Region']."</td><tr>";
		}
		echo "</table>";
	}
	
	echo '<div class="page">';
	for($page=1;$page<=$number_of_pages;$page++){
		echo '<a href="repoverview.php?page='.$page.'"><input type="button" class="pagebutton" value='.$page.'></a>';
	}
	echo '</div>';



?>
