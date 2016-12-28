<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){


		#$name = $_POST['username'];
		#$email = $_POST['email'];
		#$password = $_POST['password'];
		#$userlevel = 1;

		$uid = $_POST['uid'];
		$message = $_POST['message'];
		#$t = time();
		$subject = "NULL1";
		$product = "NULL2";
		$department = "NULL3";


		require_once('dbConnect.php');
		
		$sql = "INSERT INTO ticket (uid,message,edittstamp,subject,product,department) VALUES ('$uid','$message',CURRENT_TIME(),'$subject','$product','$department')";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully Requested";
		}else{
			echo "Could not request";
			#echo $tstamp;

		}
	}else{
echo 'error';
}