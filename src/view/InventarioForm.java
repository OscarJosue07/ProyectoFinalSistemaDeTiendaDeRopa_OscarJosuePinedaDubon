/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import dao.InventarioDAO;
import dao.ProductoDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Inventario;
import model.Producto;
/**
 *
 * @author Oscar Josue
 */
public class InventarioForm extends javax.swing.JFrame {
    
    String rol;
    String nombre;
    
    Inventario inv = new Inventario();
    InventarioDAO invDao = new InventarioDAO();
    DefaultTableModel modelo = new DefaultTableModel();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(InventarioForm.class.getName());
    
        
        public void registrarAccion(String accion) {
    String sql = "INSERT INTO bitacora (usuario, rol, accion) VALUES (?, ?, ?)";
    db.Conexion dbCon = new db.Conexion(); 
    
    try (java.sql.Connection con = dbCon.conectar(); 
         java.sql.PreparedStatement ps = con.prepareStatement(sql)) {
        
        if (con != null) {
            ps.setString(1, this.nombre); // Asegúrate de que 'nombre' sea global
            ps.setString(2, this.rol);    // Asegúrate de que 'rol' sea global
            ps.setString(3, accion);
            ps.executeUpdate();
        }
    } catch (java.sql.SQLException e) {
        System.out.println("Error en bitácora: " + e.getMessage());
    }
}

public void ListarInventario() {
    try {
        List<Inventario> listaInv = invDao.ListarInventario();
        
        // 1. Instanciamos el DAO de productos para poder ver las salidas reales
        ProductoDAO proDao = new ProductoDAO();
        List<Producto> listaPro = proDao.ListarProductos(); 

        DefaultTableModel modelo = (DefaultTableModel) tblInventario.getModel();
        modelo.setRowCount(0);
        Object[] ob = new Object[7];

        for (int i = 0; i < listaInv.size(); i++) {
            int idProdInventario = listaInv.get(i).getId_producto();
            
            // 2. Buscamos el stock real cruzando datos con el Producto
            int stockReal = 0;
            for (int j = 0; j < listaPro.size(); j++) {
                if (listaPro.get(j).getId() == idProdInventario) {
                    int ent = listaPro.get(j).getEntrada();
                    int sal = listaPro.get(j).getSalida();
                    stockReal = ent - sal; // AQUÍ ES DONDE SE HACE EL 4 - 2 = 2
                    break;
                }
            }

            ob[0] = listaInv.get(i).getId_inventario();
            ob[1] = idProdInventario;
            ob[2] = listaInv.get(i).getCantidad(); // Esto mostrará el 4 que guardaste
            ob[3] = listaInv.get(i).getUbicacion();
            ob[4] = listaInv.get(i).getTipo();
            ob[5] = listaInv.get(i).getFecha();
            
            // 3. Mostramos el resultado de la resta en la columna de Stock Actual
            ob[6] = stockReal; // Esto mostrará el 2

            modelo.addRow(ob);
        }
        tblInventario.setModel(modelo);
    } catch (Exception e) {
        System.out.println("Error al listar: " + e.toString());
    }
}
        
        
    public InventarioForm(String rolRecibido,String nombreRecibido) {
    initComponents();
    this.rol = rolRecibido;
    this.nombre = nombreRecibido;
    this.setLocationRelativeTo(null);
    ListarInventario();
    
        if (this.rol.equalsIgnoreCase("Admin") || this.rol.equalsIgnoreCase("Administrador")) {
        // Si es jefe, sale en verde
        lblRolActual.setText("En línea: Administrador - " + nombre);
        lblRolActual.setForeground(java.awt.Color.GREEN);
    } else {
        // Si es trabajador, sale en azul (o el color que prefieras)
        lblRolActual.setText("En línea: Trabajador - " + nombre);
        lblRolActual.setForeground(java.awt.Color.CYAN); 
    }
    
     restringirPermisos();
    }
    
      private void restringirPermisos() {
    if (this.rol.equals("Trabajador") || this.rol.equals("Empleado")) {
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInventario = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblRolActual = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 144, -1));

        tblInventario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Id Producto", "Cantidad", "Ubicacion", "Tipo", "Fecha", "Stock Actual"
            }
        ));
        tblInventario.setSelectionBackground(new java.awt.Color(212, 175, 55));
        tblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInventarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInventario);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 750, 500));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(85, 85, 85));
        jLabel6.setText("Buscar:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 50, 70, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/mapequeño.png"))); // NOI18N
        jLabel7.setText("jLabel4");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 120, 70));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/logo_elegance_store_300x125.png"))); // NOI18N
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, 360, 260));

        lblRolActual.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblRolActual.setForeground(new java.awt.Color(51, 255, 0));
        jPanel1.add(lblRolActual, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 0, 540, 30));

        btnSalir.setBackground(new java.awt.Color(178, 59, 59));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 580, 140, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1636, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
DefaultTableModel modelo = (DefaultTableModel) tblInventario.getModel();
    TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
    tblInventario.setRowSorter(sorter);
    
    // Filtra en todas las columnas ignorando mayúsculas/minúsculas
    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtBuscar.getText()));
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void tblInventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInventarioMouseClicked
    int fila = tblInventario.getSelectedRow();
    if (fila == -1) return;

    try {
        // Leemos las 6 columnas en el orden que aparecen en tu imagen de NetBeans
        String idInv    = String.valueOf(tblInventario.getValueAt(fila, 0)); // Id
        String idPro    = String.valueOf(tblInventario.getValueAt(fila, 1)); // Id Producto
        String cantidad = String.valueOf(tblInventario.getValueAt(fila, 2)); // Cantidad
        String ubicacion = String.valueOf(tblInventario.getValueAt(fila, 3)); // Ubicacion
        String tipo     = String.valueOf(tblInventario.getValueAt(fila, 4)); // Tipo
        String fecha    = String.valueOf(tblInventario.getValueAt(fila, 5)); // Fecha

        // Imprimimos en consola para verificar que no hay error
        System.out.println("Seleccionado ID Inventario: " + idInv + " - Producto: " + idPro);

    } catch (Exception e) {
        System.err.println("Error al leer la tabla: " + e.getMessage());
    }
    }//GEN-LAST:event_tblInventarioMouseClicked

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // Abrimos el menú enviando de nuevo los datos del usuario
        // (Luego podemos hacer que estos datos sean dinámicos)
        MenuPrincipal menu = new MenuPrincipal(this.rol, this.nombre);
        menu.setVisible(true);
        menu.setLocationRelativeTo(null);

        // Cerramos la ventana actual de Clientes
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRolActual;
    private javax.swing.JTable tblInventario;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
