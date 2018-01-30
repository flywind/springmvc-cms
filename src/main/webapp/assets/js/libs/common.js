(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'easyui', 'detailview'], factory);
    } else {
        window.Common = factory(jQuery,undefined, detailview);
    }
}(function($,undefined, detailview) {
	var Common = {};
	
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
	
	/* 警告框  */
	Common.alert = function(message, parentElement, type){
		message = message || '提示信息的内容';
		var types = ['info','primary','success','warning','danger'];
		if(($.inArray(type, types)) < 0){
			type = 'info';
		}

		var messageObj = $('<div class="alert alert-'+type+' alert-dismissible fade in" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>'+message+'</div>')
		parentElement.append(messageObj);
		return messageObj;
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
	
	/* datagrid */
    Common.datagrid = function(settingNew, element) {
        element = element || $('[data-grid="true"]');
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
        
        element.datagrid(opts);

        //创建自定义显示列
        if (opts.showColumns) {
            Common.buildCustomColumnBar(opts.columns[0], element, opts.showColumnsStr);
        }

    };
    
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

            Common.showColumnsByCustomerColumnBar($datagridParent.find('.show-columns-bar'), element);

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
        	Common.showColumnsByCustomerColumnBar($box, element);
        }
    };
    
    /*根据自定义列的checkbox来显示或者隐藏所对应的列*/
    Common.showColumnsByCustomerColumnBar = function(columnsBar, element) {
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
	
	return Common;
}));