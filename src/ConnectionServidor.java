
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ConnectionServidor implements Runnable {

    private final Socket socket;
    private final Cliente cliente;

    private final Thread thread;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    public ConnectionServidor(Socket socket, Cliente cliente) throws IOException {
        this.socket = socket;
        this.cliente = cliente;

        this.thread = new Thread(this);
        this.thread.start();

        output = new ObjectOutputStream(this.socket.getOutputStream());
        input = new ObjectInputStream(this.socket.getInputStream());
    }

    public void write(Object object) throws IOException {
        output.writeObject(object);
        output.flush();
    }

    @Override
    public void run() {
        Object object;

        while (true) {
            try {
                object = input.readObject();

                if (object instanceof Boolean) {
                    cliente.notificarIniciou(((Boolean) object));
                    
                } else if (object instanceof Celula) {
                    cliente.atualizaCelulaInimiga((Celula) object);
                } else if (object instanceof Ponto) {
                    Celula resultado = cliente.serAtacado((Ponto) object);
                    write(resultado);
                } else if (object instanceof String && ((String) object).equals("WIN")) {
                    cliente.setVitoria();
                    System.exit(0);
                }

            } catch (IOException | ClassNotFoundException ex) {
                if (ex.getMessage() != null && ex.getMessage().equals("Connection reset")) {
                    System.out.println("Fechado;");
                    System.exit(1);
                }
                ex.printStackTrace();
            } catch (NullPointerException e) {
                //FAZ NADA
            }
        }

    }

}
