package ambato.app;

import ambato.modelo.Grafo;

public final class Main {
    public static void main(String[] args) {
        Grafo red = CasoBase.crear();
        System.out.println("1. registrarPersona A01–A06 y cargar las cinco amistades");
        System.out.print(red.mostrarRed());
        for (int i = 1; i <= 6; i++) {
            String id = "A0" + i;
            System.out.printf("contarAmigos(%s) = %d%n", id, red.contarAmigos(id));
        }
        System.out.println("2. seguir(A02,A01) = " + red.seguir("A02", "A01"));
        System.out.println("listarSeguidores(A01) = " + red.listarSeguidores("A01"));
        System.out.println("3. seguir(A06,A01) = " + red.seguir("A06", "A01"));
        System.out.println("listarSeguidos(A06) = " + red.listarSeguidos("A06"));
        System.out.println("contarAmigos(A06) = " + red.contarAmigos("A06"));
        System.out.println("4. listarAmigos(A04) = " + red.listarAmigos("A04"));
        System.out.println("5. eliminarAmistad(A04,A05) = "
                + red.eliminarAmistad("A04", "A05"));
        System.out.println("contarAmigos(A04) = " + red.contarAmigos("A04"));
        System.out.println("contarAmigos(A05) = " + red.contarAmigos("A05"));
        System.out.println("6. eliminarPersona(A01) = " + red.eliminarPersona("A01"));
        System.out.println("listarAmigos(A01) = " + red.listarAmigos("A01"));
        System.out.print(red.mostrarRed());
        System.out.println("7. registrarPersona(A02, otro nombre) = "
                + red.registrarPersona("A02", "Otro nombre"));
        System.out.println("8. agregarAmistad(A03,A03) = " + red.agregarAmistad("A03", "A03"));
    }
}
