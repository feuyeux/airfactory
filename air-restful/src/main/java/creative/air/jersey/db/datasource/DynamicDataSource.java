package creative.air.jersey.db.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource  extends AbstractRoutingDataSource{  
	  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return ContextHolder.getLookupKey();  
    }  
}  