package utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.area.dto.FeeMode;

/**
 * @描述：测试excel读取 导入的jar包
 *               <p>
 *               poi-3.8-beta3-20110606.jar
 *               <p>
 *               poi-ooxml-3.8-beta3-20110606.jar
 *               <p>
 *               poi-examples-3.8-beta3-20110606.jar
 *               <p>
 *               poi-excelant-3.8-beta3-20110606.jar
 *               <p>
 *               poi-ooxml-schemas-3.8-beta3-20110606.jar
 *               <p>
 *               poi-scratchpad-3.8-beta3-20110606.jar
 *               <p>
 *               xmlbeans-2.3.0.jar
 *               <p>
 *               dom4j-1.6.1.jar
 *               <p>
 *               jar包官网下载地址：http://poi.apache.org/download.html
 *               <p>
 *               下载poi-bin-3.8-beta3-20110606.zipp
 * @作者：建宁
 * @时间：2012-08-29 下午16:27:15
 */

public class ImportExecl {

	/**
	 * 总行数
	 */

	private int		totalRows	= 0;

	/**
	 * 总列数
	 */

	private int		totalCells	= 0;

	/**
	 * 错误信息
	 */

	private String	errorInfo;

	/**
	 * 构造方法
	 */

	public ImportExecl() {

	}

	/**
	 * @描述：得到总行数
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:27:15
	 * @参数：@return
	 * @返回值：int
	 */

	public int getTotalRows() {

		return totalRows;

	}

	/**
	 * @描述：得到总列数
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:27:15
	 * @参数：@return
	 * @返回值：int
	 */

	public int getTotalCells() {

		return totalCells;

	}

	/**
	 * @描述：得到错误信息
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:27:15
	 * @参数：@return
	 * @返回值：String
	 */

	public String getErrorInfo() {

		return errorInfo;

	}

	/**
	 * @描述：验证excel文件
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:27:15
	 * @参数：@param filePath　文件完整路径
	 * @参数：@return
	 * @返回值：boolean
	 */

