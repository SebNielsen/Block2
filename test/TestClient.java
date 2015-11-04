import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestClient {
 private Scanner input;
    public TestClient() {
  }
  
  @BeforeClass
  public static void setUpClass() {
      new Thread(new Runnable(){
      @Override
      public void run() {
        EchoServer.main(null);
      }
    }).start();
  }
  
  @AfterClass
  public static void tearDownClass() {
    EchoServer.stopServer();
  }
  
  @Before
  public void setUp() {
  }
  
  @Test
  public void send() throws IOException{
    EchoClient tester = new EchoClient();
    tester.connect("localhost", 9090);
    input = tester.
    tester.send("USER#Sebastian");
    assertEquals("USERLIST#Sebastian", );
  }
}
