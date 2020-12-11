<?php
require_once '../includes/DbOperations.php';
$response = array();
if($_SERVER['REQUEST_METHOD']=='POST'){

    if(
        isset($_POST['username']) and isset($_POST['email'])and isset($_POST['password'])
    ){
        //operate data further
        $db = new DbOperations();
        if($db->createUser($_POST['username'],$_POST['password'],$_POST['email']))
        {
            $response['error']=false;
            $response['message']="User Registered Successfully";
        }else{
            $response['error']=true;
            $response['message']="Please try again";
        }
        

    }else{
        $response['error']=true;
        $response['message']="Required feilds are missing";
    }

}else{
     $response['error']=true;
     $response['message']="Invalid Request";
}

echo json_encode($response);