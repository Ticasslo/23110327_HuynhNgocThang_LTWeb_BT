package vn.ngocthang.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	@Column(name = "email", columnDefinition = "NVARCHAR(255)")
	private String email;
	
	@Column(name = "username", columnDefinition = "NVARCHAR(255)")
	private String userName;
	
	@Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
	private String fullName;
	
	@Column(name = "password", columnDefinition = "NVARCHAR(255)")
	private String passWord;
	
	@Column(name = "avatar", columnDefinition = "NVARCHAR(MAX)")
	private String avatar;
	
	@Column(name = "roleid")
	private int roleid;
	
	@Column(name = "phone", columnDefinition = "NVARCHAR(20)")
	private String phone;
	
	@Column(name = "createdDate")
	private Timestamp createdDate;

	public User() {
		super();
	}

	public User(int id, String email, String userName, String fullName, String passWord, String avatar, int roleid,
			String phone, Timestamp createdDate) {
		super();
		this.id = id;
		this.email = email;
		this.userName = userName;
		this.fullName = fullName;
		this.passWord = passWord;
		this.avatar = avatar;
		this.roleid = roleid;
		this.phone = phone;
		this.createdDate = createdDate;
	}

	public User(String email, String userName, String fullName, String passWord, String avatar, int roleid,
			String phone, Timestamp createdDate) {
		super();
		this.email = email;
		this.userName = userName;
		this.fullName = fullName;
		this.passWord = passWord;
		this.avatar = avatar;
		this.roleid = roleid;
		this.phone = phone;
		this.createdDate = createdDate;
	}

	// Tạo constructor, getters/setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", userName=" + userName + ", fullName=" + fullName
				+ ", passWord=" + passWord + ", avatar=" + avatar + ", roleid=" + roleid + ", phone=" + phone
				+ ", createdDate=" + createdDate + "]";
	}
}
