package com.supportdesk.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.supportdesk.domain.WorkOrderReport;


public class WOExcel2007Reader {

	private static final Logger LOGGER = LogManager.getLogger();

	/** Creates a new instance of POIExcelReader */
	public WOExcel2007Reader() {
	}

	/**
	 * This method is used to Read the Excel Content 2007 Format xlsx.
	 * 
	 * @param inputStream
	 */
	public List<WorkOrderReport> readExcelFile(File file) {
		/**
		 * --Define a List --Holds List Of Cells
		 */
		List<WorkOrderReport> workOrderReportList = new ArrayList<WorkOrderReport>();

		try {
			/** Creating Input Stream **/
			InputStream myInput = new FileInputStream(file);

			/** Create a workbook using the File System **/
			XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);

			/** Get the first sheet from workbook **/
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);

			/** We now need something to iterate through the cells. **/
			Iterator<Row> rowIter = mySheet.rowIterator();

			/** Counting the Line Number **/
			int rowNum = 0;
			Iterator<Cell> cellIter;
			XSSFCell myCell;
			WorkOrderReport workOrderReport = null;
			Map<String, String> workOrderMap = null;
			while (rowIter.hasNext()) {
				XSSFRow myRow = (XSSFRow) rowIter.next();
				rowNum = myRow.getRowNum();
				if (rowNum != 0) {
					cellIter = myRow.cellIterator();
					workOrderReport = new WorkOrderReport();
					workOrderMap = new HashMap<String, String>();
					while(cellIter.hasNext()) {
						myCell = (XSSFCell) cellIter.next();
						try {
							workOrderMap.put(Constants.workOrderReportMap.get(myCell.getColumnIndex()), getCellValue(myCell).trim().replaceAll("'", "''"));
						} catch(Exception e)
						{ 
							e.printStackTrace();
							LOGGER.error(e.getMessage());
						}
					}	
					BeanWrapper wrapper = new BeanWrapperImpl(WorkOrderReport.class);
					wrapper.setPropertyValues(workOrderMap);
					workOrderReport = (WorkOrderReport) wrapper.getWrappedInstance();
					workOrderReportList.add(workOrderReport);
				}
			}
			

		} catch (IllegalArgumentException illegalArgumentException) { 
			illegalArgumentException.printStackTrace();
			LOGGER.error(illegalArgumentException.getMessage());

		} catch (IOException io) {
			io.printStackTrace();
			LOGGER.error(io.getMessage());
		}
		catch (IndexOutOfBoundsException indexoutofbound) {
			indexoutofbound.printStackTrace();
			LOGGER.error(indexoutofbound.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return workOrderReportList;
	}

	private String getCellValue(XSSFCell cell) {
		/** if loop call if cell type is numeric **/
		String returnVal = null;

		try {
			if (XSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				 if (DateUtil.isCellDateFormatted(cell)) {
	                 Date date = cell.getDateCellValue();
	                 SimpleDateFormat dateFormat= new SimpleDateFormat(Constants.REMEDY_UPLOAD_TIMEFORMAT);
	                 returnVal=dateFormat.format(date);
	             } 
				 else{
					returnVal = String.valueOf(cell.getNumericCellValue());
					/** convert 1.0 value to 1 as file reader class read 1 as 1.0 **/
					double aDouble = Double.parseDouble(returnVal);
					double db = Math.round(aDouble);
					int i = (int) db;
					String aString = Integer.toString(i);
					int intpublicflag = Integer.parseInt(aString);
					Integer ia = intpublicflag;
					String strpublic = ia.toString();
					returnVal = strpublic;
				 }
				/** else if loop call if cell type is String **/
			} else if (XSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
				returnVal = cell.getStringCellValue();
				/** else if loop call if cell type is BOOLEAN **/
			} else if (XSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
				returnVal = String.valueOf(cell.getBooleanCellValue());
				/** else if loop call if cell type is BLANK **/
			} else if (XSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
				returnVal = "";
			} else {
				returnVal = String.valueOf(cell);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;

	}

}
