package au.com.trackingcentral;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		final AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// add a shutdown hook for the above context...
				ctx.registerShutdownHook();
			}
		});

	}
}
