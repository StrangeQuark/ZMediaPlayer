import java.awt.Color;//Colors
import java.awt.event.ActionEvent;//Allow for action events like clicking
import java.awt.event.ActionListener;//ActionListener

import java.io.File;
import java.io.FilenameFilter;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class ZMediaList extends JPanel implements ListSelectionListener, ActionListener
{
  private ZMediaSettingsFrame settings;
  private File musicPath;
  private String[] artistStringArray;
  private File[] artistFileArray;
  private String[] albumStringArray;
  private File[] albumFileArray;
  private String[] songStringArray;
  static File[] songFileArray;
  static int songIndex;
  private JList artistList;
  private JList albumList;
  private JList songList;
  private JScrollPane artistScrollPane;
  private JScrollPane albumScrollPane;
  private JScrollPane songScrollPane;
  private ListSelectionModel lsm;
  private ListSelectionModel lsmArtist;
  private ListSelectionModel lsmAlbum;
  private ListSelectionModel lsmSong;
  private ArrayList albumFileArrayList;
  private ArrayList albumStringArrayList;
  private ArrayList songFileArrayList;
  private ArrayList songStringArrayList;
  private JButton buttonClearSongs;
  private JButton buttonClearAlbums;
  
  public ZMediaList()
  {
    settings = new ZMediaSettingsFrame();
    musicPath = new File(settings.musicDirectoryTextField.getText());
    settings.dispose();
    setArtistArrays();
    setAlbumArrays();
    setSongArrays();
    
    artistList = new JList(artistStringArray);
    lsmArtist = artistList.getSelectionModel();
    lsmArtist.addListSelectionListener(this);
    artistScrollPane = new JScrollPane(artistList);
    
    albumList = new JList(albumStringArray);
    lsmAlbum = albumList.getSelectionModel();
    lsmAlbum.addListSelectionListener(this);
    albumScrollPane = new JScrollPane(albumList);
    
    songList = new JList(songStringArray);
    lsmSong = songList.getSelectionModel();
    lsmSong.addListSelectionListener(this);
    songScrollPane = new JScrollPane(songList);
    
    buttonClearSongs = new JButton("Clear songs");
    buttonClearSongs.addActionListener(this);
    
    buttonClearAlbums = new JButton("Clear albums");
    buttonClearAlbums.addActionListener(this);
    
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    
    artistScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    albumScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    songScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    artistScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
    albumScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
    songScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
    
    add(artistScrollPane);
    add(albumScrollPane);
    add(songScrollPane);
  }
  
  public void actionPerformed(ActionEvent e)//Override ActionListener method
  {
    String currentEvent = e.getActionCommand();
    
    if(currentEvent == "Clear songs")
    {
      if(albumScrollPane.getColumnHeader() != null)
      {
        File[] tempFiles = new File[albumFileArray.length];
        String[] tempStrings = new String[albumFileArray.length];
        
        for(int i = 0; i < albumFileArray.length; i++)
        {
          tempFiles[i] = albumFileArray[i];
          tempStrings[i] = albumStringArray[i];
        }
        
        setAlbumArrays();
        setSongArrays();
        
        albumFileArray = tempFiles;
        albumStringArray = tempStrings;
      }
      else
      {
        setAlbumArrays();
        setSongArrays();
      }

      remove(songScrollPane);
    
      songScrollPane = new JScrollPane(songList = new JList(songStringArray));
    
      lsmSong = songList.getSelectionModel();
      lsmSong.addListSelectionListener(this);
    
      add(songScrollPane);
      
      if(ZMediaSettingsFrame.darkModeCheckBox.isSelected())
      {
        setDarkModeOn();
      }
      
      setVisible(false);
      setVisible(true);
    }
    
    if(currentEvent == "Clear albums")
    {
      setAlbumArrays();
      
      remove(albumScrollPane);
      
      albumScrollPane = new JScrollPane(albumList = new JList(albumStringArray));
      
      lsmAlbum = albumList.getSelectionModel();
      lsmAlbum.addListSelectionListener(this);
      
      add(albumScrollPane);
      
      remove(songScrollPane);
      add(songScrollPane);
      
      if(ZMediaSettingsFrame.darkModeCheckBox.isSelected())
      {
        setDarkModeOn();
      }
      
      setVisible(false);
      setVisible(true);
    }
  }
  
  public void valueChanged(ListSelectionEvent e)
  {
    lsm = (ListSelectionModel)e.getSource();

    if(lsm == lsmSong)//If you click on a selection in the song pane, execute this code
    {
      songIndex = lsm.getLeadSelectionIndex();//Get the index of where you clicked in the list
      ZMediaPlayer.filePath = songFileArray[songIndex].toString();//Set the media player's song file path to the index of the song array where you clicked
      ZMediaControlsPanel.buttonPlay.doClick();//Do the same thing as if clicking on the play button
    }
    if(lsm == lsmAlbum)
    {
      setSongArrays();
      
      int number = lsm.getLeadSelectionIndex();
      
      songStringArray = (String[])songStringArrayList.get(number);
      songFileArray = (File[])songFileArrayList.get(number);
      
      remove(songScrollPane);
      
      songScrollPane = new JScrollPane(songList = new JList(songStringArray));
      
      lsmSong = songList.getSelectionModel();
      lsmSong.addListSelectionListener(this);
      
      add(songScrollPane);
      
      songScrollPane.setColumnHeaderView(buttonClearSongs);
      
      if(ZMediaSettingsFrame.darkModeCheckBox.isSelected())
      {
        setDarkModeOn();
      }
      
      setVisible(false);//Removes the lists
      setVisible(true);//Repaints the lists
    }
    if(lsm == lsmArtist)
    {
      int number = lsm.getLeadSelectionIndex();
      
      albumStringArray = (String[])albumStringArrayList.get(number);
      albumFileArray = (File[])albumFileArrayList.get(number);
      
      remove(albumScrollPane);
      
      albumScrollPane = new JScrollPane(albumList = new JList(albumStringArray));
      
      lsmAlbum = albumList.getSelectionModel();
      lsmAlbum.addListSelectionListener(this);
      
      add(albumScrollPane);
      
      setSongArrays();
      
      remove(songScrollPane);
      
      songScrollPane = new JScrollPane(songList = new JList(songStringArray));
      
      lsmSong = songList.getSelectionModel();
      lsmSong.addListSelectionListener(this);
      
      add(songScrollPane);
      
      albumScrollPane.setColumnHeaderView(buttonClearAlbums);
      songScrollPane.setColumnHeaderView(buttonClearSongs);
      
      if(ZMediaSettingsFrame.darkModeCheckBox.isSelected())
      {
        setDarkModeOn();
      }
      
      setVisible(false);
      setVisible(true);
    }
    
    lsm.clearSelection();
  }
  
  public void setArtistArrays()
  {
    artistStringArray = musicPath.list(new FilenameFilter(){public boolean accept(File file, String name){if(name.toLowerCase().endsWith(".ini")){return false;}//This hides desktop.ini
                                                                                                                                                  return file.isDirectory();}});
    artistFileArray = musicPath.listFiles();
  }

  public void setAlbumArrays()
  {
    int numOfAlbums = 0;
    albumFileArrayList = new ArrayList();
    albumStringArrayList = new ArrayList();
    
    for(int i = 0; i < artistFileArray.length; i++)
    {
      if(artistFileArray[i].listFiles() != null)
      {
        numOfAlbums += artistFileArray[i].listFiles().length;
        albumFileArrayList.add(artistFileArray[i].listFiles());
        albumStringArrayList.add(artistFileArray[i].list());
      }
    }
    
    albumFileArray = new File[numOfAlbums];
    albumStringArray = new String[numOfAlbums];
    
    int counter = 0;
    int counter2 = 0;

    for(int i = 0; i < artistFileArray.length - 1; i++)
    {
      File[] someAlbums = (File[])albumFileArrayList.get(i);
      for(int j = 0; j < someAlbums.length; j++)
      {
        albumFileArray[counter] = someAlbums[j];
        counter++;
      }
      
      String[] someStrings = (String[])albumStringArrayList.get(i);
      for(int j = 0; j < someAlbums.length; j++)
      {
        albumStringArray[counter2] = someStrings[j];
        counter2++;
      }
    }
  }
  
  public void setSongArrays()
  {
    int numOfSongs = 0;
    songFileArrayList = new ArrayList();
    songStringArrayList = new ArrayList();
    
    for(int i = 0; i < albumFileArray.length; i++)
    {
      if(albumFileArray[i].listFiles() != null)
      {
        numOfSongs += albumFileArray[i].listFiles(new FilenameFilter(){public boolean accept(File albumFileArray, String name){if(name.toLowerCase().endsWith(".wav")){return name.toLowerCase().endsWith(".wav");}
                                                                                                                               if(name.toLowerCase().endsWith(".m4a")){return name.toLowerCase().endsWith(".m4a");}
                                                                                                                               if(name.toLowerCase().endsWith(".aiff")){return name.toLowerCase().endsWith(".aiff");}
                                                                                                                                                                       return name.toLowerCase().endsWith(".mp3");}}).length;
        songFileArrayList.add(albumFileArray[i].listFiles(new FilenameFilter(){public boolean accept(File albumFileArray, String name){if(name.toLowerCase().endsWith(".wav")){return name.toLowerCase().endsWith(".wav");}
                                                                                                                                       if(name.toLowerCase().endsWith(".m4a")){return name.toLowerCase().endsWith(".m4a");}
                                                                                                                                       if(name.toLowerCase().endsWith(".aiff")){return name.toLowerCase().endsWith(".aiff");}
                                                                                                                                                                       return name.toLowerCase().endsWith(".mp3");}}));
        songStringArrayList.add(albumFileArray[i].list(new FilenameFilter(){public boolean accept(File albumFileArray, String name){if(name.toLowerCase().endsWith(".wav")){return name.toLowerCase().endsWith(".wav");}
                                                                                                                                    if(name.toLowerCase().endsWith(".m4a")){return name.toLowerCase().endsWith(".m4a");}
                                                                                                                                    if(name.toLowerCase().endsWith(".aiff")){return name.toLowerCase().endsWith(".aiff");}
                                                                                                                                                                       return name.toLowerCase().endsWith(".mp3");}}));
      }
    }
      
      songFileArray = new File[numOfSongs];
      songStringArray = new String[numOfSongs];
      
      int counter = 0;
      int counter2 = 0;
      
    for(int i = 0; i < songFileArrayList.size(); i++)
    {
      File[] someSongs = (File[])songFileArrayList.get(i);
      for(int j = 0; j < someSongs.length; j++)
      {
        songFileArray[counter] = someSongs[j];
        counter++;
      }
      
      String[] someStrings = (String[])songStringArrayList.get(i);
      for(int j = 0; j < someStrings.length; j++)
      {
        songStringArray[counter2] = someStrings[j];
        counter2++;
      }
    }
    
    if(ZMediaSettingsFrame.alphabeticalCheckBox.isSelected())
      {
        java.util.Arrays.sort(songStringArray);
        java.util.Arrays.sort(songFileArray);//not working!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!DLKBNSDOBNAIDNBADFVNANVNDBVNJDBDNB
      }
  }
  
  public void setDarkModeOn()
  {
    setBackground(Color.BLACK);//Set the JFrame background color to black
    artistList.setForeground(Color.RED);
    albumList.setForeground(Color.RED);
    songList.setForeground(Color.RED);
    artistList.setBackground(Color.BLACK);
    albumList.setBackground(Color.BLACK);
    songList.setBackground(Color.BLACK);
    artistScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
    albumScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
    songScrollPane.getVerticalScrollBar().setBackground(Color.BLACK);
    artistScrollPane.getHorizontalScrollBar().setBackground(Color.BLACK);
    albumScrollPane.getHorizontalScrollBar().setBackground(Color.BLACK);
    songScrollPane.getHorizontalScrollBar().setBackground(Color.BLACK);
  }
  
  public void setDarkModeOff()
  {
    setBackground(Color.WHITE);//Set the JFrame background color to white
    artistList.setForeground(Color.BLACK);
    albumList.setForeground(Color.BLACK);
    songList.setForeground(Color.BLACK);
    artistList.setBackground(Color.WHITE);
    albumList.setBackground(Color.WHITE);
    songList.setBackground(Color.WHITE);
    artistScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    albumScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    songScrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
    artistScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
    albumScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
    songScrollPane.getHorizontalScrollBar().setBackground(Color.WHITE);
  }
  
  public void setTinyModeOn()
  {
    setVisible(false);
  }
  
  public void setTinyModeOff()
  {
    setVisible(true);
  }
}