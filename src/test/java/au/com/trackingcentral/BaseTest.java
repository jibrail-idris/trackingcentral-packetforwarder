package au.com.trackingcentral;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
public class BaseTest {

//	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
//			"dd-MM-yyyy HH:mm:ss");

	public BaseTest() {
	}

	@Before
	public void setUp() throws Exception {

	}
	
	@Test
	public void dummy() {}
}
