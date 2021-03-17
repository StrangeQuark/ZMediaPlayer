import java.awt.Color;//Colors
import java.awt.event.ActionEvent;//Allow for action events like clicking
import java.awt.event.ActionListener;//Listen for actions like clicking
import java.awt.event.MouseAdapter;//Allow for our mouse to adapt to this program
import java.awt.event.MouseEvent;//Allow for mouse events like clicking
import java.awt.event.MouseListener;//Listen for mouse events
import java.awt.Dimension;//Width and height dimensions for the panel
import javax.swing.event.ChangeEvent;//Allow for change events that handle the slider
import javax.swing.event.ChangeListener;//Listen for change events from the slider
import javax.swing.Icon;//To add icons or "pictures" to the buttons
import javax.swing.ImageIcon;//
import javax.swing.JButton;//Allow us to add buttons to the panel
import javax.swing.JLabel;//Allow us to add labels for our volume and song time
import javax.swing.JPanel;//The panel itself
import javax.swing.JProgressBar;//Allow us to add a progress bar to seek the song and visually see the progress of the song
import javax.swing.JSlider;//All us to add a slider for volume control
import javax.swing.SpringLayout;//Allow us to add a spring layout
import javax.swing.Timer;//Add a timer which executes code on a set time interval
import javafx.util.Duration;//Duration of the song
import java.util.Random;//Random number generation


public class ZMediaControlsPanel extends JPanel implements ActionListener, ChangeListener
{
  ZMediaPlayer player = new ZMediaPlayer();
  ZMediaSettingsFrame settingsFrame;
  JPanel controlsPanel = new JPanel();
  Timer timer;
  static JButton buttonPlay;
  JButton buttonPause;
  JButton buttonStop;
  JButton buttonSettings;
  JButton buttonPrevious;
  JButton buttonNext;
  JProgressBar progressBar;
  JSlider volumeSlider;
  Dimension dimension;
  Icon playIcon;
  Icon playIconDark;
  Icon stopIcon;
  Icon pauseIcon;
  Icon pauseIconDark;
  Icon previousIcon;
  Icon previousIconDark;
  Icon nextIcon;
  Icon nextIconDark;
  SpringLayout springLayout;
  SpringLayout tinyLayout;
  JLabel volumeLabel;
  JLabel currentSongTimeLabel;
  JLabel totalSongTimeLabel;
  
