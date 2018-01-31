<%@ page import="com.osg.framework.Constants"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp">
<meta name="keywords" content="Kpass,K-pass">
<meta name="description" content="">

<link rel="shortcut icon" href="favicon.ico">
<%
	request.setAttribute("ossImageHost", "http://" + Constants.ALI_OSS_IMAGE_HOST + "/");
%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var _ctxPath = "${ctxPath}";
	var _ossImageHost = "${ossImageHost}";
</script>
<link
	href="${ctxPath}/moduleweb/resources/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/css/font-awesome.min.css?v=4.4.0"
	rel="stylesheet">
<link href="${ctxPath}/moduleweb/resources/css/animate.min.css"
	rel="stylesheet">
<link href="${ctxPath}/moduleweb/resources/css/style.min.css"
	rel="stylesheet">
<link href="${ctxPath}/moduleweb/resources/css/base.css"
	rel="stylesheet">

<link rel="stylesheet"
	href="${ctxPath}/moduleweb/resources/plugins/layer/skin/layer.css"
	id="layui_layer_skinlayercss">
<link rel="stylesheet"
	href="${ctxPath}/moduleweb/resources/plugins/layer/skin/layer.ext.css"
	id="layui_layer_skinlayerextcss">

<link
	href="${ctxPath}/moduleweb/resources/plugins/jqgrid/ui.jqgrid.css?v=5.0.0"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/plugins/switchery/switchery.css"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/css/awesome-bootstrap-checkbox.css"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/plugins/jasny/jasny-bootstrap.min.css"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/plugins/toastr/toastr.min.css"
	rel="stylesheet">
<link href="${ctxPath}/moduleweb/resources/plugins/ztree/ztree.css"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/plugins/fullcalendar/fullcalendar.css"
	rel="stylesheet">
<link
	href="${ctxPath}/moduleweb/resources/plugins/fullcalendar/fullcalendar.print.css"
	rel="stylesheet">

<script src="${ctxPath}/moduleweb/resources/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctxPath}/moduleweb/resources/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${ctxPath}/moduleweb/resources/js/jquery.metisMenu.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/jquery.slimscroll.min.js"></script>
<script src="${ctxPath}/moduleweb/resources/plugins/layer/layer.min.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/hplus.min.js?v=4.1.0"></script>
<script src="${ctxPath}/moduleweb/resources/js/contabs.min.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/pace.min.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/jquery.form.min.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/jquery.fillform.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/jquery.jsonsubmit.js"></script>


<script
	src="${ctxPath}/moduleweb/resources/plugins/jqgrid/i18n/grid.locale-cn.js?v=5.0.0"></script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/jqgrid/jquery.jqGrid.js?v=5.0.1"></script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/switchery/switchery.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/jasny/jasny-bootstrap.min.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/validate/jquery.validate.min.js?v=1.13.1">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/validate/messages_zh.min.js?v=1.13.1">
	
</script>
<script src="${ctxPath}/moduleweb/resources/plugins/laydate/laydate.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/toastr/toastr.min.js">
	
</script>
<script src="${ctxPath}/moduleweb/resources/plugins/ztree/seeyon.ui.tree-debug.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/ztree/jquery.tree-debug.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/suggest/bootstrap-suggest.min.js">
	
</script>
<script
	src="${ctxPath}/moduleweb/resources/plugins/fullcalendar/fullcalendar.min.js">
	
</script>

<script src="${ctxPath}/moduleweb/resources/js/ext/json.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/eh.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/lang.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/cookie.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/base64.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/ajax.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/object.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/token.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/shade.js"></script>
<script src="${ctxPath}/moduleweb/resources/js/ext/GpsConversion.js"></script>

<script src="${ctxPath}/moduleweb/resources/plugins/jsKnob/jquery.knob.js"></script>

<script type="text/javascript">
	var fillmaps = <c:out value="${_FILL_MAP}" default="null" escapeXml="false"/>;
	$().ready(function() {
		$.autofillform({
			fillmaps : fillmaps
		});
	});
</script>

<script src="${ctxPath}/moduleweb/resources/common.js"></script>