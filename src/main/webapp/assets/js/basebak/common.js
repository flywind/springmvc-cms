(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'logindata', 'dialog', 'tips', 'json', 'inputui', 'easyui', 'detailview', 'autocomplete', 'lightbox'], factory);
    } else {
        window.Common = factory(jQuery, LoginData, Dialog, Tips, JSON, InputUI, undefined, detailview, undefined, Lightbox);
    }
}(function($, LoginData, Dialog, Tips, JSON, InputUI, undefined, detailview, undefined, Lightbox) {

    var Common = {};
    var loginData = new LoginData();

    /* 判断IE浏览器 */
    Common.isIE = function() {
        if (!!window.ActiveXObject || "ActiveXObject" in window) {
            return true;
        } else {
            return false;
        }
    };
    
    /* 判断IE8浏览器 */
    Common.isIE8 = function() {
    	var isIE = Common.isIE();
    	if (!isIE) {
    		return false;
    	}
    	  
    	return navigator.appVersion.split(";")[1].replace(/[ ]/g,"") == "MSIE8.0";
    };
    
    /* 判断ie9以下浏览器 */
    Common.isIE9Minus = function(){
    	var isIE = Common.isIE();
        if (!isIE) {
            return false;
        }
        var msieVersion = navigator.appVersion.split(";")[1].replace(/[ ]/g,"");
        if(msieVersion === "MSIE6.0" || msieVersion === "MSIE7.0" || msieVersion === "MSIE8.0" || msieVersion === "MSIE9.0"){
            return true;
        }
    };

    /* 多语言处理 */
    Common.langHandler = function(value, defaultText) {
        return Lang && Lang[value] ? Lang[value] : defaultText || value;
    };

    /* 跳转 */
    Common.jump = function(url, target) {
        if (!url) {
            return;
        }

        if (target) {
            window.open(url, target);
        } else {
            location.href = url;
        }
    };
    
    /* 模拟for循环 */
    Common.foreach = function(i, list, callback, time){
    	var time = time || 50;
    	var type = $.type(list);
    	if(type !== 'array' && type !== 'number'){
    		return;
    	}
    	
    	var length = type === 'array' ? list.length : list;
    	var timeId = 0;
    	var i = i || 0;
    	setTimeout(function(){
    		if(i >= length){
    			clearTimeout(timeId);
    			return;
    		}
    		var flag = true;
    		if(callback && typeof callback === 'function'){
    			if(type === 'array'){
    				flag = callback(i, list[i]);
    			}else{
    				flag = callback(i);
    			}
    		}
    		if(flag === false){
    			clearTimeout(timeId);
    			return;
    		}
    		i++;
    		timeId = setTimeout(arguments.callee, time);
    	}, 0);
    };

    /* 数组消重 */
    Common.unique = function(arry) {
        var result = [];
        var temp = {};

        for (var i = 0; i < arry.length; i++) {
            if (!temp[arry[i]]) {
                result.push(arry[i]);
                temp[arry[i]] = 1;
            }
        }

        return result;
    };

    /* input输入框只允许输入数字 */
    Common.eventInputWriteNum = function(obj) {
        var key = obj.which;

        if (key >= 48 && key <= 57) {
            return true;
        } else {
            return false;
        }
    };

    /* treegrid重新统计total */
    Common.showPagerNumb = function(rowNum) {
        if (rowNum == 0) {
            window.setTimeout(function() {
                $(".pagination-info").text("显示0到0,共0记录");
            }, 2);
        }
    };

    /* 生成随机数 */
    Common.randomCode = function(type, num) {
        type = type || 1;
        num = num || 1;
        var letterAndNumberArray = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        var randomCode = '';

        for (var i = 0; i < num; i++) {
            /* 数字随机 */
            if (type == 1) {
                randomCode += letterAndNumberArray[Common.selectFrom(0, 9)];
                continue;
            }
            /* 字母随机 */
            if (type == 2) {
                randomCode += letterAndNumberArray[Common.selectFrom(10, 35)];
                continue;
            }
            /* 3表示数据与字母随机 */
            if (type == 3) {
                randomCode += letterAndNumberArray[Common.selectFrom(0, 35)];
            }
        }

        return randomCode;
    };

    /* 指定范围的随机数 */
    Common.selectFrom = function(lowerValue, upperValue) {
        var choices = upperValue - lowerValue + 1;
        return Math.floor(Math.random() * choices + lowerValue);
    };

    //根据menuNO判断是否有存在于menulist  ,挤出机特有
    Common.inMenuListByMenuNO = function(menuNO) {
        var loginData2 = window.loginData || (new LoginData());
        var menuList = loginData2.getMenuList();
        var flag = false;

        if (!menuList) {
            return;
        }

        $.each(
            menuList,
            function(i, v) {
                if (v.menuNO === menuNO * 1) {
                    flag = true;
                    return false;
                }
            }
        );

        return flag;
    };
    
    //根据文件名获取当前文件所在目录的路径
	Common.getCurrentPath = function(fileName) {
        var currentScript = document.currentScript;
        var currentScriptSrc = '';
        if (currentScript) {
            currentScriptSrc = currentScript.src;
            return currentScriptSrc.substring(0, currentScriptSrc.lastIndexOf("/") + 1);
        }

        var scripts = document.getElementsByTagName('script');
        for (var i = 0; i < scripts.length; i++) {
            currentScriptSrc = scripts[i].src;
            if (!currentScriptSrc) {
                continue;
            }
            var file = currentScriptSrc.split("/")[currentScriptSrc.split('/').length - 1];
            var fName = fileName.substring(0, fileName.lastIndexOf("."));
            var extension = fileName.substring(fileName.lastIndexOf("."));
            var reg = new RegExp('(' + fileName + ')|('+ fName + '_(.+)' + extension + ')', 'g');
            if (file.search(reg) !== -1) {
                return currentScriptSrc.substring(0, currentScriptSrc.lastIndexOf("/") + 1);
                break;
            }
        }

        return "";
    };

    /******
    获得?号后面的参数并以一组参数名与值的对象返回 
    eg:?id=5&userName=lantis 返回{id:5, userName:'lantis'}
    ******/
    Common.getSearch = function(str) {
        var vSearch = Common.getSearchStr(str);
        var param = {};

        if (vSearch) {
            var paramArray = vSearch.split('&');
            for (var i = 0; i < paramArray.length; i++) {
                param[paramArray[i].split('=')[0]] = Common.escapeValue(decodeURIComponent(paramArray[i].substr(paramArray[i].indexOf('=') + 1)));
            }
        }

        return param;
    };

    /* 获得?号后面的参数字符串  */
    Common.getSearchStr = function(str) {
        if (str && str.indexOf('?') !== -1) {
            return str.substr(str.indexOf('?') + 1);
        }

        return location.search.substr(1);
    };

    /* 对象转成&key=value字符串 */
    Common.paramToString = function(param) {
        if (!param) {
            return '';
        }

        var str = '';

        $.each(
            param,
            function(k, v) {
                str += '&' + k + '=' + v;
            }
        );

        return str;
    };

    /* 判断两个对象里的值是否相同 */
    Common.isSameObject = function(o1, o2) {
        for (var key in o1) {
            if (o1[key] != o2[key]) {
                return false;
            }
        }

        return true;
    };

    /* 追加参数 */
    Common.appedUrlParams = function(params, url) {
        if (!(params && url)) {
            return;
        }

        var urlParams = Common.getSearch(url);
        var paramsStr = '';

        params = $.extend(urlParams, params);
        paramsStr = Common.paramToString(params);
        paramsStr = paramsStr.substr(1);
        if (url.indexOf('?') !== -1) {
        	url = url.replace(url.substr(url.indexOf('?')), '');
        }

        return url + '?' + paramsStr;
    };

    /* 换成特殊值 */
    Common.escapeValue = function(str) {
        if (str == 'null') {
            return null;
        }

        if (str == 'undefined') {
            return undefined;
        }

        return str;
    };

    /* urlencode */
    Common.urlencode = function(str) {
        str = (str + '').toString();

        return encodeURIComponent(str).replace(/!/g, '%21').replace(/'/g, '%27').replace(/\(/g, '%28').replace(/\)/g, '%29').replace(/\*/g, '%2A').replace(/%20/g, '+');
    };

    /* 将null, 0, undefined的值替换成''-空字符串 */
    Common.getNormalValue = function(value) {
        if (value) {
            return value;
        }

        return '';
    };

    /* 通过GET获取参数值转换成数字类型 */
    Common.stringToNumber = function(string) {
        var num = string * 1;

        if (isNaN(num)) {
            return 0;
        }

        return num;
    };
    
    
    /* 格式化为金额格式 value:值、n:保留小数位*/
    Common.formatMoney = function(value,n) {
       if(!value){
    	   return '';
       }
       value = Common.stringToNumber(value);
       n = n > 0 && n <= 20 ? n : 2;   
       value = parseFloat((value + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = value.split(".")[0].split("").reverse(),   
	   r = value.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ ){   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
    };

    /* 截取采集设备码 */
    Common.formatRegCode = function(regCode) {
        if (!regCode) {
            return '';
        }

        regCode = regCode.substr(9);

        var newRegCode = regCode.substr(regCode.length - 9);

        if (newRegCode == "000000000") {
            regCode = regCode.substring(0, regCode.length - 9);
        } else {
            regCode = regCode.substring(0, 16);
        }

        return regCode;
    }

    /* 字符串转JSON对象 */
    Common.stringToJSON = function(data) {
    	if (!data || typeof data !== 'string') {
    		return data;
    	}
        try {
            data = JSON.parse(data);
        } catch (e) {
        	data = eval('(' + data + ')');//处理字符串中有逗号的情况
        }

        return data;
    };

    /* JSON对象转字符串 */
    Common.JSONToString = function(data) {
        try {
            data = JSON.stringify(data);
        } catch (e) {
            data = data;
        }

        return data;
    };

    /* 数字转16进制 */
    Common.dec2hex = function(num) {
        return Number(num).toString(16);
    };

    /* 数字转字符串ascii */
    Common.dec2string = function(num) {
        return Common.hex2String(Common.dec2hex(num));
    };

    /* 16进制转数字 */
    Common.hex2dec = function(string) {
        if (string == '') {
            return '';
        }

        return parseInt(string, 16);
    };

    /* 16进制转字符串ascii */
    Common.hex2string = function(str) {
        var val = "";
        var arr = str.split(",");

        if (arr.length == 0) {
            return str;
        }

        for (var i = 0; i < arr.length; i++) {
            val += arr[i].fromCharCode(i);
        }

        return val;
    };

    /* 字符串ascii转16进制 */
    Common.string2hex = function(str) {
        var byteCount = 0;

        for (i = 0; i < str.length; i++) {
            byteCount = str.charCodeAt(i);
            if (byteCount.length == 1) {
                byteCount = "0" + byteCount;
            }
            byteCount += byteCount.toString(16).toUpperCase();
        }

        return byteCount;
    };

    /* 字符串ascii转10进制 */
    Common.string2dec = function(str) {
        return Common.hex2dec(Common.string2hex(str));
    };

    /* 字符补零 */
    Common.fillZero = function(str, len) {
        if (!str) {
            return;
        }

        if (!len) {
            return str;
        }

        var strLen = str.length;

        if (strLen < len * 2) {
            for (var i = 0; i < len * 2 - strLen; i++) {
                str = '0' + str;
            }
        }

        return str;
    };

    /*****
    根据参数创建隐藏域字段
    fields:需要创建的字段名称及值的一组对象
    parentElement:需要添加到的父级元素，默认值：ID为'J_PostForm'的元素
    eg:Common.addHiddenField({'companyID': 28, 'userName':'lantis'}, $('#J_PostForm'))
    则会在页面ID为'J_PostForm'的元素里创建两个隐藏域:
    <input type="hidden" name="companyID" id="companyID" value="28" />
    <input type="hidden" name="userName" id="userName" value="lantis" />
    *****/
    Common.addHiddenField = function(fields, parentElement) {
        if (!fields) {
            return;
        }

        parentElement = parentElement || $('#J_PostForm');

        var i = 0;
        var length = fields.length;
        var hiddenFieldsName = Common.getHiddenFields(parentElement);

        for (var key in fields) {
            if ($.inArray(key, hiddenFieldsName) == -1) {
                $('<input type="hidden" name="' + key + '" id="' + key + '" value="' + fields[key] + '" />').appendTo(parentElement);
            } else {
                $('#' + key).val(fields[key]);
            }
        }
    };

    /* 获取某个元素下所有隐藏域的name并以数组返回 */
    Common.getHiddenFields = function(parentElement) {
        parentElement = parentElement || $(document.body);
        var hiddenFields = parentElement.find('input[type="hidden"]');
        var length = hiddenFields.length;
        var i = 0;
        var result = [];

        if (!length) {
            return result;
        }

        for (; i < length; i++) {
            result.push($(hiddenFields[i]).attr('name'));
        }

        return result;
    };

    /* 根据参数删除隐藏域字段 */
    Common.removeHiddenField = function(fields, parentElement) {
        if (!fields) {
            return;
        }

        parentElement = parentElement || $('#J_PostForm');
        var i = 0;
        var length = fields.length;
        var hiddenFieldsName = Common.getHiddenFields(parentElement);

        for (var key in fields) {
            if ($.inArray(key, hiddenFieldsName) != -1) {
                $('#' + key).remove();
            }
        }
    };

    /* 添加loadingBox */
    Common.addLoadingBox = function(message, parentElement) {
        message = message || Common.langHandler('loading', "正在加载，请稍候...");
        parentElement = parentElement || $(document.body);

        var $loadingBox = $('<div class="loading-box"><div class="loading-mask"></div><div class="loading">' + message + '</div></div>');

        parentElement.append($loadingBox);

        return $loadingBox;
    };

    /* 移除loadingBox */
    Common.removeLoadingBox = function(target) {
        target.fadeOut(500, function() {
            target.remove();
        });
    };

    /* 全屏 */
    Common.launchFullScreen = function(element) {
        if (element.requestFullScreen) {
            element.requestFullScreen();
        } else if (element.mozRequestFullScreen) {
            element.mozRequestFullScreen();
        } else if (element.webkitRequestFullScreen) {
            element.webkitRequestFullScreen();
        }
    };

    /* 解决在ie8下 new Date(time) 得不到日期的问题 */
    Common.getNewDateOfFormatter = function(dateTime) {
        var strSeparator = "-";
        var oDate = dateTime.split(strSeparator);
        var nDate = new Date(oDate[0], oDate[1] - 1, oDate[2]);

        return nDate;
    };


    /* 将数组里的值全部转成数字类型 */
    Common.stringToNumberInArray = function(array) {
        var result = [];
        var i = 0;
        var len = array.length;

        if (len == 0) return result;

        for (; i < len; i++) {
            result.push(array[i] * 1);
        }

        return result;
    };

    /* 重复执行函数setIntervalTimeout */
    Common.addInterval = function(fun, time, params) {
        return setTimeout(function() {
            fun(params);
            setTimeout(arguments.callee, time);
        }, time);
    };

    Common.delInterval = function(id) {
        clearTimeout(id);
    };

    /* 重复执行函数setInterval */
    Common.setInterval = function(fun, time, params) {
        return setInterval(function() {
            fun(params);
        }, time);
    };

    Common.clearInterval = function(id) {
        clearInterval(id);
    };

    /* 间隔执行函数setTimeout */
    Common.addTimeout = function(fun, time, params) {
        return setTimeout(function() {
            fun(params);
        }, time);
    };

    Common.delTimeout = function(id) {
        clearTimeout(id);
    };

    /* 获取去掉..的URL，用于需要加绝对路径的URL,例如：http://xxx.com/../upkeep/xxx */
    Common.getUrlOfTrimPoint = function(url) {
        return url.replace('\.\.', '');
    };

    /* 拼接URL地址 */
    Common.pieceUrl = function(url, domain) {
        if (!url) {
            return;
        }

        if (domain) {
            url = domain + url;
        }

        var hasParam = url.indexOf('?') != -1;

        url += (hasParam ? '&' : '?') + 'sid=' + loginData.getSID();

        return url;
    };

    /* 将字符串转成小写，不同单词间用-替换 */
    Common.getTransformClassName = function(str) {
        if (!str) {
            return '';
        }

        return str.replace(/([A-Z])/g, '-$1').toLowerCase().substr(1);
    };

    /* 联系人/手机拼凑,主要用于datagrid中formatter格式化字段 */
    Common.pieceContacts = function(value) {
        if (!value) {
            return false;
        }

        var html = '';
        var splitCode = '<em class="split">/</em>';
        var firstValue = '';
        var secondValue = '';

        html += '<ul class="list-unstyled">';

        for (var i = 0; i < arguments.length; i++) {
            firstValue = arguments[i][0] ? arguments[i][0] : '';
            secondValue = arguments[i][1] ? arguments[i][1] : '';
            if (!(firstValue || secondValue)) {
                continue;
            }

            firstValue = firstValue ? firstValue : '-';
            secondValue = secondValue ? secondValue : '-';

            html += '<li class="contacts-list-item">';
            html += '<span class="contacts-name">' + firstValue + splitCode + '</span>';
            html += '<span class="contacts-tel">' + secondValue + '</span>';
            html += '</li>';
        }

        html += '</ul>';

        return html;
    };

    /* 判断对象里的值是否全部为空 */
    Common.isEmptyByObjectValue = function(obj) {
        if (!obj && typeof obj != 'object') {
            return false;
        }

        var result = true;

        for (var i in obj) {
            if (obj[i] != '') {
                result = false;
                break;
            }
        }

        return result;
    };

    /* 处理errorCode */
    Common.errorHandler = function(code) {
        if (code === '404') {
            Common.simpleDialog(Common.langHandler('requestedAddressWasNotFound', '请求的地址未找到！'));
        } else if (code === '500') {
            Common.simpleDialog(Common.langHandler('serverError', '服务器错误!'));
        } else if (code === 'noSession') {
        	var noSessionOpend = window.noSessionOpend || false;
        	if(noSessionOpend){
        		return;
        	}
        	Common.noSessionJumpLoginPage();
            window.noSessionOpend = true;
        } else if (code === 'noSecurity') {
            Common.simpleDialog(Common.langHandler('permissionDenied', '没有权限！'));
        }
    };
    
    /* 未登录或登录超时，跳转到登录页 */
    Common.noSessionJumpLoginPage = function(loginUrl, msg){
    	var loginUrl = loginUrl || loginData.getLoginUrl();
    	var loginTimeout = msg || Common.langHandler('loginTimeout', '未登录或登录超时，请重新登录！');
    	var dialog = new Dialog({
    		title:Common.langHandler('warmTips', '温馨提示'),
			width:200,
			autoOpen: true,
			buttons: [
				{text:Common.langHandler('buttonConfirm', '确定'), click:function(e){
					e.close();
				}, styleName:'btn-success'}
			],
			onClose: function(e){
				location.href = loginUrl || '/sys/login.jsp';
				clearTimeout(timeId);
			},
	        content: loginTimeout
    	});
    	
    	var $tips = dialog.getDialogElement().find('.dialog-tips');
    	var s = 5;
    	$tips.html('<span data-time="true" class="remind-logout-sp">' + s + '</span>秒后，自动跳转到登录页。');
	    setTimeout(function() {
	        if (s <= 0) {
	        	clearTimeout(timeId);
	        	location.href = loginUrl || '/sys/login.jsp';
	            return;
	        }
	        if ($tips.find('[data-time="true"]').length > 0) {
	        	$tips.find('[data-time="true"]').html(s);
	        }
	        s --;
	        timeId = setTimeout(arguments.callee, 1000);
	    }, 0);
    	
    };

    /* 判断一个URL是否和当前域相同 */
    Common.isSameDomain = function(url) {
        var protocol = location.protocol + '//';
        hasProtocol = url.indexOf(protocol) != -1;

        if (!hasProtocol) {
            return true;
        }

        var isExist = url.indexOf(location.host) != -1;

        if (isExist) {
            return true;
        }

        return false;
    };

    /* 同域同步请求，成功返回包含fields字段的数据 */
    Common.getDataByfields = function(url, postData, fields) {
        postData = postData ? $.extend(postData, {
            'sid': window.loginData ? loginData.getSID() : ''
        }) : postData;

        var result = null;
        var setting = {
            url: url,
            data: postData,
            type: 'POST',
            dataType: 'json',
            async: false,
            success: function(data) {
                if (!data) {
                    return;
                }

                data = Common.stringToJSON(data);

                if (data.errorCode) {
                    Common.errorHandler(data.errorCode);
                    return;
                }

                if (fields == null) {
                    result = data;
                    return;
                }

                if ($.isArray(fields)) {
                    for (var i = 0; i < fields.length; i++) {
                        result[fields[i]] = data[fields[i]];
                    }
                    return;
                }

                result = data[fields];
            },
            error: function() {

            }
        };

        $.ajax(setting);

        return result;
    };

    /* 同域异步请求，成功后执行callback函数 */
    Common.sameDomainRequest = function(url, postData, callback) {
        var setting = {
            url: url,
            data: postData,
            type: 'POST',
            dataType: 'json',
            success: function(data) {
                if (!data) {
                    callback(data);
                    return;
                }

                data = Common.stringToJSON(data);

                if (data.errorCode) {
                    Common.errorHandler(data.errorCode);
                    return;
                }

                callback(data);
            }
        };

        $.ajax(setting);
    };

    /* 跨域请求，成功后执行callback函数 */
    Common.crossDomainRequest = function(url, postData, callback) {
        var setting = {
            url: url,
            data: postData,
            type: 'GET',
            dataType: 'jsonp',
            jsonp: 'callback',
            jsonpCallback: 'jsonpCallback',
            success: function(data) {
                if (!data) {
                    callback(data);
                    return;
                }

                data = Common.stringToJSON(data);

                if (data.errorCode) {
                    Common.errorHandler(data.errorCode);
                    return;
                }

                callback(data);
            }
        };

        $.ajax(setting);
    };

    /****** 
    根据某个字段获取值
    url:请求的地址
    postData:接受的参数
    processResult:如果为null、字符串、数组类型，则返回需要返回的字段名称，默认值:null，返回所有字段数据，单个字段可使用字符串作为参数，多个可使用数组作为参数，如果为function类型则异步请求成功执行回调函数
     ******/
    Common.getDataByAjax = function(url, postData, processResult) {
        if (!url) {
            return null;
        }

        url = Common.pieceUrl(url);

        var dataType = typeof processResult;

        if (dataType == 'string' || processResult == null || $.isArray(processResult)) {
            return Common.getDataByfields(url, postData, processResult);
        }

        if (dataType == 'function' && !Common.isSameDomain(url)) {
            return Common.crossDomainRequest(url, postData, processResult);
        }

        return Common.sameDomainRequest(url, postData, processResult);

    };

    /* 
    请求Action并跳转 
    一键生成保养单
    一键发送
    强制签出
    */
    Common.queryAction = function(queryURL, parms, jumpURL) {
        if (!queryURL) {
            return false;
        }

        Common.getDataByAjax(queryURL, parms, callback);

        function callback(data) {
            Common.requestCode(data, function() {
                if (!jumpURL) return;

                switch (data['RetCode']) {
                    case 0:
                        break;
                    case 1:
                        if (typeof callback == 'function') callback();
                        location.href = jumpURL;
                        break;
                }
            });
        }
    };

    /******
    取一组对象里的不重复的值并以数组类型返回
    data:JSON数据源
    field:需要操作的字段名称
    ******/
    Common.getItemsToArray = function(data, field) {
        if (!data) {
            return false;
        }

        var temp = {};
        var result = [];

        $.each(
            data,
            function(index, value) {
                if (!temp[value[field]]) {
                    result.push(value[field]);
                    temp[value[field]] = 1;
                }
            }
        );

        return result;
    };

    /****** 
    时间格式化函数
    dateTime:字符串
    format:需要返回的格式有('y-m-d', 'y-m-d h:m:s', 'y-m-d h:m', 'y/m/d', 'y/m/d h:m:s', 'y/m/d h:m')，默认值为：y-m-d h:m:s
    ******/
    Common.getTransformTime = function(dateTime, format) {
        if (!dateTime) {
            return '';
        }

        var date = 0;

        if (typeof dateTime == 'string' && dateTime.indexOf('Date') != -1) {
            date = new Date(parseInt(dateTime.replace("/Date(", "").replace(")/", ""), 10));
        } else {
            date = new Date(dateTime);
        }

        if (date < 0) {
            return '';
        }

        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var min = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        var formatObject = ['y-m-d', 'y-m-d h:m:s', 'y-m-d h:m', 'y/m/d', 'y/m/d h:m:s', 'y/m/d h:m', 'y-m', 'y/m', 'y'];
        var i = 0;
        var length = formatObject.length;
        var index = 1;

        if ($.inArray(format, formatObject) != -1) {
            index = $.inArray(format, formatObject);
        }

        switch (index) {
            case 0:
                return year + '-' + month + '-' + day;
                break;
            case 1:
                return year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + sec;
                break;
            case 2:
                return year + '-' + month + '-' + day + ' ' + hour + ':' + min;
                break;
            case 3:
                return year + '/' + month + '/' + day;
                break;
            case 4:
                return year + '/' + month + '/' + day + ' ' + hour + ':' + min + ':' + sec;
                break;
            case 5:
                return year + '/' + month + '/' + day + ' ' + hour + ':' + min;
                break;
            case 6:
                return year + '-' + month;
                break;
            case 7:
                return year + '/' + month;
                break;
            case 8:
                return year;
        }
    };

    /* 填充数据到元素
       data:数据源
       specialItems:要通过转换的对象数组：[{'feildName': 'installDate', 'functionName': Common.getTransformTime, 'param': 'y-m-d'}, , ,]
     */
    Common.fillValueToElement = function(data, specialItems, parentElement) {
        if (!data) {
            return;
        }

        parentElement = parentElement || $(document.body);
        var element = null;
        var $element = null;
        var $tagName = '';

        $.each(
            data,
            function(name, value) {
                $element = parentElement.find('[name="' + name + '"]');

                if ($element.length == 0) {
                    return;
                }

                element = $element[0];

                $tagName = element.tagName;
                $tagType = element.type;
                value = Common.Decode(getSpecialItemsResult(name, value));
                isPlaceholder = element.getAttribute('placeholder');

                if ($tagName == 'INPUT') {
                    if ($tagType === 'text' || $tagType === 'hidden') {
                        if ((value || value == 0) && isPlaceholder) {

                            var placehodlerInputs = InputUI.prototype.placehodlerInputs;

                            if (placehodlerInputs[name] == element) {
                                InputUI.prototype.removePlaceholder(element);
                            }

                        }

                        $element.val(value);
                        return;
                    }

                    if ($tagType == 'radio') {
                        $element.filter('[value="' + value + '"]').prop('checked', true);
                        return;
                    }

                    if ($tagType == 'checkbox') {
                        var valueList = $.type(value) === 'string' ? value.split(',') : value;
                        if(typeof valueList === "array"){
	                    	 $.each(valueList, function(i, v) {
	                             $element.filter('[value="' + value + '"]').prop('checked', true);
	                         });
                    	}else{
                        	 $element.filter('[value="' + value + '"]').prop('checked', true);
                    	}
                        return;
                    }

                }

                if ($tagName == 'SPAN' || $tagName == 'DIV') {
                    $element.html(value);
                    $element.attr('title', $('<div>' + value + '</div>').text());
                    // 此处一开始采用$element.text()，出现了给title重复添加的问题，为了兼容以前的value可能为html字符串加值的情况，采用强制拼接为html字符串再取text的形式
                    return;
                }

                if ($tagName == 'TEXTAREA') {
                    $element.val(value);
                    return;
                }

                if ($tagName == 'SELECT') {
                    $element.val(value);
                    return;
                }

            }
        );

        /* 针对需要转换的项的特殊处理 */
        function getSpecialItemsResult(name, value) {
            if (!specialItems) {
                return value;
            }

            $.each(
                specialItems,
                function(index, obj) {
                    var feildName = obj['feildName'];
                    if (name == feildName) {
                        var functionName = obj['functionName'];
                        var param = obj['param'];

                        if (param) {
                            value = functionName(value, param);
                            return false;
                        }

                        value = functionName(value);
                    }
                }
            );

            return value;
        }
    };

    /* 清除值 */
    Common.clearValueToElement = function(data, parentElement) {
        if (!data) {
            return;
        }

        parentElement = parentElement || $(document.body);
        var $element = null;
        var $tagName = '';

        $.each(
            data,
            function(name, value) {
                $element = parentElement.find('[name="' + name + '"]');

                if ($element.length == 0) {
                    return true;
                }

                $tagName = $element[0].tagName;
                $tagType = $element[0].type;

                if ($tagName == 'INPUT' || $tagName == 'TEXTAREA' || $tagName == 'SELECT') {
                    if ($tagType != 'file') {
                        $element.val('');
                    }

                    return true;
                }

                if ($tagName == 'SPAN' || $tagName == 'DIV') {
                    $element.html('');
                    $element.attr('title', '');
                    return true;
                }

            }
        );
    };
    
    /* 根据某个选择顺的对象清数据 */
    Common.clearValueBySelector = function(selector, parentElement){
    	if(!selector){
    		return;
    	}
    	var $parentElement = $(parentElement) || $(document.body);
    	$parentElement.find(selector).each(function(i, v){
    		var $tagName = this.tagName;
    		var $tagType = this.type;
    		var $element = $(this);
    		if ($tagName === 'INPUT' || $tagName === 'TEXTAREA' || $tagName === 'SELECT') {
                if ($tagType !== 'file') {
                    $element.val('');
                }
                return true;
            }

            if ($tagName === 'SPAN' || $tagName === 'DIV') {
                $element.html('');
                $element.attr('title', '');
                return true;
            }
    	});
    };

    /* 对语言包里的变量进行处理 */
    Common.getTextOfFormatter = function(langName, obj) {
        var str = langName ? Lang[langName] : '';

        if (!(str && obj)) {
            return '';
        }

        var reg = /\{(\$[0-9]+)\}/g;
        var resultArray = str.match(reg);

        if (!resultArray) {
            return str;
        }

        $.each(
            resultArray,
            function(index, value) {
                $n = value.replace(reg, '$1');
                str = str.replace(value, obj[$n]);
            }
        );

        return str;
    };

    /* datagrid */
    Common.datagrid = function(settingNew, element) {
        element = element || $('#J_DataGrid');
        var setting = {
            border: false,
            rownumbers: true,
            fit: true,
            fitColumns: true,
            singleSelect: true,
            striped: true,
            view: emptyView,
            pagination: true,
            pageNumber: 1,
            pageSize: 20,
            onLoadError: Common.dataGridError
        };

        var opts = $.extend(true, {}, setting, settingNew);

        opts.url = Common.pieceUrl(opts.url);



        element.datagrid(opts);

        //创建自定义显示列
        if (opts.showColumns) {
            Common.buildCustomColumnBar(opts.columns[0], element, opts.showColumnsStr);
        }

        /* 解决浏览器缩放datagrid不会自动适应宽度的BUG */
        /*$(window).resize(function() {
            if (element.length > 0) {
                element.datagrid('resize', {
                    width: $('#J_BoxBody').width() - 20
                });
            }
        });*/

    };
    /*根据自定义列的checkbox来显示或者隐藏所对应的列*/
    function showColumnsByCustomerColumnBar(columnsBar, element) {
        columnsBar.find('.checkbox[name="field"]').each(function(i, v) {
            var functionName = '';

            if (v.checked) {
                functionName = 'showColumn';
                //checkedNum ++;
            } else {
                functionName = 'hideColumn';
                //checkedNum --;
            }

            element.datagrid(functionName, this.value);
        });
    }


    /* 创建自定义显示列 */
    Common.buildCustomColumnBar = function(cols, element, showColumnsStr) {
        var $datagridParent = element.parents('[data-datagrid-box="true"]');

        if ($datagridParent.find('.show-columns-bar').length > 0) {
            $datagridParent.find('.show-columns-bar').find('.checkbox[name="field"]').each(function(i, v) {
                var functionName = '';

                if (v.checked) {
                    functionName = 'showColumn';
                    checkedNum++;
                } else {
                    functionName = 'hideColumn';
                    checkedNum--;
                }

                element.datagrid(functionName, this.value);
            });

            showColumnsByCustomerColumnBar($datagridParent.find('.show-columns-bar'), element);

            return;
        }

        var $box = $('<div class="show-columns-bar"><i style="font-size: 14px;padding-top: 9px;" class="icon-toggle fa fa-navicon" data-show-columns-icon="true"></i><div class="show-columns-box hidden" data-show-columns-box="true"><ul class="form-multiple-list"></ul></div></div>');
        var checked = 'checked';
        var colsNum = cols.length;
        var checkedNum = colsNum;
        var colsHTML = []; //['<li class="form-multiple-item form-multiple-item-all-select"><label class="form-multiple-label text-overflow"><input id="showColumnSelectAll" class="checkbox" type="checkbox" value="showColumnSelectAll" name="showColumnSelectAll"><span class="checkbox-text">全选/全不选</span></label></li>'];

        $datagridParent.css('position', 'relative');

        $.each(
            cols,
            function(i, v) {
                if (v.checkbox) {
                    return true;
                }
                if (v.hidden) {
                    checkedNum--;
                }
                if (showColumnsStr) {
                    if (showColumnsStr.indexOf(v.field) > -1) {
                        checked = 'checked';
                    } else {
                        checked = '';
                    }
                } else {
                    checked = v.hidden ? '' : 'checked';
                }

                colsHTML.push('<li class="form-multiple-item"><label class="form-multiple-label text-overflow" title="' + v.title + '"><input class="checkbox" type="checkbox" value="' + v.field + '" name="field" ' + checked + '><span class="checkbox-text">' + v.title + '</span></label></li>');
            }
        );

        $box.find('.form-multiple-list').html(colsHTML.join('\n'));
        $box.appendTo($datagridParent);

        //打开关闭事件
        var $icon = $box.find('.icon-toggle');

        $icon.click(function(event) {
            var $colBox = $box.find('.show-columns-box');

            if ($colBox.hasClass('hidden')) {
                $colBox.removeClass('hidden');
            } else {
                $colBox.addClass('hidden');
            }
        });

        //全局document,自定义列隐藏
        $(document).on('click', function(e) {
            var $target = $(e.target);
            var $currentChild = !!$box.has($target)[0];
            var targetClass = $box.find('[data-show-columns-box="true"]').attr('class');

            if ((targetClass === 'show-columns-box' || $target.closest('.show-columns-box').length > 0) && $currentChild) {
                return;
            }

            $box.find('.show-columns-box').addClass('hidden');
        });

        //根据flag判断全选是否默全勾选
        var $selectAll = $('#showColumnSelectAll');

        if (checkedNum === colsNum) {
            $selectAll.prop('checked', true);
        }

        $selectAll.click(function(e) {
            var functionName = '';
            var $checkboxs = $box.find('.checkbox[name="field"]');

            if (this.checked) {
                $checkboxs.prop('checked', true);
                functionName = 'showColumn';
            } else {
                $checkboxs.prop('checked', false);
                functionName = 'hideColumn';
            }

            $.each($checkboxs, function(i, v) {
                element.datagrid(functionName, v.value);
            });

        });

        //checked事件
        $box.find('.checkbox[name="field"]').click(function(e) {
            var functionName = '';

            if (this.checked) {
                functionName = 'showColumn';
                checkedNum++;
            } else {
                functionName = 'hideColumn';
                checkedNum--;
            }

            if (checkedNum === colsNum) {
                $selectAll.prop('checked', true);
            } else {
                $selectAll.prop('checked', false);
            }
            
            element.datagrid('resize');
            element.datagrid(functionName, this.value);
        });

        if (showColumnsStr) {
            showColumnsByCustomerColumnBar($box, element);
        }
    };
    /* 获取自定义选项列的值 */
    Common.getSelectItemForCustomColumnBar = function(element) {
        element = $('[data-datagrid-box="true"]');
        var cols = [];

        $.each(element.find('.show-columns-bar [name="field"]:checked'), function(i, v) {
            cols.push($(v).val());
        });

        return cols.join(',');
    };

    /* 获取dadagrid 中的 sortName和sortOrder */
    Common.getDatagridSort = function(element) {
        element = element || $('[data-datagrid-box="true"]');

        var sortElement = element.find('.datagrid-sort-desc, .datagrid-sort-asc');
        var sortName = sortElement.parent().attr('field');
        var classStr = sortElement.attr('class') + " ";

        classStr = classStr.substring(classStr.indexOf('datagrid-sort-') + 14);
        classStr = classStr.substring(0, classStr.indexOf(" "));

        return {
            sortName: sortName,
            sortOrder: classStr
        };
    };

    /* 流下载处理函数 */
    Common.downloadHandler = function(url) {
        var $iframe = $('[data-download-iframe="true"]');

        if ($iframe.length > 0) {
            $iframe.attr('src', url);
            return;
        }

        $iframe = $('<iframe data-download-iframe="true" style="width:0;height:0;position:absolute;top:0;z-index:-1;border:0;" frameborder="0"></iframe>');

        $iframe.attr('src', url);

        $iframe.load = function() {
            Debug.log('完成');
        };

        $(document.body).append($iframe);
    };

    /* treegrid */
    Common.treegrid = function(settingNew, element) {
        element = element || $('[data-datagrid="true"]');
        var setting = {
            border: false,
            rownumbers: true,
            fit: true,
            fitColumns: true,
            view: emptyTreeView,
            pagination: true,
            pageNumber: 1,
            pageSize: 30,
            onLoadError: Common.dataGridError
        };

        var opts = $.extend(true, {}, setting, settingNew);

        //创建自定义显示列
        if (opts.showColumns) {
            Common.buildCustomColumnBar(opts.columns[0], element, opts.showColumnsStr);
        }

        opts.url = Common.pieceUrl(opts.url);

        element.treegrid(opts);
    };

    /*js实现倒计时关闭弹出框*/
    Common.setCountdownPrompt = function(s, element, callback) {
        var selfFun = arguments.callee;

        element.html('<span data-time="true" class="text text-left text-danger">' + s + '</span> 秒后，自动关闭.');

        setTimeout(function() {
            if (s <= 1) {
                if (callback && typeof callback === 'function') {
                    callback();
                }
                return;
            }
            if (element.find('[data-time="true"]').length > 0) {
                element.find('[data-time="true"]').html(s);
            }
            selfFun(s - 1, element, callback);
        }, 1000);
    };

    /* autocomplete */
    Common.autocomplete = function(settingNew, element, callback) {
        if (!element) {
            return;
        }

        var forceMatching = (settingNew.forceMatching || settingNew.forceMatching == 0 || settingNew.forceMatching == false) ? settingNew.forceMatching : true;
        settingNew.forceMatching = forceMatching;

        var name = element.attr('data-autocomplete-name');
        var elementLen = $('[name=' + name + ']').length == 0 ? '' : $('[name=' + name + ']').length;
        var $hiddenFeild = element.parent().find('[name="' + name + '"]');

        /* 如果强制匹配，重设一些元素 */
        if (forceMatching) {
            if ($hiddenFeild.length == 0) {
                $hiddenFeild = $('<input type="text" class="input-text opacity input-autocomplete" name="' + name + '" id="' + name + elementLen + '" value="" />');
                $hiddenFeild.insertAfter(element);
            }
            //加图标
            if (!element.prop('disabled') && !element.prop('readonly')) {
                element.addClass('input-icon-auto');
            }
        }

        var setting = {
            dataType: "json",
            minChars: 0,
            width: 250,
            matchContains: "word",
            autoFill: false,
            max: 10,
            extraParams: {
                page: 1,
                rows: 10,
                sid: loginData.getSID()
            }
        };

        var opts = $.extend(true, {}, setting, settingNew);

        element.autocomplete(opts.url, opts)
            .result(function(event, data, formatted) {
                $hiddenFeild.val(data.id);
                $hiddenFeild.parent().find('.validate-text').hide();

                if (callback && typeof callback === 'function') {
                    callback(event, data, formatted);
                }

            })
            .keyup(function(e) {
                //屏蔽这几个按键的keyup事件
                if (inKeycodes(e.which)) {
                    return;
                };
                if (!forceMatching) {
                    return;
                }
                $hiddenFeild.val("");

            })
            .blur(function(e) {
                if (!forceMatching) {
                    return;
                }
                var $thisValue = $(this).val();
                var $parentBox = element.parents('.f-fix');
                var $msgBox = $parentBox.find('.validate-text');
                if ($.trim(element.val()) === '') {
                    $hiddenFeild.val('');
                    return;
                }
                if ($hiddenFeild.val() === '') {
                    var postData = {};
                    postData[settingNew.fieldName] = $thisValue;

                    postData = $.extend(postData, settingNew.extraParams);

                    Common.getDataByAjax(settingNew.url, postData, function(res) {
                        if (res.total > 0) {
                            var id = res.rows[0].id;
                            $hiddenFeild.val(id);
                            $msgBox.hide();
                        } else {
                            if ($msgBox.length > 0) {
                                $msgBox.show();
                            } else {
                                $('<label class="validate-text error">' + Common.langHandler('autoCompleteValidate', '您输入的值不能在数据库找到，提交后系统不会保存！') + '</label>').appendTo($parentBox);
                            }
                        }
                    });
                } else {
                    $msgBox.hide();
                }
            });

        function inKeycodes(keycode) {
            var keycodes = [9, 13, 27, 38, 40]; //tab, enter, esc, up, down这五个键
            return $.inArray(keycode, keycodes) !== -1;
        }
    };
    
    //对于不使用pageController构造的删除按钮，调用此方法实现删除过程的统一
    //url:接口地址
    //postData:接口参数
    //obj:删除后处理的列表对象
    //type:列表类型：treegrid|datagrid,默认datagrid
    Common.deleteItem = function(url, postData, obj, type) {
    	if (!url || !postData || !obj) {
    		return;
    	}
    	Common.confirmDialog(Common.langHandler('msgDoYouWantToDelete', '删除后数据将无法恢复！你确定要删除吗?'), Common.langHandler('alertTitlePrompt', '提示'), function(d) {
            var $msgObj = d.getDialogElement().find('.dialog-tips');
            $msgObj.html('<span class="text"><i class="icon icon-loading"></i>' + Common.langHandler('isDeleting', '正在删除...') + '</span>');
            d.getDialogElement().find('.btn-success').addClass('disabled').attr('disabled', 'disabled');
            
            Common.getDataByAjax(url, postData, function(res){
                if (res.success) {
                    $msgObj.html('<span class="text-success"><span class="fa-stack"><i class="fa fa-iot-round fa-stack-mx green-color"></i><i class="fa fa-iot-module-online fa-stack-mx white-color"></i></span>' +  (res.msg || Common.langHandler('deleteSuccess', '删除成功！')) + '</span>');
                } else {
                    $msgObj.html('<span class="text-danger"><span class="fa-stack"><i class="fa fa-iot-round fa-stack-mx red-color"></i><i class="fa fa-iot-module-offline fa-stack-mx white-color"></i></span>' + (res.msg || Common.langHandler('deleteFailed', '删除失败！')) + '</span>');
                }
                if (type == "treegrid") {
                	obj.treegrid('reload');
                } else {
                	obj.datagrid('reload');
                }
                setTimeout(function(){
                    Common.setCountdownPrompt(1, $msgObj, function(){
                        d.close();
                    });
                }, 1000);
            });
            return false;
        });
    };
    
    

    /******
    根据ID删除对应记录，并重载数据
    url:需要请求的地址
    idName:需要删除的ID字段名
    ids:需要删除的ID，删除多个ID需要以,号分割
    obj: datagrid绑定的元素
    ******/
    Common.removeItem = function(url, idName, ids, obj, type) {
        var url = url;
        var postData = {};

        postData[idName] = ids;
        obj = obj || $('[data-datagrid="true"]');

        Common.confirmDialog(Common.langHandler('msgDoYouWantToDelete', '删除后数据将无法恢复！你确定要删除吗?'), Common.langHandler('alertTitlePrompt', '提示'), function() {
            Common.getDataByAjax(url, postData, function(res) {
                if (res.success) {
                    Common.msgDialog(Common.langHandler(res.msgKey || res.msg), Common.langHandler('alertTitlePrompt', '提示'), 'success', function() {
                        if (type == "datagrid") {
                            obj.datagrid('reload');
                        } else {
                            obj.treegrid('reload');
                        }
                    });
                } else {
                    Common.simpleDialog(Common.langHandler(res.msgKey || res.msg));
                }
            });
        });

    };



    /* 删除操作 */
    Common.remove = function(url, idsName, element, type) {
        element = element || $('[data-datagrid="true"]');
        type = type || 'datagrid';

        var row = element.datagrid("getSelected");

        if (!row) {
            Common.msgDialog(Common.langHandler('msgPleaseSelectDataFirst', '请先选择一行数据'), Common.langHandler('alertTitlePrompt', '提示'), 'warning');
            return false;
        }

        var id = row[idsName];
        Common.removeItem(url, idsName, id, element, type);
    };


    //修改操作
    Common.edit = function(url, idsName, element, target) {
        Debug.log(element);
        target = target || '_self';

        if (!idsName) {

            window.open(url, target);
            return;
        }

        element = element || $('[data-datagrid="true"]');
        isParams = url.indexOf('?') != -1;

        if (!isParams) {
            url += '?';
        } else {
            url += '&';
        }

        var row = element.datagrid("getSelected");

        if (!row) {
            Common.msgDialog(Common.langHandler('msgPleaseSelectDataFirst', '请先选择一行数据'), Common.langHandler('alertTitlePrompt', '提示'), 'warning');
            return false;
        }

        if ($.isArray(idsName)) {
            var i = 0,
                length = idsName.length,
                split = '&';
            for (; i < length; i++) {
                split = (i == 0) ? '' : '&';
                if (idsName[i].indexOf('&') != -1) {
                    url += idsName[i];
                } else {
                    url += split + idsName[i] + '=' + row[idsName[i]];
                }
            }
        } else {
            if (idsName.indexOf('&') != -1) {
                url += idsName;
            } else {
                url += idsName + '=' + row[idsName];
            }
        }

        window.open(url, target);
    };

    /* 地址字段拼凑,主要用于datagrid中formatter格式化字段 */
    Common.pieceAddress = function(value) {
        if (!value) {
            return false;
        }

        var html = '';
        var province = value.province;
        var city = value.city;
        var area = value.area;
        var address = value.address;
        var splitCode = ', ';

        html += '<address class="address">';
        html += province ? '<span class="province first">' + province + (city ? splitCode : '') + '</span>' : '';
        html += city ? '<span class="city">' + city + (area ? splitCode : '') + '</span>' : '';
        html += area ? '<span class="area">' + area + (address ? splitCode : '') + '</span>' : '';
        html += address ? '<p class="address">' + address + '</p>' : '';
        html += '</address>';

        return html;
    };

    /* 电话字段拼凑,主要用于datagrid中formatter格式化字段 */
    Common.pieceTel = function(value) {
        if (!value) {
            return false;
        }

        var html = ''
        var i = 0;
        var length = value.length;

        html += '<ul class="list-unstyled">';

        for (; i < length; i++) {
            if (value[i]) {
                html += '<li class="tel-list-item">' + value[i] + '</li>';
            }
        }

        html += '</ul>';

        return html;
    };

    /* 操作栏拼凑,主要用于datagrid中formatter格式化字段 */
    Common.pieceWorks = function(value) {
        if (!value) {
            return false;
        }

        var html = '';
        var i = 0;
        var length = arguments.length;

        html += '<div class="list-center">';
        html += '<ul class="works-list list-inline">';

        for (; i < length; i++) {
            if (value[i]) {
                html += '<li class="works-list-item"><a class="edit-link" data-url="' + arguments[i][1] + '"><i class="icon-blue icon-' + arguments[i][0] + '"></i></a></li>';
            }
        }

        html += '</ul>';
        html += '</div>';

        return html;
    };

    /* 联系人/手机拼凑,主要用于datagrid中formatter格式化字段 */
    Common.pieceContacts = function(value) {
        if (!value) {
            return false;
        }

        var html = '';
        var splitCode = '<em class="split">/</em>';
        var firstValue = '';
        var secondValue = '';

        html += '<ul class="list-unstyled">';

        for (var i = 0; i < arguments.length; i++) {
            firstValue = arguments[i][0] ? arguments[i][0] : '';
            secondValue = arguments[i][1] ? arguments[i][1] : '';

            if (!(firstValue || secondValue)) {
                continue;
            }

            firstValue = firstValue ? firstValue : '-';
            secondValue = secondValue ? secondValue : '-';

            html += '<li class="contacts-list-item">';
            html += '<span class="contacts-name">' + firstValue + splitCode + '</span>';
            html += '<span class="contacts-tel">' + secondValue + '</span>';
            html += '</li>';
        }

        html += '</ul>';

        return html;
    };

    /*******
    setSelectByList生成下拉选项
    data:json数据
    fieldsd:可以是字符串也可以是数组，如果是字符串则select的文字与值一样，如果是数组则按第一个是文本，第二个是值，第三个以后的是自定义data值
    selectedID:当前选中的值ID
    element:需要将生成的DOM插入的对象
     ******/
    Common.setSelectByList = function(data, fields, selectedID, element, isRequired, groupObj) {
        if (!(data || element)) {
            return false;
        }

        var checked = '';
        var defaultOption = '<option value="">' + Common.langHandler('pleaseSelect', '请选择') + '</option>';
        var dataFields = '';
        var html = '';

        element = element || $('#setSelectByList');

        if (!isRequired) {
            html = defaultOption;
        }

        if (typeof isRequired == 'string') {
            html = isRequired;
        }

        if (groupObj) {
            creatOptionGroup();
        } else {
            creatOptios();
        }

        function creatOptios(v) {
            $.each(
                data,
                function(index, value) {
                    if (v && value[groupObj['field']] != v) {
                        return true;
                    }

                    if ($.isArray(fields)) {
                        var i = 2;
                        var length = fields.length;

                        dataFields = '';

                        for (; i < length; i++) {
                            dataFields += ' data-' + fields[i] + '="' + value[fields[i]] + '"';
                        }

                        checked = value[fields[0]] == selectedID ? ' selected="selected"' : '';
                        html += '<option value="' + value[fields[0]] + '"' + dataFields + checked + '>' + value[fields[1]] + '</option>';
                    } else {
                        checked = value[fields] == selectedID ? ' selected="selected"' : '';
                        html += '<option value="' + value[fields] + '"' + checked + '>' + value[fields] + '</option>';
                    }
                }
            )
        }

        function creatOptionGroup() {
            if (!groupObj) {
                return;
            }

            if (typeof groupObj['values'] == 'string') {
                html += '<optgroup label="' + groupObj['values'] + '">';
                creatOptios();
                html += '</optgroup>';
            }

            if ($.isArray(groupObj['values'])) {
                $.each(
                    groupObj['values'],
                    function(i, v) {
                        html += '<optgroup label="' + v + '">';
                        creatOptios(v);
                        html += '</optgroup>';
                    }
                )
            }
        }

        element.html('');
        $(html).appendTo(element);

        //如果传过来的值为数据则按顺序设置当前项
        if ($.isArray(selectedID)) {
            element.each(function(index) {
                $(this).val(selectedID[index]);
            });
        }

    };

    /* 
    设置checkbox列表
    data:必填，数据源
    fields:必填,需要显示的字段，数组类型，[0]是value, [1]是text, [2]是分类字段
    element:选填，需要追加添加到的元素对象，默认值：ID为J_CreatItems的元素
    ids:选填，已有的值ids，数组类型
    isDefault: 选填，如果为true则表示要根据isDefault的值是否禁用.
    transformFunction: 选填，如果存在，则将fields[2]进行转换
     */
    Common.setCheckboxByList = function(data, fields, element, ids, isDefault, transformFunction) {
        Common.bulidSelectForms('checkbox', data, fields, element, ids, isDefault, transformFunction);
    };

    Common.setRadioByList = function(data, fields, element, ids, isDefault, transformFunction) {
        Common.bulidSelectForms('radio', data, fields, element, ids, isDefault, transformFunction);
    };

    /* 生成checkbox或radios */
    Common.bulidSelectForms = function(type, data, fields, element, ids, isDefault, transformFunction) {
        if (!data) {
            return;
        }

        type = type || 'checkbox';

        element = element || $('#setCheckboxByList');
        isDefault = isDefault || false;

        var html = '';
        var fieldValue = fields[0];
        var fieldText = fields[1];
        var fieldCategories = fields[2];
        var categoriesArray = getCategoriesArray();
        var postFields = null;

        if (categoriesArray) {
            $.each(
                categoriesArray,
                function(index, value) {
                    groupHTML(value);
                }
            );
        } else {
            groupHTML();
        }

        $(html).appendTo(element);

        if (postFields) {
            Common.addHiddenField(postFields, element);
        }

        function groupHTML(value) {
            html += '<div class="form-multiple">';
            html += headerHTML(value);
            html += '	<div class="form-multiple-body">';
            html += '		<ul class="form-multiple-list form-multiple-grid">';
            html += listHTML(value);
            html += '		</ul>';
            html += '	</div>';
            html += '</div>';
        }

        function headerHTML(title) {
            if (!title) {
                return '';
            }

            title = transformFunction ? transformFunction(title) : title;

            var headerHTML = '';

            headerHTML += '<div class="form-multiple-heading">';
            headerHTML += '	<h3 class="form-multiple-title">' + title + '</h3>';
            headerHTML += '</div>';

            return headerHTML;
        }

        function listHTML(categorieName) {
            var listHTML = '';
            var checked = '';
            var descString = '';
            var disabled = '';

            $.each(
                data,
                function(index, value) {
                    checked = $.inArray(value[fields[0]], ids) != -1 ? ' checked="checked"' : '';
                    disabled = getDisabled(value, checked);
                    if (value[fieldCategories] && (value[fieldCategories] != categorieName)) {
                        return true;
                    }

                    if (fields[3] != undefined && !value[fields[3]]) {
                        value[fields[3]] = '无';
                    }

                    descString = value[fields[3]] ? '<br/><span style="color:#999">[' + value[fields[3]] + ']</span>' : '';

                    listHTML += '<li class="form-multiple-item"><label class="form-multiple-label text-overflow" title="' + value[fields[1]] + '"><input type="' + type + '" class="' + type + '" id="' + fields[1] + value[fields[0]] + '" name="' + fields[0] + '" value="' + value[fields[0]] + '"' + checked + disabled + ' /><span class="checkbox-text">' + value[fields[1]] + '</span>' + descString + '</label></li>';
                }
            );

            return listHTML;
        }

        function getCategoriesArray() {
            if (!fieldCategories) {
                return false;
            }

            return Common.getItemsToArray(data, fieldCategories);
        }

        function getDisabled(value, checked) {
            if (!(isDefault && value['isDefault'] && checked)) {
                return '';
            }

            postFields = postFields || {};
            postFields[fields[0]] = value[fields[0]];

            return ' disabled="disabled"';
        }
    };

    /**** 
    创建树形结构多选列表
    如角色对应的菜单选择时的多选树形菜单
    ****/
    Common.setCheckboxTreeByList = function(data, fields, element, ids, disabled) {
        if (!data) {
            return;
        }

        element = element || $('#setCheckboxTreeByList');
        disabled = disabled ? ' disabled="disabled"' : '';

        var html = '';

        headerHTML();
        listHTML();
        footerHTML();

        $(html).appendTo(element);

        function headerHTML() {
            html += '<div class="form-multiple">';
            html += '<div class="form-multiple-body">';
        }

        function listHTML() {
            creatList(data, 0);
        }

        function footerHTML() {
            html += '</div></div>';
        }

        function creatList(data, i) {
            var hiddenCss = i != 0 ? ' hidden' : '';
            var selected = '';
            var plusMinus = ' ';

            html += '<ul class="form-multiple-list form-multiple-tree' + hiddenCss + '">';

            if (!data || data.length == 0) {
                return;
            }

            var isSuperAdmin = false;
            if (loginData.getUser().id == "1") {
                isSuperAdmin = true;
            }


            $.each(
                data,
                function(index, value) {
                    plusMinus = value['children'].length > 0 ? '+' : ' ';

                    selected = $.inArray(value[fields[0]], ids) != -1 ? ' checked="checked"' : '';

                    html += '<li class="form-multiple-item">';

                    html += '<label class="form-multiple-label text-overflow" title="' + value[fields[1]] + '">' +
                        '<span class="plus-minus">' + plusMinus + '</span>' +
                        '<input type="checkbox" class="checkbox" id="' + fields[1] + value[fields[0]] + '" name="' + fields[0] + '" value="' + value[fields[0]] + '"' + selected + disabled + ' />';


                    if (isSuperAdmin && value[fields[2]]) {
                        html += '<span class="checkbox-text">' + value[fields[1]] + ' <span style="color:#999">[' + value[fields[2]] + ']</span> ' + '</span></label>';
                    } else {
                        html += '<span class="checkbox-text">' + value[fields[1]] + '</span></label>';
                    }

                    if (value['children'].length > 0) {
                        creatList(value['children'], 1);
                    }

                    html += '</li>';
                }
            );

            html += '</ul>';

        };

    };

    /* 根据字段获取消重值 */
    Common.getValuesArrayByFeild = function(field, data) {
        var result = [];

        $.each(
            data,
            function(index, value) {
                if ($.inArray(value[field], result) != -1) {
                    return true;
                }
                result.push(value[field]);
            }
        );

        return result;
    };


    /* 发送心跳包 */
    Common.sendHeartbeatPackets = function(o, serverName) {
        o = o || ws;
        if (!o || o.readyState !== 1) {
            Debug.log('没有websokect对象, 停止发心跳！');
            return null;
        }

        serverName = serverName || 'app';
        var fun = arguments.callee;
        var params = {
            "msgType": 'SERVER_HEARTBEAT_REQ'
        };

        params = Common.getFullData(params);
        params = JSON.stringify(params);

        o.send(params);
        Debug.log('向' + serverName + '服务器发送心跳包：' + params);

        return setTimeout(function() {
            fun(o, serverName);
        }, 25000);
    };

    /* 上传图片 */
    Common.setImagePreview = function(fileId, imgId, imgParentId, width, height) {
        var docObj = document.getElementById(fileId);
        var imgObjPreview = document.getElementById(imgId);

        if (docObj.files && docObj.files[0]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'block';
            imgObjPreview.style.width = width ? width : '150px';
            imgObjPreview.style.height = height ? height : '180px';

            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
        } else {
        	//IE下，使用滤镜
    		docObj.select();
    		if(document.selection!=undefined){
    			var imgSrc = document.selection.createRange().text;
    			var localImagId = document.getElementById(imgParentId);
    			//必须设置初始大小
    			localImagId.style.width = width ? width : '150px';
    			localImagId.style.height = height ? height : '180px';
    			//图片异常的捕捉，防止用户修改后缀来伪造图片
    			try {
    				localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
    				localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
    			} catch (e) {
    				alert("您上传的图片格式不正确，请重新选择!");
    				return false;
    			}
    			imgObjPreview.style.display = 'none';
    			document.selection.empty();
    		}else{
    			return false;
    		}
        }
    };

    /* 根据msgType组装完整的JSON (子系统内部通信协议用)*/
    Common.getFullData = function(data) {
        var params = data;

        if (loginData) {
            params = $.extend({}, loginData.getGDHSInfo(), data, {
                'sid': loginData.getSID()
            });
        }

        if (params.securityCode && params.from && params.from.search(/wsclient/) === -1) {
            params.from = 'wsclient_' + params.securityCode + '_' + params.from;
        }

        return params;
    };

    /* 组装rdtsWS地址 */
    Common.getRDTSWSDomain = function(host, port) {
    	var protocol = location.protocol === 'https:' ? 'wss:' : 'ws:';
        return protocol + '//' + host + ':' + port;
    };

    /* 从CMD报文中获取值 */
    Common.getValueByCMDTransferData = function(data) {
        if (!data) {
            return '';
        }

        var otherLength = 12 * 2;
        return data.substring(otherLength, data.length);
    };

    /* 显示语言 */
    Common.parseLanguage = function(parentElement) {
        Common.displayText(parentElement);
        Common.displayAttrText(parentElement);

        return parentElement;
    };

    /* 显示页面中的文字 */
    Common.displayText = function(parentElement) {
        parentElement = parentElement || $(document.body);
        var elements = parentElement.find('[data-lang]'),
            langName = '',
            text = '';

        if (!elements.length) {
            return;
        }

        $.each(
            elements,
            function(index, value) {
                langName = $(value).data('lang');
                text = Lang[langName];
                if (text) {
                    $(value).text(text).attr('title', text);
                }
            }
        );
    };

    /* 显示页面中属性中的文字 */
    Common.displayAttrText = function(parentElement) {
        parentElement = parentElement || $(document.body);
        var attrs = ['placeholder', 'title', 'alt'];
        var reg = /\{\*(.+)\*\}/g;
        var elements = null;
        var langName = '';
        var text = '';

        $.each(
            attrs,
            function(index, value) {
                elements = parentElement.find('[' + value + ']');
                if (elements.length == 0) {
                    return true;
                }
                $.each(
                    elements,
                    function(idx, val) {
                        langName = $(val).attr(value).replace(reg, '$1');
                        text = Lang[langName];
                        if (text) {
                            $(val).attr(value, text);
                        }
                    }
                );
            }
        );
    };

    /* 打开一个简单对话框 */
    Common.simpleDialog = function(content, title) {
        var dialog = new Dialog({
        	componentId: 'simpleDialog', //组件id
        	componentRelation: 'simpleDialog > dialog', //组件关系
            width: 200,
            title: title || Common.langHandler('warmTips', '温馨提示'),
            content: content,
            buttons: [{
                text: Common.langHandler('buttonConfirm', '确定'),
                click: function(d) {
                    d.close();
                }
            }]
        });

        dialog.open();
    };

    /* 打开一个消息对话框 */
    Common.msgDialog = function(content, title, iconClass, callback) {
        var dialog = new Dialog({
        	componentId: 'msgDialog', //组件id
        	componentRelation: 'msgDialog > dialog', //组件关系
            width: 200,
            title: title || Common.langHandler('warmTips', '温馨提示'),
            content: (iconClass ? '<i class="icon icon-' + iconClass + '"></i>' : '') + content
        });

        dialog.open();

        window.setTimeout(function() {
            dialog.close();
            if (typeof callback == 'function') {
                callback();
            }
        }, 1500);
    };

    /* 打开一个确认对话框 */
    Common.confirmDialog = function(content, title, sureCallback, cancelCallback) {
        var dialog = new Dialog({
        	componentId: 'confirmDialog', //组件id
        	componentRelation: 'confirmDialog > dialog', //组件关系
            width: 200,
            title: title || Common.langHandler('warmTips', '温馨提示'),
            content: content,
            buttons: [{
                text: Common.langHandler('buttonConfirm', '确定'),
                styleName: 'btn-success',
                click: function(d) {
                    var flag = true;
                    if (sureCallback && typeof sureCallback == 'function') {
                        flag = sureCallback(d);
                    }
                    if (flag === false) {
                        return;
                    }
                    d.close();
                }
            }, {
                text: Common.langHandler('buttonCancel', '取消'),
                click: function(d) {
                    var flag = true;
                    if (cancelCallback && typeof cancelCallback == 'function') {
                        flag = cancelCallback(d);
                    }
                    if (flag === false) {
                        return;
                    }
                    d.close();
                }
            }]
        });

        dialog.open();
    };

    /* 右下角的消息提示框 */
    Common.rightBottomTips = function(title, content, callback, btnTitle, width, onclosecallback, trapWindowLocation, btnArray) {
        var tips = new Tips({
        	trapWindowLocation: trapWindowLocation,
            title: title,
            content: content,
            width: width || 280,
            buttons: btnArray || [{
                text: btnTitle || Common.langHandler('handle', '处理'),
                click: callback,
                styleName: 'btn-warning'
            }],
            onClose: onclosecallback
        });

        tips.open();

        return tips;
    };


    /*********
      根据时间搓格式化时间日期
      时间格式化函数
      dateTime:字符串
      format:需要返回的格式有('y-m-d', 'y-m-d h:m:s', 'y-m-d h:m', 'y/m/d', 'y/m/d h:m:s', 'y/m/d h:m')，默认值为：y-m-d h:m:s
    *********/
    Common.getFormatTime = function(dateTime, format) {
        if (!dateTime) {
            return '';
        }

        var date = 0;

        if (typeof dateTime == 'string' && dateTime.indexOf('Date') != -1) {
            date = new Date(parseInt(dateTime.replace("/Date(", "").replace(")/", ""), 10));
        } else {
            date = new Date(dateTime * 1);
        }

        if (date < 0) {
            return '';
        }

        var year = date.getFullYear();
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var min = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var sec = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        var formatObject = ['y-m-d', 'y-m-d h:m:s', 'y-m-d h:m', 'y/m/d', 'y/m/d h:m:s', 'y/m/d h:m'];
        var i = 0;
        var length = formatObject.length;
        var index = 1;

        if ($.inArray(format, formatObject) != -1) {
            index = $.inArray(format, formatObject);
        }

        switch (index) {
            case 0:
                return year + '-' + month + '-' + day;
                break;
            case 1:
                return year + '-' + month + '-' + day + ' ' + hour + ':' + min + ':' + sec;
                break;
            case 2:
                return year + '-' + month + '-' + day + ' ' + hour + ':' + min;
                break;
            case 3:
                return year + '/' + month + '/' + day;
                break;
            case 4:
                return year + '/' + month + '/' + day + ' ' + hour + ':' + min + ':' + sec;
                break;
            case 5:
                return year + '/' + month + '/' + day + ' ' + hour + ':' + min;
                break;
        }
    };

    //截取日期
    Common.getCuttingDate = function(dataTime) {
        if (!dataTime) {
            return '';
        }

        return dataTime.split(' ')[0];
    };

    /* 获取数量元素 
    num:数量
    menuNO:属性data-menu-number等于menuNO的元素
    cssName:数量的样式
    isFlashing:是否闪动
    */
    Common.fillCountElement = function(num, menuNO, cssName, isFlashing) {
        cssName = cssName || 'tag-default';
        isFlashing = isFlashing || false;

        if (num == 0) {
            cssName = 'tag-default';
            isFlashing = false;
        }

        var $sup = $('<sup class="tag ' + cssName + '">' + num + '</sup>');
        var timeId = 0;
        var $element = null;

        if (menuNO instanceof jQuery) {
            $element = menuNO;
        } else {
            $element = $('a[data-menu-number="' + menuNO + '"]');
        }

        //赋值数量
        if ($element.find('.tag').length > 0) {
            $sup = $element.find('.tag');
            $sup.attr('class', 'tag ' + cssName).text(num);
        } else {
            $element.append($sup);
        }

        //闪动
        if (isFlashing) {
            timeId = setTimeout(function() {
                if ($sup.hasClass('invisible')) {
                    $sup.removeClass('invisible');
                } else {
                    $sup.addClass('invisible');
                }
                setTimeout(arguments.callee, 300);
            }, 300);
        }

        return timeId;
    };
    
    /**
     * 对特殊字符进行转码
     */
    Common.Decode = function(input){
    	var converter = document.createElement("DIV"); 
		converter.innerHTML = input || ''; 
		var output = converter.innerText !== undefined ? converter.innerText : converter.innerHTML; 
		converter = null; 
		return output;
    };
    
    /**
     * 对表情进行转码
     */
    Common.utf16ToEntities = function(str) {
    		var patt=/[\ud800-\udbff][\udc00-\udfff]/g; // 检测utf16字符正则
    		str = str.replace(patt, function(char){
            var H, L, code;
            if (char.length===2) {
                H = char.charCodeAt(0); // 取出高位
                L = char.charCodeAt(1); // 取出低位
                code = (H - 0xD800) * 0x400 + 0x10000 + L - 0xDC00; // 转换算法
                return "&#" + code + ";";
            } else {
                return char;
            }
        });
    	return str;
	};

    /* 模糊搜索和精确搜索切换 */
    Common.changeEventOfSwitchButton = function(opts) {
        var _this = this;
        var options = $.extend({}, this.defaults, opts);
        var $searchBtn = null;

        if (options.btn) {
            $searchBtn = options.btn;
        } else {
            btnId = options.btnId || "J_SearchSubmit";
            $searchBtn = $("#" + btnId);
        }

        //初始化重置按钮样式
        var $parentDiv = $searchBtn.parent();
        var $parentForm = $searchBtn.closest("form");
        var $resetBtn = $parentDiv.find("button[type='reset']");
        var $moreSwitch = $parentDiv.find("span[class=switch-link]");

        var $blurSearch = $("<button type='submit' class='btn-back J_BlurSearch'><span><span>" + Common.langHandler('buttonBlurSearch', '搜索') + "</span></span></button>");
        var $accurateSearch = $("<button type='submit' class='btn-back J_AccurateSearch'><span><span>" + Common.langHandler('buttonAccurateSearch', '精确搜索') + "</span></span></button>");
        var $iconBlurSwitch = $("<i class='icon icon-white icon-chevron-down J_IconBlurSwitch'></i>");

        var $btnSwitchDiv = $("<div class = 'button-switch-item'></div>");

        var $blurSearchDiv = $("<div class='button-item  J_BlurButton'></div>");
        var $accurateSearchDiv = $("<div class='button-item button-item-top J_AccurateButton'></div>");

        $blurSearchDiv.append($blurSearch).append($iconBlurSwitch);
        $accurateSearchDiv.addClass("hidden").append($accurateSearch);
        $btnSwitchDiv.append($blurSearchDiv).append($accurateSearchDiv);

        $parentDiv.html("").append($btnSwitchDiv);

        Common.addHiddenField({
            'isAccurate': 0
        }, $parentForm);

        $iconBlurSwitch.click(function() {
            if ($(this).hasClass("icon-chevron-down")) {
                $(this).removeClass("icon-chevron-down").addClass("icon-chevron-up");
                $accurateSearchDiv.removeClass("hidden");
            } else {
                $(this).removeClass("icon-chevron-up").addClass("icon-chevron-down");
                $accurateSearchDiv.addClass("hidden");
            }
        });

        $accurateSearchDiv.click(function() {
            //更换button和样式
            var $button = $(this).find("button");

            $(this).find("button").remove();
            $(this).prepend($blurSearchDiv.find("button"));
            $blurSearchDiv.find("button").remove();
            $blurSearchDiv.prepend($button);

            if ($button.hasClass("J_BlurSearch")) {
                Common.addHiddenField({
                    'isAccurate': 0
                }, $parentForm);
                if (options.blurSearchEvent) {
                    options.blurSearchEvent();
                }
            } else {
                Common.addHiddenField({
                    'isAccurate': 1
                }, $parentForm);
                if (options.accurateSearchEvent) {
                    options.accurateSearchEvent();
                }
            }

            //收起选择框
            $iconBlurSwitch.click();
        });

    };

    /* 更多筛选 */
    Common.bindEventOfMoreSwitch = function(openCallback, closeCallback) {
        $('[data-switch-title]').click(function() {
            var $targetElement = $('[data-switch-content="' + $(this).attr('data-switch-title') + '"]');
            var $this = $(this);
            
            if ($targetElement.hasClass('hidden')) {
                $targetElement.removeClass('hidden');
                $this.find('[data-switch-icon]').removeClass('s-down').addClass('s-up');

                if (typeof openCallback == 'function') {
                    openCallback();
                }
            } else {
                $targetElement.addClass('hidden');
                $this.find('[data-switch-icon]').removeClass('s-up').addClass('s-down');

                if (typeof closeCallback == 'function') {
                    closeCallback();
                }
            }
        });
    };
    
    /* 将目标设备状态转换成文字 */
    Common.getIsDefault = function(num){
		num = Common.stringToNumber(num);
		if(num==1){
			return '<span class="tag tag-blue-green">' + Lang.yes + '</span>';
		}else{
			return '<span class="tag tag-danger">' + Lang.no + '</span>';
		}
	};
    
    /* 将中文格式转换成utf-8 */
    Common.toUtf8 = function(str) {
    	if(str){
            var out, i, len, c;    
            out = "";    
            len = str.length;    
            for(i = 0; i < len; i++) {    
                c = str.charCodeAt(i);    
                if ((c >= 0x0001) && (c <= 0x007F)) {    
                    out += str.charAt(i);    
                } else if (c > 0x07FF) {    
                    out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));    
                    out += String.fromCharCode(0x80 | ((c >>  6) & 0x3F));    
                    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
                } else {    
                    out += String.fromCharCode(0xC0 | ((c >>  6) & 0x1F));    
                    out += String.fromCharCode(0x80 | ((c >>  0) & 0x3F));    
                }    
            }
            return out;    
    	}else{
    		return str;
    	}
    };
    
    Common.lightbox = function() {
    	var lightbox = new Lightbox();
    	lightbox.option({
            'resizeDuration': 200,
            wrapAround: true,//是否可以循环播放
            albumLabel: Lang.the + '%1'+ Lang.zhang +'，'+ Lang.allCountTip +'%2'+ Lang.zhang
        });
    };
    
    //获取当前浏览器及当前浏览器的版本
    Common.getBrowserInfo = function(ieVersion) {
    	var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    	var isOpera = userAgent.indexOf("Opera") > -1;
    	var flag = true;
    	
    	//判断是否firfox浏览器 ，支持firfox40.0以上
        if (userAgent.indexOf("Firefox") > -1) {
        	var explorer = userAgent.toLowerCase() ;
        	var ffVersion = explorer.match(/firefox\/([\d.]+)/)[1];
        	var pos = ffVersion.indexOf('.');
        	ffVersion = ffVersion.substring(0,pos) * 1;
 
        	if(ffVersion > 40){
    			flag = true;
    		}else{
    			flag = false;
    		}
        	
            return flag;
        } 
        
        //判断是否Chrome浏览器 ，支持Chrome40.0以上
        if (userAgent.indexOf("Chrome") > -1){
        	var explorer = userAgent.toLowerCase() ;
        	var chromeVersion = explorer.match(/chrome\/([\d.]+)/)[1];
        	
        	var pos = chromeVersion.indexOf('.');
        	chromeVersion = chromeVersion.substring(0,pos) * 1;
       
        	if(chromeVersion > 40){
    			flag = true;
    		}else{
    			flag = false;
    		}
	      return flag;
	    }
        
        //判断是否IE浏览器 ，支持IE9以上
        if ((userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) || Common.isIE()) {
            
    		var browserVersion = document.documentMode;
    		var version = ieVersion ? ieVersion : 8;
    		if(browserVersion > version){
    			flag = true;
    		}else{
    			flag = false;
    		}
        	
        	return flag;
        }; 
    };
    
    //创建 浏览器兼容版本弹出框
	Common.buildBrowserRemindTips = function(imageUrl, ieVersion){
		var browser = Common.getBrowserInfo(ieVersion);
		var isPC = Common.isPc();
		var hiddenCss = imageUrl ? '' : 'hidden';
		var downloadURL = '//resource.dataserver.cn/download/browser/';
		var ieDownloadURL = 'https://support.microsoft.com/zh-cn/help/17621/internet-explorer-downloads';
		var firefoxDownloadURL = 'http://www.firefox.com.cn/download/';
		var os = Common.getOS();
		var bit = Common.getBit();
		if (os == 'Win'){
			if(bit == 'x64'){
				downloadURL += 'chrome64_installer.zip';
			} else {
				downloadURL += 'chrome_installer.zip';
			}
		}

		if(!browser && isPC){
			var contentStr = '<div class="browser-mask-level"></div>'+  //遮罩层
			'<div class="browser-package-box">'+        //登陆框包裹层
				'<div class="form-browser-remind-content">' +	
					'<p class="form-browser-tips">hi,您当前的浏览器版本过低，可能存在安全风险，建议升级浏览器：</p>' +	
					'<div class="form-browser-type"><a href="' + downloadURL + '" target="_blank"><span class="broweser-style broweser-style-chrome">谷歌 Chrome40.0+</span></a><a href="' + ieDownloadURL + '" target="_blank"><span class="broweser-style">IE10+</span></a><a href="' + firefoxDownloadURL + '" target="_blank"><span class="broweser-style">火狐 Firfox40.0+</span></a></div>' +	
					'<p class="form-browser-tips2 ' + hiddenCss + '">或直接用<label class="browser-prompt2">App扫码浏览：</label></p>' +	
					'<div class="form-browser-image">' +
					'<div class="pull-left '+hiddenCss+'"><img src="'+imageUrl+'" width="120px" height="120px"></div>' +
					'<div class="pull-right form-image-error"></div>' +
					'</div>' +	
				'</div>' +
			 '</div>';
		
			var $contentStr = $(contentStr);
	        return $contentStr.appendTo(document.body);
		}
	};
	
	Common.isPc = function(){
		var userAgentInfo = navigator.userAgent;
	    var Agents = ["Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"];
	    var flag = true;
	    for (var v = 0; v < Agents.length; v++) {
	        if (userAgentInfo.indexOf(Agents[v]) > 0) {
	            flag = false;
	            break;
	        }
	    }
	    return flag;
	};
	
	Common.getOS = function(){
	    var sUserAgent = navigator.userAgent;
	    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
	    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");
	    
	    if (isMac) {
	    	return "Mac";
	    }
	    
	    if (isWin) {
	    	return "Win";
	    }
	};
	
	Common.getBit = function(){
		var agent = navigator.userAgent.toLowerCase();
		
	    if (agent.indexOf("win64") >= 0 || agent.indexOf("wow64") >= 0){
	    	return "x64";
	    }
	    
	    return navigator.cpuClass;
	};
	
	Common.generateUUID = function(){
		var d = new Date().getTime();
    	var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
    		var r = (d + Math.random()*16)%16 | 0;
    		d = Math.floor(d/16);
    		return (c=='x' ? r : (r&0x3|0x8)).toString(16);
    	});
    	return uuid;
	};
	
    return Common;

}));