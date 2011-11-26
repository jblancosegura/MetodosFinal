import java.util.Scanner;
import java.util.Random;


public class Proyecto { 

    final static int ITERACIONES = 100;
	public static double nextArrival  =0;// = StdRandom.exp(lambda);     // time of next arrival
        public static double nextDeparture =Double.POSITIVE_INFINITY;//= Double.POSITIVE_INFINITY;  // time of next departure
    public static void main(String[] args) { 
        double lambda; // arrival rate
        double mu; // service rate
	int N; //cantidad de procesadores
	int i = 0; // iteracion actual
	int brokerType = 0; //0 - RR, 1 - List, 2 - Paretofractal
	double wait; //tiempo de espera
	int aux; //id del procesador a departir
	int tarea_id;
	int tarea_procs;
	int contador_de_procs = 0; //cuenta cuantos procesadores tienen la tarea deseada en queue

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
	System.out.println("Elegir Algoritmo: ");
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
	nextArrival += StdRandom.exp(lambda);
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
		//nextArrival = arrivalMenor(procesador); 
		
		nextDeparture = departureMenor(procesador);
		i++;
	    
		
            	/* LLEGADA */
        	if (nextArrival <= nextDeparture) {
			/* CREAR TAREA*/
			Tarea tarea = new Tarea(i, (generator.nextInt(N) + 1)); //el ID es unico porque i es incremental y la cantidad de procesadores es de 1 a N.
			broker.assign(tarea); //segun el algoritmo asigna tareas a los procesadores.
			nextArrival += StdRandom.exp(lambda);
            	}

            	/* SALIDA */
            	else {
			aux = departureMenorIndex(procesador);	
			//nextDeparture += StdRandom.exp(mu);
			tarea_id = procesador[aux].tarea_id(); //obtenemos el id de la tarea a sacar
			tarea_procs = procesador[aux].tarea_procs();//obtenemos la cantidad de procesadores que se ocupan para procesar, valgame la redundancia, la tarea [EDIT: no hay redundancia dice Axel]
			int indices [] = new int[tarea_procs]; //hacemos el arreglo de los procesadores que tienen la tarea a departir
			contador_de_procs = 0; //incializamos el contador en 0
			indices[0] = aux;
			contador_de_procs++; //pues el del departure menor es parte de los procesadores que tienen la tarea
			
			for(int k = 0; k<N; k++){
				if(k != aux && ( procesador[k].tarea_id() == tarea_id ) ){
					indices[contador_de_procs] = k;
					contador_de_procs++;
				}	
			}
			if(contador_de_procs == tarea_procs){ //sacamos la tarea simultaneamente
				System.out.println("TAREA "+tarea_id+" SALE.");
				int x;
				for(int m = 0; m<indices.length; m++){
					x = indices[m];
					System.out.println(procesador[x].getNextD() +" : D vs A : "+procesador[x].peek());
					wait = procesador[x].getNextD() - procesador[x].dequeue();
					System.out.println("Procesador "+x+" Wait = "+wait+", queue size = "+procesador[x].size());
					if(procesador[x].queueEmpty()) procesador[x].setNextD(Double.POSITIVE_INFINITY);
					else procesador[x].setNextD(procesador[x].getNextD() + StdRandom.exp(mu));
					//else procesador[x].setNextD(nextDeparture);
				}
			}
			else{ //no estan listos todos los procesadores que contienen la tarea...se recalcula un departure
				System.out.println("Procesador "+aux+" sigue teniendo una tarea en espera.");	
				if(procesador[aux].queueEmpty()) procesador[aux].setNextD(Double.POSITIVE_INFINITY);
				else /*procesador[aux].setNextD(nextDeparture);	*/procesador[aux].setNextD(Double.POSITIVE_INFINITY);
			}
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



