<html>
	<h1>Change Password</h1>

<?php
	echo "Current time: " . date("Y-m-d h:i:sa");
?>
	<form action="changepass.php" method="POST" class="form login">
		Old Password: <input name="Oldpassword" size="20"/> <br>
		New Password: <input name="Newpassword" size="20"/> <br>
		<button class="button" type="submit">
			Confirm Change Password
		</button>
	</form>
</html>
