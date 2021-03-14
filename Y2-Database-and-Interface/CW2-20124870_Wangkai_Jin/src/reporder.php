﻿<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>Enjoy Working</title>
    <link rel="stylesheet" type="text/css" href="css/reporder.css"/>
</head>
<body>
    <header>
        <ul class="top">
            <li ><a href="reppage.php">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li class="current">
                <a href="reporder.php">Total Order</a>
            </li>
            <li>
                <a href="repaccountinfo.php">Account Info</a>
                <ul>
                    <li><a href="login.html">Log out</a></li>
                </ul>
            </li>
        </ul>
    </header>
    <div class="main">
	<h2>Total Order</h2>
	<table>
	<thead>
		<tr>
			<th>Select</th>
			<th>OrderID</th>
			<th>Masktype</th>
			<th>Quantity</th>
			<th>Amount</th>
			<th>Time</th>
			<th>CustomerName</th>
			<th>Status</th>
			<th>Action</th>
		</tr>
	</thead>
	
	<tbody>
		<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/rep_totalorder.php"); ?>
	</tbody>
	</table>
    </div>
</body>
</html>
