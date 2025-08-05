package Pantallaprincipal;

import Clases.Auto;
import Clases.Cliente;
import Clases.Moto;
import Clases.OrdenTrabajo;
import Clases.Servicio;
import Clases.Vehiculo;
import Excepciones.DatoInvalidoException;
import Excepciones.RegistroDuplicadoException;
import Gestores.GestorCliente;
import Gestores.GestorOrdenTrabajo;
import Gestores.GestorServicio;
import Gestores.GestorVehiculo;
import java.awt.BorderLayout;
import java.io.File;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class Pantalla extends javax.swing.JFrame {
GestorCliente gestorCliente = new GestorCliente();
GestorVehiculo gestorVehiculo = new GestorVehiculo();
GestorServicio gestorServicio = new GestorServicio();

GestorOrdenTrabajo gestorOrdenTrabajo = new GestorOrdenTrabajo(gestorCliente, gestorVehiculo, gestorServicio);
private void cargarClientesEnCombo() {
    cmbClientes.removeAllItems();
    for (Cliente c : gestorCliente.getListaClientes()) {
        cmbClientes.addItem(c.getIdCliente());
    }
    
    
   rbAuto.addActionListener(e -> {
    lblNumPuertas.setVisible(true);
    txtNumPuertas.setVisible(true);
    lblTipoMoto.setVisible(false);
    txtTipoMoto.setVisible(false);
});

rbMoto.addActionListener(e -> {
    lblNumPuertas.setVisible(false);
    txtNumPuertas.setVisible(false);
    lblTipoMoto.setVisible(true);
    txtTipoMoto.setVisible(true);
});
}
  private void limpiarCamposVehiculo() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtAño.setText("");
        txtNumPuertas.setText("");
        txtTipoMoto.setText("");
        cmbClientes.setSelectedIndex(0); 

        lblNumPuertas.setVisible(false);
        txtNumPuertas.setVisible(false);
        lblTipoMoto.setVisible(false);
        txtTipoMoto.setVisible(false);
        
        grupoTipoVehiculo.clearSelection(); }
    private void limpiarCamposServicio() {
    txtCodigoServicio.setText("");
    txtDescripcion.setText("");
    txtCosto.setText("");
}
    private void cargarVehiculosEnComboOrden() {
    cmbVehiculosOrden.removeAllItems();  
    
    for (Vehiculo v : gestorVehiculo.getListaVehiculos()) {
        cmbVehiculosOrden.addItem(v.getPlaca());
    }
}
private void cargarServiciosEnComboOrden() {
    cmbServiciosOrden.removeAllItems();  

    for (Servicio s : gestorServicio.getListaServicios()) {
        cmbServiciosOrden.addItem(s.getDescripcion());
    }
}
private void limpiarCamposOrden() {
    txtIdOrden.setText("");
    txtNombreClienteOrden.setText("");
    cmbVehiculosOrden.setSelectedIndex(-1); 
    cmbServiciosOrden.setSelectedIndex(-1); 
    txtFechaIngreso.setText("");
    txtFechaEntrega.setText("");
    chkCerrada.setSelected(false);
}


    public Pantalla() {
        initComponents();
        cargarVehiculosEnComboOrden();
    cargarServiciosEnComboOrden();
lblNumPuertas.setVisible(false);
txtNumPuertas.setVisible(false);
lblTipoMoto.setVisible(false);
txtTipoMoto.setVisible(false);
    }
