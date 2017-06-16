<?php
	//newPassword.php

	require "authentication.php";
	function changePassword($username, $password) {
		$sql = "UPDATE users SET password = password('$password')
			WHERE username = '$username';";
		//debug
		global $mysqli;
		$result = $mysqli->query($sql);
		if($result === TRUE){
			echo "$username 's password changed successfully<br>";
		} else {
			echo "Cannot change password for '$username':" . $mysqli->error;
		}
	
	}

	$username = $_SESSION["username"];
	$password = $_REQUEST["newPassword"];
	$currentEntered = $_REQUEST["currentPassword"];
	if(!checkLogin($username,$currentEntered)){
		echo "Current passwords do not match.";
		header("refresh:1; url = changepassform.php");
		die();
	}
	if(isset($username) and isset($password)) {
		changePassword($username, $password);
		echo"Redirecting...";
		header("refresh:3; url = index.php");
	}
?>
