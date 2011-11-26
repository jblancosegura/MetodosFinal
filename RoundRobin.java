public class RoundRobin extends Broker{

	static int indice;
	static Procesador procesadores[];
	static double lambda;
	static double mu;

	public RoundRobin(Procesador procesadores[], double lambda, double mu){
		this.indice = 0;
		this.procesadores = procesadores;
		this.lambda = lambda;
		this.mu = mu;
	}
	public void assign(Tarea tarea){
		boolean asignado = false;
		int assigns = 0;
		int num = tarea.getProcs();
		int indices [] = new int[tarea.getProcs()]; //indices de los procesadores donde meteremos la fila
		System.out.println("LLEGA TAREA " +tarea.getId()+" QUE REQUIERE "+num+" PROCESADORES.");
		while(!asignado){
			indices[assigns] = indice;
			assigns++;
			if(assigns == tarea.getProcs())
				asignado = true;
			indice = newIndice();
		}
		
		double mayor = arrivalMayor(indices); //obtenemos el arrival Mayor

		/*En caso de que las filas esten vacias ponemos el nextDeparture.*/
		// ahora assigns es el indice contenido en el arreglo de indices
		double departureTime = mayor + StdRandom.exp(mu);
		for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			procesadores[assigns].setNextD(departureTime);
		}
		//}
		
		/*Igualar arrivals a todos los procesadores participantes en la tarea.*/	
		// ahora assigns es el indice contenido en el arreglo de indices
		//double arrivalTime = mayor + StdRandom.exp(lambda);
		for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			procesadores[assigns].setNextA(Proyecto.nextArrival); //se asigna el mismo arrival time a todos los procesadores involucrados				
			procesadores[assigns].enqueue(tarea);
			System.out.println("PROCESADOR "+indices[i]+" HA ADQUIRIDO UNA TAREA NUEVA.");
		}

	}

	public int newIndice(){
		int toReturn = indice;
		toReturn++;
		if(toReturn >= procesadores.length)
			toReturn = 0;
		return toReturn;
	}

	/* Obtiene el departure mayor de los procesadores. */
    	public static double departureMayor(int arreglo[]){
		int maximo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(procesadores[arreglo[maximo]].getNextD() > procesadores[arreglo[i]].getNextD() && procesadores[arreglo[i]].getNextD() != Double.POSITIVE_INFINITY)
				maximo = i;
		}
		return procesadores[arreglo[maximo]].getNextD();
    	}

	/* Obtiene el arrival mayor de los procesadores elegidos. */
    	public static double arrivalMayor(int arreglo[]){
		int maximo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(procesadores[arreglo[maximo]].getNextA() > procesadores[arreglo[i]].getNextA())
				maximo = i;
		}
		return procesadores[arreglo[maximo]].getNextA();
    	}

	/* Obtiene el arrival maximo de los procesadores. */
    	public static double arrivalMax(Procesador arreglo[]){
		int minimo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(arreglo[minimo].getNextA() < arreglo[i].getNextA())
				minimo = i;
		}
		return arreglo[minimo].getNextA();
    	}

}
