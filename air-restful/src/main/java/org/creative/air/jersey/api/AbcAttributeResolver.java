package org.creative.air.jersey.api;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.creative.air.jersey.model.AbcDto;
import org.creative.air.jersey.model.AbcReturnDto;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

/**
 * Jesery Attribute Resolver
 * 
 * @author feuyeux@gmail.com
 * 05/08/2012
 * @version 0.1.0
 * @since 0.0.1
 */
@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@SuppressWarnings("rawtypes")
public class AbcAttributeResolver implements ContextResolver<JAXBContext> {
	private JAXBContext context;
	private Class[] types = { AbcDto.class, AbcReturnDto.class };

	public AbcAttributeResolver() throws Exception {
		JSONConfiguration jsonConfig = JSONConfiguration.mapped().attributeAsElement("id", "name", "value").build();
		//JSONConfiguration jsonConfig = JSONConfiguration.natural().build();

		this.context = new JSONJAXBContext(jsonConfig, types);
	}

	public JAXBContext getContext(Class<?> objectType) {
		for (Class Clazz : types) {
			if (Clazz.equals(objectType))
				return context;
		}
		return null;
	}
}