
// An implementation of lists. 

public class List {
	
	int direction;
	int time;
	int length; 
	List tail; 
	
	public List(){ 
		length = 0;
	}
	
	public List(int d, int ti, List t){
		direction = d; 
		time = ti;
		length = 1+t.length;
		tail = t; 
	}
	
	public boolean estVide(){
		return(this.length==0);
	}
	
	public void add(int d, int ti){
		if(this.estVide()){
			direction = d; 
			time = ti; 
			length++;
		}
		else{
			if(this.length == 1){
				int d2 = this.direction;
				int ti2 = this.time;
				List empty = new List(); 
				List l = new List(d2,ti2,empty); 
				this.tail = l; 
				this.direction = d; 
				this.time = ti; 	
				length = 2; 
			}
			else{
				int d2 = this.direction;
				int ti2 = this.time; 
				List t = this.tail; 
				List l = new List(d2,ti2,t);
				this.tail = l; 
				this.direction = d;
				this.time = ti; 
				length++;  
			}
		}
	}

	public void remove(){
		if(this.length == 1){
			length = 0; 
		}
		else{
			this.direction = this.tail.direction;
			this.time = this.tail.time;
			this.tail = this.tail.tail; 
			this.length = this.length-1; 
		}
	}
	
	public void print(){
		System.out.println("Direction :"+direction+"Time"+time);
		if(length>=2){
			this.remove(); 	
			this.print(); 			
		}	
	}
}

