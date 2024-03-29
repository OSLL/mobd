package edu.eltech.mobview.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.eltech.mobview.client.data.LonLatDTO;
import edu.eltech.mobview.client.data.TrackDTO;
import edu.eltech.mobview.client.data.track.TrackPointV2;
import edu.eltech.mobview.client.service.TrackService;
import edu.eltech.mobview.server.data.Points;
import edu.eltech.mobview.server.data.Visit10Min;

@SuppressWarnings("serial")
public class TrackServiceImpl extends RemoteServiceServlet
 implements TrackService {
	private LonLat pos = null;	
	
	private LonLatDTO coord(LonLat pos, double x, double y) {
		final double ER = 6378.137;
		final double deg2rad = Math.PI / 180.0;
		
		double klon = 180.0 / (Math.PI * ER * Math.cos(pos.getLat() * deg2rad));
		double klat = 180.0 / (Math.PI * ER);
		
		return new LonLatDTO(pos.getLon() + klon * x, pos.getLat() + klat * y);
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
	public Map<Integer, LonLatDTO> getPlaces(int userid) {
		EntityManager em = HibernateUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Points> c = cb.createQuery(Points.class);
		Root<Points> rp = c.from(Points.class);
		CriteriaQuery<Points> query = c.select(rp).where(cb.equal(rp.get("userid"), userid));
		
		TypedQuery<Points> typedQuery = em.createQuery(query);
		
		Map<Integer, LonLatDTO> result = new TreeMap<Integer, LonLatDTO>();
		
		for (Points p : typedQuery.getResultList()) {		
			result.put(p.getPointid(), coord(pos, p.getX(), p.getY()));
		}
		
		return result;
	}

	@Override
	public Map<Integer, List<TrackPointV2>> getAllTrackPoints() {
		TreeMap<Integer, List<TrackPointV2>> result =
				new TreeMap<Integer, List<TrackPointV2>>();

		EntityManager em = HibernateUtil.getEntityManager();
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Visit10Min> c = cb.createQuery(Visit10Min.class);
		Root<Visit10Min> rv = c.from(Visit10Min.class);
		CriteriaQuery<Visit10Min> query = c.select(rv);
		
		TypedQuery<Visit10Min> typedQuery = em.createQuery(query);
		
		for (Visit10Min v : typedQuery.getResultList()) {
			int userid = v.getUserid();
			
			if (result.get(userid) == null) {
				result.put(userid, new ArrayList<TrackPointV2>());
			}
			
			List<TrackPointV2> list = result.get(userid); 
			
			list.add(new TrackPointV2(v.getUnixtime_start(), v.getPlace_id()));
			list.add(new TrackPointV2(v.getUnixtime_end(), v.getPlace_id()));
		}
		
		return result;
	}

	@Override
	public Map<Integer, Map<Integer, LonLatDTO>> getAllPlaces() {
		EntityManager em = HibernateUtil.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Points> c = cb.createQuery(Points.class);
		Root<Points> rp = c.from(Points.class);
		CriteriaQuery<Points> query = c.select(rp);
		
		TypedQuery<Points> typedQuery = em.createQuery(query);
		
		Map<Integer, Map<Integer, LonLatDTO>> result = new TreeMap<Integer, Map<Integer, LonLatDTO>>();
		for (Points p : typedQuery.getResultList()) {
			int userid = p.getUserid();
			if (result.get(userid) == null) {
				result.put(userid, new TreeMap<Integer, LonLatDTO>());
			}
			
			Map<Integer, LonLatDTO> places = result.get(userid);
			places.put(p.getPointid(), coord(pos, p.getX(), p.getY()));
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

	@Override
	public Map<Integer, TrackDTO> getAllTracks() {
		Map<Integer, TrackDTO> result = 
				new TreeMap<Integer, TrackDTO>();
		
		Map<Integer, Map<Integer, LonLatDTO>> places = getAllPlaces();
		Map<Integer, List<TrackPointV2>> trackPoints = getAllTrackPoints();
		
		int count = 0;
		for (int userid : places.keySet()) {
//			if (count > 5)
//				continue;
//			++count;
			
			TrackDTO track = new TrackDTO();
			track.setPlaces(places.get(userid));
			track.setPoints(trackPoints.get(userid));
			result.put(userid, track);
		}
		
		if (places.size() != trackPoints.keySet().size()) {
			System.err.println("wrong places size");
		}
		
		return result;
	}
	
	@Override
	public void init() throws ServletException {
		Properties defaultProps = new Properties();
		InputStream in;
		
		try {
			in = getServletContext().getResourceAsStream("/WEB-INF/mobview.properties");
			defaultProps.load(in);
			double lon = Double.valueOf((String)defaultProps.get("lon"));
			double lat = Double.valueOf((String)defaultProps.get("lat"));
			pos = new LonLat(lon, lat);
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public LonLatDTO getInitPos() {
		return new LonLatDTO(pos.getLon(), pos.getLat());
	}

}
