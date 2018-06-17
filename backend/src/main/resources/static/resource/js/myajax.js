/**
 * Created by luya on 2018/6/16.
 */

var myAjax = function (type, async, url,param, succCallBack) {
    $.ajax({
        type: type,
        async: async,
        url: url,
        dataType: "json",
        data: param,
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", localStorage.getItem('JWT_TOKEN'));
        },
        success: function (result, status) {
            if (result.code == 701) {
                location.href = "/";
            }
            succCallBack(result, status);
        }
    });
}
