import java.io.*;
 public class Procesador{
	private id;	
	private Queue<Double> q;
	private boolean busy;
	private double nextA;
	private double nextB;
	
	public Procesador(int id){
		q = new Queue<Double>();
		this.id=id;
		busy=false;
		nextA=Double.POSITIVE_INFINITY;
		nextD=Double.POSITIVE_INFINITY;
		
	}

	public getId(){
		return id;
	}
	public setId(int id){
		this.id=id;
		
	}
	public getQueue(){
		return q;
	}
	public setQueue(Queue q){
		this.q=q;
	}
	public getBusy(){
		return busy;
	}
	public setBusy(boolean busy){
		this.busy=busy;
	}
	public getNextA(double nextA){
		return nextA;
	}
	public set(double nextA){
		this.nextA=nextA;
	}
	public getNextB(){
		return nextB;
	}
	public setNextB(double nextB){
		this.nextB=nextB;
	}
}
