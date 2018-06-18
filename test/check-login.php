<?php
require_once('inc/db.php');
$username = $_GET['username'];
$password = $_GET['password'];

$query = mysqli_query($conn, "select _id from tbl_users where username='".$username."' and _hashed_password='".$password."'");
$num = mysqli_num_rows($query);
if($num > 0){
    echo json_encode(Array('isLogin' => '1'));
}else{
    echo json_encode(Array('isLogin' => '0'));
}

?>