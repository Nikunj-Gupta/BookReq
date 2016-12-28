<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 #$email = $_POST['username'];
 $userid = $_POST['uid'];

 
 require_once('dbConnect.php');
 
# $sql = "SELECT * FROM users WHERE email = '$email' AND password='$password'";
$sql = "SELECT * FROM user WHERE userid = '$userid'";
 
 $result = mysqli_query($con,$sql);
 
 $check = mysqli_fetch_array($result);
 
 
 if(isset($check)){
 	echo json_encode(array("result"=>$check));
}

 else{
 echo 'failure';
 }

 #mysqli_close($con);
}