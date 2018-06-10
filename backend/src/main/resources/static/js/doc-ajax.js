/**
 * Created by luya on 2018/6/10.
 */

var jwtToken;

var ajaxpost = function () {
    $.post("/login", {account: $('#account').val(), password: $('#password').val()},
        function (user) {
            console.log(user);
            if (user == null || user === '') {
                setErrorText("password", "用户名或密码错误");
            }
            /*else{
             location.href = "/index";
             }*/
            $("#jwtcontent").text(user.content);
        });
}