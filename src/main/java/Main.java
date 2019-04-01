import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("process started....");
        MessageServer messageServer = new MessageServer(7035);
        messageServer.setStop(() -> {
            System.out.println("receive stop");
            messageServer.setAlive(false);
            messageServer.interrupt();
            System.exit(0);
        });
        messageServer.start();
    }
}
