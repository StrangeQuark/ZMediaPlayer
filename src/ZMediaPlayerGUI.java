import javax.swing.BoxLayout;//Box layout
import javax.swing.ImageIcon;//Icon
import javax.swing.JFrame;//JFrame
import java.awt.Color;//Colors
import java.awt.event.ActionEvent;//Allow for action events like clicking
import java.awt.event.ActionListener;//ActionListener
import java.net.URL;//Create icon resource

public class ZMediaPlayerGUI extends JFrame implements ActionListener//Implement ActionListener to listen for user input IE button presses
{
  static ZMediaControlsPanel controlsPanel = new ZMediaControlsPanel();
  static ZMediaList musicList = new ZMediaList();
  static ZMediaPlayerGUI zGUI;
  
  public ZMediaPlayerGUI()//Constructor
  {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Exit on close
    
    setBounds(0, 0, 750, 550);//Set the JFrame size
      
    setLocationRelativeTo(null);//Set JFrame to screen center
    
    getContentPane().setBackground(Color.WHITE);//Set the JFrame background color to white
    musicList.setBackground(Color.WHITE);//Set the ZMediaList panel background color to white
    controlsPanel.setBackground(Color.WHITE);//Set the ZMediaControlsPanel panel background color to white
    
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));//Set the layout of the container
    add(musicList);
    add(controlsPanel);
    
    URL iconURL = getClass().getResource("mediaplayericon.png");
    ImageIcon icon = new ImageIcon(iconURL);
    setIconImage(icon.getImage());
    setTitle("zMediaPlayer");
    
    setVisible(true);
    
    if(ZMediaSettingsFrame.darkModeCheckBox.isSelected())
    {
      setDarkModeOn();
      musicList.setDarkModeOn();
      controlsPanel.setDarkModeOn();
    }
    
    if(ZMediaSettingsFrame.tinyModeCheckBox.isSelected())
    {
      setTinyModeOn();
      musicList.setTinyModeOn();
      controlsPanel.setTinyModeOn();
    }
  }
  
  public void actionPerformed(ActionEvent e)//Override ActionListener method
  {
    String currentEvent = e.getActionCommand();
  }
  
  public void setDarkModeOn()
  {
    getContentPane().setBackground(Color.BLACK);//Set the JFrame background color to white
    musicList.setBackground(Color.BLACK);//Set the ZMediaList panel background color to white
    controlsPanel.setBackground(Color.BLACK);//Set the ZMediaControlsPanel panel background color to white
  }
  
  public void setDarkModeOff()
  {
    getContentPane().setBackground(Color.WHITE);//Set the JFrame background color to white
    musicList.setBackground(Color.WHITE);//Set the ZMediaList panel background color to white
    controlsPanel.setBackground(Color.WHITE);//Set the ZMediaControlsPanel panel background color to white
  }
  
  public void setTinyModeOn()
  {
    setBounds(0, 0, 270, 80);
  }
  
  public void setTinyModeOff()
  {
    setBounds(0, 0, 750, 550);
    
    setLocationRelativeTo(null);//Set JFrame to screen center
  }
  
  public static void main(String[] args)
  {
    zGUI = new ZMediaPlayerGUI();
  }
}