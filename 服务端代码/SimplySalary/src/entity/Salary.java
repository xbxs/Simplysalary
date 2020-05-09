package entity;

public class Salary {
	public Salary(String u_phone, String s_rtime, String s_term,
			String s_shift, String s_section, String s_wage) {
		super();
		this.u_phone = u_phone;
		this.s_rtime = s_rtime;
		this.s_term = s_term;
		this.s_shift = s_shift;
		this.s_section = s_section;
		this.s_wage = s_wage;
	}
	public Salary() {
		super();
	}
	private int s_id;
	private String u_phone;
	private String s_rtime;
	private String s_term;
	private String s_shift;
	private String s_section;
	private String s_wage;
	
	public Salary(int s_id, String u_phone, String s_rtime, String s_term,
			String s_shift, String s_section, String s_wage) {
		super();
		this.s_id = s_id;
		this.u_phone = u_phone;
		this.s_rtime = s_rtime;
		this.s_term = s_term;
		this.s_shift = s_shift;
		this.s_section = s_section;
		this.s_wage = s_wage;
	}
	public int getS_id() {
		return s_id;
	}
	public void setS_id(int s_id) {
		this.s_id = s_id;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getS_rtime() {
		return s_rtime;
	}
	public void setS_rtime(String s_rtime) {
		this.s_rtime = s_rtime;
	}
	public String getS_term() {
		return s_term;
	}
	public void setS_term(String s_term) {
		this.s_term = s_term;
	}
	public String getS_shift() {
		return s_shift;
	}
	public void setS_shift(String s_shift) {
		this.s_shift = s_shift;
	}
	public String getS_section() {
		return s_section;
	}
	public void setS_section(String s_section) {
		this.s_section = s_section;
	}
	public String getS_wage() {
		return s_wage;
	}
	public void setS_wage(String s_wage) {
		this.s_wage = s_wage;
	}
	@Override
	public String toString() {
		return "Salary [s_id=" + s_id + ", u_phone=" + u_phone + ", s_rtime="
				+ s_rtime + ", s_term=" + s_term + ", s_shift=" + s_shift
				+ ", s_section=" + s_section + ", s_wage=" + s_wage + "]";
	}
	
	
	
	
}
