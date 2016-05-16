/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package werewolvesinwonderland.client.view;

import java.awt.Color;
import java.awt.Component;
import java.util.Random;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import werewolvesinwonderland.protocol.Identification;
import werewolvesinwonderland.protocol.model.Player;

/**
 *
 * @author Tifani
 */
public class PlayerPanel extends javax.swing.JPanel implements TableCellRenderer {

    /**
     * Creates new form PlayerPanel
     */    
    /*public PlayerPanel(GameFrame frame, Player player) {
        this.frame = frame;
        this.player = player;
        initComponents();
        initPlayerStyle();
    }*/
    
    public PlayerPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbUsername = new javax.swing.JLabel();
        icPlayer = new javax.swing.JLabel();
        lbRole = new javax.swing.JLabel();
        btnKill = new javax.swing.JButton();

        setBackground(new Color(204,204,255,100));
        setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        setPreferredSize(new java.awt.Dimension(232, 232));
        setVerifyInputWhenFocusTarget(false);

        lbUsername.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lbUsername.setForeground(new java.awt.Color(0, 51, 51));
        lbUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUsername.setText("Snowball");

        icPlayer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icPlayer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/werewolvesinwonderland/client/assets/ic_player132_werewolf1.png"))); // NOI18N

        lbRole.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbRole.setText("Werewolf");
        lbRole.setToolTipText("");
        lbRole.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbRole.setHorizontalTextPosition(SwingConstants.CENTER);
        lbRole.setMaximumSize(new java.awt.Dimension(110, 14));
        lbRole.setMinimumSize(new java.awt.Dimension(110, 14));
        lbRole.setPreferredSize(new java.awt.Dimension(110, 14));

        btnKill.setText("Kill");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbRole, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
            .addComponent(icPlayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(92, 92, 92)
                .addComponent(btnKill)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbUsername)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKill)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    private int[] werewolfPic = {1,2};
    private int[] civilianPic = {1,2,3,4};
    /*
    private GameFrame frame;
    private Player player;*/
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKill;
    private javax.swing.JLabel icPlayer;
    private javax.swing.JLabel lbRole;
    private javax.swing.JLabel lbUsername;
    // End of variables declaration//GEN-END:variables

    private void initPlayerStyle(Player player) {
        //Random random = new Random();
        //int i;
        
        setOpaque(true);
        setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(255, 255, 255)));
        icPlayer.setVisible(true);
        lbUsername.setVisible(true);
        lbRole.setVisible(true);
        btnKill.setVisible(true);
        
        lbUsername.setText(player.getUsername());
        if (player.isAlive()) {
            //i = random.nextInt(civilianPic.length - 1 + 1) + 1;
            icPlayer.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                PlayerAvatarMaker.createStringImageResource(player, "132"))));
            lbRole.setText("?");
        } else {
            /*
            if (player.getRole().equals(Identification.ROLE_WEREWOLF))
                i = random.nextInt(werewolfPic.length - 1 + 1) + 1;
            else
                i = random.nextInt(civilianPic.length - 1 + 1) + 1;*/
            icPlayer.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                PlayerAvatarMaker.createStringImageResource(player, "132"))));
            lbRole.setText(player.getRole().substring(0, 1).toUpperCase()
                + player.getRole().substring(1));
        }            
        
        /*
        btnKill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.out.println("MIAW");
                frame.showErrorMessage("BERHASIL CLICK");
            }
        }); */
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value!=null) {
            initPlayerStyle((Player)value);
        } else {
            setOpaque(false);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(3,3,3,3));
            icPlayer.setVisible(false);
            lbUsername.setVisible(false);
            lbRole.setVisible(false);
            btnKill.setVisible(false);
        }
        return this;
    }
    
    public void disablePanel() {
        icPlayer.setVisible(false);
        lbUsername.setVisible(false);
        lbRole.setVisible(false);
        btnKill.setVisible(false);
    }
    
    public void enablePanel() {
        icPlayer.setVisible(true);
        lbUsername.setVisible(true);
        lbRole.setVisible(true);
        btnKill.setVisible(true);
    }
    
}
