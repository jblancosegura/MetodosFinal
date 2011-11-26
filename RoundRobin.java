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
		double init = arrivalMax(procesadores) + StdRandom.exp(lambda);
		System.out.println("LLEGA TAREA QUE REQUIERE "+num+" PROCESADORES.");
		while(!asignado){
			indices[assigns] = indice;
			assigns++;
			if(assigns == tarea.getProcs())
				asignado = true;
			if(!procesadores[indice].getTouched()){
				procesadores[indice].setTouched(true);
				procesadores[indice].setNextA(init);
				System.out.println("PROCESADOR "+indice+" tiene NA inicial de: "+procesadores[indice].getNextA());
			}
			indice = newIndice();
		}
		
		double mayorD = departureMayor(indices); //obtenemos el departure Mayor
		double mayor = arrivalMayor(indices); //obtenemos el arrival Mayor

		/*En caso de que todas las filas esten vacias ponemos el nextDeparture.*/
		// ahora assigns es el indice contenido en el arreglo de indices

		//si hay uno en fila, if nextDepartueActual<nextDepartureNuevo, REHACER departure, pues cuando salga el lonewolf recalcular departure para su siguiente 
		double departureTime = mayor + StdRandom.exp(mu);
		if(mayorD == Double.POSITIVE_INFINITY){
			mayorD = departureTime;
		}
		for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			if(procesadores[assigns].getNextD() != mayorD){
				procesadores[assigns].setNextD(departureTime);
				System.out.println("bitch DEPARTS @ "+procesadores[assigns].getNextD());
			}
		}
		//}
		
		/*Igualar arrivals a todos los procesadores participantes en la tarea.*/	
		// ahora assigns es el indice contenido en el arreglo de indices
		double arrivalTime = mayor + StdRandom.exp(lambda);
		for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			procesadores[assigns].enqueue();
			procesadores[assigns].setNextA(arrivalTime);
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
			if(procesadores[arreglo[maximo]].getNextD() < procesadores[arreglo[i]].getNextD() && procesadores[arreglo[i]].getNextD() != Double.POSITIVE_INFINITY)
				maximo = i;
		}
		return procesadores[arreglo[maximo]].getNextD();
    	}

	/* Obtiene el arrival mayor de los procesadores. */
    	public static double arrivalMayor(int arreglo[]){
		int maximo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(procesadores[arreglo[maximo]].getNextA() < procesadores[arreglo[i]].getNextA())
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
