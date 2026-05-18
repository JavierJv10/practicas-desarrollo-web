package view;

import model.domain.Cliente;
import model.service.ClienteService;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class ClienteConsoleUI {

	// ── Códigos ANSI ──────────────────────────────────────────────────────────
	private static final String RESET = "\u001B[0m";
	private static final String BOLD = "\u001B[1m";
	private static final String CYAN = "\u001B[36m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String RED = "\u001B[31m";
	private static final String MAGENTA = "\u001B[35m";
	private static final String WHITE = "\u001B[37m";
	private static final String BG_BLUE = "\u001B[44m";

	// ── Singleton ─────────────────────────────────────────────────────────────
	private static ClienteConsoleUI instance = null;

	private final ClienteService clienteService;
	private final Scanner scanner;

	private ClienteConsoleUI(ClienteService clienteService) {
		this.clienteService = clienteService;
		this.scanner = new Scanner(System.in);
	}

	public static ClienteConsoleUI getInstance(ClienteService clienteService) {
		if (instance == null) {
			instance = new ClienteConsoleUI(clienteService);
		}
		return instance;
	}

	// ── Método principal ──────────────────────────────────────────────────────
	public void iniciar() {
		boolean salir = false;
		while (!salir) {
			mostrarMenu();
			int opcion = leerEntero("Elige una opción: ");
			System.out.println();
			switch (opcion) {
			case 1 -> listarTodos();
			case 2 -> buscarPorId();
			case 3 -> buscarPorRazonSocial();
			case 4 -> crearCliente();
			case 5 -> actualizarCliente();
			case 6 -> eliminarCliente();
			case 0 -> salir = confirmarSalida();
			default -> printWarning("Opción no válida. Inténtalo de nuevo.");
			}
			if (!salir)
				pausar();
		}
		printInfo("¡Hasta pronto!");
		scanner.close();
	}

	// ── Menú ──────────────────────────────────────────────────────────────────
	private void mostrarMenu() {
		System.out.println();
		System.out.println(BG_BLUE + BOLD + WHITE + "  ══════════════════════════════════  " + RESET);
		System.out.println(BG_BLUE + BOLD + WHITE + "       GESTIÓN DE CLIENTES            " + RESET);
		System.out.println(BG_BLUE + BOLD + WHITE + "  ══════════════════════════════════  " + RESET);
		System.out.println(CYAN + "  1." + RESET + " Listar todos los clientes");
		System.out.println(CYAN + "  2." + RESET + " Buscar cliente por ID");
		System.out.println(CYAN + "  3." + RESET + " Buscar clientes por razón social");
		System.out.println(CYAN + "  4." + RESET + " Dar de alta un cliente");
		System.out.println(CYAN + "  5." + RESET + " Actualizar un cliente");
		System.out.println(CYAN + "  6." + RESET + " Eliminar un cliente");
		System.out.println(RED + "  0." + RESET + " Salir");
		System.out.println(CYAN + "  ────────────────────────────────────" + RESET);
	}

	// ── Opciones ──────────────────────────────────────────────────────────────
	private void listarTodos() {
		printTitulo("LISTADO DE CLIENTES");
		ArrayList<Cliente> clientes = clienteService.findAll();
		if (clientes.isEmpty()) {
			printWarning("No hay clientes registrados.");
		} else {
			clientes.forEach(this::printCliente);
			printInfo("Total: " + clientes.size() + " cliente(s).");
		}
	}

	private void buscarPorId() {
		printTitulo("BUSCAR POR ID");
		int id = leerEntero("ID del cliente: ");
		Optional<Cliente> resultado = clienteService.findById(id);
		if (resultado.isPresent()) {
			printCliente(resultado.get());
		} else {
			printWarning("No se encontró ningún cliente con ID " + id + ".");
		}
	}

	private void buscarPorRazonSocial() {
		printTitulo("BUSCAR POR RAZÓN SOCIAL");
		String razonSocial = leerTexto("Razón social (o fragmento): ");
		ArrayList<Cliente> clientes = clienteService.findByRazonSocial(razonSocial);
		if (clientes.isEmpty()) {
			printWarning("No se encontraron clientes con esa razón social.");
		} else {
			clientes.forEach(this::printCliente);
			printInfo("Total: " + clientes.size() + " cliente(s) encontrado(s).");
		}
	}

	private void crearCliente() {
		printTitulo("ALTA DE CLIENTE");
		Cliente cliente = pedirDatosCliente(new Cliente());
		if (cliente == null)
			return;
		try {
			clienteService.create(cliente);
			printExito("Cliente creado correctamente.");
		} catch (IllegalArgumentException e) {
			printError("Error de validación: " + e.getMessage());
		}
	}

	private void actualizarCliente() {
		printTitulo("ACTUALIZAR CLIENTE");
		int id = leerEntero("ID del cliente a actualizar: ");
		Optional<Cliente> existente = clienteService.findById(id);
		if (existente.isEmpty()) {
			printWarning("No se encontró ningún cliente con ID " + id + ".");
			return;
		}
		printInfo("Cliente actual:");
		printCliente(existente.get());
		System.out.println();
		Cliente cliente = pedirDatosCliente(existente.get());
		if (cliente == null)
			return;
		cliente.setId(id);
		try {
			clienteService.update(cliente);
			printExito("Cliente actualizado correctamente.");
		} catch (IllegalArgumentException e) {
			printError("Error de validación: " + e.getMessage());
		}
	}

	private void eliminarCliente() {
		printTitulo("ELIMINAR CLIENTE");
		int id = leerEntero("ID del cliente a eliminar: ");
		Optional<Cliente> existente = clienteService.findById(id);
		if (existente.isEmpty()) {
			printWarning("No se encontró ningún cliente con ID " + id + ".");
			return;
		}
		printCliente(existente.get());
		System.out.print(YELLOW + "¿Confirmas la eliminación? (s/n): " + RESET);
		String confirmacion = scanner.nextLine().trim();
		if (confirmacion.equalsIgnoreCase("s")) {
			clienteService.deleteById(id);
			printExito("Cliente eliminado correctamente.");
		} else {
			printInfo("Operación cancelada.");
		}
	}

	private boolean confirmarSalida() {
		System.out.print(YELLOW + "¿Seguro que quieres salir? (s/n): " + RESET);
		String respuesta = scanner.nextLine().trim();
		return respuesta.equalsIgnoreCase("s");
	}

	// ── Helpers de entrada ────────────────────────────────────────────────────

	/**
	 * Pide los datos de un cliente. Recibe el objeto a rellenar (nuevo o
	 * existente). Devuelve null si el usuario cancela.
	 */
	private Cliente pedirDatosCliente(Cliente cliente) {
		String razonSocial = leerTexto("Razón social: ");
		String nombreComercial = leerTexto("Nombre comercial: ");
		double limiteCredito = leerDouble("Límite de crédito: ");

		cliente.setRazonSocial(razonSocial);
		cliente.setNombreComercial(nombreComercial);
		cliente.setLimiteCredito(limiteCredito);
		return cliente;
	}

	private String leerTexto(String prompt) {
		System.out.print(MAGENTA + prompt + RESET);
		return scanner.nextLine().trim();
	}

	private int leerEntero(String prompt) {
		while (true) {
			System.out.print(MAGENTA + prompt + RESET);
			String linea = scanner.nextLine().trim();
			try {
				return Integer.parseInt(linea);
			} catch (NumberFormatException e) {
				printError("Introduce un número entero válido.");
			}
		}
	}

	private double leerDouble(String prompt) {
		while (true) {
			System.out.print(MAGENTA + prompt + RESET);
			String linea = scanner.nextLine().trim().replace(",", ".");
			try {
				return Double.parseDouble(linea);
			} catch (NumberFormatException e) {
				printError("Introduce un número decimal válido.");
			}
		}
	}

	private void pausar() {
		System.out.print(WHITE + "\nPulsa ENTER para continuar..." + RESET);
		scanner.nextLine();
	}

	// ── Helpers de salida ─────────────────────────────────────────────────────
	private void printCliente(Cliente c) {
		System.out.println(GREEN + "  ▸ " + RESET + BOLD + "[" + c.getId() + "] " + RESET + WHITE + c.getRazonSocial()
				+ RESET + " | " + CYAN + c.getNombreComercial() + RESET + " | Límite: " + YELLOW
				+ String.format("%.2f €", c.getLimiteCredito()) + RESET);
	}

	private void printTitulo(String titulo) {
		System.out.println(BOLD + CYAN + "── " + titulo + " " + "─".repeat(Math.max(0, 34 - titulo.length())) + RESET);
	}

	private void printExito(String msg) {
		System.out.println(GREEN + "✔ " + msg + RESET);
	}

	private void printWarning(String msg) {
		System.out.println(YELLOW + "⚠ " + msg + RESET);
	}

	private void printError(String msg) {
		System.err.println(RED + "✖ " + msg + RESET);
	}

	private void printInfo(String msg) {
		System.out.println(WHITE + "ℹ " + msg + RESET);
	}
}