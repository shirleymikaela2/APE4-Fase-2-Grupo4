package ambato.modelo;

import java.util.Locale;
import java.util.StringJoiner;

import static ambato.modelo.TipoRelacion.AMISTAD;
import static ambato.modelo.TipoRelacion.SEGUIR;

public final class Grafo {
    private record Persona(String usuario, String nombre) {}

    private static final class Vertice {
        final Persona persona;
        Arista aristas;
        Vertice sig;

        Vertice(Persona persona, Vertice sig) {
            this.persona = persona;
            this.sig = sig;
        }
    }

    private static final class Arista {
        final Vertice destino;
        final TipoRelacion tipo;
        Arista sig;

        Arista(Vertice destino, TipoRelacion tipo, Arista sig) {
            this.destino = destino;
            this.tipo = tipo;
            this.sig = sig;
        }
    }

    private Vertice cabeza;

    private static String normalizar(String usuario) {
        return usuario == null ? "" : usuario.strip().toLowerCase(Locale.ROOT);
    }

    private Vertice buscar(String usuario) {
        String clave = normalizar(usuario);
        for (Vertice v = cabeza; v != null; v = v.sig) {
            if (v.persona.usuario().equals(clave)) return v;
        }
        return null;
    }

    public boolean registrarPersona(String usuario, String nombre) {
        if (usuario == null || usuario.isEmpty() || usuario.indexOf(',') >= 0
                || usuario.codePoints().anyMatch(c -> Character.isWhitespace(c)
                        || Character.isSpaceChar(c)) || buscar(usuario) != null) {
            return false;
        }
        if (nombre == null || nombre.isBlank()) return false;
        cabeza = new Vertice(new Persona(normalizar(usuario), nombre.strip()), cabeza);
        return true;
    }

    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return false;

        Vertice anterior = null;
        for (Vertice v = cabeza; v != objetivo; v = v.sig) anterior = v;
        for (Vertice v = cabeza; v != null; v = v.sig) quitarHacia(v, objetivo, null);

        if (anterior == null) cabeza = objetivo.sig;
        else anterior.sig = objetivo.sig;
        return true;
    }

    private static boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.destino == destino && a.tipo == tipo) return true;
        }
        return false;
    }

    private static boolean quitarHacia(Vertice origen, Vertice destino, TipoRelacion tipo) {
        boolean eliminada = false;
        Arista anterior = null;
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.destino == destino && (tipo == null || a.tipo == tipo)) {
                if (anterior == null) origen.aristas = a.sig;
                else anterior.sig = a.sig;
                eliminada = true;
            } else {
                anterior = a;
            }
        }
        return eliminada;
    }

    private boolean agregar(String a, String b, TipoRelacion tipo) {
        Vertice origen = buscar(a);
        Vertice destino = buscar(b);
        if (origen == null || destino == null || origen == destino
                || existe(origen, destino, tipo)) return false;

        origen.aristas = new Arista(destino, tipo, origen.aristas);
        if (tipo == AMISTAD) destino.aristas = new Arista(origen, tipo, destino.aristas);
        return true;
    }

    public boolean agregarAmistad(String a, String b) {
        return agregar(a, b, AMISTAD);
    }

    public boolean seguir(String a, String b) {
        return agregar(a, b, SEGUIR);
    }

    private boolean eliminar(String a, String b, TipoRelacion tipo) {
        Vertice origen = buscar(a);
        Vertice destino = buscar(b);
        if (origen == null || destino == null || !quitarHacia(origen, destino, tipo)) {
            return false;
        }
        if (tipo == AMISTAD) quitarHacia(destino, origen, tipo);
        return true;
    }

    public boolean eliminarAmistad(String a, String b) {
        return eliminar(a, b, AMISTAD);
    }

    public boolean dejarDeSeguir(String a, String b) {
        return eliminar(a, b, SEGUIR);
    }

    private static String listarSalientes(Vertice origen, TipoRelacion tipo) {
        StringJoiner nombres = new StringJoiner(", ");
        for (Arista a = origen.aristas; a != null; a = a.sig) {
            if (a.tipo == tipo) nombres.add(a.destino.persona.nombre());
        }
        return nombres.toString();
    }

    public String listarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        return v == null ? null : listarSalientes(v, AMISTAD);
    }

    public String listarSeguidos(String usuario) {
        Vertice v = buscar(usuario);
        return v == null ? null : listarSalientes(v, SEGUIR);
    }

    public String listarSeguidores(String usuario) {
        Vertice destino = buscar(usuario);
        if (destino == null) return null;

        StringJoiner nombres = new StringJoiner(", ");
        for (Vertice v = cabeza; v != null; v = v.sig) {
            if (existe(v, destino, SEGUIR)) nombres.add(v.persona.nombre());
        }
        return nombres.toString();
    }

    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return -1;

        int cantidad = 0;
        for (Arista a = v.aristas; a != null; a = a.sig) {
            if (a.tipo == AMISTAD) cantidad++;
        }
        return cantidad;
    }

    public String mostrarRed() {
        StringBuilder red = new StringBuilder();
        for (Vertice v = cabeza; v != null; v = v.sig) {
            red.append(v.persona.usuario().toUpperCase(Locale.ROOT)).append(" ")
                    .append(v.persona.nombre()).append(" | Amigos: ")
                    .append(listarSalientes(v, AMISTAD)).append(" | Sigue a: ")
                    .append(listarSalientes(v, SEGUIR)).append('\n');
        }
        return red.toString();
    }
}
