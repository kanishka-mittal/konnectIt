<?php
    require "init.php";
    $userId=$_POST["userId"];
    $friendId=$_POST["friendId"];
    $sql_query="DELETE
                FROM
                    friends
                WHERE
                    (friend1 LIKE '$userId' AND friend2 LIKE '$friendId')
                OR
                    (friend2 LIKE '$userId' AND friend1 LIKE '$friendId');";
    if(mysqli_query($conn,$sql_query)){
        echo "Done";
    }else{
        echo mysqli_error($conn);
    }
    mysqli_close($conn);

?>