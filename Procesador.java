import java.io.*;
import java.lang.Math;

 public class Procesador{
	private int id;	
	private Queue<Double> q;
	private Queue<Tarea> tareas;

	private Tarea tareaActual;

	private double nextA;
	private double nextD;
	private double pareto;
	private int tareaID;
	private boolean touched; //si ya han sido usados o no

	/*MAKESPAN*/
	private boolean contando;
	private double makespan;
	private double init;
	/**********/

	/*PARETOFRACTAL*/
	private static final double phi = 0.5; //probabilidad de tiempo mayor que cierto valor
	private static final double H = .8; //H
	private static final double alfa = 1.58; //parametro de forma de distribucion Pareto
	private static final double A = 600; //parametro de localizacion de distribucion Pareto
	private double ta = 0; //tiempo de ejecucion de la tarea actual
	private double lastD;
	/***************/
	
	public Procesador(int i){
		q = new Queue<Double>();
		tareas = new Queue<Tarea>();
		this.id=i;
		this.pareto = 0;
		this.nextA=Proyecto.nextArrival;//StdRandom.exp(lambda); 
		this.nextD=Double.POSITIVE_INFINITY;	
		this.touched = false;
		this.makespan = 0;
		this.contando = false;
		this.lastD = 0;
	}

	public void setTareaActual(Tarea t){
		this.tareaActual = t;
	}
	public Queue<Double> getQueue(){
		return q;
	}
	public void setQueue(Queue q){
		this.q=q;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public double getPareto(){
		return pareto;
	}
	public void setPareto(double pareto){
		this.pareto = pareto;
	}
	public double getTa(){
		return ta;
	}
	public void setTa(double ta){
		this.ta = ta;
	}
	public double getNextA(){
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
		if(tareas.isEmpty())
			return nextD;
		return tareas.peek().getNextD();
	}
	
	public double getLastD(){
		return lastD;
	}

	public void setNextD(double nextD){
		lastD = nextD;
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
	public double tareaLastD(){
		return lastD;
	}
	public Tarea tareaPeek(){
		return tareas.peek();
	}

	public double getTareaReq(){
		if(tareaActual != null)
			return tareaActual.getReq();
		return 0;
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
	public boolean getContando(){
		return contando;
	}

	public void setContando(boolean contando){
		this.contando = contando;
	}
	public double getMakespan(){
		return makespan;
	}
	public void setMakespan(double makespan){
		this.makespan = makespan;
	}
	public double getInit(){
		return init;
	}
	public void setInit(double init){
		this.init = init;
	}
	/*Paretofractal*/
	public double tareasFila(double mu){
		return (q.size() * mu) + ( (A * Math.pow(q.size(),H) ) / (Math.pow(phi, (1/alfa) ) ) );
	}
	public double tareasProc(){
		return ( ta * ( Math.pow(phi, -(1/alfa) - 1 ) ) );
	}
	/*************/
}
