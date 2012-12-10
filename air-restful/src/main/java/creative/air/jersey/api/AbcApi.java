package creative.air.jersey.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import creative.air.jersey.model.AbcDto;
import creative.air.jersey.model.AbcReturnDto;
import creative.air.jersey.service.AbcService;

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
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private AbcService abcService;

	@Path("/transfer")
	@GET
	public void transfer() {
		abcService.transferAll();
	}

	@Path("/getAll1")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AbcReturnDto getAll1() {
		try {
			List<AbcDto> list = abcService.findAll1();
			return (new AbcReturnDto(list));
		} catch (Exception ex) {
			logger.error("error in getAll1");
			return (new AbcReturnDto(1, "error in getAll1"));
		}
	}

	@Path("/getAll2")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AbcReturnDto getAll2() {
		try {
			List<AbcDto> list = abcService.findAll2();
			return (new AbcReturnDto(list));
		} catch (Exception ex) {
			logger.error("error in getAll2");
			return (new AbcReturnDto(1, "error in getAll2"));
		}
	}
}
