/* Dialog对话框 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'formui'], factory);
    } else {
        window.Dialog = factory(jQuery, FormUI);
    }
}(function($, FormUI) {
	
	var usedTimes = 0;
	var componentName = 'dialog';
	
    function Dialog(option) {
        this._option = $.extend(true, {
        	componentId: '', //组件id
        	componentRelation: '', //组件关联关系
            showMask: true, //是否显示遮罩
            showTitle: true,
            title: "", //对话框标题
            content: "", //对话框内容
            dialogClass: '', // 对话框的class类
            tips: '', //小提示信息，显示在按钮左边
            width: "auto", //宽
            height: "auto", //高
            mediaBox: false, //视频对话框，用于dialog-body样式 
            autoOpen: false, //是否初始化后自动打开
            buttons: [], //按钮,[{text:"确定", click:function(){}, styleName:'btn-default'}]
            onClose: function() {}, //关闭时的回调

            disableClose: false, //禁用关闭

            drag: true, //是否可拖动

            isForm: false, //是否创建表单
            formID: '', //表单ID
            elements: null, //表单参数
            grid: false, //是否横着排

            formUIConfig: {
                formID: '',
                isTwoCol: true,
                formParentElement: null,
                formClassName: 'form-popup',
                elements: null,
                listClassName: 'list-unstyled',
                method: 'post',
                action: ''
            },

            loadConfig: {
                templatePath: '', //要load的路径
                onsuccess: function(res) {} //回调
            },

            isLoad: false, //是否load
            loadWrapper: null, //load进来的元素的包裹元素: $('<form id="xxxx" action="xxxx.do" method="post"></form>')
            url: '', //如果有url则load进来
            params: null, //参数
            onloadsuccess: function(target) {}, //load回调
            onloadDataSuccess: function() {}, //isLoad=true时的回调，与onloadsuccess区分
        }, option);

        if (this._option.showMask) {
            this._masker = this.__getMasker().appendTo("body");
        }

        this._dialog = this.__buildDialog();

        if (this._option.drag) {

            this.__bindEventOfDrag();
        }
        
        if (typeof this._option.onloadsuccess === 'function') {
            this._option.onloadsuccess.call(this, this._dialog);
        }

        if (this._option.autoOpen) {
            this.open();
        }
        
        this.__bindEventOfScroll();
        
        usedTimes++;
    };

    Dialog.prototype = {

        constructor: Dialog,

        defaults: {
            minHeight: 25, //窗口内容最小高度
            minWidth: 300, //窗口内容最小宽度
            space: 80 //窗口距离屏幕边缘边距
        },

        __buildDialog: function() {
            var jqWin = $(window);
            var scrW = window.innerWidth || jqWin.width();
            var scrH = window.innerHeight || jqWin.height();
            var dlg = this.__getDialog();
            var header = this.__getHeader(this._option.title);
            var closer = this.__getCloser();
            var con = this.__getContenter();
            var body = $("body").append(con);
            var tips = this.__getFooterTips(this._option.tips);
            var footer = this.__getFooter();
            var defs = Dialog.prototype.defaults;
            var space = defs.space;
            var minW = defs.minWidth;
            var minH = defs.minHeight;
            var conW = 0;
            var conH = 0;
            var scroll = body[0].getBoundingClientRect();
            var buttons = this._option.buttons;
            var buttonLength = buttons.length;
            var w = this._option.width;
            var h = this._option.height;
            var dlgW = w;
            var dlgH = h;


            conW = Math.max(minW, typeof w === 'string' ? minW : w);
            conW = Math.min(conW, scrW - space * 2);
            conH = Math.max(minH, typeof h === 'string' ? minH : h);
            conH = Math.min(conH, scrH - header.outerHeight() - footer.outerHeight() - space * 2);
            
            if (this._option.height == '100%'){
            	conH = window.innerHeight - header.outerHeight() - footer.outerHeight() - space * 2 + 20;
            }

            con.width(conW).height(conH);

            dlg.hide();

            if (!this._option.disableClose) {
                header.append(closer);
            }
            
            if (this._option.showTitle) {
            	dlg.append(header).append(con);
            } else {
            	dlg.append(con);
            }

            if (buttonLength) {
                dlg.append(footer.append(tips).append(this.__getButtonPanel(buttons)));
            }

            dlg.appendTo(body);

            dlg.append($('<iframe style="width:100%;height:100%;position:absolute;top:0;z-index:-1;border:0;" frameborder="0"></iframe>'));

            if (w === 'auto') {
                con.width('auto');
            }

            if (h === 'auto') {
                con.height('auto');
            }

            dlgW = dlg.outerWidth();
            dlgH = dlg.outerHeight();
            dlg.css({
                left: Math.floor((scrW) / 2 - (dlgW / 2)),
                top: Math.floor((scrH) / 2 - (dlgH / 2))
                    //marginLeft: -(Math.floor(dlgW / 2)),
                    //marginTop: -(Math.floor(dlgH / 2))
            });

            /* 解决浏览器缩放dialog会自动适应宽度 */
            $(window).resize(function() {
                dlgW = dlg.outerWidth();
                dlgH = dlg.outerHeight();

                var winWidth = $(window).width();
                var winHeight = $(window).height();

                dlg.css({
                    left: Math.floor((winWidth) / 2 - (dlgW / 2)),
                    top: Math.floor((winHeight) / 2 - (dlgH / 2))
                });
            });

            dlg.css('z-index', this.__getZIndex(2));
            
            this.body = con;

            return dlg;
        },

        __getDialog: function() {
        	var classValue = 'dialog';
        	classValue += this._option.dialogClass ? ' ' + this._option.dialogClass : '';
            return $('<div class="' + classValue + '" data-componentid="' + this.getComponentId() + '" data-componentname="' + this.getComponentName() + '" data-componentrelation="' + this.getComponentRelation() + '"></div>');
        },

        __getContenter: function() {
            var $html = this._option.content;
            var templatePath = this._option.loadConfig.templatePath;
            var loadParams = this._option.loadConfig.queryParams;
            var loadHandler = this._option.loadConfig.onsuccess;
            var dialogbodycss = this._option.mediaBox ? 'dialog-body-media' : '';
            var formUI = null;
            var _this = this;

            if ($html && $.type($html) === 'string') {
                return $('<div class="dialog-body ' + dialogbodycss + '">' + $html + '</div>');
            }

            //使用load方式
            if (templatePath) {
                var $dbody = $('<div class="dialog-body ' + dialogbodycss + '"></div>');

                $.get(templatePath, loadParams, function(res) {

                    _this.DOM = $(res);
                    
                    _this.body.append(res);

                    var flag = loadHandler($(res), _this._dialog);

                    if (flag === false) {
                        return;
                    }
                    
                    

                });

                return $dbody;
            }

            //加载表单或str 
            if (this._option.formUIConfig.elements) {
                formUI = new FormUI(this._option.formUIConfig);
                $html = formUI.getForm();
            } else if (this._option.isForm === true) {
                formUI = new FormUI({
                    formID: this._option.formID,
                    action: this._option.action,
                    elements: this._option.elements,
                    grid: this._option.grid,
                    isTwoCol: this._option.isTwoCol
                });

                $html = formUI.getForm();
            }

            this.context = $html;

            return $('<div class="dialog-body ' + dialogbodycss + '"></div>').append($html);

        },

        __getHeader: function(title) {
            return $("<div class='dialog-heading'></div>").append("<h3 class='dialog-title'>" + title + "</h3>");
        },

        __getCloser: function() {
            var _this = this;

            return $("<span href='javascript:;' class='dialog-close'>x</span>").click(function() {
            	_this._dialog.find('input').blur();
            	$(".tangram-suggestion-main").css('display', 'none');
            	_this.close();
            });
        },

        __getMasker: function() {
            var mask = $("<div class='mask'></div>");

            mask.css('z-index', this.__getZIndex(1));
            mask.hide();

            if ($.browser.msie && $.browser.version.indexOf("6") === 0) {
                var rect = document.body.getBoundingClientRect();
                mask.css({
                    height: rect.bottom - rect.top
                });
            }

            return mask;
        },

        __getButtonPanel: function(buttons) {
            var panel = $("<div data-dialog-buttons='true' class='dialog-actions'></div>");
            var i = 0;
            var l = buttons.length;

            for (; i < l; i++) {
                panel.append(this.__getButton(buttons[i]));
            }

            return panel;
        },

        __getButton: function(button) {
            return $("<button type='button' class='btn btn-middle mr10 " + button.styleName + "'><span><span>" + button.text + "</span></span></button>").click($.proxy(function(f, e, dom) {
                if (typeof f === "function") {
                    f(this, e, dom);
                }
            }, this, button.click));
        },

        __getFooter: function() {
            return $('<div class="dialog-footer"></div>');
        },

        __getFooterTips: function(tips) {
            return $('<div class="dialog-tips"></div>').html(tips);
        },

        __setLoadData: function() {
            //load页面
            var loadWrapper = this._dialog.find('.dialog-body');

            if (this._option.loadWrapper) {
                loadWrapper = this._option.loadWrapper;
                this._dialog.find('.dialog-body').append(this._option.loadWrapper);
            }

            if (this._option.isLoad === true && this._option.url) {
                var mask = $('<div class="loadingbox"><i class="icon icon-loading"></i>' + Lang.loading + '</div>');
                var _this = this;

                mask.appendTo(loadWrapper);

                loadWrapper.load(this._option.url, this._option.params, function(data) {
                    mask.hide();
                    if (data) {
                        if (typeof _this._option.onloadDataSuccess === 'function') {
                            _this._option.onloadDataSuccess.call(this, loadWrapper.contents());
                        }
                    }
                });
            }
        },

        __getZIndex: function(type) {
            /*
             * 1: mask, 2: dialog;
             */
            type = type || 2;

            var zIndexs = [996];

            $('.dialog').each(function(i, o) {
                zIndexs.push($(o).css('z-index') * 1);
            });

            zIndexs.sort(function(a, b) {
                return b - a;
            });

            if (type === 1) {
                return zIndexs[0] + 1;
            }

            return zIndexs[0] + 2;
        },

        __bindEventOfDrag: function() {
            var M = false;
            var F = true;
            var Rx, Ry;
            var b = this._dialog;
            var t = b.find('.dialog-heading');

            t.css('cursor', 'move')
                .mousedown(function(event) {
                    $(document.body).addClass('disabled-select');
                    $(document.body).bind('selectstart', function() {
                        return false;
                    });
                    if ($(event.target).hasClass('dialog-close')) {
                        M = false;
                        return false;
                    }

                    Rx = event.pageX - (b.css("left").replace('px', '') * 1);
                    Ry = event.pageY - (b.css("top").replace('px', '') * 1);
                    M = true;

                })
                .mouseup(function(event) {
                    $(document.body).removeClass('disabled-select');
                    $(document.body).unbind('selectstart');
                    M = false;
                });

            $(document)
                .mousemove(function(event) {
                    var x = event.pageX - Rx;
                    var y = event.pageY - Ry;
                    var xMin = 0;
                    var yMin = 0;
                    var xMax = $(window).width() - b.width();
                    var yMax = $(window).height() - b.height();

                    if (event.pageX <= 0 || event.pageY <= 0 || event.pageX >= $(window).width() || event.pageY > $(window).height()) {
                        F = false;
                        M = false;
                    } else {
                        F = true;
                    }

                    if (M && F) {
                        if (x <= xMin) {
                            x = 0;
                        } else if (x >= xMax) {
                            x = xMax;
                        }

                        if (y <= yMin) {
                            y = 0;
                        } else if (y >= yMax) {
                            y = yMax;
                        }

                        b.css({
                            top: y,
                            left: x
                        });
                    }
                })
                .mouseup(function(event) {
                    if (event.pageX != Rx || event.pageY != Ry) {
                        M = false;
                    }
                });
        },
        
        //绑定滚动条滚动事件，清除弹出的日期插件，隐藏弹出的combobox面板
        __bindEventOfScroll: function() {
        	$(this._dialog).find('.dialog-body').on('scroll', function() {
        		$('.xdsoft_datetimepicker').hide();
        		$('.ac_results').hide();
        		$('.input-combobox').combobox('hidePanel');
        	});
        },

        open: function() {
            if (this._option.showMask) {
                this._masker.show();
            }

            this._dialog.show();
            this._dialog.attr("tabindex", -1).focus(); //获得焦点

            if (this._option.isLoad === true) {
                this.__setLoadData();
            }
        },

        hide: function() {
            if (this._option.showMask) {
                this._masker.hide();
            }

            this._dialog.hide();
        },

        //关闭窗口
        close: function() {
            var close = this._option.onClose(this);

            if (typeof close === "boolean" && !close) {
                return;
            }

            this.remove();
        },

        remove: function() {
            if (this._option.buttons.length) {
                this._dialog.find("[data-dialog-buttons]").contents().unbind("click");
            }

            this._dialog.find(".dialog-close").unbind("click");
            $(".xdsoft_datetimepicker").hide();
            this._dialog.remove();

            if (this._option.showMask) {
                this._masker.remove();
            }
        },

        setContent: function(dom) {
            this._dialog.find('.dialog-body').append(dom);
        },
        
        //用于设置提示信息
        setFooterTips: function(tips, colorClass) {
        	var $tipsHtml = this._dialog.find('.dialog-tips');
        	$tipsHtml.html(tips);
        	if (!colorClass) {
        		return;
        	}
        	$tipsHtml.addClass(colorClass);
        },
        
        clearFooterTips: function(colorClass, time) {
        	var $tipsHtml = this._dialog.find('.dialog-tips');
        	setTimeout(function() {
        		$tipsHtml.html('');
        		if (!colorClass) {
        			return;
        		}
        		$tipsHtml.removeClass(colorClass);
        	}, time || 0);
        },

        getDialogElement: function() {
            return this._dialog;
        },

        getDOM: function() {
            return this.context;
        },
        
        //获取当前页使用数量
        getUsedTimes: function(){
        	return usedTimes;
        },
        
        //获取组件id
        getComponentId: function(){
        	return this._option.componentId || (componentName + this.getUsedTimes());
        },
        
        //获取组件名称
        getComponentName: function(){
        	return componentName;
        },
        
        //组件关联关系
        getComponentRelation: function(){
        	return this._option.componentRelation || 'dialog';
        }
    };

    return Dialog;

}));