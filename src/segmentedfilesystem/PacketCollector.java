package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketCollector {

  public PacketCollector() {}

  public static Packet createPacket(DatagramPacket input){
    byte[] data = input.getData();
    //Check if the packet is a body packet (the status byte is odd)
    if (data[0] % 2 == 1){
      DataPacket output = new DataPacket();
      //Check if this is the last packet (by examining the second bit)
      output.setisLast(((data[0] % 4) == 3));
      output.setPacketNumber((256 * Byte.toUnsignedInt(data[2])) + Byte.toUnsignedInt(data[3]));
      output.setFileID(data[1]);
      return output;

    } else { //The packet is a header packet
      HeaderPacket output = new HeaderPacket();
      output.setFileName(new String(Arrays.copyOfRange(data, 2, data.length)));
      output.setFileID(data[1]);
      return output;
    }
    }
}
