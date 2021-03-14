<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>My purchase</title>
    <link rel="stylesheet" type="text/css" href="css/customerorder.css"/>
</head>
<body>
    <!--<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/checkcookie.php"); ?>-->
    <header>
        <ul class="top">
            <li><a href="index.html">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li>
                <a href="customerpage.php">Purchase</a>
            </li>
            <li class="order">
                <a href="customerorder.php">My Order</a>
            </li>
            <li>
                <a href="cusinfoedi.php">Account Info</a>
                <ul>
                    <li><a href="logout.php">Log out</a></li>
                </ul>
            </li>
        </ul>
    </header>

    <div class="main">
        <table>
	<thead>
		<tr>
			<th>Select</th>
			<th>OrderID</th>
			<th>Masktype</th>
			<th>Quantity</th>
			<th>Amount</th>
			<th>Time</th>
			<th>Reprensentative</th>
			<th>CustomerName</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
	</thead>
	
	<tbody>
		<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/ordertable.php"); ?>
	</tbody>
	</table>
    </div>


</body>
</html>
