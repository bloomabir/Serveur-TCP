import java.io.IOException;

public class Main {
    public static void main(Strin[] args) {
        try {
            ServerTCP server = new ServerTCP(2000, "server_exo2.log");
            server.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
