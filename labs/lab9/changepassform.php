<html>
	<h1>Change Password</h1>

<?php
	require "authentication.php";
	echo "Current time: " . date("Y-m-d h:i:sa");
?>
	<form action="newPassword.php" method="POST" class="form login">

		<p>Change password for: <?php echo $_SESSION["username"]; ?></p>
		
		<p>Old Password: <input type="password" required pattern ="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="currentPassword"	
	 		onchange="form.pwd2.paOern=this.value;"></p>

		<p>New Password: <input type="password" required pattern ="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="newPassword"	
	 		onchange="form.pwd2.paOern=this.value;"></p>

		<button class="button" type="submit">
			Confirm Password Change
		</button>
	</form>
</html>
