/**
 * Created by luya on 2017/10/19.
 */
//分页参数设置 这些全局变量关系到分页的功能
var startPage = 0;
var pageSize = 10;
var currentPage = 1;
//ajax请求后台数据
function dataShowPage(dom, url, type, param, callback, size, start) {
    // 允许自定义每页显示件数
    pageSize = size || pageSize;
    // 查询时参数指定startPage为0，然后清除防止翻页是干扰
    if(start != undefined){
        currentPage = start;
        startPage = 0;
    }
    // 后台查询参数合并
    var params = {"currentPage": startPage * pageSize, "pageSize": pageSize};
    params = $.extend(params, param);
    $.ajax({
        type: type,
        async: false,
        url: url,
        data: params,
        success: function (result, status) {
            if (result["status"] == 0) {

                //权限控制按钮
                if(result["author"]==0){
                    $("#resBtn").show();
                }else{
                    $("#resBtn").hide();
                }

                layui.use(["laypage","layer"], function () {
                    var laypage = layui.laypage;
                    //调用分页
                    laypage.render({
                        elem: dom,
                        count: result["count"] || 0, //数据总数
                        curr: currentPage || 0,
                        limit: pageSize,
                        startPage: startPage,
                        theme: "#367fa9",
                        groups: 5, // 最大页码数
                        skip: true, // 允许页码跳转
                        jump: function (obj, first) {
                            currentPage = (obj.curr);
                            startPage = (obj.curr - 1);
                            if (!first) { // 加此判断，否则初始时会无限刷新
                                dataShowPage(dom, url, type, param, callback);
                            }
                        }
                    });
                });
            }
            callback(result);
            // 无数据提示
            $("#_pageData").remove();
            if(result.count == 0){
                var html = "<div id='_pageData' style='text-align: center;'><span>无符合条件数据</span></div>";
                $(dom).after(html);
                $(dom).hide();
                $("#_pageData").show();
            }else {
                $(dom).show();
            }
        },
        error: function () {
            alert("发生请求错误！");
        }
    });
}