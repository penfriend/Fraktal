$(document).ready(function () {

	/*
	function init() {

		var rootpath = "//" + window.location.host + "/_ah/api";

		gapi.client.load('training', 'v1', loadCallback, rootpath);
	}

	function loadCallback() {
		enableButtons();
	}

	function enableButtons() {
		
		var btn = document.getElementById("button");

		
		btn.onclick = function() {getResponse();};
		
		btn.innerHTML = "Click me to get the response";
		
	}

	function getResponse() {
		var request = gapi.client.training.getResponse();

		request.execute(getResponseCallback);
	}

	function getResponseCallback(response) {
		alert(response.message);
	}
	*/

	checkCookies();
	var $start_button = $('#start');

	$($start_button).click(function() {
		var player_1_name = $('#player_1_field').val();
		var player_2_name = $('#player_2_field').val();
		setCookie('player_1_name', player_1_name);
		setCookie('player_2_name', player_2_name);
		window.location = "templates/log.html";
	});

	function checkCookies() {
		var player_1_name = getCookie('player_1_name');
		var player_2_name = getCookie('player_2_name');
		if (player_1_name && player_2_name)
			window.location = "templates/log.html";
	}

	function setCookie(key, value) {
	    document.cookie = key + "=" + value;
	}

	function getCookie(cname) {
	    var name = cname + "=";
	    var ca = document.cookie.split(';');
	    for(var i = 0; i < ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0) == ' ') {
	            c = c.substring(1);
	        }
	        if (c.indexOf(name) == 0) {
	            return c.substring(name.length, c.length);
	        }
	    }
    	return "";
	}

});


