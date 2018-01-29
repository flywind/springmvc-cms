/* 是否启用调试模式 */
var DEBUGGER = false;
var Debug = Debug || {};

/* 控制台日志 */
Debug.log = function(msg) {
    var params = getParams();
    if (params && !params.debug && !DEBUGGER) {
        return;
    }

    if (window.console) {
        console.log(msg);
    }
};

//获取参数
var configScript = document.getElementById('jsPath');
var params = getParams(configScript.src);
var localJsPath = configScript.getAttribute('data-jsPath');
var langKey = localStorage.getItem('languageKey') || 'zh_CN';
var lang = 'zh_CN';
if(langKey === 'zh_CN' || langKey === 'en_US' || langKey === 'ru_RU'){
	lang = langKey;
}

//require.js 配置文件
var require = {
    baseUrl: localJsPath,
    waitSeconds: 0,
    paths: {
        //JS库
        'jquery': 'jquery-1.11.0.min',
        'bootstrap' : 'bootstrap.min',
    	"nanoScroller" : "libs/nanoScrollerJS/nanoScrollerJS.min",
		"menu" : "libs/metismenu/metismenu.min",
		"mresize" : "libs/resizeEnd/resizeEnd.min",
		"t5admin" : "t5admin",
		'common' : 'libs/common',
		'easyui_o': 'base/easyui/jquery.easyui.min',
        'detailview': 'base/easyui/datagrid-detailview',
        'easyui': 'base/easyui/locale/easyui-lang-' + lang,
        'validate_o': 'base/validate/jquery.validate',
        'validate': 'base/validate/locale/messages-' + lang,
        'ztree': 'base/ztree/js/jquery.ztree.all-3.5.min',
        'alertcreate': 'libs/alertcreate'
        
    },
    shim: {
    	"nanoScroller" : ["jquery"],
    	"bootstrap" : ["jquery"],
		"menu" : ["jquery"],
		"mresize" : ["jquery"],
		"t5admin" : ["jquery"],
		'easyui_o': ['jquery'],
		'easyui': ['easyui_o'],
		'validate_o': ['jquery'],
        'validate': ['validate_o'],
        'ztree': ['jquery'],
    }
};
//返回一个url字符串的参数对象
function getParams(str) {
    str = str || location.href;

    if (typeof str !== 'string') {
        return null;
    }

    var search = str.substr(str.indexOf('?') + 1);
    var params = {};

    if (search) {
        var paramArray = search.split('&');
        for (var i = 0; i < paramArray.length; i++) {
            params[paramArray[i].split('=')[0]] = paramArray[i].substr(paramArray[i].indexOf('=') + 1);
        }
    }

    return params;
}