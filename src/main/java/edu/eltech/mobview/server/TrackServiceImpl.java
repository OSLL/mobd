package edu.eltech.mobview.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.eltech.mobview.client.data.track.Place;
import edu.eltech.mobview.client.data.track.TrackPointV2;
import edu.eltech.mobview.client.service.TrackService;
import edu.eltech.mobview.server.data.Points;
import edu.eltech.mobview.server.data.Visit10Min;

@SuppressWarnings("serial")
public class TrackServiceImpl extends RemoteServiceServlet
 implements TrackService {
	private LonLat coord(LonLat pos, double x, double y) {
		final double ER = 6378.137;
		final double deg2rad = Math.PI / 180.0;
		
		double klon = 180.0 / (Math.PI * ER * Math.cos(pos.getLat() * deg2rad));
		double klat = 180.0 / (Math.PI * ER);
		
		return new LonLat(pos.getLon() + klon * x, pos.getLat() + klat * y);
	}

	@Override
	public List<TrackPointV2> getTrackPoints(int userid) {		
		ArrayList<TrackPointV2> result = new ArrayList<TrackPointV2>();
		EntityManager em = HibernateUtil.getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Visit10Min> c = cb.createQuery(Visit10Min.class);
		Root<Visit10Min> rv = c.from(Visit10Min.class);
		CriteriaQuery<Visit10Min> query = c.select(rv).where(cb.equal(rv.get("userid"), userid));
		
		TypedQuery<Visit10Min> typedQuery = em.createQuery(query);
		
		for (Visit10Min v : typedQuery.getResultList()) {
			result.add(new TrackPointV2(v.getUnixtime_start(), v.getPlace_id()));
			result.add(new TrackPointV2(v.getUnixtime_end(), v.getPlace_id()));
		}
		
		return result;
	}
	
	@Override
	public List<Place> getPlaces(int userid) {
		final LonLat spb = new LonLat(30.311666, 59.93816);
		
		EntityManager em = HibernateUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Points> c = cb.createQuery(Points.class);
		Root<Points> rp = c.from(Points.class);
		CriteriaQuery<Points> query = c.select(rp).where(cb.equal(rp.get("userid"), userid));
		
		TypedQuery<Points> typedQuery = em.createQuery(query);
		
		ArrayList<Place> result = new ArrayList<Place>();
		for (Points p : typedQuery.getResultList()) {		
			LonLat lonlat = coord(spb, p.getX(), p.getY());
			result.add(new Place(p.getPointsid(), lonlat.getLon(), lonlat.getLat()));
		}
		
		return result;
	}
	
	private class LonLat {
		private double lon;
		private double lat;
		
		public LonLat(double lon, double lat) {
			this.lon = lon;
			this.lat = lat;
		}
		
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
	}
}
