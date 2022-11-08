package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketCollector {
  public PacketCollector() {

  }

  public Packet createPacket(DatagramPacket input){
    Packet output;
    byte[] data = input.getData();
    if (data[0] & 1){
      output = new BodyPacket();
      output.islast = data[0] & 2;
      output.packetNumber = (256 * Byte.toUnsignedInt(data[2]) + Byte.toUnsignedInt(data[3]));

    } else {
      output = new HeaderPacket();
      output.fileName = new String(Arrays.copyOfRange(data, 2, data.length));
    }

    output.fileID = data[1];
    return output;

}
