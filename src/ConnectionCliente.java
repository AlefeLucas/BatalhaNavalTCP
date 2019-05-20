
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionCliente implements Runnable {

    private final Socket socket;
    private final Servidor servidor;

    private final Thread thread;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public ConnectionCliente(Socket socket, Servidor servidor) throws IOException {
        this.socket = socket;
        this.servidor = servidor;

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
                ConnectionCliente outroClinete = servidor.getOutroCliente(this);

                outroClinete.write(object);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
        }
    }

    public void write(Object object) throws IOException {
        output.writeObject(object);
        output.flush();
    }

}
