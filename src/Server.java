import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main (String[] args) {
        startServer();

    }

    private static void startServer(){
        try  (ServerSocket serverSocket = new ServerSocket(8100)){
            System.out.println("Сервер готов");
            try(Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out =new PrintWriter(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in)) {


                System.out.println("Клиент подключился");
                out.println("Привет!!!");
                out.flush();


                String message = "";

                Thread thread = new Thread(() -> {

                    String sMassage = "";
                    while (!socket.isClosed()) {
                        sMassage = scanner.nextLine();
                        out.println("сервер:" + sMassage);
                        out.flush();


                    }
                });
                thread.start();

                do {
                    message =in.readLine();
                    System.out.println("клиент :" +message);
                    out.flush();
                }while (!message.equalsIgnoreCase("close"));


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
