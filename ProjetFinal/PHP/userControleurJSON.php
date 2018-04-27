<?php
 require_once("../BD/connexion.inc.php");
 
 $tab=array();
 function enregistrer(){
	global $connexion,$tab;
	
	try{
		 $name=$_POST['name'];
		 $email=$_POST['email'];
		 $password=$_POST['password'];
		 $tags=$_POST['preferences'];

		 //echo($name . " " . $email . " " . $password . " " . $tags);
		 $requete="INSERT INTO user VALUES(NULL, ?,?,?,?, 0)";
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($name,$email,$password, $tags));
		 $dernierID=$connexion->lastInsertId();
		 $tab[0]="OK";
		 $tab[1]=$dernierID;
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }


 function lister(){
	 global $connexion,$tab;
	 $requete="SELECT * FROM user";
	 try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute();
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['name']=$ligne['name'];
			$tab[$i]['email']=$ligne['email'];
			$tab[$i]['preferences']=$ligne['preferences'];
			$i++;
		 }
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 function login(){
	 global $connexion,$tab;
	 $user=$_POST['user'];
	$password=$_POST['password'];
	//echo($user);
	//echo($password);
	 $requete="SELECT * FROM user WHERE name=? AND password=?";
	 try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($user,$password));
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['name']=$ligne['name'];
			$tab[$i]['email']=$ligne['email'];
			$tab[$i]['preferences']=$ligne['preferences'];
			$i++;
		 }
		 if($i==1)
		 	 $tab[0]="NOK";
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 
 /*function modifierpassword(){
	global $connexion,$tab;
	$id=$_POST['id'];
	$oldpassword=$_POST['oldpassword'];
	$newpassword=$_POST['newpassword'];

	$requete="SELECT * FROM users WHERE id=? and password=?";
	try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($id,$oldpassword));
		 $tab[0]="OK";
		 $i=1;
		 if($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){

		 	$requete="UPDATE users SET password=? WHERE id=?";
			try{
				 $stmt = $connexion->prepare($requete);
				 $stmt->execute(array($newpassword,$id));
				 $tab[0]="OK";
				}else{
					$tab[0]="Ce user est inexistant !!!";
				}
			}catch (Exception $e){
				 $tab[0]="NOK";
			}finally {
				echo json_encode($tab);
			 }	
		}else{
			$tab[0]="Ce user est inexistant !!!";
		}
	}catch (Exception $e){
		 $tab[0]="NOK";
	}finally {
		echo json_encode($tab);
	 }	
 }
 */
 function maj(){
	global $connexion,$tab;
	try{
		 $id=$_POST['id'];
		 $name=$_POST['name'];
		 $email=$_POST['email'];
		 $tags=$_POST['preferences'];
		 $requete="UPDATE user SET name=?,email=?,preferences=? WHERE id=?";
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($name,$email,$tags,$id));
		 $tab[0]="OK";
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }
 function enlever(){
	 global $connexion,$tab;
	try{
		 $id=$_POST['id'];
		 $requete="DELETE FROM user WHERE id=?";
		 //echo $requete;
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($id));
		 $tab[0]="OK";
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 function activityLiked(){
	 global $connexion,$tab;
	try{
		 $user_id=$_POST['userid'];
		 $activity_id=$_POST['activityid'];
		 $requete="INSERT INTO liked VALUES(?,?)";
		 //echo $requete;
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($user_id, $activity_id));
		 $tab[0]="OK";
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 function activityUnliked(){
	 global $connexion,$tab;
	try{
		 $user_id=$_POST['userid'];
		 $activity_id=$_POST['activityid'];
		 $requete="DELETE FROM liked WHERE user_id=? AND activity_id=?";
		 //echo $requete;
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($user_id, $activity_id));
		 $tab[0]="OK";
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }
 
 //Le controleur
 $action=$_POST['action'];


 switch($action){
	case "enregistrer":
	   enregistrer();
	   break;
	case "lister":
		lister();
		break;
	case "modifierpassword":
		modifierpassword();
		break;
	case "maj":
		maj();
		break;
	case "enlever":
		enlever();
		break;
	case "login":
		login();
		break;
	case "activityLiked":
		activityLiked();
		break;
	case "activityUnLiked":
		activityUnliked();
		break;
 }
?>
