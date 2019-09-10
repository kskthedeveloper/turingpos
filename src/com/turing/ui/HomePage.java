/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.turing.ui;

import com.turing.model.Item;
import com.turing.model.ItemCategory;
import com.turing.model.User;
import com.turing.model.UserType;
import com.turing.service.itemcategory.ItemCategoryService;
import com.turing.service.itemcategory.ItemCategoryServiceImpl;
import com.turing.service.item.ItemService;
import com.turing.service.item.ItemServiceImpl;
import com.turing.service.user.UserService;
import com.turing.service.user.UserServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kaung
 */
public class HomePage extends javax.swing.JFrame {
    LoginPage loginPage;
    DefaultTableModel itemCategoryTabelModel;
    DefaultTableModel itemTabelModel;
    ItemCategoryService itemCategoryService = new ItemCategoryServiceImpl();
    ItemService itemService = new ItemServiceImpl();
    Map<String, ItemCategory> itemCategoryMap;
    UserService userService = UserServiceImpl.getUserService();
    /**
     * Creates new form HomePage
     */
    public HomePage(LoginPage loginPage) {
        this.loginPage = loginPage;
        User user = this.userService.getUser();
        
        
        initComponents();
        
        if(user.getUserType() == UserType.CASHIER) {
            tabbedPane.remove(userPanel2);
            tabbedPane.remove(reportPanel1);
            tabbedPane.remove(purchasePanel1);
        }
        
        this.loadItemCategory();
        this.loadItem();
        this.loadItemCategoryCombo();
       
    }
    
    public void loadItemCategory() {
        
        itemCategoryTabelModel = (DefaultTableModel) tblItemCategory.getModel();
        List<ItemCategory> itemCategories = itemCategoryService.getAll();
        
        for(ItemCategory itemCategory: itemCategories) {
            Object row[] = new Object[2];
            row[0] = itemCategory.getId();
            row[1] = itemCategory.getName();
            
            
            itemCategoryTabelModel.addRow(row);
        }
    }
    
    public void loadItem() {
        
        itemTabelModel = (DefaultTableModel) tblItem.getModel();
        itemTabelModel.getDataVector().removeAllElements();
        List<Item> items = itemService.getAll();
        
        for(Item item: items) {
            Object row[] = new Object[6];
            row[0] = item.getId();
            row[1] = item.getItemCode();
            row[2] = item.getName();
            row[3] = item.getPrice();
            row[4] = item.getItemCategory().getName();
            row[5] = item.getStockQuantity();
            
            itemTabelModel.addRow(row);
        }
    }
    
    public void loadItemCategoryCombo() {
        cbItemCategory.removeAllItems();
        this.itemCategoryMap = populateItemCategoryCombo();
        for(String s: itemCategoryMap.keySet()) {
            cbItemCategory.addItem(s);
        }
    }
    
