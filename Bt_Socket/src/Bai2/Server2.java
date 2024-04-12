package Bai2;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Server2 {
    public static void main(String[] args) {
        // Khởi tạo và hiển thị giao diện đồng hồ
        ClockFrame clockFrame = new ClockFrame();
        clockFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clockFrame.setSize(300, 100);
        clockFrame.setVisible(true);

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is running on port 1234");
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = fromClient.readLine();
                if (message != null && message.equals("time")) {
                    String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                    toClient.writeBytes(currentTime + "\n");
                    System.out.println("Sent time to client: " + currentTime);
                }
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
