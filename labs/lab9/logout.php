<?php
	session_start();
	session_destroy();
	echo "You have been logged out.<br>";
?>
<a href="login.php">Click here to login again</a>
