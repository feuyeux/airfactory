package creative.air.schedule.quartz;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirCronTrigger {
	Logger log;

	public AirCronTrigger() {
		log = LoggerFactory.getLogger(getClass());
	}

	public void runTask() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		JobDetail job = new JobDetail("job1", "group1", SimpleJob.class);
		/**
		 * 		1. Seconds
		 * 		2. Minutes
		 * 		3. Hours
		 * 		4. Day-of-Month
		 * 		5. Month
		 * 		6. Day-of-Week
		 * 		7. Year (optional field)
		 * 	*/
		CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1", "group1", "0/2 * * * * ?");
		sched.addJob(job, true);
		
		log.info(job.getKey() + " has been scheduled: expression: " + trigger.getCronExpression());
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
