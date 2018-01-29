/* 
动态创建表单
elements参数样例
[
    {fieldType: 'select', label: '职业', fieldName: 'job', fieldID:'job', css:'margin-top:10px;', fieldValue: 3, options: [{fieldValue:1, fieldName:'务农'}, {fieldValue:2, fieldName:'老师'}, {fieldValue:3, fieldName:'公务员'}]},
    {fieldType: 'file', label: '附件', fieldName: 'attachment', fieldID:'attachment', options: [{fileName: '附件1', fileUrl: 'http://www.inovance.cn'}, {fileName: '附件2', fileUrl: 'http://www.inovance.cn'}]},
    {fidldType: 'button', fieldName: 'btn', options:[{type: 'submit', text: '提交', name: 'post', id: 'post', callback: null}, {type: 'reset', text: '重置', name: 'reset', id: 'reset', callback: null}]}
]
*/
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery'], factory);
    } else {
        window.FormUI = factory(jQuery);
    }
}(function($) {

    function FormUI(opts) {
        this.opts = $.extend(true, {
            formID: '',
            formParentElement: null,
            formClassName: 'form-popup',
            elements: null,
            listClassName: 'list-unstyled',
            method: 'post',
            action: '',
            isTwoCol: true
        }, opts);

        this.form = null;
        this.strForm = '';
        this.hasFileUpload = false;
        this.opts.selectDefaultText = langHandler('pleaseSelect', '请选择');
        this.textInputLength = 0;

        if (this.opts.elements && this.opts.elements.length > 0) {
            this.__bulidForm();
        }
    }

    FormUI.prototype = {

        constructor: FormUI,

        __bulidForm: function() {
            var _this = this;
            var cssForm = (this.opts.isTwoCol == true) ? 'form-group-two-col' : '';
            var cssUl = (this.opts.isTwoCol == true) ? 'list-inline form-list-col form-list-col2' : this.opts.listClassName;

            var $form = $('<form id="' + this.opts.formID + '" data-post-form="true" method="' + this.opts.method + '" action="' + this.opts.action + '" class="form"></form>');
            var $formBox = $('<div class="form-group form-grid ' + this.opts.formClassName + ' form-group-' + this.__getFormClassName(this.opts.formID) + '  ' + cssForm + '">');
            var $formUL = $('<ul class="' + cssUl + '">');

            //遍历elements并创建li
            $.each(
                this.opts.elements,
                function(i, v) {
                    _this.__bulidFormLI(v).appendTo($formUL);
                }
            );

            $formBox.append($formUL).appendTo($form);


            if (this.hasFileUpload === true) {
                $form.attr('enctype', 'multipart/form-data');
            }

            this.form = $form;

            this.strForm = $form.prop('outerHTML');

            if (this.opts.formParentElement) {
                $form.appendTo(this.opts.formParentElement);
            }
        },

        __bulidFormLI: function(o) {
            var hiddenCSS = (o.fieldType === 'hidden') || (o.fieldType === 'password' && o.isHidden === true) ? ' hidden' : '';
            var $formLI = $('<li class="form-item form-item-' + this.__getFormItemClassName(o.fieldName) + hiddenCSS + '"></li>');

            //屏蔽隐藏域及button的label标签
            if (o.fieldType !== 'hidden' && o.fieldType !== 'button') {
                var fieldID = o.fieldID ? o.fieldID : o.fieldName;
                var verifyPrompt = o.verifyPrompt ? ('<span class="remind-span" >' + o.verifyPrompt + '</span>') : '';
                $('<label class="form-label" title="' + o.label + '">' + verifyPrompt + o.label + '：</label>').appendTo($formLI);
            }

            if (o.fieldType === 'text') {
                this.textInputLength++;
                $('<div class="form-element"><div class="f-fix">' + this.__getInputText(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'hidden') {
                $('<div class="form-element"><div class="f-fix">' + this.__getInputHidden(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'password') {
                $('<div class="form-element"><div class="f-fix">' + this.__getInputPassword(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'textarea') {
                $('<div class="form-element"><div class="f-fix">' + this.__getTextarea(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'radio') {
                $('<div class="form-element"><div class="f-fix">' + this.__getInputRadio(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'select') {
                $('<div class="form-element"><div class="f-fix">' + this.__getInputSelect(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'checkbox') {
                $('<div class="form-element"><div class="f-fix">' + this.__getInputCheckbox(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'file') {
                this.hasFileUpload = true;
                $('<div class="form-element"><div class="f-fix">' + this.__getInputFile(o) + this.__getDescText(o) + '</div></div>').appendTo($formLI);
            } else if (o.fieldType === 'button') {
                this.__getButtons(o).appendTo($formLI);
            } else if (o.fieldType === 'str') {
                this.__getStr(o).appendTo($formLI);
            }

            return $formLI;
        },

        __getDescText: function(o) {
            var descText = o.descText ? ('<span class="desc-span" >' + o.descText + '</span>') : '';

            return descText;
        },

        __getInputText: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var fieldValue = o.fieldValue || '';
            var css = o.css || '';
            var className = o.className || '';
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';

            return '<input style="' + css + '" type="text" name="' + fieldName + '" id="' + fieldID + '" value="' + fieldValue + '" class="input-text ' + className + disabledClass + '" autocomplete="off"' + readonly + disabled + '>';
        },

        __getInputPassword: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var fieldValue = o.fieldValue || '';
            var css = o.css || '';
            var className = o.className || '';
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';

            return '<input style="' + css + '" type="password" name="' + fieldName + '" id="' + fieldID + '" value="' + fieldValue + '" class="input-text ' + className + disabledClass + '" autocomplete="new-password"' + readonly + disabled + '>';
        },

        __getTextarea: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var fieldValue = o.fieldValue || '';
            var css = o.css || '';
            var className = o.className || '';
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';

            return '<textarea style="' + css + '" name="' + fieldName + '" id="' + fieldID + '" class="textarea ' + className + disabledClass + '"' + readonly + disabled + '>' + fieldValue + '</textarea>';
        },

        __getInputRadio: function(o) {
            var fieldName = o.fieldName || '';
            var fieldValue = o.fieldValue || '';
            var opts = o.options;
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';
            var radioList = [];
            var checked = '';

            if (!opts || opts.length == 0) {
                return;
            }

            for (var i = 0, len = opts.length; i < len; i++) {
                checked = opts[i].fieldValue == fieldValue ? ' checked' : '';
                radioList.push('<label class="form-element-label"><input class="input-radio' + disabledClass + '" type="radio" name="' + fieldName + '" id="' + fieldName + i + '" value="' + opts[i].fieldValue + '"' + checked + readonly + disabled + '><span class="radio-text">' + opts[i].fieldName + '</span></label>');
            }

            return radioList.join('\n');
        },

        __getInputSelect: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var fieldValue = o.fieldValue || '';
            var opts = o.options;
            var css = o.css || '';
            var className = o.className || '';
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';
            var selectList = [];
            var selected = '';

            selectList.push('<select style="' + css + '" name="' + fieldName + '" id="' + fieldID + '" class="select ' + className + disabledClass + '"' + readonly + disabled + '>');
            selectList.push('<option value="" title="'+ this.opts.selectDefaultText +'">' + this.opts.selectDefaultText + '</option>');

            if (opts && opts.length > 0) {
                for (var i = 0, len = opts.length; i < len; i++) {
                    selected = opts[i].fieldValue == fieldValue ? ' selected' : '';
                    selectList.push('<option value="' + opts[i].fieldValue + '" ' + selected + ' title="'+ opts[i].fieldName +'">' + opts[i].fieldName + '</option>');
                }
            }

            selectList.push('</select>');

            return selectList.join('\n');
        },

        __getInputCheckbox: function(o) {
            var fieldName = o.fieldName || '';
            var fieldValue = o.fieldValue || '';
            var opts = o.options;
            var readonly = o.readonly ? ' readonly' : '';
            var disabled = o.disabled ? ' disabled' : '';
            var disabledClass = readonly && disabled ? ' disabled' : '';
            var checkboxList = [];
            var checked = '';

            if (!opts || opts.length == 0) {
                return;
            }

            for (var i = 0, len = opts.length; i < len; i++) {
                checked = opts[i].fieldValue == fieldValue ? ' checked' : '';
                checkboxList.push('<label class="form-element-label" title="' + opts[i].fieldName + '"><input class="input-checkbox' + disabledClass + '" type="checkbox" name="' + fieldName + '" id="' + fieldName + i + '" value="' + opts[i].fieldValue + '"' + checked + readonly + disabled + '><span class="checkbox-text">' + opts[i].fieldName + '</span></label>');
            }

            return checkboxList.join('\n');
        },

        __getInputFile: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var opts = o.options;
            var css = o.css;

            if (!opts || opts.length == 0) {
                return '<input type="file" name="' + fieldName + '" id="' + fieldID + '">';
            }

            var fileList = ['<input style="' + css + '" type="file" name="' + fieldName + '" id="' + fieldID + '">'];

            for (var i = 0, len = opts.length; i < len; i++) {
                fileList.push('<a href="' + opts[i].fileUrl + '" class="file-link" target="_blank">' + opts[i].fileName + '</a>');
            }

            return fileList.join('\n');
        },

        __getButtons: function(o) {
            var opts = o.options;
            var cssName = 'default';
            var $formElement = $('<div class="form-element"><div class="f-fix"></div></div>');

            if (!opts || opts.length == 0) {
                return $formElement;
            }

            for (var i = 0, len = opts.length; i < len; i++) {
                cssName = opts[i].cssName ? opts[i].cssName : 'default';
                callback = opts[i].callback;
                $('<button class="btn btn-middle btn-' + cssName + '" type="' + opts[i].type + '" name="' + opts[i].name + '" id="' + opts[i].id + '"><span><span>' + opts[i].text + '</span></span></button>').click($.proxy(function(f, e) {
                    if (typeof f === "function") {
                        f(this, e);
                    }
                }, this, opts[i].callback)).appendTo($formElement.find('.f-fix'));
            }

            return $formElement;
        },

        __getStr: function(o) {
            var $formElement = $('<div class="form-element"><div class="f-fix">' + (o.fieldValue || '') + '</div></div>');

            return $formElement;
        },

        __getInputHidden: function(o) {
            var fieldName = o.fieldName || '';
            var fieldID = o.fieldID ? o.fieldID : o.fieldName;
            var fieldValue = o.fieldValue || '';

            return '<input type="hidden" name="' + fieldName + '" id="' + fieldID + '" value="' + fieldValue + '" autocomplete="off">';
        },

        __getFormLabel: function(labelText) {
            return '<label class="form-label">' + labelText + '：</label>';
        },

        __getFormElement: function() {
            return '<div class="form-element"><div class="f-fix"></div></div>';
        },

        __getFormItemClassName: function(fieldName) {
            if (!fieldName) {
                return '';
            }

            return fieldName.replace(/\./g, '-').toLowerCase();
        },

        __getFormClassName: function(formID) {
            if (!formID) {
                return '';
            }

            return formID.replace(/\./g, '-').toLowerCase();
        },

        __setHideTextInput: function() {
            if (this.textInputLength === 1) {
                return '<input type="text" name="placeholder" class="hidden">';
            }

            return '';
        },

        getStrForm: function() {
            return this.strForm;
        },

        getForm: function() {
            return this.form;
        }
    };

    /* 多语言处理 */
    function langHandler(value, defaultText) {
        return window.Lang && window.Lang[value] ? window.Lang[value] : defaultText || value;
    }

    return FormUI;

}));