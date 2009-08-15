package tmacsoftware.whitematter;

import tmacsoftware.ursql.*;
import java.util.Vector;

public class NotebookHandler
{

    private TrayNotebook trayNotebook = null;
    private UrSQLController controller = null;
    private Vector<UrSQLEntity> pages = null;
    private int pageCount = 0;
    private int pageCurrent = 0;

    public NotebookHandler(TrayNotebook trayNotebook)
    {
        this.trayNotebook = trayNotebook;
        controller = new UrSQLController("notebook.udb");
        pages = new Vector<UrSQLEntity>();
    }

    /**
     * Fill pages vector with database data and go to first page
     */
    public void loadPages()
    {
        Vector<UrSQLEntity> entities = controller.getAllEntities();
        // add blank entity to avoid zero-based index
        this.pages.add(new UrSQLEntity());
        for (int i = 0; i < entities.size(); i++)
        {
            this.pages.add(entities.get(i));
        }
        this.pageCount = entities.size();

        if (pageCount == 0)
        {
            this.newPage();
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
        // set gui elements based on database data
        trayNotebook.setPageText(pages.get(index).getEntry("text").getValue());
        trayNotebook.setPageTitle(pages.get(index).getEntry("title").getValue());
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

        // add new page to pages vector and load new page
        this.pages.add(entity);
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

        // remove current page from vector and decrement page count
        this.pages.remove(this.pageCurrent);
        this.pageCount--;

        // load previous page
        if (this.pageCurrent == 1)
        {
            this.loadPage(2);
        }
        else
        {
            this.loadPage(this.pageCurrent - 1);
        }
    }
}
