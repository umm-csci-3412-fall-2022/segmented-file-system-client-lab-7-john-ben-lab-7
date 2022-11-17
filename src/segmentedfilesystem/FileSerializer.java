package segmentedfilesystem;
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.stream.Collector;
import java.util.Arrays;

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
      writeFile();
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
    //Check to confirm that we've recieved the last packet, all of the packets up to the last packet, and the
    //file name.
    return (fileSize > 0) && (numPackets == fileSize) && (fileName != null);
  }

  private byte[] convertListofBytes(ArrayList<Byte> l) {
     byte[] output = new byte[l.size()];
     for (int i = 0; i < l.size(); i++) {
      output[i] = l.get(i);
     }
     return output;
   }


  private void writeFile() throws IOException {
    FileOutputStream output = new FileOutputStream(fileName);
    //Extracts all the data from the data packets, and assembles them into the file body
    Collector<DataPacket, ArrayList<Byte>, byte[]> fileDataCollector = Collector.of(
      ArrayList<Byte>::new,
      ((x, y) -> {x.addAll(y.getBodyAsList());}),
      ((x, y) -> {x.addAll(y); return x;}),
      this::convertListofBytes
    );

    byte[] fileData = packets.stream()
      .sorted((x, y) -> x.getPacketNumber() - y.getPacketNumber())
      .collect(fileDataCollector);

    output.write(fileData);
    output.flush();
  }
}
