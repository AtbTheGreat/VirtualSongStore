import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.*; 
import javax.swing.event.*; 
import java.awt.*;
import java.util.*;
import java.io.*;
public class SongComboBoxFrame extends JFrame implements Serializable
{
  public SongComboBoxFrame()
  {
    setTitle("Your Music Library");
    setSize(220, 310);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel=new SongComboBoxPanel();
    this.add(panel);
  }
  public static void main(String[] args)
  {
    JFrame frame=new SongComboBoxFrame();
    frame.setVisible(true);
  }
}