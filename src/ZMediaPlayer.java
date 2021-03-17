import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;//
import javafx.util.Duration;

public class ZMediaPlayer
{
  Long songTimer;//Stores the current position in the song
  Media song;//The song file
  public MediaPlayer player;//The mediaplayer
  static String filePath = "Chopin.wav";
  static String filePathCheck = "Chopin.wav";//Check to see if the filepath changes
  
  final JFXPanel fxPanel = new JFXPanel();//This is needed to initialize, "javafx performs "hidden" initialization on start. Running MediaPlayer doesn't trigger initialization" -Sergey Grinev 12/24/12 StackOverflow.com
  
  public ZMediaPlayer()
  {
    try
    {
      song = new Media(new File(filePath).toURI().toURL().toString());
      
      player = new MediaPlayer(song);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void play()
  {
    if(player.getTotalDuration().compareTo(player.getCurrentTime()) == 0)//Check if they are equal [  -1 <, 0 ==, 1 >  ]
    {
      player.seek(new Duration(0));
      player.play();
    }
    
    if(filePath == filePathCheck)
    {
      player.play();
    }
    else
    {
      try
      {
      song = new Media(new File(filePath).toURI().toURL().toString());
      
      player.dispose();
      
      player = new MediaPlayer(song);
      
      filePathCheck = filePath.toString();
      
      player.play();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }
  
  public void pause()
  {
    player.pause();
  }
  
  public void stop()
  {
    player.seek(new Duration(0));
    player.stop();
  }
  
  public void seek(Duration dur)
  {
    player.seek(dur);
  }
  
  public void setVolume(double v)
  {
    player.setVolume(v);
  }
}