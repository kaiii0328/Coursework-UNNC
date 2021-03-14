<!DOCTYPE html>

<html lang="en" >
<head>
    <meta charset="utf-8" />
    <title>My purchase</title>
    <link rel="stylesheet" type="text/css" href="css/customerpage.css"/>
    <script type="text/javascript" src="js/customerpage.js"></script>

</head>
<body>
    <?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/customer_checkcookie.php"); ?>
    <header>
        <ul class="top">
            <li><a href="index.html">Home</a></li>
            <li>
                <a href="#">About</a>
                <ul class="top">
                    <li><a href="https://kaiii0328.github.io/">Author</a></li>
                </ul>
            </li>
            <li class="home">
                <a href="customerpage.php">Purchase</a>
            </li>
            <li>
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
        <div class="maskpic" id="maskpic" style="background:url('img/n95respirator3.png') no-repeat;background-position: 50% 20%;background-color:rgba(206, 200, 200, 0.39);box-shadow: 0 5px 15px rgba(0,0,0,.5);"></div>
        <div class="maskform">
            <h1>Select Types and Amount.</h1> 
	    <div class="mask">
            <form class="masks" action="purchase.php" method="post">
                <ul>
                    <li>
                        <label>Mask Type: </label>
                        <select name= "type">
                            <option onclick="changepic1();"> N95 respirators :$5 each </option>
                            <option onclick="changepic2();"> surgical masks :$8 each </option>
                            <option onclick="changepic3();"> surgical N95 respirators :$10 each</option>
                    </select>
                    </li>
                    <li>
                        <label> Quantity: </label>
                        <input type="button" value="-" onclick="sub();"/>
                        <input  type="text" id="amount" name="quantity" value="0" style="width:80px;" />
                        <input  type="button" value="+" onclick="plus();"/>
                    </li>
	   	    <li>
			<label> Reprensentative:</label>
			<?php include($_SERVER["CONTEXT_DOCUMENT_ROOT"]."/getreps.php");?>
		    </li>
                    <li>
                        <input class="buy"  type="submit" value="Buy"/>
                    </li>
                </ul>

            </form>
	    </div>
        </div>
    </div>


</body>
</html>
