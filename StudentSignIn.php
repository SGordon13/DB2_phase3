<?php
    $user = "student";
    $bool = false;
    $mysqli = new mysqli('localhost', 'root', '', 'DB2');
    $email = $_POST['email'];
    $password = $_POST['password'];
    
    // get the target ID entered
    $qGetId = "SELECT id FROM users WHERE email = '$email'";
    $id = $mysqli->query($qGetId);
    $targetID = mysqli_fetch_array($id);

    // get an array of all student ID's
    $sids = [];
    $qstudentIDs = "SELECT student_id from students";
    $res = $mysqli->query($qstudentIDs);
    while($row = mysqli_fetch_assoc($res)){
        foreach($row as $cname => $cvalue){
            array_push($sids,$cvalue);
        }
    }

    // grab student info from user table 
    $qGetInfo = "SELECT * FROM users WHERE email = '$email'";
    $result = $mysqli->query($qGetInfo);
    $testrow = mysqli_fetch_array($result);

    // check if target ID is in array of student ID's
    if(empty($targetID)){
        $response["success"] = "false";
        echo json_encode($response);
    } else if (!in_array($targetID['id'], $sids)){
        $response["success"] = "false";
        echo json_encode($response);
    } else if($password != $testrow['password']){
        $response["success"] = "false";
        echo json_encode($response);
    }else{
        // get all of the information from database starting with data in the users table
        // then accessing parent email using parent id

        // get the relevant parent id
        $getPID = "SELECT parent_id, grade FROM students WHERE student_id = {$targetID['id']}";
        $pidRes = $mysqli->query($getPID);
        $pidrow = mysqli_fetch_array($pidRes);

        // get the parent email
        $getPE = "SELECT email FROM users WHERE id = {$pidrow['parent_id']}";
        $peRes = $mysqli->query($getPE);
        $peRow = mysqli_fetch_array($peRes);

        $response["name"] = $testrow['name'];
        $response["email"] = $testrow['email'];
        $response["phone"] = $testrow['phone'];
        $response["password"] = $testrow['password'];
        $response["paEmail"] = $peRow['email'];
        $response["grade"] = $pidrow['grade'];
    }
    }
    $mysqli->close();
?>