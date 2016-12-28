<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE ticket SET status = 'Rejected' WHERE uid = '$uid' ";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully Rejected";
		}else{
			echo "Could not Reject! Try Again Later";

		}
	}else{
echo 'error';
}