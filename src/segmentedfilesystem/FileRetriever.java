package segmentedfilesystem;
import java.util.HashMap;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;

public class FileRetriever {

        HashMap<Byte, FileSerializer> files;
        int port;
        String hostName;
        private static final int FILES_EXPECTED = 3;

        public FileRetriever(String hostName, int port) {
          this.hostName = hostName;
          this.port = port;
          //server = new InetSocketAddress(hostName, port);
          files = new HashMap<Byte, FileSerializer>();
	}

	public void downloadFiles() throws IOException {
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.
          DatagramSocket s = new DatagramSocket(port, InetAddress.getByName(hostName));
          DatagramPacket p = new DatagramPacket(new byte[1028], 1028);
          int filesWritten = 0;
          while (filesWritten < FILES_EXPECTED) {
            s.receive(p);
            Packet pack = PacketCollector.createPacket(p);
            if (addPacket(pack)) {
              filesWritten++;
            };
          }
	}

        private boolean addPacket(Packet p) throws IOException {
          files.putIfAbsent(p.getFileID(), new FileSerializer());
          return files.get(p.getFileID()).addPacket(p);
        }

}
