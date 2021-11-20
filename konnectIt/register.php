<?php
    require "init.php";
    $username=$_POST["username"];
    $firstName=$_POST["fName"];
    $userPass=$_POST["password"];
    $email=$_POST["email"];
    $sql_query="INSERT INTO users(userName,firstName,email,password) VALUES('$username','$firstName','$email','$userPass')";
    if(mysqli_query($conn,$sql_query)){
        echo "Done";
    }else{
        echo mysqli_error($conn);
    }
    mysqli_free_result($result);
    mysqli_close($conn);
?>