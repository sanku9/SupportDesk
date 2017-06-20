package com.supportdesk.action;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.supportdesk.common.Constants;
import com.supportdesk.common.Utility;
import com.supportdesk.entity.ChangeRequestEntity;
import com.supportdesk.entity.IncidentSlmEntity;
import com.supportdesk.entity.SnowIncidentEntity;
import com.supportdesk.entity.WorkOrderEntity;
import com.supportdesk.service.ChangeRequestService;
import com.supportdesk.service.IncidentService;
import com.supportdesk.service.SnowTicketService;
import com.supportdesk.service.WorkOrderService;
@WebListener
public class FileMonitorListener implements ServletContextListener  {

    public final void contextInitialized(final ServletContextEvent sce) {
    	final WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        
    	//ClassPathXmlApplicationContext applicationContext = null;
		try {
			//applicationContext = new ClassPathXmlApplicationContext("/spring.xml");
			IncidentService incidentService = applicationContext.getBean("incidentService", IncidentService.class);
			WorkOrderService workOrderService = applicationContext.getBean("workOrderService", WorkOrderService.class);
			ChangeRequestService changeRequestService = applicationContext.getBean("changeRequestService", ChangeRequestService.class);

			monitorIncident(incidentService);
			monitorWorkOrder(workOrderService);
			monitorChangeRequest(changeRequestService);
			
			SnowTicketService snowTicketService = applicationContext.getBean("snowTicketService", SnowTicketService.class);
			monitorIncident(snowTicketService, workOrderService);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/*if (applicationContext != null) {
				applicationContext.close();
			}*/
		}
    }

    public final void contextDestroyed(final ServletContextEvent sce) {

    }
    
    public void monitorIncident(final IncidentService incidentService) throws Exception {
        // The monitor will perform polling on the folder every 5 seconds
        final long pollingInterval = 5 * 1000;

        File folder = new File(Constants.UPLOAD_FOLDER_INCIDENT);

        if (!folder.exists()) {
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + Constants.UPLOAD_FOLDER_INCIDENT);
        }

        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor =
                new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
                try {
                    // "file" is the reference to the newly created file
                    System.out.println("File created: "
                            + file.getCanonicalPath());
                    String ext = FilenameUtils.getExtension(file.getCanonicalPath());
                    List<IncidentSlmEntity> parseFile = null;	
            		try {
            			parseFile = Utility.parseInputIncSLMFile(file, ext, incidentService.getApplicationList(), incidentService.getQueueList());
            			incidentService.uploadIncidentSLM(parseFile);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                    
            		file.renameTo(new File(file.getParent()+"\\Archive\\"+ file.getName() +"_"+Calendar.getInstance().getTimeInMillis()));
                    
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }

        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
    
    public void monitorWorkOrder(final WorkOrderService workOrderService) throws Exception {
        // The monitor will perform polling on the folder every 5 seconds
        final long pollingInterval = 5 * 1000;

        File folder = new File(Constants.UPLOAD_FOLDER_WO);

        if (!folder.exists()) {
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + Constants.UPLOAD_FOLDER_WO);
        }

        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor =
                new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
                try {
                    // "file" is the reference to the newly created file
                    System.out.println("File created: "
                            + file.getCanonicalPath());
                    String ext = FilenameUtils.getExtension(file.getCanonicalPath());
                    List<WorkOrderEntity> parseFile = null;	
            		try {
            			parseFile = Utility.parseInputWOFile(file, ext, workOrderService.getApplicationList(), workOrderService.getQueueList());
            			workOrderService.uploadWorkOrders(parseFile);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                    
                    file.renameTo(new File(file.getParent()+"\\Archive\\"+ file.getName() +"_"+Calendar.getInstance().getTimeInMillis()));
                    
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }

        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
    
    public void monitorChangeRequest(final ChangeRequestService changeRequestService) throws Exception {
        // The monitor will perform polling on the folder every 5 seconds
        final long pollingInterval = 5 * 1000;

        File folder = new File(Constants.UPLOAD_FOLDER_CR);

        if (!folder.exists()) {
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + Constants.UPLOAD_FOLDER_CR);
        }

        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor =
                new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
                try {
                    // "file" is the reference to the newly created file
                    System.out.println("File created: "
                            + file.getCanonicalPath());
                    String ext = FilenameUtils.getExtension(file.getCanonicalPath());
                    List<ChangeRequestEntity> parseFile = null;	
            		try {
            			parseFile = Utility.parseInputCRFile(file, ext, changeRequestService.getApplicationList(), changeRequestService.getQueueList());
            			changeRequestService.uploadChangeRequests(parseFile);
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                    
                    file.renameTo(new File(file.getParent()+"\\Archive\\"+ file.getName() +"_"+Calendar.getInstance().getTimeInMillis()));
                    
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }

        };
        

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
    

    public void monitorIncident(final SnowTicketService snowTicketService, WorkOrderService workOrderService) throws Exception {
        // The monitor will perform polling on the folder every 5 seconds
        final long pollingInterval = 5 * 1000;

        File folder = new File(Constants.UPLOAD_FOLDER_SNOW_TICKET);

        if (!folder.exists()) {
            // Test to see if monitored folder exists
            throw new RuntimeException("Directory not found: " + Constants.UPLOAD_FOLDER_SNOW_TICKET);
        }

        FileAlterationObserver observer = new FileAlterationObserver(folder);
        FileAlterationMonitor monitor =
                new FileAlterationMonitor(pollingInterval);
        FileAlterationListener listener = new FileAlterationListenerAdaptor() {
            // Is triggered when a file is created in the monitored folder
            @Override
            public void onFileCreate(File file) {
                try {
                    // "file" is the reference to the newly created file
                    System.out.println("File created: "
                            + file.getCanonicalPath());
                    String ext = FilenameUtils.getExtension(file.getCanonicalPath());
                    List<IncidentSlmEntity> incidentList = null;	
            		try {
            			Utility.parseInputSnowIncFile(file, ext, snowTicketService.getApplicationList(), snowTicketService.getQueueList());
            			snowTicketService.uploadIncidentSLM(Utility.getIncidentList());
            			workOrderService.uploadWorkOrders(Utility.getWorkOrderList());
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
                    
            		file.renameTo(new File(file.getParent()+"\\Archive\\"+ file.getName() +"_"+Calendar.getInstance().getTimeInMillis()));
                    
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }

        };

        observer.addListener(listener);
        monitor.addObserver(observer);
        monitor.start();
    }
}
