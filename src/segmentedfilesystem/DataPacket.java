package segmentedfilesystem;

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

}
