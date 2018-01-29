/* Dialog对话框 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'plugs', 'addresscontroller', 'common', 'layoutsize', 'inputui', 'datetimepicker'], factory);
    } else {
        window.SearchArgForm = factory(jQuery, undefined, AddressController, Common, LayoutSize, InputUI);
    }
}(function($, undefined, AddressController, Common, LayoutSize, InputUI) {
    /**存放的key**/
    var key = "data-search-arg-index";
    var obj_key = "data-search-obj-key";
    var obj_arg = "data-search-obj-arg";
    var inputUIObj = {};

    function SearchArgForm(elem, opts) {
        this.areaArray = {};

        this.elem = elem;
        this.options = $.extend(true, {}, SearchArgForm.defaults, opts);

        this.methods = {
            "newLine": this.__newLine,
            "input": this.__input,
            "selectbox": this.__selectbox,
            "submit": this.__submit,
            "reset": this.__reset,
            "moreSwitch": this.__moreSwitch,
            "area": this.__area,
            "dateTime": this.__dateTime,
            "span": this.__span,
            "cusomer": this.__cusomer,
            "autocomplete": this.__autocomplete,
            "generateElem": this.__generateElem
        };
        this.init();
    }

    /**初始化默认变量**/
    SearchArgForm.defaults = {
        elements: [
            /*{
			type:"input",
			id:"",
			name:"",
			title:"",
			css:""
			
		},{
			type:"time",
			id:"",
			name:"",
			title:"",
			css:""
		},{
			type:"area",
		},{
			type:"selectbox",
		 	url: '', //请求地址
		 	data: [], //数组对象与URL互斥
		  	value: [], //初始值
			height: 29, // 高
	        width: 200, // 宽
	        placeholder: langHandler('pleaseSelect', "请选择"), //默认项,例如请选择
	        queryParams: {}, //请求参数
	        singleSelect: true, //是否单选, 如果为false就是多选
	        checkbox: true, //当多选时，是否显示checkbox
	        autocomplate: false, //是否自动匹配
	        fieldText: 'name', //用作显示文字的字段名称
	        fieldValue: 'value', //用作select的value值的字段名称
	        onSelect: function (row) {},
	        onUnselect: function (row) {},
	        onselectAll: function (rows) {},
	        onUnselectAll: function (rows) {}
		},
		{
			type:"submit",
			onSubmit:function
		},
		{
			type:"resetBtn",
		},
		{
			type:"newLine"
		}
		,
		{
			type:"button",
			id:"",
			name:"",
			css:"",
			click:function(elem){}
		},
		{
			type:"moreSwitch",
			title:"",
			id:"",
			name:"",
			css:"",
			subDivCss:"",
			click:function(){},
			elements:[
		         {
		        	 
		        	 
		         },{
		        	 
		         }
			]
		}*/
        ],
        onSuccess: ''
    };

    SearchArgForm.prototype = {

        constructor: SearchArgForm,

        /**初始化搜索条件**/
        init: function() {
            var _this = this;

            if (_this.options.elements && _this.options.elements.length > 0) {
                var $html = $('<div class="form-group form-grid form-group-search form-group-moreswitch-main" data-searc-form="true"><ul class="list-inline list-autowidth"></ul></div>');
                _this.elem.html("").append($html);
                $.each(_this.options.elements, function(i, v) {
                    _this.__generateElem(v, $html, _this, i);
                });
            }

            $.each(_this.options.elements, function(i, v) {
                if (v.type === "submit") {
                    if (v.isAccurate) {
                        //Common.changeEventOfSwitchButton({btn:$html.find("button[type='submit']")});
                    }
                }
            });
            
            var obj = null;
            if(this.elem){
            	obj = this.elem.find('.input-text[data-input-ui!="false"]')
            }

            inputUIObj = new InputUI(obj);

            /**成功后执行函数**/
            if (typeof _this.options.onSuccess === "function") {
                _this.options.onSuccess(_this.elem);
            }

            var layoutSize = new LayoutSize();

            layoutSize.setRightDatagridHeight();
        },

        /**换行**/
        __newLine: function(obj, $elem, searchArgForm, i) {
            $('<ul class="list-inline list-autowidth" style="margin-top:10px;" ></ul>').insertAfter($elem.find(">ul:last"));
        },

        /**输入框**/
        __input: function(obj, $elem, searchArgForm, i) {
            var width = obj.width || 120;
            var height = obj.height || 30;
            var objId = obj.id ? 'id="' + obj.id + '"' : 'id=""';
            var readonly = obj.readonly ? 'readonly' : '';
            var html = '<li class="form-item" data-index-item="' + i + '"><input style="width:' + width + 'px;height:' + height + 'px" class="input-text search-input-text' + (obj.css || "") + '" type="text" ' + objId + ' name="' + (obj.name || "") + '" placeHolder="' + (obj.placeholder || "") + '" '+readonly+'/></li>';
            $elem.find(">ul:last").append($(html));
        },

        /**下拉框**/
        __selectbox: function(obj, $elem, searchArgForm, i) {
            var html = '<li class="form-item" data-index-item="' + i + '"><select /> </li>';
            var $html = $(html);
            $html.find("select").selectbox(obj);
            $elem.find(">ul:last").append($html);
        },

        /**submit**/
        __submit: function(obj, $elem, searchArgForm, i) {
            var _this = this;
            var html = '<li class="form-item" data-index-item="' + i + '"><button type="submit" class="new-btn btn-new-search" ' + (obj.id ? 'id="' + obj.id + '"' : '') + (obj.name ? 'name="' + obj.name + '"' : '') + ' data-search-submit="true" data-seed="2-1-submit"><i class="fa fa-search mr5"></i><span>' + (obj.placeholder || Lang.buttonSearch) + '</span></button></li>';

            $html = $(html);

            if (typeof obj.onSuccess === "function") {
                $html.find("button").click(function(e) {
                    e.preventDefault();
                    obj.onSuccess(searchArgForm.elem, $html.find("button"));
                    return false;
                });
            } else {
                /*$html.find("button").click(function(e){
                	e.preventDefault();
                	return false;
                });*/
            }

            $elem.find(">ul:last").append($html);
        },

        /**reset**/
        __reset: function(obj, $elem, searchArgForm, i) {
            var html = '<li class="form-item" data-index-item="' + i + '">' + '<button type="reset" class="new-btn btn-new-reset" ' + (obj.id ? 'id="' + obj.id + '"' : '') + (obj.name ? 'name="' + obj.name + '"' : '') + ' data-search-reset="true" data-seed="2-1-reset"><i class="fa fa-eraser mr5"></i><span>' + (obj.placeholder || Lang.buttonRest) + '</span></button></li>';

            var $html = $(html);
            var _this = this;

            $html.find("button").on('click', function() {
            	
            	var elemObj = null;
                if($elem){
                	elemObj = $elem.find('.input-text[data-input-ui!="false"]');
                	$.each(elemObj, function(i, v){
                		$(v).parent().find('.icon').hide();
                	});
                }
                
            	inputUIObj.addPlaceholder();
            	
                var flag = true;

                if (typeof obj.onBefore === "function") {
                    flag = obj.onBefore(searchArgForm.elem);
                }

                /**默认将所有的元素置为初始值**/
                if (flag !== false) {
                    searchArgForm.reset();
                }
                if (typeof obj.onReset === "function") {
                    obj.onReset(searchArgForm.elem, $html.find("button"));
                    return false;
                }
            });

            $elem.find(">ul:last").append($html);
        },

        /**更多筛选**/
        __moreSwitch: function(obj, $elem, searchArgForm, index) {
            var html = '<li class="form-item" data-index-item="' + index + '">' + '<span class="switch-link switch-left" data-switch-title="moreSwitch"><span data-lang="moreSearch" >' + (obj.placeholder || Lang.moreSearch) + '</span><i class="s s-down" data-switch-icon="true"></i></span></li>';

            $elem.find(">ul:last").append($(html));

            if (obj.elements && obj.elements.length > 0) {
                $moreSwitchDiv = $('<div class="form-group form-grid form-group-search form-group-moreswitch-list-new hidden pb10" data-switch-content="moreSwitch"><ul class="list-inline list-autowidth"></ul></div>');
                
                var generateElem = searchArgForm.methods["generateElem"];
                
                $.each(obj.elements, function(i, v) {
                    generateElem(v, $moreSwitchDiv, searchArgForm, index + "_" + i);
                });

                $moreSwitchDiv.insertAfter($elem);
                
                $elem.find("[data-switch-title='moreSwitch']").click(function() {
                    if ($moreSwitchDiv.hasClass("hidden")) {
                        $moreSwitchDiv.removeClass("hidden");
                        $elem.find(".s-down").addClass("s-up").removeClass("s-down");
                    } else {
                        $moreSwitchDiv.addClass("hidden");
                        $elem.find(".s-up").addClass("s-down").removeClass("s-up");
                    }
                    var layoutSize = new LayoutSize();
                    layoutSize.setRightDatagridHeight();
                });

            }
        },

        /**省市区**/
        __area: function(obj, $elem, searchArgForm, i) {
            var html = '<li class="form-item ' + (obj.css || "") + '" data-index-item="' + i + '" data-type="area" ></li>';
            $html = $(html);
            obj.selector = $html;
            var address = new AddressController(obj);
            $html.data(obj_key, address);
            $elem.find(">ul:last").append($html);
        },

        /**时间控件**/
        __dateTime: function(obj, $elem, searchArgForm, i) {
            var width = obj.width || 120;
            var height = obj.height || 29;
            var readonly = obj.readonly ? 'readonly' : '';
            var html = '<li class="form-item" data-index-item="' + i + '"><input style="width:' + width + 'px;height:' + height + 'px"  class="input-text ' + (obj.css || "") + '" type="text" ' + (obj.id ? 'id="' + obj.id + '"' : '') + '" name="' + (obj.name || "") + '"' + ' placeHolder = "' + (obj.placeholder || "") + '" '+readonly+'/></li>';
            var $html = $(html);
            var $input = $html.find("input");

            $input.datetimepicker($.extend({
                onChangeDateTime: function() {
                    InputUI.prototype.removePlaceholder($input[0]);
                }
            }, obj.config));
            
            $elem.find(">ul:last").append($html);
        },

        /**文字**/
        __span: function(obj, $elem, searchArgForm, i) {
            var html = '<li class="form-item ' + obj.css + '" data-index-item="' + i + '">' + obj.value + '</li>';
            $elem.find(">ul:last").append($(html));
        },

        /**自定义**/
        __cusomer: function(obj, $elem, searchArgForm, i) {
        	var html = '<li class="form-item ' + obj.css + '" data-index-item="' + i + '">' + obj.value + '</li>';
            $elem.find(">ul:last").append($(html));
        },

        /**自动匹配**/
        __autocomplete: function(obj, $elem, searchArgForm, i) {
            var html = '<li class="form-item" data-index-item="' + i + '"><input class="input-text ' + (obj.css || "") + '" type="text" ' + (obj.id ? 'id="' + obj.id + '"' : '') + '" name="' + (obj.name || "") + '"' + ' placeHolder = "' + (obj.placeholder || "") + '" /></li>';
            var $html = $(html);

            Common.autocomplete({
                fieldName: obj.fieldName,
                url: obj.url,
                forceMatching: false,
                max: 20,
                extraParams: obj.extraParams,
                parse: function(data) {
                    data = typeof data === 'string' ? JSON.parse(data) : data;
                    return $.map(data.rows, function(row) {
                        return {
                            data: row,
                            value: row[obj.dataName],
                            result: row[obj.dataName]
                        };
                    });
                },
                formatItem: function(row, i, max) {
                    return row[obj.dataName];
                }
            }, $html.find("input")); //当前input ID

            $elem.find(">ul:last").append($html);
        },

        /**重新初始化**/
        reload: function(opts) {
            alert("reload");
        },

        /**重新设置url**/
        setSearchUrl: function(url) {
            alert("setSearchUrl");
        },

        /**添加参数**/
        addParams: function(params) {
            alert("addParams");
        },

        /**重置**/
        reset: function(opts, elem) {
            var _this = this;
            var selectElement = _this.__getSelectElementArray(opts);
            $.each(selectElement, function(i, v) {
                if (v.type === "area") {
                    var address = _this.elem.find("[data-index-item='" + v.index + "']").data(obj_key);
                    address.reset();
                }
                if (v.type === "selectbox") {
                    var $select = _this.elem.find("[data-index-item='" + v.index + "']").find("select");
                    $select.selectbox("reset");
                } else {
                    var $input = _this.elem.find("[data-index-item='" + v.index + "']").find("input");
                    $input.val(v.value || "");
                }
            });
        },

        /**隐藏**/
        __hide: function(opts) {
            var _this = this;
            var selectElement = _this.__getSelectElementArray(opts);
            var l = 0;

            $.each(selectElement, function(i, v) {
                if ((v.index + "").indexOf("_") > 0) {
                    l++;
                }
                _this.elem.find("[data-index-item='" + v.index + "']").addClass("hidden");
            });
            
            $.each(_this.options.elements, function(i, v) {
                if (v.type === "moreSwitch") {
                    if (l === v.elements.length) {
                        _this.elem.find("[data-index-item='" + i + "']").addClass("hidden");
                    }
                }
            });


        },

        __getValue: function(opts) {
            var _this = this;
            var selectElement = _this.__getSelectElementArray(opts);

            selectElement = $.isArray(selectElement) ? selectElement[0] : selectElement;
            if (selectElement.type === "input") {
                return _this.elem.find("[data-index-item='" + selectElement.index + "']").find("input").val();
            } else if (selectElement.type === "selectbox") {
                var $select = _this.elem.find("[data-index-item='" + selectElement.index + "']").find("select");
                return $select.selectbox("getValue");
            }
        },
        
        __setValue: function(opts){
        	 var _this = this;
        	 var selectElement = _this.__getSelectElementArray(opts);
        	 selectElement = $.isArray(selectElement) ? selectElement[0] : selectElement;
        	 if (selectElement.type === "input") {
                 return _this.elem.find("[data-index-item='" + selectElement.index + "']").find("input").val(opts.value);
             } 
        },
        
        /**清除**/
        clear: function(opts) {
            if (opts) {
                return;
            }
        },

        __matchingElement: function(opts, obj) {
            var element = [];
            var _this = this;

            if ($.isArray(opts)) {
                $.each(opts, function(ii, vv) {
                    var e = _this.__matchingInObj(vv, obj);
                    if (e) {
                        element.push(e);
                    }
                });
            } else {
                var e = _this.__matchingInObj(opts, obj);
                if (e) {
                    element.push(e);
                }
            }
            
            return element;
        },

        __matchingInObj: function(opts, obj) {
            var element;

            $.each(opts, function(i, v) {
                if (i === "name") {
                    if (v === obj.name) {
                        element = obj;
                    }
                } else if (i === "id") {
                    if (v === obj.id) {
                        element = obj;
                    }
                } else if (i === "index") {
                    if (v === obj.index) {
                        element = obj;
                    }
                }
            });

            return element;
        },

        __getSelectElementArray: function(opts) {
            var _this = this;
            var selectElement = [];

            if (opts) {
                $.each(this.options.elements, function(i, v) {
                    if (v.type === "moreSwitch") {
                        $.each(v.elements, function(i2, v2) {
                            v2.index = i + "_" + i2;
                            var ele_m = _this.__matchingElement(opts, v2);
                            if (ele_m.length > 0) {
                                selectElement = selectElement.concat(ele_m);
                            }
                        });
                    } else {
                        v.index = i;
                        var ele_m = _this.__matchingElement(opts, v);
                        if (ele_m.length > 0) {
                            selectElement = selectElement.concat(ele_m);
                        }
                    }
                });
            }

            //选择所有的元素
            else {
                $.each(this.options.elements, function(i, v) {
                    if (v.type === "moreSwitch") {
                        $.each(v.elements, function(i2, v2) {
                            v2.index = i + "_" + i2;
                            selectElement.push(v2);
                        });
                    } else {
                        v.index = i;
                        selectElement.push(v)
                    }
                });
            }

            return selectElement;
        },

        __generateElem: function(obj, $elem, searchFormArg, i) {

            var method = searchFormArg.methods[obj.type];

            if (typeof method === "function") {
                method(obj, $elem, this, i);
            }
        }
    };

    $.fn.searchArgForm = function(method, arg) {
        //第一个参数是对象，即为创建
        if (typeof method === "object") {
            if (!$(this).data(key)) {
                var searchArgForm = new SearchArgForm($(this), method);
                $(this).data(key, searchArgForm);
            }
        } else if (typeof method === "string") {
            var searchArgForm = $(this).data(key);
            if (method === "hide") {
                searchArgForm.__hide(arg);
            } else if (method === "getValue") {
                return searchArgForm.__getValue(arg);
            }else if(method === "setValue") {
            	 return searchArgForm.__setValue(arg);
            }
        }
    };

    return SearchArgForm;
}));