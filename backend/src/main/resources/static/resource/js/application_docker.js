/**
 * Created by luya on 2017/10/16.
 * docker镜像管理页面脚本
 *
 */
$(document).ready(function () {
    dockerImageAll();

    // 显示镜像详细信息
    $('#dockerTable').delegate('.showImage', "click", function () {
        var dockerId = $(this).attr("data-con-id");
        showImageInfo(dockerId)
    });
    // 镜像选择，存在不选时取消全选选中状态
    $('#dockerTable').delegate('[name = check]', "click", function () {
        $("[name = check]:checkbox").each(function () {
            if ($(this).is(':checked')) {
                $("#selectAll").prop("checked", false);
            }
        });
    });
    // 镜像不可删除标记右键弹出层
    $('#dockerTable').delegate('.rightClick', "contextmenu", function (event) {
        //以行内元素方式显示 contextmenu 层
        var imageId = $(this).children('td').eq(0).children().val();
        var checkId = $(this).children('td').find("[name=delCheck]").val();
        if (checkId == 0) {
            $("#baseImage").hide();
            $("#notBaseImage").show();
        } else {
            $("#baseImage").show();
            $("#notBaseImage").hide();
        }
        $("#delete_check_image_id").val(imageId);
        var y = event.pageY + 10;
        var x = event.pageX + 10;
        $("#contextmenu").css("display", "block");
        $("#contextmenu").offset({top: y, left: x});
        //返回 false，以屏蔽真正的右键菜单的显示
        return false;
    });
    $("#contextmenu").mouseleave(function () {
        if ($("#contextmenu").css("display") == "block") {
            $("#contextmenu").css("display", "none");
        }
    });
});

// 关闭右键基础镜像层
$(document).click(function (e) {
    if ($("#contextmenu").css("display") == "block") {
        $("#contextmenu").css("display", "none");
    }
});

// 同步镜像库
$("#refushDocker").click(function () {
    $.ajax({
        url: "/dockerimg/synch",
        data: {},
        type: "GET",
        dataType: "json",
        success: function (result) {
            if (result["status"] == 0) {
                showResult(result, "镜像", "", "同步完成");
                dockerImageAll();
            } else {
                showResult(result)
            }
        }
    });
});

// 删除镜像
$("#deleDocker").click(function () {
    var index = layer.confirm('删除后无法恢复，确定删除？', {
        btn: ['确定', '取消'], //按钮
        shade: [0.3, '#000'],
        shadeClose: true
    }, function () {
        $.ajax({
            url: "/dockerimg/delete",
            type: "POST",
            data: {"dockerId": $("#infoImageId").val()},
            dataType: "json",
            success: function (result) {
                if (result["status"] === 0) {
                    layer.msg(result["message"], {
                        anim: 2,
                        skin: 'layui-layer-rim'
                    });
                    dockerImageAll();
                } else {
                    layer.alert('以下镜像删除失败：' + result["message"], {
                        closeBtn: 0
                    });
                }
            }
        });
        layer.close(index);
        $("#myModal").modal('hide');
    });
});

// 数据库查询
function dockerImageAll() {
    var ImageName = $("#searchDockerName").val();
    var param = {key: ImageName};
    dataShowPage($("#page"), "/dockerimg/list", "GET", param, showImageData);
}

// 镜像全选反选处理
function checkSelectAll() {
    if ($("#selectAll").is(':checked')) {
        $("[name = check]:checkbox").prop("checked", true);
    } else {
        $("[name = check]:checkbox").prop("checked", false);
    }
}

// 修改基础镜像标记。
function changeDelcheck(checked) {
    var dockerId = $("#delete_check_image_id").val();
    $.ajax({
        url: "/dockerimg/update/delcheck/" + dockerId,
        data: {
            checked: checked
        },
        type: "PUT",
        dataType: "json",
        success: function (result) {
            if (result["status"] != 0) {
                layer.alert(result["message"]);
                return false;
            }
            dockerImageAll();
        },
        error: function (xhr, status, statusText) {
        }
    });
    $("#contextmenu").hide();
}

