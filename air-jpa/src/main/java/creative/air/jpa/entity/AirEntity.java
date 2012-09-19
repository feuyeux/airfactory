package creative.air.jpa.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;


public class AirEntity implements Serializable {
	private static final long serialVersionUID = -5752734491467306072L;

	@Id
	@Basic(optional = false)
	@Column(name = "id")
	private long id;
	private Timestamp createTime;
	private Timestamp updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
