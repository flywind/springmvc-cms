/* 消息提示框 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery'], factory);
    } else {
        window.Tips = factory(jQuery);
    }
}(function($) {
    var $tipsBox = $('<div class="tips-box" style="overflow:visible"></div>');

    function Tips(opts) {
        this.options = $.extend(true, {
        	trapWindowLocation: 0, //默认右下角
            title: "&nbsp;", //对话框标题
            content: "&nbsp;", //对话框内容
            width: "auto", //宽
            height: "auto", //高
            drag: true, //是否可拖动
            autoOpen: false, //是否初始化后自动打开
            buttons: [], //按钮,[{text:"确定", click:function(){}, styleName:'btn-default'}]
            onClose: function() {} //关闭时的回调
        }, opts);

        this.tips = this.__buildTips();

        if (this.options.drag) {
            this.__bindEventOfDrag();
        }

        if (this.options.autoOpen) {
            this.open();
        }
    }

    Tips.prototype = {

        constructor: Tips,

        count: 0,

        tipHeight: 0,

        __buildTips: function() {
            var $tips = this.__getTips();
            var $tipsTitle = this.__getTitle();
            var $tipsClose = this.__getCloser();
            var $tipsContent = this.__getContent();
            var $tipsFooter = this.__getFooter();
            var $additionalTitle = this.__getAdditionalTitle();

            $tipsBox.css({
                width: (this.options.width || 280) + 'px'
            });

            if (Tips.prototype.count == 0) {
                for (var i = 0; i < this.options.buttons.length; i++) {
                    $tipsFooter.append(this.__getButton(this.options.buttons[i]));
                }

                $tipsClose.prependTo($tipsTitle);
                $tipsTitle.appendTo($tips);
                $tipsContent.appendTo($tips);
                $tipsFooter.appendTo($tips);
                $tips.hide().appendTo($tipsBox);
                $tipsBox.appendTo($(document.body));
            } else {
                $tipsClose.prependTo($additionalTitle);
                $additionalTitle.appendTo($tips);
                
                if (this.options.buttons.length === 0) {
                	$tipsContent.appendTo($tips);
                } else {
                	for (var i = 0; i < this.options.buttons.length; i++) {
                        $tips.append(this.__getAdditionalContentAndButton(this.options.buttons[i]));
                    }
                }

                $tips.hide().appendTo($tipsBox);
            }

            return $tips;
        },

        __getTipsBox: function() {
            return $('<div class="tips-box" style="overflow:visible"></div>');
        },

        __getTips: function() {
            return $('<div class="tips" style="width:' + (this.options.width || 280) + 'px"></div>');
        },

        __getTitle: function() {
            return $('<div class="tips-heading"><h2 class="tips-title">' + this.options.title + '</h2></div>');
        },

        __getContent: function() {
            return $('<div class="tips-content">' + this.options.content + '</div>');
        },

        __getFooter: function() {
            return $('<div class="tips-footer" data-dialog-buttons="true"></div>');
        },

        __getButton: function(button) {
            return $('<button class="btn btn-small btn-warning"><span><span>' + (button.text || langHandler('handle', '处理')) + '</span></span></button>').click($.proxy(function(f, e) {
                if (typeof f === "function") {
                    f(this, e);
                }
            }, this, button.click));
        },

        __getCloser: function() {
            var _this = this;
            return $('<span href="javascript:;" class="tips-close">x</span>').click(function() {
                _this.close();
            });
        },

        __getAdditionalTitle: function() {
            return $('<div class="tips-heading tips-additional-heading"><h2 class="tips-title">' + this.options.title + '</h2></div>');
        },

        __getAdditionalContentAndButton: function(button) {
            var $html = $('<div class="tips-additional-content">' + this.options.content + '</div><div class="tips-additional-button"><button class="btn btn-small btn-additional-btn"><span><span>' + langHandler('handle', '处理') + '</span></span></button></div>');
            var _this = this;

            $html.find('button').on('click', function(e) {
                button.click(_this);
            });

            return $html;
        },

        __bindEventOfDrag: function() {
            var M = false;
            var F = true;
            var Rx, Ry;
            var b = this.tips.parents('.tips-box');
            var t = b.find('.tips-heading');

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

        open: function() {
            var tipsHeight = this.tips.outerHeight(true);
            var count = Tips.prototype.count;
            var boxHeight = $tipsBox.height();
            var bottom = 0;
            
            var jqWin = $(window);
            var scrW = window.innerWidth || jqWin.width();
            var scrH = window.innerHeight || jqWin.height();

            if (count > 5) {
                return;
            }

            if (count == 0) {
                bottom = 0;
                $tipsBox.height(tipsHeight);
                this.tips.css({
                    bottom: -tipsHeight + 'px',
                    zIndex: 980
                });
            } else {
                bottom = boxHeight;
                $tipsBox.height(tipsHeight + boxHeight);
                this.tips.css({
                    bottom: boxHeight + 'px',
                    zIndex: 980 - count
                });
            }
            
            var tipsBoxWidth = $tipsBox.width();
        	var tipsBoxHeight = $tipsBox.height();
        	
            //trapWindowLocation=1表示自动呼救弹窗位置为居中
            if(this.options.trapWindowLocation*1 === 1){
            	$tipsBox.css({
            		left: Math.floor((scrW) / 2 - (tipsBoxWidth / 2)) + 'px',
                    top: Math.floor((scrH) / 2 - (tipsBoxHeight / 2)) + 'px'
            	});
            } else{
            	$tipsBox.css({
            		left: Math.floor((scrW) - (tipsBoxWidth + 10)) + 'px',
            		top: Math.floor((scrH) - (tipsBoxHeight + 10)) + 'px'
            	});
            }

            this.tips.show();
            this.tips.animate({
                bottom: bottom
            }, 800);

            this.tips.attr("tabindex", -1).focus(); //获得焦点

            Tips.prototype.count++;

        },

        close: function() {

            Tips.prototype.count--;

            var count = Tips.prototype.count;
            var $tips = this.tips;
            var close = this.options.onClose(this);
            var tipsHeight = this.tips.outerHeight(true);
            var tipsboxOverflow = $tips.css('overflow');

            if (typeof close === "boolean" && !close) {
                return;
            }

            if (this.options.buttons.length) {
                $tips.find("[data-tips-buttons]").contents().unbind("click");
            }
            
            $tips.find(".tips-close").unbind("click");

            if (count == 0) {
                if (tipsboxOverflow == 'visible') {
                    $tips.fadeOut(800, $.proxy(function() {
                        $tipsBox.remove();
                    }, this));
                    return;
                }
            } else {
                if (tipsboxOverflow == 'visible') {
                    $tips.fadeOut(800);
                    return;
                }
            }

            this.tips.animate({
                top: tipsHeight
            }, 800, $.proxy(function() {
                $tips.remove();
            }, this));

        }
    };

    /* 多语言处理 */
    function langHandler(value, defaultText) {
        return window.Lang && window.Lang[value] ? window.Lang[value] : defaultText || value;
    }

    return Tips;

}));