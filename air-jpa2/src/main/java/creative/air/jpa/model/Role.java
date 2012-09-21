package creative.air.jpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AirRole")
public class Role implements Serializable {
	private static final long serialVersionUID = -4379086740480601071L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "EMP_SEQ")
	@SequenceGenerator(name = "EMP_SEQ")
	@Column(unique = true, nullable = false)
	private Integer roleId;

	@Column(nullable = false, length = 128)
	private String roleName;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "role")
	private List<User> user;

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public Integer getUserId() {
		return roleId;
	}

	public void setUserId(Integer userId) {
		roleId = userId;
	}

	public String getUserName() {
		return roleName;
	}

	public void setUserName(String userName) {
		roleName = userName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUser() {
		return user;
	}

	public void setUser(List<User> user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder buff = new StringBuilder();
		buff.append("Role: id=").append(roleId).append(" name=").append(roleName);
		return buff.toString();
	}
}