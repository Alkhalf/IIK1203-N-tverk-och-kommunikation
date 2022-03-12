
import java.io.IOException;
import java.net.ServerSocket;

public class ConcHTTPAsk {
	public static void main(String[] args) throws IOException {
		int port =Integer.parseInt(args[0]);
		ServerSocket welcomeSocket = new ServerSocket(port);
		while (true) {
			MyRunnable runnable = new MyRunnable(welcomeSocket.accept());
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}
}
