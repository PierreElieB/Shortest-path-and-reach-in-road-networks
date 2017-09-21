
import java.util.PriorityQueue; 
 
public class ShortestPathInterface {
     
    Map2 m;
    public int departure_point ;
    int time; 							// An upper bound of the travel time (it should be bigger than the diameter.
    public int[][] optimal_time ; 		// The matrix of the optimal travel times from the departure point 
    public int[][] predecessor ; 		// The matrix of the predecessors on the optimal journey from the departure point. 
    private boolean[][] toVisit; 		// A practical matrix to prevent the program from doing the same job several times
    private boolean[][] alreadyVisited; // Another practical matrix. 
    private static int epsilon; 		// Only a few edges should have a higher time than epsilon.  
     
    public ShortestPathInterface(Map2 m2, int d, int tt){
    	
        m=m2;    
        time = tt; 
        departure_point = d;         
        optimal_time = new int[m.arrayNumber][m.arrayLength];
        predecessor = new int[m.arrayNumber][m.arrayLength];
        toVisit = new boolean[m.arrayNumber][m.arrayLength]; 
        alreadyVisited = new boolean[m.arrayNumber][m.arrayLength]; 
        epsilon = m.epsilon; 
         
        //initialization of optimal_time and predecessor : we fill the two matrices with -1. 
        
        for (int i = 0; i<m.vertexNumbers;i++){
        	int q1 = i/m.arrayLength; 
        	int r1 = i%m.arrayLength; 
            optimal_time[q1][r1] = -1;
            predecessor[q1][r1]=-1;
            toVisit[q1][r1] = false; 
            alreadyVisited[q1][r1] = false; 
        }
        
        int q1 = departure_point/m.arrayLength; 
        int r1 = departure_point%m.arrayLength; 
        optimal_time[q1][r1]=0;
        predecessor[q1][r1]=departure_point;
         
        // initialization of the priority queue
        
        Labcomp c = new Labcomp();
        PriorityQueue <Label> Q = new PriorityQueue <Label> (c);
        List L = m.adjacenceListArray[q1][r1];
        
        List l3 = new List(); 
        
        while (L.estVide()==false){ 
            Label nv = new Label(L.time,L.direction, departure_point);
            int q2 = L.direction/m.arrayLength; 
            int r2 = L.direction%m.arrayLength; 
            optimal_time[q2][r2]=L.time;
            Q.add(nv);
            l3.add(L.direction, L.time);
            L.remove(); 
        }
        
        m.adjacenceListArray[q1][r1] = l3; 
        
        int t=0;
         
         
        //Implementation of Dijkstra algorithm
        
        while ((Q.isEmpty()==false)&&(t<=time)){
            Label current=Q.remove();
            int q3 = current.vertex/m.arrayLength; 
            int r3 = current.vertex%m.arrayLength; 
             
            // if the vertex hasn't been definitely marked yet
            
            if (predecessor[q3][r3]==-1){
            	
                List l4 = new List();      
                optimal_time[q3][r3]=current.weight;
                predecessor[q3][r3]=current.predecessor;
                t=current.weight;
                
                List l2 = m.adjacenceListArray[q3][r3];
                 
                 
                boolean b = l2.estVide(); 
                
                while (!b){
                	
                	int q4 = l2.direction/m.arrayLength; 
                	int r4 = l2.direction%m.arrayLength; 
                	
                    // if the following vertex hasn't been visited yet, or if the new time is better                }
                    if ((optimal_time[q4][r4]==-1)||((t+l2.time)<optimal_time[q4][r4])){
                    	
                        optimal_time[q4][r4]=t+l2.time;
                        Label follower = new Label( t+l2.time, l2.direction,current.vertex);
                        Q.add(follower);
                    }
                    
                    l4.add(l2.direction, l2.time);
                    l2.remove(); 
                    b = l2.estVide(); 
                }
                
                if(b){
                	 m.adjacenceListArray[q3][r3] = l4;                 	
                }
            }
        }
    }
     
     
    // The output of this function is the number of different places which could be reached in time milliseconds, assuming that we only choose optimal routes 
     
