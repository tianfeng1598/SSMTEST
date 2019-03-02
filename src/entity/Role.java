package entity;

import java.util.Date;

/**
 * 用户角色表
 * 
 * @author Tian
 *
 */
public class Role {

	private int id;
	private String roleCode;
	private String roleName;
	private int createdBy;
	private Date reationDate;
	private int modifyBy;
	private Date modifyDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getReationDate() {
		return reationDate;
	}

	public void setReationDate(Date reationDate) {
		this.reationDate = reationDate;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

}
