import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyRunnable extends Thread {

    private final Socket client;
    private final int counter;
    private final List<Socket> clients;

    public MyRunnable(Socket client, int counter, List<Socket> clients) {
        this.client = client;
        this.clients = clients;
        this.counter = counter;
    }

    private void sendToAllClients(String mess) {
        clients.forEach(socket -> {
            try {
                PrintStream client_output = new PrintStream(socket.getOutputStream());

                OutputStream out = socket.getOutputStream();

                out.write((socket.getInetAddress()+" sent you : "+mess).getBytes(StandardCharsets.UTF_8));
                client_output.flush();

                //client_output.println("Coucou de la part de " + counter);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    public void run() {
        try {

            PrintStream client_output = new PrintStream(client.getOutputStream());
            client_output.println("Bienvenue client n°" + counter + " sur mon serveur !"); //KEY
            client_output.flush();



            BufferedReader client_input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (client_input.readLine() != null) {
                sendToAllClients(client_input.readLine());
            }

            client.close();
            System.out.println("Client n° " + counter + " disconnected");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
