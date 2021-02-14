package Client;

import Helpers.CustomException;

import java.io.*;
import java.net.Socket;

public class ClientNetwork {

    public static Socket soc;
    public static PrintWriter printSocket;
    public static BufferedReader readSocket;

    public static void connectToServer() throws Exception {
        try {
            soc = new Socket("20.52.4.81", 6810);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(soc.isConnected());

        readSocket = new BufferedReader(new InputStreamReader(soc.getInputStream()));
        printSocket = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()), true);
        printSocket.println(String.join(";FayezIbrahimNivin;", UserProberties.name, UserProberties.role, UserProberties.field, UserProberties.encodedImage));

        UserProberties.id = Integer.parseInt(readSocket.readLine());
        System.out.println("connected to the server!!, my id is " + UserProberties.id);
    }

    public static boolean isConnected() {
        return soc != null;
    }

    public static void disconnect() throws Exception {
        if (isConnected()) {
            soc.close();
            soc = null;
        }
    }

    public static void sendToServer(String s) {
        printSocket.println(s);
    }

    public static String readFromServer() {
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
