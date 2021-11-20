<?php
    require "init.php";
    $userId=$_POST["userId"];
    $sql_query="SELECT 
                    u.userName,n.notifText,n.id
                FROM 
                    notifications n
                INNER JOIN
                    users u 
                ON 
                    u.user_id = n.sendUserId
                WHERE 
                    recUserId LIKE '$userId';";
    $result=mysqli_query($conn,$sql_query);
    if(mysqli_num_rows($result)>0){
        while($row = mysqli_fetch_assoc($result)){
            $json['notifications'][]=$row;
        }
        echo json_encode($json); 
    }else{

    }
    mysqli_free_result($result);
    mysqli_close($conn);

?>