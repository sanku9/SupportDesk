package com.supportdesk.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.supportdesk.entity.CommentEntity;
import com.supportdesk.entity.IncidentSlmEntity;

public class IncidentExcelWriter {
	
	public Sheet writeIncident(Workbook workbook, Sheet sheet, List<IncidentSlmEntity> listIncident) throws IOException {
		if (sheet == null) {
			sheet = workbook.createSheet("Status Report");
		}
		
		createHeaderRow(sheet);
		
		int rowCount = sheet.getLastRowNum();
		
		if (listIncident == null)
			listIncident = new ArrayList<IncidentSlmEntity>();
		
		for (IncidentSlmEntity incident : listIncident) {
			Row row = sheet.createRow(++rowCount);
			writeIncident(incident, row, sheet, getHeaderStringList().size());
		}
		
		sheet.createRow(++rowCount);
		sheet.createRow(++rowCount);
		
		return sheet;
	}
	
	private void writeIncident(IncidentSlmEntity incident, Row row, Sheet sheet, int columnSize) {
		
		int column = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		
	    Cell cell = row.createCell(column++);
	    cell.setCellValue(incident.getIncidentId());
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
    	cell.setCellValue(incident.getApplicationId() != null ? incident.getApplicationId().getApplName():"");
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
	    cell.setCellValue(incident.getIncidentSummary());
	    cellStyle.setWrapText(true); //Set wordwrap
	    cell.setCellStyle(cellStyle);
	    
	    cell = row.createCell(column++);
	    cell.setCellValue(incident.getIncidentStatus());
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
	    cell.setCellValue(incident.getPriority());
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
	    cell.setCellValue(sdf.format(incident.getReportedDate()));
	    cell.setCellStyle(cellStyle);
	    
	    cell = row.createCell(column++);
	    if (incident.getResolveSlaDueDate() != null)
	    	cell.setCellValue(sdf.format(incident.getResolveSlaDueDate()));
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
	    cell.setCellValue(incident.getAssigneeName());
	    cell.setCellStyle(cellStyle);
	 
	    cell = row.createCell(column++);
	    cell.setCellValue(incident.getSlaBreachFlag());
	    cell.setCellStyle(cellStyle);
	    

	    String comment = "";
	    if (incident.getCommentEntityCollection() != null) {
	    	List<CommentEntity> commentList = new ArrayList<CommentEntity>(incident.getCommentEntityCollection());
	    	Collections.sort(commentList, new Comparator<CommentEntity>()
	    			{
	    				public int compare(CommentEntity b, CommentEntity a)
	    				{
	    					if (a == null)
	    						return -1;
	    					else if (b == null)
	    						return 1;
	    					else
	    						return a.getCreatedDate().compareTo(b.getCreatedDate());

	    				}
	    			});
	    	if(commentList.size() > 0) {
	    		comment = commentList.get(0).getComment();
	    	}
	    }
	    
	    cell = row.createCell(column++);
	    cell.setCellValue(comment);
	    cell.setCellStyle(cellStyle);
	    
	    for( int i = 0; i< columnSize; i++) {
	    	if (i == 2) {
	    		sheet.setColumnWidth(2, 10000);
	    	} else {
	    		sheet.autoSizeColumn(i);
	    	}
	    }
	}
	
	private void createHeaderRow(Sheet sheet) {
		 
		List<String> headerList = getHeaderStringList();
		
	    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	    Font font = sheet.getWorkbook().createFont();
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	    font.setFontHeightInPoints((short) 12);
	    font.setColor(HSSFColor.WHITE.index);
	    cellStyle.setFont(font);

	    //sheet.addMergedRegion(org.apache.poi.ss.util.CellRangeAddress.valueOf("$A$1:$J$1"));
	    sheet.addMergedRegion(new CellRangeAddress(
	    		sheet.getLastRowNum() + 1, //first row (0-based)
	    		sheet.getLastRowNum() + 1, //last row  (0-based)
	    		0, //first column (0-based)
	    		getHeaderStringList().size() - 1  //last column  (0-based
	    ));

	    Row row0 = sheet.createRow(sheet.getLastRowNum()+1);
	    Cell cellTitle = row0.createCell(0);
    	cellTitle.setCellValue("Incident Tickets");
    	cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setBorderBottom((short) 1);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellTitle.setCellStyle(cellStyle);
		
		
		
	    Row row = sheet.createRow(sheet.getLastRowNum()+1);
	    
	    for (int i = 0; i < headerList.size(); i++) {
	    	cellTitle = row.createCell(i);
	    	cellTitle.setCellValue(headerList.get(i));
	    	
			cellStyle.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setBorderLeft((short) 1);
			cellStyle.setBorderRight((short) 1);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
			cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
			cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
			cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
			cellTitle.setCellStyle(cellStyle);
		}
	}
	
	public List<String> getHeaderStringList() {
		final List<String> strListHeader = new ArrayList<String>();
		strListHeader.add("Incident ID");
		strListHeader.add("Applciation Name");
		strListHeader.add("Summary");
		strListHeader.add("Status");
		strListHeader.add("Priority");
		strListHeader.add("Reported Date");
		strListHeader.add("Target Date (SLM date)");
		strListHeader.add("Assignee");
		strListHeader.add("SLA Breached");
		strListHeader.add("Comments");

		return strListHeader;
	}
}
