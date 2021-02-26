package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;



/**
 * Clase abtracta para estructuras lineales restringidas a operaciones
 * mete/saca/mira.
 */
public abstract class MeteSaca<T> {

    /**
     * Clase Nodo protegida para uso interno de sus clases herederas.
     */
    protected class Nodo {
        /** El elemento del nodo. */
        public T elemento;
        /** El siguiente nodo. */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
        	  this.elemento = elemento;
        }
    }

    /** La cabeza de la estructura. */
    protected Nodo cabeza;
    /** El rabo de la estructura. */
    protected Nodo rabo;

    /**
     * Agrega un elemento al extremo de la estructura.
     * @param elemento el elemento a agregar.
     */
    public abstract void mete(T elemento);

    /**
     * Elimina el elemento en un extremo de la estructura y lo regresa.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura esta  vacia.
     */
    public T saca() {
     	if(cabeza == null) throw new NoSuchElementException();
        Nodo a = cabeza;
        if(cabeza == rabo)
            cabeza = rabo = null;
        else
            cabeza = cabeza.siguiente;
        return a.elemento;


    }

    /**
     * Nos permite ver el elemento en un extremo de la estructura, sin sacarlo
     * de la misma.
     * @return el elemento en un extremo de la estructura.
     * @throws NoSuchElementException si la estructura esta vacia.
     */
    public T mira() {
    	  if (cabeza == null)
            	throw new NoSuchElementException ();
        	
        	return cabeza.elemento;
        
    }

    /**
     * Nos dice si la estructura esta vacia.
     * @return <tt>true</tt> si la estructura no tiene elementos,
     *         <tt>false</tt> en otro caso.
     */
    public boolean esVacia() {
    	return cabeza==null;
    }

    /**
     * Compara la estructura con un objeto.
     * @param o el objeto con el que queremos comparar la estructura.
     * @return <code>true</code> si el objeto recibido es una instancia de la
     *         misma clase que la estructura, y sus elementos son iguales en el
     *         mismo orden; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") MeteSaca<T> m = (MeteSaca<T>)o;
        return this.toString().equals(m.toString());
    }
}
