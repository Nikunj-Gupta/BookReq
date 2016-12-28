<?php
 
 if($_SERVER['REQUEST_METHOD']=='POST'){
 #$email = $_POST['username'];
 #$password = $_POST['password'];
 $uid  = $_POST['uid']; 		

 
 require_once('dbConnect.php');
 
 $sql = "SELECT * FROM ticket WHERE uid = '$uid'";




 
 $result = mysqli_query($con,$sql);
 
 $jsonData = array();
 while($check = mysqli_fetch_array($result))
 {
 	$jsonData[] = $check;
 }	

 echo json_encode(array("result"=>$jsonData));



 
# if(isset($check)){
 	#echo 'success';
 	#echo json_encode(array("result"=>$res));



 #	array_push($result,array("uid"=>$res['uid']));
 #	echo json_encode(array("result"=>$result));
#}
 #else
 #{
 #echo 'No Book Requested';
 #}

 #mysqli_close($con);
}