import tcpclient.TCPClient;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPAsk {
	static String http404 = "HTTP/1.1 404 Not Found\n\n";


	public static void main(String[] args) throws IOException {
		
		int argindex = 0;

		int portLocal = Integer.parseInt(args[argindex++]);

		String hostname = null;
		String string = "";
		int port = 0;
		boolean shutdown = false;
		int limit = 0;
		int timeout = 0;
		byte[] toClient1 = new byte[3000];
		String http200 = "HTTP/1.1 200 OK\r\n\r\n";
		String http400 = "HTTP/1.1  400 Bad Request\r\n\r\n";
		boolean chechk;

		ServerSocket server = new ServerSocket(portLocal);
		while (true) {
			hostname = null;
			string = "";
			port = 0;
			chechk = false;
			Socket s = server.accept();
			InputStream inputStream = s.getInputStream();
			int ss = inputStream.read(toClient1);
			String gets = new String(toClient1, 0, ss);
			String[] line = gets.split("\\R", 2);
			
		

			
			String[] queryArray = line[0].split("[?&= ]", 15);
			
			chechk = line[0].startsWith("GET /ask?");

			for (int i = 0; i < queryArray.length; i++) {
				if (queryArray[i].equals("hostname"))
					hostname = queryArray[i + 1];

				if (queryArray[i].equals("port"))
					port = Integer.parseInt(queryArray[i + 1]);

				if (queryArray[i].equals("string")) {
					string = queryArray[i + 1];

				}
			}

			if (chechk == false || (!queryArray[queryArray.length - 1].equals("HTTP/1.1")) || hostname == null || port == 0 ) {
			
				s.getOutputStream().write(http400.getBytes("UTF-8"));
				//System.out.println(400);
			}
			else {
				try {

					byte[] toClient = string.getBytes("UTF-8");
					TCPClient tcpClient = new TCPClient(false, null, null);

					byte[] toServer = tcpClient.askServer(hostname, port, toClient);

					s.getOutputStream().write(http200.getBytes("UTF-8"));
					//System.out.println(200);

					s.getOutputStream().write(toServer);
				} catch (IOException e) {
					s.getOutputStream().write(http404.getBytes("UTF-8"));
					//System.out.println(404);

				}
				
			}
			s.close();

		}

	}
}