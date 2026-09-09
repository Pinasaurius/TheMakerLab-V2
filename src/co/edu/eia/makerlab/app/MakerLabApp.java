package co.edu.eia.makerlab.app;

// Importamos todos los paquetes

import co.edu.eia.makerlab.modelo.certificacion.Certificacion;
import co.edu.eia.makerlab.modelo.equipos.EquipoLaboratorio;
import co.edu.eia.makerlab.modelo.equipos.EstacionElectronica;
import co.edu.eia.makerlab.modelo.equipos.InstrumentoAvanzado;
import co.edu.eia.makerlab.modelo.equipos.OtroInstrumento;
import co.edu.eia.makerlab.modelo.laboratorio.Laboratorio;
import co.edu.eia.makerlab.modelo.reserva.Reserva;
import co.edu.eia.makerlab.modelo.usuarios.Estudiante;
import co.edu.eia.makerlab.modelo.usuarios.Profesor;
import co.edu.eia.makerlab.modelo.usuarios.Tecnico;
import co.edu.eia.makerlab.modelo.usuarios.Usuario;
import co.edu.eia.makerlab.servicios.ServicioAdministrativo;
import co.edu.eia.makerlab.util.ConsolaUtil;
import co.edu.eia.makerlab.util.FechaSistema;
import co.edu.eia.makerlab.util.Notificador;

// Clase main de esta entrega 1
/* Despues de escoger el numero de arreglos a demostracion hara lo siguiente:
 * 1. Registrara todas las clases pedidas: usuarios laboratorios, equipos y certificaciones.
 * 2. Creara una reserva valida que sea confirmada y rechazara otras por las diferentes reglas de negocio.
 * 3. Pone en espera una solicitud valida por que no hay disponibilidad.
 * 4. Abre y cierra sesiones cumpliendo tambien la regla de abrir 2 veces.
 * 5. Crea y asigna tickets que modifican el estado de equipos y cuando modifica reservas notifica a los afectados
 * 6. Corre las consultas y los indicadores de memoria
 * 7. Recorre los usuarios y equipos demostrando el polimorfismo
 * */

