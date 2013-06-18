package creative.air.schedule.quartz;

import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//1
@org.quartz.DisallowConcurrentExecution
public class SimpleJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SimpleJob.class);
	private static int n = 1;

	public SimpleJob() {
		log.info("new a simple job");
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		//2
		/*
		 if (!checkPreviousDone(context))
		 return;
		 */
		
		log.info("n=" + n);
		JobKey jobKey = context.getJobDetail().getKey();
		log.info(jobKey + " executing at " + new Date());
		n++;

		//test 
		//结论：如果前一个任务没做完，后一个任务不会等待
		//原因：Spring定时任务默认都是并发执行的，不会等待上一次任务执行完毕，只要间隔时间到就会执行。
		//解决：
		// 1.使用@DisallowConcurrentExecution 任务顺序执行。
		// 2.在执行前做判断。
		try {
			Thread.sleep(3 * 1000L);
			log.info(jobKey + " done.");
		} catch (Exception e) {
			n--;
			log.info(jobKey + " failed.");
		}
	}

	boolean checkPreviousDone(JobExecutionContext context) {
		try {
			List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			if (jobs != null && !jobs.isEmpty()) {
				for (JobExecutionContext job : jobs) {
					if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
						log.info("< ");
						return false;
					}
				}
			}
		} catch (SchedulerException e) {
			log.error("schedule exception", e.getCause());
			return false;
		}
		return true;
	}
}
