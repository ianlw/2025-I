// ServidorFTP.java
import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorFTP {
    private static final int PUERTO = 5000;
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor FTP esperando en el puerto " + PUERTO);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                out.writeUTF("Usuario: ");
                String user = in.readUTF();
                out.writeUTF("Contraseña: ");
                String pass = in.readUTF();

                if (!user.equals(USER) || !pass.equals(PASSWORD)) {
                    out.writeUTF("Usuario o contraseña incorrecta");
                    socket.close();
                    continue;
                }

                File carpeta = new File(".");
                File[] archivos = carpeta.listFiles(File::isFile);

                out.writeUTF("Archivos disponibles:");
                for (int i = 0; i < archivos.length; i++) {
                    out.writeUTF(i + ": " + archivos[i].getName());
                }

                out.writeUTF("Ingresa el número del archivo a descargar:");
                int indice = Integer.parseInt(in.readUTF());

                if (indice < 0 || indice >= archivos.length) {
                    out.writeUTF("Índice de archivo inválido.");
                } else {
                    File archivo = archivos[indice];
                    out.writeUTF("Iniciando descarga: " + archivo.getName());
                    out.writeUTF(archivo.getName()); 

                    FileInputStream fis = new FileInputStream(archivo);
                    byte[] buffer = new byte[4096];
                    int bytes;
                    while ((bytes = fis.read(buffer)) != -1) {
                        out.write(buffer, 0, bytes);
                    }
                    fis.close();
                }

                socket.close();
                System.out.println("Transferencia finalizada.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
