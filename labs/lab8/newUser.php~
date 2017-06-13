<?php
	require "authentication.php";
	
	function addUser($username, $password) {
		$sql = "INSERT INTO users VALUE('$username',password('$password'));";
		//debug
		echo "sql = $sql";
		global $mysqli;
		$result = $mysqli->query($sql);
		if($result = TRUE){
			echo "New username/password is added";
		} else {
			echo "addUser failed" . $mysqli->error;
		}
	}

	$username = $_REQUEST["Newusername"];
	$password = $_REQUEST["Newpassword"];
	echo "<debugin'> Newusername = $username; Newpassword = $password<br>";
	if(isset($username) and isset($password)) {
		addUser($username, $password);
	}
?>
