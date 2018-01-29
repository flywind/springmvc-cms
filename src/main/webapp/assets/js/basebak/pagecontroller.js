//页面构造函数
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'common', 'modulebuildcontroller', 'searchArgForm'], factory);
    } else {
        window.PageController = factory($, Common, ModuleBuildController, undefined);
    }
}(function($, Common, ModuleBuildController, undefined) {

    function PageController(opts) {
        this.options = $.extend(true, {}, PageController.defaults, opts);
        this.grid = $(this.options.datalist.selector);
        this.gridType = this.options.datalist.type === 'treegrid' ? 'treegrid' : 'datagrid';
        this.url = this.options.datalist.options.url;

        this.params = {};

        this.init();
    };

    PageController.defaults = {
        //搜索
        search: {
            selector: '[data-search-form="true"]', //搜索表单选择器
            elements: [], //搜索表单中的元素
            url: '', //要请求的action, 默认与datalist的url一致
            queryParams: '', //要请求的额外参数,
            onSuccess: function(e) {}, //搜索表单加载完后处理函数
            submit: function(e) {}, //表单提交处理函数, 如果return false, 则不执行默认代码
            reset: function(e) {}, //表单重置处理函数, 如果return false, 则不执行默认代码
            updateHandler: function(e) {},
            onbeforeupdate: function() {}
        },

        //操作按钮
        operations: {
            selector: '[data-operations="true"]', //操作按钮父级选择器
            btns: [],
            items: getDefaultItems()
        },

        //数据列表
        datalist: {
            selector: '[data-datagrid="true"]', //gridgrid选择器
            type: 'datagrid', //datagrid或者treegrid
            isClearSelections: true, //用于区分是否在reload列表时清除已勾选的数据
            options: {
                url: '',
                queryParams: {},
                sortName: "lastUpdateTime",
                sortOrder: "desc",
                showColumns: true,
                columns: [
                    [{
                        field: 'roleName',
                        title: Lang.roleName,
                        width: 200
                    }, {
                        field: 'roleAcountCount',
                        title: Lang.roleAcountCount,
                        width: 200
                    }]
                ]
            } //grid默认参数
        },
        
        gridConfig: {
        	selector: '[data-datagrid="true"]', //grid选择器
            type: 'treegrid' //datagrid或者treegrid
        }
    };

    PageController.prototype = {
        constructor: PageController,

        init: function() {
            //初始化搜索表单
            $(this.options.search.selector).searchArgForm(this.options.search);
            this.__resetOptions();
            this.__datalistHandler();
            this.__searchHandler();
            this.__buildBtns();
            this.__operationsHandler();
        },

        //重新处理下options
        __resetOptions: function() {
            var items = this.options.operations.items;

            if (!items) {
                return;
            }

            $.each(items, function(key, obj) {
                var sameItems = obj.sameItems;
                if ($.type(sameItems) === 'array' && sameItems.length > 0) {
                    for (var i = 0, len = sameItems.length; i < len; i++) {
                        items[sameItems[i]] = obj;
                    }
                }

            });
        },

        //搜索处理
        __searchHandler: function() {
            var _this = this;
            $(this.options.search.selector).on({
                submit: function(e) {
                    e.preventDefault();

                    var flag = true;
                    if (typeof _this.options.search.submit === 'function') {
                        flag = _this.options.search.submit(e, _this.grid);
                    }

                    $(_this.options.search.selector).find("button").attr('disabled', true).addClass('disabled').css('cursor', 'default');
                    setTimeout(function() {
                        $(_this.options.search.selector).find("button").attr('disabled', false).removeClass('disabled').css('cursor', 'pointer');
                    }, 1000);

                    if (flag === false) {
                        return;
                    }
                    
                    if (typeof _this.options.search.onbeforeupdate === 'function') {
                    	var data = _this.options.search.onbeforeupdate.call(_this);
                    }
                    
                    _this.options.search.queryParams = $.extend(_this.options.search.queryParams, data);

                    _this.url = _this.options.search.url || _this.url;
                    _this.params = $.extend({}, _this.options.search.queryParams, $(this).serializeObject());

                    _this.updateDatalist();
                },
                reset: function(e) {
                	e.preventDefault();
                    var flag = true;
                    if (typeof _this.options.search.reset === 'function') {
                        flag = _this.options.search.reset(e, _this.grid);
                    }

                    $(_this.options.search.selector).find("button").attr('disabled', true).addClass('disabled').css('cursor', 'default');
                    setTimeout(function() {
                        $(_this.options.search.selector).find("button").attr('disabled', false).removeClass('disabled').css('cursor', 'pointer');
                    }, 1000);
                    
                    $('[data-operations="true"]').find("button").attr('disabled', false).removeClass('disabled').css('cursor', 'pointer');

                    if (flag === false) {
                        return;
                    }
                    
                    if (typeof _this.options.search.onbeforeupdate === 'function') {
                    	var data = _this.options.search.onbeforeupdate.call(_this);
                    }
                    
                    _this.options.datalist.options.queryParams = $.extend(_this.options.datalist.options.queryParams, data);

                    _this.url = _this.options.datalist.options.url;
                    _this.params = _this.options.datalist.options.queryParams;

                    _this.updateDatalist();
                }
            });
        },
        
        __buildBtns: function(){
        	var btnsArray = this.options.operations.btns;
        	var isArray = btnsArray instanceof Array;
        	if(!(btnsArray && isArray && btnsArray.length)){
        		return;
        	}
        	
        	var selector = this.options.operations.selector;
        	var leftButtonStrArray = [];
        	var rightButtonStrArray = [];
        	
        	$.each(
        			btnsArray,
                    function(index, value) {
        				var menuType = value.menuType;
        				var menuName = value.menuName;
                        var menuNO = value.menuNO;
                        if (!Common.inMenuListByMenuNO(menuNO)) {
                        	return;
                        }
                        var classAlias = value.classAlias;
                        var menuClass = value.menuClass;
                        var iconTag = value.iconClass ? '<span class="' + value.iconClass + '"></span>' : '';
                        
                        if (menuType === 7){
                        	rightButtonStrArray.push('<button class="' + (value.menuClass || 'btn btn-default mr5') + '" data-menu-number="' + menuNO + '" data-menu-type="' + menuType + '" data-class-alias="' + classAlias + '" data-seed="0-1-' + menuNO + '">' + iconTag + menuName + '</button>');
                        } else if (menuType === 5){
                        	leftButtonStrArray.push('<button class="' + (value.menuClass || 'btn btn-default mr5') + '" data-menu-number="' + menuNO + '" data-menu-type="' + menuType + '" data-class-alias="' + classAlias + '" data-seed="0-1-' + menuNO + '">' + iconTag + menuName + '</button>');
                        }
                    }
            );
        	if(rightButtonStrArray.length){
        		$(selector).append('<div class="actions fr" data-addbutton-menu="true">' + rightButtonStrArray.join('\n') + '</div>').removeClass('hidden');
        	}
        	
        	if(leftButtonStrArray.length){
        		$(selector).append('<div class="actions fl" data-addbutton-menu="true">' + leftButtonStrArray.join('\n') + '</div>').removeClass('hidden');
        	}
        
        },

        //操作按钮处理
        __operationsHandler: function() {
            var _this = this;
            var items = this.options.operations.items;

            if (!items) {
                Debug.log('操作按钮未配置！');
                return;
            }

            $(this.options.operations.selector + ' button').on('click', function(e) {
                var menuNO = Common.stringToNumber($(this).attr('data-menu-number'));
                var classAlias = $(this).attr('data-class-alias');
                var url = $(this).attr('data-href');
                var menuType = $(this).attr('data-menu-type') * 1;
                var rows = _this.grid.datagrid("getSelections");
                var itemNO = _this.options.operations.items[menuNO];
                var sameItems = sameItems ? itemNO.sameItems : [];
                var flag = true;
                var id = rows[0] ? rows[0].id : '';
                var obj = {
                    rows: rows,
                    grid: _this.grid,
                    self: this,
                    url: url,
                    menuNO: menuNO,
                    classAlias: classAlias,
                    menuType: menuType
                };

                //未选择数据时提示信息并返回
                if (rows.length === 0 && menuType === 5) {
                    Common.msgDialog(Lang.msgPleaseSelectDataFirst, Lang.alertTitlePrompt, 'warning');
                    return;
                }

                //没有配置不处理并返回
                if (!itemNO && (classAlias === 'add'  || classAlias === 'ADD')  && (classAlias === 'edit'  || classAlias === 'EDIT')) {
                    Debug.log('此操作按钮未配置！');
                    return;
                }

                //没有配置
                if (!itemNO) {
                    Debug.log('你操作的按钮没有配置!');
                    return;
                }

                //追加默认参数
                itemNO = $.extend(true, {
                    sortParams: true, //操作某模块时，是否继承默认排序参数，默认是 true
                    customColumnParams: true, //操作某模块时，是否继承自定义列的参数，默认是 true
                    afferentColumnParams: '', //操作某模块时，是否加入传入的列的参数，默认是null
                    singleSelect: true, //是否单选,如果为false,则支持多选
                    gridConfig: {
                    	selector: _this.options.datalist.selector, //grid选择器
                        type: _this.options.datalist.type //datagrid或者treegrid
                    },
                    dialogConfig:{
                    	componentId: 'operationsDialog'+menuNO, //组件id
                    	componentRelation: 'pagecontroller > modulebuildcontroller > dialog' //组件关系
                    }
                }, itemNO);

                //如果有回调则执行回调
                if (itemNO && typeof itemNO.click === 'function') {
                    flag = itemNO.click(obj);
                }

                //如果回调返回false则不执行下面的操作
                if (flag === false) {
                    return;
                }

                //删除
                if (classAlias === 'delete' || classAlias === 'DELETE') {
                    _this.__deleteHandler(rows, itemNO);
                    return;
                }

                //导出
                if (classAlias === 'export' || classAlias === 'EXPORT') {
                    _this.__exportHandler(rows, itemNO);
                    return;
                }

                if (id && itemNO && itemNO.filldataConfig && itemNO.filldataConfig.queryParams) {
                    itemNO.filldataConfig.queryParams[itemNO.filldataConfig.idField || 'id'] = id;
                }
                
                // 增加一个参数menuType，传递到ModuleBuildController，以便确定是传递id的操作，还是不需要传递
                //其他
                _this.modulebuildcontroller = new ModuleBuildController(itemNO, rows, menuType);

            });
        },

        //删除默认处理
        __deleteHandler: function(rows, itemNO) {
            var _this = this;
            var ids = [];
            var postData = {};
            
            $.each(rows, function(i, row) {
//                if (row && row['isDefault']) {
//                    Common.msgDialog(Lang.msgDefaultRoleCanNotDelete, Lang.alertTitlePrompt, 'warning');
//                    return true;
//                }
                ids.push(row.id);
            });

            //没有可操作的row
            if (ids.length === 0) {
                Common.msgDialog(Lang.msgDoesNotOperationData, Lang.alertTitlePrompt, 'warning');
                return;
            }

            //根据单选或多选填请求参数
            if (this.options.datalist.options.singleSelect) {
                postData.id = ids.join(',');
            } else {

                if (!itemNO) {
                    postData.id = ids.join(',');
                    return;
                }

                if (itemNO.singleSelect !== false) {
                    postData.id = ids.join(',');
                }

                if (itemNO.singleSelect === false) {
                    postData.ids = ids.join(',');
                }
            }
            
            var message = '';
            if (itemNO.message){
            	message = itemNO.message;
            } else {
            	message = Lang.msgDoYouWantToDelete;
            }
            
            //请求删除接口
            Common.confirmDialog(message, Lang.alertTitlePrompt, function(d) {
            	var $dialog = d.getDialogElement()
            	var $msgObj = $dialog.find('.dialog-tips');
            	var $dialogBody = $dialog.find('.dialog-body');
                var $buttonPost = $dialog.find(".btn-success");
                var $buttonCancel = $dialog.find(".btn-default");
            	$msgObj.html('<span class="text"><i class="icon icon-loading"></i>' + Common.langHandler('isDeleting', '正在删除...') + '</span>');
            	$buttonPost.addClass('disabled').attr('disabled', 'disabled');
            	$buttonCancel.addClass('disabled').attr('disabled', 'disabled');
            	
                Common.getDataByAjax(Common.pieceUrl(itemNO.url), $.extend({}, postData, itemNO.queryParams), function(res){
                    if (res.success) {
                    	$msgObj.html('<span class="text-success"><span class="fa-stack"><i class="fa fa-iot-round fa-stack-mx green-color"></i><i class="fa fa-iot-module-online fa-stack-mx white-color"></i></span>' +  (res.msg || Common.langHandler('deleteSuccess', '删除成功！')) + '</span>');
                    	$buttonPost.removeClass('disabled').removeAttr('disabled');
                    	$buttonCancel.removeClass('disabled').removeAttr('disabled');
                        _this.grid[_this.gridType]("reload");
                    	setTimeout(function(){
                         	Common.setCountdownPrompt(1, $msgObj, function(){
                         		d.close();
                         	});
                    	}, 1000);
                    } else {
                    	$buttonPost.hide();
                    	$buttonCancel.removeClass('disabled').removeAttr('disabled');
                    	var length = res.msg.length;
                    	if (length > 18) {
                    		$dialogBody.html('<span class="text-danger"><span class="fa-stack"><i class="fa fa-iot-round fa-stack-mx red-color"></i><i class="fa fa-iot-module-offline fa-stack-mx white-color"></i></span>' + res.msg + '</span>');
                    		$msgObj.html('');
                    	} else {
                    		$msgObj.html('<span class="text-danger"><span class="fa-stack"><i class="fa fa-iot-round fa-stack-mx red-color"></i><i class="fa fa-iot-module-offline fa-stack-mx white-color"></i></span>' + (res.msg || Common.langHandler('deleteFailed', '删除失败！')) + '</span>');
                    	}
                    }
                });
                return false;
            });
        },

        //导出默认处理
        __exportHandler: function(rows, itemNO) {

            var $table = this.grid.parents('[data-datagrid-box="true"]');
            var obj = Common.getDatagridSort($table);
            var sortParams = itemNO.sortParams ? {
                'sort': obj.sortName,
                'order': obj.sortOrder
            } : null; //导出继承排序的参数

            //加入传进来的列名参数
            var customColumnParamsStr = Common.getSelectItemForCustomColumnBar();
            
            if (itemNO.afferentColumnParams) {
                customColumnParamsStr = customColumnParamsStr + ',' + itemNO.afferentColumnParams;
            }
            
            var customColumnParams = itemNO.customColumnParams ? {
                'showColumns': customColumnParamsStr
            } : null; //导出继承自定义列的参数
            var expotrParams = $.extend({}, $(this.options.search.selector).serializeObject(), sortParams, customColumnParams, itemNO.queryParams);
            var getParams = "";

            $.each(expotrParams, function(i, v) {
            	if(i === 'searchName'){
            		getParams += i + "=" + encodeURIComponent(encodeURIComponent(v)) + "&"; //把+号也编码
            		return true;
            	}
                getParams += i + "=" + encodeURI(encodeURI(v)) + "&"; //js的encodeURI需要两次编码，具体原理请百度
            });

            this.__downloadHandler(Common.pieceUrl(itemNO.url) + "&" + getParams);
        },

        //数据列表处理
        __datalistHandler: function() {
            Common[this.gridType](this.options.datalist.options, this.grid);
        },

        //更新数据列表
        updateDatalist: function() {
            var gridOptions = this.grid[this.gridType]('options');
            gridOptions.url = Common.pieceUrl(this.url);
            gridOptions.queryParams = this.params;
            
            var selectedArr = this.grid[this.gridType]('getSelections');
            var selectedCopyArr = [].concat(selectedArr);
            if (this.options.datalist.isClearSelections) {
            	this.grid[this.gridType]('clearSelections');
            }
            this.grid[this.gridType]('reload');
            
            if (typeof this.options.search.updateHandler === 'function') {
            	this.options.search.updateHandler.call(this, selectedCopyArr);
            }
        },

        //导出--流下载处理函数 
        __downloadHandler: function(url) {
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
        },

        //返回datagrid对象
        getGrid: function() {
            return this.grid;
        },
        
        getModulebuildcontroller: function(){
        	return this.modulebuildcontroller;
        }
    };

    //操作按钮项的默认参数
    function getDefaultItems() {
        /*
        operations.items的默认参数：
        {
            1001: {
            	//模板内容配置
            	templateConfig: {
                    content: '', //第一种，string，模板内容是一个html字符串
                    formUI: null, //第二种，formUI，模板内容是通过formUI构造出来
                    url: '', //第三种，要请求的模板路径  例如，“/1.1/common/template/userCreateUpdate.tpl”
                    queryParams: {}, //要请求的额外参数
                    onsuccess: function (res) {} //导入配置
                },
                //填充数据配置
                filldataConfig: {
                	idField: 'id', //唯一标识的字段名称
                    url: '', //要请求数据的action
                    queryParams: {}, //要请求的额外参数
                    onbefore: function (res) {},  //填充数据之前的处理函数
                    onsuccess: function (res) {}  //填充成功后的处理函数
                },
            	//表单配置
                formConfig: {
                	idField: 'userId',
                    url: '', //表单提交请求的接口
                    queryParams: {}, //要请求的额外参数
                    rules: {}, //表单验证规则
                    messages: {},
                    isFileUpload: false,  // 是否是上传文件类型
                    onbeforesubmit: function(res){},  //表单提交之前的处理函数，不影响表单提交
                    onsuccess: function(res, dialog) {},  //表单提交成功之后的处理函数，覆盖常规的处理后流程
                    onfailure: function(res, dialog) {},  //表单提交失败之后的处理函数，覆盖常规的处理后流程
                },
            	//弹窗配置
                dialogConfig: {
                	dialog: true, //是否使用dialog
                	autoOpen:true,
                    width: 500,
                    height: 500,
                    title: Common.langHandler('warmTips', '温馨提示')
                },
                //grid配置
                gridConfig: {
                	selector: '[data-datagrid="true"]', //grid选择器
                    type: 'datagrid' //datagrid或者treegrid
                },
                url: '', //适用于只有一个接口的功能模块，例如 删除、导入、导出
                click: function(data) {} //菜单为1001按钮点击后的回调函数, 如果return false,则不执行默认代码
            }
        }
        */

        return null;
    }

    return PageController;

}));