public void cargarClientesEnTabla() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[] { "ID", "Nombre", "Teléfono", "Dirección" });

        for (Cliente c : gestorCliente.getListaClientes()) {
            modelo.addRow(new Object[] {
                c.getIdCliente(),
                c.getNombre(),
                c.getTelefono(),
                c.getDireccion()
            });
        }
   tblClientes.setModel(modelo);
    }
  public void cargarVehiculosEnTabla() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[] {
        "Placa", "Marca", "Modelo", "Año", "Tipo", "Dato adicional", "Cliente"
    });

    for (Vehiculo v : gestorVehiculo.getListaVehiculos()) {
        String tipo = (v instanceof Auto) ? "Auto" : "Moto";
        String extra = (v instanceof Auto)
            ? ((Auto) v).getNumPuertas() + " puertas"
            : ((Moto) v).getTipoMoto();

        modelo.addRow(new Object[] {
            v.getPlaca(),
            v.getMarca(),
            v.getModelo(),
            v.getAño(),
            tipo,
            extra,
            v.getCliente().getIdCliente()
        });
    }

    tblVehiculos.setModel(modelo);

}
  public void cargarServiciosEnTabla() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[] { "Código", "Descripción", "Costo" });

    for (Servicio s : gestorServicio.getListaServicios()) {
        modelo.addRow(new Object[] {
            s.getCodigo(),
            s.getDescripcion(),
            s.getCosto()
        });
    }

    tblServicios.setModel(modelo);
}

