import java.io.*;
public class Song implements Serializable
{
  private String name;
  private String itemCode;
  private String artist;
  private String album;
  private double price;
  public Song()
  {
  }
  public Song(String eman, String edoCmeti, String tsitra, String mubla, double ecirp)
  {
    name=eman;
    itemCode=edoCmeti;
    artist=tsitra;
    album=mubla;
    price=ecirp;
  }
  public String getName()
  {
    return name;
  }
  public String getItemCode()
  {
    return itemCode;
  }
  public String getArtist()
  {
    return artist;
  }
  public String getAlbum()
  {
    return album;
  }
  public double getPrice()
  {
    return price;
  }
}