public class MakerLabApp {
	public static void main(String[] args) {
		Notificador.reiniciarContador();

		ConsolaUtil.titulo("The MakerLab - MakerLab EIA - Entrega 1");
		System.out.println("Fecha actual del sistema (demo): " + FechaSistema.hoy());
		
        // Paso 0: ingresar numero de arreglos n
		
		int n = ConsolaUtil.leerCapacidad(
				"Ingrese la capacidad n para los arreglos de 0..*", 20, 10);
		System.out.println("Se usara capacidad n = " + n + " para todos los arreglos.");

		ServicioAdministrativo servicio = new ServicioAdministrativo(n);
        // Paso 1: registro usuarios
		ConsolaUtil.titulo("Paso 1.1 - Registro de usuarios");

		Estudiante ana = new Estudiante("E01", "Ana Ruiz", "ana@eia.edu.co", "ESTUDIANTE",
				"1001", "Pregrado 6 semestre", n);
		Estudiante luis = new Estudiante("E02", "Luis Pena", "luis@eia.edu.co", "ESTUDIANTE",
				"1002", "Pregrado 4 semestre", n);
		Profesor marta = new Profesor("P01", "Marta Gil", "marta@eia.edu.co", "PROFESOR",
				"PhD en Mecatronica", true, n);
		Tecnico jorge = new Tecnico("T01", "Jorge Diaz", "jorge@eia.edu.co", "TECNICO",
				"Tecnologo en Electronica", "5 anios en laboratorios Maker", true, n);
		Tecnico sara = new Tecnico("T02", "Sara Lopez", "sara@eia.edu.co", "TECNICO",
				"Ingeniera Electronica", "3 anios en mantenimiento", true, n);

		System.out.println("registrarUsuario(E01): " + servicio.registrarUsuario(ana));
		System.out.println("registrarUsuario(E02): " + servicio.registrarUsuario(luis));
		System.out.println("registrarUsuario(P01): " + servicio.registrarUsuario(marta));
		System.out.println("registrarUsuario(T01): " + servicio.registrarUsuario(jorge));
		System.out.println("registrarUsuario(T02): " + servicio.registrarUsuario(sara));
		System.out.println("registrarUsuario(E01 repetido): "
				+ servicio.registrarUsuario(new Estudiante("E01", "Otro", "x@eia.edu.co", "ESTUDIANTE",
						"9999", "N/A", n)));
		
        // registro laboratorios
		
		ConsolaUtil.titulo("Paso 1.2 - Registro de laboratorios");

		Laboratorio labA = new Laboratorio("LAB-A", "FabLab EIA", "Bloque F - piso 1",
				"07:00-19:00", Laboratorio.ESTADO_ACTIVO, n);
		Laboratorio labB = new Laboratorio("LAB-B", "Laboratorio de Electronica EIA", "Bloque F - piso 2",
				"08:00-18:00", Laboratorio.ESTADO_ACTIVO, n);

		System.out.println("agregarLaboratorio(LAB-A): " + servicio.agregarLaboratorio(labA));
		System.out.println("agregarLaboratorio(LAB-B): " + servicio.agregarLaboratorio(labB));

		// registro equipos
		
		ConsolaUtil.titulo("Paso 1.3 - Registro de equipos");

		InstrumentoAvanzado eq01 = new InstrumentoAvanzado("EQ01", "Impresora 3D Prusa MK4", "MEDIO",
				EquipoLaboratorio.ESTADO_OPERATIVO, "datasheet/eq01.pdf", "IMPRESION_3D");
		eq01.setDuracionEstimadaMinutos(90);
		InstrumentoAvanzado eq02 = new InstrumentoAvanzado("EQ02", "Cortadora Laser CO2", "ALTO",
				EquipoLaboratorio.ESTADO_OPERATIVO, "datasheet/eq02.pdf", "CORTE_LASER");
		eq02.setDuracionEstimadaMinutos(120);
		EstacionElectronica eq03 = new EstacionElectronica("EQ03", "Estacion de Soldadura 1", "BAJO",
				EquipoLaboratorio.ESTADO_OPERATIVO, "datasheet/eq03.pdf", 1);
		eq03.setDuracionEstimadaMinutos(60);
		EstacionElectronica eq04 = new EstacionElectronica("EQ04", "Estacion de Medicion 2", "BAJO",
				EquipoLaboratorio.ESTADO_OPERATIVO, "datasheet/eq04.pdf", 2);
		eq04.setDuracionEstimadaMinutos(90);
		OtroInstrumento eq05 = new OtroInstrumento("EQ05", "Router CNC 3 ejes", "ALTO",
				EquipoLaboratorio.ESTADO_OPERATIVO, "datasheet/eq05.pdf", "Area util 60x40 cm",
				"MADERA_Y_PLASTICO");
		eq05.setDuracionEstimadaMinutos(60);

		System.out.println("registrarEquipoEnLaboratorio(LAB-A, EQ01): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-A", eq01));
		System.out.println("registrarEquipoEnLaboratorio(LAB-A, EQ02): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-A", eq02));
		System.out.println("registrarEquipoEnLaboratorio(LAB-A, EQ05): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-A", eq05));
		System.out.println("registrarEquipoEnLaboratorio(LAB-B, EQ03): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-B", eq03));
		System.out.println("registrarEquipoEnLaboratorio(LAB-B, EQ04): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-B", eq04));
		System.out.println("registrarEquipoEnLaboratorio(LAB-B, EQ01 repetido): "
				+ servicio.registrarEquipoEnLaboratorio("LAB-B", eq01));

		// registro certificaciones (1 mal a proposito)
		
		ConsolaUtil.titulo("Paso 1.4 - Registro de certificaciones");

		Certificacion c01 = new Certificacion("C01", "2026-01-10", "2026-12-31", ana, eq01);
		Certificacion c02 = new Certificacion("C02", "2025-01-01", "2026-03-01", luis, eq01);
		Certificacion c03 = new Certificacion("C03", "2026-02-01", "2026-09-20", marta, eq03);
		Certificacion c04 = new Certificacion("C04", "2026-03-01", "2026-11-30", ana, eq03);

		System.out.println("registrarCertificacion(C01): " + servicio.registrarCertificacion(c01));
		System.out.println("registrarCertificacion(C02 vencida): " + servicio.registrarCertificacion(c02));
		System.out.println("registrarCertificacion(C03): " + servicio.registrarCertificacion(c03));
		System.out.println("registrarCertificacion(C04): " + servicio.registrarCertificacion(c04));
		System.out.println("registrarCertificacion(C01 repetida): "
				+ servicio.registrarCertificacion(new Certificacion("C01", "2026-01-01", "2026-12-31", ana, eq01)));

		// Paso 2: Confirmar y rechazar reservas
		
		ConsolaUtil.titulo("Paso 2 - Reservas: 1 confirmada y varios rechazos");

		Reserva r01 = servicio.registrarReserva("R01", "2026-09-10", "09:00", "11:00",
				"Impresion de prototipo de trabajo de grado", ana, eq01);
		r01.mostrarDatos();

		Reserva r02 = servicio.registrarReserva("R02", "2026-09-10", "13:00", "14:00",
				"Impresion de pieza personal", luis, eq01);
		r02.mostrarDatos();

		Reserva r03 = servicio.registrarReserva("R03", "2026-09-10", "10:00", "11:30",
				"Segunda impresion", ana, eq01);
		r03.mostrarDatos();

		eq02.setEstadoEquipo(EquipoLaboratorio.ESTADO_FUERA_DE_SERVICIO);
		Reserva r04 = servicio.registrarReserva("R04", "2026-09-11", "09:00", "10:00",
				"Corte de acrilico", ana, eq02);
		r04.mostrarDatos();

		Reserva r05 = servicio.registrarReserva("R05", "2026-09-12", "06:00", "07:00",
				"Impresion muy temprano", ana, eq01);
		r05.mostrarDatos();

		Reserva r06 = servicio.registrarReserva("R06", "2026-09-12", "10:00", "10:00",
				"Reserva mal definida", ana, eq05);
		r06.mostrarDatos();

		Reserva r07 = servicio.registrarReserva("R07", "2026-09-10", "08:30", "09:30",
				"Practica guiada de soldadura", marta, eq03);
		r07.mostrarDatos();

		Reserva r08 = servicio.registrarReserva("R08", "2026-09-18", "09:00", "10:00",
				"Mediciones para proyecto", marta, eq04);
		r08.mostrarDatos();

		// Paso 3: Lista de espera para solicitudes validas
		
		ConsolaUtil.titulo("Paso 3 - Lista de espera");
		System.out.println("moverAListaDeEspera(R03): " + servicio.moverAListaDeEspera("R03"));
		System.out.println("moverAListaDeEspera(R03 de nuevo): " + servicio.moverAListaDeEspera("R03"));
		System.out.println("moverAListaDeEspera(R02 rechazo por certificacion): "
				+ servicio.moverAListaDeEspera("R02"));

		
		// Paso 4: Sesiones de uso
		
		ConsolaUtil.titulo("Paso 4 - Sesiones de uso");

		servicio.abrirSesion("S01", r07, "08:35");
		System.out.println("cerrarSesion(S01): " + servicio.cerrarSesion("S01", "09:40",
				"Estano 2 m, punta de repuesto 1", "Uso normal, sin novedades", "NINGUNO"));
		System.out.println("cerrarSesion(S01 otra vez): " + servicio.cerrarSesion("S01", "09:50",
				"-", "-", "-"));

		servicio.abrirSesion("S02", r01, "09:05");
		System.out.println("cerrarSesion(S02): " + servicio.cerrarSesion("S02", "10:55",
				"Filamento PLA 40 g", "Cama nivelada al iniciar", "Atasco de filamento a mitad de impresion"));

		servicio.abrirSesion("S03", r02, "12:00");
		
		// Paso 5: Creacion y asignacion de Tickets

		ConsolaUtil.titulo("Paso 5 - Mantenimiento y bloqueo de reservas");

		servicio.crearTicket("TK01", "ALTA", "Calibracion fuera de rango en el multimetro",
				eq04, jorge, "2026-09-17", "10:00");
		r08.mostrarDatos();

		servicio.crearTicket("TK02", "BAJA", "Revision preventiva del router CNC",
				eq05, jorge, "2026-09-17", "11:00");

		servicio.crearTicket("TK02", "BAJA", "Revision preventiva del router CNC",
				eq05, sara, "2026-09-17", "11:00");

		System.out.println("cerrarTicket(TK01 sin accion): "
				+ servicio.cerrarTicket("TK01", "", "OPERATIVO"));

		System.out.println("cerrarTicket(TK01 con accion): "
				+ servicio.cerrarTicket("TK01", "Recalibracion y cambio de sonda de medicion",
						EquipoLaboratorio.ESTADO_OPERATIVO));

		System.out.println("cerrarTicket(TK02 con accion): "
				+ servicio.cerrarTicket("TK02", "Ajuste de correa del eje X y lubricacion de guias",
						EquipoLaboratorio.ESTADO_OPERATIVO));

		// Paso 6: Corre las consultas
		
		ConsolaUtil.titulo("Paso 6 - Consultas");
		servicio.consultarDisponibilidad("LAB-A", "2026-09-10", "09:00", "11:00");
		System.out.println();
		servicio.consultarReservas("E01");
		System.out.println();
		servicio.consultarEquiposFueraDeServicio();
		System.out.println();
		servicio.consultarCertificacionesPorVencer(30);
		System.out.println();
		servicio.consultarTicketsPorPrioridad("ALTA");

		//Paso 7: Revision de Usuarios
		
		ConsolaUtil.titulo("Paso 7.1 - Indicadores en memoria");
		servicio.mostrarIndicadores();

		ConsolaUtil.titulo("Paso 7.2 - Polimorfismo: usuarios");
		for (int i = 0; i < servicio.getContadorUsuarios(); i++) {
			Usuario u = servicio.getUsuario(i);
			u.mostrarDatosRoles();
		}

		ConsolaUtil.titulo("Paso 7.3 - Polimorfismo: equipos por laboratorio");
		for (int i = 0; i < servicio.getContadorLaboratorios(); i++) {
			Laboratorio lab = servicio.getLaboratorio(i);
			lab.mostrarDatos();
		}

		// RESUMEN
		
		ConsolaUtil.titulo("Paso Final - Resumen final");
		servicio.mostrarResumenGeneral();
		System.out.println("Notificaciones enviadas durante la demo: "
				+ Notificador.getTotalNotificaciones());
		ConsolaUtil.separador();
		System.out.println("Fin de la demostracion de la Entrega 1.");
	}
}
