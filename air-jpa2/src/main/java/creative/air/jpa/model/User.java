package creative.air.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * @author feuyeux@gmail.com
 * 2012-11-11
 */
@Entity
@Table(name = "AirUser")
public class User implements Serializable {
	private static final long serialVersionUID = -4379086740480601071L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "EMP_SEQ")
	@SequenceGenerator(name = "EMP_SEQ")
	@Column(unique = true, nullable = false)
	private Integer userId;

	@Column(name = "username", nullable = false, length = 128)
	private String userName;

	@JoinColumn(name = "roleId", referencedColumnName = "roleId")
	@ManyToMany
	private List<Role> role;

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("User: id=").append(userId).append(" name=").append(userName);
		for (Role r : role) {
			buff.append(r);
		}

		return buff.toString();
	}
}