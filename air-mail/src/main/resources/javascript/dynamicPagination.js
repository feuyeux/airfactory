/*
 * author: feuyeux@gmail.com
 * create time: 12th Dec 2011
 * reference: 
 * http://en.wikipedia.org/wiki/Display_resolution
 * http://www.w3school.com.cn/htmldom/dom_obj_screen.asp
 */

//var SCREENWIDTH = screen.width;
//var SCREENHEIGHT = screen.height;
var browserWidth = jQuery(document.body).width();
var browserHeight = jQuery(document.body).height();

var SIZE1 = '1';
var SIZE2 = '2';
var SIZE3 = '3';
var SIZE4 = '4';
var SIZE5 = '5';

function isXGA() {
	return (browserWidth == 1024 && browserHeight == 768);
}
function isXGAPlus() {
	return (browserWidth == 1152 && browserHeight == 864);
}
function isWXGA() {
	return (browserWidth == 1280 && browserHeight == 720);
}
function isWXGA2() {
	return (browserWidth == 1280 && browserHeight == 800);
}
function isSXGA() {
	return (browserWidth == 1280 && browserHeight == 960);
}
function isSXGAPlus() {
	return (browserWidth == 1280 && browserHeight == 1024);
}
function isHD1() {
	return (browserWidth == 1360 && browserHeight == 768);
}
function isHD2() {
	return (browserWidth == 1366 && browserHeight == 768);
}
function isWXGAPlus() {
	return (browserWidth == 1440 && browserHeight == 900);
}
function isHDPlus() {
	return (browserWidth == 1600 && browserHeight == 900);
}
function isUXGA() {
	return (browserWidth == 1600 && browserHeight == 1200);
}
function isWSXGAPlus() {
	return (browserWidth == 1680 && browserHeight == 1050);
}
function isFHD() {
	return (browserWidth == 1920 && browserHeight == 1080);
}
function isWUXGAPlus() {
	return (browserWidth == 1920 && browserHeight == 1200);
}
function isWQHD() {
	return (browserWidth == 2560 && browserHeight == 1440);
}

function getAlternativeType(){
	if(browserHeight>=1200)
		return SIZE5;
	if(browserHeight>=900)
		return SIZE4;
	if(browserHeight>=850)
		return SIZE3;
	if(browserHeight>=800)
		return SIZE2;
	return SIZE1;
}

function getPageSize() {
	var type = getDisplayResolutionType();
	if (SIZE1 == type)
		return 20;
	if (SIZE2 == type)
		return 25;
	if (SIZE3 == type)
		return 30;
	if (SIZE4 == type)
		return 35;
	if (SIZE5 == type)
		return 40;
}

function getDisplayResolutionType() {
	if (isFHD()) {
		return SIZE4;
	}
	if (isWSXGAPlus()) {
		return SIZE4;
	}
	if (isSXGAPlus()) {
		return SIZE4;
	}
	if (isWXGAPlus()) {
		return SIZE3;
	}
	if (isWUXGAPlus()) {
		return SIZE5;
	}
	if (isHD2()) {
		return SIZE1;
	}
	if (isWXGA2()) {
		return SIZE2;
	}
	if (isXGA()) {
		return SIZE1;
	}
	if (isHDPlus()) {
		return SIZE3;
	}
	if (isHD1()) {
		return SIZE1;
	}
	if (isUXGA()) {
		return SIZE4;
	}
	if (isSXGA()) {
		return SIZE3;
	}
	if (isXGAPlus()) {
		return SIZE2;
	}
	if (isWQHD()) {
		return SIZE5;
	}
	if (isWXGA()) {
		return SIZE1;
	}
	return getAlternativeType();
}