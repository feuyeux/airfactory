package creative.air.selenium2.log;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.StringMatchFilter;

public class SeleniumLogFilter extends StringMatchFilter {
	String stringToMatch = null;
	boolean acceptOnMatch = true;

	@Override
	public int decide(LoggingEvent event) {
		String loggerName = event.getLoggerName();
		if (loggerName.indexOf(stringToMatch) > -1) {
			return Filter.ACCEPT;
		} else if (loggerName.indexOf("org.apache") > -1) {
			return Filter.DENY;
		} else {
			return Filter.NEUTRAL;
		}
	}

	@Override
	public boolean getAcceptOnMatch() {
		return acceptOnMatch;
	}

	public void setAcceptOnMatch(Boolean acceptOnMatch) {
		this.acceptOnMatch = acceptOnMatch;
	}

	@Override
	public String getStringToMatch() {
		return stringToMatch;
	}

	@Override
	public void setStringToMatch(String stringToMatch) {
		this.stringToMatch = stringToMatch;
	}
}