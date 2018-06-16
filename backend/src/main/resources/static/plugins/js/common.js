/**
 * Created by luya on 2017/10/19.
 */
/**
 * 右下角提示信息页设置
 *
 * @param response ajax返回值
 * @param app   应用类型：应用，服务，资源分区等
 * @param name  应用名称
 * @param message 提示信息 例：编辑成功；添加失败
 */
function showResult(response, app, name, title) {
    if (response['status'] == 0) {
        var _div = $('<div>');
        _div.load('alert_success.html', function () {
            $(this).find('#title').html(title);
            $(this).find('#message').html(app + " <strong>" + name + "</strong> " + title);
            var child = $(this).children();
            child.fadeTo(3000, 500).slideToggle(1000, function () {
                child.alert('close');
            });
            $('.content').append(child);
        });
    }
    else {
        var _div = $('<div>');
        _div.load('alert_error.html', function () {
            $(this).find('#title').html("失败");
            $(this).find('#message').html(response['message']);
            var child = $(this).children();
            child.fadeTo(3000, 500).slideToggle(1500, function () {
                child.alert('close');
            });
            $('.content').append(child);
        });
    }
}

function dateFormat(datetime) {
    var date = new Date(datetime);
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + (date.getHours()) : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + (date.getMinutes()) : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + (date.getSeconds()) : date.getSeconds());
    return Y + M + D + h + m + s;
}

function showTags(inputId){
    var obj = new Object();
    if(inputId==null||inputId==""){
        return;
    }
    obj.inputId = inputId;
    //初始化
    obj = (function(obj){
        obj.tagValue="";
        obj.isDisable = false;
        obj.setInputValue;// 设置初始值
        obj.deleteFunc;// 删除事件，自己实现
        obj.tagModelFunc;//设置显示的格式，自己实现
        obj.initTagEvent;// 注册实现
        obj.editFunc;// 编辑事件,自己实现
        return obj;
    })(obj);

    /**
     * 初始化界面
     * @param data 要显示的数据数组
     * @param disable 是否允许编辑删除
     */
    obj.init = function (data,disable) {
        var inputId = this.inputId;
        this.data = data;
        this.isDisable = disable;
        if(!this.isDisable){
            $("#"+inputId).attr("ds","1");
        }else{
            $("#"+inputId).attr("ds","0");
        }
        if (data != null && data != "") {
            obj.setInputValue(this.inputId);
        }
        return;
    }

    /**
     *  设置初始值
     * @param inputId
     */
    obj.setInputValue = function (inputId) {
        var tagListContaine = $("#" + inputId + " .tagList");
        for (var i = 0; i < this.data.length; i++) {
            var appendItem = this.tagModelFunc(this.data[i]);
            tagListContaine.prepend(appendItem);
        }
        this.initTagEvent(inputId);
    }
    // 注册删除和编辑事件
    obj.initTagEvent = function (inputId) {
        $("#"+inputId+" .tagList .tagItem .delete").off();
        $("#"+inputId+" .tagList .tagItem").off();
        var that = this;
        var ds =  $("#"+inputId+"").attr("ds");
        if(ds=="0"){
            return;
        }
        $("#"+inputId+" .tagList .tagItem .delete").mousedown(function(){
            if(that.deleteFunc($(this).parent().attr("data-con-id"))){
                $(this).parent().remove();
            };
        });

        $("#"+inputId+" .tagList .tagItem").find("span").click(function(){
            that.editFunc($(this).parent().attr("data-con-id"));
        });
    };
    obj.deleteFunc = function(dataId){};
    obj.editFunc = function(dataId){};
    obj.tagModelFunc = function(data){};

    return obj;
}


// 验证中的正则
// 服务名称验证
var matchServer = new RegExp("^[a-z0-9]([-a-z0-9]*[a-z0-9])?$");
// 环境变量验证
var matchEnv = new RegExp("^[A-Za-z_][A-Za-z0-9_]*$");
// 镜像名称验证
var matchImage = new RegExp("^(([-a-z0-9_/]*[a-z0-9])?)[:][A-Za-z0-9]?([-._A-Za-z0-9]*[A-Za-z0-9_])?$");
// 浮点数字验证
var matchFloat  = /^\d+\.?\d*$/;
// 正数验证
var matchNumber  = /^\d+$/;
// 验证
var matchIP  =/^((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
// 验证
var matchHostLabel = new RegExp("^([A-Za-z0-9][-A-Za-z0-9_.]*)?([A-Za-z0-9])?$");
// 验证ip:port,ip:port
var matchIpArray = /^(((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d{3,6},)*(((\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.){3}(\d{1,2}|1\d\d|2[0-4]\d|25[0-5]):\d{3,6})$/;
// 验证半角英数字，符号包括-_
var matchHostName = new RegExp("^([A-Za-z0-9][-A-Za-z0-9_]*)?([A-Za-z0-9])?$");

/*
 // 标签 动态添加删除
 function showTags(inputId, disable) {
 var obj = new Object();
 if (inputId == null || inputId == "") {
 return;
 }
 obj.inputId = inputId;
 obj.isDisable = disable;
 //初始化界面
 obj.initView = function (data) {
 var inputId = this.inputId;
 // -------------显示内容是否允许编辑 Start-----------
 if (!this.isDisable) {
 // 可以编辑
 $("#" + inputId).attr("ds", "1");
 } else {
 $("#" + inputId).attr("ds", "0");
 }
 // -------------显示内容是否允许编辑 End-----------
 if (data != null && data != "") {
 this.setInputValue(inputId, data);
 }
 }

 obj.setInputValue = function (result) {
 var tagListContaine = $("#" + this.inputId + " .tagList");
 for (var i = 0; i < result.length; i++) {
 var appendListItem = tagTake.tagModelFunc(result[i]);
 tagListContaine.append(appendListItem);
 }
 tagTake.initTagEvent(this.inputId);
 }
 obj.initTagEvent = function (inputId) {
 $("#" + inputId + " .tagList .tagItem .delete").off();
 $("#" + inputId + " .tagList .tagItem").off();
 var ds = $("#" + inputId).attr("ds");
 if (ds == "0") {
 return;
 }
 $("#" + inputId + " .tagList .tagItem .delete").mousedown(function () {
 var dataId = $(this).parent().attr("data-con-id");
 tagTake.tagDeleteFunc(dataId);
 $(this).parent().remove();
 // 删除按钮事件
 });

 $("#" + inputId + " .tagList .tagItem").dblclick(function () {
 var dataId = $(this).parent().attr("data-con-id");
 tagTake.tagEditFunc(dataId);
 // 标签双击事件,需要编辑处理时使用
 });
 }
 obj.tagDeleteFunc = function (inputId) {
 }
 obj.tagEditFunc = function (inputId) {
 }
 obj.tagModelFunc = function (dataValue) {
 //return '<div class="tagItem" data-con-id="'+1+'"><span>' + dataValue + '</span><div class="delete"></div></div>';
 }
 return obj;
 }

 var tagTake = {}*/




