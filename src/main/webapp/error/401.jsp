<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>401错误</title>
</head>
<body>
	<div class="c">
		<p>
		</p>
		<p class="title">对不起，您没有访问权限或权限已失效...请重新登录</p>
		<p>
			<a class="button" href="#" id="refe"> <span
				class="button-ico1">返回上一页</span>
			</a> <a class="button" href="${ctxPath}"> <span class="button-ico2">首页</span>
			</a>
		</p>
	</div>
	<script type="text/javascript">
    	document.getElementById('refe').onclick = function(){
        	location.replace(document.referrer);
        }
	</script>
</body>

</html>
