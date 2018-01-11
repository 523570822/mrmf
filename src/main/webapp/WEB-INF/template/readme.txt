	@RequestMapping("/export")
	public ModelAndView export(HttpServletRequest request, HttpServletResponse response) throws Exception {
		File template = new File(
				request.getSession().getServletContext().getRealPath("/WEB-INF/template/template_staff_export.xls"));
		File outputExcel = new File(request.getSession().getServletContext()
				.getRealPath("/WEB-INF/template/tmp/" + DataEntity.getLongUUID() + ".xls"));

		FlipInfo<Staff> fpi = new FlipPageInfo<Staff>(request);
		fpi = staffService.queryAll(fpi);
		List<Staff> projects = fpi.getData();

		Map dataSet = new HashMap();
		dataSet.put("staff", JsonUtils.fromJson(JsonUtils.toJson(projects), List.class));

		try {
			ExcelUtil.excelGenerationByTemplate(template, outputExcel, dataSet);
			DownloadResponse ds = new DownloadResponse(response);
			ds.download(outputExcel, "员工信息.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}