    public int possiblePosition(int time) {
    	
    	int count = 0; 
    
    	// First step : we choose the vertices from which we're going to start our ascent in the graph (ie the ones which are a little bit further than time from departurePoint.
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength; 
    		long timeToI = optimal_time[q1][r1]; 
    		
    		if((timeToI>=time)&(timeToI<time+epsilon)){
    			toVisit[q1][r1] = true;   			
    		}    		
    	}
    	
    	// Second step : we make the ascents from the vertices we chose on the first step. The ascent is stopped when we've reached a vertex to which we need less than time seconds to travel. 
    	
    	for(int i =0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength;
    		
    		if(toVisit[q1][r1]){
    			toVisit[q1][r1] = false; 
    			int j = this.predecessor[q1][r1]; 
    			int q2 = j/m.arrayLength; 
    			int r2 = j%m.arrayLength; 
    			boolean b = toVisit[q2][r2]; 
    			
    			while(b){
    				toVisit[q2][r2] = false; 
    				j = predecessor[q2][r2]; 
    				q2 = j/m.arrayLength; 
        			r2 = j%m.arrayLength; 
    				b = toVisit[q2][r2];      				
    			}
    			
    			if(optimal_time[q2][r2] <= time){
    				count++; 
    			}	
    		}
    	}
        return(count); 
    }
    
    // This is the same function as possiblePosition, but the output is a little bit different : it gives an array whose first element is the number of possiblePositions and the other elements are the indices of the possible positions. 
    
    public int[] possiblePosition2(int time) {
    	
    	int N = possiblePosition(time); 
    	int[] tab = new int[N+1];
    	tab[0]=N;
    	int count=1;
    
    	// First step : we choose the vertices we're going to visit
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength; 
    		long timeToI = optimal_time[q1][r1]; 
    		
    		if((timeToI>=time)&(timeToI<time+epsilon)){
    			toVisit[q1][r1] = true;   			
    		}    		
    	}
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength;
    		
    		if(toVisit[q1][r1]){
    			toVisit[q1][r1] = false; 
    			int j = this.predecessor[q1][r1]; 
    			int q2 = j/m.arrayLength; 
    			int r2 = j%m.arrayLength; 
    			boolean b = toVisit[q2][r2]; 
    			
    			while(b){
    				toVisit[q2][r2] = false; 
    				j = predecessor[q2][r2]; 
    				q2 = j/m.arrayLength; 
        			r2 = j%m.arrayLength; 
    				b = toVisit[q2][r2];      				
    			}
    			
    			if(optimal_time[q2][r2] <= time){
    				tab[count]=j;
    				count++; 
    			}	
    		}
    	}
        return(tab); 
    }
    
    
 // The output of this function is the number of different places which could be reached in time1 milliseconds, assuming that we only choose optimal routes and that we need time2 hours to reach our final destination.
    
    
    public int possiblePosition(int time1, int time2) {
    	
    	int count = 0; 
    	
    	// First step : we choose the vertices we're going to visit (ie  the ones which are a little bit more further than times2 milliseconds). 
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength;     		
    		int timeToI = optimal_time[q1][r1]; 
    		
    		if((timeToI>=time2)&(timeToI<time2+epsilon)){
    			toVisit[q1][r1] = true;   			
    		}    		
    	}
    	
    	// Second step : we go back in direction to the departure point until we reach vertices which can be reached in less than time1 milliseconds. The boolean arrays are important to make sure we do not count some vertices twice.
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength;
    		
    		if(toVisit[q1][r1]){    			
    			      			
    			toVisit[q1][r1] = false; 
    			int j = this.predecessor[q1][r1]; 
    			int q2 = j/m.arrayLength; 
    			int r2 = j%m.arrayLength; 
    			boolean b = toVisit[q2][r2]; 
    			
    			while(b){
    				toVisit[q2][r2] = false; 
    				j = predecessor[q2][r2]; 
    				q2 = j/m.arrayLength; 
        			r2 = j%m.arrayLength; 
    				b = toVisit[q2][r2];     				
    			}
    			
    			
    			if(optimal_time[q2][r2]<= time2){
    				
    				alreadyVisited[q2][r2] = true; 
    				int k = predecessor[q2][r2]; 
    				int q3 = k/m.arrayLength; 
    				int r3 = k%m.arrayLength; 
    				b = ((!(alreadyVisited[q3][r3])))&(optimal_time[q2][r2]>time1);
    				
    				// We stop if we come to a vertex we have already visited or if we come to a vertex to whom we need less than t1 milliseconds to travel.   
    				
    				while(b){
    					j = predecessor[q2][r2]; 
    					alreadyVisited[q2][r2] = true; 
    					q2 = j/m.arrayLength; 
    					r2 = j%m.arrayLength; 
    					k = predecessor[q3][r3];
    					q3 = k/m.arrayLength; 
    					r3 = k%m.arrayLength;
        				b = (!(alreadyVisited[q3][r3]))&(optimal_time[q2][r2]>time1);
    				}
    			}
    			
    			if(optimal_time[q2][r2] <= time1){
    				count++; 
    			}	
    		}
    	}
        return(count);
    }
    
    // This is the same function as possiblePosition, but the output is a little bit different : it gives an array whose first element is the number of possiblePositions and the other elements are the indices of the possible positions. 

    
    public int[] possiblePosition2(int time1, int time2) {
    	
    	int count = 0; 
    	List l = new List(); 
    	
    	// First step : we choose the vertices we're going to visit (ie  the ones which are a little bit further than times2 milliseconds). 
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength; 
    		int timeToI = optimal_time[q1][r1]; 
    		
    		if((timeToI>=time2)&(timeToI<time2+epsilon)){
    			toVisit[q1][r1] = true;   			
    		}
    		alreadyVisited[q1][r1] = false; 
    	}
    	
    	// Second step : we go back in direction to the departure point until we reach vertices which can be reached in less than time1 milliseconds. The boolean arrays are important to make sure we do not count some vertices twice.
    	
    	for(int i = 0; i<m.vertexNumbers; i++){
    		
    		int q1 = i/m.arrayLength; 
    		int r1 = i%m.arrayLength; 
    		
    		if(toVisit[q1][r1]){
    			
    			toVisit[q1][r1] = false; 
    			int j = this.predecessor[q1][r1]; 
    			int q2 = j/m.arrayLength; 
    			int r2 = j%m.arrayLength; 
    			boolean b = toVisit[q2][r2]; 
    			
    			while(b){
    				toVisit[q2][r2] = false; 
    				j = predecessor[q2][r2];
    				q2 = j/m.arrayLength; 
    				r2 = j%m.arrayLength; 
    				b = toVisit[q2][r2];     				
    			}
    			
    			if(optimal_time[q2][r2]<= time2){
    				
    				alreadyVisited[q2][r2] = true; 
    				int k = predecessor[q2][r2]; 
    				int q3 = k/m.arrayLength; 
    				int r3 = k%m.arrayLength;
    				b = (!(alreadyVisited[q3][r3]))&(optimal_time[q2][r2]>time1);
    				
    				// We stop if we come to a vertex we have already visited or if we come to a vertex to whom we need less than t1 milliseconds to travel.   
    				
    				while(b){
    					j = predecessor[q2][r2]; 
    					alreadyVisited[q2][r2] = true; 
    					q2 = j/m.arrayLength; 
    					r2 = j%m.arrayLength; 
    					k = predecessor[q3][r3];
    					q3 = k/m.arrayLength; 
    					r3 = k%m.arrayLength;
        				b = (!(alreadyVisited[q3][r3]))&(optimal_time[q2][r2]>time1);
    				}
    			}
    			
    			if(optimal_time[q2][r2] <= time1){
    				count++; 
    				l.add(j, 0);
    			}	
    		}
    	}
    	 
    	// Creation of the output array
    	
    	int[] res = new int[count+1]; 
    	res[0] = count; 
    	
    	// We fill the array with the vertices we've found, which are stocked in the list. 
    	
    	for(int i=1; i<=count; i++){
    		int j = l.direction; 
    		res[i]= j;
    		l.remove(); 
    	}
        return(res);
    }
}
