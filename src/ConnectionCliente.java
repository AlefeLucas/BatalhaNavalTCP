
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

                if (object instanceof Boolean) {
                    servidor.notificarIniciou(((Boolean) object));
                    
                } else if (object instanceof Celula) {
                    servidor.atualizaCelulaInimiga((Celula) object);
                } else if (object instanceof Ponto) {
                    Celula resultado = servidor.serAtacado((Ponto) object);
                    write(resultado);
                } else if (object instanceof String && ((String) object).equals("WIN")) {
                    servidor.setVitoria();
                    System.exit(0);
                }

            } catch (IOException | ClassNotFoundException | InterruptedException e) {
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
        output.writeObject(object);
        output.flush();
    }

}
