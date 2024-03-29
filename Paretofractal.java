

public class Paretofractal extends Broker{

	static int indice;
	static Procesador procesadores[];
	static double lambda;
	static double mu;

	public Paretofractal(Procesador procesadores[], double lambda, double mu){
		this.indice = 0;
		this.procesadores = procesadores;
		this.lambda = lambda;
		this.mu = mu;
	}

	public void assign(Tarea tarea){
		int assigns = 0;
		int num = tarea.getProcs();
		int indices [] = new int[tarea.getProcs()]; //indices de los procesadores donde meteremos la fila
		System.out.println("LLEGA TAREA " +tarea.getId()+" QUE REQUIERE "+num+" PROCESADORES.");
		
		for(int a = 0; a<procesadores.length; a++){
			procesadores[a].setPareto(procesadores[a].tareasFila(mu) + procesadores[a].tareasProc());
		}
		quicksortP(0,(procesadores.length-1));

		/*En caso de que las filas esten vacias ponemos el nextDeparture.*/
		// ahora assigns es el indice contenido en el arreglo de indices
		double departureTime = Proyecto.nextArrival;// + tarea.getReq();
		tarea.setNextA(Proyecto.nextArrival);
		tarea.setNextD(departureTime);
		
		/*Igualar arrivals a todos los procesadores participantes en la tarea.*/	
		// ahora assigns es el indice contenido en el arreglo de indices
		//double arrivalTime = mayor + StdRandom.exp(lambda);
		for(int i = 0; i<num; i++){
			//System.out.println("PROCESADOR "+i+" Queue Size: "+procesadores[i].size());
			procesadores[i].enqueue(tarea);
			procesadores[i].setNextA(Proyecto.nextArrival); //se asigna el mismo arrival time a todos los procesadores involucrados	
			procesadores[i].setNextD(departureTime + procesadores[assigns].getTareaReq()); 	
		}
	}

	

	private void quicksortP(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		double pivot = procesadores[low + (high-low)/2].getPareto();

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (procesadores[i].getPareto() < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (procesadores[j].getPareto() > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		// Recursion
		if (low < j)
			quicksortP(low, j);
		if (i < high)
			quicksortP(i, high);
	}

	private void exchange(int i, int j) {
		Procesador temp = procesadores[i];
		procesadores[i] = procesadores[j];
		procesadores[j] = temp;
	}
}
