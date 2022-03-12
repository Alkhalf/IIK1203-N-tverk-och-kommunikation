package tcpclient;

import java.net.*;
import java.io.*;

public class TCPClient {
	public static boolean shutdown1 = false;
	public static Integer timeout1 = null;
	public static Integer limit1 = null;

	public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
		shutdown1 = shutdown;
		timeout1 = timeout;
		limit1 = limit;

	}

	@SuppressWarnings("finally")
	public byte[] askServer(String hostname, int port, byte[] toServerBytes) throws IOException {

		int bufferSize = 7;

		byte[] b = new byte[bufferSize];
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		Socket clientSocket = new Socket(hostname, port);
		if (timeout1 != null)
			clientSocket.setSoTimeout(timeout1);
		try {

			clientSocket.getOutputStream().write(toServerBytes);

			if (shutdown1) {
				clientSocket.shutdownOutput();
			}

			int byteFromServe = 0;
			InputStream inputStream = clientSocket.getInputStream();
			if (limit1 == null) {
				while ((byteFromServe = inputStream.read(b)) != -1) {
					byteArray.write(b, 0, byteFromServe);
				}

			} else if (limit1 != null) {
				int rest = limit1;
				int dataLimit = 0;
				int t;
				int j;

				while (dataLimit <= limit1) {

					if (rest >= bufferSize && rest > 0) {
						t = inputStream.read(b, 0, bufferSize);
						byteArray.write(b, 0, t);
						dataLimit += t;
						rest = rest - t;
						if (t != bufferSize)
							break;
						if (dataLimit == limit1) {
							break;
						}
					}

					if ((rest < bufferSize) && rest > 0)  {
						j = inputStream.read(b, 0, rest);
						byteArray.write(b, 0, j);
						dataLimit += j;

						break;

					}

				}
			}

		} catch (SocketException ex) {
			clientSocket.shutdownOutput();
		}

		finally {
			return byteArray.toByteArray();

		}
	}
}
