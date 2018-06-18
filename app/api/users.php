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
$app -> get('/api/user/{username}/{password}', function($request, $response){
    
    require_once('inc/db.php');
    $uname = $request -> getAttribute('username');
    $pass = $request -> getAttribute('password');
    
    $query = "select _id, name, email, username, password, date_of_birth, gender, user_type from tbl_users where username='$uname' and _hashed_password='$pass'";
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
//$app -> post('/api/newUser/{name}/{email}/{gender}/{dob}/{username}/{user_type}/{pass}', function($name, $email, $gender, $dob, $username, $user_type, $pass){
$app -> post('/api/newUser', function($request, $response){
    require_once('inc/db.php');
    /*$name = $_POST['name'];
    $email = $_POST['email'];
    $gender = $_POST['gender'];
    $date_of_birth = $_POST['dob'];
    $username = $_POST['username'];
    $user_type = $_POST['user_type'];
    $pass = $_POST['pass'];*/
    $name = $request->getParam('name');
    $email = $request -> getParam('email');
    $gender = $request -> getParam('gender');
    $username = $request -> getParam('username');
    $date_of_birth = $request -> getParam('dob');
    $user_type = $request -> getParam('user_type');
    $pass = $request -> getParam('pass');
    $height = $request -> getParam('height');
    $weight = $request -> getParam('weight');
    $position = $request -> getParam('position');
    $profile = $request -> getParam('profile');
    
    $query1 = "select _id from tbl_users where email='$email'";
    $result1 = $mysqli -> query($query1);
    $num1 = $result1 -> num_rows;
    if($num1 > 0){
        echo json_encode(Array('isLogin' => '1'));
        exit;
    }

    $query2 = "select _id from tbl_users where username='$username'";
    $result2 = $mysqli -> query($query2);
    $num2 = $result2 -> num_rows;
    if($num2 > 0){
        echo json_encode(Array('isLogin' => '2'));
        exit;
    }

    $query = "select _id from tbl_users order by _id desc limit 1";
    $result = $mysqli -> query($query);
    $row = $result -> fetch_assoc();
    $num = $result -> num_rows;
    if($num > 0){
        $id = $row['_id'];
	    $id = substr($id, 4);
	    $id = str_pad(++$id,4,'0',STR_PAD_LEFT);
        $id = "PURL".$id;
    }else{
        $id = "PURL0001";
    }
    //$pass = password_hash($pass, PASSWORD_BCRYPT);

    $query3 = "insert into tbl_users (_id, email, name, gender, date_of_birth, username, user_type, password, height, weight, position, profile) values ('$id', '$email', '$name', '$gender', '$date_of_birth', '$username', '$user_type', '$pass', '$height', '$weight', '$position', '$profile')";
    $result3 = $mysqli -> query($query3);
    if($result3){
        echo json_encode(Array('isLogin' => '0'));
        exit;
    }else{
        echo json_encode(Array('isLogin' => '3'));
    }

});