
package mx.unam.ciencias.edd;

import java.util.Iterator;



/**
 * <p>Clase para árboles binarios ordenados. Los arboles son genericos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un arbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el arbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el arbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase privada para iteradores de arboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vertices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Construye un iterador con el vertice recibido. */
        public Iterador() {
        	  pila = new Pila<>();
              Vertice v = raiz;
              while(v != null){
                pila.mete(v);
                v = v.izquierdo;
              }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
        	return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
        	Vertice v = pila.saca();
            T e = v.get();
            v = v.derecho;
            while(v != null){
              pila.mete(v);
              v = v.izquierdo;
            }
            return e;
                }


        }
    

    /**
     * El vertice del ultimo elemento agegado. Este vertice solo se puede
     * garantizar que existe <em>inmediatamente</em> despues de haber agregado
     * un elemento al arbol. Si cualquier operacion distinta a agregar sobre el
     * arbol se ejecuta despues de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parametros. Para no perder el constructor sin parametros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un arbol binario ordenado a partir de una coleccion. El arbol
     * binario ordenado tiene los mismos elementos que la coleccion recibida.
     * @param coleccion la coleccion a partir de la cual creamos el arbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al arbol. El �rbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
    	if(elemento == null) throw new IllegalArgumentException();
        if(esVacia()){
          raiz = ultimoAgregado = nuevoVertice(elemento);
          elementos++;
          return;
        }
        agrega(raiz, nuevoVertice(elemento));
        elementos++;
    }

    private void agrega(Vertice a, Vertice b){
      if(b.get().compareTo(a.get()) <= 0){
        if(a.hayIzquierdo())
          agrega(a.izquierdo,b);
        else{
          b.padre = a;
          a.izquierdo = ultimoAgregado = b;
        }
      }else{
        if(a.hayDerecho())
          agrega(a.derecho,b);
        else{
          b.padre = a;
          a.derecho = ultimoAgregado = b;
        }
    	
    	}

    }

    /**
     * Elimina un elemento. Si el elemento no este en el arbol, no hace nada; si
     * esta varias veces, elimina el primero que encuentre (in-order). El arbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
    	   
    	Vertice vertice = busca(raiz, elemento);
        if(vertice == null)
          return;
        if(vertice.hayIzquierdo()){
          Vertice aux = vertice;
          vertice = maximoEnSubarbol(vertice.izquierdo);
          aux.elemento = vertice.elemento;
        }
        if(!vertice.hayIzquierdo() && !vertice.hayDerecho())
          if(vertice == raiz)
            raiz = ultimoAgregado = null;
          else if(vertice.padre.izquierdo == vertice)
            vertice.padre.izquierdo = vertice.padre = null;
          else
            vertice.padre.derecho = vertice.padre = null;
        else if(vertice.hayIzquierdo())
          if(vertice == raiz){
            raiz = vertice.izquierdo;
            raiz.padre = null;
          }else{
            vertice.izquierdo.padre = vertice.padre;
            if(vertice.padre.izquierdo == vertice)
              vertice.padre.izquierdo = vertice.izquierdo;
            else
              vertice.padre.derecho = vertice.izquierdo;
          }
        else
          if(vertice == raiz){
            raiz = raiz.derecho;
            raiz.padre = null;
          }else{
            vertice.derecho.padre = vertice.padre;
            if(vertice.padre.izquierdo == vertice)
              vertice.padre.izquierdo = vertice.derecho;
            else
              vertice.padre.derecho = vertice.derecho;
          }
        elementos--;
    }
    protected Vertice busca(Vertice vertice, T elemento){
        if(vertice == null) return null;
        if(vertice.get().equals(elemento))return vertice;
        Vertice mi = busca(vertice.izquierdo, elemento);
        Vertice md = busca(vertice.derecho, elemento);
        return mi != null ? mi : md;
      }
    

    /**
     * Intercambia el elemento de un vertice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo mas
     * un hijo.
     * @param vertice un vertice con dos hijos distintos de <code>null</code>.
     * @return el vertice descendiente con el que vertice recibido se
     *         intercambia. El vertice regresado tiene a lo mas un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
    	  Vertice a = maximEnSubarbol(vertice.izquierdo);
          T e1 = a.elemento;
          T e2 = vertice.elemento;
          a.elemento = e2;
          vertice.elemento = e1;
          return a;
      }
      private Vertice maximEnSubarbol(Vertice v){
        while(v.hayDerecho()) v = v.derecho;
        return v;
      
    }

    /**
     * Elimina un vertice que a lo mas tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vertice a eliminar; debe tener a lo mas un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
   	  if(!vertice.hayPadre()){
          raiz = null;
          return;
        }
        if(!vertice.hayDerecho() && !vertice.hayIzquierdo()){
          Vertice padre = vertice.padre;
          if(padre.izquierdo == vertice){
            padre.izquierdo = null;
          }else{
            padre.derecho = null;
          }
        }else{
          Vertice aponer = vertice.hayDerecho() ? vertice.derecho : vertice.izquierdo;
          Vertice padre = vertice.padre;
          aponer.padre = padre;
          if(padre.izquierdo == vertice){
            padre.izquierdo = aponer;
            aponer.padre = padre;
          }else{
            padre.derecho = aponer;
            aponer.padre = padre;
          }
        }
      

    }

    /**
     * Busca un elemento en el arbol recorriendolo in-order. Si lo encuentra,
     * regresa el vertice que lo contiene; si no, regresa <tt>null</tt>.
     * @param elemento el elemento a buscar.
     * @return un vertice que contiene al elemento buscado si lo
     *         encuentra; <tt>null</tt> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
    	   if(elemento == null) return null;
           Vertice a = raiz;
           while(a != null)
             if(a.get().compareTo(elemento) == 0)
               return a;
             else if(elemento.compareTo(a.get()) < 0)
               a = a.izquierdo;
             else
               a = a.derecho;
           return null;
      
       
       }
       protected Vertice maximoEnSubarbol(Vertice vertice) {
           while (vertice.hayDerecho())
               vertice = vertice.derecho;
           return vertice;
           
          }

    /**
     * Regresa el vertice que contiene el ultimo elemento agregado al
     * arbol. Este metodo solo se puede garantizar que funcione
     * <em>inmediatamente</em> despues de haber invocado al metodo {@link
     * agrega}. Si cualquier operacion distinta a agregar sobre el arbol se
     * ejecuta despues de haber agregado un elemento, el comportamiento de este
     * metodo es indefinido.
     * @return el vertice que contiene el ultimo elemento agregado al arbol, si
     *         el metodo es invocado inmediatamente despues de agregar un
     *         elemento al arbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el arbol a la derecha sobre el vertice recibido. Si el vertice no
     * tiene hijo izquierdo, el metodo no hace nada.
     * @param vertice el vertice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (vertice == null || !vertice.hayIzquierdo())
            return;

        Vertice v = vertice(vertice);
        Vertice verticeIzq = v.izquierdo;

        verticeIzq.padre = v.padre;
        if(! esRaiz(v))
            if(esHijoIzquierdo(v))
                v.padre.izquierdo = verticeIzq;
            else
                v.padre.derecho = verticeIzq;
        else
            raiz = verticeIzq;

        v.izquierdo = verticeIzq.derecho;
        if(verticeIzq.hayDerecho())
            verticeIzq.derecho.padre = v;

        verticeIzq.derecho = v;
        v.padre = verticeIzq;
    
    }

    /**
     * Gira el arbol a la izquierda sobre el vertice recibido. Si el vertice no
     * tiene hijo derecho, el metodo no hace nada.
     * @param vertice el vertice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
  	  if (vertice == null || !vertice.hayDerecho())
          return;
      Vertice v = vertice(vertice);
      Vertice verticeDer = v.derecho;
      verticeDer.padre = v.padre;

      if (!esRaiz(v))
          if (esHijoIzquierdo(v))
              v.padre.izquierdo = verticeDer;
          else
              v.padre.derecho = verticeDer;
      else
          raiz = verticeDer;

      v.derecho = verticeDer.izquierdo;
      if (verticeDer.hayIzquierdo())
          verticeDer.izquierdo.padre = v;
      verticeDer.izquierdo = v;
      v.padre = verticeDer;
}

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el arbol, ejecutando la
     * accion recibida en cada elemento del arbol.
     * @param accion la accion a realizar en cada elemento del arbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {

        dfsPreOrder(accion, raiz);
     }
 protected void dfsPreOrder (AccionVerticeArbolBinario <T> accion , Vertice v) {
 	if (v == null)
 		return;
 	accion.actua(v);
 	dfsPreOrder(accion, v.izquierdo);
 	dfsPreOrder(accion , v.derecho);
 	
 }

    

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el arbol, ejecutando la
     * accion recibida en cada elemento del arbol.
     * @param accion la accion a realizar en cada elemento del arbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder (accion , raiz);
     }
protected void dfsInOrder(AccionVerticeArbolBinario <T> accion ,Vertice v) {
	
	if (v == null)
		return;
	dfsInOrder(accion, v.izquierdo);
	accion.actua(v);
	dfsInOrder(accion , v.derecho);
	
}

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el arbol, ejecutando la
     * accion recibida en cada elemento del arbol.
     * @param accion la accion a realizar en cada elemento del arbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder (accion , raiz);
    }

    protected void dfsPostOrder(AccionVerticeArbolBinario <T> accion ,Vertice v) {
    	
    	if (v == null)
    		return;
    	dfsPostOrder(accion, v.izquierdo);
    	
    	dfsPostOrder(accion , v.derecho);
    	accion.actua(v);
    }

    /**
     * Regresa un iterador para iterar el arbol. El arbol se itera en orden.
     * @return un iterador para iterar el arbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
