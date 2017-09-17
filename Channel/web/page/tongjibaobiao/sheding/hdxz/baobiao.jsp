<%@page import="com.channel.model.user.CXtUser" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CXtUser user = ((CXtUser) session.getAttribute("user"));
    String username = null;
    int userid = -1;
    if (user != null) {
        username = user.getUsername();
        userid = user.getId();
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>航道里程现状等级汇总表</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" type="text/css"
          href="common/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css"
          href="common/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="page/common/css/common.css">
    <link rel="stylesheet"
          href="common/bootstrap-multiselect/css/bootstrap-multiselect.css">

    <script type="text/javascript" language="javascript"
            src="common/common/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/ajaxfileupload.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" language="javascript"
            src="common/common/js/common.js"></script>
    <script type="text/javascript" language="javascript"
            src="page/common/js/constants.js"></script>
    <script type="text/javascript" src="common/wdatepicker/WdatePicker.js"></script>
</head>

<body style="background: white;overflow: hidden;">
<input type="hidden" id="username" value="<%=username%>">
<input type="hidden" id="userid" value="<%=userid%>">
<input type="hidden" id="basePath" value="<%=basePath%>">

<div class="container-fluid" style="font-family: 宋体;">
    <c:choose>
    <c:when test="${lc == null}">

        <div class="col-xs-12">
            <center>
                <h3>暂无报表数据</h3>
            </center>
        </div>
    </c:when>
    <c:otherwise>
    <div class="row">
        <div class="col-xs-12">
            <center>
                <h2 class="bbtitle">${mapinfo.xzqhname}航道里程现状等级汇总表<br><br>
                    <small class="bbtitle">${mapinfo.day}</small>
                </h2>


            </center>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12 text-right"><label>计量单位：公里</label></div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th width="160px" class="text-center" style="padding:0 0 0 0;"><img
                            class="img-responsive" src="img/line_italic.png"
                            style="width:100%;height:60%;"> <label class="bbcontentbold"
                                                                   style="position: absolute;left:30px;top:25px;">行政区划</label>
                        <label class="bbcontentbold"
                               style="position: absolute;left:100px;top:5px;">航道等级</label></th>

                    <c:forEach items="${lc[0].value}" var="k">
                        <th class="text-center bbcontentbold" style="height:30px;line-height:30px;">${k}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${lc}" begin="1" var="k">
                    <tr>
                        <td class="text-center bbcontent">${k.key}</td>
                        <c:forEach items="${k.value}" var="j">
                            <td class="text-center bbcontent">${j}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </c:otherwise>
                </c:choose>
                </tbody>

            </table>
        </div>
    </div>
</div>
</body>
</html>
