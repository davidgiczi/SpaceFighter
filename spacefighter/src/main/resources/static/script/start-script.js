/**
 * 
 */
var secCounter = 5;
var timer = setInterval(countDown, 1000);

function countDown(){
	
	if(secCounter === 0){
		
		clearInterval(timer);
		window.location.href = document.location.protocol + "//" + 
				document.location.host +
				"/SpaceFighter/playing";
	}
	else{
		
	secCounter--;
	document.getElementById("time").innerHTML = "<b>[000" + secCounter + "]</b>";
	}
	
}
