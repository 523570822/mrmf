<%@ page pageEncoding="utf-8"%>
<%@ include file="/moduleweb/resources/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html {
	width: 100%;
	height: 99%;
	margin: 0;
	font-family: "微软雅黑";
	font-size: 14px;
}

#l-map {
	height: 93%;
	width: 100%;
}

#r-result {
	width: 100%;
	margin-top: 5px;
	margin-bottom: 5px;
}

.remark {
	margin-left: 5px;
	font-size: 16px;
	font-weight: bold;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=0YBnsv6r5yFiejUT0fGvmmMc"></script>
<title>关键字输入提示词条</title>

<script type="text/javascript">
	var map, local, communityMarker = null, label = null, opts = {
		width : 250, // 信息窗口宽度
		height : 60, // 信息窗口高度
		enableMessage : false
	//设置允许信息窗发送短息
	}, menuItem = [ {
		text : '设置公司地点',
		callback : function(p) {
			selectedCommunity(p, "公司地点")
		}
	} ];

	// 截取URL参数
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	var lat = getQueryString("lat"), lng = getQueryString("lng");

	$(document).ready(
			function() {
				map = new BMap.Map("l-map");
				//青岛 120.389416,36.072348
				//北京  116.404, 39.915
				if (!lat)
					lat = 39.90669181668701;
				if (!lng)
					lng = 116.39776061708898;

				// 初始化地图，设置中心点坐标和地图级别
				var gcjPoint = GPS.bd_encrypt(lat, lng);//gcj-2转百度
				lat = gcjPoint.lat;
				lng = gcjPoint.lon;
				map.centerAndZoom(new BMap.Point(lng, lat), 16);

				map.enableScrollWheelZoom(); //开启鼠标滚轮缩放功能。仅对 PC 上有效。
				map.disableDoubleClickZoom();//关闭双击放大地图
				//添加一堆控件
				map.addControl(new BMap.NavigationControl());
				map.addControl(new BMap.ScaleControl());
				map.addControl(new BMap.OverviewMapControl());
				map.addControl(new BMap.MapTypeControl());

				map.addEventListener("dblclick", function(e) { //鼠标点击下拉列表后的事件
					selectedCommunity(e.point, "公司地点");
				});

				var menu = new BMap.ContextMenu();
				menu.addItem(new BMap.MenuItem(menuItem[0].text,
						menuItem[0].callback, 100));
				map.addContextMenu(menu);

				local = new BMap.LocalSearch(map, { //智能搜索
					onSearchComplete : addSearchMarker
				});

				var ac = new BMap.Autocomplete( //建立一个自动完成的对象
				{
					"input" : "suggestId",
					"location" : map
				});

				ac.addEventListener("onhighlight", function(e) { //鼠标放在下拉列表上的事件
					var str = "";
					var _value = e.fromitem.value;
					var value = "";
					if (e.fromitem.index > -1) {
						value = _value.province + _value.city + _value.district
								+ _value.street + _value.business;
					}
					str = "FromItem<br />index = " + e.fromitem.index
							+ "<br />value = " + value;

					value = "";
					if (e.toitem.index > -1) {
						_value = e.toitem.value;
						value = _value.province + _value.city + _value.district
								+ _value.street + _value.business;
					}
					str += "<br />ToItem<br />index = " + e.toitem.index
							+ "<br />value = " + value;
					$("#searchResultPanel").html(str);
				});

				ac.addEventListener("onconfirm",
						function(e) { //鼠标点击下拉列表后的事件
							var _value = e.item.value;
							var myValue = _value.province + _value.city
									+ _value.district + _value.street
									+ _value.business;
							$("#searchResultPanel").html(
									"onconfirm<br />index = " + e.item.index
											+ "<br />myValue = " + myValue);

							searchTXT(myValue);
						});

				if (lat != null && lng != null) {
					selectedCommunity(new BMap.Point(lng, lat), "公司地点")
				}

				$('#suggestId').bind('keypress', function(event) {
					if (event.keyCode == "13") {
						searchTXT($('#suggestId').val());
					}
				});
			});

	function searchTXT(value) {
		clearLays();
		local.search(value);
	}

	function clearLays() {
		map.clearOverlays();
		if (label != null) {
			communityMarker.setLabel(label);
		}
		if (communityMarker != null) {
			map.addOverlay(communityMarker);
		}
	}

	function selectedCommunity(p, title) {
		//查询地址信息
		//getAddress(p);
		var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",
				new BMap.Size(23, 25), {
					offset : new BMap.Size(10, 25), // 指定定位位置  
					imageOffset : new BMap.Size(0, 0 - 10 * 25)
				// 设置图片偏移  
				});
		label = new BMap.Label(title, {
			offset : new BMap.Size(20, -10)
		});
		communityMarker = new BMap.Marker(p, {
			icon : myIcon,
			title : title
		}); // 创建标注
		communityMarker.setLabel(label);
		communityMarker.enableDragging(); //可拖拽 
		clearLays();
	}

	function addSearchMarker() {
		var localresult = local.getResults(); //获取第一个智能搜索的结果
		var flag = true;
		for (var i = 0; i < localresult.getCurrentNumPois(); i++) {
			var nowdata = localresult.getPoi(i);
			var marker = new BMap.Marker(nowdata.point); // 创建标注
			marker.setTitle(nowdata.title);
			marker.disableDragging(); // 不可拖拽
			//marker.enableDragging();			//可拖拽
			map.addOverlay(marker); // 将标注添加到地图中

			var content = nowdata.title + "</br>地址:" + nowdata.address;
			if (flag) {
				map.centerAndZoom(nowdata.point, 16);
				flag = false;
			}

			addClickHandler(content, marker);
		}

		function addClickHandler(content, marker) {
			marker.addEventListener("click", function(e) {
				openInfo(content, e)
			});
		}

		function openInfo(content, e) {
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var infoWindow = new BMap.InfoWindow(content, opts); // 创建信息窗口对象 
			map.openInfoWindow(infoWindow, point); //开启信息窗口
		}
	}

	function getAddress(p, fn) {
		var gc = new BMap.Geocoder();
		//获取地址的数据地址
		gc.getLocation(p, function(rs) {
			fn(rs);
		});
	}

	function selectAddress() {
		if (communityMarker == null) {
			eh.ui.dialogTip.popup("请双击或者右键选取位置!");
		} else {
			var p = communityMarker.point;
			getAddress(p, function(address) {
				var gcjPoint = GPS.bd_decrypt(address.point.lat,
						address.point.lng);//百度坐标转gcj-2
				address.point.lat = gcjPoint.lat;
				address.point.lng = gcjPoint.lon;
				window.parent.selectAddress(address);
			});
		}
	}
</script>
</head>
<body>
	<div id="r-result">
		&nbsp;<input type="text" id="suggestId" size="20"
			style="width: 250px;" />&nbsp;<input type="button"
			onclick="searchTXT($('#suggestId').val())" value="搜索" />&nbsp;
		<!-- <input type="button" onclick="clearLays()" value="清除" /> -->
		<input type="button" value="确定" onclick="selectAddress()" />&nbsp;&nbsp;&nbsp;&nbsp;请双击或者右键选取公司的位置
	</div>
	<div id="searchResultPanel"
		style="border: 1px solid #C0C0C0; width: 350px; height: auto; display: none;"></div>
	<div id="l-map"></div>
</body>
</html>

