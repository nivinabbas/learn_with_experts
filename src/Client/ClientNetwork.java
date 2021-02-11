package Client;

import Helpers.CustomException;

import java.io.*;
import java.net.Socket;

public class ClientNetwork {

    public static Socket soc;
    public static PrintWriter printSocket;
    public static BufferedReader readSocket;

    public static void connectToServer() throws Exception {
        soc=new Socket("localHost",6810);
        System.out.println(soc.isConnected());
        readSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        printSocket= new PrintWriter(new OutputStreamWriter(soc.getOutputStream()),true);
    }

    public static void sendToServer(String s) {
        printSocket.println(s);
    }

    public static String  readFromServer() {
        try {
            return readSocket.readLine();
        } catch (IOException e) {
            try {
                throw new CustomException("error reading from server");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

}
