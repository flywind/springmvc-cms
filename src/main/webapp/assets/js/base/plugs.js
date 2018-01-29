//进度条插件
(function($, udf) {
    'use strict';

    // 保存实例化对象的data键
    var datakey = 'jquery-progressbar';
    var defaults = {
        url: 'xxxx.do', //请求地址
        value: 0, //初始值
        time: 5, // 以秒为单位的请求间隔时间
        type: 'percent', //类型：percent百分比，timer:计时器
        queryParams: {}, //请求参数
        taskId: randomCode(3, 35),
        onsuccess: function(data) {}, //异步加载成功
        oncomplete: function(data) {}, //进度100%完成要干的事
        onerror: function(data) {

        }
    };

    $.fn.progressbar = function(settings) {
        var $this = this;
        // 当前第1个参数为字符串
        var run = $.type(settings) === 'string';
        // 获取运行方法时的其他参数
        var args = [].slice.call(arguments, 1);
        // 复制默认配置
        var options = $.extend({}, defaults);
        // 运行实例化方法的元素
        var $element = null;
        // 实例化对象
        var instance = null;

        // 运行实例化方法，第1个字符不能是“_”
        // 下划线开始的方法皆为私有方法
        if (run && run[0] !== '_') {
            if (!this.length) return;

            // 只取集合中的第1个元素
            $element = $(this[0]);

            // 获取保存的实例化对象
            instance = $element.data(datakey);

            // 若未保存实例化对象，则先保存实例化对象
            if (!instance) $element.data(datakey, instance = new ProgressBar($element[0], options).__init());

            // 防止与静态方法重合，只运行原型上的方法
            // 返回原型方法结果，否则返回undefined
            return ProgressBar.prototype[settings] ? ProgressBar.prototype[settings].apply(instance, args) : udf;
        }

        // instantiation options
        else if (!run) {
            // 合并参数
            options = $.extend(options, settings);
        }

        return this.each(function() {
            var element = this;
            var instance = $(element).data(datakey);

            // 如果没有保存实例
            if (!instance) {
                // 保存实例
                $(element).data(datakey, instance = new ProgressBar(element, options).__init());
            }
        });
    };

    // 暴露插件的默认配置
    $.fn.progressbar.defaults = defaults;

    // 构造函数
    function ProgressBar(element, options) {
        this.$element = $(element);
        this.options = options;
        this.processed = 0;
        this.total = 0;
        this.progressBar = null;
        this.timeId = 0;
    }

    // 原型方法，驼峰写法
    ProgressBar.prototype = {
        constructor: ProgressBar,

        // 初始化
        __init: function() {
            this.progressBar = this.__getProgressBox();

            this.$element.append(this.progressBar);

            var _this = this;
            var handler = function() {
                if ((_this.total !== 0) && (_this.processed === _this.total)) {
                    //进度百分百后的回调函数
                    if (typeof _this.options.oncomplete === 'function') {
                        _this.options.oncomplete(_this.processed, _this.total);
                    }

                    clearTimeout(_this.timeId);

                    return;
                }

                _this.__queryData();

                _this.timeId = setTimeout(handler, _this.options.time * 1000);
            }

            handler();

            return this;
        },

        // 获取进度条
        __getProgressBox: function() {
            if (this.options.type === 'percent') {
                return $('<div class="progress"><div class="progress-bar" style="width:' + this.options.value + '%">' + this.options.value + '%</div></div>');
            } else if (this.options.type === 'timer') {
                return $('<div class="progress"><div class="progress-bar" style="width:' + this.options.value + '%">' + this.options.value + '%</div></div>');
            }
        },

        //更新进度
        updateProgress: function(processed, total) {
            this.processed = processed;
            this.total = total;

            var _this = this;
            var num = ((processed / total) * 100).toFixed(2);
            var $progressBar = this.progressBar.find('.progress-bar');
            
            if ((num * 1) < $progressBar.text().substring(0, $progressBar.text().length-1) * 1) {
            	return;
            }

            $progressBar.text(num + '%').css('width', num + '%');
        },

        //请求数据
        __queryData: function() {
            var _this = this;

            $.ajax({
                url: _this.options.url,
                data: $.extend({}, {
                    taskId: _this.options.taskId
                }, _this.options.queryParams),
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function(data) {
                    data = stringToJSON(data);
                    if (data) {
                        var flag = true;
                        if (typeof _this.options.onsuccess === 'function') {
                            flag = _this.options.onsuccess(data);
                        }

                        if (flag === false) {
                            clearTimeout(_this.timeId);
                            return;
                        }

                        _this.updateProgress(data.processed, data.total);
                    }
                },
                error: function() {

                }
            });
        },

        //设置值
        setValue: function(value) {
            var $progressBar = this.progressBar.find('.progress-bar');
            $progressBar.text(value + '%').css('width', value + '%');
        },

        //停止轮询
        stop: function() {
            clearTimeout(this.timeId);
        },

        //销毁对象
        destroy: function() {
            this.stop();
            this.$element.remove();
        },

        // 设置或获取选项
        options: function(key, val) {
            // get
            if ($.type(key) === 'string' && val === udf) {
                return this.options[key];
            }

            var map = {};

            if ($.type(key) === 'object') {
                map = key;
            } else {
                map[key] = val;
            }

            this.options = $.extend(this.options, map);
        }
    };

    /* 生成随机数 */
    function randomCode(type, num) {
        type = type || 1;
        num = num || 1;

        var letterAndNumberArray = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
        var randomCode = '';

        for (var i = 0; i < num; i++) {
            /* 数字随机 */
            if (type == 1) {
                randomCode += letterAndNumberArray[selectFrom(0, 9)];
                continue;
            }

            /* 字母随机 */
            if (type == 2) {
                randomCode += letterAndNumberArray[selectFrom(10, 35)];
                continue;
            }

            /* 3表示数据与字母随机 */
            if (type == 3) {
                randomCode += letterAndNumberArray[selectFrom(0, 35)];
            }
        }

        return randomCode;
    };

    /* 指定范围的随机数 */
    function selectFrom(lowerValue, upperValue) {
        var choices = upperValue - lowerValue + 1;

        return Math.floor(Math.random() * choices + lowerValue);
    }

    /* 字符串转JSON对象 */
    function stringToJSON(data) {
        try {
            data = JSON.parse(data);
        } catch (e) {
            data = data;
        }

        return data;
    }

})(jQuery);

