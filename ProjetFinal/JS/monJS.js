function montrer(elem){
		  document.getElementById(elem).style.display='block';
		}
function cacher(elem){
  document.getElementById(elem).style.display='none';
}
function envoyerLister(){
   document.getElementById('formLister').submit();
}
function envoyerEnreg(){
   document.getElementById('formEnreg').submit();
}
function envoyerEnlever(){
   document.getElementById('formEnlever').submit();
}
function envoyerModifier(){
   document.getElementById('formModifier').submit();
}
function envoyerMAJ(){
   document.getElementById('formMAJ').submit();
}