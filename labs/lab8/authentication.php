<?php
	session_start();	
	$mysqli = new mysqli('localhost', 'SIAD_lab7', 'secretpass', 'SIAD_lab7');
	if($mysqli->connect_error){
		die('Connection to the database has error: ' . $mysqli->connect_error);
	}

	function checkLogin($username, $password) {
		$sql = "SELECT * FROM users WHERE username = '$username' AND password = password('$password'); ";
		//debug
		echo "sql = $sql";
		global $mysqli;
		$result = $mysqli->query($sql);
		if($result->num_row == 1){
			return TRUE;
		}
		return FALSE;
	}

	echo "Authentication<br>\n";
	//store a login session in $_SESSION["logged"]
		$username = $_REQUEST["username"];
		$password = $_REQUEST["password"];
	if(isset($username) and isset($password)) {		
		if(checkLogin($username, $password)){
			echo "Valid username and password <br>";
			$_SESSION["logged"] = TRUE;
		} else {
			header("refresh:1; url = login.php");
			echo "Incorrect username or password <br>";
			session_destroy();	
		}
	}

	if(!isset($_SESSION["logged"])) {
		header("refresh:1; url = login.php");
		echo "You have not logged in <br>";
		session_destroy();
	}
?>
