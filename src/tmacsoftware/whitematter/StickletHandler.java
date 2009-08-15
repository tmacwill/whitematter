package tmacsoftware.whitematter;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import tmacsoftware.ursql.*;
import java.util.Vector;

public class StickletHandler
{

    private TrayNotebook trayNotebook = null;
    DefaultTableModel tableModel = null;
    UrSQLController controller = null;
    public boolean isTableUpdated = false;

    public StickletHandler(TrayNotebook trayNotebook)
    {
        this.trayNotebook = trayNotebook;
        this.trayNotebook.tableSticklets.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.tableModel = (DefaultTableModel) trayNotebook.tableSticklets.getModel();
        this.controller = new UrSQLController("sticklets.udb");
    }

    /**
     * Load all sticklets from database into table
     */
    public void loadSticklets()
    {
        // remove all rows
        this.tableModel.setRowCount(0);

        // iterate through entities
        Vector<UrSQLEntity> entities = controller.getAllEntities();
        for (int i = 0; i < entities.size(); i++)
        {
            // add new row from process and note data
            Vector<Object> rowData = new Vector<Object>();
            rowData.add(entities.get(i).getEntry("process").getValue());
            rowData.add(entities.get(i).getEntry("note").getValue());
            this.tableModel.addRow(rowData);
        }
    }

    /**
     * Save the sticklet with the given process and note, creating a new sticklet
     * if not already existing
     * @param process Sticklet process
     * @param note Sticklet note
     */
    public void saveSticklet(String process, String note)
    {
        String query = "process=" + process;
        UrSQLEntity entity = this.controller.getEntity(query);
        // create new note
        if (entity.isEmpty())
        {
            entity = this.controller.createEntity();
            UrSQLEntry entryProcess = new UrSQLEntry("process", process);
            UrSQLEntry entryNote = new UrSQLEntry("note", note);

            entity.addEntry(entryProcess);
            entity.addEntry(entryNote);
            this.controller.writeEntity(entity);
        }
        // note exists, so modify values
        else
        {
            UrSQLEntry entryProcess = entity.getEntry("process");
            entryProcess.setValue(process);
            UrSQLEntry entryNote = entity.getEntry("note");
            entryNote.setValue(note);

            this.controller.writeEntry(entryProcess);
            this.controller.writeEntry(entryNote);
        }

        // reload table values
        this.loadSticklets();
    }

    /**
     * Edit the sticklet with the given process
     * @param process Sticklet process
     * @param note Sticklet note
     */
    public void editSticklet(String process, String note)
    {
        Sticklet sticklet = new Sticklet(process, note, this);
        sticklet.setVisible(true);
    }

    /**
     * Delete the sticklet with the given process
     * @param process Sticklet process
     * @param note Sticklet note
     */
    public void deleteSticklet(String process, String note)
    {
        // remove entity from database
        String query = "process=" + process;
        controller.removeEntity(query);
        
        // refresh table
        this.loadSticklets();
    }

    /**
     * Edit the currently selected sticklet from the sticklet tab
     */
    public void editSelectedSticklet()
    {
        int selectedRow = this.trayNotebook.tableSticklets.getSelectedRow();
        this.editSticklet(this.tableModel.getValueAt(selectedRow, 0).toString(),
                this.tableModel.getValueAt(selectedRow, 1).toString());
    }

    /**
     * Delete the currently selected sticklet from the sticklet tab
     */
    public void deleteSelectedSticklet()
    {
        int selectedRow = this.trayNotebook.tableSticklets.getSelectedRow();
        this.deleteSticklet(this.tableModel.getValueAt(selectedRow, 0).toString(),
                this.tableModel.getValueAt(selectedRow, 1).toString());
    }
}
