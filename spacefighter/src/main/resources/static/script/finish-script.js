/**
 * 
 */

var score = document.getElementById("msg").value;

setTimeout(function closedProcess(){

	if(confirm("Szeretnél új játékot játszani?\n[Pontszám: " + score + "]")){
					
				location.href = document.location.protocol + "//" + 
				document.location.host +
				"/SpaceFighter";
				}
				
				}, 1000);