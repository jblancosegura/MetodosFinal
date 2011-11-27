public class Tarea{
	
	private int id;
	private int procs;
	private double nextA;
	private double nextD;
	
	public Tarea(int id, int procs){
		this.id = id;
		this.procs = procs;
	}
		
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
		
	public int getProcs(){
		return this.procs;
	}
	
	public void setProcs(int procs){
		this.procs = procs;
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
}
