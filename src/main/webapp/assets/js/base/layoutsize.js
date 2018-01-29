/* 页面布局构造函数 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery'], factory);
    } else {
        window.LayoutSize = factory(jQuery);
    }
}(function($) {

    function LayoutSize(opts) {

        this.options = $.extend(true, {}, LayoutSize.defaults, opts);

        this.$header = $('[data-header="true"]');
        this.$menuBox = $('[data-menu-box="true"]');
        this.$box = $('[data-box="true"]');
        this.$layoutRight = $('#J_LayoutRight');
        this.$flashtips = $('[data-flash-tips="true"]');

        this.init();

    }

    LayoutSize.defaults = {
        gridSelector: '[data-datagrid="true"]', //gridgrid选择器
        gridType: 'datagrid' //datagrid或者treegrid
    };

    LayoutSize.prototype = {

        constructor: LayoutSize,

        count: 0,

        init: function() {

            this.setWinSize();

            var $box = $('[data-rule="resizeSimple"]');
            if ($box.length === 0) {
                this.setBoxMarginTop();
                this.setDatagridHeight();
            } else {
                this.setBoxMarginTop();
                this.setLayoutRightHeight();
                this.setRightDatagridHeight();
            }

            this.setBoxHeight();

            var _this = this;
            if (LayoutSize.prototype.count == 0) {
                $(window).resize(function() {
                    _this.init();
                });
            }

            LayoutSize.prototype.count++;
        },

        setWinSize: function() {
            this.winWidth = $(window).outerWidth(true);
            this.winHeight = $(window).outerHeight(true); //$.browser.msie && $.browser.version <= 8 ? $(window).outerHeight(true) - 4 : $(window).outerHeight(true);
        },

        setBoxHeight: function() {
            if (this.$box.length == 0) {
                return;
            }

            var boxHeight = this.winHeight - this.boxMarginTop;

            this.$box.css('min-height', boxHeight + 'px');
        },

        setBoxMarginTop: function() {
            if (this.$box.length == 0) {
                return;
            }

            var $flashtipsHeight = this.$flashtips.legnth > 0 ? this.$flashtips.outerHeight(true) : 0;
            var $headerHeight = this.$header.outerHeight(true);
            var $menuBoxHeight = this.$menuBox.outerHeight(true);
            var marginTop = $flashtipsHeight + $headerHeight + $menuBoxHeight;

            this.$box.css('margin-top', marginTop + 'px');
            this.boxMarginTop = marginTop;
        },

        setDatagridHeight: function() {
            var $box = $('[data-datagrid-box="true"]');

            if ($box.length == 0) {
                return;
            }

            var scrollTop = $(window).scrollTop();
            var height = this.winHeight - ($box.offset().top - scrollTop);

            $box.height(height);

            try {
                $(this.options.gridSelector)[this.options.gridType]('resize');
            } catch (e) {

            }

        },

        setRightDatagridHeight: function() {
            var $box = $('[data-rule="resizeDatagrid"]');

            if ($box.length === 0) {
                return;
            }

            var headerHeight = $('#J_BlockHeading').outerHeight(true);
            var formHeight = this.$layoutRight.find('.form-search').outerHeight(true);

            $box.height(this.winHeight - this.boxMarginTop - headerHeight - formHeight - 15);

            try {
                $(this.options.gridSelector)[this.options.gridType]('resize');
            } catch (e) {

            }

        },

        setLayoutRightHeight: function() {
            this.$layoutRight.css({
                position: 'fixed',
                top: this.boxMarginTop,
                right: 15 + 'px'
            });
        },

        getBorder: function(element) {
            if (!element || $(element).length == 0) {
                return 0;
            }

            return $(element).css('border-top-width').replace(/px/g, '') * 1;
        },

        getPadding: function(element) {
            if (!element || $(element).length == 0) {
                return 0;
            }

            return $(element).css('padding-top').replace(/px/g, '') * 1;
        },

        getMargin: function(element) {
            if (!element || $(element).length == 0) {
                return 0;
            }

            var marginBottom = $(element).css('margin-bottom');

            if (marginBottom == 'auto') {
                return 0;
            }

            return marginBottom.replace(/px/g, '') * 1;
        },

        getWidth: function(element) {
            if (!element || $(element).length == 0) {
                return 0;
            }

            return $(element).outerWidth(true);
        },
        
        getHeight: function(element) {
            if (!element || $(element).length == 0) {
                return 0;
            }

            return $(element).outerHeight(true);
        }

    }

    return LayoutSize;

}));