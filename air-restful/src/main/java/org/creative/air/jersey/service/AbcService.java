package org.creative.air.jersey.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.creative.air.jersey.dao.AbcDao;
import org.creative.air.jersey.model.AbcDto;
import org.creative.air.jersey.model.AbcEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Tier Bean
 * 
 * @author feuyeux@gmail.com
 * 05/08/2012
 * @version 0.1.0
 * @since 0.0.1
 */
@Transactional
@Service("abcService")
public class AbcService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private AbcDao abcDao;

	public AbcDao getAbcDao() {
		return abcDao;
	}

	public void setAbcDao(AbcDao abcDao) {
		this.abcDao = abcDao;
	}

	private AbcEntity dto2Entity(AbcDto abcDto) {
		final AbcEntity abc = new AbcEntity();
		abc.setId(abcDto.getId());
		abc.setName(abcDto.getName());
		abc.setValue(abcDto.getValue());
		return abc;
	}

	private AbcDto entity2Dto(AbcEntity abc) {
		final AbcDto dto = new AbcDto();
		try {
			dto.setId(abc.getId());
		} catch (final Exception e) {
			logger.debug("abc id is empty.");
		}

		dto.setName(abc.getName());
		dto.setValue(abc.getValue());
		return dto;
	}

	public void saveABC(AbcDto abcDto) {
		logger.debug("createModelFromDto:" + abcDto);
		abcDao.save(dto2Entity(abcDto));
	}

	public AbcDto updateABC(AbcDto abcUpdated) {
		logger.debug("updateModelFromDto:" + abcUpdated);
		final AbcEntity abc = abcDao.update(dto2Entity(abcUpdated));
		return entity2Dto(abc);
	}

	public List<AbcDto> getAll() {
		final List<AbcEntity> abcs = abcDao.findAll();
		final List<AbcDto> dtos = new ArrayList<AbcDto>();
		for (final AbcEntity abc : abcs) {
			dtos.add(entity2Dto(abc));
		}
		return dtos;
	}

	public AbcDto getABC(Integer id) {
		return entity2Dto(abcDao.findById(id));
	}
}
