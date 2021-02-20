import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main (String[] args) {
        startClient();
    }


    private static void startClient(){
        try(Socket socket = new Socket("localHost", 8100);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out =new PrintWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in)) {
            String myMessage = " ";

            Thread serverReader =new Thread(() -> {
                String sMessage = " ";
                try {
                    while (!socket.isClosed()){
                        sMessage =in.readLine();
                        System.out.println( sMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverReader.start();


            while (!myMessage.equalsIgnoreCase("close")){

                myMessage = scanner.nextLine();
                out.println(myMessage);
                out.flush();
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
