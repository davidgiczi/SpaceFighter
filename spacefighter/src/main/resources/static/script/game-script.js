/**
 * 
 */

/**
 * 
 */

var JET_COLOR = "#CE2029";
var METEOR_COLOR = "#2A1B0E";
var JET_WINDOW_COLOR = "#554616";
var JET_POSITION = document.getElementById("jet-position").value;
var BOARD_ROWS = parseInt(document.getElementById("rows").value);
var BOARD_COLS = parseInt(document.getElementById("cols").value);
var deletedJetComponent;
var meteor = ["meteor", document.getElementById("meteor").value];
var run = setInterval(function playing(){sendAjaxRequest("comingMeteor")}, 500);
document.addEventListener("keydown", pressButton);
var score = 0;
var speedLevel = 1;

function pressButton(e) {

    e = e || window.event;

	if (e.keyCode === 37) {
	
       sendAjaxRequest("goJetLeft");
	}
	if (e.keyCode === 39) {
	
       sendAjaxRequest("goJetRight");
	}
 else if (e.keyCode === 13) {
	
   		 alert("Védőpajzs aktiválva...");
		
    } 
}


function sendAjaxRequest(req) {

    var xmlHTTP = new XMLHttpRequest();

    xmlHTTP.onreadystatechange = function () {

        if (xmlHTTP.readyState === 4 && xmlHTTP.status === 200) {

            var response = xmlHTTP.responseText;
				
				if(response.startsWith("jet")){
				
				parseJetResponse(response);
				displayJetResponse();	
					
				}
				else if(response.startsWith("meteor")){
					
					clearScreen();
					parseMeteorResponse(response);
					setSpeed();
					getScore();
					setupTempo();
					displayMeteor();
				}
				else if(response.startsWith("collision")){
						
					clearInterval(run);
					collision();
					setTimeout(goFinishPage, 6000);
			}
        }

    }

    var url = document.location.protocol + "//" + document.location.host +
            document.location.pathname + "/ajaxRequest?usereq=" + req;


    xmlHTTP.open("GET", url, true);
    xmlHTTP.send();


}

function parseJetResponse(resp){
	
	var inputData = resp.split("_");
	JET_POSITION = inputData[2];
	deletedJetComponent = inputData[1];
}

function displayJetResponse(){
	
	document.getElementById(deletedJetComponent).style.backgroundColor = "";
	document.getElementById(JET_POSITION).style.backgroundColor = JET_COLOR;
	
}

function clearScreen(){
	for(var i = 1; i < meteor.length; i++){
	document.getElementById(meteor[i]).style.backgroundColor = "";
}
}

function parseMeteorResponse(resp){
	meteor = resp.split("_");
}

function displayMeteor(){
	
	for(var i = 1; i < meteor.length; i++){
		
		document.getElementById(meteor[i]).style.backgroundColor = METEOR_COLOR;
		
		if(meteor[i] == JET_POSITION){
			document.getElementById(JET_POSITION).style.backgroundColor = JET_COLOR;
		}
		
	}
}

function collision(){

	 blinkingJetWindow();
	 setTimeout(explodeMeteor, 2000);
}

function blinkingJetWindow(){
	
	document.getElementById("tab").style.borderColor = JET_COLOR;
  	setTimeout(function () {
       document.getElementById("tab").style.borderColor = JET_WINDOW_COLOR;
    }, 500);


	setTimeout(function () {
       
	for(var i = 0; i < BOARD_ROWS * BOARD_COLS; i++){
		document.getElementById(i).style.borderColor = JET_COLOR;
		
	}
	
	setTimeout(function () {
       
	for(var i = 0; i < BOARD_ROWS * BOARD_COLS; i++){
		document.getElementById(i).style.borderColor = JET_WINDOW_COLOR;
	}

    }, 500);

    }, 500);

	setTimeout(blinkingJetWindow, 1000);			
}

