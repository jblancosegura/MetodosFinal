public class Proyecto { 

    final static int ITERACIONES = 10000;

    public static void main(String[] args) { 
        double lambda = Double.parseDouble(args[0]);  // arrival rate
        double mu     = Double.parseDouble(args[1]);  // service rate
	int N = Integer.parseInt(args[2]); //cantidad de msgs
	int i = 0; // iteracion actual

        Queue<Double> q = new Queue<Double>();            // arrival times of customers
        double nextArrival   = StdRandom.exp(lambda);     // time of next arrival
        double nextDeparture = Double.POSITIVE_INFINITY;  // time of next departure

        // simulate an M/M/1 queue
        while (i < ITERACIONES) {
	    i++;
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

}



