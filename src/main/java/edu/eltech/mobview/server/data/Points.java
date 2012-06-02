package edu.eltech.mobview.server.data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Points {
	@Id
	private int id;
	private int pointid;
	private int userid;
	private double x;
	private double y;
	public int getPointsid() {
		return id;
	}
	public void setPointsid(int pointsid) {
		this.id = pointsid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getPointid() {
		return pointid;
	}
	public void setPointid(int pointid) {
		this.pointid = pointid;
	}
}
