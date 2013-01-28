package creative.air.jersey.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
			abcService.saveABC(abcDto);

			//			JSONConfiguration.mapped().attributeAsElement("id").build();
			//			JSONConfiguration.mapped().attributeAsElement("name").build();
			//			JSONConfiguration.mapped().attributeAsElement("value").build();

			return (new AbcReturnDto(abcDto));
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest", ex);
			return (new AbcReturnDto(1, "Cannot add new AbcRequest:" + ex.getLocalizedMessage()));
		}
	}

	@Path("/update")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public AbcReturnDto updateAbc(AbcDto abcDto) {
		if (abcDto == null) {
			return (new AbcReturnDto(1, "AbcDto data should not be null"));
		}

		try {
			AbcDto abcUpdated = abcService.getABC(abcDto.getId());
			if (abcUpdated == null) {
				return (new AbcReturnDto(2, "AbcDto data can not find in system"));
			}
			abcService.updateABC(abcDto);
			return new AbcReturnDto(abcDto);
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest", ex);
			return (new AbcReturnDto(1, "Cannot add new AbcRequest:" + ex.getLocalizedMessage()));
		}
	}

	@Path("/get")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AbcReturnDto get(Integer id) {
		try {
			AbcDto abc = abcService.getABC(id);
			return (new AbcReturnDto(abc));
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest", ex);
			return (new AbcReturnDto(1, "Cannot add new AbcRequest:" + ex.getLocalizedMessage()));
		}
	}

	@Path("/getAll")
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public AbcReturnDto getAll() {
		try {
			List<AbcDto> list = abcService.getAll();
			return (new AbcReturnDto(list));
		} catch (Exception ex) {
			_log.error("Cannot add new AbcRequest", ex);
			return (new AbcReturnDto(1, "Cannot add new AbcRequest:" + ex.getLocalizedMessage()));
		}
	}
}
