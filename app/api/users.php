<?php

/**
 * Get all users
 */
$app -> get('/api/users/', function(){
    $query = "select * from tbl_users";
    $result = $mysqli -> query($query);
    $num = $result -> num_rows;
    $data = [];
    if($num > 0) {
        $data['msg'] = "success";
        while($row = $result -> fetch_assoc()){
            $data['result'] = $row;
        }
    }else{
        $data['msg'] = "error";
    }
    header("Content-Type: application/json");
    echo json_encode($data);
});

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

    $email = $_POST['email'];
    $name = $_POST['name'];
    $gender = $_POST['gender'];
    $date_of_birth = $_POST['date_of_birth'];
    $username = $_POST['username'];
    $user_type = $_POST['user_type'];
    $pass = $_POST['pass'];
    
    $oupt = [];

    $query1 = "select _id from tbl_users where email='$email'";
    $result1 = $mysqli -> query($query1);
    $num1 = $result1 -> num_rows;
    if($num1 > 0){
        $oupt['msg'] = "Email already exists";
        exit;
    }

    $query2 = "select _id from tbl_users where username='$username'";
    $result2 = $mysqli -> query($query2);
    $num2 = $result2 -> num_rows;
    if($num2 > 0){
        $oupt['msg'] = "Username already exists";
        exit;
    }

    $query = "select _id from tbl_users order by _id desc limit 1";
    $result = $mysqli -> query($query);
    $row = $result -> fetch_assoc();
    $num = $result -> num_rows;
    if($num > 0){
        $userID = $row['_id'];
	    $userID = substr($userID, 4);
	    $userID = str_pad(++$userID,4,'0',STR_PAD_LEFT);
	    $userID = "PURL".$userID;
    }else{
        $id = "PURL0001";
    }
    //$pass = password_hash($pass, PASSWORD_BCRYPT);

    $query3 = "insert into tbl_users (_id, email, name, gender, date_of_birth, username, user_type, _hashed_password) values ('$id', '$email', '$name', '$gender', '$date_of_birth', '$username', '$user_type', '$pass')";
    $result3 = $mysqli -> query($query3);
    if($result3){
        $oupt['msg'] = "success";
    }else{
        $oupt['msg'] = "Error";
    }
    echo json_encode($oupt);

});