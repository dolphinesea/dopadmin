package com.xinguo.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.xinguo.dop.entity.Callrecord;
import com.xinguo.dop.entity.RecordDetail;
import com.xinguo.dop.entity.RecordStatistics;

public class CreatExl {
	public static String outFile = "e:/bb2.xls";

	public static void main(String[] args) {

		List<RecordStatistics> dataset = new ArrayList<RecordStatistics>();
		RecordStatistics recordStatistics = new RecordStatistics();
		recordStatistics.setOperatorName("111");
		RecordStatistics recordStatistics1 = new RecordStatistics();
		recordStatistics1.setOperatorName("111222");
		RecordStatistics recordStatisticsfoot1 = new RecordStatistics();
		recordStatisticsfoot1.setOperatorName("合计");
		RecordStatistics recordStatisticsfoot2 = new RecordStatistics();
		recordStatisticsfoot2.setOperatorName("百分比%");

		dataset.add(recordStatistics);
		dataset.add(recordStatistics1);
		dataset.add(recordStatisticsfoot1);
		dataset.add(recordStatisticsfoot2);

		List<RecordDetail> dataset1 = new ArrayList<RecordDetail>();
		RecordDetail r1 = new RecordDetail();
		r1.setId(1);
		r1.setOperatorDesk("aaa");
		RecordDetail r2 = new RecordDetail();
		r2.setId(2);
		r2.setOperatorDesk("bbb");
		RecordDetail r3 = new RecordDetail();
		r3.setId(3);
		r3.setOperatorDesk("ccc");
		r3.setNumbertype("2");
		r3.setDealproblem("2");
		r3.setChecknumber("2");
		RecordDetail r4 = new RecordDetail();
		r4.setId(4);
		r4.setOperatorDesk("ddd");
		r4.setNumbertype("1");
		r4.setDealproblem("1");
		r4.setChecknumber("1");
		r4.setServiceterms(2);
		r4.setIsbad(2);
		dataset1.add(r1);
		dataset1.add(r2);
		dataset1.add(r3);
		dataset1.add(r4);
		// exportStatistics(dataset, "2000-1-1", "2003-3-3");
		exportDetail(dataset1, "111");
	}

	private static String exportStatistics(List<RecordStatistics> list,
			String StartTime, String EndTime, String tempPath) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					tempPath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 获取标题
			HSSFRow titlerow = sheet.getRow(0);
			HSSFCell titlecell = titlerow.getCell(0);
			titlecell
					.setCellValue("话务服务统计表（" + StartTime + "到" + EndTime + "）");

