package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genericos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciacion. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void intercambia(T[] arreglo,int a , int b) {
    	T pico = arreglo[a];
    	T navajeo = arreglo[b];
    	
    	arreglo[a] = navajeo;
    	arreglo[b] = pico;
    
    
    }
    
    public static <T> void  quickSort(T[] arreglo, Comparator<T> comparador) {
    	Cola <Integer> c1 = new Cola<>();
    	c1.mete(0);
    	c1.mete(arreglo.length -1);
    	
    	while(!c1.esVacia()) {
    		
    		
    		int ini = c1.saca();
    		
    		int fin = c1.saca();
    		if (fin <= ini) continue;
    		int i= ini +1 , j = fin;
    		while (i<j) {
    			if (comparador.compare(arreglo[i], arreglo[ini])>0 && comparador.compare(arreglo[j],arreglo[ini]) <= 0) {
    				
    				intercambia(arreglo, i++,j--);
    				
    			}
    			else if (comparador.compare(arreglo[i], arreglo[ini]) <= 0)
    				i++;
    			else j--;
    			
    		
    	}
    		if (comparador.compare(arreglo[i], arreglo[ini]) > 0)
    			i--;
    		intercambia (arreglo,ini,i);
    		c1.mete(ini);
    	    c1.mete(i -1);
    	
c1.mete(i+1);
c1.mete(fin);
    	}

    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void  selectionSort(T[] arreglo, Comparator<T> comparador) {
    	for (int i = 0 ;i<arreglo.length ; i++ ){
        	int m = i;
        	
    		for (int j = i+1 ; j<arreglo.length ; j++)
if (comparador.compare(arreglo[j], arreglo[m] )< 0)
	m = j;
    		intercambia(arreglo,i,m);
        }
        	

    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una busqueda binaria del elemento en el arreglo. Regresa el indice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo donde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la busqueda.
     * @return el indice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
    	int n  =arreglo.length -1;
   	 int centro,inf = 0, sup = n;
   	  while (inf <= sup) {
   		  centro = (sup + inf)/2;
   		   if (comparador.compare(arreglo[centro], elemento)==0) return centro ;
   		   else if ( comparador.compare(arreglo[centro], elemento)> 0) {
   			    sup = centro -1;
   			    
   		   }
   		   else {
   			   inf = centro +1;
   			   
   		   }
   	  }
   	  return -1;

    }

    /**
     * Hace una busqueda binaria del elemento en el arreglo. Regresa el indice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el indice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
