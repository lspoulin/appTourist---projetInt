<?php
 require_once("../BD/connexion.inc.php");
 
 $tab=array();

 function listerParDistance(){
	 global $connexion,$tab;
	 $photo_dir="../photos/";
	 $latitude=$_POST['latitude'];
	 $longitude=$_POST['longitude'];
	 $requete="SELECT *, 111.111 *
    DEGREES(ACOS(COS(RADIANS(latitude))
         * COS(RADIANS(?))
         * COS(RADIANS(longitude - ?))
         + SIN(RADIANS(latitude))
         * SIN(RADIANS(?)))) AS distance_in_km FROM activity
        ORDER BY distance_in_km ASC";
	 try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute(array($latitude,$longitude,$latitude));
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['title']=$ligne['title'];
			$tab[$i]['description']=$ligne['description'];
			$tab[$i]['address']=$ligne['address'];
			$tab[$i]['longitude']=$ligne['longitude'];
			$tab[$i]['latitude']=$ligne['latitude'];
			$tab[$i]['url']=$ligne['url'];
			$tab[$i]['price']=$ligne['price'];
			$tab[$i]['distanceKM']=$ligne['distance_in_km'];
			$tab[$i]['image']=base64_encode(file_get_contents($photo_dir .$ligne['image']."_small.jpg"));
			$tab[$i]['tags']=$ligne['tags'];
			$tab[$i]['liked']=false;
			$i++;
		 }
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 function lister(){
	 global $connexion,$tab;
	 $photo_dir="../photos/";
	 
	 if(isset($_POST['userid'])){
	 	$user_id=$_POST['userid'];
	 	$requete="SELECT activity.*, activity.id IN (SELECT activity.id FROM `activity` INNER JOIN liked ON liked.activity_id=activity.id WHERE liked.user_id =?) as 'liked' FROM activity";
	 }
	 else{
	 	$requete="SELECT *, false as 'liked' FROM activity";
	 }
	 try{
		 $stmt = $connexion->prepare($requete);
		 if(isset($_POST['userid'])){
		 	$stmt->execute(array($user_id));
		 }
		 else{
		 	$stmt->execute();
		 }
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['title']=$ligne['title'];
			$tab[$i]['description']=$ligne['description'];
			$tab[$i]['address']=$ligne['address'];
			$tab[$i]['longitude']=$ligne['longitude'];
			$tab[$i]['latitude']=$ligne['latitude'];
			$tab[$i]['url']=$ligne['url'];
			$tab[$i]['price']=$ligne['price'];
			$tab[$i]['distanceKM']='0.0';
			$tab[$i]['image']=base64_encode(file_get_contents($photo_dir .$ligne['image']."_small.jpg"));
			$tab[$i]['tags']=$ligne['tags'];
			$tab[$i]['liked']=$ligne['liked'];;
			$i++;
		 }
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

function listerAvecTags(){
	 global $connexion,$tab;
	 $tags = $_POST['tags'];
	 $tagsarray=explode(",",$tags);
	 $photo_dir="../photos/";
	 $requete="SELECT * FROM activity";

	 $length = count($tagsarray);
	for ($i = 0; $i < $length; $i++) {
		if ($i == 0)
			$requete=$requete." WHERE INSTR(tags, ?)>0";
		else 
			$requete=$requete." AND INSTR(tags, ?)>0";
	}

	 try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute($tagsarray);
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['title']=$ligne['title'];
			$tab[$i]['description']=$ligne['description'];
			$tab[$i]['address']=$ligne['address'];
			$tab[$i]['longitude']=$ligne['longitude'];
			$tab[$i]['latitude']=$ligne['latitude'];
			$tab[$i]['url']=$ligne['url'];
			$tab[$i]['price']=$ligne['price'];
			$tab[$i]['distanceKM']='0.0';
			$tab[$i]['image']=base64_encode(file_get_contents($photo_dir .$ligne['image']."_small.jpg"));
			$tab[$i]['tags']=$ligne['tags'];
			$tab[$i]['liked']=false;
			$i++;
		 }
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }

 function listerParId(){
	 global $connexion,$tab;
	$photo_dir="../photos/";
	 $id = $_POST['id'];


	 if(isset($_POST['userid'])){
	 	$user_id=$_POST['userid'];
	 	$requete="SELECT activity.*, activity.id IN (SELECT activity.id FROM `activity` INNER JOIN liked ON liked.activity_id=activity.id WHERE liked.user_id =?) as 'liked' FROM activity WHERE id=?";
	 	$args=array($user_id, $id);
	 }
	 else{
	 	$requete="SELECT *, false as 'liked' FROM activity WHERE id=?";	
	 	$args=array($id);
	 }



	 try{
		 $stmt = $connexion->prepare($requete);
		 $stmt->execute($args);
		 $tab[0]="OK";
		 $i=1;
		 while($ligne=$stmt->fetch(PDO::FETCH_ASSOC)){
			$tab[$i]=array();
			$tab[$i]['id']=$ligne['id'];
			$tab[$i]['title']=$ligne['title'];
			$tab[$i]['description']=$ligne['description'];
			$tab[$i]['address']=$ligne['address'];
			$tab[$i]['longitude']=$ligne['longitude'];
			$tab[$i]['latitude']=$ligne['latitude'];
			$tab[$i]['url']=$ligne['url'];
			$tab[$i]['price']=$ligne['price'];
			$tab[$i]['distanceKM']='0.0';
			$tab[$i]['image']=base64_encode(file_get_contents($photo_dir . $ligne['image'].".jpg"));
			$tab[$i]['tags']=$ligne['tags'];
			$tab[$i]['liked']=$ligne['liked'];
			$i++;
		 }
	 }catch (Exception $e){
		 $tab[0]="NOK";
	 }finally {
		echo json_encode($tab);
	 }
 }
 
 
 
 //Le controleur
 $action=$_POST['action'];


 switch($action){
	case "listerParId":
		listerParId();
		break;
	case "lister":
		lister();
		break;
	case "listerParDistance":
		listerParDistance();
		break;
	case "listerAvecTags":
		listerAvecTags();
		break;
 }
?>
