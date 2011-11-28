public class Tarea{
	
	private int id;
	private int procs;
	private double nextA;
	private double nextD;
	private double req;
	
	public Tarea(int id, int procs, double req){
		this.id = id;
		this.procs = procs;
		this.req = req;
	}
		
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}

	public double getReq(){
		return this.req;
	}
	
	public void setReq(double req){
		this.req = req;
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