  public ZMediaControlsPanel()
  {
    volumeLabel = new JLabel("Volume: 100");
    volumeLabel.setForeground(Color.BLACK);
    
    previousIcon = new ImageIcon("previousIcon.png");
    previousIconDark = new ImageIcon("previousIconDark.png");
    buttonPrevious = new JButton(previousIcon);//Create a play button
    buttonPrevious.setActionCommand("Previous");
    buttonPrevious.setBorderPainted(false);
    buttonPrevious.setContentAreaFilled(false);
    buttonPrevious.addActionListener(this);
    
    nextIcon = new ImageIcon("nextIcon.png");
    nextIconDark = new ImageIcon("nextIconDark.png");
    buttonNext = new JButton(nextIcon);//Create a play button
    buttonNext.setActionCommand("Next");
    buttonNext.setBorderPainted(false);
    buttonNext.setContentAreaFilled(false);
    buttonNext.addActionListener(this);
    
    playIcon = new ImageIcon("playIcon.png");
    playIconDark = new ImageIcon("playIconDark.png");
    buttonPlay = new JButton(playIcon);//Create a play button
    buttonPlay.setActionCommand("Play");
    buttonPlay.setBorderPainted(false);
    buttonPlay.setContentAreaFilled(false);
    buttonPlay.addActionListener(this);
    
    stopIcon = new ImageIcon("stopIcon.png");
    buttonStop = new JButton(stopIcon);//Create a stop button
    buttonStop.setActionCommand("Stop");
    buttonStop.setBorderPainted(false);
    buttonStop.setContentAreaFilled(false);
    buttonStop.addActionListener(this);
    
    pauseIcon = new ImageIcon("pauseIcon.png");
    pauseIconDark = new ImageIcon("pauseIconDark.png");
    buttonPause = new JButton(pauseIcon);//Create a pause button
    buttonPause.setActionCommand("Pause");
    buttonPause.setBorderPainted(false);
    buttonPause.setContentAreaFilled(false);
    buttonPause.addActionListener(this);
    
    buttonSettings = new JButton("Settings");//Create a settings button
    buttonSettings.setContentAreaFilled(false);
    buttonSettings.addActionListener(this);
    buttonSettings.setForeground(Color.BLACK);
    
    progressBar = new JProgressBar();//Create a progress bar
    progressBar.setBackground(Color.WHITE);
    progressBar.addMouseListener(new MouseAdapter(){public void mousePressed(MouseEvent e)
                                                    {
                                                      player.play();
                                                      
                                                      int i = progressBar.getValue();
                                                      int mouseX = e.getX();
                                                      int progBarValue = (int)Math.round(((double)mouseX / (double) progressBar.getWidth()) * progressBar.getMaximum());
                                                      
                                                      progressBar.setValue(progBarValue);
                                                      
                                                      player.seek(new Duration(progBarValue));
                                                      
                                                      timer.start();
                                                      
                                                      buttonPlay.setVisible(false);
                                                      
                                                      //player.pause();//If you want the player to pause after you click the progress bar
                                                    }
                                                   });
    
    volumeSlider = new JSlider();//Create the volume slider
    volumeSlider.setBackground(Color.WHITE);
    volumeSlider.addChangeListener(this);
    volumeSlider.setValue(100);
    volumeSlider.setOpaque(false);
    
    timer = new Timer(200, this);
    
    dimension = getMaximumSize();
    dimension.height = 40;
    
    setMaximumSize(dimension);
    setPreferredSize(dimension);
    setMinimumSize(dimension);
    
    springLayout = new SpringLayout();
    
    springLayout.putConstraint(SpringLayout.WEST, buttonSettings, 5, SpringLayout.WEST, this);
    springLayout.putConstraint(SpringLayout.NORTH, buttonSettings, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonSettings, -3, SpringLayout.SOUTH, this);
    
    springLayout.putConstraint(SpringLayout.EAST, volumeSlider, -10, SpringLayout.EAST, this);
    springLayout.putConstraint(SpringLayout.NORTH, volumeSlider, 10, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, volumeSlider, -10, SpringLayout.SOUTH, this);
    
    springLayout.putConstraint(SpringLayout.WEST, buttonPlay, 100, SpringLayout.EAST, buttonSettings);
    springLayout.putConstraint(SpringLayout.NORTH, buttonPlay, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonPlay, -3, SpringLayout.SOUTH, this);
    
    springLayout.putConstraint(SpringLayout.WEST, buttonPause, 100, SpringLayout.EAST, buttonSettings);
    springLayout.putConstraint(SpringLayout.NORTH, buttonPause, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonPause, -3, SpringLayout.SOUTH, this);

    springLayout.putConstraint(SpringLayout.EAST, buttonStop, -50, SpringLayout.WEST, volumeSlider);
    springLayout.putConstraint(SpringLayout.NORTH, buttonStop, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonStop, -3, SpringLayout.SOUTH, this);
    
    springLayout.putConstraint(SpringLayout.WEST, progressBar, 175, SpringLayout.EAST, buttonSettings);
    springLayout.putConstraint(SpringLayout.EAST, progressBar, -10, SpringLayout.WEST, buttonStop);
    springLayout.putConstraint(SpringLayout.NORTH, progressBar, 1, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, progressBar, -1, SpringLayout.SOUTH, this);

    springLayout.putConstraint(SpringLayout.SOUTH, volumeLabel, -2, SpringLayout.SOUTH, this);
    springLayout.putConstraint(SpringLayout.WEST, volumeLabel, 120, SpringLayout.EAST, buttonStop);
    
    springLayout.putConstraint(SpringLayout.EAST, buttonNext, -10, SpringLayout.WEST, volumeSlider);
    springLayout.putConstraint(SpringLayout.NORTH, buttonNext, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonNext, -3, SpringLayout.SOUTH, this);
    
    springLayout.putConstraint(SpringLayout.WEST, buttonPrevious, 60, SpringLayout.EAST, buttonSettings);
    springLayout.putConstraint(SpringLayout.NORTH, buttonPrevious, 3, SpringLayout.NORTH, this);
    springLayout.putConstraint(SpringLayout.SOUTH, buttonPrevious, -3, SpringLayout.SOUTH, this);
    
    setLayout(springLayout);
    
    add(buttonSettings);
    add(buttonPlay);
    add(progressBar);
    add(buttonStop);
    add(buttonPause);
    add(volumeSlider);
    add(volumeLabel);
    add(buttonNext);
    add(buttonPrevious);
  }
  
  public void actionPerformed(ActionEvent e)//Override ActionListener method
  {
    String currentEvent = e.getActionCommand();
    
    if(currentEvent == null)//This is the timer's events
    {
      progressBar.setValue((int)player.player.getCurrentTime().toSeconds() * 1000);
      progressBar.setMaximum((int)player.player.getTotalDuration().toSeconds() * 1000);
      
      if(player.player.getTotalDuration().compareTo(player.player.getCurrentTime()) == 0)//This if statement plays the next song in the song list if it's not the last one, if it is last, player.stop()
      {
        nextSong();
      }
    }
    
    if(currentEvent == "Play")
    {
      buttonPlay.setVisible(false);
      
      timer.start();
      
      player.play();
    }
    
    if(currentEvent == "Pause")
    {
      buttonPlay.setVisible(true);
      
      player.pause();
    }

    if(currentEvent == "Stop")
    {
      buttonPlay.setVisible(true);
      
      player.stop();
      
      progressBar.setValue(0);
      
      timer.stop();
    }
    
    if(currentEvent == "Next")
    {
      nextSong();
    }
    
    if(currentEvent == "Previous")
    {
      if((int)player.player.getCurrentTime().toSeconds() < 3)
      {
        if(ZMediaList.songIndex != 0)
        {
          ZMediaList.songIndex--;
          ZMediaPlayer.filePath = ZMediaList.songFileArray[ZMediaList.songIndex].toString();//Set the media player's song file path to the index of the song array where you clicked
          buttonPlay.doClick();
        }
        else
        {
          buttonStop.doClick();
        }
      }
      else
      {
        buttonStop.doClick();
      }
    }
    
    if(currentEvent == "Settings")
    {
      settingsFrame = new ZMediaSettingsFrame();
      settingsFrame.setVisible(true);
    }
  }
  
