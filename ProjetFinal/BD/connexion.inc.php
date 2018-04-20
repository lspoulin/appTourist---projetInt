<?php
define("USAGER","root");
define("PASSE","root");
try {
  $dns = 'mysql:host=localhost;dbname=gestionTouristique';
  $options = array(
	PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
  );
  $connexion = new PDO( $dns, USAGER, PASSE, $options );
} catch ( Exception $e ) {
	echo $e->getMessage();
	echo "Probleme de connexion au serveur de bd";
	exit();
}
?>