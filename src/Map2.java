
import java.io.*;
public class Map2 {

	static int arrayLength2 = 1000; 		
	static int epsilon = 60000; 				// This is an upper bound for the travel time among one single edge.
	String adress ;  					    	// This is the address of the data file. 
	List[][] adjacenceListArray;		 		// adjacenceListVertex[i] is the list of the vertices j (represented by an int) such that there exists an edge from vertex i to vertex j. It also gives the time needed to travel from i to j
	int vertexNumbers; 							// number of vertexes in the Map2.
	long[][] correspondingArray;  				// correspondingArray[i] represents the practical number (between 0 and vertexNumbers-1 which corresponds to the vertex i (i may be very large) 
	Tree correspondingArrayReverse; 			// dual array of the array correspondingArray : in fact, this is not an array but a binary search tree. 
	Coordinate[][] coordinateArray; 			// matrix which contains the coordinates of the point i  
	int arrayNumber; 
	int arrayLength; 
	int edgeNumber; 
	
	public Map2(String ad){
		
		this.adress = ad; 
		this.arrayLength = arrayLength2; 
		count(); 
		arrayNumber = vertexNumbers/arrayLength +1; 
		correspondingArray= new long[arrayNumber][arrayLength];
		coordinateArray = new Coordinate[arrayNumber][arrayLength]; 
		correspondingArrayReverse = new Tree();
		makeCAandAL(); 
		
	}
	
	// Count the number of vertices in the map. 
	
	private void count(){
		
		try{
			
		BufferedReader br = new BufferedReader(new FileReader(adress));
		
		int a = 0; 
		String ligne ;
		
		while (true){
			
			ligne=br.readLine();
			
			if (ligne.charAt(0)=='v'){
				a=a+1; 
			}
			else{
				
				if(ligne.charAt(0)=='a'){
					break;
				}
			}
		}
		
		this.vertexNumbers = a; 
		br.close();
		}
		
		catch (FileNotFoundException exception){
			 System.out.println ("Le fichier n'a pas ete trouve"); 
		} 
		catch (IOException e){
			System.out.println("Le fichier n'a pas ete trouve");
		}
	}
		
