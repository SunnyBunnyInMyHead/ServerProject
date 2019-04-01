import java.io.*;
import java.net.Socket;

public class StopServer {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.140",7035);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());
        pw.print("stop");
        pw.flush();
        pw.close();
        socket.close();
    }
}
