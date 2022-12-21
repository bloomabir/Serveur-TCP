package exo1;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintStream;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

public class ServerTCP {

    private ServerSocket socket;
    private FileWriter logs;

    private final SimpleDateFormat date_format = new SimpleDateFormat(
        "dd-MM-yyyy"
    );
    private final SimpleDateFormat hour_format = new SimpleDateFormat(
        "hh:mm:ss"
    );


    /**
     * The constructor
     * @param port the src port 
     * @param logsPath the file to append the logs
     */
     public ServerTCP(int port, String logsPath) {
        try {
            this.socket = new ServerSocket(port);
            this.logs = new FileWriter(logsPath, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    //KEY
    public void startServer() {
        Socket client = null;
        PrintStream client_output = null;
        Date date = new Date();
        String date_str = "";
        String hour_str = "";
        String log = "";

        //TimeUnit time = TimeUnit.SECONDS;
        //time.sleep(1L);
        System.out.println("cc");
        try {
            while (!this.socket.isClosed()) {
                client = socket.accept(); //KEY;
                
                date_str = date_format.format(date);
                hour_str = hour_format.format(date);

                log = date_str + " " + hour_str + " : Connection from " + client.getInetAddress() + "\n";
                System.out.println(log);
                logs.write(log);



                client_output = new PrintStream(client.getOutputStream()); //KEY
                client_output.print("Bienvenue sur mon serveur et au revoir \n"); //KEY

                client.close();
                logs.close();
            }
        } catch (IOException i) {
            System.out.println(i.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        ServerTCP server = new ServerTCP(2024, "../server_exo1.log"); 
        server.startServer();
    }
}