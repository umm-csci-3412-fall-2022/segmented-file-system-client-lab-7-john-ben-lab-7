package segmentedfilesystem;

import java.net.DatagramPacket;
import java.util.Arrays;

public class PacketCollector {

  public PacketCollector() {}

  public static Packet createPacket(DatagramPacket input){
    Packet output;
    byte[] data = input.getData();
    //Check if the packet is a body packet (the status byte is odd)
    if (data[0] & 1 == 1){
      output = new DataPacket();
      //Check if this is the last packet (by examining the second bit)
      output.islast = ((data[0] & 2) == 2);
      output.packetNumber = (256 * Byte.toUnsignedInt(data[2])) + Byte.toUnsignedInt(data[3]);

    } else { //The packet is a header packet
      output = new HeaderPacket();
      output.fileName = new String(Arrays.copyOfRange(data, 2, data.length));
    }

    output.fileID = data[1];
    return output;
    }
}
