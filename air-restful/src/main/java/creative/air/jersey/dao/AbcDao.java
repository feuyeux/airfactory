package creative.air.jersey.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import creative.air.jersey.db.datasource.ContextHolder;
import creative.air.jersey.db.datasource.DynamicDataSource;
import creative.air.jersey.model.AbcDto;

@Repository
public class AbcDao {
	String C1 = "insert into abc(name,value) value(?,?)";
	String RA1 = "select t.id ,t.name,t.value from abc t";
	String R1 = "select t.id ,t.name,t.value from abc t where t.id=?";
	String U1 = "update abc set t.name=?,t.value=? where id=?";
	String D1 = "delete abc where t.id=?";
	String C2 = "insert into abc2(name,value) value(?,?)";
	String RA2 = "select t.id ,t.name,t.value from abc2 t";
	String R2 = "select t.id ,t.name,t.value from abc2 t where t.id=?";
	String U2 = "update abc2 set t.name=?,t.value=? where id=?";
	String D2 = "delete abc2 where t.id=?";
	protected JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DynamicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public int[] insertAll2(final List<AbcDto> abcList) {
		ContextHolder.setLookup(ContextHolder.DATASOURCE2);
		return jdbcTemplate.batchUpdate(C2, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				AbcDto abc = abcList.get(index);
				ps.setString(1, abc.getName());
				ps.setString(2, abc.getValue());
			}

			@Override
			public int getBatchSize() {
				return abcList.size();
			}
		});
	}

	public List<AbcDto> findAll1() {
		ContextHolder.setLookup(ContextHolder.DATASOURCE1);
		return jdbcTemplate.queryForObject(RA1, new RowMapper<List<AbcDto>>() {
			@Override
			public List<AbcDto> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<AbcDto> abcList = new ArrayList<AbcDto>();
				while (rs.next()) {
					AbcDto abc = new AbcDto();
					abc.setId(rs.getInt(1));
					abc.setName(rs.getString(2));
					abc.setValue(rs.getString(3));
					abcList.add(abc);
				}
				return abcList;
			}
		});
	}

	public List<AbcDto> findAll2() {
		ContextHolder.setLookup(ContextHolder.DATASOURCE2);
		return jdbcTemplate.queryForObject(RA2, new RowMapper<List<AbcDto>>() {
			@Override
			public List<AbcDto> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<AbcDto> abcList = new ArrayList<AbcDto>();
				while (rs.next()) {
					AbcDto abc = new AbcDto();
					abc.setId(rs.getInt(1));
					abc.setName(rs.getString(2));
					abc.setValue(rs.getString(3));
					abcList.add(abc);
				}
				return abcList;
			}
		});
	}

}
