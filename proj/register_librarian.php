
<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){


		$name = $_POST['username'];
		$email = $_POST['email'];
		$password = $_POST['password'];
		$userlevel = 2;
		
		require_once('dbConnect.php');
		
		//$sql = "INSERT INTO user (name,email,password,userlevel) VALUES ('$name','$email','$password',2)";

		$hashed = hash('sha256', $password); 		//hash the password.
		$randomstring = md5(uniqid(rand(), true)); 	//Get a random unique string for salt.
		$salt = substr($randomstring, 0, 8); 		//Cut the salt from the random string.
		$hashed = hash('sha256', $salt . $hashed); 	//Hash the password with salt.

		$sql = "INSERT INTO user (email,password,salt,userlevel) VALUES ('$email','$hashed','$salt',2)";

		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully Registered";
		}else{
			echo "Could not register";

		}
	}else{
echo 'error';
}