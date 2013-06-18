package creative.air.schedule.quartz;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirCronTrigger {
	Logger log;

	public AirCronTrigger() {
		log = LoggerFactory.getLogger(getClass());
	}

	public void runTask() throws Exception {
		JobBuilder jb = org.quartz.JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1");
		JobDetail jobDetail = jb.build();

		/**
		 * 		1. Seconds
		 * 		2. Minutes
		 * 		3. Hours
		 * 		4. Day-of-Month
		 * 		5. Month
		 * 		6. Day-of-Week
		 * 		7. Year (optional field)
		 * 	*/
		CronScheduleBuilder schedBuilder = org.quartz.CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
		TriggerBuilder<CronTrigger> tb = org.quartz.TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(schedBuilder);
		CronTrigger trigger = tb.build();

		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		Date ft = sched.scheduleJob(jobDetail, trigger);

		log.info(jobDetail.getKey() + " has been scheduled:" + ft + " expression: " + trigger.getCronExpression());
		sched.start();
		try {
			Thread.sleep(12 * 1000L);
		} catch (Exception e) {
		}
		sched.shutdown(true);

		SchedulerMetaData metaData = sched.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}
}
