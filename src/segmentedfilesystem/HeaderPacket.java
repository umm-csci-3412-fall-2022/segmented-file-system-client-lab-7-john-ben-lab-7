package segmentedfilesystem;

public class HeaderPacket extends Packet {
  protected String fileName;

  public HeaderPacket() {}

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String n) {
    fileName = n;
  }
}
