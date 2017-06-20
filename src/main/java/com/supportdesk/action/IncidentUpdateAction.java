package com.supportdesk.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.exception.ApplicationBaseException;
import com.supportdesk.service.IncidentService;

public class IncidentUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log    logger  = LogFactory.getLog(IncidentUpdateAction.class);
	List<IncidentSlmEntity> incidentList = new ArrayList<IncidentSlmEntity>();
	
	@Autowired
	IncidentService incidentService;
	
	private File incidentFile;
	
	private String fileExtension;
	
	private String status;
	
	private String commentId;	
	private String comments;	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<IncidentSlmEntity> getIncidentList() {
		return incidentList;
	}

	public void setIncidentList(List<IncidentSlmEntity> incidentList) {
		this.incidentList = incidentList;
	}

	public IncidentService getIncidentService() {
		return incidentService;
	}

	public void setIncidentService(IncidentService incidentService) {
		this.incidentService = incidentService;
	}
	
	public File getIncidentFile() {
		return incidentFile;
	}

	public void setIncidentFile(File incidentFile) {
		this.incidentFile = incidentFile;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	// Your result List
	protected List<IncidentSlmEntity>      gridModel;

	public List<IncidentSlmEntity> getGridModel() {
		return gridModel;
	}

	public void setGridModel(List<IncidentSlmEntity> gridModel) {
		this.gridModel = gridModel;
	}

	public String execute() throws ApplicationBaseException {
        return SUCCESS;
    }
	/*
	public IncidentEntity getModel() {
		// TODO Auto-generated method stub
		return incidentEntity;
	}*/
	
	public String incidentUpdate() {
		List<IncidentSlmEntity> parseIncList = null;	
		/*try {
			parseIncList = Utility.parseInputIncSLMFile(getIncidentFile(), getFileExtension(), incidentService.getApplicationList(), incidentService.getQueueList());
			incidentService.uploadIncidentSLM(parseIncList);
			//incidentService.runIncidentUpdate();
			//incidentService.runSLAUpdate();
			System.out.println("Reached..");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addActionError("Some Problem Occurred While Uploading File. Please Check The File Format and Data.");
			e.printStackTrace();
			return "success";
		}*/
		//System.out.println("List Size :"+parseFile.size());
		addActionMessage("Records Uploaded Successfully.");
		return "success";
	}
	
	public String updateIncidentComment() {
		incidentService.updateIncidentComment(commentId, comments);
		addActionMessage("Comment Updated Successfully.");
		return "success";
	}
}
