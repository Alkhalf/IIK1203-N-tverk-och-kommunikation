package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public TCPClient() {
    }

	public byte[] askServer(String hostname, int port, byte[] bytesToServer)  throws IOException{

 			try {
 				Socket clientSocket = new Socket (hostname, port);
 			
 				clientSocket.getOutputStream().write(bytesToServer, 0, bytesToServer.length);
 				byte[] b = new byte[500];
 				InputStream inputStream = clientSocket.getInputStream(); 
 		   
 				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
 				int byteFromServe=0; 
 				while ((byteFromServe =inputStream.read(b)  ) != -1 )  {
 					byteArray.write(b, 0, byteFromServe);
 				}
			
 				clientSocket.close();
 			
 				return byteArray.toByteArray();
 			}
		    catch (SocketException ex) {
		    	return ex.getMessage().getBytes();
		    }
	}
}