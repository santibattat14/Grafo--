package com.battatydelacruz.pablo.santiago.app;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.util.HashSet;

public class Graph<V> {
    // Lista de adyacencia.
    private Map<V, Set<V>> adjacencyList = new HashMap();

    /******************************************************************
     * Añade el vértice ‘v‘ al grafo.
     *
     * @param v vértice a añadir.
     * @return ‘true‘ si no estaba anteriormente y ‘false‘ en caso
     *         contrario.
     ******************************************************************/
    public boolean addVertex(V v) {
        if (adjacencyList.containsKey(v)) {
            return false;
        }
        adjacencyList.put(v, new HashSet<V>());
        return true;
    }

    /******************************************************************
     * Añade un arco entre los vértices ‘v1‘ y ‘v2‘ al grafo. En
     * caso de que no exista alguno de los vértices, lo añade
     * también.
     *
     * @param v1 el origen del arco.
     * @param v2 el destino del arco.
     * @return ‘true‘ si no existía el arco y ‘false‘ en caso
     *         contrario.
     ******************************************************************/
    public boolean addEdge(V v1, V v2) {
        addVertex(v1);
        addVertex(v2);
        if (adjacencyList.get(v1).contains(v2)) {
            return false;
        }
        adjacencyList.get(v1).add(v2);
        return true;
    }

    /******************************************************************
     * Obtiene el conjunto de vértices adyacentes a ‘v‘.
     *
     * @param v vértice del que se obtienen los adyacentes.
     * @return conjunto de vértices adyacentes.
     ******************************************************************/
    public Set<V> obtainAdjacents(V v) throws Exception {
        Set<V> adjacents = adjacencyList.get(v);
        if (adjacents == null) {
            throw new Exception("Vertex not found");
        }
        return adjacents;
    }

    /******************************************************************
     * Comprueba si el grafo contiene el vértice dado.
     *
     * @param v vértice para el que se realiza la comprobación.
     * @return ‘true‘ si ‘v‘ es un vértice del grafo.
     ******************************************************************/
    public boolean containsVertex(V v) {
        return adjacencyList.containsKey(v);
    }

       /******************************************************************
     * Método ‘toString()‘ reescrito para la clase ‘Grafo.java‘.
     *
     * @return una cadena de caracteres con la lista de
     *         adyacencia.
     ******************************************************************/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V vertex : adjacencyList.keySet()) {
            sb.append(vertex.toString() + ": ");
            for (V adjacent : adjacencyList.get(vertex)) {
                sb.append(adjacent.toString() + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /*********************************************************
     * Obtiene, en caso de que exista, un camino entre ‘v1‘ y
     * ‘v2‘. En caso contrario, devuelve ‘null‘.
     *
     * @param v1 el vértice origen.
     * @param v2 el vértice destino.
     * @return lista con la secuencia de vértices desde ‘v1‘ hasta
     *         ‘v2‘ * pasando por arcos del grafo.
     *********************************************************/
    public List<V> onePath(V v1, V v2) {
        Map<V, V> trace = new HashMap(); // Crear la tabla traza
        Stack<V> open = new Stack(); // Crear la pila abierta
        open.push(v1);
        trace.put(v1, null); // Inicializar la tabla traza
        boolean found = false;

        while (!open.isEmpty() && !found) {
            V v = open.pop();
            found = v.equals(v2);

            if (!found) {
                for (V s : adjacencyList.get(v)) {
                    if (!trace.containsKey(s)) { // Evitar ciclos
                        open.push(s);
                        trace.put(s, v);
                    }
                }
            }
        }

        if (found) {
            List<V> path = new ArrayList();
            V v = v2;
            while (v != null) { // Reconstruir el camino
                path.add(0, v);
                v = trace.get(v);
            }
            return path;
        } else {
            return null; // No se encontró un camino
        }
    }
}

