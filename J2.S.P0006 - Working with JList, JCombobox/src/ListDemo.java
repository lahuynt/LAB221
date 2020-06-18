
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Huynh Lam - SE62917
 */
public class ListDemo extends javax.swing.JFrame {

    /**
     * Creates new form ListDemo
     */
    public ListDemo() {
        initComponents();
        loadImageName();
    }

    // Declare image
    ImageIcon iconHaiji = new ImageIcon("img/Haiji.jpg");
    ImageIcon iconKakeru = new ImageIcon("img/Kakeru.jpg");
    ImageIcon iconNicochan = new ImageIcon("img/Nicochan.jpg");
    ImageIcon iconNira = new ImageIcon("img/Nira.jpg");
    ImageIcon iconYuki = new ImageIcon("img/Yuki.jpg");

    // Load image name to JList
    private void loadImageName() {
        String imageName[] = {"Haiji", "Kakeru", "Nicochan", "Nira", "Yuki"};
        lsImg.setListData(imageName);
    }

    // Resize Image
    private ImageIcon resizeImg(ImageIcon icon) {
        Image img = icon.getImage();
        img = img.getScaledInstance(lbImg.getWidth(), lbImg.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        return icon;
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
        lsImg = new javax.swing.JList<>();
        lbImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("List Demo");

        lsImg.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lsImg.setSelectedIndex(1);
        lsImg.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lsImgValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lsImg);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbImg, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lsImgValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lsImgValueChanged
        Object selected = lsImg.getSelectedValue();

        // icon Haiji
        if (selected.toString().equals("Haiji")) {
            lbImg.setIcon(resizeImg(iconHaiji));
        }

        // icon Kakeru
        if (selected.toString().equals("Kakeru")) {
            lbImg.setIcon(resizeImg(iconKakeru));
        }

        // icon Nicochan
        if (selected.toString().equals("Nicochan")) {
            lbImg.setIcon(resizeImg(iconNicochan));
        }

        // icon Nira
        if (selected.toString().equals("Nira")) {
            lbImg.setIcon(resizeImg(iconNira));
        }

        // icon Yuki
        if (selected.toString().equals("Yuki")) {
            lbImg.setIcon(resizeImg(iconYuki));
        }
    }//GEN-LAST:event_lsImgValueChanged

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
            java.util.logging.Logger.getLogger(ListDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListDemo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListDemo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbImg;
    private javax.swing.JList<String> lsImg;
    // End of variables declaration//GEN-END:variables
}