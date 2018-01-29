(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['jquery', 'common', 'dialog', 'logindata'], factory);
    } else {
        window.Common = factory($, Common, Dialog, LoginData);
    }
}(
    function($, Common, Dialog, LoginData) {
    	
    	if(!window.Lang){
    		Lang = {};
    	}

        var Common = Common || {};
        var loginData = new LoginData();

        /* 截取采集设备码 */
        Common.formatRegCode = function(regCode) {
            if (!regCode) {
                return '';
            }
            regCode = regCode.substr(9);

            var newRegCode = regCode.substr(regCode.length - 9);

            if (newRegCode == "000000000") {
                regCode = regCode.substring(0, regCode.length - 9);
            } else {
                regCode = regCode.substring(0, 16);
            }

            return regCode;
        };
        
        /* 故障类型转换 */
        Common.getFaultType = function(faultType, rows) {
        	if (!faultType) {
                return Lang.fault;
            }
            if (faultType == "1") {
                //故障
                return Lang.fault;
            } else if(faultType == "2"){
                //告警
                return Lang.faultWarning;
            } else if(faultType == "3"){
                //预警
                return Lang.faultRemind;
            }else{
                //离线
                return Lang.offline;
            }
            return Lang.fault;
        };
        
        //单元格换色
        Common.styler = function(faultType, rows) {
        	if (!faultType) {
                return 'background-color:#ef3d43;color:#fff;';
            }
            if (faultType == "1") {
                //故障
                return 'background-color:#ef3d43;color:#fff;';
            } else if(faultType == "2"){
                //告警
                return 'background-color:#F67F32;color:#fff;';
            } else if(faultType == "3"){
                //预警
                return 'background-color:#ffac29;color:#fff;';
            }else{
                //离线
                return 'background-color:#999;color:#fff;';
            }
            return 'background-color:#ef3d43;color:#fff;';
        };

        /* 特殊※字符替换成换行符 */
        Common.characterReplaceBR = function(str) {
            var element = '<hr class="line" />',
                reg = new RegExp(Constant.SpecialCode, 'g');

            return str.replace(reg, element);
        };

        /* 将0,1布尔转换成是否 */
        Common.getTransformBooleam = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return Lang.no;
                    break;
                case 1:
                    return Lang.yes;
                    break;
                default:
                    return num;
            }
        };
        
        /* 将客户服务质量评分转换成文字 */
        Common.getTransforReturnVisitScore = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return Lang.verySatisfied;
                    break;
                case 2:
                    return Lang.satisfaction;
                    break;
                case 3:
                    return Lang.general;
                    break;
                case 4:
                    return Lang.dissatisfied;
                    break;
                default:
                    return num;
            }
        };

        /* 将电梯技监状态转换成文字 */
        Common.getTransformSuperviseStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-blue-green">' + Lang.superviseStatusNormal + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-primary">' + Lang.superviseStatusTakePaymentNotice + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-warning">' + Lang.superviseStatusTakePayment + '</span>';
                    break;
                case 4:
                    return '<span class="tag tag-danger">' + Lang.superviseStatusReservation + '</span>';
                    break;
                default:
                    return num;
            }
        };
        
        /* 将维保单状态转换成文字 1：待派单；2:待接受；3：已接受；4：已签到；5：已完成；6:已评分；7：已作废 */
        Common.getTransformAircUpkeepStatus = function(num) {
        	num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-warning">' + Lang.WaitSending + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-brown">' + Lang.statusOfAccepted + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-warning">' + Lang.statusOfHavaAccepted + '</span>';
                    break;
                case 4:
                    return '<span class="tag tag-info">' + Lang.pepoleStatusOfSignIn + '</span>';
                    break;
                case 5:
                    return '<span class="tag tag-success">' + Lang.statusOfCompleted + '</span>';
                    break;
                case 6:
                    return '<span class="tag tag-success">' + Lang.customerHasOrdered + '</span>';
                    break;
                case 7:
                    return '<span class="tag tag-success">' + Lang.orderMissing + '</span>';
                    break;
                default:
                    return ;
            }
        };

        /* 将电梯状态转换成文字 */
        Common.getTransformElevatorStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1: //在线
                    return '<span class="tag tag-success" data-class="tag tag-success">' + Lang.elevatorStatusOfOnline + '</span>';
                    break;
                case 2: //离线
                    return '<span class="tag tag-default" data-class="tag tag-default">' + Lang.elevatorStatusOfOffline + '</span>';
                    break;
                case 3: //故障
                    return '<span class="tag tag-danger" data-class="tag tag-danger">' + Lang.elevatorStatusOfFault + '</span>';
                    break;
                default:
                    return '<span class="tag tag-primary" data-class="tag tag-primary">' + num + '</span>';
            }
        };

        /* 将电梯使用状态转换成文字 */
        Common.getTransformElevUsageState = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return Lang.elevatorStatusOfNormal;
                    break;
                case 1:
                    return Lang.elevStop;
                    break;
                case 2:
                    return Lang.elevLongStop;
                    break;
                case 3:
                    return Lang.elevDemolition;
                    break;
                default:
                    return num;
            }
        };

        /* 将电梯类型转换成文字 */
        Common.getTransformElevatorType = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return Lang.verticalElevator;
                    break;
                case 2:
                    return Lang.staircaseElevator;
                    break;
                default:
                    return '';
            }
        };

        /*  将楼盘类型(使用场合)转换成文字
        0.待定
        1.办公综合楼宇
        2.工厂企业楼宇
        3.机关单位楼宇
        4.政府部门楼宇
        5.住宅小区楼宇
        6.商住综合楼宇
        10.重点宾馆酒店
        11.重点公共场馆
        12.重点场所车站
        13.重点场所机场
        14.重点场所商场
        15.重点学院校园
        16.重点场所医院
        17.重点娱乐场所
        99.其他场所楼宇
        */
        Common.getTransformBuildingType = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 0: //待定
                    return Lang.usageSite0;
                    break;
                case 1: //办公综合楼宇
                    return Lang.usageSite1;
                    break;
                case 2: //工厂企业楼宇
                    return Lang.usageSite2;
                    break;
                case 3: //机关单位楼宇
                    return Lang.usageSite3;
                    break;
                case 4: //政府部门楼宇
                    return Lang.usageSite4;
                    break;
                case 5: //住宅小区楼宇
                    return Lang.usageSite5;
                    break;
                case 6: //商住综合楼宇
                    return Lang.usageSite6;
                    break;
                case 10: //重点宾馆酒店
                    return Lang.usageSite10;
                    break;
                case 11: //重点公共场馆
                    return Lang.usageSite11;
                    break;
                case 12: //重点场所车站
                    return Lang.usageSite12;
                    break;
                case 13: //重点场所机场
                    return Lang.usageSite3;
                    break;
                case 14: //重点场所商场
                    return Lang.usageSite14;
                    break;
                case 15: //重点学院校园
                    return Lang.usageSite15;
                    break;
                case 16: //重点场所医院
                    return Lang.usageSite16;
                    break;
                case 17: //重点娱乐场所
                    return Lang.usageSite17;
                    break;
                case 98: //研发调试
                    return Lang.usageSite98;
                    break;
                case 99: //其他场所楼宇
                    return Lang.usageSite99;
                    break;
                default:
                    return '';
            }
        };

        /* 将使用状态转换成文字 */
        Common.getElevUsageState = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return '<span class="tag tag-blue-green">' + Lang.elevNormal + '</span>';
                    break;
                case 1:
                    return '<span class="tag tag-primary">' + Lang.elevStop + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-purple">' + Lang.elevLongStop + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-primary">' + Lang.elevDemolition + '</span>';
                    break;
                default:
                    return num;
            }
        };
        /* 将年检状态转换成文字 */
        Common.getYearStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-blue-green ">' + Lang.normalTime + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-danger">' + Lang.extendedTime + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-warning">' + Lang.extendedWarning + '</span>';
                    break;
                default:
                    return '<span class="tag tag-blue-green">' + Lang.normalTime + '</span>';
            }
        };

        /* 格式化额定载重 */
        Common.getLoadWeightTransForm = function(value) {
            if (value) {
                return '<span>' + value + '</span><span class="unit">kg</span>';
            } else {
                return '';
            }
        };

        /* 格式化额定速度 */
        Common.getSpeedTransForm = function(value) {
            if (value) {
                return '<span>' + value + '</span><span class="unit">m/s</span>';
            } else {
                return '';
            }
        };

        /* 将年检记录状态转换成文字 */
        Common.getYearRecordStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-primary year-inspection-status">' + Lang.autoNew + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-brown year-inspection-status">' + Lang.pendingAdmission + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-warning year-inspection-status">' + Lang.waitingBilling + '</span>';
                    break;
                case 4:
                    return '<span class="tag tag-info year-inspection-status">' + Lang.technicalPendingAppointment + '</span>';
                    break;
                case 5:
                    return '<span class="tag tag-success year-inspection-status">' + Lang.supervisionComplete + '</span>';
                    break;
                default:
                    return '<span class="tag tag-success year-inspection-status">' + Lang.supervisionComplete + '</span>';
            }
        };

        /* 将维保人员类别转换成文字 */
        Common.maintainerType = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return Lang.informationManagementPersonnel;
                    break;
                case 2:
                    return Lang.maintenancePersonnel;
                    break;
                default:
                    return ' ';
            }
        };

        /* 将保养单状态转换成文字 */
        Common.maintenanceBillStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-pink">' + Lang.waitSure + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-primary">' + Lang.waitSend + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-info">' + Lang.statusOfAccepted + '</span>';
                    break;
                case 4:
                    return '<span class="tag tag-purple">' + Lang.waitSignIn + '</span>';
                    break;
                case 5:
                    return '<span class="tag tag-brown">' + Lang.waitSignOut + '</span>';
                    break;
                case 6:
                    return '<span class="tag tag-green">' + Lang.waitCheck + '</span>';
                    break;
                case 7:
                    return '<span class="tag tag-success">' + Lang.checkComplete + '</span>';
                    break;
                default:
                    return ' ';
            }
        };

        /* 将变更保养单状态转换成文字 */
        Common.getFormatterChangeStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-danger">' + Lang.changeApply + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-success">' + Lang.changeSuccess + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-Fail">' + Lang.changeFail + '</span>';
                    break;
                default:
                    return ' ';
            }
        };

        /* 将保养单申请变更状态转换成文字 */
        Common.maintenanceBillChangeStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-pink">' + Lang.pepoleStatusOfChange + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-primary">' + Lang.changeStatusCompleted + '</span>';
                    break;
                default:
                    return ' ';
            }
        };

        /* 将故障状态转换成文字 */
        Common.getTransformFaultStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-success">' + Lang.elevatorStatusOfNormal + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-danger">' + Lang.elevatorStatusOfFault + '</span>';
                    break;
                default:
                    return ' ';
            }
        };

        /* 将设备状态转换成文字 */
        Common.getTransformDeviceStatus = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-blue-green">' + Lang.elevatorStatusOfOnline + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-default">' + Lang.elevatorStatusOfOffline + '</span>';
                    break;
                default:
                    return '';
            }
        };

        /* 将物业人员类型转换成文字 */
        Common.getTransformPersonType = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return Lang.personInCharge;
                    break;
                case 2:
                    return Lang.elevatorDrivers;
                    break;
                default:
                    return '';
            }
        };

        /* 将救援状态转换成文字 */
        Common.getTransformRescueState = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return '<span class="tag tag-danger">' + Lang.notComplete + '</span>';
                    break;
                case 1:
                    return '<span class="tag tag-success">' + Lang.achieveEffective + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-primary">' + Lang.completeInvalid + '</span>';
                    break;
                default:
                    return '';
            }
        };

        /* 处理语音事件的操作按钮 */
        Common.getVoiceEventHandlerButtons = function(id, isRelease, postUrl) {
            postUrl = postUrl || URL.AccpetVoiceMonitor;
            var postData = {
                'id': id
            };
            var postCallback = function(dialog) {
                var $dlg = dialog.getDialogElement(),
                    $msgObj = $dlg.find(".dialog-tips"),
                    $msgBt = $dlg.find(".btn-success"),
                    href = location.href;

                $msgObj.html('<span class="text text-left text-primary"><i class="icon icon-loading"></i>' + Lang.msgLoadingProcess + '</span>');
                $msgBt.attr('disabled', true).addClass('disabled');

                //请求而未被处理的语音请求列表
                Common.getDataByAjax(postUrl, postData, function(res) {
                    if (res.success) {
                        $msgObj.html('<span class="text text-left text-success">' + Lang.msgProcessSuccess + '</span>');
                        setTimeout(function() {
                            dialog.close();
                            if (href.search('monitorOfVoice.jsp') != -1 || href.search('ctrlEventList.jsp') != -1) {
                                $('[data-datagrid="true"]').datagrid('reload');
                            }
                        }, 1300);
                    } else {
                        $msgBt.attr('disabled', false).removeClass('disabled');
                        $msgObj.html('<span class="text text-left text-danger">' + Lang.msgProcessError + '</span>');
                    }
                });
            };
            var buttons = [{
                text: Lang.cancel,
                click: function(e) {
                    e.close();
                },
                styleName: 'btn-default'
            }];

            if (!isRelease) {
                buttons.unshift({
                    text: Lang.sureOfAccepted,
                    click: postCallback,
                    styleName: 'btn-success'
                });
            }

            return buttons;
        };

        /* 平台接入状态转换为彩色文字 0,接入平台，1或者null表示未接入平台*/
        Common.platformStatus = function(num) {

            if (undefined == num) {
                return '<span class="tag tag-default">' + Lang.accessNone + '</span>';
            }
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return '<span class="tag tag-blue-green">' + Lang.accessSuccess + '</span>';
                    break;
                case 1:
                    return '<span class="tag tag-default">' + Lang.accessNone + '</span>';
                    break;
                default:
                    return '<span class="tag tag-default">' + Lang.accessNone + '</span>';
            }
        };

        /* 将设备状态转换成文字 */
        Common.getPlatformName = function(num) {
            if (undefined == num) {
                return '<span class="tag tag-default">' + Lang.noPlatform + '</span>';
            }
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return '<span class="tag tag-default">' + Lang.noPlatform + '</span>';
                    break;
                case 1:
                    return '<span class="tag tag-blue-green">' + Lang.hangzhouPlatform + '</span>';
                    break;
                case 2:
                    return '<span class="tag tag-danger">' + Lang.nanjingPlatform + '</span>';
                    break;
                case 3:
                    return '<span class="tag tag-purple">' + Lang.wuxiPlatform + '</span>';
                    break;
                case 4:
                    return '<span class="tag tag-success">' + Lang.ningboPlatform + '</span>';
                    break;
                case 9:
                    return '<span class="tag tag-primary">' + Lang.otherPlatform + '</span>';
                    break;
                default:
                    return '';
            }
        };

        /* 取消平台接入操作*/
        Common.cancelPlatform = function(url, idsName, element, type) {
            element = element || $("#J_DataGrid");
            type = type || 'datagrid';

            var row = element.datagrid("getSelected");

            if (!row) {
                Common.msgDialog(Lang.msgPleaseSelectDataFirst, Lang.alertTitlePrompt, 'warning');
                return false;
            }

            var id = row[idsName];
            Common.removePlatformItem(url, idsName, id, element, type);
        };

        //取消平台接入操作的具体实现
        Common.removePlatformItem = function(url, idName, ids, obj, type) {
            var postData = {};

            postData[idName] = ids;
            obj = obj || $('#J_DataGrid');

            Common.confirmDialog(Lang.msgDoYouWantToCancelPlatform, Lang.alertTitlePrompt, function() {
                Common.getDataByAjax(url, postData, function(res) {
                    if (res.success) {
                        Common.msgDialog(Common.langHandler(res.msgKey, res.msg), Lang.alertTitlePrompt, 'success', function() {
                            if (type == "datagrid") {
                                obj.datagrid('reload');
                            } else {
                                obj.treegrid('reload');
                            }
                        });
                    } else {
                        Common.simpleDialog(Common.langHandler(res.msgKey, res.msg));
                    }
                });
            });

        };

        //转换短信发送记录格式化
        Common.getMessageCommitResult = function(num) {
            num = Common.stringToNumber(num);
            switch (num) {
                case 1:
                    return '<span class="tag tag-blue-green">' + Lang.statusOK + '</span>';;
                    break;
                case 0:
                    return '<span class="tag tag-danger">' + Lang.statusFailure + '</span>';
                    break;
                default:
                    return num;
            }
        };

        Common.getMessageResultDescribe = function(str) {
            if (str == Lang.submitSuccess) {
                return '-';
            } else {
                return str;
            }
        };

        //过滤特殊字符 “”，datagird鼠标放上去，显示title
        Common.stripscript = function(str) {
            /* var pattern = new RegExp("[`~\\\\!@#\“”\$%\^&\*\(\)_\+<>\?:\"{},\.\/;'\[\\]]"); */
            var pattern = new RegExp("[`\“”\"]");
            var value = "";
            for (var i = 0; i < str.length; i++) {
                value = value + str.substr(i, 1).replace(pattern, '  ');
            }
            return '<div class="datagrid-popup-context" title="' + value + '">' + value + '</div>';
        };

        Common.getAppMessageType = function(str) {

            if (str == "trapsPeopleMaintain") {
                return Lang.trapsPeople;
            } else
            if (str == "nonTrapsPeopleMaintain") {
                return Lang.noTrapsPeople;
            } else
            if (str == "planMaintain") {
                return Lang.planMaintain;
            } else
            if (str == "upkeepMaintain") {
                return Lang.upkeepMaintain;
            } else
            if (str == "onlyNotify") {
                return Lang.customMessageType;
            }

        };

        /* 创建VLC播放器 */
        Common.getVLCPlayer = function(fileUrl, width, height) {
            $('#J_VlcContent').empty();
            width = width || 460;
            height = height || 260;
            //判断浏览器是IE还是其它(1:IE、2:其它)
            var isIE = 0;
            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                isIE = 1;
            } else {
                isIE = 2;
            }
            var url = Common.pieceUrl('/sys/page/base/vlcPlayer.jsp') + '&fileUrl=' + fileUrl + '&width=' + width + '&height=' + height + '&isIE=' + isIE;
            var $html = $('<iframe width="' + width + '" height="' + height + '" frameborder="0" scrolling="no" src="' + url + '" id="iframeId"></iframe>');

            $html.appendTo($('#J_VlcContent'));

            $('#J_VlcContent').removeClass('hidden');
            $("#J_VlcContent").css({
                'left': 0 + "px",
                'top': 20 + "px",
                'z-index': 100
            });

        }

        //国际化配置项类型格式化
        Common.languageConfigType = function(num) {

            num = Common.stringToNumber(num);

            //substring截取前面的数字
            switch (num) {
                case 1:
                    return Lang.pageInfor.substring(1, Lang.pageInfor.length);
                    break;
                case 2:
                    return Lang.widgetInfo.substring(1, Lang.widgetInfo.length);;
                    break;
                case 3:
                    return Lang.serverInfo.substring(1, Lang.serverInfo.length);;
                    break;
                case 4:
                    return Lang.systemBaseInfo.substring(1, Lang.systemBaseInfo.length);;
                    break;
                case 5:
                    return Lang.menuInfo.substring(1, Lang.menuInfo.length);;
                    break;
                case 6:
                    return Lang.protocol.substring(1, Lang.protocol.length);;
                    break;
                case 7:
                    return Lang.faultsms.substring(1, Lang.faultsms.length);;
                    break;
                case 8:
                    return Lang.map.substring(1, Lang.map.length);;
                    break;
                case 99:
                    return Lang.other.substring(2, Lang.other.length);;
                    break;
                default:
                    return '';
            }
        }

        //国际化配置项类型格式化
        Common.translateProgress = function(num) {
            if (undefined == num) {
                num = 0;
            }
            num = Common.stringToNumber(num);
            switch (num) {
                case 0:
                    return '<span class="tag tag-danger">' + '0%' + '</span>';
                    break;
                case 50:
                    return '<span class="tag tag-warning">' + '50%' + '</span>';
                    break;
                case 100:
                    return '<span class="tag tag-blue-green">' + '100%' + '</span>';
                    break;
                default:
                    return '';
            }
        };

        //创建 监控提醒弹出框
		Common.buildMonitorRemindTips = function(limitTime, callback){
			if(limitTime >= 10000){
				return;
			}
			setTimeout(function(){
				var contentStr = '<div class="form-group form-grid form-monitor-remind-tips">' +	
					'<ul class="list-unstyled">' +	
						'<li class="form-item">' +	
							'<span class="tip-time-default">'+Lang.residenceTimeInTheMonitoringPage+'</span>' +
							'<b class="tip-time-primary tip-time-primary-left">'+limitTime+'</b>' +
							'<span class="tip-time-primary">'+Lang.mintue+'</span>' +
						'</li>' +
						'<li class="form-item">' +
							'<span class="tip-time-default">'+Lang.systemWill+'</span>' +
							'<b class="tip-time-danger tip-limit-time" id="J_LimitTime">10</b>' +
							'<span class="tip-time-default">'+Lang.returnToIndex+'<span>' +
						'</li>' +
					'</ul>' +
				'</div>';
				
				var s = 10;
				var timeId = 0;
				var dialog = new Dialog({
					title:Lang.warmTips,
					width:400,
					height:100,
					buttons: [
						{text:Lang.continueToMonitor, click:function(e){
							e.close();
							if(callback && typeof callback == 'function'){
		                    	callback();
		        			}
						}, styleName:'btn-success'}
					],
					onClose: function(e){
						clearTimeout(timeId);
						if(callback && typeof callback == 'function'){
	                    	callback();
	        			}
					},
			        content: contentStr
				});
				
				dialog.open();
				
				var element = $('#J_LimitTime');
			    element.html('<span data-time="true">' + s + '</span>');
			    setTimeout(function() {
			        if (s <= 0) {
			        	var jumpUrl = loginData.user.homepageUrl;
			        	var sid = loginData.getSID();
			        	var customerCode = loginData.user.customerCode;
			        	location.href = jumpUrl + '?sid=' + customerCode + '/' + sid;
			            return;
			        }
			        if (element.find('[data-time="true"]').length > 0) {
			            element.find('[data-time="true"]').html(s);
			        }
			        s --;
			        timeId = setTimeout(arguments.callee, 1000);
			    }, 0);
			}, 1000 * 60 * limitTime);
		};

        /**
         * 提交方式转向指定页面
         * 
         * @param url:
         *            要转向的页面，例如：cube/group360view/group360view
         * @param param:
         *            json格式参数或者参数串
         * @param newWinTag:
         *            是否打开新窗口 true:新窗口
         */
        Common.redirectToSubmit = function(url, param, newWinTag) {
            if (param) {
                if (typeof param == 'string') {
                    if (param.toLowerCase() == "[object object]") {
                        return;
                    } else {
                        param = Common.param2Json(param);
                    }
                }
            }
            var hiddenElements = '';
            if (Common.isJson(param)) {
                for (var key in param) {
                    hiddenElements += '<input type="hidden" id="' + key + '" name="' + key + '" value="' + param[key] + '"/>';
                }
            }
            if (newWinTag && newWinTag == true) {
                newWinTag = "target=\"_blank\"";
            } else {
                newWinTag = "";
            }
            $('#tempDiv').remove();
            $("<div id='tempDiv'></div>").appendTo($(document.body)).css('display', 'none').append(
                '<form id="tempForm" action="' + url + '" method="post" ' + newWinTag + ' accept-charset="UTF-8">' + hiddenElements + '</form>');
            $("#tempForm").submit().remove();
        };


        /**
         * 请求参数串转换为json
         */
        Common.param2Json = function(param, overwrite) {
            param = encodeURI(param);
            var obj = {},
                pairs = param.split('&'),
                d = decodeURIComponent,
                name, value;
            $.each(pairs, function(i, pair) {
                pair = pair.split('=');
                name = d(pair[0]);
                value = $.trim(d(pair[1]));
                // 去除空格
                obj[name] = overwrite || !obj[name] ? value : [].concat(obj[name]).concat(value);
            });
            return obj;
        };

        /**
         * 判断是否为json对象
         */
        Common.isJson = function(obj) {
            return typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object object]" && !obj.length;
        };

        /**
         * 获取select多个选中的值
         */
        Common.getMultipleSelect = function(id,ids) {
        	var value = "";
            var obj ;
            if(id && id!=''){
            	obj = document.getElementById("roleIds");
            	for (i = 0; i < obj.length; i++) {
                    if (obj.options[i].selected) {
                        value += obj.options[i].value + ",";
                    }
                }
                if (value.length > 0) {
                    value = value.substr(0, value.length - 1);
                }
        	}else{
        		obj = $("#"+ids+" option:selected");
        		for (i = 0; i < obj.length; i++) {
        			value += obj[i].value + ",";
                }
                if (value.length > 0) {
                    value = value.substr(0, value.length - 1);
                }
        	}
            return value;
        }
        //根据resouceCode判断是否有存在于menulist,空压机机特有
        Common.inMenuListByResourceCode = function (resourceCode,menuList){
        	if(!menuList){
                return false;
            }
            var flag = false;
            $.each(
                menuList,
                function(i, v){
                    if(v.resourceCode == resourceCode && v.resourceStatus === 'OPEN'){
                        flag = true;
                        return false;
                    }
                }
            );
            return flag;
        }
        
        //根据resouceCode判断是否有存在于menulist,空压机机特有
        Common.inMenuListByMenuNO = function (menuNO,menuList){
        	if(!menuList){
                return false;
            }
            var flag = false;
            $.each(
                menuList,
                function(i, v){
                    if(v.menuNO == menuNO && v.resourceStatus === 'OPEN'){
                        flag = true;
                        return false;
                    }
                }
            );
            return flag;
        }
        
        //根据resouceCode判断是否有存在于menulist,空压机机特有
        Common.getCurrentMenuUrlByMenuNO = function (menuNO, menuList){
        	if(!menuList){
                return false;
            }
            var url = '';
            $.each(
                menuList,
                function(i, v){
                    if(v.menuNO == menuNO && v.resourceStatus === 'OPEN'){
                    	url = v.url;
                        return false;
                    }
                }
            );
            return url;
        }
        
        //处理undefined问题
        Common.dealUndefined = function(obj) {
        	if(obj==undefined || obj==null){
        		return '';
        	}else{
        		return obj;
        	}
        }
        /* 将app类型转为文字 */
		Common.getTransformAppType = function (num) {
		    num = Common.stringToNumber(num);
		    switch (num) {
		    case 1:
		        return '<span class="tag tag-blue-green">' + Lang.aircBusiness + '</span>';
		        break;
		    case 2:
		        return '<span class="tag tag-default">' + Lang.installApp + '</span>';
		        break;
		    default:
		        return '';
		    }
		};
		
		Common.getCurrentLanguageKey = function(){
			var languageKey = localStorage.getItem('languageKey') || 'zh_CN';
			
			return languageKey;
		};
		
		/* 将app类型转为文字 */
		Common.getLinks = function (value, rows, links) {
        	var htmlArray = '';
        	var menuClass = '';
        	var url = '';
        	var classAlias = '';
        	var target = '';
        	var menuNO = '';
        	var menuName = '';
        	var classAlias = '';
        	var iconTag = '';
        	var menuType = '';
        	var actionUrl = '';
        	var actionType = '';
        	
        	htmlArray += '<ul class="list-inline form-list-col form-list-col3">';
        	$.each(
        	    links,
        	    function(index, value){
        	    	menuClass = value.menuClass;
        	    	url = value.url;
        	    	classAlias = value.classAlias;
                    target = classAlias == 'openLink' ? '_blank' : '_self';
                    menuNO = value.menuNO;
                    menuName = value.menuName;
                    classAlias = value.classAlias;
                    iconTag = value.iconClass ? '<span class="' + value.iconClass + '"></span>' : '';
                    menuType = value.newMenuType;
                    actionUrl = value.action;
                    actionType = value.actionType;
                    
        	    	htmlArray += '<li class="link-item"><a class="operation-btn datagrid-operation-bg" data-row-id="'+rows.id+'" data-action-type="'+actionType+'" data-action="'+actionUrl+'" data-href="' + url + '" target="' + target + '" data-menu-number="' + menuNO + '" data-menu-type="' + menuType + '" data-class-alias="' + classAlias + '">' + iconTag + menuName + '</a></li>';
        	    }
        	);

        	htmlArray += '</ul>';
        	        
        	return htmlArray;
		};
		
		// 根据regCode查询流量
		// @参数 [regCode1, regCode2, ...] 
		// @带查询流量结果数组的回调函数 function([{flowNnmber: 2.31, regCode: regCode1}, {flowNnmber: 2.31, regCode: regCode1}, ...]){}
		Common.getFlowNumberByRegCodes = function(regCodes, callback){
			if(typeof regCodes === 'string'){
				regCodes = [regCodes];
			}
			if(regCodes.length === 0){
				return;
			}
			var params = {
                'regCode': regCodes.join(',')
            };
			var postUrl = '/monitor/deviceMonitor/getDeviceSimFlow';
			
            Common.getDataByAjax(postUrl, params, function(data) {
                if(!data || data.success === 0){
                	return;
                }
                var rows = data.obj;

                if(callback && typeof callback === 'function'){
                	callback(rows);
                }
            });
        };
        
        return Common;
    }
));