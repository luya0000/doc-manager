$(function () {
    $(document).ajaxStart(function () {
        Pace.restart();
    });
    initUserMenu();
    initUserInfo();
})

function initUserMenu() {
    //先将菜单清理掉
    $('.sidebar-menu > li:eq(0)').nextAll().remove();
    myAjax("get", false, "/sys/menu/list", null, showMenus);
}

function showMenus(result, status) {
    if (result.code === 200) {
        setMenu($('.sidebar-menu'), result.content);
        $('.smenuitem').bind('click', function () {
            if ($(this).attr("data") != '') {
                $(".content").empty();//清除原有内容，避免页面内容冲突
                $(".content").load($(this).attr("data"));
            }
        });
        //首页菜单单独处理点击事件
        $('.home-menu').bind('click', function () {
            if ($(this).attr("data") != '') {
                //$('#title').empty();
                $('.treeview').removeClass("active");
                $(this).parent().addClass("active");
                $(".content").empty();//清除原有内容，避免页面内容冲突
                $(".content").load($(this).attr("data"));
            }
        });
        $("#dashboard").trigger("click");
    }
}

function setMenu(menuid, menus) {
    var menuhtm = [];
    menuhtm.push("<li id='menu' class='treeview active'>");
    menuhtm.push("<a href='#/content' class='home-menu'> 首页</a>");
    menuhtm.push("</li>");

    $.each(menus, function (n, menudata) {
        menuhtm.push("<li id=\"menu");
        menuhtm.push(menudata.menuId);
        menuhtm.push("\" class=\"treeview\">");
        //首页菜单 单独处理
        menuhtm.push("<a href=\#>");
        menuhtm.push(menudata.menuName);
        if (menudata.subMenus.length > 0) {
            menuhtm.push("</span><i class=\"fa fa-angle-left pull-right\"></i></a>")
            menuhtm.push(setSecondLevelMenu(menudata.subMenus));
        } else {
            menuhtm.push("</a>")
        }
        menuhtm.push("</li>");
    })
    menuid.append('' + menuhtm.join('') + '');
}

function setSecondLevelMenu(menus) {
    var menuhtm = [];
    menuhtm.push("<ul class=\"treeview-menu\">");
    $.each(menus, function (n, menudata) {
        menuhtm.push("<li><a class=\"smenuitem\" href='"+menudata.menuUrl+"'>" + menudata.menuName);
        menuhtm.push("</a>")
        menuhtm.push("</li>");
    })
    menuhtm.push("</ul>");
    return '' + menuhtm.join('') + '';
}

/*function getMenuIcon(menutype) {
    var iconClass;
    switch (menutype) {
        case "0" :
            iconClass = "fa-home";
            break;//首页
        case "1" :
            iconClass = "fa-th";
            break;//应用管理
        case "1-1" :
            iconClass = "fa-file-image-o";
            break;//私库镜像
        case "1-2" :
            iconClass = "fa-file";
            break;//我的应用
        case "1-3" :
            iconClass = "fa-cogs";
            break;//公共服务
        case "2" :
            iconClass = "fa-cloud";
            break;//资源管理
        case "2-1" :
            iconClass = "fa-cubes";
            break;//资源分区
        case "2-2" :
            iconClass = "fa-codepen";
            break;//主机池
        case "2-3" :
            iconClass = "fa-th";
            break; // 集群安装
        case "3" :
            iconClass = "fa-fa-eye";
            break; //host group
        case "3-1" :
            iconClass = "fa-circle";
            break;//host node
        case "3-2" :
            iconClass = "fa-circle";
            break;//host node
        case "4" :
            iconClass = "fa-gear";
            break; //host group
        case "4-1" :
            iconClass = "fa-user-md";
            break;//租户管理
        case "4-2" :
            iconClass = "fa-key";
            break;//host node
        case "4-3" :
            iconClass = "fa-bars";
            break;//日志
        case "5" :
            iconClass = "fa-plug";
            break; //第三方接入
        case "5-1" :
            iconClass = "fa-exchange";
            break;//日志接入
    }
    return iconClass;
}*/

// 获取用户信息
function initUserInfo() {
    myAjax("get", false, "/loginUser", null, showUserName);
}
// 显示用户信息
function showUserName(result, status) {
    if (result.code === 200) {
        $('.username').html(result.content.userName);
    }
}
