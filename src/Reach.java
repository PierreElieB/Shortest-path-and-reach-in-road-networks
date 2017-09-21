
public class Reach {
	
	Map2 m; 
	int nu; 
	int t; 
	ShortestPathInterface S; 
	int vertexNumbers; 
	static int coef = 20; 
	
	Reach(Map2 Map2, int n, int tt){
		m = Map2; 
		nu = n; 									// the vertex we're going to calculate the reach
		t = tt; 									// long time compare to the Map2 (it could be twice the diameter of the Map2 eg)
		S = new ShortestPathInterface(m, nu, t); 
		vertexNumbers = m.vertexNumbers; 
	}
	
	// Calculate a 2-approximation of the reach of the vertex nu. 
	
	public int calculate(){
		
		// First : we calculate an upper bound for the reach.
		
		int k = 0; 
		
		for(int i=0; i<vertexNumbers; i++){
			
			int q1 = i/m.arrayLength; 
			int r1 = i%m.arrayLength; 
			if(S.optimal_time[q1][r1]>k){
				k = S.optimal_time[q1][r1]; 
			}
		}
		
		// Then : we try to find a pair of vertice (u,v) such that : 
		//				- one shortest way from u to v goes through nu
		//				- travel time from u to nu is k 
		//				- travel time from nu to v is k
		// 				- there exists u2 and v2 with an optimal travel time from nu equals to 2k such that one shortest path from nu to u2 goes through u and one shortest path from nu to v2 goes through v. 
		
		// If we could find such a pair of vertices : the reach of nu is at least k. 
		// If not : the reach is at most 2k and we repeat the same search with k twice smaller. 
		
		for(int a = 0; a<16; a++){
			
			int[] tab = S.possiblePosition2(k/2,k); 
			int n = tab[0];  
			
			for(int i = 1; i<n; i++){
				
				int vertexI = tab[i];
				ShortestPathInterface t = new ShortestPathInterface(m, vertexI, coef*k);
				
				for(int j = i+1; j<n+1; j++){
				
				// We test if the optimal way from tab[i] to tab[j] passes through vertex nu. 
					int vertex = (int) tab[j]; 
					boolean b = true;  
					
					while(b){
						
						int q2 = vertex/m.arrayLength; 
						int r2 = vertex%m.arrayLength;
						vertex = t.predecessor[q2][r2];
						b = !((vertex==nu)||(vertex==vertexI)); 
					}
					
					if(vertex==nu){
						return(k); 
					}					
				}
			}
			k = k/2; 			
		}
		return(k); 
	}
}
