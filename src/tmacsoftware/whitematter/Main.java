package tmacsoftware.whitematter;

import javax.swing.UIManager;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        TrayNotebook trayNotebook = new TrayNotebook();
        trayNotebook.setVisible(true);
    }

}
