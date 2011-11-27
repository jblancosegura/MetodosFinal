import java.io.*;
 public class Procesador{
	//private id;	
	private Queue<Double> q;
	private Queue<Tarea> tareas;

	
	private double nextA;
	private double nextD;
	private int tareaID;
	private boolean touched; //si ya han sido usados o no
	
	public Procesador(){
		q = new Queue<Double>();
		tareas = new Queue<Tarea>();
		//this.id=id;
		//busy=false;
		this.nextA=Proyecto.nextArrival;//StdRandom.exp(lambda); 
		this.nextD=Double.POSITIVE_INFINITY;	
		this.touched = false;
	}
	/*
	public getId(){
		return id;
	}
	public setId(int id){
		this.id=id;
		
	}*/
	public Queue<Double> getQueue(){
		return q;
	}
	public void setQueue(Queue q){
		this.q=q;
	}
	public double getNextA(){
		//return nextA;
		if(tareas.isEmpty())
			return nextA;
		return tareas.peek().getNextA();
	}
	public void setNextA(double nextA){
		if(!tareas.isEmpty())
			tareas.peek().setNextA(nextA);
		this.nextA=nextA;
	}
	public double getNextD(){
		//return nextD;
		if(tareas.isEmpty())
			return nextD;
		return tareas.peek().getNextD();
	}
	public void setNextD(double nextD){
		if(!tareas.isEmpty())
			tareas.peek().setNextD(nextD);
		this.nextD=nextD;
	}

	public boolean queueEmpty(){
		return q.isEmpty();
	}

	public void enqueue(Tarea t){
		tareas.enqueue(t);
		q.enqueue(nextA);
		tareas.peek().setNextA(nextA);
	}

	public double dequeue(){
		double tareaA = tareas.peek().getNextA();
		tareas.dequeue();
		/*return*/ q.dequeue();
		return tareaA;
	}
	public double size(){
		return q.size();
	}
	
	public double peek(){
		return q.peek();
	}

	public int tarea_id(){
		if(!tareas.isEmpty())
			return tareas.peek().getId();
		return -1;
	}

	public int tarea_procs(){
		return tareas.peek().getProcs();
	}

	public boolean getTouched(){
		return touched;
	}

	public void setTouched(boolean touched){
		this.touched = touched;
	}
}
