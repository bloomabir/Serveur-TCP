
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ServerTCP {

    private final ServerSocket socket;
    private final FileWriter logs;
    private List<Socket> clients;

    private final SimpleDateFormat date_format = new SimpleDateFormat(
            "dd-MM-yyyy"
    );
    private final SimpleDateFormat hour_format = new SimpleDateFormat(
            "hh:mm:ss"
    );

    /**
     * The constructor
     *
     * @param port     the src port
     * @param logsPath the file to append the logs
     */
    /* KEY */
    public ServerTCP(int port, String logsPath) throws IOException {
        this.socket = new ServerSocket(port);
        this.logs = new FileWriter(logsPath, true);
        this.clients = new ArrayList<>();
    }

    /* KEY */
    public void startServer() throws IOException {
        int counter = 0;
        /* Listen for incoming connections */
        while (!this.socket.isClosed()) {
            Socket client = socket.accept();
            counter++

            this.clients.add(client);
            setClients(clients.stream().filter(s -> !s.isClosed()).collect(Collectors.toList()));

            System.out.println("Number of connected users : " + clients.size());

            Date date = new Date();
            String date_str = date_format.format(date);
            String hour_str = hour_format.format(date);
            String log = date_str + " " + hour_str + " : Connection from " + client.getInetAddress();

            System.out.println(log);

            logs.write(log + "\n");
            logs.flush();

            new MyRunnable(client, counter, clients.stream().filter(c -> !c.equals(client)).toList()).start();
        }

        logs.close();
    }

    public void setClients(List<Socket> clients) {
        this.clients = clients;
    }
}
