package mx.unam.ciencias.edd;



/**
 * Clase para pilas genericas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representacion en cadena de la pila.
     * @return una representacion en cadena de la pila.
     */
    @Override public String toString() {
    	if (cabeza== null)
    		return "";
    	
    	Nodo n1 = cabeza;
        
    	String cadena = "";
        while (n1!= null ) {
        	
        	cadena += n1.elemento.toString() + "\n";
        	    	n1 = n1.siguiente;
        	
        }
        
        return cadena;    
    
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
    	if (elemento == null) throw new IllegalArgumentException(); 
    	Nodo nodo = new Nodo(elemento);
    	if(cabeza == null) {
    		cabeza = rabo = nodo;
    	} else{
    		nodo.siguiente = cabeza;
    		cabeza = nodo;
    }
    }
}
