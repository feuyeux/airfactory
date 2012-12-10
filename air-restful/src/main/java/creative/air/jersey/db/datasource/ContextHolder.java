package creative.air.jersey.db.datasource;

public class ContextHolder {
	public static final String DATASOURCE1 = "dataSource1";
	public static final String DATASOURCE2 = "dataSource2";

	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setLookup(String lookupKey) {
		contextHolder.set(lookupKey);
	}

	public static String getLookupKey() {
		return contextHolder.get();
	}

	public static void clearLookup() {
		contextHolder.remove();
	}
}