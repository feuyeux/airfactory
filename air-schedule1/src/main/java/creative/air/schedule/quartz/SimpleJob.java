package creative.air.schedule.quartz;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//1@org.quartz.DisallowConcurrentExecution
public class SimpleJob implements Job {
	private static Logger log = LoggerFactory.getLogger(SimpleJob.class);
	private static int n = 1;

	public SimpleJob() {
		log.info("new a simple job");
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("n=" + n);
		String jobName = context.getJobDetail().getFullName();
		log.info("SimpleJob says: " + jobName + " executing at " + new Date());
		n++;

		//test 
		//结论：如果前一个任务没做完，后一个任务不会等待
		//原因：Spring定时任务默认都是并发执行的，不会等待上一次任务执行完毕，只要间隔时间到就会执行。
		//解决：
		// 1.使用@DisallowConcurrentExecution 任务顺序执行。
		// 2.在执行前做判断。
		try {
			Thread.sleep(3 * 1000L);
			log.info(jobName + " done.");
		} catch (Exception e) {
			n--;
			log.info(jobName + " failed.");
		}
	}
}
