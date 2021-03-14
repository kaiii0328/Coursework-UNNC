﻿<?php
	$servername = "localhost";
	$username = "scywj1";
	$password = "JWKInUNNC2019-2020";
	$dbname = "scywj1";
	$link = mysqli_connect($servername, $username, $password, $dbname);

	if (!$link) {
	 die("Connection failed:".mysqli_connect_error());
	}
	$user=$_COOKIE['username'];
	$sql ="SELECT * FROM Customer WHERE UserName='$user'";
	$result = $link->query($sql);
	$row=$result->fetch_assoc();
?>

<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>My purchase</title>
    <link rel="stylesheet" type="text/css" href="css/cusinfoedi.css"/>

</head>
<body>
    <header>
        <ul class="top">
            <li ><a href="index.html">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li>
                <a href="customerpage.php">Purchase</a>
            </li>
            <li>
                <a href="customerorder.php">My Order</a>
            </li>
            <li class="home">
                <a href="cusinfoedi.php">Account Info</a>
                <ul>
                    <li><a href="logout.php">Log out</a></li>
                </ul>
            </li>
        </ul>
    </header>
    
    <div class="main">
        <h2>Personal File</h2>
    	<form class="assign" action="cusedit.php" method="post">
                <ul>
                    <li>
                        <label>User Name:</label>
                        <?php echo '&nbsp&nbsp&nbsp<input type="text" name="username" value="'.$row['UserName'].'" onfocus="this.blur()"/>';?>
			<label class="right">Password: </label>
			<?php echo '<input type="text" name="password"value="'.$row['Password'].'"/>';?>
	   	    <li>
			<label>Realname:</label>
			<?php echo '&nbsp&nbsp&nbsp&nbsp<input type="text" name="realname" value="'.$row['Realname'].'" onfocus="this.blur()"/>';?>
			<label class="right">Telephone: </label>
			<?php echo '<input type="text" name="telephone" value="'.$row['Telephone'].'"/>';?>
		    </li>
		    <li>
			<label>Passport ID:  </label>
			<?php echo '<input type="text" name="emid" value="'.$row['PassportID'].'" onfocus="this.blur()"/>' ;?>
			<label class="right">Email:  </label>
			<?php echo '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="text" name="email" value="'.$row['Email'].'"/>';?>
		    </li>
		    <li>
			<label>Region:  </label>
			<?php echo '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="text" name="region" value="'.$row['Region'].'"/>' ;?>
		    </li>
		    <li>
			<input type="submit" value="Edit"/>
		    </li>
                </ul>

            </form>
    </div>


</body>
</html>