// 定义显示内容
function showImageData(result) {
    // 清空全选框
    $("#selectAll").prop("checked", false);
    if (result.status == 0) {
        var tablelist = "";
        $.each(result.data, function (i, item) {
            tablelist += [
                '<tr class="rightClick">',
                '<td><input type="checkbox" value="' + item.id + '" name="check"></td>',
                '<td data-con-id="' + item.id + '" class="showImage"><a href="#">' + item.title + '</a></td>',
                '<td>' + item.imageName + '</td>',
                '<td>' + item.version + '</td>',
                item.versionType == 0 ? '<td>开发版</td>' : '<td>生产版</td>',
                item.publicImage == 1 ? '<td>私有镜像</td>' : '<td>公共镜像</td>',
                '<td>' + item.category + '</td>',
                '<td>' + dateFormat(item.lastUpdated) + '</td>',
                '<td><input type="hidden" value="' + item.delCheck + '" name="delCheck" />',
                item.delCheck == 1 ? '基础镜像' : '非基础镜像' + '</td>',
                '</tr>'
            ].join('');
        });
        $('#dockerTable').find('td').remove();
        $('#dockerTable').append(tablelist);
    } else if (result.data.status == 1) {
        alert(result.data.message);
    }
}

// 详细信息显示
function showImageInfo(dockerId) {
    $.ajax({
        url: "/dockerimg/search/" + dockerId,
        type: "GET",
        dataType: "json",
        success: function (result) {
            var data = result.data;
            $("#infoImageId").val(dockerId);
            $.each(data, function (a, b) {
                $("#imageName").html(data.imageName);
                $("#version").html(data.version);
                $("#publicImage").html(data.publicImage == 0 ? "私有镜像" : "公共镜像");
                $("#status").html(data.status == 0 ? "未使用" : "已使用");
                $("#versionType").html(data.versionType == 0 ? "开发版" : "生产版");
                $("#registryId").html(data.registryId);
                $("#clusterId").html(data.clusterId);
                $("#category").html(data.category);
                //	$("#size").html(Math.round(data.size/1024)+"M");
                if (a == "lastUpdated") {
                    $("#lastUpdated").html(dateFormat(data.lastUpdated))
                }
            });
        }
    });

    $("#myModal").modal('show');
}

// 镜像库设置页面
$("#addImage_registry_url").click(function () {
    $(".content").load("add_registry_url.html");
});

// 上传镜像
$("#addDocker").click(function () {
    addImage();
    $("#addDockerData").find('name').empty();
    $("#addimagelabel").html("导入镜像");

    $('#addDockerData').find('[type="text"]').val("");
    $('#addDockerData').find('textarea').val("");

    $("#modifydockersave").attr("id", "adddockersave");

    $("#addDockerSave").attr("modifybtn", "adddockersave");
    $("#adddockerName").closest('.form-group').removeClass('has-error');
    $("#addversion").closest('.form-group').removeClass('has-error');
    $('#addDockerData').find('.formtips').remove().end();
});

// 导入验证
$('form :input').blur(function () {
    var $parent = $(this).parent();
    $parent.find(".formtips").remove();
    $(this).closest('.form-group').removeClass('has-error');

    //验证imageName
    if ($(this).is('#adddockerName')) {
        if (this.value == "") {
            $(this).closest('.form-group').addClass('has-error');
            $(this).parent().append('<span class="formtips" style="color:#dd4b39">不能为空!</span>');
        }
    }

    //验证version
    if ($(this).is('#addversion')) {
        if (this.value == "") {
            $(this).closest('.form-group').addClass('has-error');
            $(this).parent().append('<span class="formtips" style="color:#dd4b39">不能为空!</span>');
        }
    }
}).keyup(function () {
    $(this).triggerHandler("blur");
}).focus(function () {
    $(this).triggerHandler("blur");
});

// 导入镜像
$("#addDockerSave").click(function () {

    $("#addDockerSave").attr("modifybtn");

    $("form :input.images-control").trigger('blur');
    var numError = $('form .onError').length;
    if (numError) {
        return false;
    } else {
        //镜像上传
        $.ajax({
            url: "/dockerimg/create/1",
            type: "POST",
            data: $('#addDockerData').serialize(),
            dataType: "json",
            success: function (result) {
                if (result.status == 0) {
                    $("#myModal2").modal('hide');
                    setTimeout(jmpDockerInstall, 500);
                } else if (result.status == 2 && result.message == "EXIST") {
                    show_confirm();
                } else if (result.status == 1) {
                    alert(result.message);
                }
            },
            error: function (data) {
                alert(data.status + ":" + data.message);
            }
        });
    }
});

