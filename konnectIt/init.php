<?php 
    $db_name="konnectit";
    $mysql_user="root";
    $mysql_pass="";
    $server_name="localhost";
    $conn=mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);
    if(!$conn){
        // echo "Connection Error....".mysqli_connect_error();
    }else{
        // echo "<h3>Databse Connection Success</h3>";
    }
?>