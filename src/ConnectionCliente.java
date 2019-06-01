
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionCliente implements Runnable {

    private final Socket socket;
    private final Servidor servidor;
    private int id;

    private final Thread thread;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public ConnectionCliente(int id, Socket socket, Servidor servidor) throws IOException {
        this.socket = socket;
        this.servidor = servidor;
        this.id = id;
        this.thread = new Thread(this);
        this.thread.start();

        output = new ObjectOutputStream(this.socket.getOutputStream());
        input = new ObjectInputStream(this.socket.getInputStream());
    }

    @Override
    public void run() {
        Object object;
        while (true) {
            try {
                object = input.readObject();

                if (object != null) {
                    System.out.println("RECEBENDO " + String.format("%8s", object.getClass().getSimpleName()) + " DO CLIENTE " + id);
                }
                ConnectionCliente outroClinete = servidor.getOutroCliente(this);

                outroClinete.write(object);

            } catch (IOException | ClassNotFoundException e) {
                if (e.getMessage().equals("Connection reset")) {
                    System.out.println("Fechado;");
                    System.exit(1);
                }
                e.printStackTrace();
            } catch (NullPointerException e) {
                //FAZ NADA
            }
        }
    }

    public void write(Object object) throws IOException {
        if (object != null) {
            System.out.println("ENVIANDO  " + String.format("%8s", object.getClass().getSimpleName()) + " AO CLIENTE " + id);
        }
        output.writeObject(object);
        output.flush();
    }

}
