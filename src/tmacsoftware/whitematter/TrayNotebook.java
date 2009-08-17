package tmacsoftware.whitematter;

import java.awt.event.ActionEvent;
import java.util.Vector;
import org.jdesktop.jdic.tray.*;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class TrayNotebook extends javax.swing.JFrame
{

    private NotebookHandler notebookHandler = null;
    private StickletHandler stickletHandler = null;
    private ClipboardHandler clipboardHandler = null;
    public static Vector<String> activeSticklets = new Vector<String>();
    private JMenuItem itemShowHide = null;
    private JMenuItem itemCreateSticklet = null;
    private JMenuItem itemQuit = null;

    public TrayNotebook()
    {
        initComponents();

        // set icon for frame
        URL path = this.getClass().getResource("/images/icon.png");
        ImageIcon image = new ImageIcon(path);
        this.setIconImage(image.getImage());

        // load tray icon
        this.loadTray();

        // create handler for notebook
        this.notebookHandler = new NotebookHandler(this);

        // create handler for sticklets
        this.stickletHandler = new StickletHandler(this);
        this.stickletHandler.loadSticklets();

        // create handler for clipboard
        this.clipboardHandler = new ClipboardHandler(this);
        this.clipboardHandler.start();

        (new StickletWatcher(stickletHandler)).start();

    }

    public void setPageTitle(String title)
    {
        this.txtTitlePage.setText(title);
    }

    public String getPageTitle()
    {
        return this.txtTitlePage.getText();
    }

    public void setPageText(String text)
    {
        this.txtPage.setText(text);
    }

    public String getPageText()
    {
        return this.txtPage.getText();
    }

    public void setPageLabel(String label)
    {
        this.lblPage.setText(label);
    }

    public String getPageLabel()
    {
        return this.lblPage.getText();
    }

    public void setClipboardText(String text)
    {
        this.txtClipboard.setText(text);
    }

    public String getClipboardText()
    {
        return this.txtClipboard.getText();
    }

    public void loadTray()
    {
        // load tray image from jar
        URL path = this.getClass().getResource("/images/tray-small.png");
        ImageIcon image = new ImageIcon(path);

        JPopupMenu menu = new JPopupMenu();

        this.itemShowHide = new JMenuItem("Hide");
        itemShowHide.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                showHide();
            }
        });

        this.itemCreateSticklet = new JMenuItem("Create Sticklet");
        this.itemCreateSticklet.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                Sticklet sticklet = new Sticklet("", "", stickletHandler);
                sticklet.setVisible(true);
            }
        });

        this.itemQuit = new JMenuItem("Quit");
        this.itemQuit.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        menu.add(this.itemShowHide);
        menu.addSeparator();
        menu.add(this.itemCreateSticklet);
        menu.addSeparator();
        menu.add(this.itemQuit);

        // create new system tray icon
        TrayIcon trayIcon = new TrayIcon(image, "WhiteMatter", menu);
        trayIcon.addActionListener(new java.awt.event.ActionListener()
        {

            public void actionPerformed(ActionEvent e)
            {
                showHide();
            }
        });

        // add tray icon to tray
        SystemTray systemTray = SystemTray.getDefaultSystemTray();
        systemTray.addTrayIcon(trayIcon);
    }

    /**
     * Show or hide form based on visibility and adjust tray menu label
     */
    public void showHide()
    {
        // set correct label
        if (this.isVisible())
        {
            this.itemShowHide.setText("Hide");
        }
        else
        {
            this.itemShowHide.setText("Show");
        }

        // show/hide form
        this.setVisible(!this.isVisible());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        panelNotebook = new javax.swing.JPanel();
        btnNextPage = new javax.swing.JButton();
        btnPreviousPage = new javax.swing.JButton();
        btnDeletePage = new javax.swing.JButton();
        btnSavePage = new javax.swing.JButton();
        btnNewPage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPage = new javax.swing.JTextArea();
        lblTitlePage = new javax.swing.JLabel();
        txtTitlePage = new javax.swing.JTextField();
        lblPage = new javax.swing.JLabel();
        panelSticklets = new javax.swing.JPanel();
        btnNewSticklet = new javax.swing.JButton();
        btnDeleteSticklet = new javax.swing.JButton();
        btnEditSticklet = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSticklets = new javax.swing.JTable();
        panelClipboard = new javax.swing.JPanel();
        btnUpdateClipboard = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtClipboard = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WhiteMatter");

        btnNextPage.setText(">");
        btnNextPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPageActionPerformed(evt);
            }
        });

        btnPreviousPage.setText("<");
        btnPreviousPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousPageActionPerformed(evt);
            }
        });

        btnDeletePage.setText("Delete Page");
        btnDeletePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletePageActionPerformed(evt);
            }
        });

        btnSavePage.setText("Save Page");
        btnSavePage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSavePageActionPerformed(evt);
            }
        });

        btnNewPage.setText("New Page");
        btnNewPage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPageActionPerformed(evt);
            }
        });

        txtPage.setColumns(20);
        txtPage.setLineWrap(true);
        txtPage.setRows(5);
        txtPage.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtPage);

        lblTitlePage.setText("Title:");

        txtTitlePage.setText("New Page");

        lblPage.setText("Page 1 of 1");

        javax.swing.GroupLayout panelNotebookLayout = new javax.swing.GroupLayout(panelNotebook);
        panelNotebook.setLayout(panelNotebookLayout);
        panelNotebookLayout.setHorizontalGroup(
            panelNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(panelNotebookLayout.createSequentialGroup()
                        .addComponent(lblTitlePage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitlePage, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))
                    .addGroup(panelNotebookLayout.createSequentialGroup()
                        .addComponent(btnPreviousPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletePage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSavePage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewPage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(btnNextPage))
                    .addComponent(lblPage))
                .addContainerGap())
        );
        panelNotebookLayout.setVerticalGroup(
            panelNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNotebookLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitlePage)
                    .addComponent(txtTitlePage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelNotebookLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPreviousPage)
                    .addComponent(btnNextPage)
                    .addComponent(btnDeletePage)
                    .addComponent(btnSavePage)
                    .addComponent(btnNewPage))
                .addContainerGap())
        );

        tabs.addTab("Notebook", panelNotebook);

        btnNewSticklet.setText("New Sticklet");
        btnNewSticklet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewStickletActionPerformed(evt);
            }
        });

        btnDeleteSticklet.setText("Delete Sticklet");
        btnDeleteSticklet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteStickletActionPerformed(evt);
            }
        });

        btnEditSticklet.setText("Edit Sticklet");
        btnEditSticklet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditStickletActionPerformed(evt);
            }
        });

        tableSticklets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process", "Note"
            }
        ));
        jScrollPane2.setViewportView(tableSticklets);

        javax.swing.GroupLayout panelStickletsLayout = new javax.swing.GroupLayout(panelSticklets);
        panelSticklets.setLayout(panelStickletsLayout);
        panelStickletsLayout.setHorizontalGroup(
            panelStickletsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStickletsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelStickletsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addGroup(panelStickletsLayout.createSequentialGroup()
                        .addComponent(btnDeleteSticklet)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditSticklet, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewSticklet)))
                .addContainerGap())
        );
        panelStickletsLayout.setVerticalGroup(
            panelStickletsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelStickletsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(panelStickletsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNewSticklet)
                    .addComponent(btnDeleteSticklet)
                    .addComponent(btnEditSticklet))
                .addContainerGap())
        );

        tabs.addTab("Sticklets", panelSticklets);

        btnUpdateClipboard.setText("Update");
        btnUpdateClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateClipboardActionPerformed(evt);
            }
        });

        txtClipboard.setColumns(20);
        txtClipboard.setLineWrap(true);
        txtClipboard.setRows(5);
        txtClipboard.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtClipboard);

        javax.swing.GroupLayout panelClipboardLayout = new javax.swing.GroupLayout(panelClipboard);
        panelClipboard.setLayout(panelClipboardLayout);
        panelClipboardLayout.setHorizontalGroup(
            panelClipboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClipboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelClipboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                    .addComponent(btnUpdateClipboard, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        panelClipboardLayout.setVerticalGroup(
            panelClipboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClipboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdateClipboard)
                .addContainerGap())
        );

        tabs.addTab("Clipboard", panelClipboard);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNextPageActionPerformed
    {//GEN-HEADEREND:event_btnNextPageActionPerformed
        notebookHandler.nextPage();
    }//GEN-LAST:event_btnNextPageActionPerformed

    private void btnPreviousPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnPreviousPageActionPerformed
    {//GEN-HEADEREND:event_btnPreviousPageActionPerformed
        notebookHandler.previousPage();
    }//GEN-LAST:event_btnPreviousPageActionPerformed

    private void btnNewPageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewPageActionPerformed
    {//GEN-HEADEREND:event_btnNewPageActionPerformed
        notebookHandler.newPage();
    }//GEN-LAST:event_btnNewPageActionPerformed

    private void btnDeletePageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeletePageActionPerformed
    {//GEN-HEADEREND:event_btnDeletePageActionPerformed
        notebookHandler.deletePage();
    }//GEN-LAST:event_btnDeletePageActionPerformed

    private void btnSavePageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSavePageActionPerformed
    {//GEN-HEADEREND:event_btnSavePageActionPerformed
        notebookHandler.savePage();
    }//GEN-LAST:event_btnSavePageActionPerformed

    private void btnUpdateClipboardActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdateClipboardActionPerformed
    {//GEN-HEADEREND:event_btnUpdateClipboardActionPerformed
        clipboardHandler.updateText();
    }//GEN-LAST:event_btnUpdateClipboardActionPerformed

    private void btnNewStickletActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnNewStickletActionPerformed
    {//GEN-HEADEREND:event_btnNewStickletActionPerformed
        Sticklet sticklet = new Sticklet("", "", this.stickletHandler);
        sticklet.setVisible(true);
    }//GEN-LAST:event_btnNewStickletActionPerformed

    private void btnEditStickletActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnEditStickletActionPerformed
    {//GEN-HEADEREND:event_btnEditStickletActionPerformed
        stickletHandler.editSelectedSticklet();
    }//GEN-LAST:event_btnEditStickletActionPerformed

    private void btnDeleteStickletActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteStickletActionPerformed
    {//GEN-HEADEREND:event_btnDeleteStickletActionPerformed
        stickletHandler.deleteSelectedSticklet();
    }//GEN-LAST:event_btnDeleteStickletActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletePage;
    private javax.swing.JButton btnDeleteSticklet;
    private javax.swing.JButton btnEditSticklet;
    private javax.swing.JButton btnNewPage;
    private javax.swing.JButton btnNewSticklet;
    private javax.swing.JButton btnNextPage;
    private javax.swing.JButton btnPreviousPage;
    private javax.swing.JButton btnSavePage;
    private javax.swing.JButton btnUpdateClipboard;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblPage;
    private javax.swing.JLabel lblTitlePage;
    private javax.swing.JPanel panelClipboard;
    private javax.swing.JPanel panelNotebook;
    private javax.swing.JPanel panelSticklets;
    public javax.swing.JTable tableSticklets;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTextArea txtClipboard;
    private javax.swing.JTextArea txtPage;
    private javax.swing.JTextField txtTitlePage;
    // End of variables declaration//GEN-END:variables
}
