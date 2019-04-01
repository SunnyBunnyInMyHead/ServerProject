import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServer extends Thread {
    private ServerSocket serverSocket;
    private boolean alive = true;
    private Stop stop;

    public MessageServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    @Override
    public void run() {
        while (alive) {
            System.out.println("Waiting for clients...");
            try {
                final Socket soc = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        /*PrintWriter pw = new PrintWriter(soc.getOutputStream());
                        pw.print("If you read this message it means that you deal with it! " +
                                "Send me private message in telegram with text: \"I deal with it, the code 1970\". " +
                                "Send this code to the server and receive file .txt with task.");
                        pw.flush();
                        BufferedReader reader =
                                new BufferedReader(new InputStreamReader(soc.getInputStream()));
                        //Scanner scanner = new Scanner(soc.getInputStream());
                        if(reader.readLine().equals("stop")){
                            stop.onStop();
                        }else if (reader.readLine().equals("1970")){
                            DataOutputStream dataOutputStream = new DataOutputStream(soc.getOutputStream());
                            dataOutputStream.writeUTF();
                        }
                        reader.close();
                        pw.close();
                        soc.close();*/


                        DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
                        dos.writeUTF("If you read this message it means that you deal with it! " +
                                "Send me private message in telegram with text: \"I deal with it, the code 1970\". " +
                                "Send this code to the server and receive file .txt. Send \"size\" for receive file size(int). And dont forget close connection by the command \"close_connection\"");
                        dos.flush();
                        System.out.println("send start message");
                        DataInputStream dis = new DataInputStream(soc.getInputStream());
                        while (true) {
                            String command = "";
                            try {
                                System.out.println("wait for command");
                                command = dis.readUTF();
                                receiveCommand(command, dos, () -> {
                                    dis.close();
                                    dos.close();
                                    soc.close();
                                });
                                if(command.equals("close_connection")||command.equals("stop")){
                                    break;
                                }
                            } catch (EOFException e){
                                e.getMessage();
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                                break;
                            }
                        }


                    } catch (IOException e) {
                        //e.printStackTrace();
                    }
                });
                thread.setDaemon(true);
                thread.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection completed");
        }
        System.out.println("Finish");
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    private void receiveCommand(String command, DataOutputStream dos, Stop stop1) throws IOException {
        Consts.Commands commands = Utils.getCommand(command);
        if(command==null){
            return;
        }
        switch (commands) {
            case STOP: {
                System.out.println("receive stop");
                stop.onStop();
                break;
            }
            case SIZE: {
                System.out.println("receive size");
                File file = new File("task.txt");
                dos.writeInt((int) file.length());
                dos.flush();
                break;
            }
            case TASK_FILE: {
                System.out.println("receive 1970");
                File file = new File("task.txt");
                byte[] mybytearray = new byte[(int) file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(mybytearray, 0, mybytearray.length);
                dos.write(mybytearray, 0, mybytearray.length);
                dos.flush();
                break;
            }
            case CLOSE_CONNECTION: {
                System.out.println("receive close_connection");
                stop1.onStop();
                break;
            }
        }
    }
}
