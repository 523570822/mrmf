<%@ page language="java" pageEncoding="utf-8" %>
<%@ include file="/moduleweb/resources/common.jsp" %>
<html>
<head>
    <title></title>
    <style>
        .inputwidth {
            width: 44%;
        }
        .comment
        {
            width:100%;
            height:100%;
        }
    </style>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5><a href="javascript:history.go(-1)">《返回</a> 店铺工位配置</h5>
                </div>
                <div class="ibox-content">
                    <c:if test="${returnStatus.status == 0}">
                        <div class="alert alert-danger">
                            <i class="fa fa-exclamation-triangle"></i>${returnStatus.message}</div>
                    </c:if>

                    <form id="config" method="post" class="form-horizontal">
                        <input type="hidden" value="${organId}" name="organId" id="organId">
                        <c:if test="${opc._id!=null}">
                            <input type="hidden" value="${opc._id}" name="_id" id="_id">
                        </c:if>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">工位类型</label>
                            <div class="col-sm-6">
                                分账模式<input type="radio"  id="account" name="leaseType" value="1" checked="checked"
                                           <c:if test="${opc.leaseType==1}">checked</c:if>/>
                                租金模式<input type="radio"  id="positionType" name="leaseType" value="0"
                                           <c:if test="${opc.leaseType==0}">checked</c:if>/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">工位数量</label>
                            <div class="col-sm-6">
                                <input class="form-control" type="number" min="0" max="100" required value="${opc.num}"
                                       id="num" name="num"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">租金/天</label>

                                <div class="col-sm-6" id="rent">
                                    <input class="form-control" type="number" min="0" max="100"  required
                                           value="${opc.leaseMoney}" id="leaseMoney" name="leaseMoney"/>
                                </div>


                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">工位展示</label>
                            <div class="col-sm-6">
                                <div id="attachesDiv" class="clear">
                                    <c:forEach items="${opc.images}" var="image" varStatus="stateIndex">
                                       <span id="${stateIndex.index}" style="float:left;">
                                           <img src="${ossImageHost}${image}" style="margin:10px;display: block"
                                                width="100" d height="100"
                                                onclick="delImage(${stateIndex.index})">
                                           <input type="button" class="btn btn-danger" style="margin-left: 34px"
                                                  value="删除" onclick="deleteImage(${stateIndex.index})"/>
                                           <input type="hidden" id="images[${stateIndex.index}]"
                                                  name="images[${stateIndex.index}]" value="${image}" class="attach"/>
                                       </span>
                                    </c:forEach>
                                </div>
                                <div class="f-left" style="width: 120px; padding-top: 10px">
                                    <input type="button" class="btn btn-primary btn-block"
                                           onclick="selectFile()" accept="image/*"
                                           style="width: 100%; height: 34px; padding: 0" value="选择上传展示图"/>
                                </div>
                            </div>

                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">设施描述</label>
                            <div class="col-sm-6">
                                <textarea name="describe" class="comment"
                                          style="margin: 0px; resize:none; overflow-y:auto;" >${opc.describe}</textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">区间表</label>
                            <div class="col-sm-6">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th></th>
                                        <th>区间</th>
                                        <th>店面</th>
                                        <th>技师</th>
                                        <th>平台</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tab">
                                    <c:forEach items="${opc.positionSettingsList}" var="op" varStatus="objIndex">
                                        <tr>
                                            <td width="50px"><a>删 除</a></td>
                                            <td width="50%">
                                                <input class="form-control" type="number"
                                                       name="positionSettingsList[${objIndex.index}].qu1"
                                                       value="${op.qu1}" style="width: 44%; display: inline-block"/>-
                                                <input class="form-control" type="number"
                                                       name="positionSettingsList[${objIndex.index}].qu2"
                                                       value="${op.qu2}" style="width: 44%; display: inline-block"/>
                                            </td>
                                            <td><input class="form-control"
                                                       name="positionSettingsList[${objIndex.index}].organAmount"
                                                       value="${op.organAmount}"></td>
                                            <td><input class="form-control"
                                                       name="positionSettingsList[${objIndex.index}].staffAmount"
                                                       value="${op.staffAmount}"></td>
                                            <td><input class="form-control"
                                                       name="positionSettingsList[${objIndex.index}].adminAmount"
                                                       value="${op.adminAmount}"></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-9">
                                <button class="btn btn-primary" type="button" id="add">添加</button>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <button class="btn btn-primary" type="button" id="sub">提交</button>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>
<div class="hidden">
    <form action="" id="fileForm">
        <input type="file" rel="msgImage" autocomplete="off" multiple="multiple" name="onlyFile"
               id="onlyFile" onchange="changeFile(this)" placeholder="File here"/>
    </form>
