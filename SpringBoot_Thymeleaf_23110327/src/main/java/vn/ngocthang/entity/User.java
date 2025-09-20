package vn.ngocthang.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	@Column(name = "avatar", columnDefinition = "NVARCHAR(500)")
	private String avatar;
	
	@Column(name = "roleid")
	private int roleid;
	
	@Column(name = "phone", columnDefinition = "NVARCHAR(20)")
	private String phone;
	
	@Column(name = "createdDate", columnDefinition = "DATETIME")
	private Timestamp createdDate;
}
