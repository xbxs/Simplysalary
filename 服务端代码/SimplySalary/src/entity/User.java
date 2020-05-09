package entity;

public class User {
	public User() {
		super();
	}
	private String u_phone;
	private String u_name;
	private String u_head;
	private String u_flag;
	private String u_pw;
	private int u_bas;
	private String u_wage;
	private String u_section;
	private String u_ower;
	public User(String u_phone, String u_name, String u_head, String u_flag,
			String u_pw, int u_bas, String u_wage, String u_section,
			String u_ower) {
		super();
		this.u_phone = u_phone;
		this.u_name = u_name;
		this.u_head = u_head;
		this.u_flag = u_flag;
		this.u_pw = u_pw;
		this.u_bas = u_bas;
		this.u_wage = u_wage;
		this.u_section = u_section;
		this.u_ower = u_ower;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_head() {
		return u_head;
	}
	public void setU_head(String u_head) {
		this.u_head = u_head;
	}
	public String getU_flag() {
		return u_flag;
	}
	public void setU_flag(String u_flag) {
		this.u_flag = u_flag;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
	public int getU_bas() {
		return u_bas;
	}
	public void setU_bas(int u_bas) {
		this.u_bas = u_bas;
	}
	public String getU_wage() {
		return u_wage;
	}
	public void setU_wage(String u_wage) {
		this.u_wage = u_wage;
	}
	public String getU_section() {
		return u_section;
	}
	public void setU_section(String u_section) {
		this.u_section = u_section;
	}
	public String getU_ower() {
		return u_ower;
	}
	public void setU_ower(String u_ower) {
		this.u_ower = u_ower;
	}
	@Override
	public String toString() {
		return "User [u_phone=" + u_phone + ", u_name=" + u_name + ", u_head="
				+ u_head + ", u_flag=" + u_flag + ", u_pw=" + u_pw + ", u_bas="
				+ u_bas + ", u_wage=" + u_wage + ", u_section=" + u_section
				+ ", u_ower=" + u_ower + "]";
	}
		
}