</div>
<script>
    $(document).ready(function () {
        var sss=$('input:radio[name="leaseType"]:checked').val();
        if(0 == sss){
            $("#rent input").removeAttr("readonly");
        }else{
            $("#leaseMoney").val(0);
            $("#rent input").attr({"readonly":true});
        }

    });

    $("input:radio[name=leaseType]").change(function () {
        $("#leaseMoney").val(0);
        var val=$('input:radio[name="leaseType"]:checked').val();
        if(val==0){
            $("#rent input").removeAttr("readonly");
        }else if(val==1){
            $("#rent input").attr({"readonly":true});
        }
    });


    function deleteImage(id) {
        $("#" + id + "").remove();
    }
    //动态添加
    var num = 0;
    var flag = 0;
    if (flag == 0) {
        if ($("#_id").val() != null) {
            var len = ${opc.positionSettingsList.size()}
                num = (len > 0) ? len : 0;
        }
        flag == 1;
    }
    $("#add").click(function () {//添加按钮事件
        var tr = "<tr><td width='50px'><a onclick='del(this)'>删   除</a></td><td width='50%'><input class='form-control' style='width: 44%; display: inline-block' type='number' name='positionSettingsList[" + num + "].qu1'/>-" + "<input class='form-control' style='width: 44%; display: inline-block' type='number' name='positionSettingsList[" + num + "].qu2'/></td><td>"
            + "<input class='form-control' name='positionSettingsList[" + num + "].organAmount' ></td><td><input class='form-control' name='positionSettingsList[" + num + "].staffAmount'></td><td><input class='form-control' name='positionSettingsList[" + num + "].adminAmount' ></td></tr>";
        $("#tab").append(tr);
        num += 1;
    });

    $("input:radio[name=favourableType]").change(function () {
        var val = $('input:radio[name="favourableType"]:checked').val();
        if (val == 0) {
            $("#xuan").html("金额")
        } else if (val == 1) {
            $("#xuan").html("比例")
        }
    });
    function del(row) {//删除事件
        $(row).parent().parent().remove();
    }
    $("#tab tr td a").click(function () {//删除事件
        $(this).parent().parent().remove();
        return false;
    });
    //提交表单的方法
    $("#sub").click(function () {
        var obj = $("#config").serialize();
        $.get("${ctxPath}/OrganPosition/doAdd.do", obj, function (date) {
            if (date) {
                window.location.href = "${ctxPath}/OrganPosition/toQuery.do";
            } else {
                alert("操作失败")
            }
        })
    })
    var it;
    var fs = [];
    function selectFile() { // 选择轮播图
        it = "img";
        if (fs && fs.length >= 3) {
            toastr.warning("轮播图最多三张！");
        }
        $('#onlyFile').click();
    }
    function changeFile(_this) {
        $.shade.show();
        var filePaths = $("#onlyFile")[0].files;//或者这样写 document.getElementById("id").files;
        var array = [];
        for (var i = 0; i < filePaths.length; i++) {
            array[i] = filePaths[i];
        }
        $("#fileForm").ajaxSubmit({
            type: 'post',
            headers: {
                'type': $(_this).attr("rel"),
                'isPublic': 'true'
            },
            url: _ctxPath + '/w/file/upload.do',
            success: function (result) {
                $.shade.hide();
                if (it == "img") {
                    fs = [];
                    for (var i = 0; i < result.data.length; i++) {
                        var fileId = result.data[i];
                        fs.push(fileId);
                    }
                    showAttach();
                    $("#onlyFile2").val("");
                }
                $("#onlyFile").val("");
                toastr.success("图片上传成功");

            },
            error: function (XmlHttpRequest, textStatus, errorThrown) {
                $.shade.hide();
                toastr.success("图片上传失败");
            }
        });
    }
    function showAttach() {
        //$(".attach").remove();
        var num = $("#attachesDiv").children('span').length;
        for (var i = 0; i < fs.length; i++) {//获得已有图片的位置的i 终点fs+i

            num += i;
            //var s = fs[i].split('|');
            var url = _ossImageHost + fs[i] + "@!style400";
            //fileName = s[1];
            var a = "<span id= \"" + num + "\" style=\" float: left\" > ";
            var b = "<img src=\"" + url + "\" class=\"attach\" style=\"margin:10px;display: block\" width=\"100\" height=\"100\" onclick=\"delImage(" + num + ")\"/>";
            var c = "<input type=\"button\" class=\"btn btn-danger\" style=\"margin-left: 34px\" value=\" 删除 \" onclick=\"deleteImage(" + num + ")\"/>";
            var d = "<input type=\"hidden\" id=\"images["
                + num
                + "]\" name=\"images["
                + num + "]\" value=\"" + fs[i]
                + "\" class=\"attach\"> ";
            var e = a + b + c + d;
            $("#attachesDiv").append(e);
        }
    }
    function getFileName(obj) {
        var val = obj.value;
        var idx = val.lastIndexOf("/");
        if (idx == -1)
            idx = val.lastIndexOf("\\");
        return val.substring(idx + 1);
    }

</script>
</body>
</html>
