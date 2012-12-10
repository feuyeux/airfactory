package creative.air.jersey.service;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import creative.air.jersey.dao.AbcDao;
import creative.air.jersey.model.AbcDto;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 05/08/2012
 * @since  0.0.1
 * @version 0.0.1
 */
@Service("abcService")
public class AbcService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	AbcDao abcDao;

	public void transferAll() {
		logger.debug(Calendar.getInstance().getTime());
		abcDao.insertAll2(abcDao.findAll1());
		logger.debug(Calendar.getInstance().getTime());
	}

	public List<AbcDto> findAll1() {
		return abcDao.findAll1();
	}

	public List<AbcDto> findAll2() {
		try {
			return abcDao.findAll2();
		} catch (Exception e) {
			return null;
		}
	}
}
