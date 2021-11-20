<?php
    require "init.php";
    $userId=$_POST["userId"];
    $sql_query="SELECT 
                    u.userName,u.firstName,u.user_id
                FROM 
                    friends f
                INNER JOIN
                    users u 
                ON 
                    u.user_id != '$userId' AND ( u.user_id = f.friend2 OR u.user_id =f.friend1 )
                WHERE 
                    friend1 LIKE '$userId' OR friend2 LIKE '$userId';";
    $result=mysqli_query($conn,$sql_query);
    if(mysqli_num_rows($result)>0){
        while($row = mysqli_fetch_assoc($result)){
            $json['friends'][]=$row;
        }
        echo json_encode($json); 
    }else{

    }
    mysqli_free_result($result);
    mysqli_close($conn);

?>