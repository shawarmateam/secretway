import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ByteArrayServer {
    public static void main(String[] args) {
        int port = 1201; // Порт, на котором будет работать сервер

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен. Ожидание подключения клиента...");

            // Ожидание подключения клиента
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                // Чтение данных из входного потока
                InputStream inputStream = clientSocket.getInputStream();
                byte[] buffer = new byte[2200]; // Буфер для чтения данных
                int bytesRead;

                // Чтение данных в массив байтов
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    // Обработка полученных данных
                    // Например, можно создать новый массив байтов нужного размера
                    byte[] dataReceived = new byte[bytesRead];
                    System.arraycopy(buffer, 0, dataReceived, 0, bytesRead);

                    // Здесь можно обработать полученные данные
                    System.out.println("Получено " + bytesRead + " байт.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

