import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.*; 
import javax.swing.event.*; 
import java.awt.*;
import java.util.*;
import java.io.*;
public class SongComboBoxPanel extends JPanel implements ActionListener,ItemListener,Serializable
{
  private JLabel selectSong, itemCodeLabel, descriptionLabel, artistLabel, albumLabel, priceLabel;
  private JComboBox songList;
  private JTextField itemCodeTextField, descriptionTextField, artistTextField, albumTextField, priceTextField;
  private JButton add, edit, delete, accept, cancel, exit;
  private ArrayList<Song> songs=new ArrayList<Song>();
  private Vector<String> songNames=new Vector<String>();
  private boolean addBoolean, editBoolean; //These booleans exist so that the accept() method can behave differently based on whether the button "add" or the button "edit" was just selected
  private ObjectOutputStream out;
  private ObjectInputStream in;
  private File songFile;
  public SongComboBoxPanel()
  {
    songFile=new File("SongFile.dat");
    processStreams();
    createSongPanel();
    createButtonPanel();
    addBoolean=false;
    editBoolean=false;
  }
  private void createSongPanel()
  {
    Song selectedSong=songs.get(0);
    selectSong=new JLabel("Select song: ");
    itemCodeLabel=new JLabel("Item code: ");
    itemCodeTextField=new JTextField(selectedSong.getItemCode(), 10);
    itemCodeTextField.setEditable(false);
    descriptionLabel=new JLabel("Description: ");
    descriptionTextField=new JTextField(selectedSong.getName(), 10);
    descriptionTextField.setEditable(false);
    artistLabel=new JLabel("Artist: ");
    artistTextField=new JTextField(selectedSong.getArtist(), 10);
    artistTextField.setEditable(false);
    albumLabel=new JLabel("Album: ");
    albumTextField=new JTextField(selectedSong.getAlbum(), 10);
    albumTextField.setEditable(false);
    priceLabel=new JLabel("Price: ");
    priceTextField=new JTextField(""+selectedSong.getPrice(), 10);
    priceTextField.setEditable(false);
    this.add(selectSong);
    this.add(songList);
    this.add(itemCodeLabel);
    this.add(itemCodeTextField);
    this.add(descriptionLabel);
    this.add(descriptionTextField);
    this.add(artistLabel);
    this.add(artistTextField);
    this.add(albumLabel);
    this.add(albumTextField);
    this.add(priceLabel);
    this.add(priceTextField);
    songList.addItemListener(this);
  }
  private void createButtonPanel()
  {
    add=new JButton("Add");
    edit=new JButton("Edit");
    delete=new JButton("Delete");
    accept=new JButton("Accept");
    cancel=new JButton("Cancel");
    exit=new JButton("Exit");
    this.add(add);
    this.add(edit);
    this.add(delete);
    this.add(accept);
    this.add(cancel);
    this.add(exit);
    add.addActionListener(this);
    edit.addActionListener(this);
    delete.addActionListener(this);
    accept.addActionListener(this);
    cancel.addActionListener(this);
    exit.addActionListener(this);
    accept.setEnabled(false);
    cancel.setEnabled(false);
  }
  public void actionPerformed(ActionEvent e)
  {
    Object source=e.getSource();
    if(source==add)
    {
      add();
    }
    else if(source==edit)
    {
      edit();
    }
    else if(source==delete)
    {
      delete();
    }
    else if(source==cancel)
    {
      cancel();
    }
    else if(source==exit)
    {
      exit();
    }
    else if(source==accept)
    {
      accept();
    }
  }
  private void add()
  {
    add.setEnabled(false);
    edit.setEnabled(false);
    delete.setEnabled(false);
    accept.setEnabled(true);
    cancel.setEnabled(true);
    itemCodeTextField.setText("");
    itemCodeTextField.setEditable(true);
    descriptionTextField.setText("");
    descriptionTextField.setEditable(true);
    artistTextField.setText("");
    artistTextField.setEditable(true);
    albumTextField.setText("");
    albumTextField.setEditable(true);
    priceTextField.setText("");
    priceTextField.setEditable(true);
    addBoolean=true;
    editBoolean=false;
  }
  private void edit()
  {
    add.setEnabled(false);
    edit.setEnabled(false);
    delete.setEnabled(false);
    accept.setEnabled(true);
    cancel.setEnabled(true);
    descriptionTextField.setEditable(true);
    artistTextField.setEditable(true);
    albumTextField.setEditable(true);
    priceTextField.setEditable(true);
    addBoolean=false;
    editBoolean=true;
  }
  private void delete()
  {
    int selected=songList.getSelectedIndex();
    songs.remove(selected);
    songNames.remove(selected);
    if(selected>=songs.size())
    {
      selected=0;
    }
    Song nextSong=songs.get(selected);
    songList.setSelectedIndex(selected);
  }
  private void cancel()
  {
    int selected=songList.getSelectedIndex();
    Song selectedSong=songs.get(selected);
    songList.setSelectedIndex(selected);
    setTextFields(selectedSong);
    editBoolean=false;
    addBoolean=false;
  }
  private void exit()
  {
    try
    {
      out=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("SongFile.dat")));
      for(int i=0; i<songs.size(); i++)
      {
        out.writeObject(songs.get(i));
      }
      out.close();
      System.exit(0);
    }
    catch(IOException e)
    {
      System.out.println("IOException thrown");
    } 
  }
  private void accept()
  {
    int selected=songList.getSelectedIndex();
    Song selectedSong=songs.get(selected);
    if(addBoolean==true && editBoolean==false)
    {
      if(!descriptionTextField.getText().equals("") && !itemCodeTextField.getText().equals("") && !artistTextField.getText().equals("") && !albumTextField.getText().equals("") && !priceTextField.getText().equals(""))
      {
        Song newSong=new Song(descriptionTextField.getText(), itemCodeTextField.getText(), artistTextField.getText(), albumTextField.getText(), Double.parseDouble(priceTextField.getText()));
        songs.add(newSong);
        songNames.add(newSong.getName());
        songList.setSelectedIndex(songs.size()-1);
      }
      else if(descriptionTextField.getText().equals("") || itemCodeTextField.getText().equals("") || artistTextField.getText().equals("") || albumTextField.getText().equals("") || priceTextField.getText().equals(""))
      {
        JOptionPane.showMessageDialog(this, "Your song cannot have blank information!", "Etiquette Violation", JOptionPane.ERROR_MESSAGE);
        cancel();
      }
    }
    else if(addBoolean==false && editBoolean==true)
    {
      if(!descriptionTextField.getText().equals(selectedSong.getName()) || !itemCodeTextField.getText().equals(selectedSong.getItemCode()) || !artistTextField.getText().equals(selectedSong.getArtist()) || !albumTextField.getText().equals(selectedSong.getAlbum()) || !priceTextField.getText().equals(""+selectedSong.getPrice()))
      {
        Song oldSong=selectedSong;
        selectedSong=new Song(descriptionTextField.getText(), itemCodeTextField.getText(), artistTextField.getText(), albumTextField.getText(), Double.parseDouble(priceTextField.getText()));
        songs.set(selected, selectedSong);
        songNames.set(selected, selectedSong.getName());
        songList.setSelectedIndex(selected);
      }
      else if(descriptionTextField.getText().equals(selectedSong.getName()) && itemCodeTextField.getText().equals(selectedSong.getItemCode()) && artistTextField.getText().equals(selectedSong.getArtist()) && albumTextField.getText().equals(selectedSong.getAlbum()) && priceTextField.getText().equals(""+selectedSong.getPrice()))
      {
        JOptionPane.showMessageDialog(this, "Please edit at least one text field!", "Etiquette Violation", JOptionPane.ERROR_MESSAGE);
        cancel();
      }
    }
    editBoolean=false;
    addBoolean=false;
  }
  public void itemStateChanged(ItemEvent e)
  {
    Object source=e.getSource();
    int selected=songList.getSelectedIndex();
    Song selectedSong=songs.get(selected);
    if(source==songList)
    {
      setTextFields(selectedSong);
      repaint();
    }
  }
  private void setTextFields(Song s)
  {
    itemCodeTextField.setText(s.getItemCode());
    descriptionTextField.setText(s.getName());
    artistTextField.setText(s.getArtist());
    albumTextField.setText(s.getAlbum());
    priceTextField.setText(""+s.getPrice());
    itemCodeTextField.setEditable(false);
    descriptionTextField.setEditable(false);
    artistTextField.setEditable(false);
    albumTextField.setEditable(false);
    priceTextField.setEditable(false);
    add.setEnabled(true);
    edit.setEnabled(true);
    delete.setEnabled(true);
    accept.setEnabled(false);
    cancel.setEnabled(false);
  }
  private void processStreams()
  {
    try
    {
      if(songFile.exists())
      {
        out=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(songFile, true)));
        in=new ObjectInputStream(new BufferedInputStream(new FileInputStream(songFile)));
        Song currentSong=new Song();
        while((currentSong=(Song)in.readObject())!=null)
        {
          songs.add(currentSong);
          songNames.add(currentSong.getName());
        }
      }
      else if(!songFile.exists())
      {
        Song[] songsProcessed={new Song("If We Hold On Together", "LBT88", "Diana Ross", "The Force Behind the Power", .99), new Song("I'm Returning Home", "TROLOLO", "Eduard Khil", "*single", .99), new Song("May it Be", "LOTR01", "Enya", "*single", 1.89), new Song("Johnny B. Goode", "88MPH", "Marvin Berry and the Starlighters", "Back to the Future Soundtrack", .99), new Song("Never Gonna Give You Up", "RICKROLL", "Rick Astley", "Whenever You Need Somebody", 1.29)};
        for(int i=0; i<songsProcessed.length; i++)
        {
          songs.add(songsProcessed[i]);
          songNames.add(songsProcessed[i].getName());
        }
      }
    }
    catch(EOFException e)
    {
      try
      {
        in.close();
      }
      catch(IOException j)
      {
        System.out.println("IOException thrown");
      }
    }
    catch(IOException e)
    {
      System.out.println("IOException thrown");
    }
    catch(ClassNotFoundException e)
    {
      System.out.println("ClassNotFoundException thrown");
    }
    finally
    {
      songList=new JComboBox(songNames);
    }
  }
}