package GUI;

import controllers.WriteFile;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;
import jdk.nashorn.internal.runtime.FindProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Huynh Lam - SE62917
 */
public class Main extends javax.swing.JFrame {

    //Declare
    String tmp;
    String findStr;
    boolean findDown;
    PrinterJob pj;
    boolean isNewFile;
    String filePath;
    String textoriginal;
    private UndoManager um;
    boolean wordWrap;

    public Main() {
        initComponents();
        um = new UndoManager();
        Document doc = this.txt.getDocument();
        doc.addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                um.addEdit(e.getEdit());
            }
        });

        setNotepad();
    }

    public void setNotepad() {
        // Set mnemonic
        mnNew.setMnemonic(KeyEvent.VK_N);
        mnSave.setMnemonic(KeyEvent.VK_S);
        mnCopy.setMnemonic(KeyEvent.VK_C);
        mnCut.setMnemonic(KeyEvent.VK_X);
        mnDelete.setMnemonic(KeyEvent.VK_DELETE);
        mnEdit.setMnemonic(KeyEvent.VK_E);
        mnFile.setMnemonic(KeyEvent.VK_F);
        mnPaste.setMnemonic(KeyEvent.VK_V);
        mnSelectAll.setMnemonic(KeyEvent.VK_A);
        mnCopy.setMnemonic(KeyEvent.VK_O);
        mnPrint.setMnemonic(KeyEvent.VK_P);
        mnUndo.setMnemonic(KeyEvent.VK_Z);
        mnRedo.setMnemonic(KeyEvent.VK_R);
        mnFile.setMnemonic(KeyEvent.VK_F);
        mnReplace.setMnemonic(KeyEvent.VK_J);

        tmp = txt.getText();
        findDown = true;
        findStr = "";
        isNewFile = true;
        textoriginal = "";
        filePath = "";
        wordWrap = false;
    }

    public String getFindStr() {
        return findStr;
    }

    public void setFindStr(String findStr) {
        this.findStr = findStr;
    }

    public boolean isFindDown() {
        return findDown;
    }

    public void setFindDown(boolean findDown) {
        this.findDown = findDown;
    }

    public JTextArea getTxt() {
        return txt;
    }

    public void setTxt(JTextArea txt) {
        this.txt = txt;
    }

    // <editor-fold defaultstate="collapsed" desc="VerifySave">
    private boolean VerifySave() {
        if (!textoriginal.equals(txt.getText())) {
            int result;
            Object[] options = {"Save", "Don't save", "Cancel"};

            if (!isNewFile) {
                result = JOptionPane.showOptionDialog(this, "Do you want to save change to" + filePath, "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            } else {
                String file = this.getTitle().replaceAll("- Notepad", "");
                result = JOptionPane.showOptionDialog(this, "Do you want to save change to" + file, "Notepad", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            }

            if (result == JOptionPane.YES_OPTION) {
                if (!isNewFile) {
                    try {
                        WriteFile wf = new WriteFile(filePath, txt.getText());
                        wf.Write();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JFileChooser save = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                    save.setFileFilter(filter);
                    int option = save.showSaveDialog(this);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        try {
                            BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath() + ".txt"));
                            out.write(this.txt.getText());
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (result == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        return true;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="FindText">
    public void FindText() {
        String s = txt.getText();
        int posStart = txt.getCaretPosition();
        int pos;

        if (findDown) {
            pos = s.indexOf(findStr, posStart);
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Not found!");
                txt.setCaretPosition(posStart);
            } else {
                txt.select(pos, pos + findStr.length());
            }
        } else {
            if (txt.getSelectedText() != null) {
                posStart -= txt.getSelectedText().length();
            }
            s = s.substring(0, posStart);
            pos = s.lastIndexOf(findStr);
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Not found!");
                txt.setCaretPosition(posStart);
            } else {
                txt.select(pos, pos + findStr.length());
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ReplaceText">
    public void ReplaceText(String content) {
        if (txt.getSelectedText() != null) {
            txt.replaceSelection(content);
        }
        String s = txt.getText();
        int posStart = txt.getCaretPosition();
        int pos = s.indexOf(findStr, posStart);
        if (pos == -1) {
            posStart = 0;
            pos = s.indexOf(findStr, posStart);
            if (pos == -1) {
                JOptionPane.showMessageDialog(this, "Not found!");
                return;
            }
        }
        txt.select(pos, pos + findStr.length());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ReplaceAll">
    public void ReplaceAll(String content) {
        String s = txt.getText();
        s = s.replaceAll(findStr, content);
        txt.setText(s);
    }
    // </editor-fold>

    private void formWindowClosing(java.awt.event.WindowEvent evt) {
        if (!VerifySave()) {
            return;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnFile = new javax.swing.JMenu();
        mnNew = new javax.swing.JMenuItem();
        mnOpen = new javax.swing.JMenuItem();
        mnSave = new javax.swing.JMenuItem();
        mnSaveAs = new javax.swing.JMenuItem();
        mnPrint = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnExit = new javax.swing.JMenuItem();
        mnEdit = new javax.swing.JMenu();
        mnUndo = new javax.swing.JMenuItem();
        mnRedo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnCopy = new javax.swing.JMenuItem();
        mnCut = new javax.swing.JMenuItem();
        mnPaste = new javax.swing.JMenuItem();
        mnDelete = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnFind = new javax.swing.JMenuItem();
        mnReplace = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mnSelectAll = new javax.swing.JMenuItem();
        mnFormat = new javax.swing.JMenu();
        mnWordWrap = new javax.swing.JMenuItem();
        mnChangeFont = new javax.swing.JMenuItem();
        mnView = new javax.swing.JMenu();
        mnLight = new javax.swing.JMenuItem();
        mnDark = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Notepad");

        txt.setColumns(20);
        txt.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        txt.setRows(5);
        jScrollPane1.setViewportView(txt);

        mnFile.setText("File");
        mnFile.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mnNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnNew.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnNew.setText("New");
        mnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNewActionPerformed(evt);
            }
        });
        mnFile.add(mnNew);

        mnOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        mnOpen.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        mnOpen.setText("Open");
        mnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnOpenActionPerformed(evt);
            }
        });
        mnFile.add(mnOpen);

        mnSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnSave.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnSave.setText("Save");
        mnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSaveActionPerformed(evt);
            }
        });
        mnFile.add(mnSave);

        mnSaveAs.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        mnSaveAs.setText("Save As...");
        mnSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSaveAsActionPerformed(evt);
            }
        });
        mnFile.add(mnSaveAs);

        mnPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        mnPrint.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        mnPrint.setText("Print");
        mnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPrintActionPerformed(evt);
            }
        });
        mnFile.add(mnPrint);
        mnFile.add(jSeparator2);

        mnExit.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        mnFile.add(mnExit);

        jMenuBar1.add(mnFile);

        mnEdit.setText("Edit");
        mnEdit.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnEdit.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                mnEditMenuSelected(evt);
            }
        });

        mnUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        mnUndo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnUndo.setText("Undo");
        mnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnUndoActionPerformed(evt);
            }
        });
        mnEdit.add(mnUndo);

        mnRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        mnRedo.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnRedo.setText("Redo");
        mnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnRedoActionPerformed(evt);
            }
        });
        mnEdit.add(mnRedo);
        mnEdit.add(jSeparator3);

        mnCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        mnCopy.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnCopy.setText("Copy");
        mnCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCopyActionPerformed(evt);
            }
        });
        mnEdit.add(mnCopy);

        mnCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnCut.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnCut.setText("Cut");
        mnCut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnCutActionPerformed(evt);
            }
        });
        mnEdit.add(mnCut);

        mnPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        mnPaste.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnPaste.setText("Paste");
        mnPaste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnPasteActionPerformed(evt);
            }
        });
        mnEdit.add(mnPaste);

        mnDelete.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        mnDelete.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnDelete.setText("Delete");
        mnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDeleteActionPerformed(evt);
            }
        });
        mnEdit.add(mnDelete);
        mnEdit.add(jSeparator1);

        mnFind.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        mnFind.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnFind.setText("Find");
        mnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnFindActionPerformed(evt);
            }
        });
        mnEdit.add(mnFind);

        mnReplace.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        mnReplace.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnReplace.setText("Replace");
        mnReplace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnReplaceActionPerformed(evt);
            }
        });
        mnEdit.add(mnReplace);
        mnEdit.add(jSeparator4);

        mnSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        mnSelectAll.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnSelectAll.setText("Select All");
        mnSelectAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnSelectAllActionPerformed(evt);
            }
        });
        mnEdit.add(mnSelectAll);

        jMenuBar1.add(mnEdit);

        mnFormat.setText("Format");
        mnFormat.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N

        mnWordWrap.setFont(new java.awt.Font("sansserif", 0, 24)); // NOI18N
        mnWordWrap.setText("Word Wrap");
        mnWordWrap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnWordWrapActionPerformed(evt);
            }
        });
        mnFormat.add(mnWordWrap);

        mnChangeFont.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnChangeFont.setText("Change Font");
        mnChangeFont.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnChangeFontActionPerformed(evt);
            }
        });
        mnFormat.add(mnChangeFont);

        jMenuBar1.add(mnFormat);

        mnView.setText("View Mode");
        mnView.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mnLight.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnLight.setText("Light");
        mnLight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnLightActionPerformed(evt);
            }
        });
        mnView.add(mnLight);

        mnDark.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        mnDark.setText("Dark");
        mnDark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnDarkActionPerformed(evt);
            }
        });
        mnView.add(mnDark);

        jMenuBar1.add(mnView);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void mnCopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCopyActionPerformed
        txt.copy();
    }//GEN-LAST:event_mnCopyActionPerformed

    private void mnCutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnCutActionPerformed
        txt.cut();
    }//GEN-LAST:event_mnCutActionPerformed

    private void mnPasteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPasteActionPerformed
        txt.paste();
    }//GEN-LAST:event_mnPasteActionPerformed

    private void mnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDeleteActionPerformed
        txt.replaceSelection("");
    }//GEN-LAST:event_mnDeleteActionPerformed

    private void mnSelectAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSelectAllActionPerformed
        txt.selectAll();
    }//GEN-LAST:event_mnSelectAllActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
        if (!VerifySave()) {
            return;
        }
        System.exit(0);
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSaveActionPerformed
        if (!textoriginal.equals(txt.getText())) {
            if (!isNewFile) {
                try {
                    textoriginal = txt.getText();
                    WriteFile wf = new WriteFile(filePath, textoriginal);
                    wf.Write();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JFileChooser save = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
                save.setFileFilter(filter);
                int option = save.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    try {
                        textoriginal = txt.getText();
                        filePath = save.getSelectedFile().getPath() + ".txt";
                        WriteFile wf = new WriteFile(filePath, textoriginal);
                        wf.Write();
                        isNewFile = false;
                        setTitle(save.getSelectedFile().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }//GEN-LAST:event_mnSaveActionPerformed

    private void mnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNewActionPerformed
        if (!VerifySave()) {
            return;
        }
        isNewFile = true;
        txt.setText("");
    }//GEN-LAST:event_mnNewActionPerformed

    private void mnLightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnLightActionPerformed
        txt.setBackground(Color.WHITE);
        txt.setForeground(Color.BLACK);
    }//GEN-LAST:event_mnLightActionPerformed

    private void mnDarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnDarkActionPerformed
        txt.setBackground(Color.BLACK);
        txt.setForeground(Color.WHITE);
    }//GEN-LAST:event_mnDarkActionPerformed

    private void mnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnUndoActionPerformed
        if (um.canUndo()) {
            try {
                um.undo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_mnUndoActionPerformed

    private void mnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnRedoActionPerformed
        if (um.canRedo()) {
            try {
                um.redo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_mnRedoActionPerformed

    private void mnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnFindActionPerformed
        FindDialog fd = new FindDialog(this, true);
        fd.setVisible(true);
    }//GEN-LAST:event_mnFindActionPerformed

    private void mnReplaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnReplaceActionPerformed
        ReplaceDialog rd = new ReplaceDialog(this, true);
        rd.setVisible(true);
    }//GEN-LAST:event_mnReplaceActionPerformed

    private void mnChangeFontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnChangeFontActionPerformed
        FontDialog fd = new FontDialog(this, true);
        fd.setVisible(true);
    }//GEN-LAST:event_mnChangeFontActionPerformed

    private void mnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnOpenActionPerformed
        if (!VerifySave()) {
            return;
        }

        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        open.setFileFilter(filter);
        int option = open.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            this.txt.setText("");
            try {
                Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
                while (scan.hasNext()) {
                    this.txt.append(scan.nextLine() + "\n");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Can't open file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_mnOpenActionPerformed

    private void mnSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnSaveAsActionPerformed
        JFileChooser save = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        save.setFileFilter(filter);
        int option = save.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                textoriginal = txt.getText();
                filePath = save.getSelectedFile().getPath() + ".txt";
                WriteFile wf = new WriteFile(filePath, textoriginal);
                wf.Write();
                isNewFile = false;
                setTitle(save.getSelectedFile().getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_mnSaveAsActionPerformed

    private void mnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnPrintActionPerformed
        try {
            txt.print();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_mnPrintActionPerformed

    private void mnEditMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_mnEditMenuSelected
        boolean setFind = !"".equals(txt.getText());
        mnFind.setEnabled(setFind);
        boolean setEdit = !(txt.getSelectedText() == null);
        mnCopy.setEnabled(setEdit);
        mnCut.setEnabled(setEdit);
        mnDelete.setEnabled(setEdit);
    }//GEN-LAST:event_mnEditMenuSelected

    private void mnWordWrapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnWordWrapActionPerformed
        if (wordWrap == false) {
            wordWrap = true;
            txt.setLineWrap(true);
            txt.setWrapStyleWord(true);
        } else {
            wordWrap = false;
            txt.setLineWrap(false);
            txt.setWrapStyleWord(false);
        }

    }//GEN-LAST:event_mnWordWrapActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem mnChangeFont;
    private javax.swing.JMenuItem mnCopy;
    private javax.swing.JMenuItem mnCut;
    private javax.swing.JMenuItem mnDark;
    private javax.swing.JMenuItem mnDelete;
    private javax.swing.JMenu mnEdit;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenu mnFile;
    private javax.swing.JMenuItem mnFind;
    private javax.swing.JMenu mnFormat;
    private javax.swing.JMenuItem mnLight;
    private javax.swing.JMenuItem mnNew;
    private javax.swing.JMenuItem mnOpen;
    private javax.swing.JMenuItem mnPaste;
    private javax.swing.JMenuItem mnPrint;
    private javax.swing.JMenuItem mnRedo;
    private javax.swing.JMenuItem mnReplace;
    private javax.swing.JMenuItem mnSave;
    private javax.swing.JMenuItem mnSaveAs;
    private javax.swing.JMenuItem mnSelectAll;
    private javax.swing.JMenuItem mnUndo;
    private javax.swing.JMenu mnView;
    private javax.swing.JMenuItem mnWordWrap;
    private javax.swing.JTextArea txt;
    // End of variables declaration//GEN-END:variables
}
