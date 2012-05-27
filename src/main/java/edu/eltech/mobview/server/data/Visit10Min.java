package edu.eltech.mobview.server.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="visit_sequence_10min")
public class Visit10Min {
	@Id
	@Column(name="id")
	private int visit10id;
	private int userid;
	private int unixtime_start;
	private int tz_start;
	private int unixtime_end;
	private int tz_end;
	private int place_id;
	private int trusted_start;
	private int trusted_end;
	
	public int getVisit10id() {
		return visit10id;
	}
	public void setVisit10id(int visit10id) {
		this.visit10id = visit10id;
	}
	public int getTz_start() {
		return tz_start;
	}
	public void setTz_start(int tz_start) {
		this.tz_start = tz_start;
	}
	public int getUnixtime_end() {
		return unixtime_end;
	}
	public void setUnixtime_end(int unixtime_end) {
		this.unixtime_end = unixtime_end;
	}
	public int getTz_end() {
		return tz_end;
	}
	public void setTz_end(int tz_end) {
		this.tz_end = tz_end;
	}
	public int getPlace_id() {
		return place_id;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	public int getTrusted_start() {
		return trusted_start;
	}
	public void setTrusted_start(int trusted_start) {
		this.trusted_start = trusted_start;
	}
	public int getTrusted_end() {
		return trusted_end;
	}
	public void setTrusted_end(int trusted_end) {
		this.trusted_end = trusted_end;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUnixtime_start() {
		return unixtime_start;
	}
	public void setUnixtime_start(int unixtime_start) {
		this.unixtime_start = unixtime_start;
	}

}

