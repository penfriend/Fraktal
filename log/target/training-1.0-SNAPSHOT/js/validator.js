$(document).ready(function() {
	
	var regex = /^[a-z0-9]{5,10}$/i;
	var $player_1 = $('#player_1');
	var $player_2 = $('#player_2');
	var $player_1_field = $('#player_1_field');
	var $player_2_field = $('#player_2_field');
	var $start_button = $('#start');
	var fields = [false, false];

	defaultClass($player_1, $player_1_field);
	defaultClass($player_2, $player_2_field);
	disableButton($start_button);

	$player_1_field.keyup(function() {
		validate($player_1, $player_1_field, regex, 0);
	});

	$player_2_field.keyup(function() {
		validate($player_2, $player_2_field, regex, 1);
	});

	function validate(parent, field, reg, index) {
		var value = field.val();
		if (value === "") {
			defaultClass(parent, field);
			fields[index] = false;
		}
		else if (reg.test(value)) {
			successClass(parent, field);
			fields[index] = true;
		}
		else {
			dangerClass(parent, field);
			fields[index] = false;
		}
		if (checkFields())
			enableButton($start_button);
		else
			disableButton($start_button);
	}

	function checkFields() {
		for (var i = 0; i < fields.length; i++) {
			if (fields[i] === false)
				return false;
		}
		return true;
	}

	function successClass(parent, field) {
		var $error = $('#' + parent.attr('id') + ' .form-control-feedback');
		$($error).hide();
		parent.removeClass('has-danger');
		parent.addClass('has-success');
		field.removeClass('form-control-danger');
		field.addClass('form-control-success');
	}

	function dangerClass(parent, field) {
		var $error = $('#' + parent.attr('id') + ' .form-control-feedback');
		$($error).show();
		parent.removeClass('has-success');
		parent.addClass('has-danger');
		field.removeClass('form-control-success');
		field.addClass('form-control-danger');
	}

	function defaultClass(parent, field) {
		var $error = $('#' + parent.attr('id') + ' .form-control-feedback');
		$($error).hide();
		parent.removeClass('has-success');
		parent.removeClass('has-danger');
		field.removeClass('form-control-success');
		field.removeClass('form-control-danger');
	}

	function enableButton(button) {
		button.prop('disabled', false);
	}

	function disableButton(button) {
		button.prop('disabled', true);
	}

});