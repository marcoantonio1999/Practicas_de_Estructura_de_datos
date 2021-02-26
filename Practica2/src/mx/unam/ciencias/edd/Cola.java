package mx.unam.ciencias.edd;



/**
 * Clase para colas genericas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representacion en cadena de la cola.
     * @return una representacion en cadena de la cola.
     */
    @Override public String toString() {
    	Nodo n1 = cabeza;
        
    	String cadena = "";
        while (n1!= null ) {
        	
        	cadena += n1.elemento + ",";
        	    	n1 = n1.siguiente;
        	
        }
       
        return cadena + "";

    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
    	if (elemento == null)
    		throw new IllegalArgumentException();
    	
    	Nodo nodo = new Nodo(elemento);
    	if(rabo == null) {
    		rabo = cabeza = nodo;
    	} else{
            rabo.siguiente = nodo;
            rabo = rabo.siguiente;
    	}
 
    }
}
