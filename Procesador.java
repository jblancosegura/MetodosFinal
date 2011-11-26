import java.io.*;
 public class Procesador{
	//private id;	
	private Queue<Double> q;
	
	private double nextA;
	private double nextD;
	private int tareaID;
	private boolean touched; //si ya han sido usados o no
	
	public Procesador(){
		q = new Queue<Double>();
		//this.id=id;
		//busy=false;
		this.nextA=0;//StdRandom.exp(lambda); 
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
		return nextA;
	}
	public void setNextA(double nextA){
		this.nextA=nextA;
	}
	public double getNextD(){
		return nextD;
	}
	public void setNextD(double nextD){
		this.nextD=nextD;
	}

	public boolean queueEmpty(){
		return q.isEmpty();
	}

	public void enqueue(){
		q.enqueue(nextA);
	}

	public double dequeue(){
		return q.dequeue();
	}
	public double size(){
		return q.size();
	}
	
	public double peek(){
		return q.peek();
	}

	public boolean getTouched(){
		return touched;
	}

	public void setTouched(boolean touched){
		this.touched = touched;
	}
}
