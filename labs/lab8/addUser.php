<html>
	<h1>Add User</h1>

<?php
	echo "Current time: " . date("Y-m-d h:i:sa");
?>
	<form action="newUser.php" method="POST" class="form login">
	
		<p>Username: <input type="text" required pattern ="\w+" name="Newusername"></p>
		
		<p>Password: <input type="password" required pattern ="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="Newpassword"	
	 		onchange="form.pwd2.paOern=this.value;"></p>

		<p>Confirm Password: <input type="password" required pattern ="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}" name="Newpassword2"	
	 		onchange="form.pwd2.paOern=this.value;"></p>

		<button class="button" type="submit">
			Add User to Database

		</button>
	</form>
</html>