// 镜像重复上传询问
function show_confirm() {
    var r = confirm("镜像已经存在，是否覆盖更新");
    if (r == true) {
        $.ajax({
            url: "/dockerimg/importimg",
            type: "GET",
            dataType: "json",
            success: function (result) {
                if (result.status == 0) {
                    $("#myModal2").modal('hide');
                    setTimeout(jmpDockerInstall, 500);
                } else if (result.status == 1) {
                    alert(result.message);
                }
            }
        });
    }
    else {

    }
}

function jmpDockerInstall() {
    $(".content").load("Ku8_docker_pkg_import_guide_3.html");
}
// 文件管理页面跳转
$("#file_manage").click(function () {
    $(".content").load("file_manage.html");
});
/**
 * 指定文件夹下镜像文件列表
 */
function addImage() {
    $.ajax({
        url: "/extresources/listuploadedimages",
        type: "GET",
        dataType: "json",
        success: function (data) {
            var pathurl = "";
            $.each(data, function (i, item) {
                pathurl += "<option value=" + i + ',' + item + ">" + i + "</option>"
            });
            $("#addPathUrl").html(pathurl);
        }
    });
}
$("#Image_package_import").click(function () {
    $(".content").load("Ku8_docker_pkg_import_guide_3.html");
});

// 删除镜像库内的镜像
function deleteImage() {

    var dockerIds = "";
    $('input[name="check"]:checked').each(function () {
        dockerIds += $(this).val() + ",";
    });
    if (dockerIds.split(',').length <= 1) {
        layer.msg("请先选择镜像！", {
            anim: 2,
            skin: 'layui-layer-rim',
            shadeClose: false
        });
        return false;
    }
    var index = layer.confirm('删除后无法恢复，确定删除？', {
        btn: ['确定', '取消'], //按钮
        shade: [0.3, '#000'],
        shadeClose: true
    }, function () {
        $.ajax({
            url: "/dockerimg/delete/dockers",
            type: "POST",
            data: {dockerIds: dockerIds},
            dataType: "json",
            async: false,
            success: function (data) {
                if (data.status === 0) {
                    layer.msg(data.message, {
                        anim: 2,
                        skin: 'layui-layer-rim'
                    });
                } else {
                    layer.alert('以下镜像删除失败,请确认没有被应用使用：' + data.message, {
                        closeBtn: 0
                    });
                }
                dockerImageAll();
            }
        });
        layer.close(index);
    });

}

// 上传镜像
function uploadimages() {
    var $ = jQuery,
        $list = $('#thelist'),
        state = 'pending',
        uploader;
    uploader = WebUploader.create({
        // 选完文件后，自动上传。
        auto: true,
        //去重
        duplicate: true,
        // 不压缩image
        resize: true,
        // swf文件路径
        swf: '../js/Uploader.swf',
        // 文件接收服务端。
        server: '/dockerimg/upload-image',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: {
            "id": '#pickerImage',
            "multiple": false   //禁止多选。
        },
        sendAsBinary: true,
        method: 'POST',
        accept: {
            extensions: 'tar,tar.gz'
        }

    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        //$('#myModalMask').modal({backdrop: 'static', keyboard: false});
        $list.append('<div id="' + file.id + '" class="item">' +
            '<h4 class="info">' + file.name + '</h4>' +
            '<p class="state">等待上传...</p>' +
            '</div>');
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on('uploadProgress', function (file, percentage) {
        var $li = $('#' + file.id),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if (!$percent.length) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                '</div>' +
                '</div>').appendTo($li).find('.progress-bar');
        }
        $li.find('p.state').text('上传中');
        $percent.css('width', percentage * 100 + '%');
    });
    /*上传成功*/
    uploader.on('uploadSuccess', function (file) {
        $('#' + file.id).find('p.state').text('已上传');
        $.ajax({
            url: "/extresources/listuploadedimages",
            type: "GET",
            dataType: "json",
            success: function (data) {
                var pathurl = "";
                $.each(data, function (i, item) {
                    pathurl += "<option value=" + i + ',' + item + ">" + i + "</option>"
                });
                $("#addPathUrl").html(pathurl);
            }
        });
    });
    /*上传出错*/
    uploader.on('uploadError', function (file) {
        $('#' + file.id).find('p.state').text('上传出错');
    });

    uploader.on('uploadComplete', function (file) {
        //	$("#myModalMask").modal('hide');
        $('#' + file.id).find('.progress').fadeOut();
    });

}
uploadimages();