function explodeMeteor(){
	
	clearScreen();
	meteor.push(JET_POSITION - BOARD_COLS);
	displayMeteor();
	setTimeout(function explode(){explodeProcess1(1)}, 300);
	setTimeout(blinkingMeteor, 300);
	setTimeout(function explode(){explodeProcess1(2)}, 600);
	setTimeout(blinkingMeteor, 600);
	setTimeout(function explode(){explodeProcess1(3)}, 900);
	setTimeout(blinkingMeteor, 900);
	setTimeout(function explode(){explodeProcess1(4)}, 1200)
	setTimeout(blinkingMeteor, 1200);
	setTimeout(function explode(){explodeProcess1(5)}, 1300);
	setTimeout(explodeProcess2, 1600);
	setTimeout(blinkingMeteor, 1700);
}

function blinkingMeteor(){
	
	setTimeout(clearScreen, 500);
	setTimeout(displayMeteor, 1000);
	setTimeout(blinkingMeteor, 2000);
}

function explodeProcess1(distFromJet){
	
	
	clearScreen();
	if(distFromJet === 1){
	meteor = [];
	}
	else{
		var jetIndex = meteor.indexOf(JET_POSITION);
		meteor.splice(jetIndex, 1);
		var frontOfJetMeteorIndex = meteor.indexOf(JET_POSITION - BOARD_COLS);
		meteor.splice(frontOfJetMeteorIndex, 1);
	}	
	var jet_X= Math.floor(JET_POSITION / BOARD_COLS);
	var jet_Y = JET_POSITION % BOARD_COLS;
	JET_POSITION = JET_POSITION - BOARD_COLS;
	meteor.push("meteor")
	meteor.push(JET_POSITION);
	meteor.push(JET_POSITION - BOARD_COLS);
	
	
	for(var y = jet_Y - distFromJet;  0 <= y; y -= 2){
		meteor.push(jet_X * BOARD_COLS + y);
	}
	
	for(var y = jet_Y + distFromJet; y < BOARD_COLS; y += 2){
		meteor.push(jet_X * BOARD_COLS + y);
	}

	displayMeteor();	
}

function explodeProcess2(){
	
	clearScreen();
	meteor = [];
	meteor.push("meteor")
	meteor.push(JET_POSITION);
	
	for(var x = 0; x < BOARD_ROWS; x++){
		for(var y = 0; y < BOARD_COLS; y++){
			
			if( (x * BOARD_COLS + y) % 2 == 1 && (x * BOARD_COLS + y) != JET_POSITION){
				meteor.push(x * BOARD_COLS + y);
			}
			
		}
		
	}
	
	displayMeteor();
}


function getScore(){
	
	var meteor_X = Math.floor(meteor[1] / BOARD_COLS);
	
	if(meteor_X == BOARD_ROWS - 1){
				
		switch(speedLevel){
			
			case 1:
			score += 50;
			break;
			case 2:
			score += 100;
			break;
			case 3:
			score += 200;
			break;
			case 4:
			score += 300;
			break;
			case 5:
			score += 500;
			
		}
	document.getElementById("score").innerHTML = "<b>[" + getScoreValue(score) + "]</b>";	
	}
}

function getScoreValue(scoreValue){
	
	if(100 > scoreValue){
		return "00" + score;
	}
	else if(scoreValue >= 100 && scoreValue < 1000){
		return "0" + score;
	}

	return score;
}

function setSpeed(){
	
	if(500 > score){
		speedLevel = 1;
	}
	else if(score >= 500 && score < 1500){
		speedLevel = 2;
	}
	else if(score >= 1500 && score < 3500){
		speedLevel = 3;
	}
	else if(score >= 3500 && score < 6500){
		speedLevel = 4;
	}
	else if(score >= 6500){
		speedLevel = 5;
	}

}

function setupTempo(){
	
	switch(score){
		
		case 500 :
		clearInterval(run);
		run = setInterval(function playing(){sendAjaxRequest("comingMeteor")}, 400);
		break;
		case 1500 :
		clearInterval(run);
		run = setInterval(function playing(){sendAjaxRequest("comingMeteor")}, 300);
		break;
		case 3500 :
		clearInterval(run);
		run = setInterval(function playing(){sendAjaxRequest("comingMeteor")}, 200);
		break;
		case 6500 :
		clearInterval(run);
		run = setInterval(function playing(){sendAjaxRequest("comingMeteor")}, 100);
	}
	
}

function goFinishPage(){
	
	location.href = document.location.protocol + "//" + document.location.host +
            document.location.pathname + "/theEnd?result=" + score;
	 m
}

