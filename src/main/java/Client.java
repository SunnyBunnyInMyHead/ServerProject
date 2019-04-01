import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        //read
        Socket socket = null;
/*        try {
            //socket = new Socket("192.168.1.110", 7035);
            socket = new Socket("31.43.132.107", 7035);

            Scanner scanner = new Scanner(socket.getInputStream());
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //write
        /*try {
            //socket = new Socket("192.168.1.110", 7035);
            socket = new Socket("31.43.132.107", 7035);
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.print("stop");
            pw.flush();
            pw.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //write receive file
        try {
            //socket = new Socket("192.168.1.110", 7035);
            socket = new Socket("31.43.132.107", 7035);
            //socket = new Socket("127.0.0.1", 7035);


            DataOutputStream pw = new DataOutputStream(socket.getOutputStream());
            DataInputStream stream = new DataInputStream(socket.getInputStream());


            System.out.println(stream.readUTF());

            pw.writeUTF("size");
            pw.flush();
            System.out.println("send size");
            int size;
            while (true) {
                System.out.println("wait for size response ");
                size = stream.readInt();
                System.out.println(size);
                if (size != 0) {
                    break;
                }
            }
            System.out.println("receive: size" + size);

            pw.writeUTF("1970");
            pw.flush();
            System.out.println("send 1970");

            byte[] bytes = new byte[size];
            stream.read(bytes, 0, size);
            File file = new File("receive.txt");
            file.createNewFile();
            FileOutputStream foi = new FileOutputStream(file);
            foi.write(bytes);
            foi.flush();
            foi.close();

            pw.writeUTF("close_connection");
            pw.flush();

            pw.close();
            socket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
