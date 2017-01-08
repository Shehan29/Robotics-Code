package DevilVision;

/**
 * @author Tom Bottiglieri Team 254, The Cheesy Poofs
 */
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

public class DevilVisionServer implements Runnable {

    private static DevilVisionServer instance_;
    Thread serverThread = new Thread(this);
    private int listenPort_;
    private Vector connections_;
    private boolean curLeftStatus_ = false, curRightStatus_ = false;
    double lastHeartbeatTime_ = -1;
    private boolean listening_ = true;

    public static DevilVisionServer getInstance() {
        if (instance_ == null) {
            instance_ = new DevilVisionServer();
        }
        return instance_;
    }

    public static DevilVisionServer getInstance(int port) {
        if (instance_ == null) {
            instance_ = new DevilVisionServer(port);
        }
        return instance_;
    }

    public void start() {
        serverThread.start();
    }

    public void stop() {
        listening_ = false;
    }

    private DevilVisionServer() {
        this(1180);
    }

    private DevilVisionServer(int port) {
        listenPort_ = port;
        connections_ = new Vector();
    }

    public boolean hasClientConnection() {
        return lastHeartbeatTime_ > 0 && (Timer.getFPGATimestamp() - lastHeartbeatTime_) < 3.0;
    }

    public void setPort(int port) {
        listenPort_ = port;
    }

    public void reset() {
        curLeftStatus_ = curRightStatus_ = false;
    }

    public boolean getLeftStatus() {
        return curLeftStatus_;
    }

    public boolean getRightStatus() {
        return curRightStatus_;
    }

    // This class handles incoming TCP connections
    private class VisionServerConnectionHandler implements Runnable {

        SocketConnection connection;

        public VisionServerConnectionHandler(SocketConnection c) {
            connection = c;
        }

        public void run() {
            try {
                InputStream is = connection.openInputStream();

                int ch = 0;
                byte[] b = new byte[1024];
                double timeout = 5;
                double lastHeartbeat = Timer.getFPGATimestamp();
                DevilVisionServer.this.lastHeartbeatTime_ = lastHeartbeat;
                while (Timer.getFPGATimestamp() < lastHeartbeat + timeout) {
                    boolean gotData = false;
                    while (is.available() > 0) {
                        gotData = true;
                        int read = is.read(b);
                        for (int i = 0; i < read; ++i) {
                            byte reading = b[i];
                            boolean leftStatus = (reading & (1 << 1)) > 0;
                            boolean rightStatus = (reading & (1 << 0)) > 0;
                            DevilVisionServer.this.curLeftStatus_ = leftStatus;
                            DevilVisionServer.this.curRightStatus_ = rightStatus;
                        }
                        lastHeartbeat = Timer.getFPGATimestamp();
                        DevilVisionServer.this.lastHeartbeatTime_ = lastHeartbeat;
                    }

                    try {
                        Thread.sleep(50); // sleep a bit
                    } catch (InterruptedException ex) {
                        System.out.println("Thread sleep failed.");
                    }
                }
                is.close();
                connection.close();

            } catch (IOException e) {
            }
        }
    }

  // run() to implement Runnable
    // This method listens for incoming connections and spawns new
    // VisionServerConnectionHandlers to handle them
    public void run() {
        ServerSocketConnection s = null;
        try {
            s = (ServerSocketConnection) Connector.open("serversocket://:" + listenPort_);
            while (listening_) {
                SocketConnection connection = (SocketConnection) s.acceptAndOpen();
                Thread t = new Thread(new DevilVisionServer.VisionServerConnectionHandler(connection));
                t.start();
                connections_.addElement(connection);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    System.out.println("Thread sleep failed.");
                }
            }
        } catch (IOException e) {
            System.out.println("Socket failure.");
        }
    }
}