package creative.air.jersey.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import creative.air.jersey.dao.AbcDao;
import creative.air.jersey.model.AbcDto;
import creative.air.jersey.model.AbcEntity;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Transactional
@Service("abcService")
public class AbcService {
	private Logger logger = Logger.getLogger(this.getClass());
	private AbcDao abcDao;

	public AbcDao getAbcDao() {
		return abcDao;
	}

	public void setAbcDao(AbcDao abcDao) {
		this.abcDao = abcDao;
	}

	private AbcEntity dto2Entity(AbcDto abcDto) {
		AbcEntity abc = new AbcEntity();
		abc.setName(abcDto.getName());
		abc.setValue(abcDto.getValue());
		return abc;
	}

	private AbcDto entity2Dto(AbcEntity abc) {
		AbcDto dto = new AbcDto();
		dto.setId(abc.getId());
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
		AbcEntity abc = abcDao.update(dto2Entity(abcUpdated));
		return entity2Dto(abc);
	}

	public List<AbcDto> getAll() {
		List<AbcEntity> abcs = abcDao.findAll();
		List<AbcDto> dtos = new ArrayList<AbcDto>();
		for (AbcEntity abc : abcs) {
			dtos.add(entity2Dto(abc));
		}
		return dtos;
	}

	public AbcDto getABC(Integer id) throws Exception {
		return entity2Dto(abcDao.findById(id));
	}
}
