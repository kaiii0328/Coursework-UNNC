<?php
	setcookie('username','',time()-3600);
	$url = 'login.html';
	header('Location:'.$url);
?>