	public boolean validateExcel(String filePath) {

		/** 检查文件名是否为空或者是否是Excel格式的文件 */

		if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))) {

			errorInfo = "文件名不是excel格式";

			return false;

		}

		/** 检查文件是否存在 */

		File file = new File(filePath);

		if (file == null || !file.exists()) {

			errorInfo = "文件不存在";

			return false;

		}

		return true;

	}

	/**
	 * @描述：根据文件名读取excel文件
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:27:15
	 * @参数：@param filePath 文件完整路径
	 * @参数：@return
	 * @返回值：List
	 */

	public List<List<String>> read(String filePath) {

		List<List<String>> dataLst = new ArrayList<List<String>>();

		InputStream is = null;

		try {

			/** 验证文件是否合法 */

			if (!validateExcel(filePath)) {

				System.out.println(errorInfo);

				return null;

			}

			/** 判断文件的类型，是2003还是2007 */

			boolean isExcel2003 = true;

			if (WDWUtil.isExcel2007(filePath)) {

				isExcel2003 = false;

			}

			/** 调用本类提供的根据流读取的方法 */

			File file = new File(filePath);

			is = new FileInputStream(file);

			dataLst = read(is, isExcel2003);

			is.close();

		} catch (Exception ex) {

			ex.printStackTrace();

		} finally {

			if (is != null) {

				try {

					is.close();

				} catch (IOException e) {

					is = null;

					e.printStackTrace();

				}

			}

		}

		/** 返回最后读取的结果 */

		return dataLst;

	}

	/**
	 * @描述：根据流读取Excel文件
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:40:15
	 * @参数：@param inputStream
	 * @参数：@param isExcel2003
	 * @参数：@return
	 * @返回值：List
	 */

	public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {

		List<List<String>> dataLst = null;

		try {

			/** 根据版本选择创建Workbook的方式 */

			Workbook wb = null;

			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb);

		} catch (IOException e) {

			e.printStackTrace();

		}

		return dataLst;

	}

	/**
	 * @描述：读取数据
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:50:15
	 * @参数：@param Workbook
	 * @参数：@return
	 * @返回值：List<List<String>>
	 */

	private List<List<String>> read(Workbook wb) {

		List<List<String>> dataLst = new ArrayList<List<String>>();

		/** 得到第一个shell */

		Sheet sheet = wb.getSheetAt(0);

		/** 得到Excel的行数 */

		this.totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数 */

		if (this.totalRows >= 1 && sheet.getRow(0) != null) {

			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();

		}

		/** 循环Excel的行 */

		for (int r = 0; r < this.totalRows; r++) {

			Row row = sheet.getRow(r);

			if (row == null) {

				continue;

			}

			List<String> rowLst = new ArrayList<String>();

			/** 循环Excel的列 */

			for (int c = 0; c < this.getTotalCells(); c++) {

				Cell cell = row.getCell(c);

				String cellValue = "";

				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}

				rowLst.add(cellValue);

			}

			/** 保存第r行的第c列 */

			dataLst.add(rowLst);

		}

		return dataLst;

	}

	/**
	 * @描述：main测试方法
	 * @作者：建宁
	 * @时间：2012-08-29 下午17:12:15
	 * @参数：@param args
	 * @参数：@throws Exception
	 * @返回值：void
	 */

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		ImportExecl poi = new ImportExecl();
		List<List<String>> list = poi.read("C:\\Users\\miaoqiang\\Desktop\\复星联合/job.xlsx");
		// 解析职业
		poi.getJobInfo(list);
		// 解析费率
		// poi.getSXFee(list);
		// 解析最低保费条件
		// poi.getSXLimitPriceCond(list);
		// 获取满足条件的最低保费
		// poi.getSXLimitPrice(list);
	}

	// 解析寿险费率EXCEL表格，获取费率字典
	public void getSXFee(List<List<String>> list) {
		if (list != null) {
			System.out.println("    trial_data_tb: [");
			Map<String, FeeMode> fees = new HashMap<String, FeeMode>();
			// 遍历行
			for (int i = 0; i < 20; i++) {
				if (i > 1) {
					List<String> cellList = list.get(i);
					// 遍历列
					for (int j = 1; j < cellList.size();) {
						if (cellList.get(j).equals("")) {
							j = j + 3;
							continue;
						} else {
							FeeMode fm = new FeeMode();
							fm.setAge((int) Double.parseDouble(cellList.get(0)));
							fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
							if (j < 13) {
								fm.setSex(Short.valueOf("1"));
							} else {
								fm.setSex(Short.valueOf("0"));
							}
							fm.setMainFee(Double.parseDouble(cellList.get(j)));
							fm.setFee(Double.parseDouble(cellList.get(j + 1)));
							fm.setSubFee(Double.parseDouble(cellList.get(j + 2)));
							// fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());

							// 打印费率数据字典
							System.out.print("        {\"age\": \"" + fm.getAge() + "\",");
							System.out.print("  \"sex\": \"" + fm.getSex() + "\",");
							System.out.print("  \"PayPeriod\": \"" + fm.getPayPeriod() + "\",");
							// System.out.print("  \"insurPeriod\": \"" + fm.getInsurPeriod() + "\",");
							System.out.print("  \"mainFee\": \"" + fm.getMainFee() + "\",");
							System.out.print("  \"fee\": \"" + fm.getFee() + "\",");
							System.out.print("  \"subFee\": \"" + fm.getSubFee() + "\"");
							if (fm.getAge() == 17 && fm.getSex() == 0 && fm.getPayPeriod().equals("1")
									&& fm.getFee() == 467) {
								System.out.println("}");
							} else {
								System.out.println("},");
							}
							j = j + 3;
						}
					}
				}
			}
			System.out.println("    ],");
		}
	}

	// 解析寿险费率EXCEL表格，获取最低保费
	public void getSXLimitPrice(List<List<String>> list) throws CloneNotSupportedException {
		if (list != null) {
			List<FeeMode> fees = new ArrayList<FeeMode>();
			// 遍历行
			for (int i = 0; i < 20; i++) {
				if (i > 1) {
					List<String> cellList = list.get(i);
					// 遍历列
					for (int j = 1; j < cellList.size(); j++) {
						if (cellList.get(j).equals("")) {
							continue;
						} else {
							FeeMode fm = new FeeMode();
							fm.setAge((int) Double.parseDouble(cellList.get(0)));
							fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(1).get(j)))).toString());
							if (j < 7) {
								fm.setSex(Short.valueOf("1"));
							} else {
								fm.setSex(Short.valueOf("0"));
							}
							fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
							fm.setMainFee(Double.parseDouble(cellList.get(j)));
							fm.setSubFee(Double.parseDouble(cellList.get(j + 1)));

							// 保额
							// int amount5 = 5;
							int amount10 = 10 * 10000;
							int amount20 = 20 * 10000;
							int amount30 = 30 * 10000;
							FeeMode fm10 = (FeeMode) fm.clone();
							FeeMode fm20 = (FeeMode) fm.clone();
							FeeMode fm30 = (FeeMode) fm.clone();
							// 保费
							// Long amount = Math.round(amount5 * 10000 * fm.getSubFee() / 1000
							// / (1 - fm.getMainFee() / 1000));
							// Double price5 = ((Long) Math.round((amount * fm.getMainFee() / 1000 + fm.getSubFee()
							// * amount5 * 10) * 100)).doubleValue() / 100;
							Long mount10 = Math.round(amount10 * fm10.getSubFee() / 1000
									/ (1 - fm10.getMainFee() / 1000));
							Double price10 = ((Long) Math.round((mount10 * fm10.getMainFee() / 1000 + fm10.getSubFee()
									* amount10 / 1000) * 100)).doubleValue() / 100;
							Long mount20 = Math.round(amount20 * fm20.getSubFee() / 1000
									/ (1 - fm20.getMainFee() / 1000));
							Double price20 = ((Long) Math.round((mount20 * fm20.getMainFee() / 1000 + fm20.getSubFee()
									* amount20 / 1000) * 100)).doubleValue() / 100;
							Long mount30 = Math.round(amount30 * fm30.getSubFee() / 1000
									/ (1 - fm30.getMainFee() / 1000));
							Double price30 = ((Long) Math.round((mount30 * fm30.getMainFee() / 1000 + fm30.getSubFee()
									* amount30 / 1000) * 100)).doubleValue() / 100;
							// if (fm.getPayPeriod().equals("1")) {
							// this.findFeePrice(fees, fm, amount5, price5, "500");
							this.findFeePrice(fees, fm10, amount10, price10, "500");
							this.findFeePrice(fees, fm20, amount20, price20, "500");
							this.findFeePrice(fees, fm30, amount30, price30, "500");
							// } else {
							// this.findFeePrice(fees, fm, amount5, price5, "200");
							// this.findFeePrice(fees, fm, amount10, price10, "200");
							// this.findFeePrice(fees, fm, amount20, price20, "200");
							// this.findFeePrice(fees, fm, amount30, price30, "200");
							// }
							j++;
						}
					}
				}
			}
			if (fees != null) {
				Collections.sort(fees, new Comparator<FeeMode>() {
					@Override
					public int compare(FeeMode o1, FeeMode o2) {
						Double power1 = o1.getPrice();
						Double power2 = o2.getPrice();
						return power1.compareTo(power2);
					}
				});
			}
			for (FeeMode f : fees) {
				System.out.println("保费：" + f.toString());
			}

			// System.out.println("最低保费：" + fees.get(0).toString());
			// System.out.println("最高保费：" + fees.get(fees.size() - 1).toString());
		}
	}

	// 解析寿险费率EXCEL表格，获取最低保费条件
	public void getSXLimitPriceCond(List<List<String>> list) {
		if (list != null) {
			System.out.println("    trial_data_tb: [");
			Map<String, FeeMode> fees = new HashMap<String, FeeMode>();
			// 遍历行
			for (int i = 0; i < 20; i++) {
				if (i > 1) {
					List<String> cellList = list.get(i);
					// 遍历列
					for (int j = 1; j < cellList.size(); j++) {
						if (cellList.get(j).equals("")) {
							continue;
						} else {
							FeeMode fm = new FeeMode();
							fm.setAge((int) Double.parseDouble(cellList.get(0)));
							fm.setPayPeriod(((Integer) ((int) Double.parseDouble(list.get(1).get(j)))).toString());
							if (j < 7) {
								fm.setSex(Short.valueOf("1"));
							} else {
								fm.setSex(Short.valueOf("0"));
							}
							fm.setInsurPeriod(((Integer) ((int) Double.parseDouble(list.get(0).get(j)))).toString());
							fm.setMainFee(Double.parseDouble(cellList.get(j)));
							fm.setSubFee(Double.parseDouble(cellList.get(j + 1)));

							// 打印保费计算额度
							Long amount = Math.round(10 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
							Double fee10 = ((Long) Math
									.round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 10 * 10) * 100))
									.doubleValue() / 100;
							amount = Math.round(20 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
							Double fee20 = ((Long) Math
									.round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 20 * 10) * 100))
									.doubleValue() / 100;
							amount = Math.round(30 * 10000 * fm.getSubFee() / 1000 / (1 - fm.getMainFee() / 1000));
							Double fee30 = ((Long) Math
									.round((amount * fm.getMainFee() / 1000 + fm.getSubFee() * 30 * 10) * 100))
									.doubleValue() / 100;

							this.findLessFee(fm, fee10, "10", fees, "500");
							this.findLessFee(fm, fee20, "20", fees, "500");
							this.findLessFee(fm, fee30, "30", fees, "500");
							j++;
						}
					}
				}
			}
			if (fees != null) {
				Iterator<Map.Entry<String, FeeMode>> errorEntryIter = fees.entrySet().iterator();
				while (errorEntryIter.hasNext()) {
					Map.Entry<String, FeeMode> errorEntry = errorEntryIter.next();
					FeeMode feeMode = errorEntry.getValue();
					if (feeMode.getAge() != 18) {
						String amount = errorEntry.getKey().split(":")[4];
						System.out.print("        {\"age\": \"" + feeMode.getAge() + "\",");
						System.out.print("  \"sex\": \"" + feeMode.getSex() + "\",");
						System.out.print("  \"PayPeriod\": \"" + feeMode.getPayPeriod() + "\",");
						System.out.print("  \"insurPeriod\": \"" + feeMode.getInsurPeriod() + "\",");
						System.out.println("  \"amount\": \"" + amount + "\"},");
					}

				}
			}
			System.out.println("    ],");
		}
	}

	// 解析寿险职业EXCEL表格
	public void getJobInfo(List<List<String>> list) {
		if (list != null) {
			Long loopfirst = 1l;
			Long loopsecond = 1l;
			Long loopthird = 1l;
			Long comId = 17828543L;
			Long comCode = 873000L;
			Long firstId = 0l;
			Long secondId = 0l;
			Long thirdId = 0l;
			String tableName = "insert into ins.ins_job_info (JOB_ID, PARENT_JOB_ID, INS_COM_ID, NAME, JOB_CODE, GRADE, REMARK, LEAT, WEIGHT, STATUS, ADDER_NO, UPDATER_NO, ADDER_NAME, UPDATER_NAME, ADD_TIME, UPDATE_TIME) values (";
			System.out.println("set define off;");
			System.out.println("");
			for (int i = 0; i < list.size(); i++) {
				if (i >= 1) {
					List<String> cellList = list.get(i);
					for (int j = 0; j < cellList.size(); j++) {
						if (j >= 0) {
							if (cellList.get(j).equals(""))
								continue;
							else {
								if (j == 0) {
									firstId = (comCode * 100 + loopfirst) * 100000;
									System.out.println(tableName + firstId + ",0" + "," + comId + ",'"
											+ cellList.get(j).substring(2) + "','" + cellList.get(j).substring(0, 2)
											+ "',99" + ",'" + "',0" + ",1" + ",1" + ",0" + ",0" + ",'admin'"
											+ ",'admin'" + ",sysdate" + ",sysdate);");
									// }
									loopsecond = 1l;
									loopfirst++;
								} else if (j == 1) {
									secondId = (firstId / 1000 + loopsecond) * 1000;
									System.out.println(tableName + secondId + "," + firstId + "," + comId + ",'"
											+ cellList.get(j).substring(4) + "','" + cellList.get(j).substring(0, 4)
											+ "',99" + ",'" + "',0" + ",1" + ",1" + ",0" + ",0" + ",'admin'"
											+ ",'admin'" + ",sysdate" + ",sysdate);");
									loopthird = 1l;
									loopsecond++;
								} else if (j == 2) {
									thirdId = secondId + loopthird;
									System.out.println(tableName + thirdId + "," + secondId + "," + comId + ",'"
											+ cellList.get(3) + "','" + cellList.get(j) + "',"
											+ (int) Double.parseDouble(cellList.get(4))
											+ ",'',1,1,1,0,0,'admin','admin',sysdate,sysdate);");
									loopthird++;
								}
							}
						}
					}
				}
			}
			System.out.println("");
			System.out.println("commit;");
		}
	}

	// 解析保费条件
	public void findLessFee(FeeMode fm, Double fee, String feeValue, Map<String, FeeMode> fees, String limit) {
		if (fm.getPayPeriod().equals("1") && fee.compareTo(Double.parseDouble(limit)) >= 0) {
			FeeMode value = fees.get("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":"
					+ fm.getPayPeriod() + ":" + feeValue);
			if (value != null) {
				Integer age = value.getAge();
				if (age.compareTo(fm.getAge()) > 0) {
					fees.put("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
							+ feeValue, fm);
				}
			} else {
				fees.put("a:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
						+ feeValue, fm);
			}
		} else if (!fm.getPayPeriod().equals("1") && fee.compareTo(Double.parseDouble(limit)) >= 0) {
			FeeMode value = fees.get("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":"
					+ fm.getPayPeriod() + ":" + feeValue);
			if (value != null) {
				Integer age = value.getAge();
				if (age.compareTo(fm.getAge()) > 0) {
					fees.put("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
							+ feeValue, fm);
				}
			} else {
				fees.put("b:" + fm.getSex().toString() + ":" + fm.getInsurPeriod() + ":" + fm.getPayPeriod() + ":"
						+ feeValue, fm);
			}
		}
	}

	// 解析保费
	public void findFeePrice(List<FeeMode> fees, FeeMode fm, int amount, Double price, String limit) {
		if (price.compareTo(Double.parseDouble(limit)) >= 0) {
			fm.setPrice(price);
			fm.setAmount(amount);
			fees.add(fm);
		}
	}
}

/**
 * @描述：工具类
 * @作者：建宁
 * @时间：2012-08-29 下午16:30:40
 */

class WDWUtil {

	/**
	 * @描述：是否是2003的excel，返回true是2003
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:29:11
	 * @参数：@param filePath　文件完整路径
	 * @参数：@return
	 * @返回值：boolean
	 */

	public static boolean isExcel2003(String filePath) {

		return filePath.matches("^.+\\.(?i)(xls)$");

	}

	/**
	 * @描述：是否是2007的excel，返回true是2007
	 * @作者：建宁
	 * @时间：2012-08-29 下午16:28:20
	 * @参数：@param filePath　文件完整路径
	 * @参数：@return
	 * @返回值：boolean
	 */

	public static boolean isExcel2007(String filePath) {

		return filePath.matches("^.+\\.(?i)(xlsx)$");

	}

}
