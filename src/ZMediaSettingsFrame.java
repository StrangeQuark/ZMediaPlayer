import java.awt.Color;
import java.awt.Dimension;//Width and height dimensions for the panel
import java.awt.event.ActionEvent;//Allow for action events like clicking
import java.awt.event.ActionListener;//Listen for actions like clicking
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class ZMediaSettingsFrame extends JFrame implements ActionListener
{
  static JCheckBox darkModeCheckBox;
  static JCheckBox tinyModeCheckBox;
  static JCheckBox loopOneCheckBox;
  static JCheckBox loopAllCheckBox;
  static JCheckBox autoPlayCheckBox;
  static JCheckBox randomCheckBox;
  static JCheckBox alphabeticalCheckBox;
  static JTextField musicDirectoryTextField;
  public JFileChooser musicDirectoryFileChooser;
  public JButton musicDirectoryButton;
  public JButton saveButton;
  private FileWriter fileWriter;
  private PrintWriter printWriter;
  private BufferedReader bufferedReader;
  
  public ZMediaSettingsFrame()
  {
    setBounds(0, 0, 250, 200);//Set the JFrame size
    
    setLayout(new FlowLayout());
      
    setLocationRelativeTo(null);//Set JFrame to screen center
    
    musicDirectoryTextField = new JTextField();
    musicDirectoryTextField.setPreferredSize(new Dimension(100,20));
    
    darkModeCheckBox = new JCheckBox("Dark mode");
    darkModeCheckBox.addActionListener(this);
    
    tinyModeCheckBox = new JCheckBox("Tiny mode");
    tinyModeCheckBox.addActionListener(this);
    
    loopOneCheckBox = new JCheckBox("Loop one song");
    loopOneCheckBox.addActionListener(this);
    
    loopAllCheckBox = new JCheckBox("Loop all songs");
    loopAllCheckBox.addActionListener(this);
    
    autoPlayCheckBox = new JCheckBox("Auto play");
    autoPlayCheckBox.addActionListener(this);
    
    randomCheckBox = new JCheckBox("Random");
    randomCheckBox.addActionListener(this);
    
    alphabeticalCheckBox = new JCheckBox("Alphabetize songs");
    alphabeticalCheckBox.addActionListener(this);
    
    saveButton = new JButton("Save");
    saveButton.addActionListener(this);
    
    add(musicDirectoryTextField);
    add(darkModeCheckBox);
    add(tinyModeCheckBox);
    add(loopOneCheckBox);
    add(loopAllCheckBox);
    add(autoPlayCheckBox);
    add(randomCheckBox);
    //add(alphabeticalCheckBox);
    
    add(saveButton);
    
    getContentPane().setBackground(Color.WHITE);
    
    loadSettings();
  }
  
  public void actionPerformed(ActionEvent e)
  {
    String currentEvent = e.getActionCommand();
    
    if(currentEvent == "Dark mode")
    {
      if(darkModeCheckBox.isSelected())
      {
        ZMediaPlayerGUI.zGUI.setDarkModeOn();
        ZMediaPlayerGUI.musicList.setDarkModeOn();
        ZMediaPlayerGUI.controlsPanel.setDarkModeOn();
      }
      else
      {
        ZMediaPlayerGUI.zGUI.setDarkModeOff();
        ZMediaPlayerGUI.musicList.setDarkModeOff();
        ZMediaPlayerGUI.controlsPanel.setDarkModeOff();
      }
    }
    
    if(currentEvent == "Tiny mode")
    {
      if(tinyModeCheckBox.isSelected())
      {
        ZMediaPlayerGUI.zGUI.setTinyModeOn();
        ZMediaPlayerGUI.musicList.setTinyModeOn();
        ZMediaPlayerGUI.controlsPanel.setTinyModeOn();
      }
      else
      {
        ZMediaPlayerGUI.zGUI.setTinyModeOff();
        ZMediaPlayerGUI.musicList.setTinyModeOff();
        ZMediaPlayerGUI.controlsPanel.setTinyModeOff();
      }
    }
    
    if(currentEvent == "Save")
    {
      saveSettings();
    }
  }
  
  public void saveSettings()
  {
    try
    {fileWriter = new FileWriter("settings.txt");}
    catch(Exception e)
    {e.printStackTrace();}
    
    printWriter = new PrintWriter(fileWriter);
    
    printWriter.println(musicDirectoryTextField.getText() + " " + darkModeCheckBox.isSelected() + " " + tinyModeCheckBox.isSelected() + " " +
                        loopOneCheckBox.isSelected() + " " + loopAllCheckBox.isSelected() + " " + autoPlayCheckBox.isSelected() + " " +
                        randomCheckBox.isSelected() + " " + alphabeticalCheckBox.isSelected());
    
    if(printWriter != null)
    {printWriter.close();}
  }
  
  public void loadSettings()
  {
    String token;
    String settingsString;
    
    try
    {
      bufferedReader = new BufferedReader(new FileReader("settings.txt"));
      
      settingsString = bufferedReader.readLine();
      
      StringTokenizer tokenizer = new StringTokenizer(settingsString);
      
      int i = 0;
      
      while(tokenizer.hasMoreTokens())
      {
        token = tokenizer.nextToken();
      
        if(i == 0)
        {
          musicDirectoryTextField.setText(token);
        }
        if(i == 1)
        {
          Boolean boolVal = Boolean.valueOf(token);
          darkModeCheckBox.setSelected(boolVal);
        }
        if(i == 2)
        {
          Boolean boolVal = Boolean.valueOf(token);
          tinyModeCheckBox.setSelected(boolVal);
        }
        if(i == 3)
        {
          Boolean boolVal = Boolean.valueOf(token);
          loopOneCheckBox.setSelected(boolVal);
        }
        if(i == 4)
        {
          Boolean boolVal = Boolean.valueOf(token);
          loopAllCheckBox.setSelected(boolVal);
        }
        if(i == 5)
        {
          Boolean boolVal = Boolean.valueOf(token);
          autoPlayCheckBox.setSelected(boolVal);
        }
        if(i == 6)
        {
          Boolean boolVal = Boolean.valueOf(token);
          randomCheckBox.setSelected(boolVal);
        }
        if(i == 7)
        {
          Boolean boolVal = Boolean.valueOf(token);
          alphabeticalCheckBox.setSelected(boolVal);
        }
        i++;
      }
    }
    catch(Exception e)
    {e.printStackTrace();}
  }
}