package segmentedfilesystem;
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.FileOutputStream;

public class FileSerializer {
  String fileName;
  int numPackets = 0;
  int fileSize = 0;
  PriorityQueue<DataPacket> packets = new PriorityQueue<DataPacket>(1028, (x, y) -> x.getPacketNumber() - y.getPacketNumber());

  public FileSerializer() {}

  public boolean addPacket(Packet p) throws IOException {
    if (p instanceof HeaderPacket) {
      HeaderPacket hp = (HeaderPacket) p;
      addHeaderPacket(hp);
    } else if (p instanceof DataPacket) {
      DataPacket dp = (DataPacket) p;
      addDataPacket(dp);
    }
    if (isWritable()) {
      //Ship the File
      return true;
    }
    return false;
  }

  private void addHeaderPacket(HeaderPacket p) {
    fileName = p.getFileName();
  }

  private void addDataPacket(DataPacket p) {
    packets.add(p);
    numPackets++;
    if (p.isLastByte()) {
      fileSize = p.getPacketNumber() + 1;
    }
  }

  private boolean isWritable() {
    return (numPackets == fileSize) && (fileName != null);
  }

  private void writeFile() throws IOException {
    FileOutputStream output = new FileOutputStream(fileName);
    byte[] fileData = packets.stream()
                      .collect(ArrayList<Byte>::new,
                              ((x, y) -> {x.addAll(y.getBody());}),
                              ((x, y) -> {x.addAll(y);}),
                              ((x) -> {x.toArray(new byte[]{});}));
    output.write(fileData);
    output.flush();
  }
}
