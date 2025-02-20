<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <script type="text/javascript" src="lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="css/H-ui.min.css" rel="stylesheet" type="text/css"/>
    <link href="css/H-ui.admin.css" rel="stylesheet"
          type="text/css"/>
    <link href="lib/icheck/icheck.css" rel="stylesheet"
          type="text/css"/>
    <link href="lib/Hui-iconfont/1.0.1/iconfont.css"
          rel="stylesheet" type="text/css"/>
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <script src="js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="lib/My97DatePicker/WdatePicker.js"></script>

    <link rel="stylesheet" href="kindeditor/themes/default/default.css"/>
    <script charset="utf-8" src="kindeditor/kindeditor-min.js"></script>
    <script charset="utf-8" src="kindeditor/lang/zh_CN.js"></script>
    <script>
        var editor;
        KindEditor.ready(function (K) {
            editor = K.create('textarea[name="content"]', {
                resizeType: 1,
                allowPreviewEmoticons: false,
                allowImageUpload: true,
                afterBlur: function () {
                    this.sync();
                },
                items: [
                    'source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                    'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                    'insertunorderedlist', '|', 'emoticons', 'image', 'multiimage', 'link', 'fullscreen']
            });
        });
    </script>
    <title>基本设置</title>

</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
    用户兑换券查看 <span class="c-gray en">&gt;</span>添加兑换券 <a
        class="btn btn-success radius r mr-20"
        style="line-height: 1.6em; margin-top: 3px"
        href="javascript:location.replace(location.href);" title="刷新"><i
        class="Hui-iconfont">&#xe68f;</i></a>
</nav>
<br>
<div class="pd-20" style="width: 80%;">

    <div class="row cl">
        <label class="form-label col-2" style="text-align: right;font-size:18px;"><strong>兑换券信息</strong></label>
    </div>
    <br>

    <div class="row cl">
        <label class="form-label col-2" style="text-align: right">兑换模板：</label>
        <div class="formControls col-10">
            <span style="font-size:20px;color:red;vertical-align:middle">*</span>
            <select id="template_id" class="input-text" style="width: 80%" onclick="templateselect()">
                <option value="">请选择兑换模板</option>
                <c:forEach items="${templates}" var="template">
                    <option value="${template.template_id}">${template.templateName }</option>
                </c:forEach>
            </select>
            <span  id='template_namespan' style="font-size:14px;color:red;vertical-align:middle"></span>
        </div>
    </div>
    <br>

    <div class="row cl">
        <label class="form-label col-2" style="text-align: right">数量：</label>
        <div class="formControls col-10">
            <span style="font-size:20px;color:red;vertical-align:middle">*</span>
            <input type="text" id="num"
                   placeholder="请填写数量" value="" class="input-text" style="width: 80%" onchange="templatenumchange()">
            <span  id='template_numspan' style="font-size:14px;color:red;vertical-align:middle"></span>
        </div>
    </div>
    <br>


    <div class="row cl">
        <div class="formControls col-4" style="text-align: right">
            <button onClick="modelAdd()" id="butt"
                    class="btn btn-primary radius" type="button">
                <i class="Hui-iconfont">&#xe632;</i> 添加
            </button>
        </div>
        <div class="formControls col-4" style="text-align: center">
            <button onClick="history.go(-1);" class="btn btn-default radius"
                    type="button">&nbsp;&nbsp;返回&nbsp;&nbsp;
            </button>
        </div>
    </div>

</div>

<br><br>
<script type="text/javascript">

    //模板点击触发事件
    function templateselect() {
        $('#num').val("");
        $('#template_namespan').text("");
    }

    function  templatenumchange(){
        var number = $('#num').val();
        var regu = /^[0-9]\d*$/;
        if (number != "") {
            if (regu.test(number.trim())) {
                $('#template_numspan').text("");
            }else{
                $('#template_numspan').text("输入格式有误！");
                return;
            }
        }else{
            $('#template_numspan').text("必填");
            return;
        }
    }
    //提交模板
    function modelAdd() {
        var templateName = $('#template_id option:checked').text();
        var num = $('#num').val();
        var template_id = $('#template_id').val();
        var template_numspan=$('#template_numspan').text();
        var template_namespan=$('#template_namespan').text();

        if(templateName== "请选择兑换模板" || templateName == null || templateName == ""){
            $('#template_namespan').text("必填");
        }

        if(num== "undefined" || num == null || num == ""){
            $('#template_numspan').text("必填");
        }

        if(templateName!= "请选择兑换模板" &&templateName != null && templateName != ""
            &&num!= "undefined" && num != null && num != ""&&template_numspan==""&&template_namespan==""){
            spanclear()
            $.ajax({
                url: 'templategiftInsert.html',
                type: 'post',
                data: 'template_id=' + template_id + '&num='
                + num+"&templateName="+templateName,
                success: function (rs) {
                    if (rs == 1) {

                        alert("添加成功！");
                        window.location.href = document.referrer;
                    } else {

                        alert("添加失败！");
                    }
                }
            })
        }
    }


    //取消必填
    function spanclear(){
        $('#template_numspan').text("");
        $('#template_namespan').text("");
    }

</script>

</body>
</html>