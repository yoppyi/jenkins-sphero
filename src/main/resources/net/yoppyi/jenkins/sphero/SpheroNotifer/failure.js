var spheron = require('spheron');
var sphero = spheron.sphero();
var spheroPort = process.argv[2];
var status = process.argv[3];
// var spheroPort =
// 'BTHENUM\DEV_6886E700837A\8&304360A4&0&BLUETOOTHDEVICE_6886E700837A';
var COLORS = spheron.toolbelt.COLORS;
var repeat = false;

var success = function() {
	sphero.setRGB(COLORS.BLUE, false);
	var delay = 500;
	var i = 1;
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLACK, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.setRGB(COLORS.BLUE, false);
	}, delay * i++);
	setTimeout(function() {
		sphero.close(function() {
			// NOP
		});
	}, delay * i++);
};

var fail = function() {
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
};

sphero.on('open', function() {
	if (status == 'SUCCESS') {
		success();
	} else {
		fail();
	}

});

sphero.open(spheroPort);
