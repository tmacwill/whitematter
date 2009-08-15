package tmacsoftware.whitematter;

import tmacsoftware.ursql.*;
import java.util.Vector;
import java.io.File;

public class StickletWatcher extends Thread
{

    public Vector<UrSQLEntity> shownEntities = null;
    private StickletHandler stickletHandler = null;

    public StickletWatcher(StickletHandler stickletHandler)
    {
        this.shownEntities = new Vector<UrSQLEntity>();
        this.stickletHandler = stickletHandler;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(1000);
                Vector<String> procs = this.getRunningProcesses();

                Vector<UrSQLEntity> entities = this.stickletHandler.controller.getAllEntities();

                // remove any processes that are no longer running from active sticklets vector
                for (int i = 0; i < TrayNotebook.activeSticklets.size(); i++)
                {
                    if (!isProcessRunning(TrayNotebook.activeSticklets.get(i)))
                    {
                        TrayNotebook.activeSticklets.remove(i);
                    }
                }

                // iterate through database entries
                for (int i = 0; i < entities.size(); i++)
                {
                    // iterate through running processes
                    for (int j = 0; j < procs.size(); j++)
                    {
                        // check if process and database entry match
                        if (procs.get(j).contains(entities.get(i).getEntry("process").getValue()))
                        {
                            // make sure sticklet has not already been shown
                            if (!TrayNotebook.activeSticklets.contains(entities.get(i).getEntry("process").getValue()))
                            {
                                // create new sticklet
                                Sticklet sticklet = new Sticklet(entities.get(i).getEntry("process").getValue(),
                                        entities.get(i).getEntry("note").getValue(), this.stickletHandler);

                                sticklet.setVisible(true);
                                // add process to active vector
                                TrayNotebook.activeSticklets.add(entities.get(i).getEntry("process").getValue());
                            }
                        }
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public Vector<String> getRunningProcesses()
    {
        return (System.getProperty("os.name").compareTo("Linux") == 0) ? getRunningProcessesLinux() : getRunningProcessesWindows();
    }

    /**
     * Get all running processes by parsing the /proc directory
     * @return Vector containing string off all running processes
     */
    public Vector<String> getRunningProcessesLinux()
    {
        Vector<String> processes = new Vector<String>();
        File proDir = new File("/proc");
        // each folder in /proc represents a process id
        String[] pids = proDir.list();
        // iterate through process ids
        for (int i = 0; i < pids.length; i++)
        {
            // cmdline file contains name of process
            String path = "/proc/" + pids[i] + "/cmdline";
            // don't bother trying to read files that don't exist
            File checkFile = new File(path);
            if (checkFile.exists() == true)
            {
                try
                {
                    // read file
                    Filio fio = new Filio(path);
                    // add name of process to list
                    processes.add(fio.read());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return processes;
    }

    public Vector<String> getRunningProcessesWindows()
    {
        return new Vector<String>();
    }

    /**
     * Check if given process is running
     * @param Process Process to check
     * @return If process is running
     */
    public boolean isProcessRunning(String process)
    {
        Vector<String> procs = getRunningProcesses();
        boolean found = false;
        for (int i = 0; i < procs.size(); i++)
        {
            if (procs.get(i).contains(process))
            {
                found = true;
            }
        }
        return found;
    }
}
