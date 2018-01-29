/* 文本输入框UI */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery'], factory);
    } else {
        window.InputUI = factory(jQuery);
    }
}(function($) {

    /* 文本输入框UI */
    function InputUI($e, opts) {
        this.options = $.extend({}, InputUI.prototype.defaults, opts);
        this.elements = $e || $('.input-text[data-input-ui!="false"]');
        this.init();
    }

    InputUI.prototype = {
        constructor: InputUI,

        placehodlerInputs: {},

        defaults: {
            placeholder: true,
            clearButton: true,
            inputWrapElement: $('<span class="input-ui-box"></span>'),
            placeholderElement: $('<span class="placeholder-text"></span>'),
            clearButtonElement: $('<span class="icon icon-clear">×</span>')
        },

        init: function() {
            //InputUI.prototype.placehodlerInputs = [].slice.apply(this, this.$elements);
            this.setUI();
        },

        /* 增加placehoder */
        addPlaceholder: function() {
        	var isSupportPlaceholder = InputUI.isSupportPlaceholder();
        	if (isSupportPlaceholder) {
        		return;
        	}
            this.elements.each(function(index, element) {
                if ($(this).parent().find('.placeholder-text').length) {
                    $(this).parent().find('.placeholder-text').show();
                    return;
                }
                var $placeholder = $('<span class="placeholder-text"></span>'),
                    $this = $(this);

                $placeholder.text($this.attr('placeholder'));
                $placeholder.click(function() {
                    $this.focus();
                });

                if ($(this).val()) {
                    $placeholder.hide();
                }

                $placeholder.appendTo($this.parent());
            });
        },

        /* 删除placehoder */
        removePlaceholder: function(nodeList) {

            nodeList = nodeList || Array.prototype.slice.apply(this.elements);

            var name = '';

            if (!(nodeList instanceof Array)) {
                nodeList = [nodeList];
            }

            $.each(nodeList, function(index, element) {
                name = element.name;
                $(element).parent().find('.placeholder-text').hide();
                $(element).parent().find('.icon').show();
                InputUI.prototype.placehodlerInputs[name] = null;
            });
        },

        /* 清除输入框文本的按钮 */
        addClearValue: function($e) {
            
            if ($e.parent().find('.icon-clear').length) {
                $e.parent().find('.icon-clear').show();
                return;
            }

            var _this = this;
            var $clearValue = $('<span class="icon icon-clear">×</span>');
            var $getInputWidth = $e.outerWidth();
            var isSupportPlaceholder = InputUI.isSupportPlaceholder();

            $clearValue.css('left', ($getInputWidth - 25) + 'px');
            $clearValue.appendTo($e.parent());

            $clearValue.click(function() {
                $e.val('').focus();
                $(this).hide();
                if (!isSupportPlaceholder) {
                    _this.addPlaceholder($e);
                }
            });
        },

        /* 移除清除输入框文本的按钮 */
        removeClearValue: function($e) {
            $e.parent().find('.icon-clear').hide();
        },

        /* 设置清除输入框中的值的button */
        setClearValueButton: function() {
            var _this = this;

            this.elements.keyup(function() {
                var $this = $(this);
                if ($this.val()) {
                    _this.addClearValue($this);
                } else {
                    _this.removeClearValue($this);
                }
            }).blur(function() {
                var $this = $(this);
                if ($this.val()) {
                    _this.addClearValue($this);
                }
            }).focus(function() {
                var $this = $(this);
                if ($this.val()) {
                    _this.addClearValue($this);
                }
            });
        },
        /* 设置placeholder */
        setPlaceholder: function(v) {
        	
            var text = $(v).attr('placeholder');
            var name = v.name;
            
            $(v).after(this.options.placeholderElement.clone().text(text));
            
            $('.placeholder-text').click(function() {
                $(this).parent('.input-ui-box').find('input').focus();
            });

            InputUI.prototype.placehodlerInputs[name] = v;
        },

        /* 设置清除按钮 */
        setClearButton: function(v) {
            var $e = $(v);
            var $added = this.options.clearButtonElement.clone();
            var value = $e.val();

            $added.addClass('hidden');

            $e.after($added);

            $added.click(function(e) {
                $(this).parent('.input-ui-box').find('input').val('').focus();
            });
        },

        /* 设置包裹元素（input父级） */
        setInputWrap: function(v) {
            var $e = $(v);
            var $added = this.options.inputWrapElement.clone();

            $e.wrap($added);
        },

        /* 绑定事件 */
        bindEvents: function(v) {
            var $e = $(v);
            var $eParent = $e.parent('.input-ui-box');
            var $clearButton = $eParent.find('.icon');
            var $placeholder = $eParent.find('.placeholder-text');
            var handler = function(value) {
                if (value !== '') {
                	$placeholder.hide();
                	$clearButton.show();
                } else {
                	$placeholder.show();
                	$clearButton.hide();
                }
            };

            if ($e.val() !== '') {
            	$placeholder.hide();
            	$clearButton.show();
            }

            $e.keyup(function() {
                handler($(this).val());
            })
            .blur(function() {
                handler($(this).val());
            })
            .focus(function() {
                handler($(this).val());
            });
        },

        /* 设置UI */
        setUI: function(element) {
            var _this = this;
            var isDisabled = false;
            $.each(
                element || _this.elements,
                function(i, v) {
                    isDisabled = $(v).prop('disabled') || $(v).prop('readonly');
                    _this.setInputWrap(v);
                    if (_this.options.placeholder && !InputUI.isSupportPlaceholder()) {
                         _this.setPlaceholder(v);
                    }

                    if (!isDisabled && _this.options.clearButton) {
                        _this.setClearButton(v);
                    }

                    _this.bindEvents(v);
                }
            );

        }
    }

    /* 是否支持HTML5属性placeholder */
    InputUI.isSupportPlaceholder = function() {
        return 'placeholder' in document.createElement('input');
    }

    return InputUI;

}));