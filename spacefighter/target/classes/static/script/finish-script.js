/**
 * 
 */
var METEOR_COLOR = "#2A1B0E"
var score = document.getElementById("msg").value;
var randValueStore = [];

while(randValueStore.length < 4){
	
	var randValue = Math.floor(Math.random() * 4 + 1);
	
	if( !randValueStore.includes(randValue) ){
		randValueStore.push(randValue);
	}
	
}

setTimeout(function appearing(){randMeteorAppearing(randValueStore[0]);}, 100);
setTimeout(function appearing(){randMeteorAppearing(randValueStore[1]);}, 400);
setTimeout(function appearing(){randMeteorAppearing(randValueStore[2]);}, 700);
setTimeout(function appearing(){randMeteorAppearing(randValueStore[3]);}, 1000);

function randMeteorAppearing(value){
	
	switch(value){
		
		case 1:
		appearMeteor1();
		break;
		case 2:
		appearMeteor2();
		break;
		case 3:
		appearMeteor3();
		break;
		case 4:
		appearMeteor4();
	
	}
	
}


function appearMeteor1(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 0 && (x * 30 + y) % 2 === 0){
	document.getElementById(x * 30 + y).style.backgroundColor = METEOR_COLOR;
	}
	}
}

	setTimeout(disappearMeteor1, 300);
}

function disappearMeteor1(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 0 && (x * 30 + y) % 2 === 0){
	document.getElementById(x * 30 + y).style.backgroundColor = "";
	
}
	}
}

}

function appearMeteor2(){

for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 1 && (x * 30 + y) % 2 === 1){
	document.getElementById(x * 30 + y).style.backgroundColor = METEOR_COLOR;
}
	}
}
	setTimeout(disappearMeteor2, 300);
}

function disappearMeteor2(){

for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 1 && (x * 30 + y) % 2 === 1){
	document.getElementById(x * 30 + y).style.backgroundColor = "";
}
	}
}
}

function appearMeteor3(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 0 && (x * 30 + y) % 2 === 1){
	document.getElementById(x * 30 + y).style.backgroundColor = METEOR_COLOR;
	}
	}
}
	setTimeout(disappearMeteor3, 300);
}

function disappearMeteor3(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 0 && (x * 30 + y) % 2 === 1){
	document.getElementById(x * 30 + y).style.backgroundColor = "";
	}
	}
}

}

function appearMeteor4(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 1 && (x * 30 + y) % 2 === 0){
	document.getElementById(x * 30 + y).style.backgroundColor = METEOR_COLOR;
	}
	}
}
	setTimeout(disappearMeteor4, 300);
}

function disappearMeteor4(){
	
for(var x = 0; x < 23; x++){
	for(var y = 0; y < 30; y++){
	
	if(x % 2 === 1 && (x * 30 + y) % 2 === 0){
	document.getElementById(x * 30 + y).style.backgroundColor = "";
	}
	}
}
	
}

setTimeout(function closedProcess(){

	
	if(confirm("Szeretnél új játékot játszani?\n[Pontszám: " + score + "]\n")){
					
				location.href = document.location.protocol + "//" + 
				document.location.host +
				"/SpaceFighter";
				}
		
				}, 2000);