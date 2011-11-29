public class List extends Broker{

	static int indice;
	static Procesador procesadores[];
	static double lambda;
	static double mu;

	public List(Procesador procesadores[], double lambda, double mu){
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
		
		quicksort(0,(procesadores.length-1));
		for(int c = 0; c<procesadores.length; c++){
			System.out.println("Procesador "+procesadores[c].getId()+" :size = "+procesadores[c].size());
		}
		/*En caso de que las filas esten vacias ponemos el nextDeparture.*/
		// ahora assigns es el indice contenido en el arreglo de indices
		double departureTime = Proyecto.nextArrival;// + tarea.getReq();
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
		System.out.println("---LIST---");
		for(int i = 0; i<num; i++){
			//assigns = indices[i];
			//System.out.println("PROCESADOR "+procesadores[i].getId()+" Queue Size: "+procesadores[i].size());
			procesadores[i].enqueue(tarea);
			procesadores[i].setNextA(Proyecto.nextArrival); //se asigna el mismo arrival time a todos los procesadores involucrados	
			procesadores[i].setNextD(departureTime + procesadores[assigns].getTareaReq()); 	
		}
		System.out.println("---HAN ADQUIRIDO LA NUEVA TAREA---");
	}

	private void quicksort(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		double pivot = procesadores[low + (high-low)/2].size();

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (procesadores[i].size() < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (procesadores[j].size() > pivot) {
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
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private void exchange(int i, int j) {
		Procesador temp = procesadores[i];
		procesadores[i] = procesadores[j];
		procesadores[j] = temp;
	}
}
