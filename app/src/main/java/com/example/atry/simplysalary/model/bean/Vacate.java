package com.example.atry.simplysalary.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Vacate implements Parcelable {
	private String v_id;
	private String u_phone;
	private String v_rtime;
	private String v_term;
	private String v_shift;
	private String v_section;
	private String v_reason;
	private String v_tuser;
	private String v_status;
	private String v_btime;
	private String v_etime;
	private String v_type;
	public Vacate() {
		super();
	}
	public Vacate(String v_id, String u_phone, String v_rtime, String v_term,
			String v_shift, String v_section, String v_reason, String v_tuser,
			String v_status, String v_btime, String v_etime, String v_type) {
		super();
		this.v_id = v_id;
		this.u_phone = u_phone;
		this.v_rtime = v_rtime;
		this.v_term = v_term;
		this.v_shift = v_shift;
		this.v_section = v_section;
		this.v_reason = v_reason;
		this.v_tuser = v_tuser;
		this.v_status = v_status;
		this.v_btime = v_btime;
		this.v_etime = v_etime;
		this.v_type = v_type;
	}
	public String getV_id() {
		return v_id;
	}
	public void setV_id(String v_id) {
		this.v_id = v_id;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getV_rtime() {
		return v_rtime;
	}
	public void setV_rtime(String v_rtime) {
		this.v_rtime = v_rtime;
	}
	public String getV_term() {
		return v_term;
	}
	public void setV_term(String v_term) {
		this.v_term = v_term;
	}
	public String getV_shift() {
		return v_shift;
	}
	public void setV_shift(String v_shift) {
		this.v_shift = v_shift;
	}
	public String getV_section() {
		return v_section;
	}
	public void setV_section(String v_section) {
		this.v_section = v_section;
	}
	public String getV_reason() {
		return v_reason;
	}
	public void setV_reason(String v_reason) {
		this.v_reason = v_reason;
	}
	public String getV_tuser() {
		return v_tuser;
	}
	public void setV_tuser(String v_tuser) {
		this.v_tuser = v_tuser;
	}
	public String getV_status() {
		return v_status;
	}
	public void setV_status(String v_status) {
		this.v_status = v_status;
	}
	public String getV_btime() {
		return v_btime;
	}
	public void setV_btime(String v_btime) {
		this.v_btime = v_btime;
	}
	public String getV_etime() {
		return v_etime;
	}
	public void setV_etime(String v_etime) {
		this.v_etime = v_etime;
	}
	public String getV_type() {
		return v_type;
	}
	public void setV_type(String v_type) {
		this.v_type = v_type;
	}
	@Override
	public String toString() {
		return "Vacate [v_id=" + v_id + ", u_phone=" + u_phone + ", v_rtime="
				+ v_rtime + ", v_term=" + v_term + ", v_shift=" + v_shift
				+ ", v_section=" + v_section + ", v_reason=" + v_reason
				+ ", v_tuser=" + v_tuser + ", v_status=" + v_status
				+ ", v_btime=" + v_btime + ", v_etime=" + v_etime + ", v_type="
				+ v_type + "]";
	}
	public static final Parcelable.Creator<Vacate> CREATOR = new Creator<Vacate>() {
		@Override
		public Vacate createFromParcel(Parcel source) {
			Vacate vacate = new Vacate();
			vacate.u_phone = source.readString();
			vacate.v_rtime = source.readString();
			vacate.v_btime = source.readString();
			vacate.v_term = source.readString();
			vacate.v_shift = source.readString();
			vacate.v_section = source.readString();
			vacate.v_reason = source.readString();
			vacate.v_tuser = source.readString();
			vacate.v_status = source.readString();
			vacate.v_etime = source.readString();
			vacate.v_type = source.readString();
			return vacate;
		}

		@Override
		public Vacate[] newArray(int size) {
			return new Vacate[size];
		}
	};
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		    dest.writeString(u_phone);
			dest.writeString(v_rtime);
			dest.writeString(v_btime);
			dest.writeString(v_term);
			dest.writeString(v_shift);
			dest.writeString(v_section);
			dest.writeString(v_reason);
			dest.writeString(v_tuser);
			dest.writeString(v_status);
			dest.writeString(v_etime);
			dest.writeString(v_type);
	}
}
