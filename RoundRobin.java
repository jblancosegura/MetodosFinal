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

		/*En caso de que las filas esten vacias ponemos el nextDeparture.*/
		// ahora assigns es el indice contenido en el arreglo de indices
		double departureTime = Proyecto.nextArrival + StdRandom.exp(mu);
		tarea.setNextA(Proyecto.nextArrival);
		tarea.setNextD(departureTime);
		/*for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			if(procesadores[assigns].queueEmpty())
				procesadores[assigns].setNextD(departureTime);
		}*/
		
		/*Igualar arrivals a todos los procesadores participantes en la tarea.*/	
		// ahora assigns es el indice contenido en el arreglo de indices
		//double arrivalTime = mayor + StdRandom.exp(lambda);
		for(int i = 0; i<indices.length; i++){
			assigns = indices[i];
			procesadores[assigns].enqueue(tarea);
			procesadores[assigns].setNextA(Proyecto.nextArrival); //se asigna el mismo arrival time a todos los procesadores involucrados	
			procesadores[assigns].setNextD(departureTime); 	
			System.out.println("PROCESADOR "+procesadores[assigns].getId()+" HA ADQUIRIDO UNA TAREA NUEVA.");
		}

	}

	public int newIndice(){
		int toReturn = indice;
		toReturn++;
		if(toReturn >= procesadores.length)
			toReturn = 0;
		return toReturn;
	}
}
