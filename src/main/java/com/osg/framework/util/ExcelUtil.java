/**
 * $Author: wuym $
 * $Rev: 2 $
 * $Date:: 2010-01-29 19:12:44#$:
 *
 * Copyright (c) 2010 UFIDA, Inc. All rights reserved.
 *
 * This software is the proprietary information of UFIDA, Inc.
 * Use is subject to license terms.
 */
package com.osg.framework.util;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import jxl.Cell;
import jxl.CellType;
import jxl.FormulaCell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.CellReferenceHelper;
import jxl.biff.formula.FormulaException;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.Blank;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public final class ExcelUtil {

	private static final Logger LOGGER = Logger.getLogger(ExcelUtil.class);

	public static void excelGenerationByTemplate(final File template, final File outputExcel, final Map dataSet)
			throws Exception {
		excelGenerationByTemplate(template, outputExcel, dataSet, "yyyy-MM-dd HH:mm");
	}

	public static void excelGenerationByTemplate(final File template, final File outputExcel, final Map dataSet,
			final String dateFormat) throws Exception {
		try {
			File outDir = outputExcel.getParentFile();
			if (!outDir.exists())
				outDir.mkdirs();
			Workbook templateWorkbook = Workbook.getWorkbook(template);
			WritableWorkbook outputWorkbook = Workbook.createWorkbook(outputExcel, templateWorkbook);
			for (int sheetNumber = 0; sheetNumber < outputWorkbook.getNumberOfSheets(); sheetNumber++) {
				WritableSheet sheet = outputWorkbook.getSheet(sheetNumber);
				Cell[] rowCells;
				Cell cell;
				formatFormulaCell(templateWorkbook.getSheet(sheetNumber), sheet);
				for (int row = 0; row < sheet.getRows(); row++) {
					rowCells = sheet.getRow(row);
					Range[] ranges = sheet.getMergedCells();
					boolean expanded = false;
					int expandCount = -1;
					for (int column = 0; column < rowCells.length; column++) {
						cell = rowCells[column];
						if (cell.getType().equals(CellType.LABEL)) {
							Label label = (Label) cell;
							String str = label.getString();
							String type = "label";
							// token analyse
							Reader reader = new StringReader(str);
							char c;
							int i;
							StringBuffer sb = new StringBuffer();
							while ((i = reader.read()) != -1) {
								c = (char) i;
								if (c == '{') {
									StringBuffer buf = new StringBuffer();

									int j = reader.read();
									while ((c = (char) j) != '}' && j != -1) {
										buf.append(c);
										j = reader.read();
									}
									String token = buf.toString();
									int typeIdx = token.indexOf('~');
									if (typeIdx != -1 && typeIdx < (token.length() - 1)) {
										type = token.substring(typeIdx + 1);
										token = token.substring(0, typeIdx);
									}
									int dotIdx = token.indexOf('.');
									if (dotIdx == -1) {
										// result
										sb.append(
												getValue(token, dataSet.get(resolveToken(token)), dateFormat, dataSet));
									} else if (dotIdx < (token.length() - 1)) {
										// resultset
										String rsName = token.substring(0, dotIdx);
										String attStr = token.substring(dotIdx + 1);
										List v = (List) dataSet.get(rsName);
										if (v != null) {
											int addIdx = attStr.indexOf('+');
											if (addIdx == -1) {
												if (v.size() > 0) {
													Map record = (Map) v.get(0);
													sb.append(getValue(attStr, record.get(resolveToken(attStr)),
															dateFormat, dataSet));
												}
											} else if (addIdx == (attStr.length() - 1)) {
												attStr = attStr.substring(0, addIdx);
												if (!expanded) {
													expandRow(sheet, row, v.size(), v.size());
													expanded = true;
												}

												for (int m = 0; m < v.size(); m++) {
													Map record = (Map) v.get(m);
													if (m == 0) {
														sb.append(getValue(attStr, record.get(resolveToken(attStr)),
																dateFormat, dataSet));
													} else {
														Cell ce = sheet.getCell(column, row);
														addCell(sheet, column, row + m, getValue(attStr,
																record.get(resolveToken(attStr)), dateFormat, dataSet),
																type, ce.getCellFormat());
													}
												}
											} else {
												expandCount = Integer.parseInt(attStr.substring(addIdx + 1));
												attStr = attStr.substring(0, addIdx);
												if (!expanded) {
													expandRow(sheet, row, expandCount, v.size());
													expanded = true;
												}

												for (int m = 0; m < expandCount; m++) {
													if (m < v.size()) {
														Map record = (Map) v.get(m);
														if (m == 0) {
															sb.append(getValue(attStr, record.get(resolveToken(attStr)),
																	dateFormat, dataSet));
														} else {
															Cell ce = sheet.getCell(column, row);
															addCell(sheet, column, row + m,
																	getValue(attStr, record.get(resolveToken(attStr)),
																			dateFormat, dataSet),
																	type, ce.getCellFormat());
														}
													} else {
														Cell ce = sheet.getCell(column, row);
														addCell(sheet, column, row + m,
																getValue("", "", dateFormat, dataSet), type,
																ce.getCellFormat());
													}
												}
											}
										}
									} else {
										sb.append('{').append(token).append('}');
									}
								} else
									sb.append(c);
							}
							reader.close();
							addCell(sheet, label.getColumn(), label.getRow(), sb.toString(), type,
									label.getCellFormat());
						}
					}
				}
			}

			outputWorkbook.write();
			outputWorkbook.close();

		} catch (Exception e) {
			throw new Exception("", e);
		}
	}

	private static void addCell(WritableSheet sheet, int column, int row, String value, String type, CellFormat cf)
			throws RowsExceededException, WriteException {
		WritableCell addCell;
		if (type.startsWith("number")) {
			if (value != null && !value.equals("")) {
				int scale = 2;
				try {
					scale = Integer.parseInt(type.substring(6));
				} catch (Exception e) {
					/** ignore it */
				}
				double dvalue = 0;
				try {
					dvalue = new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
				} catch (Exception e) {
					/** ignore it */
				}
				StringBuffer sb = new StringBuffer("0");
				if (scale > 0) {
					sb.append(".");
				}
				for (int i = 0; i < scale; i++) {
					sb.append("0");
				}
				NumberFormat nf = new NumberFormat(sb.toString());
				WritableCellFormat wcf = new WritableCellFormat(nf);
				wcf.setAlignment(cf.getAlignment());
				wcf.setBackground(cf.getBackgroundColour(), cf.getPattern());
				wcf.setBorder(Border.ALL, BorderLineStyle.THIN); // TODO Border
				wcf.setOrientation(cf.getOrientation());
				wcf.setShrinkToFit(cf.isShrinkToFit());
				wcf.setVerticalAlignment(cf.getVerticalAlignment());
				wcf.setWrap(cf.getWrap());
				addCell = new Number(column, row, dvalue, wcf);
			} else {
				addCell = new Label(column, row, "", cf);
			}
		} else {
			addCell = new Label(column, row, value, cf);
		}
		sheet.addCell(addCell);
	}

	private static void formatFormulaCell(Sheet originSheet, WritableSheet genSheet)
			throws FormulaException, WriteException, WriteException {
		for (int row = 0; row < originSheet.getRows(); row++) {
			Cell[] rowCells = originSheet.getRow(row);
			for (int column = 0; column < rowCells.length; column++) {
				Cell cell = rowCells[column];
				if (cell.getType() == CellType.NUMBER_FORMULA || cell.getType() == CellType.STRING_FORMULA
						|| cell.getType() == CellType.BOOLEAN_FORMULA || cell.getType() == CellType.FORMULA_ERROR) {
					FormulaCell fc = (FormulaCell) cell;
					Formula formula = new Formula(cell.getColumn(), cell.getRow(), fc.getFormula(),
							cell.getCellFormat());
					genSheet.addCell(formula);
				}
			}
		}
	}

	private static List getFormulaCellList(WritableSheet sheet) {
		List result = new ArrayList();
		for (int row = 0; row < sheet.getRows(); row++) {
			Cell[] rowCells = sheet.getRow(row);
			for (int column = 0; column < rowCells.length; column++) {
				Cell cell = rowCells[column];
				if (cell instanceof Formula) {
					result.add(cell);
				}
			}
		}
		return result;
	}

	private static String resolveToken(String token) {
		String result = "";
		if (token != null) {
			int idx = token.indexOf(':');
			if (idx != -1)
				result = token.substring(0, idx);
			else
				result = token;
		}
		return result;
	}

	private static String getValue(String token, Object valueObj, String dateFormat, Map dataSet) {
		String result = "";
		if (valueObj != null) {
			result = valueObj.toString();
			int idx = token.indexOf(':');
			if (valueObj instanceof Date)
				result = DateUtil.format((Date) valueObj, dateFormat);
			if (idx != -1) {
				String clz = token.substring(idx + 1);
				token = token.substring(0, idx);
				if (!"".equals(clz)) {
					try {
						CellDataRender render = (CellDataRender) Class.forName(clz).newInstance();
						result = render.rend(token, result, dataSet);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	private static int getColWidth(WritableSheet sheet, int column, int curRow) {
		Range[] ranges = sheet.getMergedCells();
		for (int i = 0; i < ranges.length; i++) {
			Range range = ranges[i];
			Cell leftCell = range.getTopLeft();
			Cell rightCell = range.getBottomRight();
			if (leftCell.getColumn() == column && leftCell.getRow() == curRow) {
				return rightCell.getColumn() - leftCell.getColumn() + 1;
			}
		}
		return 1;
	}

	private static void expandRow(WritableSheet sheet, int baseRow, int expandCount, int recordCount)
			throws RowsExceededException, WriteException, FormulaException, IOException {
		List addFormulaCellList = new ArrayList();
		Cell[] rowCells = sheet.getRow(baseRow);
		for (int i = 1; i < expandCount; i++) {
			int row = baseRow + i;
			sheet.insertRow(row);
			int column = 0;
			for (int j = 0; j < rowCells.length; j++) {
				Cell ce = rowCells[j];
				int colspan = getColWidth(sheet, column, baseRow);
				if (colspan > 1) {
					// merge cells
					sheet.mergeCells(column, row, column + colspan - 1, row);
					Cell copyCell = sheet.getCell(column, baseRow);
					if (copyCell instanceof Formula) {
						if (recordCount > i) {
							Formula copyFormula = (Formula) copyCell;
							Formula formula = new Formula(column, row,
									addFormulaRefRow(copyFormula.getContents(), baseRow, i + 1),
									copyCell.getCellFormat());
							addFormulaCellList.add(formula);
						}
					}
					Label label = new Label(column, row, "", copyCell.getCellFormat());
					sheet.addCell(label);
					// fill in blank cells of the spanned column
					for (int k = 1; k < colspan; k++) {
						Blank blank = new Blank(column + k, row, sheet.getCell(column + k, baseRow).getCellFormat());
						sheet.addCell(blank);
					}
					// set row height
					sheet.setRowView(row, sheet.getRowView(baseRow).getDimension() / 20);
				}
				// calculate current column
				column = column + colspan;
			}
		}

		List formulaCellList = getFormulaCellList(sheet);
		for (int l = 0; l < formulaCellList.size(); l++) {
			Formula formula = (Formula) formulaCellList.get(l);
			Reader reader = new StringReader(formula.getContents().toUpperCase());
			char c;
			int i;
			StringBuffer sb = new StringBuffer();
			boolean change = false;
			while ((i = reader.read()) != -1) {
				c = (char) i;
				if (c > 'A' || c < 'Z') {
					StringBuffer buf = new StringBuffer();
					buf.append(c);

					int j = reader.read();
					c = (char) j;
					boolean hasDigit = false;
					while (Character.isLetter(c) && j != -1) {
						buf.append(c);
						j = reader.read();
						c = (char) j;
					}
					if (Character.isDigit(c) && j != -1) {
						buf.append(c);
						j = reader.read();
						c = (char) j;
						while (Character.isDigit(c) && j != -1) {
							buf.append(c);
							j = reader.read();
							c = (char) j;
						}
						hasDigit = true;
					}
					if (hasDigit) {
						int refRow = CellReferenceHelper.getRow(buf.toString());
						if (refRow > baseRow) {
							change = true;
							CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
									refRow + expandCount - 1, sb);
						} else if (refRow == baseRow) {
							if (formula.getRow() == baseRow) {

							} else {
								change = true;
								CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
										refRow, sb);
								sb.append(":");
								CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
										refRow + expandCount - 1, sb);
							}
						} else {
							sb.append(buf.toString());
						}
					} else {
						sb.append(buf);
					}
					if (j != -1) {
						sb.append(c);
					}
				} else
					sb.append(c);
			}
			reader.close();
			if (change) {
				Formula newFormula = new Formula(formula.getColumn(), formula.getRow(), sb.toString(),
						formula.getCellFormat());
				sheet.addCell(newFormula);
			}
		}

		for (int i = 0; i < addFormulaCellList.size(); i++) {
			sheet.addCell((Formula) addFormulaCellList.get(i));
		}
	}

	private static String addFormulaRefRow(String formula, int baseRow, int expandCount) throws IOException {
		Reader reader = new StringReader(formula.toUpperCase());
		char c;
		int i;
		StringBuffer sb = new StringBuffer();
		while ((i = reader.read()) != -1) {
			c = (char) i;
			if (c > 'A' || c < 'Z') {
				StringBuffer buf = new StringBuffer();
				buf.append(c);

				int j = reader.read();
				c = (char) j;
				boolean hasDigit = false;
				while (Character.isLetter(c) && j != -1) {
					buf.append(c);
					j = reader.read();
					c = (char) j;
				}
				if (Character.isDigit(c) && j != -1) {
					buf.append(c);
					j = reader.read();
					c = (char) j;
					while (Character.isDigit(c) && j != -1) {
						hasDigit = true;
						buf.append(c);
						j = reader.read();
						c = (char) j;
					}
				}
				if (hasDigit) {
					int refRow = CellReferenceHelper.getRow(buf.toString());
					if (refRow == baseRow) {
						CellReferenceHelper.getCellReference(CellReferenceHelper.getColumn(buf.toString()),
								refRow + expandCount - 1, sb);
					} else {
						sb.append(buf.toString());
					}
				} else {
					sb.append(buf);
				}
				if (j != -1) {
					sb.append(c);
				}
			} else
				sb.append(c);
		}
		reader.close();
		return sb.toString();
	}

}