public void cargarOrdenesEnTabla() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new String[] {
        "ID", "Cliente", "Vehículo", "Servicios", "Fecha Ingreso", "Fecha Entrega", "Cerrada", "Total"
    });

    for (OrdenTrabajo ot : gestorOrdenTrabajo.getListaOrdenes()) {
        // Obtener descripciones de servicios
        StringBuilder descripciones = new StringBuilder();
        for (Servicio s : ot.getServicios()) {
            descripciones.append(s.getDescripcion()).append(", ");
        }
        if (descripciones.length() > 0) {
            descripciones.setLength(descripciones.length() - 2);
        }

        modelo.addRow(new Object[] {
            ot.getIdOrden(),
            ot.getCliente().getNombre(),
            ot.getVehiculo().getPlaca(),
            descripciones.toString(),
            ot.getFechaIngreso(),
            ot.getFechaEntrega(),
            ot.isCerrada() ? "Sí" : "No",
            ot.calcularCostoTotal()
        });
    }

    tblOrdenes.setModel(modelo);
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoTipoVehiculo = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelClientes = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDireccion = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnRegistrar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        Panelvehiculos = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblVehiculos = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        cmbClientes = new javax.swing.JComboBox<>();
        txtModelo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAño = new javax.swing.JTextField();
        rbAuto = new javax.swing.JRadioButton();
        rbMoto = new javax.swing.JRadioButton();
        lblTipoMoto = new javax.swing.JLabel();
        lblNumPuertas = new javax.swing.JLabel();
        btnRegistrarVehiculo = new javax.swing.JButton();
        btnConsultarVehiculo = new javax.swing.JButton();
        btnEliminarVehiculo = new javax.swing.JButton();
        btnModificarVehiculo = new javax.swing.JButton();
        txtTipoMoto = new javax.swing.JTextField();
        txtNumPuertas = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoServicio = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        tablaservicios = new javax.swing.JScrollPane();
        tblServicios = new javax.swing.JTable();
        btnRegistrarServicio = new javax.swing.JButton();
        btnModificarServicio = new javax.swing.JButton();
        btnConsultarServicio = new javax.swing.JButton();
        btnEliminarServicio = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtIdOrden = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNombreClienteOrden = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        cmbVehiculosOrden = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cmbServiciosOrden = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        txtFechaIngreso = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtFechaEntrega = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        chkCerrada = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblOrdenes = new javax.swing.JTable();
        btnRegistrarOrden = new javax.swing.JButton();
        btnConsultarOrden = new javax.swing.JButton();
        btnModificarOrden = new javax.swing.JButton();
        btnEliminarOrden = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        itmAbrir = new javax.swing.JMenuItem();
        itmGuardar = new javax.swing.JMenuItem();
        itmSalir = new javax.swing.JMenuItem();
        itmAyuda = new javax.swing.JMenu();
        itmAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jLabel1.setText("ID de Cliente");

        jLabel2.setText("Nombre");

        jLabel3.setText("Telefono");

        jLabel4.setText("Direccion");

        txtDireccion.setColumns(20);
        txtDireccion.setRows(5);
        jScrollPane1.setViewportView(txtDireccion);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblClientes);

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnConsultar.setText("Consultar");
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelClientesLayout = new javax.swing.GroupLayout(PanelClientes);
        PanelClientes.setLayout(PanelClientesLayout);
        PanelClientesLayout.setHorizontalGroup(
            PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClientesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelClientesLayout.createSequentialGroup()
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(58, 58, 58)
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdCliente)
                            .addComponent(txtNombre)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelClientesLayout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelClientesLayout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnConsultar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRegistrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelClientesLayout.setVerticalGroup(
            PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelClientesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelClientesLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelClientesLayout.createSequentialGroup()
                                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34))
                            .addGroup(PanelClientesLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(46, 46, 46)))
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRegistrar)
                                .addComponent(btnModificar))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnConsultar)
                        .addComponent(btnEliminar)))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", PanelClientes);

        jLabel5.setText("Placa");

        jLabel6.setText("Marca");

        jLabel7.setText("Modelo");

        tblVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblVehiculos);

        jLabel8.setText("Cliente");

        cmbClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Año");

        grupoTipoVehiculo.add(rbAuto);
        rbAuto.setText("Auto");

        grupoTipoVehiculo.add(rbMoto);
        rbMoto.setText("Moto");

        lblTipoMoto.setText("Tipo de Moto");

        lblNumPuertas.setText("Numero de puertas");

        btnRegistrarVehiculo.setText("Registrar");
        btnRegistrarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarVehiculoActionPerformed(evt);
            }
        });

        btnConsultarVehiculo.setText("Consultar");
        btnConsultarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarVehiculoActionPerformed(evt);
            }
        });

        btnEliminarVehiculo.setText("Eliminar");
        btnEliminarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVehiculoActionPerformed(evt);
            }
        });

        btnModificarVehiculo.setText("Modificar");
        btnModificarVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarVehiculoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelvehiculosLayout = new javax.swing.GroupLayout(Panelvehiculos);
        Panelvehiculos.setLayout(PanelvehiculosLayout);
        PanelvehiculosLayout.setHorizontalGroup(
            PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelvehiculosLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelvehiculosLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addComponent(jLabel8))
                            .addComponent(jLabel7)
                            .addComponent(jLabel9)
                            .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)))
                        .addGap(42, 42, 42)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAño)
                            .addComponent(txtMarca)
                            .addComponent(txtPlaca)
                            .addComponent(cmbClientes, 0, 150, Short.MAX_VALUE)
                            .addComponent(txtModelo)))
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConsultarVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificarVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarVehiculo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(32, 32, 32)
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelvehiculosLayout.createSequentialGroup()
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbAuto)
                            .addComponent(rbMoto))
                        .addGap(35, 35, Short.MAX_VALUE)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoMoto)
                            .addComponent(lblNumPuertas))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTipoMoto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumPuertas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42))))
        );
        PanelvehiculosLayout.setVerticalGroup(
            PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelvehiculosLayout.createSequentialGroup()
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cmbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumPuertas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNumPuertas)
                            .addComponent(rbAuto))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbMoto)
                    .addComponent(lblTipoMoto)
                    .addComponent(txtTipoMoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(36, 36, 36)
                .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(28, 28, 28)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrarVehiculo)
                            .addComponent(btnModificarVehiculo))
                        .addGap(29, 29, 29)
                        .addGroup(PanelvehiculosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsultarVehiculo)
                            .addComponent(btnEliminarVehiculo))
                        .addGap(169, 169, 169))
                    .addGroup(PanelvehiculosLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82))))
        );

        jTabbedPane1.addTab("Vehiculos", Panelvehiculos);

        jLabel10.setText("Codigo de servicio");

        jLabel11.setText("Descripcion");

        jLabel12.setText("Costo");

        tblServicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaservicios.setViewportView(tblServicios);

        btnRegistrarServicio.setText("Registrar");
        btnRegistrarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarServicioActionPerformed(evt);
            }
        });

        btnModificarServicio.setText("Modificar");
        btnModificarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarServicioActionPerformed(evt);
            }
        });

        btnConsultarServicio.setText("Consultar");
        btnConsultarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarServicioActionPerformed(evt);
            }
        });

        btnEliminarServicio.setText("Eliminar");
        btnEliminarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarServicioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12)
                            .addComponent(btnRegistrarServicio))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigoServicio)
                                    .addComponent(txtDescripcion)
                                    .addComponent(txtCosto, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnEliminarServicio)
                                    .addComponent(btnModificarServicio)))))
                    .addComponent(btnConsultarServicio))
                .addGap(18, 18, 18)
                .addComponent(tablaservicios, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(txtCodigoServicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrarServicio)
                            .addComponent(btnModificarServicio))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsultarServicio)
                            .addComponent(btnEliminarServicio)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(tablaservicios, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Servicios", jPanel1);

        jLabel13.setText("ID de Orden");

        jLabel14.setText("Nombre Cliente");

        jLabel15.setText("Vehiculos placa");

        cmbVehiculosOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setText("Servicios ID");

        cmbServiciosOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setText("Fecha de ingreso");

        jLabel18.setText("Fecha de Entrega");

        jLabel19.setText("Cerrado(Si/No)");

        chkCerrada.setText("Cerrado");

        tblOrdenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblOrdenes);

        btnRegistrarOrden.setText("Registrar");
        btnRegistrarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarOrdenActionPerformed(evt);
            }
        });

        btnConsultarOrden.setText("Consultar");
        btnConsultarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarOrdenActionPerformed(evt);
            }
        });

        btnModificarOrden.setText("Modificar");
        btnModificarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarOrdenActionPerformed(evt);
            }
        });

        btnEliminarOrden.setText("Eliminar");
        btnEliminarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarOrdenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtIdOrden)
                            .addComponent(txtNombreClienteOrden)
                            .addComponent(cmbVehiculosOrden, 0, 150, Short.MAX_VALUE)
                            .addComponent(cmbServiciosOrden, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnConsultarOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnModificarOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarOrden, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkCerrada)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtFechaIngreso)
                                .addComponent(txtFechaEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtIdOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17)
                        .addComponent(txtFechaIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel13))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtNombreClienteOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(txtFechaEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(cmbVehiculosOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(chkCerrada))
                .addGap(34, 34, 34)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(cmbServiciosOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrarOrden)
                            .addComponent(btnModificarOrden))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnConsultarOrden)
                            .addComponent(btnEliminarOrden)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ordenes de Trabajo", jPanel2);

        mnuArchivo.setText("Archivo");

        itmAbrir.setText("Abrir Archivo");
        itmAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmAbrirActionPerformed(evt);
            }
        });
        mnuArchivo.add(itmAbrir);

        itmGuardar.setText("Grardar Archivo");
        itmGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmGuardarActionPerformed(evt);
            }
        });
        mnuArchivo.add(itmGuardar);

        itmSalir.setText("Salir");
        itmSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(itmSalir);

        jMenuBar1.add(mnuArchivo);

        itmAyuda.setText("Ayuda");

        itmAcerca.setText("Acerca de el sistema");
        itmAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmAcercaActionPerformed(evt);
            }
        });
        itmAyuda.add(itmAcerca);

        jMenuBar1.add(itmAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
btnRegistrar.addActionListener(e -> {
    try {
        String id = txtIdCliente.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            throw new DatoInvalidoException("Todos los campos deben estar llenos.");
        }

        Cliente cliente = new Cliente(id, nombre, telefono, direccion);

        boolean exito = gestorCliente.registrarCliente(cliente);

        if (!exito) {
            throw new RegistroDuplicadoException("Ya hay un usario con este ID.");
        }

        JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente.");

        txtIdCliente.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

      
    } catch (DatoInvalidoException | RegistroDuplicadoException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});


    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
      btnConsultar.addActionListener(e -> {
    try {
        cargarClientesEnTabla();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar los clientes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

    }//GEN-LAST:event_btnConsultarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        btnModificar.addActionListener(e -> {
    try {
       
        String id = txtIdCliente.getText().trim();
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            throw new DatoInvalidoException("Todos los campos deben estar llenos.");
        }

        boolean modificado = gestorCliente.modificarCliente(id, nombre, telefono, direccion);

        if (!modificado) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Cliente modificado correctamente.");

        txtIdCliente.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

 
    } catch (DatoInvalidoException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
     btnEliminar.addActionListener(e -> {
    try {
       
        String id = txtIdCliente.getText().trim();

        
        if (id.isEmpty()) {
            throw new DatoInvalidoException("Debe ingresar el ID del cliente a eliminar.");
        }

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar el cliente con ID: " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        boolean eliminado = gestorCliente.eliminarCliente(id);

        if (!eliminado) {
            JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");

        txtIdCliente.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

    } catch (DatoInvalidoException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
});

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
 int pestaña = jTabbedPane1.getSelectedIndex();

    if (pestaña == 1) { 
        cargarClientesEnCombo();
    } else if (pestaña == 3) { 
        cargarVehiculosEnComboOrden();
        cargarServiciosEnComboOrden();
    }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void btnRegistrarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarVehiculoActionPerformed
btnRegistrarVehiculo.addActionListener(e -> {
    String placa = txtPlaca.getText().trim();
    String marca = txtMarca.getText().trim();
    String modelo = txtModelo.getText().trim();
    String añoTexto = txtAño.getText().trim();
    String idCliente = (String) cmbClientes.getSelectedItem();

    if (placa.isEmpty() || marca.isEmpty() || modelo.isEmpty() || añoTexto.isEmpty() || idCliente == null || (!rbAuto.isSelected() && !rbMoto.isSelected())) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos y seleccione el tipo de vehículo.");
        return;
    }

    int año;
    try {
        año = Integer.parseInt(añoTexto);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El año debe ser un número.");
        return;
    }

    Cliente cliente = gestorCliente.buscarClientePorId(idCliente);
    if (cliente == null) {
        JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
        return;
    }

    Vehiculo vehiculo;
    if (rbAuto.isSelected()) {
        String puertasTexto = txtNumPuertas.getText().trim();
        if (puertasTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de puertas.");
            return;
        }

        int numPuertas;
        try {
            numPuertas = Integer.parseInt(puertasTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Número de puertas inválido.");
            return;
        }

        vehiculo = new Auto(placa, marca, modelo, año, numPuertas, cliente);
    } else {
        String tipoMoto = txtTipoMoto.getText().trim();
        if (tipoMoto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el tipo de moto.");
            return;
        }

        vehiculo = new Moto(placa, marca, modelo, año, tipoMoto, cliente);
    }

    boolean exito = gestorVehiculo.registrarVehiculo(vehiculo);
    if (exito) {
        JOptionPane.showMessageDialog(this, "Vehículo registrado correctamente.");
        limpiarCamposVehiculo();
    } else {
        JOptionPane.showMessageDialog(this, "Ya existe un vehículo con esa placa.");
    }
});

    }//GEN-LAST:event_btnRegistrarVehiculoActionPerformed

    private void btnConsultarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarVehiculoActionPerformed
      btnConsultarVehiculo.addActionListener(e -> {
    cargarVehiculosEnTabla();
});

    }//GEN-LAST:event_btnConsultarVehiculoActionPerformed

    private void btnModificarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarVehiculoActionPerformed
     btnModificarVehiculo.addActionListener(e -> {
    int fila = tblVehiculos.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un vehículo de la tabla para modificar.");
        return;
    }

    String placa = (String) tblVehiculos.getValueAt(fila, 0); 
    String marca = txtMarca.getText().trim();
    String modelo = txtModelo.getText().trim();
    String añoTexto = txtAño.getText().trim();

    if (marca.isEmpty() || modelo.isEmpty() || añoTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        return;
    }

    int año;
    try {
        año = Integer.parseInt(añoTexto);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El año debe ser un número.");
        return;
    }

    String infoExtra = "";
    if (rbAuto.isSelected()) {
        infoExtra = txtNumPuertas.getText().trim();
        if (infoExtra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de puertas.");
            return;
        }
    } else if (rbMoto.isSelected()) {
        infoExtra = txtTipoMoto.getText().trim();
        if (infoExtra.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el tipo de moto.");
            return;
        }
    } else {
        JOptionPane.showMessageDialog(this, "Seleccione si el vehículo es Auto o Moto.");
        return;
    }

    boolean exito = gestorVehiculo.modificarVehiculo(placa, marca, modelo, año, infoExtra);
    if (exito) {
        JOptionPane.showMessageDialog(this, "Vehículo modificado correctamente.");
        cargarVehiculosEnTabla();
        limpiarCamposVehiculo();
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró el vehículo.");
    }
});

    }//GEN-LAST:event_btnModificarVehiculoActionPerformed

    private void btnEliminarVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVehiculoActionPerformed
       btnEliminarVehiculo.addActionListener(e -> {
    int fila = tblVehiculos.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un vehículo de la tabla para eliminar.");
        return;
    }

    String placa = (String) tblVehiculos.getValueAt(fila, 0); // columna 0 = Placa

    int confirmacion = JOptionPane.showConfirmDialog(this,
        "¿Estás seguro de eliminar el vehículo con placa " + placa + "?",
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

    if (confirmacion == JOptionPane.YES_OPTION) {
        boolean exito = gestorVehiculo.eliminarVehiculo(placa);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Vehículo eliminado correctamente.");
            cargarVehiculosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el vehículo.");
        }
    }
});

    }//GEN-LAST:event_btnEliminarVehiculoActionPerformed

    private void btnRegistrarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarServicioActionPerformed
    btnRegistrarServicio.addActionListener(e -> {
    String codigo = txtCodigoServicio.getText().trim();
    String descripcion = txtDescripcion.getText().trim();
    String costoTexto = txtCosto.getText().trim();

    if (codigo.isEmpty() || descripcion.isEmpty() || costoTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        return;
    }

    double costo;
    try {
        costo = Double.parseDouble(costoTexto);
        if (costo < 0) {
            JOptionPane.showMessageDialog(this, "El costo no puede ser negativo.");
            return;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El costo debe ser un número válido.");
        return;
    }

    Servicio servicio = new Servicio(codigo, descripcion, costo);

    boolean exito = gestorServicio.registrarServicio(servicio);
    if (exito) {
        JOptionPane.showMessageDialog(this, "Servicio registrado correctamente.");
        limpiarCamposServicio();
    } else {
        JOptionPane.showMessageDialog(this, "Ya existe un servicio con ese código.");
    }
});

    }//GEN-LAST:event_btnRegistrarServicioActionPerformed

    private void btnConsultarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarServicioActionPerformed
        btnConsultarServicio.addActionListener(e -> {
    cargarServiciosEnTabla();
});
    }//GEN-LAST:event_btnConsultarServicioActionPerformed

    private void btnModificarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarServicioActionPerformed
        btnModificarServicio.addActionListener(e -> {
    int fila = tblServicios.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para modificar.");
        return;
    }

    String codigo = (String) tblServicios.getValueAt(fila, 0); 
    String nuevaDescripcion = txtDescripcion.getText().trim();
    String nuevoCostoTexto = txtCosto.getText().trim();

    if (nuevaDescripcion.isEmpty() || nuevoCostoTexto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.");
        return;
    }

    double nuevoCosto;
    try {
        nuevoCosto = Double.parseDouble(nuevoCostoTexto);
        if (nuevoCosto < 0) {
            JOptionPane.showMessageDialog(this, "El costo no puede ser negativo.");
            return;
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "El costo debe ser un número válido.");
        return;
    }

    boolean exito = gestorServicio.modificarServicio(codigo, nuevaDescripcion, nuevoCosto);
    if (exito) {
        JOptionPane.showMessageDialog(this, "Servicio modificado correctamente.");
        cargarServiciosEnTabla();
        limpiarCamposServicio();
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró el servicio.");
    }
});
    }//GEN-LAST:event_btnModificarServicioActionPerformed

    private void btnEliminarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarServicioActionPerformed
       btnEliminarServicio.addActionListener(e -> {
    int fila = tblServicios.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un servicio de la tabla para eliminar.");
        return;
    }

    String codigo = (String) tblServicios.getValueAt(fila, 0); 

    int confirmacion = JOptionPane.showConfirmDialog(this,
        "¿Está seguro de eliminar el servicio con código " + codigo + "?",
        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

    if (confirmacion == JOptionPane.YES_OPTION) {
        boolean exito = gestorServicio.eliminarServicio(codigo);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
            cargarServiciosEnTabla();
            limpiarCamposServicio();
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio.");
        }
    }
});

    }//GEN-LAST:event_btnEliminarServicioActionPerformed

    private void btnRegistrarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarOrdenActionPerformed
 String idOrden = txtIdOrden.getText().trim();
    String nombreCliente = txtNombreClienteOrden.getText().trim();
    String placaSeleccionada = (String) cmbVehiculosOrden.getSelectedItem();
    String descripcionServicio = (String) cmbServiciosOrden.getSelectedItem();
    String fechaIngreso = txtFechaIngreso.getText().trim();
    String fechaEntrega = txtFechaEntrega.getText().trim();
    boolean cerrada = chkCerrada.isSelected();

    if (idOrden.isEmpty() || nombreCliente.isEmpty() || placaSeleccionada == null ||
        descripcionServicio == null || fechaIngreso.isEmpty() || fechaEntrega.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Por favor complete todos los campos.");
        return;
    }

    Cliente cliente = gestorCliente.buscarClientePorNombre(nombreCliente);
    if (cliente == null) {
        JOptionPane.showMessageDialog(this, "Cliente no encontrado.");
        return;
    }

    Vehiculo vehiculo = gestorVehiculo.buscarVehiculoPorPlaca(placaSeleccionada);
    if (vehiculo == null) {
        JOptionPane.showMessageDialog(this, "Vehículo no encontrado.");
        return;
    }

    Servicio servicio = gestorServicio.buscarPorDescripcion(descripcionServicio);
    if (servicio == null) {
        JOptionPane.showMessageDialog(this, "Servicio no encontrado.");
        return;
    }

    OrdenTrabajo orden = new OrdenTrabajo(idOrden, cliente, vehiculo, fechaIngreso, fechaEntrega);
    orden.agregarServicio(servicio);
    if (cerrada) {
        orden.cerrarOrden();
    }
  boolean exito = gestorOrdenTrabajo.registrarOrden(orden);
