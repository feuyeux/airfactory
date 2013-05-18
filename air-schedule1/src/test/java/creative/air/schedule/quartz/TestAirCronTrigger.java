package creative.air.schedule.quartz;

import org.junit.Test;

public class TestAirCronTrigger {
	@Test
	public void testRunTask() throws Exception {
		AirCronTrigger example = new AirCronTrigger();
		example.runTask();
	}
}
