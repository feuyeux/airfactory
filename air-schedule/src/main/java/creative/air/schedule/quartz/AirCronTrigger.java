package creative.air.schedule.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

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

		JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
		CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/2 * * * * ?")).build();
		Date ft = sched.scheduleJob(job, trigger);
		log.info(job.getKey() + " has been scheduled:" + ft + " expression: " + trigger.getCronExpression());

		sched.start();
		try {
			Thread.sleep(60L * 1000L);
		} catch (Exception e) {
		}

		sched.shutdown(true);

		SchedulerMetaData metaData = sched.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}
}
