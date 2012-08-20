package creative.air.jersey.api;

import creative.air.jersey.model.AbcDto;
import creative.air.jersey.model.AbcReturnDto;
import creative.air.jersey.service.AbcService;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Component()
@Path("/abc")
public class AbcApi {
	private Logger _log = Logger.getLogger(this.getClass());
	@Autowired
	private AbcService abcService;

	@Path("/add")
	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AbcReturnDto addAbc(AbcDto abcDto) {
		if (abcDto == null) {
			return (new AbcReturnDto(1, "AbcDto data should not be null"));
		}

		try {
			AbcDto AbcListCreated = abcService.saveABC(abcDto);

//			JSONConfiguration.mapped().attributeAsElement("id").build();
//			JSONConfiguration.mapped().attributeAsElement("name").build();
//			JSONConfiguration.mapped().attributeAsElement("value").build();

			return (new AbcReturnDto(AbcListCreated));
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest by TestFolder", ex);
			return (new AbcReturnDto(1, "Cannot add new AbcRequest by TestFolder"));
		}
	}

	@Path("/get")
	@GET
	@Produces({ MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public AbcReturnDto getAll() {
		try {
			List<AbcDto> list = abcService.getAll();
			return (new AbcReturnDto(list));
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest by TestFolder");
			return (new AbcReturnDto(1, "Cannot add new AbcRequest by TestFolder"));
		}
	}
}
