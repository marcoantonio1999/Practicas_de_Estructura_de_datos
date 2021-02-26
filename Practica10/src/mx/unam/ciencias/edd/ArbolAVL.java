package mx.unam.ciencias.edd;

import mx.unam.ciencias.edd.ArbolAVL.VerticeAVL;
import mx.unam.ciencias.edd.ArbolBinario.Vertice;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles AVL. La única diferencia
     * con los vértices de árbol binario, es que tienen una variable de clase
     * para la altura del vértice.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
        	super(elemento);
               altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
        	return altura;
        	}

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
        	if(raiz == null) return "";
        	            int[] arbin = new int[altura()];
        	            for(int i = 0; i < arbin.length; i++){
        	              arbin[i] = 0;
        	            }
        	            return toString(this, 0, arbin);
        	//return elemento.toString()+ " " + altura + "/" + daBalance(this);
        	//
        	//      
        	//        }
        	//        
        	//        public int calculaAltura(VerticeAVL v) {
        	//        	if (v == null)
        	//        		return -1;
        	//        	return 1 + (int)Math.max(calculaAltura((VerticeAVL)(v.izquierdo)), calculaAltura((VerticeAVL)(v.derecho)));
        	//        	
        	        }        
        	        
        	        
        	        private String toString(VerticeAVL v, int a, int[] arbin){
        	            String s = v.elemento.toString() + " " + v.altura + "/" + daBalance(v) + "\n";
        	            if(v.hayIzquierdo() && v.hayDerecho()){
        	              arbin[a] = 1;
        	              s += dibujaEspacios(a,arbin);
        	              s += "├─›";
        	              s += toString((VerticeAVL)v.izquierdo, a+1, arbin);
        	              s += dibujaEspacios(a,arbin);
        	              s += "└─»";
        	              arbin[a] = 0;
        	              s += toString((VerticeAVL)v.derecho, a+1, arbin);
        	            }else if(v.hayIzquierdo()){
        	              s += dibujaEspacios(a,arbin);
        	              s += "└─›";
        	              arbin[a] = 0;
        	              s += toString((VerticeAVL)v.izquierdo, a+1, arbin);
        	            }else if(v.hayDerecho()){
        	              s += dibujaEspacios(a,arbin);
        	              s += "└─»";
        	              arbin[a] = 0;
        	              s += toString((VerticeAVL)v.derecho, a+1, arbin);
        	            }
        	           return s;
        	         }
        	        
        	        private int daBalance(VerticeAVL v) {
        	        	return getAlturaCalculada((VerticeAVL)v.izquierdo) - getAlturaCalculada((VerticeAVL)(v.derecho));
        	            
        			}
        	 
        			private String dibujaEspacios(int a, int[] arbin){
        	    	    String s = "";
        	    	    for(int i = 0; i < a; i++)
        	    		if(arbin[i] == 1)
        	    		    s += "│  ";
        	    		else
        	    		    s += "   ";
        	    	    return s;
        	   }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)o;
            return (altura == vertice.altura && super.equals(o));
            }
    }
    public int daBalance(VerticeAVL v) {
    	    	
    	    	return getAlturaCalculada((VerticeAVL)v.izquierdo) - getAlturaCalculada((VerticeAVL)(v.derecho));
    	    
    	    }
    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
     super();
     }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
       super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
    	super.agrega(elemento);
    	       rebalanceo((VerticeAVL)ultimoAgregado);
    	    }
    	private void rebalanceo (VerticeAVL vertice) {
    		if (vertice == null) 
    			return;
    		cambiaAltura(vertice);
    	
    	    if (balance(vertice) == -2) {
    	        if (balance((VerticeAVL)(vertice.derecho)) == 1)
    	            giraDerechaAVL((VerticeAVL)(vertice.derecho));
    	
    	        giraIzquierdaAVL(vertice);
    	    } else if (balance(vertice) == 2) {
    	        if (balance((VerticeAVL)(vertice.izquierdo)) == -1)
    	            giraIzquierdaAVL((VerticeAVL)(vertice.izquierdo));
    	
    	        giraDerechaAVL(vertice);
    	     }
    	    
    	    rebalanceo((VerticeAVL)(vertice.padre));
    	}
    	
    	private void cambiaAltura(VerticeAVL vertice) {
    	    vertice.altura = getAlturaCalculada(vertice);
    	}
    	public int balance(VerticeAVL vertice) {
    	    return getAltura((VerticeAVL)(vertice.izquierdo)) - getAltura((VerticeAVL)(vertice.derecho));
    
    	    
    	}
    	/**
    	 * Regresa la altura del vértice AVL.
    	 * @param vertice el vértice del que queremos la altura.
    	 * @return la altura del vértice AVL.
    	 * @throws ClassCastException si el vértice no es instancia de {@link
    	 *         VerticeAVL}.
    	 */
    	public int getAltura(VerticeArbolBinario<T> vertice) {
    	if (vertice == null)
    		return -1;
    	return vertice.altura();
    	
    	
    	
    	//   return vertice == null ? -1 : (VerticeAVL) vertice.altura;
    	}
    	
    	private int getAlturaCalculada(VerticeAVL vertice) {
    	    if (vertice == null)return -1;
    		return 1 + Math.max(getAltura((VerticeAVL)(vertice.izquierdo)), getAltura((VerticeAVL)(vertice.derecho)));
    	}
    	
    	private void giraIzquierdaAVL(VerticeAVL vertice){
    	    super.giraIzquierda(vertice);
    	    cambiaAltura(vertice);
    	    cambiaAltura((VerticeAVL)(vertice.padre));
    	}
    	
    	private void giraDerechaAVL(VerticeAVL vertice){
    	    super.giraDerecha(vertice);
    	    cambiaAltura(vertice);
        cambiaAltura((VerticeAVL)(vertice.padre));
    
    	}
        /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
    	VerticeAVL vertice = (VerticeAVL)(busca(raiz, elemento));
    	
    	        if (vertice == null)
    	            return;
    	        if (vertice.hayIzquierdo()) {
    	            VerticeAVL aux = vertice;
    	           vertice = (VerticeAVL)(maximoEnSubarbol(vertice.izquierdo));
    	            aux.elemento = vertice.elemento;
    	        }
    	        if (esHoja(vertice))
    	            eliminaHoja(vertice);
    	        else
    	            subirHijo(vertice);
    	        
    	        rebalanceo((VerticeAVL)(vertice.padre));
    	        elementos--;
    	    }
    	    	    private void eliminaHoja(VerticeAVL vertice) {
    	        if (vertice == raiz)
    	            raiz = ultimoAgregado = null;
    	        else if (esHijoIzquierdo(vertice))
    	            vertice.padre.izquierdo = null;
    	        else
    	            vertice.padre.derecho = null;
    	    }
    	    	    private void subirHijo(VerticeAVL vertice) {
    	    	    	        if (vertice.hayIzquierdo())
    	    	    	            if (vertice == raiz) {
    	    	    	                raiz = vertice.izquierdo;
    	    	    	                raiz.padre = null;
    	    	    	            } else {
    	    	    	                vertice.izquierdo.padre = vertice.padre;
    	    	    	               if (esHijoIzquierdo(vertice))
    	    	    	                    vertice.padre.izquierdo = vertice.izquierdo;
    	    	    	                else
    	    	    	                   vertice.padre.derecho = vertice.izquierdo;
    	    	    	            }
    	    	    	        else
    	    	    	            if (vertice == raiz) {
    	    	    	               raiz = raiz.derecho;
    	    	    	                raiz.padre = null;
    	    	    	            } else {
    	    	    	                vertice.derecho.padre = vertice.padre;
    	    	    	                if (esHijoIzquierdo(vertice))
    	    	    	                    vertice.padre.izquierdo = vertice.derecho;
    	    	    	                else
    	    	    	                    vertice.padre.derecho = vertice.derecho;
    	    	    	            }    
}

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
