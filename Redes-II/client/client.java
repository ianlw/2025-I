import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteFTP {
    private static final String HOST = "localhost";
    private static final int PUERTO = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

            Scanner scanner = new Scanner(System.in);

            System.out.print(in.readUTF());  
            out.writeUTF(scanner.nextLine());

            System.out.print(in.readUTF());   
            out.writeUTF(scanner.nextLine());

            String respuesta = in.readUTF();
            System.out.println(respuesta);

            if (respuesta.contains("incorrectas")) {
                return;
            }

            // Mostrar archivos
            while (true) {
                String linea = in.readUTF();
                if (linea.contains("Ingresa")) {
                    System.out.print(linea);
                    break;
                }
                System.out.println("• " + linea);
            }

            String archivo = scanner.nextLine();
            out.writeUTF(archivo);

            String estado = in.readUTF();
            System.out.println(estado);

            if (estado.contains("Iniciando")) {
                // Recibir el nombre real del archivo desde el servidor
                String nombreArchivo = in.readUTF();  // <- nuevo

                FileOutputStream fos = new FileOutputStream(nombreArchivo);
                byte[] buffer = new byte[4096];
                int bytes;
                InputStream rawIn = socket.getInputStream();

                while ((bytes = rawIn.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytes);
                    if (bytes < 4096) break; // Fin de archivo
                }

                fos.close();
                System.out.println("Archivo descargado como: " + nombreArchivo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