	// Read the data file : 
	//		- creation of the tree and the matrix making the corresponding between small and large numbers used to code one vertex ;
	// 		- Read the coordinates of all vertices.
	// 		- Read the time required to travel along the edges and make the adjacence array. 
	
	
	private void makeCAandAL(){
		
		try{
		BufferedReader br = new BufferedReader(new FileReader(adress));
		
		String ligne ;
		
		for (int ind=0; ind<vertexNumbers; ind++){
			
			ligne=br.readLine();
			
			int N = ligne.length();
			int e = 0; 
			int f = 0; 
			int g =0; 
		
			while(!(ligne.charAt(2+e)==' ')){					// looking for the number of characters which code the vertex.
				e = e+1; 
			}
			
			while(!(ligne.charAt(e+f+3)==' ')){			// looking for the number of characters which code the longitude. 
				f = f+1; 
			}
			
			while(e+f+g+4<N){		// looking for the number of characters which code the 	latitude.				
				g = g+1; 
			}
			
			
			long readNumber = 0; 
			int longitude = 0; 
			int latitude = 0; 
			long power = 1; 
			int power2 = 1; 
			
			for(int j = 0; j<=e-1; j++){					// reading of the number coding the vertex.
				int nbr = ligne.charAt(1+e-j)-48; 
				readNumber = readNumber + nbr*power; 
				power = 10*power; 				
			}
			
			power2 = 1; 
			
			for(int j = 0; j<=f-1; j++){					// reading of the number representing the latitude. 
				int nbr = ligne.charAt(e+f+2-j)-48; 
				longitude = longitude + nbr*power2; 
				power2 = 10*power2; 				
			}
			power2 = 1; 
			
			for(int j = 0; j<=g-1; j++){					// reading of the number representing the longitude. 
				int nbr = ligne.charAt(e+f+g+3-j)-48; 
				latitude = latitude + nbr*power2; 
				power2 = 10*power2; 				
			}
			
			int q1 = ind/arrayLength; 
			int r1 = ind%arrayLength; 
			correspondingArray[q1][r1]=readNumber;
			correspondingArrayReverse.insert(ind, readNumber);
			Coordinate c = new Coordinate(longitude, latitude); 
			coordinateArray[q1][r1] = c; 
			
			
		}
		
		adjacenceListArray = new List[arrayNumber][arrayLength];  
		
		for(int i = 0; i < arrayNumber; i++){
			
			for(int j = 0; j< arrayLength; j++){
				
				adjacenceListArray[i][j] = new List(); 
			}
			
		}
		
		int count = 0; 
		ligne=br.readLine(); 
		
		while (ligne!=null){
			
			int N = ligne.length();
			int e = 0; 
			int f = 0; 
			int g =0; 
			count++; 
		
			while(!(ligne.charAt(2+e)==' ')){					// looking for the number of characters which code the vertex which is the origin of the edge.
				e = e+1; 
			}
			
			while(!(ligne.charAt(e+f+3)==' ')){			// looking for the number of characters which code the arrival vertex.
				f = f+1; 
			}
			
			while(e+f+g+4<N){		// looking for the number of characters which code the 	time.				
				g = g+1; 
				
				
			}
			
			long origin = 0;
			long destination = 0; 
			int time = 0; 
			long power = 1; 
			int power2 = 1; 
			
			for(int j = 0; j<=e-1; j++){					// reading of the number coding the vertex. 
				
				int nbr = ligne.charAt(1+e-j)-48; 
				origin = origin + nbr*power; 
				power = 10*power; 				
			}
			
			power = 1; 
			
			for(int j = 0; j<=f-1; j++){					// reading of the number coding the destination vertex. 
				int nbr = ligne.charAt(e+f+2-j)-48; 
				destination = destination + nbr*power; 
				power = 10*power; 				
			}
			power2 = 1; 
			
			for(int j = 0; j<=g-1; j++){					// reading of the number coding the time.
				int nbr = ligne.charAt(e+f+g+3-j)-48; 
				time = time + nbr*power2; 
				power2 = 10*power2; 				
			}
			
			int vertexOriginCode = correspondingArrayReverse.corresponding(origin);  
			int q1 = vertexOriginCode/arrayLength; 
			int r1 = vertexOriginCode%arrayLength; 
			(adjacenceListArray[q1][r1]).add(correspondingArrayReverse.corresponding(destination),time);
			
			ligne = br.readLine(); 
			edgeNumber = count; 
		}
		
		br.close();
		}
		
		catch (FileNotFoundException exception){
			 System.out.println ("File has not been found."); 
		} 
		catch (IOException e){
			System.out.println("File has not been found.");
		}
	}

	
	public String outputstring(int depart, int[] vertice_index ){
		
		String res = "var plottedPoints = [";
		int L = vertice_index[0];
		
		for (int i =1 ; i<L ; i++){
			
			int q = vertice_index[i]/arrayLength ;
			int r = vertice_index[i]%arrayLength ;
			
			Coordinate C = coordinateArray[q][r];
			
			float lat = C.latitude/(float) 1000000 ;
			float longi = C.longitude/(float)1000000;
			
			res=res+"["+lat+","+longi+"],";
		}
		int q = vertice_index[L]/arrayLength ;
		int r = vertice_index[L]%arrayLength ;
		
		Coordinate C = coordinateArray[q][r];
		
		float lat = C.latitude/(float) 1000000 ;
		float longi = C.longitude/(float)1000000;
		
		res=res+"["+lat+","+longi+"]];";
		
				
		q = depart/arrayLength ;
		r = depart%arrayLength ;
		
	    C = coordinateArray[q][r];
		
		lat = C.latitude/(float) 1000000 ;
		longi = C.longitude/(float)1000000;
		res=res+"var centralMarker = ["+lat+","+longi+"]";
		
		return res; 	
	}
	
	// Input of the function : a pair of coordinates (representing a point on earth)
	// Output : the number of the vertices of the map which is the closest one to the input point. 
	
	public int findVertex(int lat, int lon){
		
		int res = 0; 
		int q1 = res/arrayLength; 
		int r1 = res%arrayLength; 
		Coordinate c = coordinateArray[q1][r1]; 
		int difference = Math.max(Math.abs(c.latitude-lat),Math.abs(c.longitude-lon)); 
		
		for(int i=1; i<vertexNumbers; i++){
			q1 = i/arrayLength; 
			r1 = i%arrayLength; 
			c = coordinateArray[q1][r1]; 
			int difference2 = Math.max(Math.abs(c.latitude-lat),Math.abs(c.longitude-lon)); 
			
			if(difference2<difference){
				res = i; 
				difference = difference2; 
			}
		}
		return(res); 		
	}
}