//选择器插件（模拟select下拉列表）
(function($, window, udf) {
    'use strict';

    // 保存实例化对象的data键
    var datakey = 'jquery-selectbox';
    var defaults = {
        url: '', //请求地址
        data: [], //数组对象与URL互斥
        value: undefined, //初始值
        height: 30, // 高
        width: 120, // 宽
        placeholder: langHandler('pleaseSelect', "请选择"), //默认项,例如请选择
        queryParams: {}, //请求参数
        singleSelect: true, //是否单选, 如果为false就是多选
        checkbox: true, //当多选时，是否显示checkbox
        autocomplate: false, //是否自动匹配
        fieldName: '', //字段name用于服务端接收的字段名字
        fieldText: 'name', //用作显示文字的字段名称
        fieldValue: 'value', //用作select的value值的字段名称
        readonly: false, //只读
        css: '',
        onselect: function(value, row) {},
        onchange: function(value, row) {},
        onsuccess: function(res) {},
        onunselect: function(row) {},
        onselectall: function(rows) {},
        onunselectall: function(rows) {}
    };

    $.fn.selectbox = function(settings) {
        var $this = this;
        // 当前第1个参数为字符串
        var run = $.type(settings) === 'string';
        // 获取运行方法时的其他参数
        var args = [].slice.call(arguments, 1);
        // 复制默认配置
        var options = $.extend({}, defaults);
        // 运行实例化方法的元素
        var $element = null;
        // 实例化对象
        var instance = null;

        // 运行实例化方法，第1个字符不能是“_”
        // 下划线开始的方法皆为私有方法
        if (run && settings[0] !== '_') {

            // 获取保存的实例化对象
            instance = this.data(datakey);

            // 若未保存实例化对象，则先保存实例化对象
            if (!instance) {
                return;
            }

            // 防止与静态方法重合，只运行原型上的方法
            // 返回原型方法结果，否则返回undefined
            return SelectBox.prototype[settings] ? SelectBox.prototype[settings].apply(instance, args) : udf;
        }

        // instantiation options
        else if (!run) {
            // 合并参数
            options = $.extend(options, settings);
        }

        return this.each(function() {
            var element = this;
            var instance = $(element).data(datakey);

            // 如果没有保存实例
            if (!instance) {
                // 保存实例
                instance = new SelectBox(element, options).__init();
                $(element).data(datakey, instance);
            }
        });
    };

    // 暴露插件的默认配置
    $.fn.selectbox.defaults = defaults;

    // 构造函数
    function SelectBox(element, options) {
        this.$element = $(element);
        this.options = options;
        this.data = [];
        this.value = [];
        this.text = [];
        this.selections = [];
        this.defaultZIndex = 900;
    }

    //原型方法，驼峰写法
    SelectBox.prototype = {
        constructor: SelectBox,

        count: 0,

        //初始化
        __init: function() {

            this.__buildSelectBox();

            if (this.options.url) {

                this.__queryData();

            } else {

                this.data = this.options.data;
                this.__setSelectItems();
                this.setValue();
            }

            SelectBox.prototype.count++;

            return this;
        },

        //创建Select盒子
        __buildSelectBox: function() {
            var _this = this;
            var selectboxCSS = 'width:' + this.options.width + 'px;line-height:' + this.options.height + 'px;z-index:' + this.defaultZIndex;

            this.selectBox = $('<div class="selectbox'+(this.options.readonly ? ' disabled' : '')+' '+this.options.css+'" data-selectbox="true" style="' + selectboxCSS + '"></div>');
            this.selectDisplay = $('<div class="selectbox-display" data-selectbox-display="true"></div>');
            this.selectOptions = $('<div class="selectbox-options" data-selectbox-options="true" style="width:' + this.options.width + 'px"></div>');
            this.selectList = $('<ul class="selectbox-list" data-selectbox-list="true"></ul>');

            this.selectBox.append(this.selectDisplay.append(this.__getSelectDisplayContents())).append(this.selectOptions.append(this.selectList));

            //绑定点击显示部分事件（select）
            this.selectDisplay.on('click', function(e) {
            	if(_this.options.readonly === true){
            		return;
            	}
                _this.__displayClickHandle(this, e);
            });

            this.selectBox.insertAfter(this.$element);

            this.$element.removeAttr('name').hide();
        },

        //返回selector显示部分
        __getSelectDisplayContents: function() {
            var name = this.options.fieldName || this.$element.attr('name');
            var style = 'height:' + this.options.height + 'px;line-height:' + this.options.height + 'px;';
            var showText = this.options.autocomplate ? '<input type="text" data-text="true" class="input-text text-overflow" readonly style="width:' + this.options.width + 'px;" data-input-ui="false">' : '<span data-text="true" class="selectbox-text text-overflow" style="' + style + 'padding-top:0;padding-bottom:0;"></span>';
            var $html = $(showText + '<span class="icon icon-chevron-down"></span><input type="text" class="opacity" data-value="true" name="' + name + '">');

            return $html;
        },

        //填充selector列表部分
        __setSelectItems: function() {
            var _this = this;
            var items = [];
            var checkbox = _this.options.singleSelect === false && _this.options.checkbox ? '<input type="checkbox" name="item"> ' : '';
            var value = '';
            var text = '';
            var data = '';
            var placeholder = this.$element.attr('placeholder') || this.options.placeholder;
            var firstNumber = placeholder ? 1 : 0;

            //解绑事件并清除已有item列表
            this.selectList.off("click", 'li');
            this.selectList.empty();

            items[0] = '<li class="selectbox-item text-overflow selectbox-item-placeholder" data-value="" data-selectbox-item-placeholder="true">' + placeholder + '</li>';

            if ($.type(this.data) === 'array' && this.data.length > 0) {

                for (var i = 0, len = this.data.length; i < len; i++) {
                    data = this.data[i];
                    value = $.type(data) == 'object' ? data[this.options.fieldValue] : data;
                    text = $.type(data) == 'object' ? data[this.options.fieldText] : data;

                    items[i + firstNumber] = '<li class="selectbox-item text-overflow" data-value="' + value + '" data-index="' + i + '">' + checkbox + text + '</li>';
                }
            }


            this.selectList.append(items.join('\n'));

            //事件代理
            this.selectList.on('click', 'li', function(e) {
                _this.__itemClickHandle(this, e);
            });

            //全局document
            $(document).on('click', function(e) {
                var $target = $(e.target);
                var $currentChild = !!_this.selectBox.has($target)[0];
                var targetClass = $target.attr('class');
                var $icon = _this.selectDisplay.find('.icon');
                var $iconHasUp = $icon.hasClass('icon-chevron-up');

                if ((targetClass === 'selectbox-display' || targetClass === 'selectbox-options' || $target.closest('.selectbox-options').length > 0 || $target.closest('.selectbox-display').length > 0) && $currentChild) {
                    return;
                }

                _this.selectBox.css('z-index', _this.defaultZIndex);
                _this.selectOptions.hide();
                $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');
            });

        },

        //显示区域事件处理
        __displayClickHandle: function(self, e) {
            var $self = $(self);
            var $icon = this.selectDisplay.find('.icon');
            var $iconHasUp = $icon.hasClass('icon-chevron-up');

            if ($iconHasUp) {
                $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');
                this.selectOptions.hide();
                this.selectBox.css('z-index', this.defaultZIndex);
                return;
            }

            $icon.removeClass('icon-chevron-down').addClass('icon-chevron-up');

            this.selectOptions.show();
            this.selectBox.css('z-index', this.defaultZIndex + SelectBox.prototype.count);
        },

        //列表项点击事件处理
        __itemClickHandle: function(self, e) {
            var $self = $(self);
            var isPlaceholder = $self.attr('data-selectbox-item-placeholder') === 'true';
            var items = this.selectList.find('li');
            var $icon = this.selectDisplay.find('.icon');

            //请选择
            if (isPlaceholder) {

                this.__updateData('reset');
                this.__updateViews();

                items.removeClass('selected');
                $self.addClass('selected');

                this.selectOptions.hide();
                $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');

                if (typeof this.options.onselect === 'function') {
                    this.options.onselect('', null);
                }

                return;
            }
                        
            var isSelected = $self.hasClass('selected');
            var $checkbox = $self.find('input[type="checkbox"]');
            var row = this.data[stringToNumber($self.attr('data-index'))];
            var value = $.type(row) != 'object' ? row : row[this.options.fieldValue];
            var updateType = '';
            var $iconHasUp = $icon.hasClass('icon-chevron-up');


            //单选
            if (this.options.singleSelect) {
                if (typeof this.options.onselect === 'function') {
                    this.options.onselect(value, row);
                }

                updateType = 'replace';

                items.removeClass('selected');
                $self.addClass('selected');

                this.selectOptions.hide();
                $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');
            }

            //多选
            else {
                if (isSelected) {

                    if (typeof this.options.onUnSelect === 'function') {
                        this.options.onUnSelect(value, row);
                    }

                    updateType = 'del';

                    if (this.options.checkbox) {
                        $checkbox.prop('checked', false);
                    }

                    $self.removeClass('selected');

                } else {

                    if (typeof this.options.onselect === 'function') {
                        this.options.onselect(value, row);
                    }

                    updateType = 'add';

                    if (this.options.checkbox) {
                        $checkbox.prop('checked', true);
                    }

                    $self.addClass('selected');
                }
            }

            this.__updateData(updateType, row);
            this.__updateViews();
        },

        //更新视图（文字显示、值显示、已选中的行数据）
        __updateViews: function() {
            var $text = this.selectDisplay.find('[data-text="true"]');
            var text = this.text.join(',') || this.options.placeholder;

            if ($text.is('input')) {
                $text.val(text);
            } else {
                $text.text(text);
            }
            
            var $firstSelectItem = this.selectDisplay.next('.selectbox-options').find('.selectbox-list .selectbox-item:first-child');
            var $validErrorItem = this.selectDisplay.find('.validate-text.error');
            if(text && text !== $firstSelectItem.html() && $validErrorItem.length){
            	$validErrorItem.remove();
            }
            
            $text.attr('title', text);
            this.selectDisplay.find('[data-value="true"]').val(this.value.join(','));

        },

        //更新数据
        __updateData: function(type, data) {

            if ($.type(data) !== 'array') {
                data = [data];
            }

            //重置
            if (type === 'reset') {
                this.selections = [];
                this.text = [];
                this.value = [];
                return;
            }

            var row = null;
            var value = '';
            var text = '';
            var changed = false;

            for (var i = 0, len = data.length; i < len; i++) {

                row = data[i];
                value = $.type(row) === 'object' ? row[this.options.fieldValue] : row;
                text = $.type(row) === 'object' ? row[this.options.fieldText] : row;

                //type:更新类型，replace替换，add增加，del删除, reset重置
                switch (type) {
                    case 'replace':
                        if ($.inArray(value, this.value) !== -1) {
                            break;
                        }
                        this.selections = [row];
                        this.text = [text];
                        this.value = [value];
                        changed = true;
                        break;
                    case 'add':
                        this.selections.push(row);
                        this.text.push(text);
                        this.value.push(value);
                        changed = true;
                        break;
                    case 'del':
                        var index = $.inArray(value, this.value);
                        if (index !== -1) {
                            this.selections.splice(index, 1);
                            this.text.splice(index, 1);
                            this.value.splice(index, 1);
                            changed = true;
                        }
                }
            }

            //onchange回调
            if (changed && this.options.onchange && (typeof this.options.onchange === 'function')) {
                this.options.onchange(this.value, data);
            }
        },

        //请求数据
        __queryData: function() {
            var _this = this;
            $.ajax({
                url: _this.options.url,
                data: _this.options.queryParams,
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function(data) {
                    data = stringToJSON(data);

                    if (data) {
                        _this.data = data.rows;
                        _this.__setSelectItems();
                        _this.setValue();
                    }

                    if (typeof _this.options.onsuccess === 'function') {
                        _this.options.onsuccess(data);
                    }
                },
                error: function() {

                }
            });
        },

        //设值
        setValue: function(value) {

            var _this = this;
            var val = value || this.options.value;
            var $dom = null;

            if (val === undefined) {
                _this.selectList.find('li').get(0).click();
                return;
            }

            if ($.type(val) !== 'array') {
                val = [val];
            }

            $.each(val, function(i, v) {
                $dom = _this.selectList.find('[data-value="' + v + '"]');
                if ($dom.length > 0) {
                    $dom.click();
                }
            });

        },

        //重置初始值
        reset: function() {
            var v = this.options.value;
            var $dom = null;

            if (v === undefined) {
                $dom = this.selectList.find('.selectbox-item-placeholder');
            } else {
                $dom = this.selectList.find('[data-value="' + v + '"]');
            }

            if ($dom.length > 0) {
                $dom.click();
            }
        },

        //清除值
        clear: function() {
            var $placeholder = this.selectList.find('.selectbox-item-placeholder');
            var items = this.selectList.find('li');
            var $icon = this.selectDisplay.find('.icon');

            this.__updateData('del');
            this.__updateViews();

            items.removeClass('selected');
            $placeholder.addClass('selected');

            this.selectOptions.hide();
            $icon.removeClass('icon-chevron-up').addClass('icon-chevron-down');
        },

        //返回已选择的值
        getValue: function() {
            return this.value;
        },

        //返回已选择的行数据
        getSelections: function() {
            return this.selections;
        },

        //重设数据
        setData: function(data) {
            this.data = data; 
            this.__setSelectItems();

            var $placeholder = this.selectList.find('.selectbox-item-placeholder');
            if(!this.data && $placeholder.length > 0){
            	$placeholder.click();
            }
        },

        //设置或获取选项
        options: function(key, val) {
            // get
            if ($.type(key) === 'string' && val === udf) {
                return this.options[key];
            }

            var map = {};

            if ($.type(key) === 'object') {
                map = key;
            } else {
                map[key] = val;
            }

            this.options = $.extend(this.options, map);
        }

    };

    /* 多语言处理 */
    function langHandler(value, defaultText) {
        return window.Lang && window.Lang[value] ? window.Lang[value] : defaultText || value;
    }

    /* 字符串转换成数字类型 */
    function stringToNumber(string) {
        var num = string * 1;

        if (isNaN(num)) {
            return 0;
        }

        return num;
    }

    /* 字符串转JSON对象 */
    function stringToJSON(data) {
        try {
            data = JSON.parse(data);
        } catch (e) {
            data = data;
        }
        return data;
    }

})(jQuery, window);

