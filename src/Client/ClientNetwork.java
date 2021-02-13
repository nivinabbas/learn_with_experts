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

    public static void readOnlineUsers() {
        printSocket.println("GET_ONLINE_USERS");
        System.out.println("getting online users from server....");
        try {
            String onlineUsersStr = readSocket.readLine();

            String[] x = onlineUsersStr.split(";user_seperator;");
            System.out.println("length is " + x.length);


            if (onlineUsersStr != null) {
                for (String str : onlineUsersStr.split(";user_seperator;")) {
                    String[] userInfo = str.split(";FayezIbrahimNivin;");
                    User user = new User(Integer.parseInt(userInfo[0]), userInfo[1], userInfo[2], userInfo[3], userInfo[4]);
                    String oppositeRole = UserProberties.role.equals("Novice") ? "Expert" : "Novice";

                    boolean doesExist = UserProberties.onlineUsers.contains(user);
                    boolean isOppositeRoles = user.getRole().equals(oppositeRole);
                    boolean isSameField = user.getField().equals(UserProberties.field);

                    System.out.println("str: " + str);

                    if (!doesExist && isOppositeRoles && isSameField)
                        UserProberties.onlineUsers.add(user);
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading online users from server");
        }

    }

}
