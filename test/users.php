<?php
/**
 * Fetch single user by username and password
 */
$app -> get('/api/user/{uname}/{password}', function($request, $response){
    
    require_once('inc/db.php');
    $uname = $request -> getAttribute('uname');
    $pass = $request -> getAttribute('password');
    
    $query = "select _id from tbl_users where username='$uname' and _hashed_password='$pass'";
    $result = $mysqli -> query($query);
    $num = $result -> num_rows;
    if($num > 0){
        echo json_encode(Array('isLogin' => '1'));
    }else{
        echo json_encode(Array('isLogin' => '0'));
    }

});

/**
 * Register user
 */
$app -> post('/api/newUser', function(){
    
    require_once('inc/db.php');

    

});