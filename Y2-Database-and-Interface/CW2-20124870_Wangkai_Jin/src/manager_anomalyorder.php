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
	
	$sql = "SELECT * FROM OrderingInfo WHERE Status='Anomaly'";
	$result = $link->query($sql);

	$results_per_pages =7;
	$number_of_results = mysqli_num_rows($result);
	$number_of_pages = ceil($number_of_results/$results_per_pages);
	$this_page_first_result = ($page-1)*$results_per_pages;
	$sql = "SELECT * FROM OrderingInfo WHERE Status='Anomaly' LIMIT $this_page_first_result,$results_per_pages";
	$result = $link->query($sql);	
	if ($result->num_rows >0){
		while($row = $result-> fetch_assoc()) {
			//echo "<form action='rep_delete.php' method='post'>";
			echo "<tr><td>"."<input type='checkbox' name='keytodelete'  value='$row[OrderID]'/>"."</td><td>".$row['OrderID']."</td><td>".$row['Masktype']."</td><td>".$row['Quantity']."</td><td>".$row['Amount']."</td><td class='time'>".$row['Time']."</td><td>".$row['RepName']."</td><td>".$row['CusName']."</td><td>".$row['Status']."</td><tr>";
			//echo "</form>";
			
		}
		echo "</table>";
	}

	echo '<div class="page">';
	for($page=1;$page<=$number_of_pages;$page++){
		echo '<a href="managerpage.php?page='.$page.'"><input type="button" class="pagebutton" value='.$page.'></a>';
	}
	echo '</div>';
?>
