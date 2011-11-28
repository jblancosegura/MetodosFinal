import java.util.Scanner;
import java.util.Random;
import java.io.*; 


public class Proyecto { 

        final static int ITERACIONES = 10000;
	public static double nextArrival  =0;// = StdRandom.exp(lambda);     // time of next arrival
        public static double nextDeparture =Double.POSITIVE_INFINITY;//= Double.POSITIVE_INFINITY;  // time of next departure
	public static double makespan = 0; //makespan esperado
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

	/* Leer archivos de las tareas. */
	FileReader frIds = new FileReader("ids.txt"); 
	FileReader frRun = new FileReader("runtimes.txt"); 
	FileReader frReq = new FileReader("reqs.txt"); 

	BufferedReader brIds = new BufferedReader(frIds); 
	BufferedReader brRun = new BufferedReader(frRun); 
	BufferedReader brReq = new BufferedReader(frReq); 
	/********************************/

	

	Scanner stdIn = new Scanner(System.in);
	Broker broker;
	Random generator = new Random(); //para generar el numero de procesos requeridos
	

	/* RECABACION DE DATOS*/
	System.out.println("----------BIENVENIDOS AL FUTURO----------");
	System.out.println(".  Para Paretofractal se recomienda usar una mu de .0003422910911897 que proviene de 1/u = 2921.49  .");
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

	/* INICIALIZACION DE DATOS */
	nextArrival += StdRandom.exp(lambda);
	Procesador [] procesador = new Procesador[N];
	double [] valor_esperado = new double[N]; 
	for(int j = 0; j<N; j++){
		procesador[j] = new Procesador(j); 
		valor_esperado[j] = 0;
	}	
	/* ********************** */
	
	/* CREACION DEL BROKER */
	switch(brokerType){
		case 0: broker = new RoundRobin(procesador, lambda, mu); break;
		case 1: broker = new List(procesador, lambda, mu); break;
		case 2: broker = new Paretofractal(procesador, lambda, mu); break;
		default: broker = new RoundRobin(procesador, lambda, mu); break;
	}
	/*                     */



        // Simulacion
     //   while (i < ITERACIONES) {
	String s; //lecutra de linea del archivo 
	while((s = brIds.readLine()) != null) { 
		//nextArrival = arrivalMenor(procesador); 
		
		nextDeparture = departureMenor(procesador);
		i++;
	    
		/*E[m]*/
		for(int c = 0; c<N; c++){
			valor_esperado[c] += procesador[c].size(); //vamos sumando el total de procesos por cola
		}
		/*****/

            	/* LLEGADA */
        	if (nextArrival <= nextDeparture) {
			/* CREAR TAREA*/
			Tarea tarea = new Tarea(Integer.parseInt(s), (generator.nextInt(N) + 1)); //el ID es unico porque i es incremental y la cantidad de procesadores es de 1 a N.
			broker.assign(tarea); //segun el algoritmo asigna tareas a los procesadores.
			/**********************/
			/*Actualizar TA para el Paretofractal y el makespan*/
			for(int c = 0; c<N; c++){
				procesador[c].setTa(nextArrival - procesador[c].tareaLastD());
				if(!procesador[c].queueEmpty() && !procesador[c].getContando() ){ //queue estaba vacia y ahora se inicia el conteo del makespan
					procesador[c].setContando(true);
					procesador[c].setInit(nextArrival);
				}
				procesador[c].setMakespan(nextArrival - procesador[c].getInit());
			}
			makespan(procesador); //calculates and prints makespan
			/**************/
			nextArrival += StdRandom.exp(lambda);
            	}

            	/* SALIDA */
            	else {
			aux = departureMenorIndex(procesador);	//indice del procesador con el menor departure
			tarea_id = procesador[aux].tarea_id(); //obtenemos el id de la tarea a sacar
			tarea_procs = procesador[aux].tarea_procs();//obtenemos la cantidad de procesadores que se ocupan para procesar la tarea
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
					wait = procesador[x].getNextD() - procesador[x].dequeue();
					System.out.println("Procesador "+procesador[x].getId()+" Wait = "+wait+", queue size = "+procesador[x].size());
					if(procesador[x].queueEmpty())procesador[x].setNextD(Double.POSITIVE_INFINITY);
					else procesador[x].setNextD(procesador[x].getNextD());
				}
			}
			else{ //no estan listos todos los procesadores que contienen la tarea...se recalcula un departure
				System.out.println("Procesador "+procesador[aux].getId()+" sigue teniendo una tarea en espera.");	
				if(procesador[aux].queueEmpty()) procesador[aux].setNextD(Double.POSITIVE_INFINITY);
				else procesador[aux].setNextD(procesador[aux].getNextD() + StdRandom.exp(mu));
			}

			/**********************/
			/*Actualizar el makespan*/
			for(int c = 0; c<N; c++){
				procesador[c].setMakespan(nextDeparture - procesador[c].getInit());
				if(procesador[c].queueEmpty() && procesador[c].getContando() ){ //queue estaba vacia y se sigue contando, hay que detenerlo
					procesador[c].setContando(false);
				}
			}
			makespan(procesador); //calculates and prints makespan
            	}
		
		
        }
	//medimos cantidad de procesos por cola (procesador)
	double valor_esperado_final = 0;
	for(int c = 0; c<N; c++){
		System.out.println("Procesador "+procesador[c].getId()+" :E[m] = "+(valor_esperado[c]/i));
		valor_esperado_final += (valor_esperado[c]/i);
	}
	System.out.println("Promedio del valor esperado de procesos en cola: "+(valor_esperado_final / N) );
	System.out.println("Valor esperado del makespan es: "+(makespan / i) );

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

	/* Obtiene el makespan y lo imprime.*/
    	public static void makespan(Procesador arreglo[]){
		int maximo = 0;
		for(int i = 0; i<arreglo.length; i++){
			if(arreglo[maximo].getMakespan() < arreglo[i].getMakespan())
				maximo = i;
		}
		System.out.println("El makespan actual es: "+arreglo[maximo].getMakespan()+" y es del Procesador "+arreglo[maximo].getId());
		makespan += arreglo[maximo].getMakespan();
    	}
}



