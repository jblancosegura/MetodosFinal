import java.util.Scanner;

public class Proyecto { 

    final static int ITERACIONES = 10;

    public static void main(String[] args) { 
        double lambda; // arrival rate
        double mu; // service rate
	int N; //cantidad de procesadores
	int i = 0; // iteracion actual
	int brokerType = 0; //0 - RR, 1 - List, 2 - Paretofractal
	Scanner stdIn = new Scanner(System.in);
	Broker broker;

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
	for(int j; j<N; j++){
		procesador[j] = new Procesador(); 
	}	
	/* ********************** */
	
	/* CREACION DEL BROKER */
	switch(brokerType){
		case 0: broker = new RoundRobin(); break;
		case 1: broker = new List(); break;
		case 2: broker = new Paretofractal(); break;
		default: broker = new RoundRobin(); break;
	}
	/*                     */

        Queue<Double> q = new Queue<Double>();            // arrival times of customers
        double nextArrival   = StdRandom.exp(lambda);     // time of next arrival
        double nextDeparture = Double.POSITIVE_INFINITY;  // time of next departure


	double nextArrivalMenor = 0;
        // Simulacion
        while (i < ITERACIONES) {
		i++;
	    
		/* CREAR TAREA*/
		broker.assign(tarea);
            	// it's an arrival
        	if (nextArrival <= nextDeparture) {
			if (q.isEmpty()) nextDeparture = nextArrival + StdRandom.exp(mu);
			if(q.size()<N) q.enqueue(nextArrival);
			else System.out.println("EL PAQUETAZO HA SIDO RECHAZADO");
                	nextArrival += StdRandom.exp(lambda);
            	}

            	// it's a departure
            	else {
                	double wait = nextDeparture - q.dequeue();
			System.out.println("Wait = "+wait+", queue size = "+q.size());
                	//StdOut.printf("Wait = %6.2f, queue size = %d\n", wait, q.size());
                	if (q.isEmpty()) nextDeparture = Double.POSITIVE_INFINITY;
                	else             nextDeparture += StdRandom.exp(mu);
            	}
        }

    }
    
	/* Obtiene el indice del menor de un arreglo. */
    	public static double arrivalMenor(Procesador arreglo[]){
		int minimo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(Procesador[minimo].getNextA() > Procesador[i].getNextA())
				minimo = i;
		}
		return Procesador[minimo].getNextA();
    	}

}



