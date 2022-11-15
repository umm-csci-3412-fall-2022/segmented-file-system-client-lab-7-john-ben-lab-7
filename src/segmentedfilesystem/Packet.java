package segmentedfilesystem;

public abstract class Packet {
  protected byte fileID;

  public byte getFileID() {
    return fileID;
  }

  public void setFileID(byte i) {
    fileID = i;
  }
}
