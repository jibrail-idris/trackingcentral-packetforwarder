package au.com.trackingcentral.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.entities.DataPacket;

@Transactional
public class DataPacketDAOTest extends BaseTest {
	@Autowired
	private DataPacketDAO dataPacketDAO;

	@Test
	public void save_One() {
		Date timestamp = new Date();
		Random random = new Random();
		DataPacket dataPacket = new DataPacket();
		Boolean isError = false;
		String randomRaw = "Raw" + random.nextInt(1000);
		String randomImei = "" + random.nextInt(1000);
		Integer randomImeiPosition = random.nextInt(5);

		dataPacket.setTimestamp(timestamp);
		dataPacket.setIsError(isError);
		dataPacket.setRaw(randomRaw);
		dataPacket.setImei(randomImei);
		dataPacket.setImeiPosition(randomImeiPosition);

		dataPacketDAO.save(dataPacket);
		Integer id = dataPacket.getId();
		Assert.assertTrue(id > 0);
		Assert.assertEquals(randomRaw, dataPacket.getRaw());
		Assert.assertEquals(randomImei, dataPacket.getImei());
		Assert.assertEquals(randomImeiPosition, dataPacket.getImeiPosition());
	}

	@Test
	public void read_One() {
		Date timestamp = new Date();
		Random random = new Random();
		DataPacket dataPacket = new DataPacket();

		String randomRaw = "Raw" + random.nextInt(1000);
		String randomImei = "" + random.nextInt(1000);
		Integer randomImeiPosition = random.nextInt(5);
		Boolean isError = true;
		dataPacket.setTimestamp(timestamp);
		dataPacket.setIsError(isError);
		dataPacket.setRaw(randomRaw);
		dataPacket.setImei(randomImei);
		dataPacket.setImeiPosition(randomImeiPosition);
		dataPacketDAO.save(dataPacket);

		Integer id = dataPacket.getId();
		DataPacket dataPacketResult = dataPacketDAO.read(id);
		Assert.assertEquals(dataPacket, dataPacketResult);
		Assert.assertEquals(id, dataPacketResult.getId());
		Assert.assertEquals(randomRaw, dataPacketResult.getRaw());
		Assert.assertEquals(isError, dataPacketResult.isError());
		Assert.assertEquals(randomImei, dataPacketResult.getImei());
		Assert.assertEquals(randomImeiPosition,
				dataPacketResult.getImeiPosition());
	}

	@Test
	public void read_OneByIEMI() {
		Date timestamp = new Date();
		Random random = new Random();
		DataPacket dataPacket = new DataPacket();

		String randomRaw = "Raw" + random.nextInt(1000);
		String randomImei = "" + random.nextInt(1000);
		Integer randomImeiPosition = random.nextInt(5);
		Boolean isError = false;
		dataPacket.setTimestamp(timestamp);
		dataPacket.setIsError(isError);
		dataPacket.setRaw(randomRaw);
		dataPacket.setImei(randomImei);
		dataPacket.setImeiPosition(randomImeiPosition);
		dataPacketDAO.save(dataPacket);

		Integer id = dataPacket.getId();
		String imei = dataPacket.getImei().toString();
		List<DataPacket> results = dataPacketDAO.readByIMEI(imei);
		Assert.assertEquals(1, results.size());
		DataPacket dataPacketResult = results.get(0);
		Assert.assertEquals(dataPacket, dataPacketResult);
		Assert.assertEquals(id, dataPacketResult.getId());
		Assert.assertEquals(randomRaw, dataPacketResult.getRaw());
		Assert.assertEquals(isError, dataPacketResult.isError());
	}

	/**
	 * This test reads multiple data packets sent by one IMEI source.
	 */
	@Test
	public void read_MultipleByIMEI() {
		Random random = new Random();
		int numberOfPackets = 5;
		String randomImei = "" + random.nextInt(1000);
		for (int i = 0; i < numberOfPackets; i++) {

			String randomRaw = "Raw" + random.nextInt(1000);
			Date timestamp = new Date();
			Integer randomImeiPosition = random.nextInt(5);

			DataPacket dataPacket = new DataPacket();
			Boolean isError = false;
			dataPacket.setTimestamp(timestamp);
			dataPacket.setIsError(isError);
			dataPacket.setRaw(randomRaw);
			dataPacket.setImei(randomImei);
			dataPacket.setImeiPosition(randomImeiPosition);

			dataPacketDAO.save(dataPacket);
		}

		List<DataPacket> dataPackets = dataPacketDAO.readByIMEI(randomImei);

		Assert.assertEquals(numberOfPackets, dataPackets.size());
	}

	@Test
	public void delete_One() {
		Date timestamp = new Date();
		Random random = new Random();
		DataPacket dataPacket = new DataPacket();
		String randomRaw = "Raw" + random.nextInt(1000);
		String randomImei = "" + random.nextInt(1000);
		Integer randomImeiPosition = random.nextInt(5);
		Boolean isError = false;

		dataPacket.setTimestamp(timestamp);
		dataPacket.setIsError(isError);
		dataPacket.setRaw(randomRaw);
		dataPacket.setImei(randomImei);
		dataPacket.setImeiPosition(randomImeiPosition);
		dataPacketDAO.save(dataPacket);
		Integer id = dataPacket.getId();
		DataPacket dataPacketResult = dataPacketDAO.read(id);
		Assert.assertEquals(id, dataPacketResult.getId());
		dataPacketDAO.delete(id);
		DataPacket dataPacketDeleted = dataPacketDAO.read(id);
		Assert.assertNull(dataPacketDeleted);
	}
}