			// 获取副标题
			HSSFRow smalltitlerow = sheet.getRow(1);
			HSSFCell smalltitlecell = smalltitlerow.getCell(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smalltitlecell.setCellValue("制表日期:" + sdf.format(new Date()));

			// 获取数据行
			HSSFRow datarow = sheet.getRow(4);
			HSSFCell datacell = datarow.getCell(0);
			HSSFCellStyle datacellCellStyle = datacell.getCellStyle();

			datacellCellStyle.setBorderBottom(new Short("1"));
			datacellCellStyle.setBorderRight(new Short("1"));

			for (short i = 4; i < list.size() + 4; i++) {
				HSSFRow row = sheet.createRow(i); // 在索引0的位置开始创建行(最顶端的行)

				RecordStatistics recordStatistics = list.get(i - 4);
				Field[] fields = recordStatistics.getClass()
						.getDeclaredFields();
				for (short j = 0; j < 14; j++) {
					HSSFCell cell = row.createCell(j); // 在索引0的位置开始创建单元格(左上端)
					cell.setCellStyle(datacellCellStyle);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置单元格的类型为字符串;

					Field field = fields[j];
					String fieldName = field.getName();
					String getMethodName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					String textValue = null;
					try {
						Class tCls = recordStatistics.getClass();
						Method getMethod = tCls.getMethod(getMethodName,
								new Class[] {});
						Object value = getMethod.invoke(recordStatistics,
								new Object[] {});
						// 判断值的类型后进行强制类型转换

						if (value == null) {
							textValue = null;
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}

					} catch (SecurityException e) {
						
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} finally {
						// 清理资源
					}
					cell.setCellValue(textValue); // 在单元格输入一些内容;
				}
			}
			String filePath = "e:/st1.xls";
			FileOutputStream out = new FileOutputStream(filePath); // 创建文件输出流
			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("文件生成...");
			return filePath;

		} catch (Exception e) {
			System.out.println("已运行 xlCreate():" + e);
			return "";
		}
	}

	private static void exportDetail(List<RecordDetail> list,
			String operatorName) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					"e:/dt.xls"));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 获取副标题
			HSSFRow operatorNamerow = sheet.getRow(2);
			HSSFCell operatorNamecell = operatorNamerow.getCell(1);

			operatorNamecell.setCellValue(operatorName);

			// 获取副标题
			HSSFRow smalltitlerow = sheet.getRow(2);
			HSSFCell smalltitlecell = smalltitlerow.getCell(10);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smalltitlecell.setCellValue(sdf.format(new Date()));

			// 获取数据行
			HSSFRow datarow = sheet.getRow(5);
			HSSFCell datacell = datarow.getCell(0);
			HSSFCellStyle datacellCellStyle = datacell.getCellStyle();

			datacellCellStyle.setBorderBottom(new Short("1"));
			datacellCellStyle.setBorderRight(new Short("1"));

			for (short i = 4; i < list.size() + 4; i++) {
				HSSFRow row = sheet.createRow(i); // 在索引0的位置开始创建行(最顶端的行)

				RecordDetail recordDetail = list.get(i - 4);
				Field[] fields = recordDetail.getClass().getDeclaredFields();

				int cellindex = 0;
				for (int j = 0; j < fields.length; j++) {
					HSSFCell cell = null;
					String fieldName = fields[j].getName();
					String getMethodName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					String textValue = null;
					try {
						Class tCls = recordDetail.getClass();
						Method getMethod = tCls.getMethod(getMethodName,
								new Class[] {});
						Object value = getMethod.invoke(recordDetail,
								new Object[] {});
						if (j == 4) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("1")) {
								SetCell(datacellCellStyle, row, cellindex,
										"重要1");
							} else if (value.toString().trim().equals("2")) {
								SetCell(datacellCellStyle, row, cellindex,
										"重要2");
							} else {
								SetCell(datacellCellStyle, row, cellindex, "");
							}

						}
						if (j == 5) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("1")) {
								SetCell(datacellCellStyle, row, cellindex, "超时");
							} else {
								SetCell(datacellCellStyle, row, cellindex, "");
							}
						}
						if (j == 6) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("1")) {
								textValue = "基本";
								SetCell(datacellCellStyle, row, cellindex,
										textValue);
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("2")) {
								textValue = "非基本";
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex,
										textValue);
							}
						} else if (j == 8) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("1")) {
								textValue = "大超时";
								SetCell(datacellCellStyle, row, cellindex,
										textValue);
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("2")) {
								textValue = "小超时";
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex,
										textValue);
							}
						} else if (j == 9) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("1")) {
								textValue = "处理好";
								SetCell(datacellCellStyle, row, cellindex,
										textValue);
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("2")) {
								textValue = "处理坏";
								SetCell(datacellCellStyle, row, cellindex, "");
								cellindex = cellindex + 1;
								SetCell(datacellCellStyle, row, cellindex,
										textValue);

							}
						} else if (j == 10) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("2")) {
								SetCell(datacellCellStyle, row, cellindex,
										"用于不佳");
							} else {
								SetCell(datacellCellStyle, row, cellindex, "");
							}
						} else if (j == 11) {
							if (value == null) {
								SetCell(datacellCellStyle, row, cellindex, "");
							} else if (value.toString().trim().equals("2")) {
								SetCell(datacellCellStyle, row, cellindex, "差");
							} else {
								SetCell(datacellCellStyle, row, cellindex, "");
							}
						} else {
							if (value == null) {
								textValue = "";
							} else {
								// 其它数据类型都当作字符串简单处理
								textValue = value.toString();
							}
							SetCell(datacellCellStyle, row, cellindex,
									textValue);
						}

						cellindex++;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			FileOutputStream out = new FileOutputStream("e:/dt1.xls"); // 创建文件输出流
			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("文件生成...");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("已运行 xlCreate():" + e);
		}
	}

	private static void SetCell(HSSFCellStyle datacellCellStyle, HSSFRow row,
			int j, String textValue) {
		HSSFCell cell;
		cell = row.createCell(j); // 在索引0的位置开始创建单元格(左上端)
		cell.setCellStyle(datacellCellStyle);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(textValue);
	}
}
