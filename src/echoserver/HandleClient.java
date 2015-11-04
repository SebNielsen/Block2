/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;

/**
 *
 * @author sebastiannielsen
 */
public class HandleClient extends Thread {
    private Socket socket;
    private Scanner input;
    private PrintWriter writer;
    private EchoServer server;
    private String message;
    
    public HandleClient(Socket socket, EchoServer server) throws IOException {
    this.socket = socket;
    this.server = server;
    input = new Scanner(socket.getInputStream());
    writer = new PrintWriter(socket.getOutputStream(), true);

    }
    
    @Override
    public void run(){
        try {
            message = input.nextLine(); //IMPORTANT blocking call
            Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ",message));
            while (!message.equals(ProtocolStrings.STOP)) {
//                writer.println(message.toUpperCase());
                server.send(message);
                Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, String.format("Received the message: %1$S ",message.toUpperCase()));
                message = input.nextLine(); //IMPORTANT blocking call
            }
            writer.println(ProtocolStrings.STOP);//Echo the stop message back to the client for a nice closedown
            socket.close();
            server.removeHandler(this);
            
            Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Closed a Connection");
        } catch (IOException ex) {
            Logger.getLogger(HandleClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void send(String msg){
        writer.println(msg);
    }
    
}
