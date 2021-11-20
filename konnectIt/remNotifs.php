<?php
    require "init.php";
    $id=$_POST["id"];
    $sql_query="DELETE
                FROM
                    notifications
                WHERE
                    id LIKE '$id';";
    if(mysqli_query($conn,$sql_query)){
        echo "Done";
    }else{
        echo mysqli_error($conn);
    }
    mysqli_close($conn);

?>