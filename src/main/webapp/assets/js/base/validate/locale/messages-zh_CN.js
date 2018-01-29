/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
(function ($) {
	$.extend($.validator.messages, {
		required: "必填字段",
		remote: "请修正该字段",
		email: "请输入正确格式的电子邮件",
		url: "请输入合法的网址",
		date: "请输入合法的日期",
		dateISO: "请输入合法的日期 (ISO).",
		number: "请输入合法的数字",
		digits: "只能输入正整数和零",
		creditcard: "请输入合法的信用卡号",
		equalTo: "请再次输入相同的值",
		accept: "请输入拥有合法后缀名的字符串",
		maxlength: $.validator.format("请输入一个长度最多是 {0} 的字符串"),
		minlength: $.validator.format("请输入一个长度最少是 {0} 的字符串"),
		rangelength: $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
		range: $.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
		max: $.validator.format("请输入一个最大为 {0} 的值"),
		min: $.validator.format("请输入一个最小为 {0} 的值"),
        idCard: "请输入正确的身份证号码",
        useName: "用户名只能包括中文字、英文字母、数字和下划线",
        mobile: "请输入正确的手机号码",
        tel: "请输入正确的电话号码",
        contact: "请输入正确的手机号或电话号码",
        fax: "请输入正确传真号码",
        zipCode: "请输入正确的邮政编码",
        dateTime: "请输入正确的时间格式：YYYY-MM-DD HH:MM:SS",
        compareDate: "结束日期必须大于开始日期",
        compareEqualDate: "结束日期必须大于或者等于开始日期",
        compareLessThanDate:"开始日期必须小于结束日期",
        compareLessThanEqualDate:"开始日期必须小于或者等于结束日期",
        splitMaxlength: $.validator.format("最大值不能大于 {0} 个"),
        splitMinlength: $.validator.format("最小值不能小于 {0} 个"),
        filetype: "上传的文件类型不允许",
        inputBox:"只允许输入 数字、字母、连接符、下划线的组合",
        hex: "只允许输入0-F的四位字符",
        letterAndNumber: "必须包含字母+数字",
        specialCharacters: "不能包含特殊字符",
        only: "你输入的值有重复，请重新输入"
	});
}(jQuery));