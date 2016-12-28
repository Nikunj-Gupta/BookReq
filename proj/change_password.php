<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){

		$uid = $_POST['uid'];
		$curr = $_POST['curr_pass'];
		$new = $_POST['new_pass'];
		$value = $_POST['value'];


 require_once('dbConnect.php');

 $sql = "SELECT salt FROM user WHERE uid = '$uid'";
			 $result = mysqli_query($con,$sql);
			 $check = mysqli_fetch_array($result);
			 $salt = $check['salt'];

			  $hashed = hash('sha256', $salt . hash('sha256', $curr));
 
 $sql = "SELECT * FROM user WHERE uid = '$uid' AND password ='$hashed'";
 
 $result = mysqli_query($con,$sql);
 
 $check = mysqli_fetch_array($result);
 
 if(isset($check)){
 	$sql = "SELECT salt FROM user WHERE uid = '$uid'";
			 $result = mysqli_query($con,$sql);
			 $check = mysqli_fetch_array($result);
			 $salt = $check['salt'];

			  $newhashed = hash('sha256', $salt . hash('sha256', $new));
 	$sql = "UPDATE user SET password = '$newhashed' WHERE uid = '$uid' ";
 	if(mysqli_query($con,$sql)){
			echo "Password Updated";
		}else{
			echo "Could not Order!";
		}


 #	array_push($result,array("uid"=>$res['uid']));
 #	echo json_encode(array("result"=>$result));
}
 else{
 echo 'failure';
 }

 #mysqli_close($con);
}