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

class ClockFrame extends JFrame {
    private JLabel timeLabel;

    public ClockFrame() {
        timeLabel = new JLabel("", SwingConstants.CENTER);
        updateTime();
        add(timeLabel);
        Thread updateThread = new Thread(this::updateTimeLoop);
        updateThread.start();
    }

    private void updateTimeLoop() {
        try {
            while (true) {
                // Gửi yêu cầu lấy thời gian đến server
                Socket socket = new Socket("localhost", 1234);
                DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
                toServer.writeBytes("time\n");
                
                // Nhận thời gian từ server và cập nhật lên giao diện
                BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String currentTime = fromServer.readLine();
                timeLabel.setText(currentTime);
                
                // Đóng kết nối và chờ 1 giây trước khi gửi yêu cầu mới
                socket.close();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void updateTime() {
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        timeLabel.setText(currentTime);
    }
}