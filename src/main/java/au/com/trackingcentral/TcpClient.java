package au.com.trackingcentral;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TcpClient {
	public static void main(String[] args) throws IOException {
		Socket s = new Socket();
        s.connect(new InetSocketAddress("10.0.0.1", 272));
        PrintWriter writer = new PrintWriter(s.getOutputStream());
        writer.write("ADASD");
        writer.flush();
	}
}