import java.util.Scanner;
import java.util.Random;


public class Proyecto { 

    final static int ITERACIONES = 10;
	public static double nextArrival  =0;// = StdRandom.exp(lambda);     // time of next arrival
        public static double nextDeparture =0;//= Double.POSITIVE_INFINITY;  // time of next departure
    public static void main(String[] args) { 
        double lambda; // arrival rate
        double mu; // service rate
	int N; //cantidad de procesadores
	int i = 0; // iteracion actual
	int brokerType = 0; //0 - RR, 1 - List, 2 - Paretofractal
	double wait; //tiempo de espera
	int aux; //id del procesador a departir

	Scanner stdIn = new Scanner(System.in);
	Broker broker;
	Random generator = new Random(); //para generar el numero de procesos requeridos
	

	/* RECABACION DE DATOS*/
	System.out.println("----------BIENVENIDOS AL FUTURO----------");
	System.out.print("Lambda: ");
	lambda = stdIn.nextDouble();
	System.out.print("Mu: ");
	mu = stdIn.nextDouble();
	System.out.print("Cantidad de Procesadores: ");
	N = stdIn.nextInt();
	System.out.print("Elegir Algoritmo: ");
	System.out.println("0 - RoundRobin ");
	System.out.println("1 - List ");
	System.out.println("2 - Paretofractal ");
	brokerType = stdIn.nextInt();
	System.out.println("----------BIENVENIDOS AL FUTURO----------");
	System.out.println("----------INICIANDO SIMULACION CON LOS SIGUIENTES DATOS----------");
	System.out.println("Lambda: " + lambda);
	System.out.println("Mu: " + mu);
	System.out.println("Cantidad de Procesadores: " + N);
	/*                   */

	/*
	System.out.println("----------INICIANDO SIMULACION EN 10----------");
	System.out.println("----------INICIANDO SIMULACION EN 09----------");
	System.out.println("----------INICIANDO SIMULACION EN 08----------");
	System.out.println("----------INICIANDO SIMULACION EN 07----------");
	System.out.println("----------INICIANDO SIMULACION EN 06----------");
	System.out.println("----------INICIANDO SIMULACION EN 05----------");
	System.out.println("----------INICIANDO SIMULACION EN 04----------");
	System.out.println("----------INICIANDO SIMULACION EN 03----------");
	System.out.println("----------INICIANDO SIMULACION EN 02----------");
	System.out.println("----------INICIANDO SIMULACION EN 01----------");
	System.out.println("----------INICIANDO SIMULACION YA!!!----------");
	*/

	/* INICIALIZACION DE DATOS */
	Procesador [] procesador = new Procesador[N];
	for(int j = 0; j<N; j++){
		procesador[j] = new Procesador(); 
	}	
	/* ********************** */
	
	/* CREACION DEL BROKER */
	switch(brokerType){
		case 0: broker = new RoundRobin(procesador, lambda, mu); break;
		case 1: broker = new List(); break;
		case 2: broker = new Paretofractal(); break;
		default: broker = new RoundRobin(procesador, lambda, mu); break;
	}
	/*                     */



        // Simulacion
        while (i < ITERACIONES) {
		nextArrival = arrivalMenor(procesador); System.out.println("iter: "+i); System.out.println(nextArrival); 
		nextDeparture = departureMenor(procesador); System.out.println(nextDeparture);
		i++;
	    
		
            	/* LLEGADA */
        	if (nextArrival <= nextDeparture) {
			/*if (q.isEmpty()) nextDeparture = nextArrival + StdRandom.exp(mu);
                	nextArrival += StdRandom.exp(lambda);*/
			/* CREAR TAREA*/
			Tarea tarea = new Tarea(i, (generator.nextInt(N) + 1)); //el ID es unico porque i es incremental y la cantidad de procesadores es de 1 a N.
			broker.assign(tarea); //segun el algoritmo asigna tareas a los procesadores.
            	}

            	/* SALIDA */
            	else {
			aux = departureMenorIndex(procesador);	
			nextDeparture += StdRandom.exp(mu);
			for(int k = 0; k<N; k++){
				if((procesador[k].getNextD() == procesador[aux].getNextD()) && k != aux && (procesador[k].peek() == procesador[aux].peek())){
					wait = procesador[k].getNextD() - procesador[k].dequeue();
					System.out.println("Procesador "+k+" Wait = "+wait+", queue size = "+procesador[k].size());
					if(procesador[k].queueEmpty()) procesador[k].setNextD(Double.POSITIVE_INFINITY);
					else procesador[k].setNextD(nextDeparture);
				}	
			}
			wait = procesador[aux].getNextD() - procesador[aux].dequeue();
			System.out.println("Procesador "+aux+" Wait = "+wait+", queue size = "+procesador[aux].size());	
			if(procesador[aux].queueEmpty()) procesador[aux].setNextD(Double.POSITIVE_INFINITY);
			else procesador[aux].setNextD(nextDeparture);	
            	}
        }

    }
    
	/* Obtiene el arrival menor de los procesadores. */
    	public static double arrivalMenor(Procesador arreglo[]){
		int minimo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(arreglo[minimo].getNextA() > arreglo[i].getNextA())
				minimo = i;
		}
		return arreglo[minimo].getNextA();
    	}

	/* Obtiene el arrival menor de los procesadores. */
    	public static double departureMenor(Procesador arreglo[]){
		int minimo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(arreglo[minimo].getNextD() > arreglo[i].getNextD())
				minimo = i;
		}
		return arreglo[minimo].getNextD();
    	}

	/* Obtiene el indice del departure menor de los procesadores. */
    	public static int departureMenorIndex(Procesador arreglo[]){
		int minimo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(arreglo[minimo].getNextD() > arreglo[i].getNextD())
				minimo = i;
		}
		return minimo;
    	}

}



