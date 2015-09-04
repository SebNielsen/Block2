package echoserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import shared.ProtocolStrings;
import utils.Utils;


public class EchoServer{

  private static boolean keepRunning = true;
  private static ServerSocket serverSocket;
  private static final Properties properties = Utils.initProperties("server.properties");
  private List<HandleClient> clients = new ArrayList<>();
 

  public static void stopServer() {
    keepRunning = false;
  }

  private void runServer()
  {
    int port = Integer.parseInt(properties.getProperty("port"));
    String ip = properties.getProperty("serverIp");
    
    Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Sever started. Listening on: "+port+", bound to: "+ip);
    try {
      serverSocket = new ServerSocket();
      serverSocket.bind(new InetSocketAddress(ip, port));
      do {
        Socket socket = serverSocket.accept(); //Important Blocking call
        Logger.getLogger(EchoServer.class.getName()).log(Level.INFO, "Connected to a client");        
        HandleClient handleClient = new HandleClient(socket, this);
        handleClient.start();
        clients.add(handleClient);
      } while (keepRunning);
    } 
    catch (IOException ex) {
      Logger.getLogger(EchoServer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void removeHandler(HandleClient client){
      clients.remove(client);
  }
  
  public void send(String msg){
      for(HandleClient client : clients){
          client.send(msg.toUpperCase());
      }
  } 

  public static void main(String[] args) {
     try{
        new EchoServer().runServer();
        String logFile = properties.getProperty("logFile");
        Utils.setLogFile(logFile,EchoServer.class.getName());
     }
     
     finally{
        Utils.closeLogger(EchoServer.class.getName());
     }
  }

}
