<?php	
	$mysqli = new mysqli('localhost', 'SIAD_lab7', 'secretpass', 'SIAD_lab7');
	if($mysqli->connect_error){
		die('Connection to the database has error: ' . $mysqli->connect_error);
	}
	$sql = "SELECT * FROM users;";
	$result = $mysqli->query($sql);
	if($result->num_rows > 0){
		while($row = $result->fetch_assoc()) {
			echo "username: " . $row['username'] . ", password: " .$row['password'] . "<br>";
		}
	} else {
		echo "No users";
	}
?>
