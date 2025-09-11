package ngocthang.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cate_id")
	private int cateid;

	@Column(name = "cate_name", columnDefinition = "NVARCHAR(255)")
	private String catename;

	@Column(name = "icons", columnDefinition = "NVARCHAR(MAX)")
	private String icon;

	@Column(name = "userid")
	private int userid;

	// Constructors
	public Category() {
		super();
	}

	public Category(String catename, String icon) {
		super();
		this.catename = catename;
		this.icon = icon;
	}

	public Category(int cateid, String catename, String icon) {
		super();
		this.cateid = cateid;
		this.catename = catename;
		this.icon = icon;
	}

	public Category(int cateid, String catename, String icon, int userid) {
		super();
		this.cateid = cateid;
		this.catename = catename;
		this.icon = icon;
		this.userid = userid;
	}

	// Getters và Setters
	public int getId() {
		return cateid;
	}

	public void setId(int cateid) {
		this.cateid = cateid;
	}

	public String getName() {
		return catename;
	}

	public void setName(String catename) {
		this.catename = catename;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Override
	public String toString() {
		return "Category [cateid=" + cateid + ", catename=" + catename + ", icon=" + icon + ", userid=" + userid + "]";
	}
}
