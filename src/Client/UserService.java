package Client;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class UserService extends Service<String> {
    @Override
    protected Task createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws InterruptedException {
                System.out.println("waiting a message from server......");
                String s = ClientNetwork.readFromServer();
                return s;
            }
        };
    }
}
