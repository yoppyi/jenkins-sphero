var spheron = require('spheron');
var sphero = spheron.sphero();
var spheroPort = process.argv[2];
var COLORS = spheron.toolbelt.COLORS;

sphero.on('open', function() {
	sphero.roll(100, 100, 100, 100);
	sphero.setRGB(COLORS.RED, false);
	var delay = 500;
	var i = 1;
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.RED, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.RED, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.RED, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.RED, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.roll(0, 0, 0, 0);
		sphero.close(function() {
			// NOP
		});
	}, delay * i++);
});

sphero.open(spheroPort);
