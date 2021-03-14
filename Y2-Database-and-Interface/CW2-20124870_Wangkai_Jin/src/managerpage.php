<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Manage the System</title>
    <link rel="stylesheet" type="text/css" href="css/managerpage.css"/>

</head>
<body>
    <?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/manager_checkcookie.php"); ?>
    <header>
        <ul class="top">
            <li class="current"><a href="managerpage.php">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li>
                <a href="#">HR Management</a>
                <ul>
                    <li><a href="repregister.html">New Sales Rep</a></li>
                    <li><a href="repoverview.php">Rep Overview</a></li>
                    <li><a href="clientoverview.php">Client Overview</a></li>
                </ul>
            </li>
            <li>
                <a href="">Mask Info</a>
                <ul>
                    <li><a href="maskoverview.php">Overview</a></li>
		    <li><a href="repkpi.php">Rep KPI</a></li>
                </ul>
            </li>
            <li>
                <a href="#">Account Info</a>
                <ul>
                    <li><a href="logout.php">Log out</a></li>
                </ul>
            </li>
        </ul>
    </header>
    
    <div class="main">
		<h2>Purchase with anomaly </h2>
		<table>
		<thead>
		<tr>
			<th>Select</th>
			<th>OrderID</th>
			<th>Masktype</th>
			<th>Quantity</th>
			<th>Amount</th>
			<th>Time</th>
			<th>Representative</th>
			<th>CustomerName</th>
			<th> Status </th>
		</tr>
		</thead>
	
	<tbody>
		<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/manager_anomalyorder.php"); ?>
	</tbody>
	</table>
    </div>


</body>
</html>
