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
        'jquery': 'jquery-1.8.0.min',
		'common': 'base/common',
		'logindata': 'base/logindata',
		'dialog': 'base/dialog',
        'tips': 'base/tips',
        'json': 'base/json',
        'lightbox': 'base/lightbox/lightbox',
        'inputui': 'base/inputui',
        'easyui_o': 'base/easyui/jquery.easyui.min',
        'detailview': 'base/easyui/datagrid-detailview',
        'easyui': 'base/easyui/locale/easyui-lang-' + lang,
        'industry': 'base/industry',
        'baseaddresscontroller': 'base/addresscontroller',
        'addresscontroller': 'base/addresscontroller',
        'addressdata': 'base/addressdata',
        'pagecontroller': 'base/pagecontroller',
        'modulebuildcontroller': 'base/modulebuildcontroller',
        'searchArgForm': 'base/searchargform',
        'formui': 'base/formui',
        'plugs': 'base/plugs',
        'rule': 'base/rule',
        'oldlayoutsize': 'base/layoutsize',
        'layoutsize': 'base/layoutsize',
        'ajaxfileupload': 'base/jquery.ajaxfileupload',
        'validate_o': 'base/validate/jquery.validate',
        'validate': 'base/validate/locale/messages-' + lang,
        'autocomplete': 'base/autocomplete/jquery.autocomplete',
        'datetimepicker': 'base/datetimepicker/jquery.datetimepicker',
        'comboTree': 'base/comboTree/comboTree'
        
    },
    shim: {
    	'easyui_o': ['jquery'],
		'easyui': ['easyui_o'],
		'ajaxfileupload': ['jquery'],
		'validate_o': ['jquery'],
        'validate': ['validate_o'],
        'rule': ['validate'],
        'plugs': ['jquery', 'common'],
        'autocomplete': ['jquery'],
        'bootstrap': ['jquery'],
        'searchArgForm': ['jquery'],
        'datetimepicker': ['jquery']
    }
};

document.write('<script src="'+localJsPath+'base/locale/lang-'+langKey+'.js"></script>');
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