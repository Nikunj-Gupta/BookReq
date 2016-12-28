<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE ticket SET status = 'Ordered' WHERE uid = '$uid' ";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully Ordered";
		}else{
			echo "Could not Order!";
		}
	}else{
echo 'error';
}