<%@ page pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
</head>
<body>
	<img id="qrimage" src="${ctxPath }/organ/qr/${param.organId}.do" />
	<div style="font-size: 16px;text-align: center; width: 200px;margin-top: 6px">${param.organName}</div>
	<script type="text/javascript">window.print();</script>
</body>
</html>

