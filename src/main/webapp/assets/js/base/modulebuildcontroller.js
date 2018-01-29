//组件构造函数
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'common', 'dialog', 'formui', 'ajaxfileupload', 'validate'], factory);
    } else {
        window.ModuleBuildController = factory($, Common, Dialog, FormUI, undefined, undefined);
    }
}(function($, Common, Dialog, FormUI, undefined, undefined) {

    function ModuleBuildController(opts, rows, customerMenuType) {
        this.options = $.extend(true, {}, ModuleBuildController.defaults, opts);
        this.idField = this.options.idField || 'id';
        this.rows = rows;
        this.id = (rows && rows.length > 0) ? rows[0].id : '';
        this.customerMenuType = customerMenuType ? customerMenuType : '';
        this.loaded = false;
        this.data = null;

        this.init();
    }

    ModuleBuildController.defaults = {
        //模板内容配置
        templateConfig: {
            content: '', //第一种，string，模板内容是一个html字符串
            formUI: null, //第二种，formUI，模板内容是通过formUI构造出来
            url: '', //第三种，要请求的模板路径  例如，“/1.1/common/template/userCreateUpdate.tpl”
            queryParams: {}, //要请求的额外参数
            onbefore: function(dom) {}, //导入前的处理函数
            onsuccess: function(dom, dialog) {} //导入成功后的处理函数
        },
        //填充数据配置
        filldataConfig: {
        	localData: null, //20161230新增，可以传递本地数据，传递后不再使用url请求数据
            idField: 'id', //唯一标识的字段名称
            url: '', //要请求数据的action
            queryParams: {}, //要请求的额外参数
            onBeforePostData:function(rows, param){},
            onbefore: function(data, dom) {}, //填充数据之前的处理函数
            onsuccess: function(data, dom) {} //填充成功后的处理函数
        },
        //表单配置
        formConfig: {
            idField: '',
            url: '', //表单提交请求的接口
            queryParams: {}, //要请求的额外参数
            rules: {}, //表单验证规则
            messages: {},
            errorPlacement: null,
            isFileUpload: false, // 是否是上传文件类型
            isImport: false, //是否是导入，默认不是，如果false则返回错误信息时不用dialog
            autoCalcMsgDialogHeight: false, // 导入返回错误信息的时候是否根据返回信息中的<br>计算dialog的高度，默认false
            onbeforesubmit: function(res) {}, //表单提交之前的处理函数，不影响表单提交
            onsuccess: function(res, dialog) {}, //表单提交成功之后的处理函数，覆盖常规的处理后流程
            onfailure: function(res, dialog) {} //表单提交失败之后的处理函数，覆盖常规的处理后流程
        },
        //弹窗配置
        dialogConfig: {
        	componentId: '', //组件id
        	componentRelation: 'modulebuildcontroller > dialog', //组件关系
            dialog: true, //是否使用dialog
            autoOpen: true,
            width: 500,
            height: 500,
            title: Common.langHandler('warmTips', '温馨提示'),
            confirm: {
            	title: '',
            	content: ''
            },
            onSubmitBtnClick: function(dom) {}
        },
        //grid配置
        gridConfig: {
            selector: '[data-datagrid="true"]', //grid选择器
            type: 'datagrid' //datagrid或者treegrid
        },
        singleSelect: true, //是否单选,如果为false,则支持多选
        click: function(rows, e) {}, //菜单为1001按钮点击后的回调函数, 如果return false,则不执行默认代码
        idField: 'id' //唯一标识的字段名称
    };

    ModuleBuildController.prototype = {
        constructor: ModuleBuildController,

        init: function() {
            if (this.options.dialogConfig.dialog) {
                this._buildDialog();
            }

            this._buildDOM();
        },

        //创建DOM
        _buildDOM: function() {
            var templateConfig = this.options.templateConfig;
            var params = templateConfig.queryParams;
            var onsuccess = templateConfig.onsuccess;
            var onbefore = templateConfig.onbefore;
            var context = templateConfig.content;
            var formlist = templateConfig.formUI;
            var path = templateConfig.url;
            var _this = this;

            //string，模板内容是一个html字符串
            if (context) {

                this.DOM = this._getParseDOM(context instanceof $ ? context : $(context));

                _this.fileIds = _this._getFileIds(_this.DOM);
                
                if (onbefore && typeof onbefore === 'function') {
                	onbefore(_this.DOM);
                }
                
                if (_this.options.dialogConfig.dialog) {
                    _this.dialog.setContent(_this.DOM);
                }
                
                if (_this.options.templateConfig.boxElement){
                	_this.options.templateConfig.boxElement.append(_this.DOM);
                }

                if (typeof onsuccess === 'function') {
                    onsuccess(_this.DOM, _this.dialog, _this.rows);
                }

                if (_this.options.filldataConfig.localData || _this.options.filldataConfig.url) {
                    _this.fillData();
                }

                if (!_this.options.formConfig.url) {
                    return;
                }

                if (_this.options.formConfig.isFileUpload) {
                    _this.submitFileHandler();
                    return;
                }

                _this.submitHandler();

                return;
            }

            //formUI，模板内容是通过formUI构造出来
            if (formlist) {
                var myForm = new FormUI(formlist);
                var formDOM = myForm.getForm();

                this.DOM = this._getParseDOM(formDOM);

                _this.fileIds = _this._getFileIds(_this.DOM);
                
                if (onbefore && typeof onbefore === 'function') {
                	onbefore(_this.DOM);
                }
                
                if (_this.options.dialogConfig.dialog) {
                    _this.dialog.setContent(_this.DOM);
                }
                
                if (_this.options.templateConfig.boxElement){
                	_this.options.templateConfig.boxElement.append(_this.DOM);
                }

                if (typeof onsuccess === 'function') {
                    onsuccess(_this.DOM, _this.dialog, _this.rows);
                }

                if (_this.options.filldataConfig.localData || _this.options.filldataConfig.url) {
                    _this.fillData();
                }

                if (!_this.options.formConfig.url) {
                    return;
                }

                if (_this.options.formConfig.isFileUpload) {
                    _this.submitFileHandler();
                    return;
                }

                _this.submitHandler();

                return;
            }

            //要请求的模板路径  例如，“/1.1/common/template/userCreateUpdate.tpl”
            if (path) {
                $.get(path, params, function(res) {

                    _this.loaded = true;
                    _this.DOM = _this._getParseDOM($(res));

                    _this.fileIds = _this._getFileIds(_this.DOM);

                    if (onbefore && typeof onbefore === 'function') {
                    	onbefore(_this.DOM);
                    }
                    
                    if (_this.options.dialogConfig.dialog) {
                        _this.dialog.setContent(_this.DOM);
                    }
                    
                    if (_this.options.templateConfig.boxElement){
                    	_this.options.templateConfig.boxElement.append(_this.DOM);
                    }
                    
                    if (typeof onsuccess === 'function') {
                        onsuccess(_this.DOM, _this.dialog, _this.rows);
                    }

                    if (_this.options.filldataConfig.localData || _this.options.filldataConfig.url) {
                        _this.fillData();
                    }

                    if (!_this.options.formConfig.url) {
                        return;
                    }

                    if (_this.options.formConfig.isFileUpload) {
                        _this.submitFileHandler();
                        return;
                    }

                    _this.submitHandler();
                });

                return;
            }

        },

        _getFileIds: function(DOM) {
            var fileIds = [];
            var $fileDoms = DOM.find('input[type="file"]');

            $.each(
                $fileDoms,
                function(i, v) {
                    fileIds[i] = $(v).attr('id');
                }
            );
            
            return fileIds;
        },

        _getParseDOM: function($context) {
            var $DOM = Common.parseLanguage($context);
            return $DOM;
        },

        _buildDialog: function() {
            var _this = this;
            var dialogConfig = this.options.dialogConfig;
            var opts = dialogConfig.buttons && dialogConfig.buttons.length > 0 ? $.extend(true, {}, this.options.dialogConfig, {
                content: this.DOM || ''
            }) : $.extend(true, {}, this.options.dialogConfig, {
                buttons: [{
                    text: Common.langHandler('buttonConfirm', '提交'),
                    styleName: 'btn-success',
                    click: function(d) {
                    	var $msgObj = d.getDialogElement().find(".dialog-tips");
                    	$msgObj.html(''); // 点击确定的时候先清掉错误提示
                    	
                    	var confirmConfig = _this.options.dialogConfig.confirm;
                    	if (confirmConfig.title !== ''){
                    		Common.confirmDialog(confirmConfig.content, confirmConfig.title, function(d){
                                _this.DOM.submit();
                                d.close();
                    		}, function(d){
                    			d.close();
                    		});
                    	} else {
                    		_this.DOM.submit();
                    		if(dialogConfig.onSubmitBtnClick && typeof dialogConfig.onSubmitBtnClick === 'function'){
                    			dialogConfig.onSubmitBtnClick(_this.DOM);
                    		}
                    	}
                    }
                }, {
                    text: Common.langHandler('buttonCancel', '取消'),
                    styleName: 'btn-default',
                    click: function(d) {
                    	if (dialogConfig.onCancel && typeof dialogConfig.onCancel === 'function'){
                    		dialogConfig.onCancel(d);
                    	}
                        d.close();
                    }
                }],
                content: this.DOM || ''
            });

            this.dialog = new Dialog(opts);
        },

        fillData: function() {
            var flag = true;
            var DOM = this.DOM;
            var filldataConfig = this.options.filldataConfig;
            var idField = filldataConfig.idField;
            var onbefore = filldataConfig.onbefore;
            var onsuccess = filldataConfig.onsuccess;
            var onBeforePostData = filldataConfig.onBeforePostData;
            var url = filldataConfig.url;
            var localData = filldataConfig.localData;
            var _this = this;
            var params = {};

            params[idField] = this.id;

            params = $.extend(params, filldataConfig.queryParams);
            
            if (onBeforePostData && typeof onBeforePostData === 'function') {
                flag = onBeforePostData(this.rows, params);
            }
            if(flag === false) {
            	return;
            }
            
            if (localData){
            	data = localData;
            	if (onbefore && typeof onbefore === 'function') {
                    flag = onbefore(data, DOM);
                }

                if (flag === false) {
                    return false;
                }

                if (flag != undefined || flag != null) {
                    data = flag;
                }

                Common.fillValueToElement(data, null, DOM);

                if (onsuccess && typeof onsuccess === 'function') {
                    flag = onsuccess(data, DOM);
                }

                if (flag === false) {
                    return false;
                }

                _this.data = data;
                return;
            }

            if (!url) {
                return;
            }
            $.ajax({
                url: Common.pieceUrl(url),
                type: 'post',
                data: params,
                success: function(data) {
                    data = Common.stringToJSON(data);

                    if (!data) {
                        return;
                    }

                    if (onbefore && typeof onbefore === 'function') {
                        flag = onbefore(data, DOM);
                    }

                    if (flag === false) {
                        return false;
                    }

                    if (flag != undefined || flag != null) {
                        data = flag;
                    }

                    Common.fillValueToElement(data, null, DOM);

                    if (onsuccess && typeof onsuccess === 'function') {
                        flag = onsuccess(data, DOM);
                    }

                    if (flag === false) {
                        return false;
                    }

                    _this.data = data;
                }
            });
        },

        submitHandler: function() {

            var _this = this;
            var formConfig = this.options.formConfig;
            var gridConfig = this.options.gridConfig;
            var url = formConfig.url;
            var queryParams = formConfig.queryParams;
            var onbeforesubmit = formConfig.onbeforesubmit;
            var onsuccess = formConfig.onsuccess;
            var onfailure = formConfig.onfailure;
            var rules = formConfig.rules;
            var messages = formConfig.messages;
            var errorPlacement = formConfig.errorPlacement;
            var onkeyup = formConfig.onkeyup;


            this.DOM.validate({
                rules: rules,
                messages: messages,
                errorPlacement: errorPlacement,
                onkeyup : function(element) {
                	if(onkeyup && typeof onkeyup === 'function'){
                		onkeyup(element);
                	}
                },
                submitHandler: function(form) {
                    var formDatas = _this.DOM.serializeObject();
                    var defaultId = {};
                    
                    // 如果传递过来的menuType为5，则增加id 否则不增加
                    if (_this.customerMenuType){
                    	if (_this.customerMenuType * 1 === 5){
                    		defaultId[formConfig.idField] = _this.id;
                    	}
                    } else {
                    	defaultId[formConfig.idField] = _this.id;
                    }
                    
                    var data = $.extend({}, formDatas, defaultId, queryParams);
                    var beforeData = null;

                    if (onbeforesubmit && typeof onbeforesubmit === 'function') {
                        beforeData = onbeforesubmit(_this.DOM, data);
                    }
                    
                    if (beforeData === false) {
                        return;
                    }

                    data = $.extend(data, beforeData);
                    if (_this.dialog){
                    	var $dialog = _this.dialog.getDialogElement();
                        var $msgObj = $dialog.find(".dialog-tips");
                        var $buttonPost = $dialog.find(".btn-success");
                        var $buttonCancel = $dialog.find(".btn-default");

                        $buttonPost.prop('disabled', true).addClass('disabled');
                        $buttonCancel.prop('disabled', true).addClass('disabled');
                        $msgObj.html('<i class="icon icon-loading"></i> <span class="text text-left text-primary">正在处理，请稍后。。。</span>');
                    }
                    
                    Common.getDataByAjax(url, data, function(res) {
                        var flag = true;

                        if (res.success) {

                            if (onsuccess && typeof onsuccess === 'function') {
                                flag = onsuccess(res, _this.dialog);
                            }

                            if (flag === false) {
                                return;
                            }
                            
                            if (_this.dialog) {
                            	$msgObj.html('<span class="text text-left text-success">' + res.msg + '</span>');
                            	
                            	setTimeout(function() {
                                    Common.setCountdownPrompt(1, $msgObj, function() {
                                    	if (_this.dialog){
                                            _this.dialog.close();
                                    	}
                                        $(gridConfig.selector)[gridConfig.type]('reload');
                                    });
                                }, 1000);
                            }
                        } else {

                            if (onfailure && typeof onfailure === 'function') {
                                flag = onfailure(res, _this.dialog);
                            }

                            if (flag === false) {
                                return;
                            }
                            if (_this.dialog) {
                            	if (res.msg){
                                    $msgObj.html('<span class="text text-left text-danger">'+res.msg+'</span>');
                                } else {
                                    $msgObj.html('<span class="text text-left text-danger">处理失败，请重试。。。</span>');
                                }
                            	
                                $buttonPost.prop('disabled', false).removeClass('disabled');
                            }
                        }
                        if (_this.dialog) {
                        	$buttonCancel.prop('disabled', false).removeClass('disabled');
                        }
                    });
                }
            });
        },

        submitFileHandler: function() {
            var _this = this;
            var formConfig = this.options.formConfig;
            var gridConfig = this.options.gridConfig;
            var url = formConfig.url;
            var queryParams = formConfig.queryParams;
            var onbeforesubmit = formConfig.onbeforesubmit;
            var onsuccess = formConfig.onsuccess;
            var onfailure = formConfig.onfailure;
            var rules = formConfig.rules;
            var messages = formConfig.messages;
            var isImport = formConfig.isImport;
            var autoCalcMsgDialogHeight = formConfig.autoCalcMsgDialogHeight;


            this.DOM.validate({
                rules: rules,
                messages: messages,
                submitHandler: function(form) {

                    var formDatas = _this.DOM.serializeObject();
                    var defaultId = {};
                    
                    // 如果传递过来的menuType为5，则增加id 否则不增加
                    if (_this.customerMenuType){
                        if (_this.customerMenuType * 1 === 5){
                            defaultId[formConfig.idField] = _this.id;
                        }
                    } else {
                        defaultId[formConfig.idField] = _this.id;
                    }
                    
                    defaultId[formConfig.idField] = _this.id;
                    var data = $.extend({}, formDatas, defaultId, queryParams);
                    var beforeData = null;

                    if (onbeforesubmit && typeof onbeforesubmit === 'function') {
                        beforeData = onbeforesubmit(_this.DOM, data);
                    }

                    data = $.extend(data, beforeData);
                    
                    if (beforeData === false) {
                        return;
                    }

                    if (_this.dialog){
                        var $dialog = _this.dialog.getDialogElement();
                        var $msgObj = $dialog.find(".dialog-tips");
                        var $buttonPost = $dialog.find(".btn-success");
                        var $buttonCancel = $dialog.find(".btn-default");

                        $buttonPost.prop('disabled', true).addClass('disabled');
                        $buttonCancel.prop('disabled', true).addClass('disabled');
                        $msgObj.html('<i class="icon icon-loading"></i> <span class="text text-left text-primary">正在处理，请稍后。。。</span>');
                    }

                    $.ajaxFileUpload({
                        url: Common.pieceUrl(url),
                        secureuri: false,
                        fileElementId: _this.fileIds,
                        dataType: 'JSON',
                        data: data,
                        success: function(res) {
                            if (typeof res === 'string') {
                                var startIndex = res.indexOf('{');
                                var endIndex = res.lastIndexOf('}');
                                res = res.substring(startIndex, endIndex + 1);
                            }
                            
                            if (typeof res === 'string' && res.indexOf('Request Entity Too Large') !== -1) {
                                res = {
                                    success: 0,
                                    msg: '文件大小超出限制'
                                }
                            } else if (!res) {
                                res = {
                                    success: 0,
                                    msg: '上传失败，请稍后再试'
                                }
                            } else {
                                res = typeof res === 'string' ? JSON.parse(res) : res;
                            }
                            
                            if (res.success) {
                                if (onsuccess && typeof onsuccess === 'function') {
                                    flag = onsuccess(res, _this.dialog);
                                }

                                if (flag === false) {
                                    return;
                                }
                                
                                if (_this.dialog){
                                    $msgObj.html('<span class="text text-left text-success">' + res.msg + '</span>');
                                    
                                    setTimeout(function() {
                                        Common.setCountdownPrompt(1, $msgObj, function() {
                                            _this.dialog.close();
                                            $(gridConfig.selector)[gridConfig.type]('reload');
                                        });
                                    }, 1000);
                                }
                            } else {
                                if (onfailure && typeof onfailure === 'function') {
                                    flag = onfailure(res, _this.dialog);
                                }

                                if (flag === false) {
                                    return;
                                }
                                
                                if(isImport){
                                    var msg = res.msg;
                                    var height = 20;
                                    var width = 400;
                                    if (autoCalcMsgDialogHeight) {
                                        // 根据返回的行数计算dialog高度，默认20，多一行增加25。
                                        var arr = res.msg.split('<br>');
                                        height += (arr.length - 2 ) * 25;
                                        if (height > 480){
                                            height = 480;
                                        }
                                        width = 500;
                                    } else {
                                        height = 340;
                                    }
                                    
                                    var dialog2 = new Dialog({
                                        width: width,
                                        height: height,
                                        buttons: [{
                                            text: Lang.buttonCancel,
                                            click: function(e) {
                                                e.close();
                                            },
                                            styleName: 'btn-default'
                                        }],
                                        title: Lang.miantenanceBillErrorPrompt,
                                        content: res.msg
                                    });

                                    dialog2.open();
                                    if (_this.dialog){
                                        $msgObj.html('<span class="text text-left text-primary">' + Lang.msgLoadingError + '</span>');
                                    }
                                } else {
                                    if (_this.dialog){
                                        $msgObj.html('<span class="text text-left text-primary">' + res.msg + '</span>');
                                    }
                                }
                            }
                            if (!_this.dialog){
                                return;
                            }
                            
                            if ($msgObj.find('.text-success').length > 0){
                                $buttonCancel.attr('disabled', false).removeClass('disabled');
                            } else {
                                $buttonPost.prop('disabled', false).removeClass('disabled');
                                $buttonCancel.attr('disabled', false).removeClass('disabled');
                            }
                        },
                        error: function(res, status, e) {
                            alert(e);
                        }
                    });
                }
            });
        },

        getDialog: function() {
            return this.dialog;
        },

        getDOM: function() {
            return this.DOM;
        },
        
        setRules: function(rules){
        	this.options.formConfig.rules = rules;
        },
        
        // 20161230新增，可以动态添加数据至表单
        fillDataToForm: function(filldataConfig){
    		var filldataConfig = $.extend(true, {}, this.options.filldataConfig, filldataConfig);
    		this.options.filldataConfig = filldataConfig;
        	this.DOM[0].reset();
        	this.fillData();
        },
        
        updateConfig: function(config){
        	this.options = $.extend(true, {}, this.options, config);
        }
    };

    return ModuleBuildController;

}));