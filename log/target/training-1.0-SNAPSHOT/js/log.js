$(document).ready(function() {
	
	checkCookies();
	setNicknames();

	function checkCookies() {
		var player_1_exist = document.cookie.indexOf('player_1_name');
		var player_2_exist = document.cookie.indexOf('player_2_name');
		if (player_1_exist === -1 || player_2_exist === -1)
			window.location = "/";
	}

	function setNicknames() {
		var player_1_name = getCookie('player_1_name');
		var player_2_name = getCookie('player_2_name');
		$("#player_1").text(player_1_name);
		$("#player_2").text(player_2_name);
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
