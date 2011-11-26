public class Tarea{
	
	private int id;
	private int procs;
	
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
	
}
