 /* 登录数据构造函数 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'validate'], factory);
    } else {
        window.Rule = factory(jQuery, undefined);
    }
}(function($, undefined) {
	
	if(!window.Lang){
		Lang = {};
	}
	
	function Rule(){
		this.init();
	}

	Rule.prototype = {
		init: function(){
			 // 最大长度为50的字符串
	        $.validator.addMethod("inputPattern", function (value, element, param) {  
	        	var len = 0;
	        	value = value.toString().trim();
	            for (var i = 0; i < value.length; i++) {
	                 var a = value.charAt(i);
	                 if (a.match(/[^\x00-\xff]/ig) != null) 
	                {
	                    len += 2;
	                }
	                else
	                {
	                    len += 1;
	                }
	            }
	            return this.optional(element) || len<=50;
	        }, $.validator.format(Lang.inputPatternRule));  
			
			// 最大长度为500的字符串
			$.validator.addMethod("textareaPattern", function (value, element, param) {  
				var len = 0;
	        	value = value.toString().trim();
	            for (var i = 0; i < value.length; i++) {
	                 var a = value.charAt(i);
	                 if (a.match(/[^\x00-\xff]/ig) != null) 
	                {
	                    len += 2;
	                }
	                else
	                {
	                    len += 1;
	                }
	            }
	            return this.optional(element) || len<=500;
			}, $.validator.format(Lang.textareaPatternRule));  
			
			// -180到180，18位小数
			$.validator.addMethod("longitudeFormat", function (value, element, param) { 
				 value = value.toString().trim();
				 var decimal = /^-?\d+(\.\d{1,18})?$/;  
				 return this.optional(element) || decimal.test(value) && value <= 180 && value >= -180;  
			}, $.validator.format(Lang.longitudeFormatRule));
			
			// -90到90，18位小数
			$.validator.addMethod("latitudeFormat", function (value, element, param) {  
				 value = value.toString().trim();
				 var decimal = /^-?\d+(\.\d{1,18})?$/;  
				 return this.optional(element) || decimal.test(value) && value <= 90 && value >= -90;  
			}, $.validator.format(Lang.latitudeFormatRule));
			
			// 正整数 -- 只能输入正整数，最大65535
			$.validator.addMethod("positiveintegerFiveByte", function (value, element, param) {  
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint > 0 && (aint + "") == value && value <= 65535); 
			}, $.validator.format(Lang.positiveintegerFiveByteRule));
			
			// 整数9999999999 -- 只能输入正整数和0，最多支持10位数字
			$.validator.addMethod("numberTenByte", function (value, element, param) { 
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint >= 0 && (aint + "") == value && value.length <= 10); 
			}, $.validator.format(Lang.numberTenByteRule));
			
			// 正整数9999999999 -- 只能输入正整数，最多支持10位数字
			$.validator.addMethod("numberElevByte", function (value, element, param) { 
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint > 0 && (aint + "") == value && value.length <= 10); 
			}, $.validator.format(Lang.numberElevByteRule));
			
			// 整数9999 -- 只能输入正整数和0
			$.validator.addMethod("numberFourByte", function (value, element, param) {  
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint >= 0 && (aint + "") == value && value <= 9999); 
			}, $.validator.format(Lang.numberFourByteRule));
			
			// 正整数9999 -- 1-9999
			$.validator.addMethod("positiveintegerFourByte", function (value, element, param) {  
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint > 0 && (aint + "") == value && value <= 9999); 
			}, $.validator.format(Lang.positiveintegerFourByteRule));
			
			// -99999到99999，2位小数
			$.validator.addMethod("positiveintegerEightByte", function (value, element, param) {  
				 value = value.toString().trim();
				 var decimal = /^-?\d+(\.\d{1,2})?$/;  
				 return this.optional(element) || decimal.test(value) && value <= 99999 && value >= -99999;  
			}, $.validator.format(Lang.positiveintegerEightByte));
			
			// 提醒时间 -- 只能输入正整数，最小为24最大为9999的值
			$.validator.addMethod("remindTime", function (value, element, param) {  
				value = value.toString().trim();
				var aint = parseInt(value);	
		        return this.optional(element) || (aint > 0 && (aint + "") == value && value <= 9999 && value >= 24); 
			}, $.validator.format(Lang.remindTimeRule));
			
			// 字符验证  
			$.validator.addMethod("string", function (value, element) {  
			   value = value.toString().trim();
			   return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);  
			}, Lang.stringRule);  
			
			// 温度格式
			$.validator.addMethod("temperatureFormat", function (value, element) { 
			   value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= -999.9 && value <= 999.9);  
			}, $.validator.format(Lang.temperatureFormatRule));  
			
			// 验证值小数999.99
			$.validator.addMethod("decimalTwo", function (value, element) {  
			   value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,2})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= 0 && value <= 999.99);  
			}, $.validator.format(Lang.decimalTwoRule)); 
			
			// 验证值小数999.9999
			$.validator.addMethod("decimalThree", function (value, element) {  
			   value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,4})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= 0 && value <= 999.9999);  
			}, $.validator.format(Lang.decimalThreeRule)); 

			// 验证百分比
			$.validator.addMethod("percentage", function (value, element) { 
				value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,2})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= 0 && value <= 100);  
			}, $.validator.format(Lang.percentageRule)); 
			
			// 验证值小数65535,最多支持6位小数  
			$.validator.addMethod("decimaSix", function (value, element) {
				value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,6})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= 0 && value <= 65535);  
			}, $.validator.format(Lang.decimaSixRule)); 
			
			// 验证值小数999999999,最多支持2位小数  
			$.validator.addMethod("decimaSeven", function (value, element) {
				value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,2})?$/;  
			   return this.optional(element) || (decimal.test(value) && value > 0 && value < 999999999);  
			}, $.validator.format(Lang.decimaSevenRule)); 
			
			// 小数1.00 
			$.validator.addMethod("percentageSix", function (value, element) {  
				value = value.toString().trim();
			   var decimal = /^-?\d+(\.\d{1,6})?$/;  
			   return this.optional(element) || (decimal.test(value) && value >= 0 && value <= 1);  
			}, $.validator.format(Lang.percentageSixRule)); 
			
			// 手机号码验证  
			$.validator.addMethod("isMobile", function (value, element) {
				value = value.toString().trim();
		        return this.optional(element) || (value.length == 11 && /^0?1[3|4|5|7|8|9][0-9]\d{8}$/.test(value));
			}, Lang.isMobileRule);  
			
			//电话号码与手机验证
			$.validator.addMethod("isTel", function (value, element) {  
				value = value.toString().trim();
			   //电话号码格式010-12345678 或者  0180-12345678  或者 12345678 或者 18877775555
               var contact = /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/g;
               if((value.length != 0) && (value.length < 7)){
               		return false;
               }
               if((value.length != 0) && (value.length > 13)){
              		return false;
               }
               return this.optional(element) || (contact.test(value));
			}, Lang.isTelRule);  
			
			// 邮箱验证  
			$.validator.addMethod("isEmail", function (value, element) {
				value = value.toString().trim();
				if((value.length != 0) && (value.length > 256)){
              		return false;
                }
				return this.optional(element) || /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$/i.test(value);
			}, Lang.isEmailRule); 

			//传真号码验证
			$.validator.addMethod("isFax", function (value, element) {  
				value = value.toString().trim();
				var fax = /^(\d{3,4}-?)?\d{7,8}$/g;
			    return this.optional(element) || (fax.test(value));
			}, Lang.isFaxRule); 

			// 邮政编码验证  
			$.validator.addMethod("isZipCode", function (value, element) {  
				value = value.toString().trim();
			   var zip = /^[0-9]{6}$/;  
			   return this.optional(element) || (zip.test(value));  
			}, Lang.isZipCodeRule); 
			
			//密码  6到20之间的字符串，数字和字母组合，不能输入特殊字符(!@#$&_,.)
			$.validator.addMethod("passwordFormat", function (value) { 
				var reg = /^(?![^A-Za-z]+$)(?![^0-9]+$)[a-zA-Z0-9\u4e00-\u9fa5!@#$&_,.]{6,20}$/;
		        return reg.test(value); 
		    }, Lang.passwordFormatRule);
		}
	};		
	
    return new Rule();
}));
