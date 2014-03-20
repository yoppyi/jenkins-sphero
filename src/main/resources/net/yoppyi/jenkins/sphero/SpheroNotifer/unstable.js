var spheron = require('spheron');
var sphero = spheron.sphero();
var spheroPort = process.argv[2];
var COLORS = spheron.toolbelt.COLORS;
var repeat = false;

sphero.on('open', function() {
	sphero.setRGB(COLORS.YELLOW, false);
	var delay = 500;
	var i = 1;
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.YELLOW, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.YELLOW, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.YELLOW, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.YELLOW, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.close(function() {
			// NOP
		});
	}, delay * i++);

});

sphero.open(spheroPort);
