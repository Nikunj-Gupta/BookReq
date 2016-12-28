<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		
		require_once('dbConnect.php');
		
		$sql = "UPDATE ticket SET status = 'Procured' WHERE uid = '$uid' ";
		$result = mysqli_query($con,$sql);
		if($result){
			$sql = "SELECT * FROM user WHERE uid = '$uid'";
			$result = mysqli_query($con,$sql);
			$check = mysqli_fetch_array($result);
			echo json_encode(array("result"=>$check));
		}else{
			echo "Could not Update Status! Try again later!";
		}

		$sql = "SELECT * FROM users WHERE uid = '$uid'";

		
		
	}else{
echo 'error';
}