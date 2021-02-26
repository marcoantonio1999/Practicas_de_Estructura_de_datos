package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase privada para iteradores de montículos. */
    private class Iterador implements Iterator<T> {

        /* Indice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return indice < arbol.length && arbol[indice] != null;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
        	if(hasNext()){
        		            return arbol[indice++];
        		          }
        		          throw new NoSuchElementException();
        		         
        }
    }

    /* Clase estática privada para poder implementar HeapSort. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
        	  this.elemento = elemento;
        	           indice = -1;
        	          
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
        	 return indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
        	 this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
        	 return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
    	 this.arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
    	  this(coleccion,coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
    	      arbol = nuevoArreglo(n);
    	      elementos = n;
    	      int nuevoIndice = 0;
    	      for(T e:  iterable){
    	        arbol[nuevoIndice] = e;
    	        arbol[nuevoIndice].setIndice(nuevoIndice++);
    	      }
    	      for(int i = (int)Math.floor((elementos)/2); i > -1; i--){
    	     acomodaAbajo(arbol[i]);
    	      }

    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
    	 T[] nuevoArbol;
    	       int j = 0;
    	       if(elementos >= arbol.length){
    	         nuevoArbol = arbol;
    	         arbol = nuevoArreglo(arbol.length * 2);
    	         for(T e: nuevoArbol){
    	      
    	         	arbol[j++] = e;
    	         }
    	       
    	       }
    	       arbol[elementos] = elemento;
    	      
    	       elemento.setIndice(elementos++);
    	       
    	       acomodaArriba(elemento);
    	      
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
    	  if (esVacia()) {
    		      	  
    		        
    		          throw new IllegalStateException();
    		        }
    		        T min;
    		        min = arbol[0];
    		        
    		        intercambia(arbol[0], arbol[--elementos]);
    		       
    		        arbol[elementos].setIndice(-1);
    		        arbol[elementos] = null;
    		        acomodaAbajo(arbol[0]);
    		        return min;
    		       
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
    	  int aux = elemento.getIndice();
    	       intercambia(elemento, arbol[--elementos]);
    	       arbol[elementos] = null;
    	       elemento.setIndice(-1);
    	       reordena(arbol[aux]);
    	       
    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	  if(elemento == null) {
    		          return false;
    		        }
    		        for (T a : arbol){
    		          if(a.equals(elemento)) {
    		            return true;
    		          }
    		          
    		        }
    		        return false;
    		       
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <tt>true</tt> si ya no hay elementos en el montículo,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
    	  for(int i =0; i < elementos; i++){
    		          arbol[i] = null;
    		        
    		        }
    		        elementos = 0;
    		       
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
    	 if (elemento == null) {
    		     	  
    		       
    		         return;
    		       }
    		         
    		         acomodaAbajo(elemento);
    		       
    		         acomodaArriba(elemento);
    		      
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
    	  return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
    	 if(!indiceValido(i)) {
    		         throw new NoSuchElementException();
    		       }
    		         return arbol[i];
    		      
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
     	String s = "";
     	      
     	   	for (int i = 0; i < elementos; i++) {
     	       
     	    		s += String.format("%s, ", get(i).toString());
     	     }
     	     
     	    	return s;
     	    	      	     	      
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param o el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)o;
        for(int i = 0; i < arbol.length; i++){
        	          for (int j = 0; i < monticulo.arbol.length; j++){
        	            if(arbol[i].equals(monticulo.arbol[i])) {
        	              break;
        	           
        	            }else
        	              return false;
        	          }
        	        }
        	        return true;
        	   
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
      	Lista<Adaptador<T>> adaptador = new Lista<>();
      	      
      	Lista<T> ordenada = new Lista<>();
      	      for(T e : coleccion){
      	        Adaptador<T> adaptadores = new Adaptador<>(e);
      	        adaptador.agrega(adaptadores);
      	      }
      	      MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<Adaptador<T>>(adaptador);
      	      while(!monticulo.esVacia()){
      	        T elemento = monticulo.elimina().elemento;
      	        ordenada.agrega(elemento);
      	      }
      	      return ordenada;
      	    }
      	
      	
      	
      	    private void acomodaAbajo(T elemento){
      	
      	      if(elemento == null) {
      	        return;
      	      }
      	
      	      int Izq = 2*elemento.getIndice() +1;
      	      int Der = 2*elemento.getIndice() + 2;
      	      int hijoMen;
      	
      	      if(!indiceValido(Izq) && (!indiceValido(Der))) {
      	        return;
      	
      	      }
      	      hijoMen = Der;
      	
      	      if(indiceValido(Izq)){
      	        if(indiceValido(Der)){
      	          if(arbol[Izq].compareTo(arbol[Der]) < 0) {
      	            hijoMen = Izq;
      	            
      	          }
      	        }
      	
      	       else {
      	        hijoMen = Izq;
      	      }
      	    }
      	
      	      if(arbol[hijoMen].compareTo(elemento) < 0){
      	        intercambia(elemento, arbol[hijoMen]);
      	        acomodaAbajo(elemento);
      	      }
      	    }
      	
      	
      	    private boolean indiceValido(int i){
      	      return !(i < 0 || i >= elementos || arbol[i] == null);
      	    }
      	
      	
      	    private void intercambia (T i, T j){
      	      int aux = j.getIndice();
      	      arbol[i.getIndice()] = j;
      	      arbol[j.getIndice()] = i;
      	      j.setIndice(i.getIndice());
      	      i.setIndice(aux);
      	    }
      	    private void acomodaArriba(T elemento){
      	        int p = (elemento.getIndice()-1);
      	        if(p != -1){
      	          p = (int)Math.floor(p/2);
      	        }
      	        if((!indiceValido(p)) || arbol[p].compareTo(elemento) < 0)
      	          return;
      	        intercambia(arbol[p], elemento);
      	        acomodaArriba(elemento);
      	      }
}