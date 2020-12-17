/**
 * 
 */

var JET_COLOR = "#ce2029";
var METEOR_COLOR = "#2A1B0E";
var jetComponent;
var deletedJetComponent;
var meteor = document.getElementById("meteor").value;

alert(meteor);

document.addEventListener("keydown", pressButton);

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
					
					
				}
				
				
        }

    };

    var url = document.location.protocol + "//" + document.location.host +
            document.location.pathname + "/ajaxRequest?usereq=" + req;


    xmlHTTP.open("GET", url, true);
    xmlHTTP.send();


}

function parseJetResponse(resp){
	
	var inputData = resp.split("_");
	jetComponent = inputData[2];
	deletedJetComponent = inputData[1];
}

function displayJetResponse(){
	
	document.getElementById(deletedJetComponent).style.backgroundColor = "";
	document.getElementById(jetComponent).style.backgroundColor = JET_COLOR;
	
}


