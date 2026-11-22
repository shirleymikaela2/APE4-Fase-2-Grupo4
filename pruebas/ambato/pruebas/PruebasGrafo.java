package ambato.pruebas;

import ambato.app.CasoBase;
import ambato.modelo.Grafo;
import java.util.Objects;

public final class PruebasGrafo {
    private static int verificaciones;

    public static void main(String[] args) {
        secuenciaGuia();
        validaciones();
        tiposYBajas();
        System.out.println("Total: " + verificaciones + " verificaciones correctas.");
    }

    private static void igual(Object esperado, Object obtenido) {
        if (!Objects.equals(esperado, obtenido)) {
            throw new AssertionError("Esperado [" + esperado + "], obtenido [" + obtenido + "]");
        }
        verificaciones++;
    }

    private static void secuenciaGuia() {
        Grafo red = CasoBase.crear();
        igual(6L, red.mostrarRed().lines().count());
        String[] vecinos = {"Andrés, Mateo", "Valeria, Sofía", "Andrés, Mateo",
                "Camila, Sofía, Valeria", "Andrés", ""};
        int[] grados = {2, 2, 2, 3, 1, 0};
        for (int i = 0; i < grados.length; i++) {
            String id = "A0" + (i + 1);
            igual(grados[i], red.contarAmigos(id));
            igual(vecinos[i], red.listarAmigos(id));
            igual("", red.listarSeguidos(id));
        }
        System.out.println("Paso 1 correcto");
        igual(true, red.seguir("A02", "A01"));
        igual("Mateo", red.listarSeguidores("A01"));
        igual("", red.listarSeguidores("A02"));
        System.out.println("Paso 2 correcto");
        igual(true, red.seguir("A06", "A01"));
        igual("Sofía", red.listarSeguidos("A06"));
        igual("Diego, Mateo", red.listarSeguidores("A01"));
        igual(0, red.contarAmigos("A06"));
        System.out.println("Paso 3 correcto");
        igual("Camila, Sofía, Valeria", red.listarAmigos("A04"));
        System.out.println("Paso 4 correcto");
        igual(true, red.eliminarAmistad("A04", "A05"));
        igual("Sofía, Valeria", red.listarAmigos("A04"));
        igual("", red.listarAmigos("A05"));
        System.out.println("Paso 5 correcto");
        igual(true, red.eliminarPersona("A01"));
        igual(5L, red.mostrarRed().lines().count());
        igual(false, red.mostrarRed().contains("Sofía"));
        igual(null, red.listarAmigos("A01"));
        igual("", red.listarSeguidos("A02"));
        igual("", red.listarSeguidos("A06"));
        igual("Valeria", red.listarAmigos("A02"));
        igual("Valeria", red.listarAmigos("A04"));
        System.out.println("Paso 6 correcto");
        igual(false, red.registrarPersona("A02", "Otro nombre"));
        igual(false, red.registrarPersona("a02", "Otro nombre"));
        System.out.println("Paso 7 correcto");
        igual(false, red.agregarAmistad("A03", "a03"));
        System.out.println("Paso 8 correcto");
    }

    private static void validaciones() {
        Grafo red = new Grafo();
        for (String invalido : new String[]{null, "", " ", " A", "A ", "A B", "A,B", "A\tB",
                "A\u00a0B"}) {
            igual(false, red.registrarPersona(invalido, "Nombre"));
        }
        igual(false, red.registrarPersona("u", " "));
        igual(true, red.registrarPersona("u", "Nombre completo"));
        igual("", red.listarAmigos(" U "));
        igual(null, red.listarAmigos("otro"));
        igual(null, red.listarSeguidos(null));
        igual(null, red.listarSeguidores("otro"));
        igual(-1, red.contarAmigos("otro"));
        igual(false, red.agregarAmistad("u", "otro"));
        igual(false, red.seguir("otro", "u"));
        igual(false, red.seguir("u", "U"));
        igual(false, red.eliminarAmistad("u", "otro"));
        igual(false, red.dejarDeSeguir("u", "otro"));
        igual(false, red.eliminarPersona("otro"));
        igual(true, red.eliminarPersona("U"));
        igual("", red.mostrarRed());
        System.out.println("Validaciones de entrada correctas");
    }

    private static void tiposYBajas() {
        Grafo red = new Grafo();
        igual(true, red.registrarPersona("a", "Ana"));
        igual(true, red.registrarPersona("b", "Bea"));
        igual(true, red.registrarPersona("c", "Caro"));
        igual(true, red.agregarAmistad("a", "b"));
        igual(false, red.agregarAmistad("b", "a"));
        igual(true, red.seguir("a", "b"));
        igual(false, red.seguir("a", "B"));
        igual(true, red.seguir("b", "a"));
        igual(true, red.dejarDeSeguir("a", "b"));
        igual(false, red.dejarDeSeguir("a", "b"));
        igual("", red.listarSeguidos("a"));
        igual("Ana", red.listarSeguidos("b"));
        igual("Bea", red.listarAmigos("a"));
        igual(true, red.eliminarAmistad("b", "a"));
        igual(false, red.eliminarAmistad("a", "b"));
        igual("Ana", red.listarSeguidos("b"));
        igual("", red.listarAmigos("b"));
        igual(true, red.agregarAmistad("a", "b"));
        igual(true, red.seguir("c", "a"));
        igual(true, red.seguir("a", "c"));
        igual(true, red.eliminarPersona("a"));
        igual("", red.listarSeguidos("b"));
        igual("", red.listarSeguidos("c"));
        igual("", red.listarAmigos("b"));
        igual("", red.listarSeguidores("c"));
        igual(true, red.registrarPersona("a", "Ana nueva"));
        igual("", red.listarSeguidores("a"));
        igual("", red.listarAmigos("a"));
        igual("", red.listarSeguidos("a"));
        igual(true, red.eliminarPersona("a"));
        igual(true, red.eliminarPersona("c"));
        igual(true, red.eliminarPersona("b"));
        igual("", red.mostrarRed());
        System.out.println("Independencia de relaciones y limpieza correctas");
    }
}
