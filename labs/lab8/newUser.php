<?php
	require "authentication.php";
	
	function addUser($username, $password) {
		$sql = "INSERT INTO users VALUE('$username',password('$password'));";
		//debug
		global $mysqli;
		$result = $mysqli->query($sql);
		if($result === TRUE){
			echo "$username added successfully";
		} else {
			echo "Cannot add user '$username':" . $mysqli->error;
		}
	}

	$username = $_REQUEST["Newusername"];
	$password = $_REQUEST["Newpassword"];
	echo "<debugin'> Username = $username; Password = $password<br>";
	if(isset($username) and isset($password)) {
		addUser($username, $password);
	}
?>
