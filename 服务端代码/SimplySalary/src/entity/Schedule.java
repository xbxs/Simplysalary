package entity;

public class Schedule {
	private int s_id;
	private String u_phone;
	private String s_time;
	private String s_shift;
	private String s_term;
	public Schedule() {
		super();
	}
	
	public Schedule(int s_id, String u_phone, String s_time, String s_shift,
			String s_term) {
		super();
		this.s_id = s_id;
		this.u_phone = u_phone;
		this.s_time = s_time;
		this.s_shift = s_shift;
		this.s_term = s_term;
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
	public String getS_time() {
		return s_time;
	}
	public void setS_time(String s_time) {
		this.s_time = s_time;
	}
	public String getS_shift() {
		return s_shift;
	}
	public void setS_shift(String s_shift) {
		this.s_shift = s_shift;
	}
	public String getS_term() {
		return s_term;
	}
	public void setS_term(String s_term) {
		this.s_term = s_term;
	}

	@Override
	public String toString() {
		return "Schedule [s_id=" + s_id + ", u_phone=" + u_phone + ", s_time="
				+ s_time + ", s_shift=" + s_shift + ", s_term=" + s_term + "]";
	}
	
}
