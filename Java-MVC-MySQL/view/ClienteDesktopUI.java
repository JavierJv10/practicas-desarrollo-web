package view;

import model.domain.Cliente;
import model.service.ClienteService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * ClienteDesktopUI — Interfaz gráfica de escritorio para la gestión de clientes.
 *
 * <p>Equivalente gráfico de {@link ClienteConsoleUI}: ofrece las mismas operaciones
 * (listar, buscar, alta, modificación y baja) pero mediante componentes Swing.</p>
 *
 * <p>Características principales:
 * <ul>
 *   <li>Patrón <b>Singleton</b>: una única instancia de ventana por ejecución.</li>
 *   <li>Ventana principal con tabla de solo lectura, filtros y botón de alta.</li>
 *   <li>Botones de acción incrustados en cada fila de la tabla (modificar / eliminar).</li>
 *   <li>Formulario modal reutilizable para alta, modificación y consulta previa a baja.</li>
 *   <li>Paleta oscura con colores de acento diferenciados por tipo de operación.</li>
 * </ul>
 * </p>
 *
 * <p><b>Dependencias:</b> {@link ClienteService} para la lógica de negocio y
 * {@link Cliente} como entidad de dominio.</p>
 *
 * @see ClienteConsoleUI
 */
public class ClienteDesktopUI extends JFrame {

    // ══════════════════════════════════════════════════════════════════════════
    // SINGLETON
    // ══════════════════════════════════════════════════════════════════════════

    /** Única instancia de la ventana (patrón Singleton). */
    private static ClienteDesktopUI instance = null;

    /** Servicio de negocio que gestiona las operaciones CRUD sobre clientes. */
    private final ClienteService clienteService;


    // ══════════════════════════════════════════════════════════════════════════
    // PALETA DE COLORES
    // Todos los colores se definen aquí como constantes para facilitar el
    // mantenimiento y garantizar coherencia visual en toda la interfaz.
    // ══════════════════════════════════════════════════════════════════════════

    /** Fondo general de la ventana (tono muy oscuro azulado). */
    private static final Color COLOR_BG           = new Color(15,  17,  26);

    /** Fondo de paneles secundarios (ligeramente más claro que el fondo). */
    private static final Color COLOR_SURFACE      = new Color(22,  26,  42);

    /** Fondo alternativo para elementos elevados (tarjetas, cabeceras de tabla). */
    private static final Color COLOR_SURFACE_ALT  = new Color(30,  35,  55);

    /** Color para separadores, bordes y líneas divisorias. */
    private static final Color COLOR_BORDER       = new Color(45,  52,  80);

    /** Color de acento principal (azul eléctrico); usado en botones de búsqueda y selección. */
    private static final Color COLOR_ACCENT       = new Color(82,  130, 255);

    /** Verde para operaciones de éxito y confirmación de alta. */
    private static final Color COLOR_SUCCESS      = new Color(52,  199, 89);

    /** Amarillo/ámbar para advertencias y confirmación de baja. */
    private static final Color COLOR_WARNING      = new Color(255, 184, 0);

    /** Rojo para operaciones destructivas (botón eliminar, errores). */
    private static final Color COLOR_DANGER       = new Color(255, 59,  48);

    /** Color del texto principal (blanco suave). */
    private static final Color COLOR_TEXT         = new Color(220, 225, 245);

    /** Color del texto secundario o desactivado (gris azulado). */
    private static final Color COLOR_TEXT_MUTED   = new Color(120, 130, 165);

    /** Fondo de filas impares de la tabla. */
    private static final Color COLOR_ROW_ODD      = new Color(22,  26,  42);

    /** Fondo de filas pares de la tabla (efecto zebra). */
    private static final Color COLOR_ROW_EVEN     = new Color(27,  32,  50);

    /** Color de fila seleccionada en la tabla. */
    private static final Color COLOR_ROW_SEL      = new Color(50,  75,  140);


    // ══════════════════════════════════════════════════════════════════════════
    // FUENTES
    // Se definen aquí para reutilizarlas en todos los componentes y mantener
    // una tipografía uniforme a lo largo de la interfaz.
    // ══════════════════════════════════════════════════════════════════════════

    /** Fuente grande para títulos de sección y encabezado principal. */
    private static final Font FONT_TITLE  = new Font("Segoe UI", Font.BOLD,  20);

    /** Fuente estándar para etiquetas, celdas y texto general. */
    private static final Font FONT_LABEL  = new Font("Segoe UI", Font.PLAIN, 13);

    /** Fuente negrita para encabezados de columna y textos destacados. */
    private static final Font FONT_BOLD   = new Font("Segoe UI", Font.BOLD,  13);

    /** Fuente pequeña para textos auxiliares y pie de página. */
    private static final Font FONT_SMALL  = new Font("Segoe UI", Font.PLAIN, 11);

    /** Fuente monoespaciada para datos numéricos (IDs, importes). */
    private static final Font FONT_MONO   = new Font("Consolas", Font.PLAIN, 12);

    /** Fuente para el texto de los botones. */
    private static final Font FONT_BTN    = new Font("Segoe UI", Font.BOLD,  12);


    // ══════════════════════════════════════════════════════════════════════════
    // ÍNDICES DE COLUMNAS DE LA TABLA
    // Constantes que identifican cada columna por su posición ordinal.
    // Usarlas evita "números mágicos" dispersos por el código.
    // ══════════════════════════════════════════════════════════════════════════

    /** Cabeceras visibles de la tabla, en el mismo orden que los índices COL_*. */
    private static final String[] COLUMNAS = {
        "ID", "Razón Social", "Nombre Comercial", "Límite Crédito", "Modificar", "Eliminar"
    };

    private static final int COL_ID        = 0; // Identificador único del cliente
    private static final int COL_RAZON     = 1; // Razón social
    private static final int COL_COMERCIAL = 2; // Nombre comercial
    private static final int COL_LIMITE    = 3; // Límite de crédito (double)
    private static final int COL_MOD       = 4; // Botón de acción "Modificar"
    private static final int COL_DEL       = 5; // Botón de acción "Eliminar"


    // ══════════════════════════════════════════════════════════════════════════
    // COMPONENTES SWING PRINCIPALES
    // Se declaran como atributos de instancia para poder acceder a ellos desde
    // los distintos métodos de la clase (actualización de datos, eventos, etc.).
    // ══════════════════════════════════════════════════════════════════════════

    /** Tabla que muestra el listado de clientes en la ventana principal. */
    private JTable tabla;

    /**
     * Modelo de datos de la tabla. Permite añadir, eliminar y actualizar filas
     * en tiempo de ejecución sin recrear la tabla.
     */
    private DefaultTableModel modeloTabla;

