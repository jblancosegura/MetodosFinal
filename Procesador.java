import java.io.*;
 public class Procesador{
	private id;	
	private Queue<Double> q;
	private boolean busy;
	private double nextA;
	private double nextB;
	
	public Procesador(){
		q = new Queue<Double>();
		//this.id=id;
		busy=false;
		nextA=Double.POSITIVE_INFINITY;
		nextD=Double.POSITIVE_INFINITY;
		
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
	public boolean getBusy(){
		return busy;
	}
	public void setBusy(boolean busy){
		this.busy=busy;
	}
	public double getNextA(double nextA){
		return nextA;
	}
	public void set(double nextA){
		this.nextA=nextA;
	}
	public double getNextB(){
		return nextB;
	}
	public void setNextB(double nextB){
		this.nextB=nextB;
	}
}
