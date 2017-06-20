package com.supportdesk.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.supportdesk.entity.QueueEntity;
import com.supportdesk.exception.ApplicationExceptionHandler;
import com.supportdesk.exception.ApplicationServiceException;
import com.supportdesk.service.QueueService;

public class QueueUpdateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private QueueEntity queueEntity = new QueueEntity();
	private String oper;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}
	public QueueEntity getQueueEntity() {
		return queueEntity;
	}

	public void setQueueEntity(QueueEntity user) {
		this.queueEntity = user;
	}

	@Autowired
	QueueService queueService;
	
	public QueueService getQueueService() {
		return queueService;
	}

	public void setQueueService(QueueService queueService) {
		this.queueService = queueService;
	}

	public QueueEntity getModel() {
		return queueEntity;
	}
	
	public String updateQueue() throws ApplicationServiceException {

		final ApplicationExceptionHandler exHandler = new ApplicationExceptionHandler();
		
		String status = null;
		try {
			
			if (getOper()!=null) {
				
				if(getOper().equalsIgnoreCase("del")) {
					if (queueEntity.getQueueId() == null) 
						queueEntity.setQueueId(Long.parseLong(getId()));
					queueService.delete(queueEntity);
				} else if (getOper().equalsIgnoreCase("edit")) {		
					if (queueEntity.getQueueId() == null) 
						queueEntity.setQueueId(Long.parseLong(getId()));
					queueService.update(queueEntity);
				} else if (getOper().equalsIgnoreCase("add")) {
					queueService.addUser(queueEntity);
				}
				status = SUCCESS;
			}
			
		}

		catch (Exception e) {
			exHandler.logErrors(e);
			status = ERROR;
			//throw new ApplicationServiceException(e.getMessage(),e.getMessage(),e) ;
		}
		
		return status;

	}
}
