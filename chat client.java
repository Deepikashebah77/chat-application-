import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 12345);
        new ReadThread(socket).start();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String message;
        while ((message = console.readLine()) != null) {
            out.println(message);
        }
        socket.close();
    }

    private static class ReadThread extends Thread {
        private Socket socket;
        private BufferedReader in;

        public ReadThread(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e);
            }
        }
    }
}
