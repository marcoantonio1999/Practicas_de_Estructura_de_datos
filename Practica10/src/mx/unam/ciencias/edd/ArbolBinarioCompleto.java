package mx.unam.ciencias.edd;

import java.util.Iterator;


/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios completos. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Constructor que recibe la raíz del árbol. */
        public Iterador() {
        	cola = new Cola<>();
            if(raiz != null)
                cola.mete(raiz);
        
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
        	return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
        	 Vertice vertice = cola.saca();
             if (vertice.hayIzquierdo())
                 cola.mete(vertice.izquierdo);
             if (vertice.hayDerecho())
                 cola.mete(vertice.derecho);
             return vertice.get();
        
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
    	if (elemento == null)
            throw new IllegalArgumentException();

        Vertice el = new Vertice(elemento);
        elementos ++;
        
        if (raiz == null)
           raiz = el;       	 
        
        else {
            
            Vertice a = raiz;
            Cola<Vertice> cola = new Cola<>();
            
            cola.mete(a);
            while (!cola.esVacia()) {
                a = cola.saca();
            
                if (!a.hayIzquierdo() || !a.hayDerecho()) {
                    el.padre = a;
                    if (!a.hayIzquierdo())
                         a.izquierdo = el;
                    else if (!a.hayDerecho())
                       a.derecho = el;
            
                    break;
                }
   cola.mete(a.izquierdo);
                cola.mete(a.derecho);
            }
            }

    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
    	  if(elemento == null || esVacia())
    		    return ;
    		    	
    		    Cola<Vertice> cola = new Cola<>();
    		    
    		    cola.mete(raiz);
    		    
    		    Vertice elimina = null;
    		    Vertice ultimo = null;
    		    while (!cola.esVacia()){
    		    	ultimo = cola.saca();
    		    	if (ultimo.get().equals(elemento)) elimina = ultimo ;
    		    	if(ultimo.hayIzquierdo()) cola.mete(ultimo.izquierdo);
    		    	if (ultimo.hayDerecho()) cola.mete(ultimo.derecho);
    		    }
    		    if (elimina == null)
    		    	return ;
    		    T a1 = elimina.get();
    		    T a2 = ultimo.get();
    		    
    		    elimina.elemento = a2;
    		    ultimo.elemento = a1;
    		    
    		    if (ultimo == raiz) {
    		    	raiz = null;
    		    raiz = null;
    		    elementos = 0;
    		    return ;
    		    }
    		    Vertice padre = ultimo.padre;
    		    if(padre.izquierdo == ultimo)
    		    	padre.izquierdo = null;
    		    else padre.derecho = null;
    		    elementos --;
    		    

    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
    
    	if (elementos == 0)
    		return -1;
    	
    	return (int) ( Math.log(elementos)/Math.log(2));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
        	return ;
       Cola<Vertice> cola = new Cola<>();
       cola.mete(raiz);
       Vertice a;
       while (!cola.esVacia()) {
    	   
    	   a = cola.saca();
    	   accion.actua(a);
       if (a.hayIzquierdo())
       cola.mete(a.izquierdo);
       if(a.hayDerecho())
    	   cola.mete(a.derecho);
       }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
