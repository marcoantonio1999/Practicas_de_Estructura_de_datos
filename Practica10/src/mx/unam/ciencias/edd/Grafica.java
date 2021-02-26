package mx.unam.ciencias.edd;

import java.util.Iterator; 


import java.util.NoSuchElementException;



/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase privada para iteradores de gráficas. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Vertices para gráficas; implementan la interfaz ComparableIndexable y
     * VerticeGrafica */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
this.elemento = elemento;
this.color = 	Color.NINGUNO;
this.vecinos = new Lista<>(); 
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
           return elemento; 
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
        return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
this.indice = indice;            
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
        	if (distancia > vertice.distancia)
                return 1;
            else if (distancia < vertice.distancia)
                return -1;
            return 0;
        }
    }

    /* Vecinos para gráficas; un vecino es un vértice y el peso de la arista que
     * los une. Implementan VerticeGrafica. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return vecino.get();
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        this.vertices = new Lista<>();
 aristas = 0;   
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
       return vertices.getElementos();    
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null || this.contiene(elemento))
      	  throw new IllegalArgumentException();
      	 vertices.agrega(new Vertice(elemento));

    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
    	Vertice va = (Vertice) vertice(a);
        Vertice vb = (Vertice) vertice(b);
        if (a.equals(b) || sonVecinos(a, b))
            throw new IllegalArgumentException("a y b ya están conectados, o a es igual a b");
        va.vecinos.agrega(new Vecino(vb, 1));
        vb.vecinos.agrega(new Vecino(va, 1));
        aristas++;        
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
    	Vertice va = (Vertice) vertice(a);
        Vertice vb = (Vertice) vertice(b);
        if (a.equals(b) || sonVecinos(a, b) || peso < 0)
            throw new IllegalArgumentException("a y b ya están conectados, o a es igual a b");
     if(!(contiene(a))|| !(contiene(b)))
    	 throw new NoSuchElementException();
        va.vecinos.agrega(new Vecino(vb, peso));
        vb.vecinos.agrega(new Vecino(va, peso));
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
    	Vertice va = (Vertice) vertice(a);
        Vertice vb = (Vertice) vertice(b);
        if (a.equals(b) || !sonVecinos(a, b))
            throw new IllegalArgumentException("a o b no están conectados.");
        Vecino ve_ab = null, ve_ba = null;
        for (Vecino ve : va.vecinos) {
            if (ve.vecino.equals(vb)) {
                ve_ab = ve;
            }
        }
        for (Vecino ve : vb.vecinos) {
            if (ve.vecino.equals(va)) {
                ve_ba = ve;
            }
        }
        va.vecinos.elimina(ve_ab);
        vb.vecinos.elimina(ve_ba);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
    	for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return true;
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
    	  if (!contiene(elemento))
              throw new NoSuchElementException("El elemento no está contenido en la gráfica.");
          Vertice v = (Vertice) vertice(elemento);
          for (Vertice ver : vertices)
              for (Vecino vec : ver.vecinos)
                  if (vec.vecino.equals(v)) {
                      ver.vecinos.elimina(vec);
                      aristas--;
                  }
          vertices.elimina(v);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
    	 if (!this.contiene(a) || !this.contiene(b))
             throw new NoSuchElementException("a o b no son elementos de la gráfica");
         Vertice va = (Vertice)vertice(a);
         Vertice vb = (Vertice)vertice(b);
         for (Vecino ve : va.vecinos)
             if (ve.vecino.equals(vb))
                 return true;
         return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
    	if (!(contiene(a)) || !(contiene(b))) 
            throw new NoSuchElementException();
    	if (!sonVecinos(a,b))
    		throw new IllegalArgumentException();
    	
    	
    		Vertice va = (Vertice) vertice(a);
            Vertice vb = (Vertice) vertice(b);
 
            for (Vecino ve : va.vecinos)
        if(ve.vecino.equals(vb))    
    	return ve.peso;
                 return -1;
            }


    
    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
    	if (!(contiene(a)) || !(contiene(b)))
    		throw new NoSuchElementException();
        if (!sonVecinos(a,b))
        	throw new IllegalArgumentException();
    
    		Vertice va = (Vertice) vertice(a);
            Vertice vb = (Vertice) vertice(b);
            for (Vecino ve : va.vecinos)
                if (ve.vecino.equals(vb))
                    ve.peso = peso;
    for(Vecino v : vb.vecinos)       
             if(v.vecino.equals(va))
            	 v.peso = peso;
    	}
    
    

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
    	for (Vertice e : vertices)
            if (e.elemento.equals(elemento))
                return e;
        throw new NoSuchElementException();
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
    if (vertice == null || !Vecino.class.isInstance(vertice) && !Vertice.class.isInstance(vertice))
    	throw new IllegalArgumentException();
    	if (vertice.getClass().getSimpleName().compareTo("Vertice")==0) {     
   
    Vertice v =(Vertice)vertice;
    v.color = color;
    return;
    }
    if (vertice.getClass().getSimpleName().compareTo("Vecino") == 0) {
    	
    	Vecino v = (Vecino)vertice;
    	v.vecino.color = color;
    	return;
    }
    }
    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
    	Vertice p = (Vertice)vertices.getPrimero();
    	for(Vertice k : vertices) {
    		k.color = Color.ROJO;
    	}
    	        	Cola<Vertice> q = new Cola<>();
    	        	p.color = Color.NEGRO;
    	        	q.mete(p);
    	        	while (!q.esVacia()) {
    	        		Vertice u = (Vertice)q.saca();
    	        		for (Vecino m : u.vecinos) {
    	        			if (m.getColor() == Color.ROJO) {
    	        				m.vecino.color = Color.NEGRO;
    	        				q.mete(m.vecino);
    	        			}
    	        	}
    	        	}
    	for (Vertice i : vertices)
    		if(i.color == Color.ROJO)
    	return false;
    	return true;

    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
    	for (VerticeGrafica<T> v : vertices)
            accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
    	Cola<Vertice> cola = new Cola<Vertice>();
    	recorrido(elemento, accion, cola);
    	paraCadaVertice(ver -> setColor(ver,Color.NINGUNO));
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
    	
    Pila<Vertice> pila = new Pila<Vertice>();
    recorrido(elemento, accion, pila);
    paraCadaVertice(ver -> setColor(ver,Color.NINGUNO));
    }
    private void recorrido(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Grafica<T>.Vertice> metesaca) {
        if (!contiene(elemento))
            throw new NoSuchElementException("El elemento no está en la gráfica.");
        Vertice v = (Vertice) vertice(elemento);
        paraCadaVertice(ver -> setColor(ver,Color.ROJO));
        v.color = Color.NEGRO;
        metesaca.mete(v);
        while (!metesaca.esVacia()) {
            Vertice e = metesaca.saca();
            
            accion.actua(e);
            for (Vecino ve : e.vecinos) {
                if (ve.vecino.color == Color.ROJO) {
                    ve.vecino.color = Color.NEGRO;
                    metesaca.mete(ve.vecino);
                }
            }
        }
    
    }
        /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
    	return vertices.getElementos() == 0;
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
    	aristas = 0;
        vertices.limpia();
       
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
    	String s = "";
    	paraCadaVertice (v -> setColor (v,Color.ROJO));
    	for (Vertice v : vertices) {
    		for (Vecino vecino : v.vecinos)
    			if (vecino.getColor() != Color.NEGRO)
    				s += String.format("(%d, %d), ", v.elemento , vecino.get());
    	    setColor(v,Color.NEGRO);
    	    }
    	   return s;
    	          
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)o;
        if ((vertices.getLongitud() != grafica.vertices.getLongitud()) ||
        		(aristas != grafica.getAristas()) || getElementos() != grafica.getElementos())
        	   return false;
        boolean b = false;
        Vertice u;
        
        for(Vertice v : vertices) {
if(!(grafica.contiene(v.elemento)))
    return false;    
u = (Vertice)(grafica.vertice(v.elemento));	
for (Vecino vl : v.vecinos) {
    for   (Vecino ve : u.vecinos)  	
	if(vl.vecino.elemento == ve.vecino.elemento)
        b = true;	
		break;
        		}
        	}
        	return b;		

    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
    	if (!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();
        Vertice s = (Vertice)vertice(origen);
        Vertice t = (Vertice)vertice(destino);
        for (Vertice v : vertices)
            v.distancia = Double.MAX_VALUE;
        s.distancia = 0;
    
        MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>(vertices);
        while (!monticulo.esVacia()) {
            Vertice u = monticulo.elimina();
            //ve.peso es la aristas abajo.
            for (Vecino v : u.vecinos) 
                if (distancia(v.vecino) == Double.POSITIVE_INFINITY ||
                        distancia(u) + 1 < distancia(v.vecino)) {
                    v.vecino.distancia = u.distancia + 1;
                    monticulo.reordena(v.vecino);
                }
            }
            Lista<VerticeGrafica<T>> l = new Lista<>();
            //Validacion para saber si nuestra gráfica fue conexa.
            if (distancia(t) != Double.MAX_VALUE) {
                Vertice vAux = t;
                while (vAux != s) 
                    for (Vecino v : vAux.vecinos) {
                        if (distancia(vAux) - 1 ==distancia(v.vecino)) {
                            l.agregaInicio(vAux);
                            vAux = v.vecino;
                            break;
                        }
                    }
                    if (vAux == s) 
                        l.agregaInicio(s);
                    
                
            }
            return l;
                
                }
            
        
 
    
    private double distancia(Grafica<T>.Vertice v) {
		// TODO Auto-generated method stub
		return v.distancia;
	}

	/**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
    	Lista<VerticeGrafica<T>> l= new Lista<>();
        if(!contiene(origen)||!contiene(destino))
          throw new NoSuchElementException("El elemento no esta en la grafica");
        Vertice s= (Vertice)vertice(origen);
        Vertice t= (Vertice)vertice(destino);
        if(s.equals(t)){
          l.agrega(s);
          return l;
        }
        for(Vertice v: vertices){
          v.distancia=Double.POSITIVE_INFINITY;
        }
        s.distancia=0;
        MonticuloMinimo<Vertice> m= new MonticuloMinimo<>(vertices);

        while(!m.esVacia()){
          Vertice u=m.elimina();
          for(Vecino v: u.vecinos){
            if(v.vecino.distancia> u.distancia+v.peso){
              v.vecino.distancia=u.distancia+v.peso;
              m.reordena(v.vecino);
            }
          }
        }
          if(t.distancia!=Double.POSITIVE_INFINITY){
            Vertice u=t;
            while(u!=s){
              for(Vecino v : u.vecinos){
                if(v.vecino.distancia==u.distancia-v.peso){
                  l.agregaInicio(u);
                  u=v.vecino;
                  break;
                }
              }
              if(u==s){
              l.agregaInicio(s);
            }
          }
        }
          return l;
          
          
    }
    
    
    
        
    
    private Lista<VerticeGrafica<T>> reversaTrayectoria(Vertice vo, Vertice vd, boolean esDijkstra) {
    	Lista<VerticeGrafica<T>> l = new Lista<>();
        //Validacion para saber si nuestra gráfica fue conexa.
        if (vd.distancia != Double.POSITIVE_INFINITY) {
            Vertice vAux = vd;
            while (vAux != vo) {
                for (Vecino ve : vAux.vecinos) {
                    if (vAux.distancia - (esDijkstra ? ve.peso : 1) == ve.vecino.distancia) {
                        l.agregaInicio(vAux);
                        vAux = ve.vecino;
                        break;
                    }
                }
                if (vAux == vo) {
                    l.agregaInicio(vo);
                }
            }
        }
        return l;
    }
  
}    
    