    /** Campo de texto donde el usuario escribe el criterio de búsqueda. */
    private JTextField campoBusqueda;

    /**
     * Desplegable que permite elegir el tipo de filtro:
     * "Todos", "Por ID" o "Por Razón Social".
     */
    private JComboBox<String> comboFiltro;

    /** Etiqueta del pie de página que muestra el número de clientes listados. */
    private JLabel labelTotal;


    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCTOR PRIVADO (SINGLETON)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Constructor privado. Solo puede invocarse desde {@link #getInstance}.
     *
     * @param clienteService servicio de negocio inyectado por dependencia.
     */
    private ClienteDesktopUI(ClienteService clienteService) {
        this.clienteService = clienteService;
        configurarVentana(); // Propiedades básicas del JFrame
        construirUI();        // Creación y disposición de todos los paneles
        cargarClientes(clienteService.findAll()); // Carga inicial de datos
    }

    /**
     * Devuelve la única instancia de la ventana, creándola si aún no existe.
     *
     * @param clienteService servicio de negocio; solo se usa en la primera llamada.
     * @return instancia única de {@link ClienteDesktopUI}.
     */
    public static ClienteDesktopUI getInstance(ClienteService clienteService) {
        if (instance == null) {
            instance = new ClienteDesktopUI(clienteService);
        }
        return instance;
    }


    // ══════════════════════════════════════════════════════════════════════════
    // MÉTODO PÚBLICO DE ARRANQUE
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Centra la ventana en pantalla y la hace visible.
     * Equivalente a {@code ClienteConsoleUI.iniciar()}.
     */
    public void iniciar() {
        setLocationRelativeTo(null); // Centrar respecto a la pantalla
        setVisible(true);
    }


    // ══════════════════════════════════════════════════════════════════════════
    // CONFIGURACIÓN GENERAL DE LA VENTANA
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Establece las propiedades básicas del {@link JFrame}:
     * título, tamaño mínimo/preferido, color de fondo y layout raíz.
     */
    private void configurarVentana() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 580));
        setPreferredSize(new Dimension(1100, 680));
        getContentPane().setBackground(COLOR_BG);

        // BorderLayout divide la ventana en tres zonas:
        //   NORTH  → encabezado con título, filtros y botón de alta
        //   CENTER → tabla de clientes con scroll
        //   SOUTH  → pie de página con contador de registros
        setLayout(new BorderLayout(0, 0));
    }


    // ══════════════════════════════════════════════════════════════════════════
    // CONSTRUCCIÓN DE LA INTERFAZ
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Ensambla los tres paneles principales de la ventana y llama a {@link #pack()}
     * para ajustar el tamaño al contenido preferido de cada componente.
     */
    private void construirUI() {
        add(crearPanelEncabezado(), BorderLayout.NORTH);
        add(crearPanelCentral(),    BorderLayout.CENTER);
        add(crearPanelPie(),        BorderLayout.SOUTH);
        pack();
    }


    // ══════════════════════════════════════════════════════════════════════════
    // PANEL ENCABEZADO (título + filtros + botón de alta)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Construye el panel superior de la ventana.
     * <ul>
     *   <li><b>Izquierda:</b> icono decorativo y título de la aplicación.</li>
     *   <li><b>Derecha:</b> combo de tipo de filtro, campo de texto,
     *       botón Buscar y botón Dar de alta.</li>
     * </ul>
     *
     * @return panel configurado listo para añadir al {@code BorderLayout.NORTH}.
     */
    private JPanel crearPanelEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_SURFACE);
        // Línea divisoria inferior entre el encabezado y la tabla
        panel.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        // ── Bloque izquierdo: icono + título ──────────────────────────────
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 14));
        panelTitulo.setOpaque(false);

        JLabel iconoTitulo = new JLabel("◈"); // Símbolo decorativo
        iconoTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        iconoTitulo.setForeground(COLOR_ACCENT);

        JLabel lblTitulo = new JLabel("GESTIÓN DE CLIENTES");
        lblTitulo.setFont(FONT_TITLE);
        lblTitulo.setForeground(COLOR_TEXT);

        panelTitulo.add(iconoTitulo);
        panelTitulo.add(lblTitulo);

        // ── Bloque derecho: controles de búsqueda + botón de alta ─────────
        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 11));
        panelAcciones.setOpaque(false);

        // Combo para seleccionar el modo de filtrado
        comboFiltro = new JComboBox<>(new String[]{"Todos", "Por ID", "Por Razón Social"});
        estilizarCombo(comboFiltro);
        comboFiltro.setPreferredSize(new Dimension(170, 33));

        // Campo de texto donde se introduce el valor a buscar
        campoBusqueda = new JTextField();
        estilizarTextField(campoBusqueda);
        campoBusqueda.setPreferredSize(new Dimension(200, 33));
        campoBusqueda.setEnabled(false); // Desactivado en modo "Todos" (no necesita criterio)
        campoBusqueda.setToolTipText("Se mostrarán todos los clientes");

        // Botón que dispara la consulta con el criterio actual
        JButton btnBuscar = crearBoton("Buscar", COLOR_ACCENT, "🔍");
        btnBuscar.addActionListener(e -> ejecutarBusqueda());

        // También se puede lanzar la búsqueda pulsando Enter desde el campo de texto
        campoBusqueda.addActionListener(e -> ejecutarBusqueda());

        // Listener del combo: activa/desactiva el campo de texto según el modo elegido
        // y lanza una recarga automática al seleccionar "Todos"
        comboFiltro.addActionListener(e -> {
            int idx = comboFiltro.getSelectedIndex();
            boolean necesitaCriterio = (idx != 0); // Índice 0 = "Todos" → sin campo de texto
            campoBusqueda.setEnabled(necesitaCriterio);
            campoBusqueda.setToolTipText(
                idx == 0 ? "Se mostrarán todos los clientes"
              : idx == 1 ? "Introduce el ID del cliente"
              :             "Introduce la razón social o un fragmento"
            );
            if (!necesitaCriterio) {
                campoBusqueda.setText(""); // Limpiar cualquier texto residual
                ejecutarBusqueda();        // Recargar el listado completo inmediatamente
            }
        });

        // Botón de alta: abre el formulario en blanco para crear un nuevo cliente
        JButton btnAlta = crearBoton("  Dar de alta  ", COLOR_SUCCESS, "＋");
        btnAlta.addActionListener(e -> abrirFormularioAlta());

        panelAcciones.add(comboFiltro);
        panelAcciones.add(campoBusqueda);
        panelAcciones.add(btnBuscar);
        panelAcciones.add(Box.createHorizontalStrut(8)); // Separador visual antes del botón de alta
        panelAcciones.add(btnAlta);

        panel.add(panelTitulo,   BorderLayout.WEST);
        panel.add(panelAcciones, BorderLayout.EAST);
        return panel;
    }


    // ══════════════════════════════════════════════════════════════════════════
    // PANEL CENTRAL (tabla de clientes)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Construye el panel central con la tabla de clientes.
     *
     * <p>La tabla es de <b>solo lectura</b>: ninguna celda puede editarse
     * directamente. La edición se canaliza siempre a través del formulario modal.
     * Las dos últimas columnas contienen botones de acción por fila (Modificar
     * y Eliminar) que se renderizan con {@link RendererBoton} y cuyo clic se
     * intercepta mediante un {@link MouseListener}.</p>
     *
     * @return panel con la tabla envuelta en un {@link JScrollPane}.
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BG);
        panel.setBorder(new EmptyBorder(14, 16, 6, 16));

        // ── Modelo de tabla ────────────────────────────────────────────────
        // Se sobreescribe isCellEditable() para bloquear la edición directa en
        // todas las celdas, incluidas las de las columnas de botones.
        modeloTabla = new DefaultTableModel(COLUMNAS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ninguna celda es editable directamente
            }

            /**
             * Informa a JTable del tipo de dato de cada columna.
             * Esto es necesario para que los renderers personalizados de Integer
             * y Double se apliquen correctamente sin conflictos con el renderer
             * por defecto de Object.
             */
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == COL_ID)     return Integer.class;
                if (columnIndex == COL_LIMITE)  return Double.class;
                return String.class;
            }
        };

        // ── Configuración visual de JTable ─────────────────────────────────
        tabla = new JTable(modeloTabla);
        tabla.setRowHeight(38);                            // Filas generosas para legibilidad
        tabla.setShowHorizontalLines(true);                // Líneas horizontales entre filas
        tabla.setShowVerticalLines(false);                 // Sin líneas verticales (más limpio)
        tabla.setGridColor(COLOR_BORDER);
        tabla.setBackground(COLOR_ROW_ODD);
        tabla.setForeground(COLOR_TEXT);
        tabla.setFont(FONT_LABEL);
        tabla.setSelectionBackground(COLOR_ROW_SEL);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setFocusable(false);                         // Sin travesía de foco por teclado
        tabla.getTableHeader().setReorderingAllowed(false);// Columnas en orden fijo

        // ── Estilo del encabezado de columnas ──────────────────────────────
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(COLOR_SURFACE_ALT);
        header.setForeground(COLOR_TEXT_MUTED);
        header.setFont(FONT_BOLD);
        header.setPreferredSize(new Dimension(0, 36));
        header.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

        // ── Anchos preferidos de columna ───────────────────────────────────
        // Los valores son orientativos; el usuario puede redimensionar columnas.
        int[] anchos = {55, 260, 230, 120, 100, 100};
        for (int i = 0; i < anchos.length; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
        }

        // ── Renderers personalizados ───────────────────────────────────────
        // Aplican el efecto zebra y la paleta de colores a todas las celdas.
        tabla.setDefaultRenderer(Object.class,  new RendererFilaAlterna());
        tabla.setDefaultRenderer(Integer.class, new RendererFilaAlterna(SwingConstants.CENTER));
        tabla.setDefaultRenderer(Double.class,  new RendererImporte());

        // Renderers de botón para las columnas de acción
        tabla.getColumnModel().getColumn(COL_MOD).setCellRenderer(
            new RendererBoton("✏  Modificar", COLOR_ACCENT));
        tabla.getColumnModel().getColumn(COL_DEL).setCellRenderer(
            new RendererBoton("✕  Eliminar",  COLOR_DANGER));

        // ── Listener de clics en la tabla ──────────────────────────────────
        // Detecta si el clic fue en COL_MOD o COL_DEL y abre el formulario
        // correspondiente con el ID del cliente de esa fila.
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila    = tabla.rowAtPoint(e.getPoint());
                int columna = tabla.columnAtPoint(e.getPoint());
                if (fila < 0) return; // Clic fuera de cualquier fila, ignorar

                if (columna == COL_MOD) {
                    int id = (int) modeloTabla.getValueAt(fila, COL_ID);
                    abrirFormularioModificacion(id);
                } else if (columna == COL_DEL) {
                    int id = (int) modeloTabla.getValueAt(fila, COL_ID);
                    abrirFormularioBaja(id);
                }
            }
        });

        // Cambiar el cursor a "manita" al pasar sobre las columnas de botón,
        // para indicar al usuario que son elementos clicables.
        tabla.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = tabla.columnAtPoint(e.getPoint());
                boolean sobreBoton = (col == COL_MOD || col == COL_DEL);
                tabla.setCursor(sobreBoton
                    ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                    : Cursor.getDefaultCursor());
            }
        });

        // ── ScrollPane envolvente ──────────────────────────────────────────
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(COLOR_BG);
        scroll.getViewport().setBackground(COLOR_BG);
        scroll.setBorder(new LineBorder(COLOR_BORDER, 1));
        scroll.getVerticalScrollBar().setUI(new ScrollBarUI()); // Barra personalizada

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }


    // ══════════════════════════════════════════════════════════════════════════
    // PANEL PIE DE PÁGINA (contador de registros + texto de ayuda)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Construye el panel inferior de la ventana.
     * Muestra el número de clientes actualmente visibles en la tabla
     * y un mensaje de ayuda sobre las acciones disponibles.
     *
     * @return panel configurado listo para añadir al {@code BorderLayout.SOUTH}.
     */
    private JPanel crearPanelPie() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_SURFACE);
        panel.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 0, 0, COLOR_BORDER), // Línea separadora superior
            new EmptyBorder(7, 16, 7, 16)               // Margen interior
        ));

        // El texto de este label se actualiza en cargarClientes()
        labelTotal = new JLabel("0 clientes");
        labelTotal.setFont(FONT_SMALL);
        labelTotal.setForeground(COLOR_TEXT_MUTED);

        JLabel lblAyuda = new JLabel("Haz clic en ✏ para modificar o en ✕ para eliminar un cliente");
        lblAyuda.setFont(FONT_SMALL);
        lblAyuda.setForeground(COLOR_TEXT_MUTED);

        panel.add(labelTotal, BorderLayout.WEST);
        panel.add(lblAyuda,   BorderLayout.EAST);
        return panel;
    }


    // ══════════════════════════════════════════════════════════════════════════
    // CARGA Y ACTUALIZACIÓN DE DATOS EN LA TABLA
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Vacía la tabla y la rellena con la lista de clientes recibida.
     * También actualiza el contador del pie de página.
     *
     * <p>Se llama tanto en la carga inicial como tras cada operación CRUD
     * y cada búsqueda, para mantener la vista sincronizada con el repositorio.</p>
     *
     * @param clientes lista de clientes a mostrar; puede estar vacía pero no {@code null}.
     */
    private void cargarClientes(ArrayList<Cliente> clientes) {
        modeloTabla.setRowCount(0); // Eliminar todas las filas existentes antes de recargar

        for (Cliente c : clientes) {
            // Cada fila se compone de: datos del cliente + texto fijo para los botones.
            // Los textos de botón son meramente informativos; el renderer los ignora
            // y dibuja su propio JButton estilizado.
            modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getRazonSocial(),
                c.getNombreComercial(),
                c.getLimiteCredito(),
                "✏  Modificar",
                "✕  Eliminar"
            });
        }

        // Actualizar el contador con forma singular/plural correcta
        int n = clientes.size();
        labelTotal.setText(n + (n == 1 ? " cliente" : " clientes"));
    }


    // ══════════════════════════════════════════════════════════════════════════
    // LÓGICA DE BÚSQUEDA / FILTRADO
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Lee el tipo de filtro y el criterio de búsqueda e invoca el método
     * correspondiente del servicio para recargar la tabla.
     *
     * <p>Equivalente a los métodos {@code listarTodos()}, {@code buscarPorId()}
     * y {@code buscarPorRazonSocial()} de {@link ClienteConsoleUI}.</p>
     */
    private void ejecutarBusqueda() {
        int    tipoFiltro = comboFiltro.getSelectedIndex();
        String criterio   = campoBusqueda.getText().trim();

        switch (tipoFiltro) {
            case 0 -> // "Todos": listar sin ningún filtro
                cargarClientes(clienteService.findAll());

            case 1 -> { // "Por ID": parsear entero y buscar por clave primaria
                try {
                    int id = Integer.parseInt(criterio);
                    ArrayList<Cliente> resultado = new ArrayList<>();
                    // findById devuelve Optional; si existe, lo añadimos a la lista
                    clienteService.findById(id).ifPresent(resultado::add);
                    cargarClientes(resultado);
                    if (resultado.isEmpty()) {
                        mostrarAviso("No se encontró ningún cliente con ID " + id + ".");
                    }
                } catch (NumberFormatException ex) {
                    // El usuario introdujo texto no numérico en el campo de ID
                    mostrarError("El ID debe ser un número entero válido.");
                }
            }

            case 2 -> { // "Por Razón Social": búsqueda parcial por texto
                if (criterio.isEmpty()) {
                    mostrarAviso("Introduce al menos un carácter para buscar por razón social.");
                    return;
                }
                ArrayList<Cliente> resultados = clienteService.findByRazonSocial(criterio);
                cargarClientes(resultados);
                if (resultados.isEmpty()) {
                    mostrarAviso("No se encontraron clientes con esa razón social.");
                }
            }
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // APERTURA DE FORMULARIOS MODALES
    //
    // Los tres métodos siguientes siguen el mismo flujo:
    //   1. Recuperar (o crear) el objeto Cliente de dominio.
    //   2. Abrir el diálogo modal con el modo adecuado.
    //   3. Si el usuario confirmó, ejecutar la operación en el servicio.
    //   4. Recargar la tabla para reflejar el cambio.
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Abre el formulario en modo <b>Alta</b> con todos los campos vacíos.
     * Si el usuario confirma, invoca {@link ClienteService#create(Cliente)}.
     * Equivalente a {@code crearCliente()} en {@link ClienteConsoleUI}.
     */
    private void abrirFormularioAlta() {
        // Se pasa un Cliente vacío como plantilla; el diálogo lo rellenará con los datos del usuario
        DialogoCliente dialogo = new DialogoCliente(this, ModoFormulario.ALTA, new Cliente());
        dialogo.setVisible(true); // Bloquea hasta que el usuario cierra el diálogo (modal)

        if (dialogo.isConfirmado()) {
            try {
                clienteService.create(dialogo.getClienteResultante());
                mostrarExito("Cliente creado correctamente.");
                ejecutarBusqueda(); // Refrescar la tabla con el nuevo registro
            } catch (IllegalArgumentException ex) {
                mostrarError("Error de validación: " + ex.getMessage());
            }
        }
    }

    /**
     * Recupera el cliente con el {@code id} dado y abre el formulario en modo
     * <b>Modificación</b> con sus datos precargados.
     * Si el usuario confirma, invoca {@link ClienteService#update(Cliente)}.
     * Equivalente a {@code actualizarCliente()} en {@link ClienteConsoleUI}.
     *
     * @param id identificador del cliente a modificar.
     */
    private void abrirFormularioModificacion(int id) {
        Optional<Cliente> encontrado = clienteService.findById(id);
        if (encontrado.isEmpty()) {
            mostrarAviso("No se encontró ningún cliente con ID " + id + ".");
            return;
        }

        DialogoCliente dialogo = new DialogoCliente(this, ModoFormulario.MODIFICACION, encontrado.get());
        dialogo.setVisible(true);

        if (dialogo.isConfirmado()) {
            try {
                Cliente actualizado = dialogo.getClienteResultante();
                actualizado.setId(id); // El ID no se muestra en el formulario; lo restauramos manualmente
                clienteService.update(actualizado);
                mostrarExito("Cliente actualizado correctamente.");
                ejecutarBusqueda(); // Refrescar la tabla con los datos modificados
            } catch (IllegalArgumentException ex) {
                mostrarError("Error de validación: " + ex.getMessage());
            }
        }
    }

    /**
     * Recupera el cliente con el {@code id} dado y abre el formulario en modo
     * <b>Baja</b> con sus datos visibles pero no editables.
     * Si el usuario confirma, invoca {@link ClienteService#deleteById(int)}.
     * Equivalente a {@code eliminarCliente()} en {@link ClienteConsoleUI}.
     *
     * @param id identificador del cliente a eliminar.
     */
    private void abrirFormularioBaja(int id) {
        Optional<Cliente> encontrado = clienteService.findById(id);
        if (encontrado.isEmpty()) {
            mostrarAviso("No se encontró ningún cliente con ID " + id + ".");
            return;
        }

        DialogoCliente dialogo = new DialogoCliente(this, ModoFormulario.BAJA, encontrado.get());
        dialogo.setVisible(true);

        if (dialogo.isConfirmado()) {
            clienteService.deleteById(id);
            mostrarExito("Cliente eliminado correctamente.");
            ejecutarBusqueda(); // Refrescar la tabla tras el borrado
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // DIÁLOGOS DE NOTIFICACIÓN
    // Encapsulan JOptionPane para unificar el estilo de los mensajes emergentes.
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Muestra un diálogo de información (operación exitosa).
     * @param mensaje texto a mostrar.
     */
    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación correcta",
            JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra un diálogo de advertencia (resultado vacío, validación leve).
     * @param mensaje texto a mostrar.
     */
    private void mostrarAviso(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Aviso",
            JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra un diálogo de error (fallo de validación o excepción del servicio).
     * @param mensaje texto a mostrar.
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error",
            JOptionPane.ERROR_MESSAGE);
    }


    // ══════════════════════════════════════════════════════════════════════════
    // HELPERS DE ESTILIZACIÓN DE COMPONENTES
    // Aplican la paleta y la tipografía definidas a los controles estándar de
    // Swing, garantizando un aspecto uniforme en toda la interfaz.
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Aplica el estilo oscuro de la interfaz a un {@link JTextField}.
     * @param campo campo de texto a estilizar.
     */
    private void estilizarTextField(JTextField campo) {
        campo.setBackground(COLOR_SURFACE_ALT);
        campo.setForeground(COLOR_TEXT);
        campo.setCaretColor(COLOR_ACCENT); // Cursor de inserción en color de acento
        campo.setFont(FONT_LABEL);
        campo.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDER, 1),   // Borde exterior
            new EmptyBorder(4, 8, 4, 8)        // Padding interior para no pegar el texto al borde
        ));
    }

    /**
     * Aplica el estilo oscuro de la interfaz a un {@link JComboBox}.
     * También personaliza el renderer de cada ítem del desplegable.
     * @param combo desplegable a estilizar.
     */
    private void estilizarCombo(JComboBox<String> combo) {
        combo.setBackground(COLOR_SURFACE_ALT);
        combo.setForeground(COLOR_TEXT);
        combo.setFont(FONT_LABEL);
        combo.setBorder(new LineBorder(COLOR_BORDER, 1));

        // Renderer personalizado para los ítems del desplegable cuando está abierto
        combo.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            JLabel lbl = new JLabel(value);
            lbl.setFont(FONT_LABEL);
            lbl.setBorder(new EmptyBorder(4, 10, 4, 10));
            lbl.setOpaque(true);
            lbl.setForeground(COLOR_TEXT);
            lbl.setBackground(isSelected ? COLOR_ROW_SEL : COLOR_SURFACE_ALT);
            return lbl;
        });
    }

    /**
     * Crea y devuelve un {@link JButton} estilizado con el color y símbolo indicados.
     * Incluye un efecto hover que aclara el color de fondo al pasar el ratón.
     *
     * @param texto     texto visible en el botón.
     * @param colorBase color de fondo en estado normal.
     * @param emoji     carácter o símbolo que se antepone al texto.
     * @return botón configurado y listo para usar.
     */
    private JButton crearBoton(String texto, Color colorBase, String emoji) {
        JButton btn = new JButton(emoji + " " + texto);
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setBackground(colorBase);
        btn.setFocusPainted(false);   // Ocultar el rectángulo de foco al tabular
        btn.setBorderPainted(false);  // Sin borde decorativo de Look&Feel
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(6, 14, 6, 14)); // Padding interno del botón

        // Efecto hover: el color se aclara ligeramente al entrar el ratón
        Color colorHover = colorBase.brighter();
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(colorHover); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(colorBase);  }
        });

        return btn;
    }


    // ══════════════════════════════════════════════════════════════════════════
    // ENUMERADO: MODO DEL FORMULARIO
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Define el propósito con el que se abre {@link DialogoCliente}.
     * Determina el título, la editabilidad de los campos, y el texto, icono
     * y color del botón de confirmación.
     *
     * <ul>
     *   <li>{@code ALTA}         — campos vacíos y editables, botón verde "＋ Crear cliente".</li>
     *   <li>{@code MODIFICACION} — campos precargados y editables, botón azul "✏ Guardar cambios".</li>
     *   <li>{@code BAJA}         — campos precargados y de solo lectura, botón rojo "✕ Eliminar cliente".</li>
     * </ul>
     */
    private enum ModoFormulario {
        ALTA, MODIFICACION, BAJA
    }


    // ══════════════════════════════════════════════════════════════════════════
    // DIÁLOGO REUTILIZABLE DE CLIENTE (Alta / Modificación / Baja)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Ventana modal compartida por las tres operaciones que requieren mostrar
     * o editar datos de un cliente: alta, modificación y baja.
     *
     * <p>El diálogo adapta automáticamente su comportamiento según el
     * {@link ModoFormulario} recibido:
     * <ul>
     *   <li><b>Título</b> de la ventana.</li>
     *   <li><b>Editabilidad</b> de los campos (desactivados en modo Baja).</li>
     *   <li><b>Texto, icono y color</b> del botón de confirmación.</li>
     *   <li><b>Aviso de irreversibilidad</b> visible solo en modo Baja.</li>
     * </ul>
     * </p>
     *
     * <p>Tras cerrarse, el llamador consulta {@link #isConfirmado()} y, si es
     * {@code true}, obtiene el cliente con los datos introducidos mediante
     * {@link #getClienteResultante()}.</p>
     */
    private class DialogoCliente extends JDialog {

        /**
         * {@code true} si el usuario pulsó el botón de confirmación;
         * {@code false} si canceló o cerró la ventana.
         */
        private boolean confirmado = false;

        /**
         * Objeto {@link Cliente} devuelto al llamador cuando la operación se confirma.
         * En modo Alta es un objeto nuevo rellenado por el formulario.
         * En Modificación y Baja es el mismo objeto recibido, actualizado o sin cambios.
         */
        private Cliente clienteResultante;

        // ── Campos del formulario ──────────────────────────────────────────
        private JTextField campoRazonSocial;
        private JTextField campoNombreComercial;
        private JTextField campoLimiteCredito;

        /**
         * Construye el diálogo con el modo y los datos del cliente indicados.
         *
         * @param parent  ventana padre; necesaria para la modalidad y el centrado.
         * @param modo    {@link ModoFormulario} que determina el comportamiento visual.
         * @param cliente datos a precargar; en modo Alta puede ser un {@link Cliente} vacío.
         */
        DialogoCliente(JFrame parent, ModoFormulario modo, Cliente cliente) {
            super(parent, true); // true = modal: bloquea la ventana padre hasta que se cierre

            this.clienteResultante = cliente;

            // Título adaptado al modo de operación
            String titulo = switch (modo) {
                case ALTA         -> "Alta de cliente";
                case MODIFICACION -> "Modificar cliente #" + cliente.getId();
                case BAJA         -> "Eliminar cliente #" + cliente.getId();
            };
            setTitle(titulo);
            setResizable(false);
            setSize(440, 340);
            setLocationRelativeTo(parent); // Centrar respecto a la ventana padre

            // Ensamblar los tres bloques del diálogo
            JPanel contenido = new JPanel(new BorderLayout(0, 0));
            contenido.setBackground(COLOR_SURFACE);
            contenido.add(crearCabecera(titulo, modo), BorderLayout.NORTH);
            contenido.add(crearCuerpo(cliente, modo),  BorderLayout.CENTER);
            contenido.add(crearPieBotones(modo),       BorderLayout.SOUTH);

            setContentPane(contenido);
        }

        /**
         * Construye la cabecera del diálogo con un icono de color y el título.
         * El color del icono varía según el modo para reforzar la intención de la acción.
         *
         * @param titulo texto del encabezado.
         * @param modo   modo actual del formulario.
         * @return panel de cabecera.
         */
        private JPanel crearCabecera(String titulo, ModoFormulario modo) {
            JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 12));
            cabecera.setBackground(COLOR_SURFACE_ALT);
            cabecera.setBorder(new MatteBorder(0, 0, 1, 0, COLOR_BORDER));

            // Icono y color diferenciados por modo para reforzar la semántica de la acción
            String icono = switch (modo) {
                case ALTA         -> "＋";
                case MODIFICACION -> "✏";
                case BAJA         -> "✕";
            };
            Color colorIcono = switch (modo) {
                case ALTA         -> COLOR_SUCCESS;
                case MODIFICACION -> COLOR_ACCENT;
                case BAJA         -> COLOR_DANGER;
            };

            JLabel lblIcono = new JLabel(icono);
            lblIcono.setFont(new Font("Segoe UI", Font.BOLD, 20));
            lblIcono.setForeground(colorIcono);

            JLabel lblTitulo = new JLabel(titulo);
            lblTitulo.setFont(FONT_BOLD);
            lblTitulo.setForeground(COLOR_TEXT);

            cabecera.add(lblIcono);
            cabecera.add(lblTitulo);
            return cabecera;
        }

        /**
         * Construye el cuerpo del formulario con los tres campos de datos del cliente.
         * En modo Baja los campos se desactivan ({@code setEditable(false)}) para
         * evitar que el usuario modifique datos accidentalmente antes de confirmar.
         *
         * @param cliente datos a precargar en los campos.
         * @param modo    modo actual del formulario.
         * @return panel con la cuadrícula de etiquetas y campos de texto.
         */
        private JPanel crearCuerpo(Cliente cliente, ModoFormulario modo) {
            JPanel cuerpo = new JPanel(new GridBagLayout());
            cuerpo.setBackground(COLOR_SURFACE);
            cuerpo.setBorder(new EmptyBorder(20, 24, 10, 24));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6, 4, 6, 4); // Margen uniforme entre celdas
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill   = GridBagConstraints.HORIZONTAL;

            // En modo Baja los campos son de solo lectura; en los demás, editables
            boolean editable = (modo != ModoFormulario.BAJA);

            // ── Fila 0: Razón social ───────────────────────────────────────
            campoRazonSocial = crearCampoFormulario(
                cliente.getRazonSocial() != null ? cliente.getRazonSocial() : "");
            campoRazonSocial.setEditable(editable);
            agregarFilaFormulario(cuerpo, gbc, 0, "Razón social:", campoRazonSocial);

            // ── Fila 1: Nombre comercial ───────────────────────────────────
            campoNombreComercial = crearCampoFormulario(
                cliente.getNombreComercial() != null ? cliente.getNombreComercial() : "");
            campoNombreComercial.setEditable(editable);
            agregarFilaFormulario(cuerpo, gbc, 1, "Nombre comercial:", campoNombreComercial);

            // ── Fila 2: Límite de crédito ──────────────────────────────────
            // En modo Alta con valor 0 se deja el campo vacío para que el usuario lo introduzca.
            // En los demás modos se muestra el valor formateado con 2 decimales.
            String limiteStr = (cliente.getLimiteCredito() == 0 && modo == ModoFormulario.ALTA)
                ? ""
                : String.format("%.2f", cliente.getLimiteCredito());
            campoLimiteCredito = crearCampoFormulario(limiteStr);
            campoLimiteCredito.setEditable(editable);
            agregarFilaFormulario(cuerpo, gbc, 2, "Límite de crédito:", campoLimiteCredito);

            // ── Fila 3 (solo en modo Baja): advertencia de irreversibilidad ─
            if (modo == ModoFormulario.BAJA) {
                gbc.gridy     = 3;
                gbc.gridx     = 0;
                gbc.gridwidth = 2; // La advertencia ocupa las dos columnas del grid
                JLabel lblAviso = new JLabel("⚠  Esta operación no se puede deshacer.");
                lblAviso.setFont(FONT_SMALL);
                lblAviso.setForeground(COLOR_WARNING);
                cuerpo.add(lblAviso, gbc);
            }

            return cuerpo;
        }

        /**
         * Crea un campo de texto estilizado con el valor inicial indicado.
         *
         * @param valorInicial texto a mostrar al abrirse el diálogo.
         * @return campo de texto configurado con la paleta de la interfaz.
         */
        private JTextField crearCampoFormulario(String valorInicial) {
            JTextField campo = new JTextField(valorInicial);
            estilizarTextField(campo); // Reutiliza el helper de estilización global
            campo.setPreferredSize(new Dimension(240, 32));
            return campo;
        }

        /**
         * Añade una fila compuesta por una etiqueta (columna izquierda) y un campo
         * (columna derecha) al panel del formulario usando {@link GridBagLayout}.
         *
         * @param panel  panel destino con {@link GridBagLayout}.
         * @param gbc    restricciones reutilizables (se modifica internamente fila/columna).
         * @param fila   índice de fila dentro del grid (0-based).
         * @param label  texto de la etiqueta descriptiva.
         * @param campo  componente de entrada de datos.
         */
        private void agregarFilaFormulario(JPanel panel, GridBagConstraints gbc,
                                            int fila, String label, JComponent campo) {
            // Columna 0: etiqueta descriptiva (ancho fijo)
            gbc.gridy     = fila;
            gbc.gridx     = 0;
            gbc.gridwidth = 1;
            gbc.weightx   = 0; // La etiqueta no se estira horizontalmente
            JLabel lbl = new JLabel(label);
            lbl.setFont(FONT_BOLD);
            lbl.setForeground(COLOR_TEXT_MUTED);
            panel.add(lbl, gbc);

            // Columna 1: campo de entrada (se estira para ocupar el espacio restante)
            gbc.gridx   = 1;
            gbc.weightx = 1.0;
            panel.add(campo, gbc);
        }

        /**
         * Construye el pie del diálogo con los botones Cancelar y Confirmar.
         *
         * <p>El botón de confirmación varía según el modo:
         * <ul>
         *   <li>Alta:         icono "＋", texto "Crear cliente",    color verde.</li>
         *   <li>Modificación: icono "✏", texto "Guardar cambios",  color azul.</li>
         *   <li>Baja:         icono "✕", texto "Eliminar cliente", color rojo.</li>
         * </ul>
         * </p>
         *
         * @param modo modo actual del formulario.
         * @return panel con los dos botones de acción.
         */
        private JPanel crearPieBotones(ModoFormulario modo) {
            JPanel pie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
            pie.setBackground(COLOR_SURFACE_ALT);
            pie.setBorder(new MatteBorder(1, 0, 0, 0, COLOR_BORDER));

            // ── Botón Cancelar ─────────────────────────────────────────────
            // Cierra el diálogo sin modificar el flag 'confirmado' (queda en false)
            JButton btnCancelar = crearBoton("Cancelar", COLOR_SURFACE_ALT, "✕");
            btnCancelar.setForeground(COLOR_TEXT_MUTED);
            btnCancelar.addActionListener(e -> dispose());

            // ── Botón Confirmar (adaptado al modo) ─────────────────────────
            String textoConfirmar = switch (modo) {
                case ALTA         -> "  Crear cliente";
                case MODIFICACION -> "  Guardar cambios";
                case BAJA         -> "  Eliminar cliente";
            };
            String iconoConfirmar = switch (modo) {
                case ALTA         -> "＋";
                case MODIFICACION -> "✏";
                case BAJA         -> "✕";
            };
            Color colorConfirmar = switch (modo) {
                case ALTA         -> COLOR_SUCCESS;
                case MODIFICACION -> COLOR_ACCENT;
                case BAJA         -> COLOR_DANGER;
            };

            JButton btnConfirmar = crearBoton(textoConfirmar, colorConfirmar, iconoConfirmar);
            btnConfirmar.addActionListener(e -> {
                // En modo Baja no hay datos que validar: los campos son de solo lectura
                if (modo != ModoFormulario.BAJA && !validarYGuardar()) {
                    return; // Validación fallida: mantener el diálogo abierto
                }
                confirmado = true; // Marcar la operación como confirmada
                dispose();         // Cerrar el diálogo y devolver el control al llamador
            });

            pie.add(btnCancelar);
            pie.add(btnConfirmar);
            return pie;
        }

        /**
         * Valida los valores introducidos en el formulario y, si son correctos,
         * los transfiere al objeto {@link #clienteResultante}.
         *
         * <p>Validaciones realizadas:
         * <ul>
         *   <li>Razón social: no puede estar vacía.</li>
         *   <li>Nombre comercial: no puede estar vacío.</li>
         *   <li>Límite de crédito: debe ser un número decimal válido y no negativo.</li>
         * </ul>
         * </p>
         *
         * @return {@code true} si todos los campos son válidos y los datos se han
         *         transferido al objeto Cliente; {@code false} si hay algún error.
         */
        private boolean validarYGuardar() {
            String razonSocial     = campoRazonSocial.getText().trim();
            String nombreComercial = campoNombreComercial.getText().trim();
            // Reemplazar coma por punto para aceptar tanto "1.234,56" como "1234.56"
            String limiteStr       = campoLimiteCredito.getText().trim().replace(",", ".");

            if (razonSocial.isEmpty()) {
                mostrarErrorDialogo("La razón social no puede estar vacía.");
                campoRazonSocial.requestFocus();
                return false;
            }

            if (nombreComercial.isEmpty()) {
                mostrarErrorDialogo("El nombre comercial no puede estar vacío.");
                campoNombreComercial.requestFocus();
                return false;
            }

            double limiteCredito;
            try {
                limiteCredito = Double.parseDouble(limiteStr);
                if (limiteCredito < 0) {
                    mostrarErrorDialogo("El límite de crédito no puede ser negativo.");
                    campoLimiteCredito.requestFocus();
                    return false;
                }
            } catch (NumberFormatException ex) {
                mostrarErrorDialogo("El límite de crédito debe ser un número decimal válido.");
                campoLimiteCredito.requestFocus();
                return false;
            }

            // Transferir los datos validados al objeto de dominio
            clienteResultante.setRazonSocial(razonSocial);
            clienteResultante.setNombreComercial(nombreComercial);
            clienteResultante.setLimiteCredito(limiteCredito);
            return true;
        }

        /**
         * Muestra un diálogo de error usando {@code this} como padre,
         * para que se apile correctamente sobre el diálogo modal.
         *
         * @param mensaje mensaje de error a mostrar.
         */
        private void mostrarErrorDialogo(String mensaje) {
            JOptionPane.showMessageDialog(this, mensaje, "Error de validación",
                JOptionPane.ERROR_MESSAGE);
        }

        // ── Getters para que el llamador recupere el resultado ──────────────

        /** @return {@code true} si el usuario confirmó la operación. */
        boolean isConfirmado() { return confirmado; }

        /** @return el objeto {@link Cliente} resultante de la interacción con el formulario. */
        Cliente getClienteResultante() { return clienteResultante; }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // RENDERER: FILAS ALTERNAS (efecto zebra + paleta oscura)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Renderer para celdas de texto e Integer que alterna el color de fondo
     * entre filas pares e impares (efecto zebra) y aplica la paleta oscura.
     * También gestiona el color de la fila seleccionada.
     */
    private class RendererFilaAlterna extends DefaultTableCellRenderer {

        /** Alineación horizontal de la celda (constante de {@link SwingConstants}). */
        private final int alineacion;

        /** Constructor con alineación a la izquierda por defecto. */
        RendererFilaAlterna() { this(SwingConstants.LEFT); }

        /**
         * @param alineacion constante de alineación: {@code LEFT}, {@code CENTER} o {@code RIGHT}.
         */
        RendererFilaAlterna(int alineacion) {
            this.alineacion = alineacion;
            setHorizontalAlignment(alineacion);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Fuente monoespaciada para la columna de IDs; estándar para el resto
            setFont(column == COL_ID ? FONT_MONO : FONT_LABEL);
            setForeground(COLOR_TEXT);
            setBorder(new EmptyBorder(0, 10, 0, 10)); // Padding lateral para no pegar el texto

            // Color de fondo: prioridad → selección → paridad de fila
            if (isSelected) {
                setBackground(COLOR_ROW_SEL);
            } else {
                setBackground(row % 2 == 0 ? COLOR_ROW_EVEN : COLOR_ROW_ODD);
            }

            return this;
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // RENDERER: IMPORTE (columna de límite de crédito)
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Renderer especializado para la columna "Límite Crédito".
     * Formatea el {@code double} con dos decimales y añade el símbolo "€",
     * alinea el importe a la derecha y lo colorea en amarillo para destacarlo.
     */
    private class RendererImporte extends DefaultTableCellRenderer {

        RendererImporte() {
            setHorizontalAlignment(SwingConstants.RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            // Formatear el valor double como "1.234,56 €" antes de pasarlo al super
            if (value instanceof Double) {
                value = String.format("%.2f €", (Double) value);
            }

            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            setFont(FONT_MONO);
            setForeground(COLOR_WARNING); // Amarillo para que el importe destaque visualmente
            setBorder(new EmptyBorder(0, 10, 0, 10));

            setBackground(isSelected ? COLOR_ROW_SEL
                : row % 2 == 0 ? COLOR_ROW_EVEN : COLOR_ROW_ODD);

            return this;
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // RENDERER: BOTÓN DE ACCIÓN EN CELDA
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * Renderer que dibuja un {@link JButton} estilizado dentro de una celda de la tabla.
     *
     * <p>Al ser un <i>renderer</i> (y no un <i>editor</i>), el botón es puramente
     * visual: no captura eventos. El clic real lo gestiona el {@link MouseMotionAdapter}
     * registrado sobre la tabla, que detecta la columna y actúa en consecuencia.</p>
     *
     * <p>El borde exterior del botón se colorea con el mismo color que el fondo de
     * la fila (zebra/selección) para que el botón parezca integrado en la celda.</p>
     */
    private class RendererBoton implements TableCellRenderer {

        /** Botón plantilla reutilizado en cada llamada a {@code getTableCellRendererComponent}. */
        private final JButton boton;

        /**
         * @param texto  texto visible en el botón.
         * @param color  color de fondo del botón (varía entre Modificar y Eliminar).
         */
        RendererBoton(String texto, Color color) {
            boton = new JButton(texto);
            boton.setFont(new Font("Segoe UI", Font.BOLD, 11));
            boton.setForeground(Color.WHITE);
            boton.setBackground(color);
            boton.setFocusPainted(false);
            boton.setBorderPainted(false);
            boton.setOpaque(true);
            boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            // El borde exterior del botón "funde" el componente con el fondo de la fila,
            // evitando que el botón parezca flotante sobre la celda
            Color colorFondo = isSelected ? COLOR_ROW_SEL
                : row % 2 == 0 ? COLOR_ROW_EVEN : COLOR_ROW_ODD;
            boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(5, 8, 5, 8, colorFondo),
                BorderFactory.createEmptyBorder(0, 6, 0, 6)
            ));

            return boton;
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // UI PERSONALIZADA: BARRA DE DESPLAZAMIENTO
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * UI personalizada para la barra de desplazamiento vertical del {@link JScrollPane}.
     *
     * <p>Elimina los botones de flecha (arriba/abajo) y dibuja el <i>thumb</i>
     * como un rectángulo redondeado minimalista, acorde con la estética oscura
     * de la interfaz.</p>
     */
    private static class ScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            thumbColor = new Color(45, 52, 80);  // Color del "pulgar" (zona arrastrable)
            trackColor = new Color(22, 26, 42);  // Color del carril de fondo
        }

        /**
         * Elimina el botón de flecha superior sustituyéndolo por uno de tamaño cero
         * (invisible), ya que en este diseño el scroll solo se usa arrastrando el thumb
         * o con la rueda del ratón.
         */
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return crearBotonInvisible();
        }

        /** Igual que {@link #createDecreaseButton}: elimina el botón de flecha inferior. */
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return crearBotonInvisible();
        }

        /**
         * Devuelve un {@link JButton} con tamaño cero en todas sus dimensiones,
         * efectivamente invisible y sin ocupar espacio en el layout.
         */
        private JButton crearBotonInvisible() {
            JButton b = new JButton();
            Dimension cero = new Dimension(0, 0);
            b.setPreferredSize(cero);
            b.setMinimumSize(cero);
            b.setMaximumSize(cero);
            return b;
        }

        /**
         * Dibuja el thumb (zona arrastrable) con esquinas redondeadas usando
         * {@link Graphics2D#fillRoundRect}. Un margen de 2px en cada lado
         * separa visualmente el thumb del carril.
         */
        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle tb) {
            if (tb.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(tb.x + 2, tb.y + 2, tb.width - 4, tb.height - 4, 4, 4);
            g2.dispose();
        }

        /** Dibuja el carril como un rectángulo sólido del color {@link #trackColor}. */
        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle tb) {
            g.setColor(trackColor);
            g.fillRect(tb.x, tb.y, tb.width, tb.height);
        }
    }


    // ══════════════════════════════════════════════════════════════════════════
    // ICONO DE VENTANA GENERADO PROGRAMÁTICAMENTE
    // ══════════════════════════════════════════════════════════════════════════

    /**
     * {@link ImageIcon} cuya imagen se genera en memoria mediante {@link Graphics2D},
     * dibujando la letra "C" sobre un fondo azul redondeado de 32×32 píxeles.
     *
     * <p>Se usa como icono de la barra de título de la ventana, evitando la
     * necesidad de incluir un fichero de imagen externo en el proyecto.</p>
     */
    private static class BufferedImageIcon extends ImageIcon {

        /**
         * Construye el icono de 32×32 px con la letra "C" centrada.
         */
        BufferedImageIcon() {
            java.awt.image.BufferedImage img =
                new java.awt.image.BufferedImage(32, 32,
                    java.awt.image.BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo: rectángulo azul con esquinas redondeadas (radio 8px)
            g2.setColor(new Color(82, 130, 255)); // COLOR_ACCENT
            g2.fillRoundRect(0, 0, 32, 32, 8, 8);

            // Letra "C" centrada sobre el fondo
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
            FontMetrics fm = g2.getFontMetrics();
            int x = (32 - fm.stringWidth("C")) / 2;
            int y = (32 - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString("C", x, y);

            g2.dispose();
            setImage(img);
        }
    }
}
