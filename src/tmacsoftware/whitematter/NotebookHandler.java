package tmacsoftware.whitematter;

import tmacsoftware.ursql.*;
import java.util.Vector;

public class NotebookHandler
{

    private TrayNotebook trayNotebook = null;
    private UrSQLController controller = null;
    private int pageCount = 0;
    private int pageCurrent = 0;
    private final String WELCOME_TEXT = "Welcome to WhiteMatter 2.0! WhiteMatter " +
            "is a desktop note manager program. You are currently viewing the" +
            " notebook, which can be minimized to the tray by clicking the" +
            " WhiteMatter logo" + System.getProperty("line.separator") +
            System.getProperty("line.separator") + "Sticklets are notes" +
            " that you can tie to a particular application. Select the name of" +
            " the application, then type your note. The next time you open that " +
            "application, your note will pop up with your reminder. So, " +
            "you can create a reminder that will pop up when you open Firefox " +
            "saying \"Email Mom those pictures\"";

    public NotebookHandler(TrayNotebook trayNotebook)
    {
        this.trayNotebook = trayNotebook;
        this.controller = new UrSQLController("notebook.udb");

        Vector<UrSQLEntity> entities = controller.getAllEntities();
        this.pageCount = entities.size();

        // if database is blank, create a new page with welcome text
        if (pageCount == 0)
        {
            this.pageCount = 1;

            // write default page data to database
            UrSQLEntity entity = controller.createEntity();
            UrSQLEntry title = new UrSQLEntry("title", "Welcome to WhiteMatter!");
            UrSQLEntry text = new UrSQLEntry("text", this.WELCOME_TEXT);
            UrSQLEntry page = new UrSQLEntry("page", String.valueOf(this.pageCount));

            entity.addEntry(title);
            entity.addEntry(text);
            entity.addEntry(page);
            controller.writeEntity(entity);

            this.loadPage(this.pageCount);
        }
        else
        {
            this.loadPage(1);
        }
    }

    /**
     * Load the given page
     * @param index Page number
     */
    public void loadPage(int index)
    {
        UrSQLEntity entity = controller.getEntity("page=" + String.valueOf(index));
        // set gui elements based on database data
        trayNotebook.setPageText(entity.getEntry("text").getValue());
        trayNotebook.setPageTitle(entity.getEntry("title").getValue());
        String label = "Page " + index + " of " + pageCount;
        trayNotebook.setPageLabel(label);

        // set current page
        this.pageCurrent = index;
    }

    /**
     * Go to the next page
     */
    public void nextPage()
    {
        // increment current page
        this.pageCurrent++;
        // go to first page if at last
        if (this.pageCurrent > this.pageCount)
        {
            this.pageCurrent = 1;
        }

        // load new current page
        this.loadPage(pageCurrent);
    }

    /**
     * Go to the previous page
     */
    public void previousPage()
    {
        // decrement current page
        this.pageCurrent--;
        // go to last page if at first
        if (this.pageCurrent < 1)
        {
            this.pageCurrent = this.pageCount;
        }

        // load new current page
        this.loadPage(pageCurrent);
    }

    /**
     * Save current page data to database
     */
    public void savePage()
    {
        // write page info to database
        UrSQLEntity entity = controller.getEntity("page=" + String.valueOf(this.pageCurrent));
        UrSQLEntry title = entity.getEntry("title");
        title.setValue(trayNotebook.getPageTitle());
        UrSQLEntry text = entity.getEntry("text");
        text.setValue(trayNotebook.getPageText());

        controller.writeEntry(title);
        controller.writeEntry(text);
    }

    /**
     * Create a new page
     */
    public void newPage()
    {
        // increment page count
        this.pageCount++;

        // write default page data to database
        UrSQLEntity entity = controller.createEntity();
        UrSQLEntry title = new UrSQLEntry("title", "New Page");
        UrSQLEntry text = new UrSQLEntry("text", "This is a new page");
        UrSQLEntry page = new UrSQLEntry("page", String.valueOf(this.pageCount));

        entity.addEntry(title);
        entity.addEntry(text);
        entity.addEntry(page);
        controller.writeEntity(entity);

        this.loadPage(this.pageCount);
    }

    /**
     * Delete current page
     */
    public void deletePage()
    {
        // remove current page from database
        controller.removeEntity("page=" + String.valueOf(this.pageCurrent));

        // decrement all page numbers higher than deleted page
        for (int i = 0; i <= this.pageCount; i++)
        {
            if (i > this.pageCurrent)
            {
                UrSQLEntity entity = controller.getEntity("page=" + String.valueOf(i));
                UrSQLEntry entry = entity.getEntry("page");
                entry.setValue(String.valueOf(entry.getValueAsInt() - 1));
                controller.writeEntry(entry);
            }
        }
        this.pageCount--;

        // load previous page
        if (this.pageCurrent == 1)
        {
            this.loadPage(1);
        }
        else
        {
            this.loadPage(this.pageCurrent - 1);
        }
    }
}
