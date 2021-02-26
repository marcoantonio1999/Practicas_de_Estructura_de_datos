package mx.unam.ciencias.edd;



/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<tt>null</tt>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices de árboles rojinegros. La única
     * diferencia con los vértices de árbol binario, es que tienen un campo para
     * el color del vértice.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
        	   super(elemento);
               this.color = Color.NINGUNO;
           	
           
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
        	if(raiz == null)return "";
            int[] arbin = new int[altura()];
            for(int i = 0; i < arbin.length; i++){
              arbin[i] = 0;
            }
            return toString(this, 0, arbin);
        }

        /**
          * metodo auxiliar para crear la representacion de un vertice
          * @param arbin arreglo binario, contiene informacion acerca de cuando dibujar una barra vertical
          * @return la representacion en cadena del vertice
          */
        private String toString(VerticeRojinegro v, int a, int[] arbin){
            String s = (v.color == Color.ROJO ? "R{" : "N{") + v.elemento.toString() + "}\n";
            if(v.hayIzquierdo() && v.hayDerecho()){
              arbin[a] = 1;
              s += dibujaEspacios(a,arbin);
              s += "├─›";
              s += toString((VerticeRojinegro)v.izquierdo, a+1, arbin);
              s += dibujaEspacios(a,arbin);
              s += "└─»";
              arbin[a] = 0;
              s += toString((VerticeRojinegro)v.derecho, a+1, arbin);
            }else if(v.hayIzquierdo()){
              s += dibujaEspacios(a,arbin);
        s += "└─›";
              arbin[a] = 0;
s += toString((VerticeRojinegro)v.izquierdo, a+1, arbin);
            }else if(v.hayDerecho()){
              s += dibujaEspacios(a,arbin);
              s += "└─»";
              arbin[a] = 0;
              s += toString((VerticeRojinegro)v.derecho, a+1, arbin);
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

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass())
                return false;
            @SuppressWarnings("unchecked")
            VerticeRojinegro vertice = (VerticeRojinegro)o;
            return (color == vertice.color && super.equals(o));
       }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() {
    	super();
        
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
        }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
       return new VerticeRojinegro(elemento);
    	   
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
    	VerticeRojinegro vr = (VerticeRojinegro)vertice;
    	return vr.color;
    	}

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
super.agrega(elemento);
        
    	VerticeRojinegro ver = (VerticeRojinegro) ultimoAgregado;
     
        //El algoritmo dice que al agregar simpre seran vertices ROJOS. 
        
    	ver.color = Color.ROJO;
        rebalanceoAgrega(ver);

    
    }
     
    
    private void rebalanceoAgrega(VerticeRojinegro vertice ) { 
    	VerticeRojinegro padre,abuelo,tio;
    	
    	/** caso 1
      	el vertice v tiene padre null: coloreamos a v de negro y terminamos 
      	*/
    if (vertice.padre == null) {
    	vertice.color = Color.NEGRO;
    return;
    
    }
    padre = (VerticeRojinegro)vertice.padre;
    /** caso 2
     el vertice p es negro: como el vertice v es rojo el arbol no se ha desbalanceado y terminamos  
    */  
    if(esNegro(padre))
    	return;
    
    abuelo = (VerticeRojinegro)padre.padre;
    
    
    if(abuelo != null) {
    	if(abuelo.derecho == padre)
    		tio = (VerticeRojinegro)abuelo.izquierdo;
    	else
    		tio = (VerticeRojinegro)abuelo.derecho;
    	
    
    
    /**caso 3 
     
     el vertice t es rojo :como tambien p es rojo ,
    coloreamos t y p de negro ,coloreamos a de rojo y hacemos recursion sobre a
   */
    
  if (tio != null && getColor(tio) == Color.ROJO) {
     padre.color =  Color.NEGRO;
     tio.color = Color.NEGRO;
     abuelo.color =  Color.ROJO;
     
     
     
     rebalanceoAgrega(abuelo);
     return;
 }
    
 /** --Caso 4---
  * El vertice y su padre estan cruzados.
  * Giramos sobre el padre en su direccion. 
  * Se cumple unicamente cuando solo uno es hijo izquierdo. */
 if (padre.izquierdo == vertice ^ abuelo.izquierdo == padre) {
     if (abuelo.izquierdo == padre)
       super.giraIzquierda(padre);
     else
        super.giraDerecha(padre);
     /**Intercambiamos el vertice con el padre, por que cuando se hace
     un giro el vertice es el padre y el padre ahora es el vertice.
     */
     VerticeRojinegro aux = vertice;
     vertice = padre;
     padre = aux;
    
 }
 /** --Caso 5---
 * Coloreamos al padre de NEGRO y al abuelo de ROJO, giramos sobre el
 * abuelo en direccion contraria del vertice. */
 padre.color = Color.NEGRO;
 abuelo.color = Color.ROJO;
 if (padre.izquierdo == vertice)
    super.giraDerecha(abuelo);
 if(padre.derecho == vertice)
     super.giraIzquierda(abuelo);
 
    }
 
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
       	VerticeRojinegro vertice = (VerticeRojinegro)(busca(raiz, elemento));
        VerticeRojinegro hijo, fantasma = null;

        if (vertice == null)
            return;
        if (vertice.hayIzquierdo()) {
            VerticeRojinegro aux = vertice;
            vertice = (VerticeRojinegro)(maximoEnSubarbol(vertice.izquierdo));
            aux.elemento = vertice.elemento;
        }
        if (esHoja(vertice)){
            fantasma = (VerticeRojinegro)(nuevoVertice(null));
            fantasma.color = Color.NEGRO;
            fantasma.padre = vertice;
            vertice.izquierdo = fantasma;
        }
        hijo = obtenerHijo(vertice);
        subirHijo(vertice);

        if(esNegro(vertice) && esNegro(hijo)){
            hijo.color = Color.NEGRO;
            rebalanceoElimina(hijo);
        }else 
            hijo.color = Color.NEGRO;

        mataFantasma(fantasma);

        elementos--;
    }

    private void subirHijo(VerticeRojinegro vertice){
        if (vertice.hayIzquierdo())
            //En este caso solamente sube y elimina el elemento que esta en la raiz.
            if (vertice == raiz) {
                raiz = vertice.izquierdo;
                raiz.padre = null;
            } else {
                //El que se quiere elimiar esta entre vertices.
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

    private void mataFantasma(VerticeRojinegro fantasma){
        if(fantasma != null)
            if(esRaiz(fantasma))
                raiz = ultimoAgregado = fantasma = null;
            else
                if(esHijoIzquierdo(fantasma))
                    fantasma.padre.izquierdo = null;
                else
                    fantasma.padre.derecho = null;
    }

    private void rebalanceoElimina(VerticeRojinegro vertice) {
        VerticeRojinegro padre, hermano, sobrinoIzq, sobrinoDer, aux;
        /** --Caso 1--
         * El padre es null.
         * Terminamos. */
        if (vertice.padre == null){
            vertice.color = Color.NEGRO;
            raiz = vertice;
            return;
        }
        padre = (VerticeRojinegro)vertice.padre;
        hermano = obtenerHermano(vertice);
        /** --Caso 2--
         * El hermano es rojo.
         * Coloreamos al hermano de NEGRO, al padre de ROJO, y  giramos
         * sobre el padre en la direccion del vertice. */
        if (!esNegro(hermano)) {
            hermano.color = Color.NEGRO;
            padre.color = Color.ROJO;
            
            if (esHijoIzquierdo(vertice))
                super.giraIzquierda(padre);
            else
                super.giraDerecha(padre);
            //Como se hizo un giro se deben de actualizar las referencias.
            padre = (VerticeRojinegro)(vertice.padre);
            hermano = obtenerHermano(vertice);
        }
        sobrinoIzq = (VerticeRojinegro)(hermano.izquierdo);
        sobrinoDer = (VerticeRojinegro)(hermano.derecho);
        /** --Caso 3--
         * El padre, el hermano y los hijos del hermano son negros.
         * Coloreamos al hermano de ROJO, recursamos sobre el padre y terminamos */
        if (esNegro(padre) && esNegro(hermano) && sobrinosNegros(sobrinoIzq, sobrinoDer)) {
            hermano.color = Color.ROJO;
            rebalanceoElimina(padre);
            return;
        }
        /** --Caso 4--
         * El hermano y los sobrinos son negros, y el padre es ROJO.
         * Coloreamos al padre de NEGRO, al hermano de ROJO y terminamos. */
        if (esNegro(hermano) && sobrinosNegros(sobrinoIzq, sobrinoDer) && !esNegro(padre)) {
            padre.color = Color.NEGRO;
            hermano.color = Color.ROJO;
            return;
        }
        /** --Caso 5--
         * Los sobrinos son bicoloreados cruzados.
         * Coloreamos al sobrino ROJO de NEGRO, hermano de ROJO y giramos sobre 
         * el hermano en la direccion contraria al vertice. */
        if (sonVerticesBicoloreados(sobrinoIzq, sobrinoDer) && sonSobrinoCruzados(vertice, sobrinoIzq, sobrinoDer)) {
            if(!esNegro(sobrinoIzq))
                sobrinoIzq.color = Color.NEGRO;
            else
                sobrinoDer.color = Color.NEGRO;

            hermano.color = Color.ROJO;

            if(esHijoIzquierdo(vertice))
                super.giraDerecha(hermano);
            else
                super.giraIzquierda(hermano);
            //Se prepara para el caso 6.
            hermano = obtenerHermano(vertice);
            sobrinoIzq = (VerticeRojinegro)(hermano.izquierdo);
            sobrinoDer = (VerticeRojinegro)(hermano.derecho);
        }
        /** --Caso 6--
         * El sobrino cruzado es ROJO.
         * Coloreamos al hermano de color del padre, al padre de NEGRO,
         * al sobrino cruzado de NEGRO y giramos sobre padre en la
         * direccion del vertice */
        hermano.color = padre.color;
        padre.color = Color.NEGRO;

        if(esHijoIzquierdo(vertice))
            sobrinoDer.color = Color.NEGRO;
        else
            sobrinoIzq.color = Color.NEGRO;

        if(esHijoIzquierdo(vertice))
            super.giraIzquierda(padre);
        else
            super.giraDerecha(padre);
    }

    private VerticeRojinegro obtenerHijo(VerticeRojinegro vertice){
        if(vertice.hayIzquierdo())
            return (VerticeRojinegro)(vertice.izquierdo);
        return (VerticeRojinegro)(vertice.derecho);
    }

    private boolean sonSobrinoCruzados(VerticeRojinegro vertice, VerticeRojinegro sobrinoIzq, VerticeRojinegro sobrinoDer) {
        return esNegro(sobrinoIzq) && esHijoDerecho(vertice) || esNegro(sobrinoDer) && esHijoIzquierdo(vertice);
    }

    private boolean sonVerticesBicoloreados(VerticeRojinegro a, VerticeRojinegro b) {
        return esNegro(a) ^ esNegro(b);
    }

    private boolean esNegro(VerticeRojinegro vertice) {
        return vertice == null || vertice.color == Color.NEGRO;
    }

    private boolean sobrinosNegros(VerticeRojinegro sobrinoIzq, VerticeRojinegro sobrinoDer) {
        return esNegro(sobrinoIzq) && esNegro(sobrinoDer);
    }

    private VerticeRojinegro obtenerHermano(VerticeRojinegro vertice) {
        if (esHijoIzquierdo(vertice))
            return (VerticeRojinegro)(vertice.padre.derecho);
        return (VerticeRojinegro)(vertice.padre.izquierdo);
   }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
