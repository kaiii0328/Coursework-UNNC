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

	if(!isset($_GET['page'])){
		$page=1;
	}
	else{
		$page=$_GET['page'];	
	}
	
	$sql = "SELECT * FROM OrderingInfo WHERE CusName = '$user'";
	$result = $link->query($sql);
	$currenttime=date("y-m-d h:i:s");
	if($result->num_rows>0){
		while($row = $result-> fetch_assoc()) {
			$pretime=$row['Time'];
			$status=$row['Status'];
			$gap = ceil((strtotime($currenttime)-strtotime($pretime))/3600);
			if ($status!=='Anomaly'){
			if ($gap>24){
				$update = "UPDATE OrderingInfo SET Status='Complete' WHERE (CusName='$user' AND Time='$pretime')";
				mysqli_query($link,$update);
			}else{
				$update = "UPDATE OrderingInfo SET Status='Processing' WHERE (CusName='$user' AND Time='$pretime')";
				mysqli_query($link,$update);
			}
			}
		}
	}
	$results_per_pages =6;
	$number_of_results = mysqli_num_rows($result);
	$number_of_pages = ceil($number_of_results/$results_per_pages);
	$this_page_first_result = ($page-1)*$results_per_pages;
	$sql = "SELECT * FROM OrderingInfo WHERE CusName = '$user' LIMIT $this_page_first_result,$results_per_pages";
	$result = $link->query($sql);	
	if ($result->num_rows >0){
		while($row = $result-> fetch_assoc()) {
			echo "<form action='delete.php' method='post'>";
			echo "<tr><td>"."<input type='checkbox' name='keytodelete'  value='$row[OrderID]'/>"."</td><td>".$row['OrderID']."</td><td>".$row['Masktype']."</td><td>".$row['Quantity']."</td><td>".$row['Amount']."</td><td class='time'>".$row['Time']."</td><td>".$row['RepName']."</td><td>".$row['CusName']."</td><td>".$row['Status']."</td><td>"."<input type='submit' class='delete' name='delete' value='Cancel'/>"."</td><tr>";
			echo "</form>";
			
		}
		echo "</table>";
	}

	echo '<div class="page">';
	for($page=1;$page<=$number_of_pages;$page++){
		echo '<a href="customerorder.php?page='.$page.'"><input type="button" class="pagebutton" value='.$page.'></a>';
	}
	echo '</div>';
?>
