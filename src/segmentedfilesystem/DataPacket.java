package segmentedfilesystem;
import java.util.ArrayList;

public class DataPacket extends Packet {
  protected int packetNumber;
  protected byte[] body;
  protected boolean isLast;

  public DataPacket() {}

  public int getPacketNumber() {
    return packetNumber;
  }

  public boolean isLastByte() {
    return isLast;
  }

  public byte[] getBody() {
    return body;
  }

  public void setBody(byte[] body) {
    this.body = body;
  }

  public void setPacketNumber(int n) {
    packetNumber = n;
  }

  public void setisLast(boolean b) {
    isLast = b;
  }

  public ArrayList<Byte> getBodyAsList() {
    ArrayList<Byte> output = new ArrayList<Byte>();
    for (byte b: body) {
      output.add(b);
    }
    return output;
  }
}
