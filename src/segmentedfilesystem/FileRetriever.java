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
          files = new HashMap<Byte, FileSerializer>();
	}

	public void downloadFiles() throws IOException {
          DatagramSocket s = new DatagramSocket();
          //Create a dummy packet to send to the server to initialize the connection
          DatagramPacket sendP = new DatagramPacket(new byte[1028], 1028, InetAddress.getByName(hostName), port);
          s.send(sendP);
          DatagramPacket p = new DatagramPacket(new byte[1028], 1028);
          int filesWritten = 0;
          while (filesWritten < FILES_EXPECTED) {
            s.receive(p);
            Packet pack = PacketCollector.createPacket(p);
            //addPacket() returns true if the last packet is added and the
            //file is written out.
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
