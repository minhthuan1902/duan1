package baitapsocketbai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<Socket> clientSockets = new ArrayList<>();
    private static List<String> clientUsernames = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server started. Waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        private String username;

        public ClientHandler(Socket socket) {
            try {
                this.clientSocket = socket;
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                out.println("Enter your username:");
                username = in.readLine();
                clientUsernames.add(username);
                clientSockets.add(clientSocket);

                sendMessageToAllClients(username + " has joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    sendMessageToAllClients(username + ": " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                clientUsernames.remove(username);
                clientSockets.remove(clientSocket);
                sendMessageToAllClients(username + " has left the chat.");

                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendMessageToAllClients(String message) {
            for (Socket socket : clientSockets) {
                try {
                    PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
                    socketOut.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
