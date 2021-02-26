package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

import mx.unam.ciencias.edd.ArbolBinario.Vertice;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        public T elemento;
        /** El padre del vértice. */
        public Vertice padre;
        /** El izquierdo del vértice. */
        public Vertice izquierdo;
        /** El derecho del vértice. */
        public Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public Vertice(T elemento) {
        	this.elemento =  elemento;
            
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <tt>true</tt> si el vértice tiene padre,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayPadre() {
        	if (padre != null)
            	return true;
            return false;
     
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <tt>true</tt> si el vértice tiene izquierdo,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
        	 if (izquierdo != null)
               	return true;
               return false;
           
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <tt>true</tt> si el vértice tiene derecho,
         *         <tt>false</tt> en otro caso.
         */
        @Override public boolean hayDerecho() {
        	if(derecho != null)
            	return true;
            return false;
       
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
        	if (padre == null)
            	throw new NoSuchElementException();
            return padre;
           
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
          	if (izquierdo == null)
                throw new NoSuchElementException() ;
                return izquierdo;  
         
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
        	  if (derecho == null)
                	throw new NoSuchElementException ();
      return derecho;
         
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
        	
        	if (raiz == null)
            	return -1 ;
            return 1+ Math.max(hayDerecho() ? derecho.altura() : -1 , hayIzquierdo() ? izquierdo.altura(): -1);
        
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
        	if (this == raiz)
            	return 0;
            return 1 + (padre.profundidad());
        
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
        	  return elemento;
         	 
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)o;
            return raiz.get().equals(vertice.get()) && equals(raiz.izquierdo, vertice.izquierdo)
                    && equals(raiz.derecho, vertice.derecho);
        
        }
        private boolean equals(Vertice i, Vertice d) {
            
            if (i == null && d == null)
                return true;
          
            else if (i != null && d == null || i == null && d != null)
                return false;
                     return i.get().equals(d.get()) && equals(i.izquierdo, d.izquierdo)
                && equals(i.derecho, d.derecho);
     }

        

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        public String toString() {
        	if(altura() == 0) return elemento.toString();
            int[] arbin = new int[altura()];
            for(int i = 0; i < arbin.length; i++){
              arbin[i] = 0;
            }
            return toString(this, 0, arbin);
        
        }
    
        private String toString(Vertice v, int a, int[] arbin){
            String s = v.elemento.toString() + "\n";
            if(v.hayIzquierdo() && v.hayDerecho()){
              arbin[a] = 1;
              s += dibujaEspacios(a,arbin);
              s += "├─›";
              s += toString(v.izquierdo, a+1, arbin);
              s += dibujaEspacios(a,arbin);
              s += "└─»";
              arbin[a] = 0;
              s += toString(v.derecho, a+1, arbin);
            }else if(v.hayIzquierdo()){
              s += dibujaEspacios(a,arbin);
              s += "└─›";
              arbin[a] = 0;
              s += toString(v.izquierdo, a+1, arbin);
            }else if(v.hayDerecho()){
              s += dibujaEspacios(a,arbin);
              s += "└─»";
              arbin[a] = 0;
              s += toString(v.derecho, a+1, arbin);
            }
            return s;
        }

        /**
          * metodo auxiliar que dibuja los espacios en un arbol
          * @param a altura del vertice
          * @param arbin arreglo binario
          * @return los espacios a agregar en la cadena
          */
        private String dibujaEspacios(int a, int[] arbin){
          String s = "";
          for(int i = 0; i < a; i++)
            if(arbin[i] == 1)
              s += "│  ";
            else
              s += "   ";
          return s;
        }

		
}

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
    	for (T e : coleccion)
            agrega(e);
    
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
    	if (raiz == null)   
  	       return -1;
  	    	return raiz.altura();
  	       
     }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
return elementos;
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	  if(raiz == null) return false;
         	return busca (elemento ) != null;
         
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <tt>null</tt>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <tt>null</tt> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
    	return busca(raiz, elemento);
    }
    protected Vertice busca(Vertice vertice, T elemento) {
        if (vertice == null)
            return null;
        if (vertice.get().equals(elemento))
        	
            return vertice;
        Vertice mi = busca(vertice.izquierdo, elemento);
        Vertice md = busca(vertice.derecho, elemento);
        return mi != null ? mi : md;
    
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
     	if (raiz == null)
   		 throw new NoSuchElementException();
   	 return raiz;

    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
    	   return raiz == null;
    	   
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
        elementos = 0;
  
    }

    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked")
        ArbolBinario<T> arbol = (ArbolBinario<T>)o;
        return esVacia() || raiz.equals(arbol.raiz);

    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
    	if(raiz == null) return "";
        return raiz.toString();
    	 
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        return (Vertice)vertice;
    }
 // metodos para decirnos si es raiz, hijo izquierdo, hijo derecho o si es hoja
    protected boolean esRaiz(Vertice vertice){
        return vertice == raiz;
    }    
    protected boolean esHijoIzquierdo(Vertice vertice){
        if(!vertice.hayPadre())
            return false;
        return vertice.padre.izquierdo == vertice;        
    }

    protected boolean esHijoDerecho(Vertice vertice){
        if(!vertice.hayPadre())
            return false;
        return vertice.padre.derecho == vertice;        
    }
    protected boolean esHoja(Vertice vertice){
        return !vertice.hayDerecho() && !vertice.hayIzquierdo();
    }
}