if (exito) {
    JOptionPane.showMessageDialog(this, "Orden registrada correctamente.\nCosto total: $" + orden.calcularCostoTotal());
    limpiarCamposOrden();
} else {
    JOptionPane.showMessageDialog(this, "Ya existe una orden con ese ID.");
}

    }//GEN-LAST:event_btnRegistrarOrdenActionPerformed

    private void btnConsultarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarOrdenActionPerformed
     cargarOrdenesEnTabla();
    }//GEN-LAST:event_btnConsultarOrdenActionPerformed

    private void btnModificarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarOrdenActionPerformed
          String idOrden = txtIdOrden.getText().trim();
    String nuevaFecha = txtFechaEntrega.getText().trim();

    if (idOrden.isEmpty() || nuevaFecha.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el ID de la orden y la nueva fecha de entrega.");
        return;
    }

    boolean exito = gestorOrdenTrabajo.cambiarFechaEntrega(idOrden, nuevaFecha);

    if (exito) {
        JOptionPane.showMessageDialog(this, "Fecha de entrega modificada correctamente.");
        cargarOrdenesEnTabla(); 
        limpiarCamposOrden();   
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró una orden con ese ID.");
    }
    }//GEN-LAST:event_btnModificarOrdenActionPerformed

    private void btnEliminarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarOrdenActionPerformed
      String id = txtIdOrden.getText().trim();

    if (id.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el ID de la orden que desea eliminar.");
        return;
    }

    int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar esta orden?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirmacion != JOptionPane.YES_OPTION) {
        return;
    }

    boolean eliminado = gestorOrdenTrabajo.eliminarOrden(id);
    if (eliminado) {
        JOptionPane.showMessageDialog(this, "Orden eliminada correctamente.");
        cargarOrdenesEnTabla(); 
        limpiarCamposOrden();   
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró ninguna orden con ese ID.");
    }
    }//GEN-LAST:event_btnEliminarOrdenActionPerformed

    private void itmAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmAbrirActionPerformed
     JFileChooser fileChooser = new JFileChooser();
