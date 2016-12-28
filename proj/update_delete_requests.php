<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$tid = $_POST['tid'];
		//$message = $_POST['message'];
		
		require_once('dbConnect.php');
		
		$sql = "DELETE FROM ticket WHERE tid = '$tid' AND status != 'Ordered'";
		
		
		if(mysqli_query($con,$sql)){
			echo "Successfully deleted ";
		}else{
			echo "Couldn't delete! Its already Ordered";
		}
	}else{
echo 'error';
}