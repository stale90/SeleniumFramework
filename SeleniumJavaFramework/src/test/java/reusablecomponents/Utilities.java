package reusablecomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import config.FrameworkException;
import config.Report;

public class Utilities {
	
	static Properties props = new Properties();
	static String configFileName = "config.properties";
	static XSSFWorkbook workbook = null;
	static XSSFSheet sheet = null;
	static XSSFRow row = null;

	/**
	 * Set Configuration parameter in Configuration file.
	 * 
	 * @param strKey
	 *            - Key to be written
	 * @param strValue
	 *            - Value for key @ in case of error
	 */
	public static void setProperty(String strKey, String strValue) {
		try {
			File file = new File(configFileName);
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				props.load(in);
				props.setProperty(strKey, strValue);
				props.store(new FileOutputStream(configFileName), null);
				in.close();
			} else {
				throw new FrameworkException("Configuration File not found at location.");
			}

		} catch (Exception e) {
			throw new FrameworkException("Unknown Error encountered while writing " + strKey
					+ " to configuration file. ---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Function to read configuration properties parameter from Configuration file.
	 * 
	 * @param strKey
	 *            - Configuration name
	 * @return - string value with configuration name, returns null in case
	 *         configuration parameter not found. @ in case of error.
	 */

	public static String getProperty(String strKey) {
		String strValue;
		try {
			File file = new File(configFileName);
			if (file.exists()) {
				FileInputStream in = new FileInputStream(file);
				props.load(in);
				strValue = props.getProperty(strKey);
				in.close();
			} else
				throw new FrameworkException("Configuration File not found at location.");

		} catch (Exception e) {
			throw new FrameworkException("Unknown Error encountered while reading " + strKey
					+ " from configuration file. ---" + e.getClass() + "---" + e.getMessage());
		}
		if (strValue != null) {
			return strValue;
		} else {
			throw new FrameworkException(
					"Value '" + strKey + "' not configured in config file. Contact automation team");
		}
	}

	/**
	 * Returns current date in MM/DD/YYYY format.
	 * 
	 * @return Returns current date. @ in case of error.
	 */
	public static String getCurrentDate() {
		int month, date, year;
		String[] Date = LocalDateTime.now().toString().split("T")[0].split("-");
		date = Integer.parseInt(Date[2]);
		month = Integer.parseInt(Date[1]);
		year = Integer.parseInt(Date[0]);
		return month + "/" + date + "/" + year;
	}

	/**
	 * Get timestamp im yyyymmdd_hhmmss format.
	 * 
	 * @param timeZone
	 *            - local or utc
	 * @return timestamp in desired format.
	 */
	public static String getTimeStamp(String timeZone) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		if (timeZone.toLowerCase().equals("utc")) {
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		} else {
			dateFormat.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
		}
		return dateFormat.format(new Date());
	}

	/**
	 * This function is defined to read content from spreadsheet. This will return
	 * content only and will not return headers.
	 * 
	 * @param fileName
	 *            - Complete path of file.
	 * @param sheetName
	 *            - Sheet from where data needs to be retrieved.
	 * @return The content from spreadsheet @ in case of error.
	 */
	public static String[][] Read_Excel(String fileName, String sheetName) {
		try {
			FileInputStream fileInputStream = new FileInputStream(new File(fileName));
			String content[][] = null;
			workbook = new XSSFWorkbook(fileInputStream);
			sheet = workbook.getSheet(sheetName);
			int rowNum = sheet.getLastRowNum();
			workbook.close();
			content = Read_Excel(fileName, sheetName, rowNum);
			return content;

		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for reading.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while reading " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while reading " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * This function is defined to read content from spreadsheet till particular row
	 * number. This will return content only and will not return headers.
	 * 
	 * @param fileName
	 *            - Complete path of file.
	 * @param sheetName
	 *            - Sheet from where data needs to be retrieved.
	 * @param rowNum
	 *            - RowNumber till data needs to be retrieved.
	 * @return The content from spreadsheet @ in case of error.
	 */
	public static String[][] Read_Excel(String fileName, String sheetName, int rowNum) {
		try {
			String content[][] = null;
			FileInputStream file = null;
			XSSFWorkbook workbook = null;
			XSSFSheet sheet = null;
			int colNum = 0;
			file = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
			colNum = sheet.getRow(0).getLastCellNum();
			content = new String[rowNum][colNum];

			for (int i = 0; i < rowNum; i++) {
				XSSFRow row = sheet.getRow(i + 1);
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = row.getCell(j);
					String value;
					if (cell != null) {
						value = cell.getStringCellValue();
						content[i][j] = value;
					} else {
						content[i][j] = "";
					}
				}
			}
			workbook.close();

			return content;
		} catch (FileNotFoundException e) {
			throw new FrameworkException("File " + fileName + " not found for reading.");
		} catch (IOException e) {
			throw new FrameworkException("Exception occured while reading " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while reading " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * 
	 * This is to write logs/messages in excel file.
	 * 
	 * @param fileName
	 *            - Complete path of file where error needs to be logged.
	 * @param sheetName
	 *            - Sheet Name from the file, where message needs to be logged.
	 * @param rowNum
	 *            - Row Number where message needs to be written.
	 * @param colNum
	 *            - Column Number where message needs to be written.
	 * @param Message
	 *            - Message/Log @ in case of error.
	 */
	public static void Write_Excel(String fileName, String sheetName, int rowNum, int colNum, String Message)
			throws FileNotFoundException, IOException {
		try {
			FileInputStream fileReadStream = new FileInputStream(new File(fileName));
			workbook = new XSSFWorkbook(fileReadStream);

			try {
				sheet = workbook.getSheet(sheetName);
			} catch (NullPointerException e) {
				sheet = workbook.createSheet(sheetName);
			}

			try {
				row = sheet.getRow(rowNum);
			} catch (NullPointerException e) {
				row = sheet.createRow(rowNum);
			}

			if (row == null) {
				row = sheet.createRow(rowNum);
			}
			row.createCell(colNum).setCellValue(Message);
			fileReadStream.close();

			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.flush();
			outFile.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
			FileOutputStream outFile = new FileOutputStream(new File(fileName));
			workbook.write(outFile);
			outFile.close();
			Write_Excel(fileName, sheetName, rowNum, colNum, Message);

		} catch (IOException e) {
			throw new FrameworkException("Exception occured while writing on " + fileName);
		} catch (Exception e) {
			throw new FrameworkException("Unknown Exception while writing on " + fileName + "&" + sheetName + "---"
					+ e.getClass() + "---" + e.getMessage());
		}
	}

	public static String randomNum() {
		return new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()).toString();
	}

	public static String getRandomEmailId() {
		try {
			return "at" + randomNum() + "@mailnesia.com";
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while framing random email---" + e.getClass() + "---" + e.getMessage());
		}
	}

	public static String formatInvoiceNumber(String invoiceNum) {
		try {
			if (invoiceNum.length() == 12) {
				invoiceNum = invoiceNum.substring(0, 7) + "-" + invoiceNum.substring(7, 11) + "-"
						+ invoiceNum.substring(11, 12);
			}
			return invoiceNum;
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while formating Invoice Number---" + e.getClass() + "---" + e.getMessage());
		}
	}

	public static String formatCustomerID(String cid) {
		try {
			while (cid.length() < 15) {
				cid = "0" + cid;
			}
			cid = cid.substring(0, 5) + "-" + cid.substring(5, 10) + "-" + cid.substring(10, cid.length());
			return cid;
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while formating Customer ID---" + e.getClass() + "---" + e.getMessage());
		}
	}

	public static String formatPhoneNo(String phone) {
		try {
			if (phone.length() == 10) {
				phone = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-"
						+ phone.substring(6, phone.length());

			}
			System.out.println("Phone : " + phone);
			return phone;
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while formating Phone number---" + e.getClass() + "---" + e.getMessage());
		}
	}


	public static String getRandomEmailId(String chars) {
		try {
			return "user" + chars + randomNum() + "@mailnesia.com";
		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while framing random email---" + e.getClass() + "---" + e.getMessage());
		}
	}

	/**
	 * Returns current date from dd MMM YYYY to MM/DD/YYYY format.
	 *
	 * @return Returns date. @ in case of error.
	 */

	public static String changeDateFormat(String scheduleddate) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
			Date date = sdf.parse(scheduleddate);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(date);

		} catch (Exception e) {
			throw new FrameworkException(
					"Unknown Exception while changing the format---" + e.getClass() + "---" + e.getMessage());
		}
	}


	public static int getMonthNumber(String monthName) {
		try {
			Date date = new SimpleDateFormat("MMM").parse(monthName);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
			Report.log("Something went wrong when finding number of Month: " + monthName + "----" + e.getMessage());
			return Calendar.getInstance().get(Calendar.MONTH) + 1;
		}
	}
	
}