int resultado = fileChooser.showOpenDialog(this);

if (resultado == JFileChooser.APPROVE_OPTION) {
    File archivoSeleccionado = fileChooser.getSelectedFile();
    JOptionPane.showMessageDialog(this, "Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
}

    }//GEN-LAST:event_itmAbrirActionPerformed

    private void itmGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmGuardarActionPerformed
         JFileChooser fileChooser = new JFileChooser();
    int resultado = fileChooser.showSaveDialog(this);

    if (resultado == JFileChooser.APPROVE_OPTION) {
        File archivoGuardar = fileChooser.getSelectedFile();
        JOptionPane.showMessageDialog(this, "Archivo guardado como: " + archivoGuardar.getAbsolutePath());
    }
    }//GEN-LAST:event_itmGuardarActionPerformed

    private void itmSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSalirActionPerformed
   System.exit(0);
    }//GEN-LAST:event_itmSalirActionPerformed

    private void itmAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmAcercaActionPerformed
     JDialog acerca = new JDialog(this, "Acerca de", true);
    acerca.setSize(300, 150);
    acerca.setLocationRelativeTo(this);
    acerca.setLayout(new BorderLayout());

    JLabel lblInfo = new JLabel("<html><center>Proyecto<br>Uisil<br>Autores:<br>Steven leonardo<br>Glenn Huertas<br>Versión 1.0</center></html>", SwingConstants.CENTER);
    acerca.add(lblInfo, BorderLayout.CENTER);
    acerca.setVisible(true);
    }//GEN-LAST:event_itmAcercaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelClientes;
    private javax.swing.JPanel Panelvehiculos;
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnConsultarOrden;
    private javax.swing.JButton btnConsultarServicio;
    private javax.swing.JButton btnConsultarVehiculo;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarOrden;
    private javax.swing.JButton btnEliminarServicio;
    private javax.swing.JButton btnEliminarVehiculo;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnModificarOrden;
    private javax.swing.JButton btnModificarServicio;
    private javax.swing.JButton btnModificarVehiculo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JButton btnRegistrarOrden;
    private javax.swing.JButton btnRegistrarServicio;
    private javax.swing.JButton btnRegistrarVehiculo;
    private javax.swing.JCheckBox chkCerrada;
    private javax.swing.JComboBox<String> cmbClientes;
    private javax.swing.JComboBox<String> cmbServiciosOrden;
    private javax.swing.JComboBox<String> cmbVehiculosOrden;
    private javax.swing.ButtonGroup grupoTipoVehiculo;
    private javax.swing.JMenuItem itmAbrir;
    private javax.swing.JMenuItem itmAcerca;
    private javax.swing.JMenu itmAyuda;
    private javax.swing.JMenuItem itmGuardar;
    private javax.swing.JMenuItem itmSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblNumPuertas;
    private javax.swing.JLabel lblTipoMoto;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JRadioButton rbAuto;
    private javax.swing.JRadioButton rbMoto;
    private javax.swing.JScrollPane tablaservicios;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblOrdenes;
    private javax.swing.JTable tblServicios;
    private javax.swing.JTable tblVehiculos;
    private javax.swing.JTextField txtAño;
    private javax.swing.JTextField txtCodigoServicio;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextArea txtDireccion;
    private javax.swing.JTextField txtFechaEntrega;
    private javax.swing.JTextField txtFechaIngreso;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdOrden;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreClienteOrden;
    private javax.swing.JTextField txtNumPuertas;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoMoto;
    // End of variables declaration//GEN-END:variables
}
