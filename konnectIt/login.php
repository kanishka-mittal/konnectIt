<?php
    require "init.php";
    $username=$_POST["username"];
    $userPass=$_POST["password"];
    $sql_query="SELECT 
                    * 
                FROM 
                    users 
                WHERE 
                    username LIKE '$username' AND password LIKE '$userPass';";
    $result=mysqli_query($conn,$sql_query);
    if(mysqli_num_rows($result)>0){
        $row=mysqli_fetch_assoc($result);
        $fName=$row['firstName'];
        $userId=$row['user_id'];
        $json['user_info'][]=$row;
        echo json_encode($json); 
    }else{
        echo "Login Failed....Try Again...";
    }
    mysqli_free_result($result);
    mysqli_close($conn);

?>