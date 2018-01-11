function selectImg() {
			$('#onlyFile').click();
		}
		function changeFile(_this) {
			//$.shade.show();
			$("#fileForm").ajaxSubmit({
				type : 'post',
				headers : {
					'type' : $(_this).attr("rel"),
					'isPublic' : 'true'
				},
				url : URL.fileupload,
				success : function(data) {
					$.shade.hide();
					var imgpath = _ossImageHost + data.data[0];
					$("#logoimg").attr("src", imgpath);
					$("#logo").val(data.data[0]);
				},
				error : function(XmlHttpRequest, textStatus, errorThrown) {
					$.shade.hide();
				}
			});

		}