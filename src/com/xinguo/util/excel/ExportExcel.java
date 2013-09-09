package com.xinguo.util.excel;

import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.record.FontRecord;
import org.apache.poi.hssf.record.formula.functions.T;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;

import sun.util.logging.resources.logging;

import com.xinguo.dop.entity.RecordDetail;
import com.xinguo.dop.entity.RecordStatistics;
import com.xinguo.dop.entity.Subscriber;
import com.xinguo.util.common.SystemConfig;

public class ExportExcel<T> {
	public static String outFile = "e:/bb2.xls";

	/**
	 * 导出统计excel
	 * @param list
	 * @param StartTime
	 * @param EndTime
	 * @param tempPath
	 * @param out
	 */
	public static void exportStatistics(List<RecordStatistics> list,
			String StartTime, String EndTime, String tempPath, OutputStream out) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					tempPath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);
			// 获取标题
			HSSFRow titlerow = sheet.getRow(0);
			HSSFCell titlecell = titlerow.getCell(0);
			/*HSSFFont titlefont = workbook.createFont();
			titlefont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titlefont.setFontHeightInPoints((short)18);
			HSSFCellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(titlefont);
			titlecell.setCellStyle(cellStyle);*/
			titlecell.setCellValue("话务服务统计表（" + StartTime + "到" + EndTime + "）");

			// 获取副标题
			HSSFRow smalltitlerow = sheet.getRow(1);
			HSSFCell smalltitlecell = smalltitlerow.getCell(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smalltitlecell.setCellValue("制表日期:" + sdf.format(new Date()));

			// 获取数据行
			HSSFRow datarow = sheet.getRow(2);
			HSSFCell datacell = datarow.getCell(0);
			
			//设置数据行样式
			HSSFCellStyle datacellCellStyle = workbook.createCellStyle();
			datacellCellStyle.setBorderBottom(new Short("1"));
			datacellCellStyle.setBorderRight(new Short("1"));
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short)10);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			datacellCellStyle.setFont(font);
			datacell.setCellStyle(datacellCellStyle);
			
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
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} finally {
						// 清理资源
					}
					cell.setCellValue(textValue); // 在单元格输入一些内容;
				}
			}

			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("文件生成...");

		} catch (Exception e) {
			System.out.println("已运行 xlCreate():" + e);

		}
	}

	/**
	 * 导出详细excel 单人
	 * 
	 * @param list
	 * @param operatorName
	 * @param tempPath
	 * @param out
	 */
	public static void exportDetail(List<RecordDetail> list,
			String operatorName, String tempPath, OutputStream out) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					tempPath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			HSSFSheet sheet = workbook.getSheetAt(0);

			workbook.setSheetName(0, operatorName);

			// 获取工号
			HSSFRow operatorNamerow = sheet.getRow(2);
			HSSFCell operatorNamecell = operatorNamerow.getCell(1);

			operatorNamecell.setCellValue(operatorName);

			// 获取时间
			// HSSFRow smalltitlerow = sheet.getRow(2);
			HSSFCell smalltitlecell = operatorNamerow.getCell(13);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smalltitlecell.setCellValue(sdf.format(new Date()));
			// smalltitlecell.setCellValue("dsads");

			// 获取数据行
			HSSFRow datarow = sheet.getRow(4);
			HSSFCell datacell = datarow.getCell(0);
			HSSFCellStyle datacellCellStyle = datacell.getCellStyle();

			datacellCellStyle.setBorderBottom(new Short("1"));
			datacellCellStyle.setBorderRight(new Short("1"));

			listToDateCell(list, sheet, datacellCellStyle);

			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("文件生成...");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("已运行 xlCreate():" + e);
		}
	}

	/**
	 * 导出详细excel 多人
	 * 
	 * @param list
	 * @param operatorName
	 * @param tempPath
	 * @param out
	 */
	public static void exportDetailMuti(List<List<RecordDetail>> list, String operatorName,
			String tempPath, OutputStream out) {
		try {
			//String[] operatorNameArr = operatorName.split(",");

			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(
					tempPath));
			HSSFWorkbook workbook = new HSSFWorkbook(fs);

			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					
					
					/*this is bak
					 * List l1=getOpList(operatorNameArr[j], list);
					if (l1==null) {
						continue;
					}
					*/
					// can not happen,protected this proc
					if(list.get(j)== null || list.get(j).size() == 0){
						list.remove(j);
					}
					HSSFSheet sheet = workbook.cloneSheet(0);

					workbook.setSheetName(j + 1, list.get(j).get(0).getOperatorName());
					// 获取工号
					HSSFRow operatorNamerow = sheet.getRow(2);
					HSSFCell operatorNamecell = operatorNamerow.getCell(1);

					operatorNamecell.setCellValue(list.get(j).get(0).getOperatorName());

					// 获取时间
					HSSFCell smalltitlecell = operatorNamerow.getCell(13);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					smalltitlecell.setCellValue(sdf.format(new Date()));

					// 获取数据行
					HSSFRow datarow = sheet.getRow(4);
					HSSFCell datacell = datarow.getCell(0);
					HSSFCellStyle datacellCellStyle = datacell.getCellStyle();

					datacellCellStyle.setBorderBottom(new Short("1"));
					datacellCellStyle.setBorderRight(new Short("1"));
					
					sheet.setColumnWidth(8, (sdf.format(new Date()).getBytes().length) * 200);
					
					listToDateCell( list.get(j), sheet, datacellCellStyle);
					
					

				}
				workbook.removeSheetAt(0);// 删除模版

			}
			//2012-11-16 delete this down on code.
			/*else{

				HSSFSheet sheet = workbook.getSheetAt(0);

				workbook.setSheetName(0, operatorNameArr[0]);

				// 获取工号
				HSSFRow operatorNamerow = sheet.getRow(2);
				HSSFCell operatorNamecell = operatorNamerow.getCell(1);

				operatorNamecell.setCellValue(operatorName);

				// 获取时间
				HSSFCell smalltitlecell = operatorNamerow.getCell(13);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				smalltitlecell.setCellValue(sdf.format(new Date()));

				// 获取数据行
				HSSFRow datarow = sheet.getRow(4);
				HSSFCell datacell = datarow.getCell(0);
				HSSFCellStyle datacellCellStyle = datacell.getCellStyle();

				datacellCellStyle.setBorderBottom(new Short("1"));
				datacellCellStyle.setBorderRight(new Short("1"));
				
				listToDateCell( list.get(0), sheet, datacellCellStyle);
				
			}*/
			workbook.write(out);
			out.flush();
			out.close();
			System.out.println("文件生成...");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("已运行 xlCreate():" + e);
		}
	}
	/**
	 * this is method bak
	 * @param operatorName
	 * @param list
	 * @return
	 */
	private static List getOpList(String operatorName,List list){
		List listDetail=null;
		for (int i = 0; i < list.size(); i++) {
			//List<RecordDetail> rdDetail=(List<RecordDetail>)(list.get(i));
			if (((List<RecordDetail>)(list.get(i))).get(0).getOperatorName().equals(operatorName)) {
				listDetail=(List)list.get(i);
			}
		}
		return listDetail;
	}
	
    public static   void exportSubscriber(String pageSize,List<Subscriber> list, OutputStream out,
			String tempPath) throws FileNotFoundException, IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(tempPath));
		// print itme of number. 25

		int printCount = 25;
		if (SystemConfig.isSubscriberPageBreak == 0)
			printCount = SystemConfig.subscriberPageBreak;
		else if (SystemConfig.isSubscriberPageBreak == 1)
			printCount = new Integer(pageSize);
		System.out.println("exportExcel printCount:"+printCount+";SystemConfig.isSubscriberPageBreak="+SystemConfig.isSubscriberPageBreak);
		int index = 0; // excel of index
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row = null,infoRow = sheet.getRow(0);
		HSSFCell cell = null;
		HSSFCellStyle style = workbook.getSheetAt(0).getRow(1)
										.getCell(0).getCellStyle();
		for (int i = 0; i < list.size(); i++) {
			if ( i != 0 && i % printCount == 0  ){

				index+=2;
				sheet.addMergedRegion(new CellRangeAddress(index-1,index-1, 0, 5));
				sheet.setRowBreak(index-1);
				// jump first row
                row = sheet.createRow(index);
			   for (int j = 0; j < 6; j++) {
                  cell = row.createCell(j);
				  cell.setCellStyle(infoRow.getCell(0).getCellStyle());
				  cell.setCellValue(infoRow.getCell(j).getRichStringCellValue());
			  }
			}
			    index++;
				// from pageCount of first.
	        	row = sheet.createRow(index);
	        	// set next row.
	        	// i
	        	cell = row.createCell(0);
	        	cell.setCellStyle(style);
	        	cell.setCellValue(i+1);
	        	// number.
	        	cell = row.createCell(1);
	        	cell.setCellStyle(style);
	        	cell.setCellValue(list.get(i).getNumber());
	        	//linetype
	        	cell = row.createCell(2);
	        	cell.setCellStyle(style);
	        	cell.setCellValue((list.get(i).getLinetype() == 0) ? "自动" : (list.get(i).getLinetype() == 1) ? "供电" : "网关");
	        	// .priority
	        	cell = row.createCell(3);
	        	cell.setCellStyle(style);
	        	cell.setCellValue(list.get(i).getCall_priority_name());
	        	// .dnd
	        	cell = row.createCell(4);
	        	cell.setCellStyle(style);
	        	cell.setCellValue((list.get(i).getDnd()==null)?"":list.get(i).getDnd()?"是":"否");
	        	// Description.
	        	cell = row.createCell(5);
	        	cell.setCellStyle(style);
	        	cell.setCellValue(list.get(i).getDescription());
	        	
	        
        }
		workbook.write(out);
		
	}	
	/**
	 * method bak
	 * @param <T>
	 * @param list
	 * @param out
	 * @param tempPath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static <T> void exportSubscriber_bak(List list, OutputStream out,
			String tempPath) throws FileNotFoundException, IOException {

		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(tempPath));
		// excel 最多创建255个sheet
		// 遍历集合数据，产生数据行
		Iterator<T> it = list.iterator();
		HSSFRow row, tmprow;
		HSSFSheet sheet = null;
		int index = 0;
		tmprow = workbook.getSheetAt(0).getRow(1);
		HSSFCellStyle style = tmprow.getCell(0).getCellStyle();
		for (int j = 0; j < list.size(); j++) {
			if( j >6000) break;
			if (j % 100 == 0 || j == 0) {
				sheet = workbook.cloneSheet(0);
				index = 0;
				workbook.setSheetName((j / 100)+1, (j + 1) + "-" + (j + 100));
			}
			index++;
			row = sheet.createRow(index);

			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			HSSFCell cellseq = row.createCell(0);
			cellseq.setCellStyle(style);
			cellseq.setCellValue(index);
			for (short i = 0; i < fields.length-1; i++) {
				HSSFCell cell = row.createCell(i+1);
				cell.setCellStyle(style);
				Field field = fields[i];
				String fieldName = field.getName();
				
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				// System.out.println(getMethodName);
				if ("getFavorite".equals(getMethodName)
						|| "getCall_priority_name".equals(getMethodName)) {
					continue;
				}
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					cell.setCellValue("");
					if (value instanceof Boolean) {
						boolean bValue = (Boolean) value;
						textValue = bValue ? "是" : "否";
					} else {
						// 其它数据类型都当作字符串简单处理
						if (value != null) {
							textValue = value.toString();
						} else {
							textValue = "";
						}

					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
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
			}

		}
		workbook.removeSheetAt(0);
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void listToDateCell(List<RecordDetail> list, HSSFSheet sheet,
			HSSFCellStyle datacellCellStyle) {

		for (short i = 4; i < list.size() + 4; i++) {
			//if(i==200)break;
			HSSFRow row = sheet.createRow(i); // list在索引0的位置开始创建行(最顶端的行)

			RecordDetail recordDetail = (RecordDetail) list.get(i - 4);
			recordDetail.setId(i - 4 + 1);
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
					if ("getStartdate".equals(getMethodName)) {
						SimpleDateFormat sdf1 = new SimpleDateFormat(
								"yyyy-MM-dd");

						value = sdf1.format(value);
					}
					if ("getStarttime".equals(getMethodName)) {
						SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
						value = sdf1.format(value);
					}

					if (j == 4) {
						if (value == null) {
							textValue = "";
							SetCell(datacellCellStyle, row, cellindex, "");
						} else if (value.toString().trim().equals("1")) {
							textValue = "重要1";
						} else if (value.toString().trim().equals("2")) {
							textValue = "重要2";
						} else {
							textValue = "";
						}
						SetCell(datacellCellStyle, row, cellindex, textValue);
					} else if (j == 5) {
						if (value == null) {
							textValue = "";

						} else if (value.toString().trim().equals("1")) {
							textValue = "超时";
						} else {
							textValue = "";
						}
						SetCell(datacellCellStyle, row, cellindex, textValue);
					} else if (j == 6) {
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
						} else {
							SetCell(datacellCellStyle, row, cellindex, "");
							cellindex = cellindex + 1;
							SetCell(datacellCellStyle, row, cellindex, "");
						}
					} else if (j == 8) {
						if (value == null) {
							textValue = "";
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
						} else {
							SetCell(datacellCellStyle, row, cellindex, "");
							cellindex = cellindex + 1;
							SetCell(datacellCellStyle, row, cellindex, "");
						}
					} else if (j == 9) {
						if (value == null) {
							textValue = "";
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
						} else {
							SetCell(datacellCellStyle, row, cellindex, "");
							cellindex = cellindex + 1;
							SetCell(datacellCellStyle, row, cellindex, "");
						}
					} else if (j == 10) {
						if (value == null) {
							textValue = "";

						} else if (value.toString().trim().equals("2")) {
							textValue = "用于不佳";

						} else {
							textValue = "";
						}
						SetCell(datacellCellStyle, row, cellindex, textValue);
					} else if (j == 11) {
						if (value == null) {
							textValue = "";

						} else if (value.toString().trim().equals("1")) {
							textValue = "差";

						} else {
							textValue = "";
						}
						SetCell(datacellCellStyle, row, cellindex, textValue);
					} else if (j == fields.length - 1) {
						break;
					} else {
						if (value == null) {
							textValue = "";
						} else {
							// 其它数据类型都当作字符串简单处理
							textValue = value.toString();
						}
						SetCell(datacellCellStyle, row, cellindex, textValue);
					}
					cellindex++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
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
		// exportDetail(dataset1, "111");
	}
}