  public void stateChanged(ChangeEvent e)//For progress bar and volume slider, however we don't want anything to happen when the state changes because then this code will execute 
  {//                                      anytime the bar starts to "fill up" as a song plays so we don't add a listener, this only controls the volume slider
    JSlider source = (JSlider)e.getSource();
    double volume = source.getValue() / 100.0;
    String volumeString = "Volume: " + (int)source.getValue();
    volumeLabel.setText(volumeString);
    player.setVolume(volume);
  }
  
  public void setDarkModeOn()
  {
    setBackground(Color.BLACK);//Set the JFrame background color to white
    buttonPlay.setIcon(playIconDark);
    buttonPause.setIcon(pauseIconDark);
    buttonPrevious.setIcon(previousIconDark);
    buttonNext.setIcon(nextIconDark);
    volumeLabel.setForeground(Color.RED);
    buttonSettings.setForeground(Color.RED);
  }
  
  public void setDarkModeOff()
  {
    setBackground(Color.WHITE);//Set the JFrame background color to white
    buttonPlay.setIcon(playIcon);
    buttonPause.setIcon(pauseIcon);
    buttonPrevious.setIcon(previousIcon);
    buttonNext.setIcon(nextIcon);
    volumeLabel.setForeground(Color.BLACK);
    buttonSettings.setForeground(Color.BLACK);
  }
  
  public void setTinyModeOn()
  {
    tinyLayout = new SpringLayout();
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonSettings, 5, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonSettings, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonSettings, -3, SpringLayout.SOUTH, this);
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonPrevious, 75, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonPrevious, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonPrevious, -3, SpringLayout.SOUTH, this);
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonPlay, 115, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonPlay, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonPlay, -3, SpringLayout.SOUTH, this);
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonPause, 115, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonPause, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonPause, -3, SpringLayout.SOUTH, this);
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonStop, 155, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonStop, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonStop, -3, SpringLayout.SOUTH, this);
    
    tinyLayout.putConstraint(SpringLayout.WEST, buttonNext, 195, SpringLayout.WEST, this);
    tinyLayout.putConstraint(SpringLayout.NORTH, buttonNext, 3, SpringLayout.NORTH, this);
    tinyLayout.putConstraint(SpringLayout.SOUTH, buttonNext, -3, SpringLayout.SOUTH, this);
    
    setLayout(tinyLayout);
    
    remove(progressBar);
    remove(volumeSlider);
    remove(volumeLabel);
  }
  
  public void setTinyModeOff()
  {
    setLayout(springLayout);
    
    add(progressBar);
    add(volumeSlider);
    add(volumeLabel);
  }
  
  public void nextSong()
  {
    if(ZMediaSettingsFrame.autoPlayCheckBox.isSelected())
        {
          if(ZMediaSettingsFrame.loopOneCheckBox.isSelected())
          {
            buttonPlay.doClick();
          }
          else
          {
            if(ZMediaSettingsFrame.randomCheckBox.isSelected())
            {
              Random rand = new Random();
              int randomSong = rand.nextInt(ZMediaList.songFileArray.length);
              ZMediaPlayer.filePath = ZMediaList.songFileArray[randomSong].toString();
              buttonPlay.doClick();
            }
            else
            {
              if(ZMediaList.songIndex < ZMediaList.songFileArray.length - 1)
              {
                ZMediaList.songIndex++;
                ZMediaPlayer.filePath = ZMediaList.songFileArray[ZMediaList.songIndex].toString();//Set the media player's song file path to the index of the song array where you clicked
                buttonPlay.doClick();
              }
              else
              {
                if(ZMediaSettingsFrame.loopAllCheckBox.isSelected())
                {
                  ZMediaList.songIndex = 0;
                  ZMediaPlayer.filePath = ZMediaList.songFileArray[ZMediaList.songIndex].toString();//Set the media player's song file path to the index of the song array where you clicked
                  buttonPlay.doClick();
                }
                else
                {
                  buttonStop.doClick();
                }
              }
            }
          }
        }
        else
        {
          buttonStop.doClick();
        }
  }
}