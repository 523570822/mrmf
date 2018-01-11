/*
 * jQuery Auto Fill Form Plugin @requires jQuery v1.1 or later @author Andy
 */
(function($) {
	/**
	 * $.autofillform() provides a mechanism for fill form automatically, no
	 * more script or code, very convenient.
	 * 
	 * $.autofillform() accepts a single option object argument, the following
	 * attributes are supported:
	 * 
	 * fillformobj: Identifies the data to fill form.
	 */
	$._autofill = new Object();

	$.autofillform = function(options) {
		var settings = {};
		options = $.extend(settings, options);

		var fillmaps = options.fillmaps ? options.fillmaps : new Object();
		$._autofill.filllists = fillmaps;

		for ( var f in fillmaps) {
			$("#" + f).fillform(fillmaps[f]);
		}
	}

	$.fn.clearform = function(options) {
		var settings = {
			clearHidden : false
		};
		options = $.extend(settings, options);
		this.find(':input').each(function() {
			$t = $(this);
			switch (this.type) {
			case 'passsword':
			case 'select-multiple':
			case 'select-one':
			case 'text':
			case "number":
			case "digits":
			case 'textarea':
				$t.val('');
			case 'hidden':
				if ($t.data("suggest_raw")) {
					$t.val('');
					$t.attr("selectedIndex", "");
					$t.data("suggest_raw").attr("data-id", "");
				} else if (options.clearHidden)
					$t.val('');
				break;
			case 'checkbox':
			case 'radio':
				this.checked = false;
			}
		});
	}

	$.fn.fillform = function(fillData) {
		if (this[0] == null)
			return;
		this.each(function(i) {
			var frm = $(this);
			// frm.resetValidate();
			for ( var fi in fillData) {
				var ft = $("#" + fi, frm).add($("." + fi, frm));
				if (fi == frm.attr("id") && fi == frm.attr("name")) {
					frm.fill(fillData[fi], fi, frm);
				} else {
					ft.each(function(i) {
						$(this).fill(fillData[fi], fi, frm);
					});
				}
			}
			frm = null;
		});
	}

	$.fn.fill = function(v, fi, frm) {
		var el = this[0], eq = $(el), tag = el.tagName.toLowerCase();
		if (v && typeof v == "string")
			v = v.replace(/<\/\/script>/gi, "</script>");
		var t = el.type, val = el.value;
		switch (tag) {
		case "input":
			switch (t) {
			case "email":
			case "password":
			case "number":
			case "digits":
			case "text":
				var f = false;
				if (v instanceof Array) {
					var clz = eq.attr("class");
					if (clz) {
						var cls = clz.split(" ");
						for (var i = 0; i < v.length; i++) {
							for (var j = 0; j < cls.length; j++) {
								var cl = cls[j];
								if (eq.attr("name") == cl + "[" + i + "]") {
									eq.val(v[i]);
									f = true;
									break;
								}
							}
							if (f)
								break;
						}
					}
				} else if (v instanceof Object) {
					for ( var o in v) {
						if (eq.attr("id") == (fi + "." + o)) {
							eq.val(v[o]);
							f = true;
							break;
						}
					}
				}
				if (!f) {
					eq.val(v);
				}
				break;
			case "hidden":
				/*
				 * var cp = eq.attrObj("_comp"), ctp; if (cp) { ctp =
				 * cp.attr("compType"); if (ctp === "selectPeople") { var pv =
				 * "", pt = ""; if (v && v.startsWith("{")) { v =
				 * $.parseJSON(v); cp.comp(v); pv = v.value; pt = v.text; }
				 * cp.val(pt); eq.val(pv); break; } }
				 */
				if (eq.data("suggest_raw")) {
					var dd = eq.data("suggest_data");
					for (var i = 0; i < dd.length; i++) {
						if (v === dd[i]._id) {
							if (eq.attr("selectedIndex") == ''
									|| eq.attr("selectedIndex") != i) {
								eq.val(v);
								eq.attr("selectedIndex", i);
								eq.data("suggest_raw").attr("data-id", v);
								eq.data("suggest_raw").val(
										dd[i][eq.data("suggest_name")]);
								eq.trigger("change");
							}
							break;
						}
					}
				} else if (v instanceof Object) {
					for ( var o in v) {
						if (eq.attr("id") == (fi + "." + o)) {
							eq.val(v[o]);
							break;
						}
					}
				} else {
					eq.val(v);
				}
				break;
			case "checkbox":
				if (v instanceof Array) {
					var found = false;
					for (var i = 0; i < v.length; i++) {
						if (v[i] == val) {
							el.checked = true;
							found = true;
							break;
						}
					}
					if (!found)
						el.checked = false;
				} else {
					if (v == true || v == val)
						el.checked = true;
					else
						el.checked = false;
				}
				break;
			case "radio":
				/*
				 * if (frm) { $("input[type=radio]", frm).each( function() { v =
				 * String(v); // convert boolean to string if ((this.id == fi ||
				 * this.name == fi) && v == this.value && !this.checked)
				 * this.checked = true; }); } else
				 */
				if (v == el.value && !el.checked) {
					el.checked = true;
				} else {
					el.checked = false;
				}
			}
			break;
		case "textarea":
			eq.val(v);
			break;
		case "select":
			switch (t) {
			case "select-one":
				v = '' + v; // number convert to string
				if (typeof v === 'string') {
					selectVal(eq[0], v);
				} else if (v instanceof Array) {
					for (var i = 0; i < v.length; i++) {
						selectVal(eq[0], v[i]);
					}
				}
				break;
			case "select-multiple":
				var ops = el.options;
				var sv = v;
				if (typeof v === 'string') {
					sv = v.split(",");
				}
				for (var i = 0; i < ops.length; i++) {
					var op = ops[i];
					// extra pain for IE...
					var opv = op.value;
					for (var j = 0; j < sv.length; j++) {
						if (opv == sv[j]) {
							op.selected = true;
						}
					}
				}
			}
			break;
		default:
			if (!((!v || v == '') && $(this)[0].innerHTML.indexOf('&nbsp;') != -1)) {
				if (v && eq.parent('.text_overflow').length == 1) {
					eq.attr('title', v);
				}
				if (v && typeof v == "string")
					v = v.replace(/\n/g, '<br/>');
				el.innerHTML = v;
			}
		}

		function selectVal(ele, vl) {
			for (var i = 0; i < ele.options.length; i++) {
				if (ele.options[i].value == vl) {
					ele.options[i].selected = true;
					break;
				}
			}
			$(ele).trigger('change');
		}
		/*
		 * if (this.attr('validate')) { this.validate(); }
		 */
	};

	$.fn.fillgrid = function(d) {
		this.each(function(i) {
			var t = this.tagName.toLowerCase(), e = $(this);
			switch (t) {
			case "table":
				elem.grid.addData(d);
				break;
			}
		});
	};
})(jQuery);