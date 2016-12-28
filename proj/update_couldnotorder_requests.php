<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE ticket SET status = 'CouldNotOrder' WHERE uid = '$uid' ";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully updated";
		}else{
			echo "Could not update! Try again later!";

		}
	}else{
echo 'error';
}