// JavaScript Document
// 项目根路径
var rootPath1;
function rootPath() {
	if (rootPath1) {
		return rootPath1;
	}
	var webroot = document.location.href;
	webroot = webroot.substring(webroot.indexOf('//') + 2, webroot.length);
	webroot = webroot.substring(webroot.indexOf('/') + 1, webroot.length);
	webroot = webroot.substring(0, webroot.indexOf('/'));
	rootpath1 = "/" + webroot;
	if(rootpath1=="/"){
		return "";
	}else{
		return rootpath1;
	}
};