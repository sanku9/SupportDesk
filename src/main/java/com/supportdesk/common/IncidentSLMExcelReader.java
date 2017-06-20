package com.supportdesk.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.supportdesk.domain.IncidentReport;


public class IncidentSLMExcelReader {
	private static final Logger LOGGER = LogManager.getLogger();

	/** Creates a new instance of POIExcelReader */
	public IncidentSLMExcelReader() {
	}
	/**
	 * This method is used to Read the Excel Content.
	 * 
	 * @param inputStream
	 */
	public List<IncidentReport> readExcelFile(File file)
	{
		/**
		 * --Define a List --Holds List Of Cells
		 */
		List<IncidentReport> incidentReportList = new ArrayList<IncidentReport>();
		try {
			/** Creating Input Stream **/
			FileInputStream myInput = new FileInputStream(file);

			/** Create a POIFSFileSystem object **/
			POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

			/** Create a workbook using the File System **/
			HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

			/** Get the first sheet from workbook **/
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);

			/** We now need something to iterate through the cells. **/
			Iterator<Row> rowIter = mySheet.rowIterator();

			/** Counting the Line Number **/
			int rowNum = 0;
			Iterator<Cell> cellIter;
			HSSFCell myCell;
			IncidentReport incidentReport = null;
			Map<String, String> incidentMap = null;
			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				rowNum = myRow.getRowNum();
				//System.out.println("RowNum :"+rowNum);
				if (rowNum != 0) {

					cellIter = myRow.cellIterator();

					incidentReport = new IncidentReport();
					incidentMap = new HashMap<String, String>();
					while(cellIter.hasNext()) {
						myCell = (HSSFCell) cellIter.next();
						try {
							incidentMap.put(Constants.incidentReportMap.get(myCell.getColumnIndex()), getCellValue(myCell).trim().replaceAll("'", "''"));
						} catch(Exception e)
						{ 
							e.printStackTrace();
							LOGGER.error(e.getMessage());
						}
					}	
					BeanWrapper wrapper = new BeanWrapperImpl(IncidentReport.class);
					wrapper.setPropertyValues(incidentMap);
					incidentReport = (IncidentReport) wrapper.getWrappedInstance();
					//System.out.println(incidentReport);
					incidentReportList.add(incidentReport);
				} 
				
			}

		} catch (IllegalArgumentException illegalArgumentException) {
			LOGGER.error(illegalArgumentException.getMessage());
			illegalArgumentException.printStackTrace();
			illegalArgumentException.printStackTrace();
			throw illegalArgumentException;    
		} 
		catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}

		catch(IndexOutOfBoundsException indexoutofbound)
		{			
			LOGGER.error(indexoutofbound.getMessage());
			indexoutofbound.printStackTrace();
			throw indexoutofbound;				
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}

		System.out.println("incidentReportList Size"+incidentReportList.size());

		return incidentReportList;
	}

	private String getCellValue(HSSFCell cell) {
		String returnVal = null;
		try{
			/**if loop call if cell type is numeric **/
			if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					SimpleDateFormat dateFormat= new SimpleDateFormat(Constants.REMEDY_UPLOAD_TIMEFORMAT);
					returnVal=dateFormat.format(date);
				} 
				else{
					//System.out.println("Type Number");
					returnVal = String.valueOf(cell.getNumericCellValue());
					/**convert 1.0 value to 1 as file reader class read 1 as 1.0 **/	
					double aDouble = Double.parseDouble(returnVal);
					double db = Math.round(aDouble);
					int i = (int) db;
					String aString = Integer.toString(i);
					int intpublicflag = Integer.parseInt(aString);
					Integer ia = intpublicflag;
					String strpublic = ia.toString();
					returnVal=strpublic;
				}
				/** else if loop call if cell type is String **/
			} else if (HSSFCell.CELL_TYPE_STRING == cell.getCellType()) {
				//System.out.println("Type Strng");
				returnVal = cell.getStringCellValue();

				/**else if loop call if cell type is BOOLEAN **/	
			} else if (HSSFCell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
				//System.out.println("Type Boolean");
				returnVal = String.valueOf(cell.getBooleanCellValue());
			} 
			/**else if loop call if cell type is BLANK **/	
			else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
				//System.out.println("Type Blank");
				returnVal = "";
			} 
			/*else if(HSSFDateUtil.isCellDateFormatted(cell)){
			CellValue cValue = formulaEv.evaluate(cell);
			double dv = cValue.getNumberValue();
			Date date = HSSFDateUtil.getJavaDate(dv);

		    String dateFmt = cell.getCellStyle().getDataFormatString();
		    //strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as 
		    //Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
		    //will always be 00 for date since "m" is minutes of the hour.

		    strValue = new CellDateFormatter(dateFmt).format(date); 

		}*/
			else {
				returnVal = String.valueOf(cell);
			}				
		}
		catch(Exception e)
		{
			LOGGER.error(e.getMessage());
		}
		return returnVal;
	}


}
