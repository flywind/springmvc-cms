/* 登录数据构造函数 */
(function(factory) {
    if (typeof define === "function" && define.amd) {
        define(['json'], factory);
    } else {
        window.LoginData = factory(JSON);
    }
}(function(JSON) {

    function LoginData() {
        this.storage = getStorage(LoginData.defaults.storageType);
        this.init();
    }

    // 默认参数
    LoginData.defaults = {
        storageType: 2, //本地存储类型，1为sessionStorage; 2为localStorage
        logoImage: '/base/images/logo.png', //默认的LOGO地址
        loginBackImage: '/base/images/login_bg.png', //默认登录页的背景图片地址
        loginBackColor: '#5d84a5', //默认登录页的背景颜色值
        loginHeaderColor: '#1a83cf', //默认头部标题背景色
        headImg: '/base/images/head.gif' //左边菜单上面那个头像图片地址
    };

    LoginData.prototype = {
        constructor: LoginData,

        /* 初始化数据 */
        init: function() {
            for (var i = this.storage.length; i > 0; i--) {
                var key = this.storage.key(i - 1);
                this[key] = this.getItem(key);
            }
        },

        /* 缓存登录信息至本地存储 */
        setCacheData: function(data) {
            if (!data) {
                return;
            }

            data = stringToJSON(data);

            this.setItem('company', data.company);
            this.setItem('gdhsInfo', data.gdhsInfo);
            this.setItem('menuList', data.menuList);
            this.setItem('menuTree', data.menuTree);
            this.setItem('user', data.user);
            this.setItem('sid', data.sid);
            this.setItem('customerInfo', data.customerInfo);
            this.setItem('pluginVersion', data.pluginVersion || '');
        },

        /* 设置项 */
        setItem: function(key, value) {
            setStorageItem(key, value, LoginData.defaults.storageType);
            this[key] = value;
        },

        /* 获取项 */
        getItem: function(key) {
            return getStorageItem(key, LoginData.defaults.storageType) || '';
        },

        /* 删除项 */
        removeItem: function(key) {
            removeStorage(key, LoginData.defaults.storageType);
            if (this[key]) {
                this[key] = null;
            }
        },

        /* 设置customerInfo */
        setCustomerInfo: function(data) {
            if (!data) {
                return;
            }

            data = stringToJSON(data);
            data = $.extend(true, {}, this.getItem('customerInfo'), data);
            this.setItem('customerInfo', data);
        },

        /* 返回customerInfo */
        getCustomerInfo: function() {
            return this.customerInfo;
        },

        /* 清除登录缓存数据 */
        removeCacheData: function(key) {
            this.removeItem(key);
        },

        /* 设置资源文件地址 */
        setResourcePath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('resourcePath', path);
        },
        
        /* 设置资源文件下的inovance文件地址 */
        setLibJsPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('libJsPath', path);
        },

        /* 设置本地资源文件地址 */
        setLocalResourcePath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localResourcePath', path);
        },
        
        /* 设置本地公共主键库文件地址 */
        setLocalComponentsPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localComponents', path);
        },
        
        /* 设置本地存储pages件地址 */
        setLocalPagesPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localPages', path);
        },

        /* 返回本地资源文件地址 */
        getLocalResourcePath: function() {
            return this.localResourcePath;
        },
        
        /* 设置本地公共主键库文件地址 */
        getLocalComponentsPath: function() {
        	return this.localComponents;
        },
        
        /* 设置本地存储pages件地址 */
        getLocalPagesPath: function() {
        	return this.localPages;
        },

        /* 设置本地资源JS文件地址 */
        setLocalJsPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localJsPath', path);
        },

        /* 返回本地资源JS文件地址 */
        getLocalJsPath: function() {
            return this.localJsPath;
        },

        /* 设置本地资源Skin文件地址 */
        setLocalSkinPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localSkinPath', path);
        },

        /* 返回本地资源Skin文件地址 */
        getLocalSkinPath: function() {
            return this.localSkinPath;
        },

        /* 获取树型菜单列表 */
        getMenuTree: function() {
            return this.menuTree;
        },

        /* 获取菜单列表 */
        getMenuList: function() {
            return this.menuList;
        },

        /* 获取sid */
        getSID: function() {
            return this.sid;
        },

        /* 获取公司信息 */
        getCompany: function() {
            return this.company;
        },

        /* 获取当前登录公司名 */
        getLoginCompanyName: function() {
            return this.company ? (this.company.companyName || '') : '';
        },

        /* 获取公司名称 */
        getCompanyName: function() {
            return this.customerInfo ? (this.customerInfo.companyName || '') : '';
        },

        /* 获取公司地址 */
        getCompanyAddress: function() {
            return this.customerInfo ? (this.customerInfo.address || '') : '';
        },

        /* 获取公司电话 */
        getCompanyTel: function() {
            return this.customerInfo ? (this.customerInfo.phone || '') : '';
        },

        /* 获取公司邮箱 */
        getCompanyEmail: function() {
            return this.customerInfo ? (this.customerInfo.email || '') : '';
        },

        /* 获取公司传真 */
        getCompanyFax: function() {
            return this.customerInfo ? (this.customerInfo.fax || '') : '';
        },

        /* 获取网站名称 */
        getWebsiteName: function() {
            return this.customerInfo ? (this.customerInfo.customerSiteName || '') : '';
        },

        /* 获取网站副标题 */
        getWebsiteSubName: function() {
            return this.customerInfo ? (this.customerInfo.customerSubTitle || '') : '';
        },

        /* 获取当前域名 */
        getDomain: function() {
            return this.customerInfo ? ('//' + this.customerInfo.subDomain || '') : '';
        },
        /* 获取 1.0版本下的 app 下载路径 */
        getAppUrl: function() {
            return this.customerInfo ? (this.customerInfo.appUrl || '') : '';
        },
        /* 获取 1.1版本下的 app 下载路径 */
        getEbsAppUrl: function() {
            return this.customerInfo ? (this.customerInfo.ebsAppUrl || '') : '';
        },
        /* 获取 1.0版本下的 app 二维码扫描路径*/
        getQrcodeUrl: function() {
            return this.customerInfo ? (this.customerInfo.qrcodeUrl || '') : '';
        },
        /* 获取 1.1版本下的 app 二维码扫描路径*/
        getEbsQrcodeUrl: function() {
            return this.customerInfo ? (this.customerInfo.ebsQrcodeUrl || '') : '';
        },
        
        /* 获取 1.1版本下的 app 版本号*/
        getEbsAppVersion: function() {
            return this.customerInfo ? (this.customerInfo.ebsAppVersion || '') : '';
        },

        /* 获取自定义资源文件路径 */
        getCustomResourcePath: function() {
            return this.customResourcePath;
        },

        /* 获取自定义资源文件路径 */
        setCustomResourcePath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('customResourcePath', path);
        },

        /* 设置本地图片路径 */
        setLocalImagePath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localImagePath', path);
        },

        /* 设置本地APP路径  */
        setLocalAppPath: function(path) {
            if (!path) {
                return;
            }

            this.setItem('localAppPath', path);
        },

        /* 获取本地图片路径 */
        getLocalImagePath: function() {
            return this.localImagePath;
        },

        /* 获取本地APP路径 */
        getLocalAppPath: function() {
            return this.localAppPath;
        },

        /* 获取网站logo地址 */
        getLogoImg: function() {
            var img = '';
            if (this.customerInfo) {
                img = this.customerInfo.logoImg;
                img = img ? (this.customResourcePath ? this.customResourcePath + img : img) : (this.resourcePath + LoginData.defaults.logoImage);
            }
            return img;
        },

        /* 设置网站logo地址 */
        setLogoImg: function(logoImg) {
            this.customerInfo.logoImg = logoImg;
            this.setItem('customerInfo', this.customerInfo);
        },

        /* 获取背景颜色 */
        getBackColor: function() {
            return this.customerInfo ? (this.customerInfo.backColor || LoginData.defaults.loginBackColor) : LoginData.defaults.loginBackColor;
        },
        
        /* 获取标题颜色 */
        getHeaderColor: function() {
        	return this.customerInfo ? (this.customerInfo.headerColor || LoginData.defaults.loginHeaderColor) : LoginData.defaults.loginHeaderColor;
        },

        /* 获取背景图片 */
        getBackImg: function() {
            var img = '';
            if (this.customerInfo) {
                img = this.customerInfo.backImg;
                img = img ? (this.customResourcePath + img) : (this.resourcePath + LoginData.defaults.loginBackImage);
            }

            return img;
        },

        /* 获取用户唯一标识customerCode */
        getCustomerCode: function() {
            return this.customerInfo ? (this.customerInfo.customerCode || '') : '';
        },

        /* 获取行业类型 */
        getType: function() {
            return this.customerInfo ? (this.customerInfo.type || '') : '';
        },

        /* 获取员工姓名 */
        getEmployeeName: function() {
            return this.user ? (this.user.fullName || '') : '';
        },

        /* 获取用户信息 */
        setUser: function(data) {
            this.setItem('user', data);
        },

        /* 返回user */
        getUser: function() {
            return this.user;
        },

        /* 获取用户电话 */
        getUserPhone: function() {
            return this.user ? (this.user.phone || '') : '';
        },

        /* 获取用户email */
        getUserEmail: function() {
            return this.user ? (this.user.email || '') : '';
        },

        /* 获取用户名 */
        getUserName: function() {
            return this.user ? (this.user.userName || '') : '';
        },
        /*获取用户验证电话*/
        getUserValidatePhone: function() {
            return this.user ? (this.user.validatePhone || '') : '';
        },
        /*获取用户验证邮箱*/
        getUserValidateEmail: function() {
            return this.user ? (this.user.validateEmail || '') : '';
        },

        /* 获取用户ID */
        getUserId: function() {
            return this.user ? (this.user.id || '') : '';
        },
        /* 获取用户头像 */
        getUserHeadImage: function() {
            return this.user ? (this.user.headImageUrl || this.getResourcePath() + LoginData.defaults.headImg) : this.getResourcePath() + LoginData.defaults.headImg;
        },

        /* 获取websokect服务器地址 */
        getWSDomain: function(projectName, pathName) {
            projectName = projectName || 'monitor';
            pathName = pathName || 'websocket';
            var protocol = location.protocol === 'https:' ? 'wss:' : 'ws:';
            var host = location.host;

            return protocol + '//' + host + '/' + projectName + '/' + pathName;
        },
        
        /* 获取websokect服务器地址 */
        getWebSocketUrl: function(){
        	var protocol = location.protocol === 'https:' ? 'wss:' : 'ws:';
            var host = location.host;
            return protocol + '//' + host + '/websocket';
        },

        /* 设置消息头 */
        setGDHSInfo: function(data) {
            this.setItem('gdhsInfo', data);
        },

        /* 获取消息头 */
        getGDHSInfo: function() {
            return this.gdhsInfo ? (this.gdhsInfo || null) : null;
        },

        /* 获取资源文件域名 */
        getResourcePath: function() {
            return this.resourcePath ? this.resourcePath : '';
        },
        
        /* 获取资源文件下的inovance文件地址 */
        getLibJsPath: function(){
        	return this.libJsPath ? this.libJsPath : '';
        },

        /* 获取公司理念 */
        getCompanyIdea: function() {
            return this.customerInfo ? (this.customerInfo.companyIdea || '') : '';
        },

        /* 获取登录页网址 */
        getLoginUrl: function() {
            return this.loginUrl;
        },

        /* 设置登录页网址 */
        setLoginUrl: function(str) {
            this.setItem('loginUrl', str);
        },
        
        /* 设置rdtsWebSocketUrl */
        setRdtsWebSocketUrl: function(str){
        	this.setItem('rdtsWebSocketUrl', str);
        },
        
        /* 获取rdtsWebSocketUrl */
        getRdtsWebSocketUrl: function(str){
        	return this.rdtsWebSocketUrl;
        },
        
        /* 获取key的数量 */
        getKeys: function() {

        },
        
        /* 获取pluginVersion */
        getPluginVersion: function(str){
        	return this.pluginVersion;
        }
        
    }

    window.LoginData = LoginData;

    /* 返回storage对象 */
    function getStorage(type) {
        if (type == 1) {
            return window.sessionStorage;
        } else if (type == 2) {
            return window.localStorage;
        }
    }

    /* 是否支持sessionStorage存储 */
    function isSupportStorage(type) {
        type = type || 1; //1:sessionStorage, 2:localStorage, 3:其它
        if (type == 1) {
            return 'sessionStorage' in window;
        } else if (type == 2) {
            return 'localStorage' in window;
        }
    }

    /* 信息存储在本地浏览器 */
    function setStorageItem(key, value, type) {
        type = type || 1; //1:sessionStorage, 2:localStorage, 3:其它

        var sStorage = getStorage(type);

        if (isSupportStorage()) {
            if (sStorage.getItem(key)) {
                sStorage.removeItem(key);
            }

            value = JSONToString(value);
            sStorage.setItem(key, value);
        }
    }

    /* 信息存储在本地浏览器 */
    function getStorageItem(key, type) {
        type = type || 1; //1:sessionStorage, 2:localStorage, 3:其它

        var sStorage = getStorage(type);

        if (isSupportStorage()) {
            if (!sStorage.getItem(key)) {
                return null;
            };

            return stringToJSON(sStorage.getItem(key));
        }
    }

    /* 删除本地数据库 */
    function removeStorage(key, type) {
        type = type || 1; //1:sessionStorage, 2:localStorage, 3:其它

        var sStorage = getStorage(type);

        if (isSupportStorage()) {
            if (typeof key === 'string') {
                if (getStorageItem(key, type)) {
                    sStorage.removeItem(key);
                }
                return;
            };

            if (key instanceof Array) {
                for (var i = 0; i < key.length; i++) {
                    if (getStorageItem(key[i], type)) {
                        sStorage.removeItem(key[i]);
                    }
                }
                return;
            };

            sStorage.clear();
        }
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

    /* JSON对象转字符串 */
    function JSONToString(data) {
        try {
            data = JSON.stringify(data);
        } catch (e) {
            data = data;
        }

        return data;
    };

    /* 某个值是否存在于数组中 */
    function inArray(value, arr) {
        for (var i = 0, len = arr.length; i < len; i++) {
            if (arr[i] === value) {
                return i;
            }
        }

        return -1;
    }

    return LoginData;

}));