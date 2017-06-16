<html>
	<h1>Login</h1>

<?php
	echo "Current time: " . date("Y-m-d h:i:sa");
?>
	<form action="index.php" method="POST" class="form login">
		<p>Username: <input type="text" required pattern ="\w+" name="username"></p>
		<p>Password: <input type="password" required pattern ="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="password"	
	 		onchange="form.pwd2.paOern=this.value;"></p>	
		<button class="button" type="submit">
			Login
		</button>
	</form>
</html>
