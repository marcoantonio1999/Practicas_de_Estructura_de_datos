package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import mx.unam.ciencias.edd.MeteSaca.Nodo;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase Nodo privada para uso interno de la clase Lista. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            this.siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null ;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (siguiente == null)
            {
            throw new NoSuchElementException();
            }
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
           if (anterior == null )
           {
        	   throw new NoSuchElementException();
           }
        siguiente = anterior;
        anterior = anterior.anterior;
        return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
            anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null) {
            throw  new IllegalArgumentException();}
        Nodo n = new Nodo(elemento);
        if (cabeza == null) 
        {
        	
        			rabo = cabeza = n;			
        }
        else 
        {
        	rabo.siguiente = n;
        	n.anterior = rabo;
        	rabo = n;
        }
        longitud ++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
    agrega(elemento);
    }
    

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
    	if (elemento == null) {
            throw  new IllegalArgumentException();}
    	Nodo n = new Nodo(elemento);
    	if (longitud == 0) 
    
        {
    
        			cabeza = rabo = n;			
        }
        else 
        {
        	cabeza.anterior = n;
        n.siguiente = cabeza;
        	cabeza = n;
        }
        longitud ++;
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null)
        	throw new IllegalArgumentException();
        	if(i >= getLongitud() ) 
        	{
        		
        		agregaFinal(elemento);
        		return;
        	}
    
    if (i <= 0) 
    {
    agregaInicio(elemento);	
    return;
    
    }
    	
    int contador = 0;
    Nodo e = new Nodo(elemento);
    Nodo t = cabeza;
    while(t != null) {
    	if (contador == i) {
    		Nodo b = t.anterior;
    		t.anterior = e;
    		e.siguiente = t;
    		b.siguiente = e;
    		e.anterior = b;
    		longitud ++;
    		break;
    		
    	}
    t = t.siguiente;
    contador ++;
    }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    
    private Nodo buscaNodo(Nodo a, T elemento) {
    if (a == null)
    	return null;
    if(a.elemento.equals(elemento))
    	return a;
    return buscaNodo(a.siguiente,elemento);
    
    }
    
    
    
    @Override public void elimina(T elemento) {
        Nodo nodo = buscaNodo(cabeza,elemento);
        if (nodo == null) {
        	return;
        }else if (cabeza == rabo) {
        	cabeza = rabo = null;
        }else if (cabeza == nodo) {
        		
        	cabeza = cabeza.siguiente;
        	cabeza.anterior = null;
        }else if (rabo==nodo) {
        	rabo = rabo.anterior;
        	rabo.siguiente = null;
        }else {
        	nodo.siguiente.anterior = nodo.anterior;
        	nodo.anterior.siguiente = nodo.siguiente;
        }
        longitud --;
        
    }	

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(cabeza == null)
        	throw new NoSuchElementException();
         if (cabeza == rabo) {
        	 T elemento = cabeza.elemento;
         cabeza = rabo = null;
         longitud --;
         return elemento ;
    }
    T elemento = cabeza.elemento;
    cabeza = cabeza.siguiente;
    cabeza.anterior = null;
    longitud --;
    return elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (cabeza == null)
      throw new   	NoSuchElementException();
        if (cabeza == rabo) {
        	T elemento = rabo.elemento;
        	cabeza = rabo = null;
        	longitud --;
        	return elemento;
        	
        }
    T elemento = rabo.elemento;
    rabo = rabo.anterior;
    rabo.siguiente = null;
    longitud --;
    return elemento;
    
    
    
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for(T t : this) {
        	if(t.equals(elemento))
        		return true;
        	
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<>();
    for(T t : this) {
    	lista.agregaInicio(t);
    	
    }
    return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
    	 Lista<T> lista = new Lista<>();
    	    for(T t : this) {
    	    	lista.agregaFinal(t);
    	    	
    	    }
    	    return lista;

    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        cabeza = null;
        rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(cabeza == null) 
        	throw new NoSuchElementException();
        	return cabeza.elemento;
        
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
    	if(cabeza == null)
        	throw new NoSuchElementException();
        	return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        int contador = 0;
        for(T t:this) {
        	if(contador == i) 
        		return t;
        		contador ++;
        	
        
        }
    throw new ExcepcionIndiceInvalido();
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si
     *         el elemento no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
    	
    	
  
    	int contador = 0;
        for(T t:this) {
        	if (t.equals(elemento))
        		return contador;
        contador ++;
    }
return -1;
}
    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        String cadena = "[";
        for(T t: this) {
        	cadena += cadena.equals("[") ? t.toString() : ", " + t.toString();
        
        }
        return cadena + "]";
        }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;
        if (this.longitud != lista.getLongitud())return false;
        Nodo a1 = cabeza;
        Nodo a2 = lista.cabeza;
        while (a1 != null && a2 != null) {
        if (!a1.elemento.equals(a2.elemento))	return false ;
        a1 = a1.siguiente;
        a2 = a2.siguiente;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public  static <T> Lista<T> merge(Lista <T> i, Lista <T> d,Comparator <T> c){
   	 
        int n1 = i.getLongitud();
        int n2 = d.getLongitud();
 
        
        int L[] = new int [n1];
        int R[] = new int [n2];
 
 
 Lista<T> list = new Lista<>();
   
  
        int n  = 0, j = 0;
 
 
        int k = 0;
        while (n <n1 && j < n2)
        { 
            if (c.compare(i.get(n), d.get(j)) <= 0)
            {
                list.agrega(i.get(n));
                n ++;
                
            }
            else
            {
               list.agrega(d.get(j));;
                j++;
            }
            
        }
 
  
        while (n<n1)
        {
       	 list.agrega(i.get(n));
            n++;
            
        }
 
        
        while (j < n2)
        {
       	 list.agrega(d.get(j));
            j++;
            
        }
   return list;
   }

    
public Lista<T> mergeSort(Comparator<T> comparador) {
    	
    	
    	if(cabeza == rabo) return copia();
        Lista<T> li = new Lista<>();
        Lista<T> ld = new Lista<>();
        int c = 0;
        for (T el : this) {
             
            Lista<T> ll = (c++ < getLongitud()/2) ? li : ld;
            ll.agrega(el);
        }
        return merge(li.mergeSort(comparador), ld.mergeSort(comparador), comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergeSort(Lista<T> lista) {
    	return lista.mergeSort((a,b )-> a.compareTo(b));  
      }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <tt>true</tt> si elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
    	
    	Lista<T> list = mergeSort(comparador); 
    			for (T el :list )
         if (comparador.compare(el, elemento)== 0) {
        return true;
        } 
    			return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <tt>true</tt> si el elemento está contenido en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