/* TAB选项卡 */
(function($, window, udf) {
    $.fn.tabbox = function(opts) {
        var settings = {
            eventType: 1, //触发事件类型，1为click, 2为hover
            defaultActive: '', //默认激活的id,如果为all则为第一个选项卡打开，内容全部打开,如果为数字的话，则打开对应的索引，索引从0开始
            onChange: function(targetContentId, targetLink, targetContent) {} //点击后的回调函数，第一个参数为当前显示的content元素的ID，第二个参数为当前点击的link元素，第三个参数为当前显示的content元素
        };

        var options = $.extend(settings, opts);

        return this.each(function() {
            var $this = $(this);
            var $links = $this.find('[data-tab-link]');
            var $contents = $this.find('[data-tab-content]');
            var event = (options.eventType == 1 || !options.eventType) ? 'click' : 'mouseover';

            //绑定事件
            $links.bind(event, function(e) {
                var index = $links.index($(this));
                var tagName = $(this).tagName;
                var id = (tagName !== 'A') ? $(this).find('a').attr('href') : $(this).attr('href');
                if (typeof options.onBeforeChange == 'function') {
                    var isEditedSuccessOrNotChanged = options.onBeforeChange(id.replace('#', ''), $(this), $(id));
                    if(!isEditedSuccessOrNotChanged){
                    	return;
                    }
                }
                $links.removeClass('selected');
                $(this).addClass('selected');

                if (options.defaultActive === 'all' && index == 0) {
                    $contents.removeClass('hidden');
                } else {
                    $contents.addClass('hidden');
                    $(id).removeClass('hidden');
                }

                if (typeof options.eventCallback == 'function') {
                    options.eventCallback(id.replace('#', ''), $(this), $(id));
                }

                if (typeof options.onChange == 'function') {
                    options.onChange(id.replace('#', ''), $(this), $(id));
                }

                e.preventDefault();
            });

            //触发第一个元素的事件
            var index = 0;
            if (typeof options.defaultActive === 'number') {
                index = options.defaultActive;
            } else if (options.defaultActive && options.defaultActive !== 'all') {
                index = $contents.index($('#' + options.defaultActive));
            }

            var $firstLink = $links.eq(index);
            if (options.eventType == 1 || !options.eventType) {
                $firstLink.click();
            } else {
                $firstLink.mouseover();
            }
        });
    }
})(jQuery, window);