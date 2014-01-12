package au.com.trackingcentral.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import au.com.trackingcentral.entities.DataPacket;

public class DataPacketDAO extends BaseDAO {

	public void save(DataPacket dataPacket) {
		Session session = sessionFactory.getCurrentSession();
		session.save(dataPacket);
		session.flush();
	}

	public DataPacket read(Integer dataPacketID) {
		Session session = sessionFactory.getCurrentSession();
		DataPacket dataPacket = (DataPacket) session
				.createCriteria(DataPacket.class)
				.add(Restrictions.idEq(dataPacketID)).uniqueResult();
		return dataPacket;
	}

	@SuppressWarnings("unchecked")
	public List<DataPacket> readAll() {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(DataPacket.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<DataPacket> readByIMEI(String imei) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(DataPacket.class)
				.add(Restrictions.eq("imei", imei)).list();
	}

	public void delete(Integer dataPacketID) {
		Session session = sessionFactory.getCurrentSession();
		DataPacket dataPacket = read(dataPacketID);
		session.delete(dataPacket);
	}

}