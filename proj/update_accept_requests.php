<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE ticket SET status = 'Accepted' WHERE uid = '$uid' ";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully Accepted";
		}else{
			echo "Could not Accept! Try Again Later";

		}
	}else{
echo 'error';
}