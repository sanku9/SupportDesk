package com.supportdesk.action;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class EmailSchedulerListener implements ServletContextListener  {

    public final void contextInitialized(final ServletContextEvent sce) {
    	/*JobDetail job = JobBuilder.newJob(EmailJob.class)
    			.withIdentity("StatusReport", "group1").build();

    		try {

    			Trigger trigger = TriggerBuilder
    			  .newTrigger()
    			  .withIdentity("anyTriggerName", "group1")
    			  //.withSchedule(CronScheduleBuilder.cronSchedule("0 30 10-18 ? * MON-FRI"))
    			  .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * ? * MON-FRI"))
    			  .build();

    			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    			scheduler.start();
    			scheduler.scheduleJob(job, trigger);

    		} catch (SchedulerException e) {
    			e.printStackTrace();
    		}*/
    }

    public final void contextDestroyed(final ServletContextEvent sce) {

    }
}
