package tmacsoftware.whitematter;

import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class ClipboardHandler extends Thread
{

    private TrayNotebook trayNotebook = null;
    private Clipboard clipboard = null;
    private String newClip = "";
    private String oldClip = "";
    private String allClip = "";

    public ClipboardHandler(TrayNotebook trayNotebook)
    {
        this.trayNotebook = trayNotebook;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    public void updateText()
    {
        Transferable clipContent = clipboard.getContents(this);

        // make sure clipboard is text data
        if ((clipContent != null) && (clipContent.isDataFlavorSupported(DataFlavor.stringFlavor)))
        {
            try
            {
                Thread.sleep(4000);
                // store text data in temporary string
                newClip = (String) clipContent.getTransferData(DataFlavor.stringFlavor);
                // aviod duplicate entries
                if (newClip.compareTo(oldClip) != 0)
                {
                    // add recent clipboard data to history
                    allClip += newClip;
                    // append new lines to separate content
                    allClip += System.getProperty("line.separator") + System.getProperty("line.separator");
                    // latest entry to be compared to a new one later
                    oldClip = newClip;
                    // set form text
                    trayNotebook.setClipboardText(allClip);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(3000);
                updateText();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
