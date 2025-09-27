package ngocthang.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "fullname", columnDefinition = "NVARCHAR(50)")
	private String fullname;

	@Column(name = "phone")
	private Integer phone;

	@Column(name = "passwd", length = 32, nullable = false)
	private String password;

	@Column(name = "signup_date")
	private LocalDateTime signupDate;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "is_admin", nullable = false)
	private boolean admin;

	public User() {
		super();
	}

	public User(String email, String fullname, Integer phone, String password, LocalDateTime signupDate,
			LocalDateTime lastLogin, boolean admin) {
		super();
		this.email = email;
		this.fullname = fullname;
		this.phone = phone;
		this.password = password;
		this.signupDate = signupDate;
		this.lastLogin = lastLogin;
		this.admin = admin;
	}

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

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(LocalDateTime signupDate) {
		this.signupDate = signupDate;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fullname=" + fullname + ", phone=" + phone
				+ ", signupDate=" + signupDate + ", lastLogin=" + lastLogin + ", admin=" + admin + "]";
	}
}