    public Map<String, ItemCategory> populateItemCategoryCombo() {
        Map<String, ItemCategory> map = new HashMap();
        for(ItemCategory itemCategory: itemCategoryService.getAll()) {
            map.put(itemCategory.getName(), itemCategory);
        }
        return map;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        paneItem = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtItemCategoryName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnAddItemCategory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblItemCategory = new javax.swing.JTable();
        btnEditItemCategory = new javax.swing.JButton();
        txtItemCategoryId = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtItemName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbItemCategory = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItem = new javax.swing.JTable();
        lblItemId = new javax.swing.JLabel();
        btnItemAdd = new javax.swing.JButton();
        btnItemEdit = new javax.swing.JButton();
        txtItemPrice = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtItemCode = new javax.swing.JTextField();
        salePanel1 = new com.turing.ui.SalePanel();
        purchasePanel1 = new com.turing.ui.PurchasePanel();
        reportPanel1 = new com.turing.ui.ReportPanel();
        userPanel2 = new com.turing.ui.UserPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabbedPaneMouseClicked(evt);
            }
        });

        jLabel1.setText("Item Category");

        txtItemCategoryName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemCategoryNameActionPerformed(evt);
            }
        });

        jLabel2.setText("Name:");

        btnAddItemCategory.setText("Add");
        btnAddItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemCategoryActionPerformed(evt);
            }
        });

        tblItemCategory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItemCategory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemCategoryMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblItemCategory);

        btnEditItemCategory.setText("Edit");
        btnEditItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditItemCategoryActionPerformed(evt);
            }
        });

        txtItemCategoryId.setEditable(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnAddItemCategory)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEditItemCategory))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(txtItemCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(txtItemCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(47, 47, 47))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtItemCategoryId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemCategoryName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddItemCategory)
                    .addComponent(btnEditItemCategory))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setText("Item:");

        txtItemName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemNameActionPerformed(evt);
            }
        });

        jLabel4.setText("Name");

        jLabel5.setText("Price");

        cbItemCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbItemCategoryActionPerformed(evt);
            }
        });

        jLabel6.setText("Category");

        tblItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "code", "name", "price", "category", "stock quantity"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblItemMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblItem);

        btnItemAdd.setText("Add");
        btnItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemAddActionPerformed(evt);
            }
        });

        btnItemEdit.setText("Edit");
        btnItemEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnItemEditActionPerformed(evt);
            }
        });

        jLabel7.setText("Code");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnItemAdd))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblItemId)
                                .addGap(94, 94, 94)
                                .addComponent(jLabel7))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtItemCode, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(txtItemPrice))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnItemEdit))
                .addGap(22, 22, 22))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblItemId)
                    .addComponent(jLabel7)
                    .addComponent(txtItemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(cbItemCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnItemAdd)
                    .addComponent(btnItemEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout paneItemLayout = new javax.swing.GroupLayout(paneItem);
        paneItem.setLayout(paneItemLayout);
        paneItemLayout.setHorizontalGroup(
            paneItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneItemLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        paneItemLayout.setVerticalGroup(
            paneItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Item", paneItem);
        tabbedPane.addTab("Sale", salePanel1);
        tabbedPane.addTab("Purchase", purchasePanel1);
        tabbedPane.addTab("Report", reportPanel1);
        tabbedPane.addTab("User", userPanel2);

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabbedPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedPaneMouseClicked
        // TODO add your handling code here:
        //        this.add(new SalePanel());
        this.loadItem();

    }//GEN-LAST:event_tabbedPaneMouseClicked

    private void btnItemEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemEditActionPerformed
        // TODO add your handling code here:
        int id = Integer.parseInt(lblItemId.getText());
        String itemName = txtItemName.getText();
        int price = Integer.parseInt(txtItemPrice.getText());
        String itemCategory = cbItemCategory.getItemAt(cbItemCategory.getSelectedIndex());
        String itemCode = txtItemCode.getText();

        if(itemName != "" && txtItemPrice.getText() != "" && itemCode != "") {
            this.itemService.edit(id, itemCode, itemName, price, this.itemCategoryMap.get(itemCategory), 0);
            itemTabelModel.setValueAt(lblItemId.getText(), tblItem.getSelectedRow(), 0);
            itemTabelModel.setValueAt(itemCode, tblItem.getSelectedRow(), 1);
            itemTabelModel.setValueAt(itemName, tblItem.getSelectedRow(), 2);
            itemTabelModel.setValueAt(price, tblItem.getSelectedRow(), 3);
            itemTabelModel.setValueAt(itemCategory, tblItem.getSelectedRow(), 4);

            this.lblItemId.setText("");
            this.txtItemName.setText("");
            this.txtItemPrice.setText("0");
            this.txtItemCode.setText("");
            this.cbItemCategory.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnItemEditActionPerformed

    private void btnItemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnItemAddActionPerformed
        // TODO add your handling code here:
        String itemName = txtItemName.getText();
        int price = Integer.parseInt(txtItemPrice.getText());
        String itemCategory = cbItemCategory.getItemAt(cbItemCategory.getSelectedIndex());
        String itemCode = txtItemCode.getText();

        if(itemName != "" && txtItemPrice.getText() != "" && itemCode !="") {
            int id = this.itemService.save(itemCode, itemName, price, this.itemCategoryMap.get(itemCategory), 0);
            this.itemTabelModel.insertRow(this.itemTabelModel.getRowCount(), new Object[]{id, itemCode, itemName, price, this.itemCategoryMap.get(itemCategory).getName(), 0});
            this.txtItemName.setText("");
            this.txtItemCode.setText("");
            this.txtItemPrice.setText("0");
        }
    }//GEN-LAST:event_btnItemAddActionPerformed

    private void tblItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemMouseClicked
        // TODO add your handling code here:
        String itemId = String.valueOf(itemTabelModel.getValueAt(tblItem.getSelectedRow(), 0));
        String itemCode = String.valueOf(itemTabelModel.getValueAt(tblItem.getSelectedRow(), 1));
        String itemName = String.valueOf(itemTabelModel.getValueAt(tblItem.getSelectedRow(), 2));
        String price = String.valueOf(itemTabelModel.getValueAt(tblItem.getSelectedRow(), 3));
        String itemCategoryKey = String.valueOf(itemTabelModel.getValueAt(tblItem.getSelectedRow(), 4));

        lblItemId.setText(itemId);
        txtItemCode.setText(itemCode);
        txtItemName.setText(itemName);
        txtItemPrice.setText(price);
        cbItemCategory.setSelectedItem(itemCategoryKey);

    }//GEN-LAST:event_tblItemMouseClicked

    private void cbItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbItemCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbItemCategoryActionPerformed

    private void txtItemNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemNameActionPerformed

    private void btnEditItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditItemCategoryActionPerformed
        // TODO add your handling code here:

        int itemCategoryId = Integer.parseInt(txtItemCategoryId.getText());
        String itemCategoryName = txtItemCategoryName.getText();
        if(itemCategoryName != "") {
            this.itemCategoryService.update(itemCategoryId, itemCategoryName);

            itemCategoryTabelModel.setValueAt(txtItemCategoryId.getText(), tblItemCategory.getSelectedRow(), 0);
            itemCategoryTabelModel.setValueAt(txtItemCategoryName.getText(), tblItemCategory.getSelectedRow(), 1);

            txtItemCategoryId.setText("");
            txtItemCategoryName.setText("");
            this.loadItemCategoryCombo();
        }
    }//GEN-LAST:event_btnEditItemCategoryActionPerformed

    private void tblItemCategoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemCategoryMouseClicked
        // TODO add your handling code here:
        String itemCategoryId = String.valueOf(itemCategoryTabelModel.getValueAt(tblItemCategory.getSelectedRow(), 0));
        String itemCategoryName = String.valueOf(itemCategoryTabelModel.getValueAt(tblItemCategory.getSelectedRow(), 1));

        txtItemCategoryId.setText(itemCategoryId);
        txtItemCategoryName.setText(itemCategoryName);
    }//GEN-LAST:event_tblItemCategoryMouseClicked

    private void btnAddItemCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemCategoryActionPerformed
        // TODO add your handling code here:
        String itemCategoryName = txtItemCategoryName.getText();
        if(itemCategoryName != "") {
            int id = this.itemCategoryService.save(itemCategoryName);
            this.itemCategoryTabelModel.insertRow(this.itemCategoryTabelModel.getRowCount(), new Object[]{id, itemCategoryName});
            this.txtItemCategoryName.setText("");
            this.loadItemCategoryCombo();
        }
    }//GEN-LAST:event_btnAddItemCategoryActionPerformed

    private void txtItemCategoryNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCategoryNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtItemCategoryNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.userService.setUser(null);
        this.setVisible(false);
        this.loginPage.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private ItemCategory getItemCategory(String name) {
        return itemCategoryMap.get(name);
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddItemCategory;
    private javax.swing.JButton btnEditItemCategory;
    private javax.swing.JButton btnItemAdd;
    private javax.swing.JButton btnItemEdit;
    private javax.swing.JComboBox<String> cbItemCategory;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblItemId;
    private javax.swing.JPanel paneItem;
    private com.turing.ui.PurchasePanel purchasePanel1;
    private com.turing.ui.ReportPanel reportPanel1;
    private com.turing.ui.SalePanel salePanel1;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tblItem;
    private javax.swing.JTable tblItemCategory;
    private javax.swing.JTextField txtItemCategoryId;
    private javax.swing.JTextField txtItemCategoryName;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemName;
    private javax.swing.JFormattedTextField txtItemPrice;
    private com.turing.ui.UserPanel userPanel2;
    // End of variables declaration//GEN-END:variables
}
