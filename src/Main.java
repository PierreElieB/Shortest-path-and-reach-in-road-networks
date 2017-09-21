
import java.util.Scanner; 

public class Main{
	
	Map2 m; 
	
	Main(){
		
		Map2 m; 
		Scanner saisieUtilisateur = new Scanner(System.in);
		
		System.out.println("Select the map : if Man, press 1; if Malta, press 2; if Ile de France, press 3; if France, press 4");
		int ent = saisieUtilisateur.nextInt(); 
		
		switch(ent){
			case 1 : 
				m = new Map2("ressources/man.in"); 	
				System.out.print("man");
				break; 
		
			case 2 :
				m = new Map2("ressources/malta.in"); 
				break; 
		
			case 3 :
				m = new Map2("ressources/idf.in"); 
				break; 
		
			case 4 :
				m = new Map2("ressources/france.in"); 
				break; 
			default : 
				m = new Map2("ressources/france.in");
				
		}
		
		boolean b = true; 
		
		while(b){

			System.out.println("What would you like to do ? ");
			System.out.println("If you would like to know the possible positions accessible from a vertex in a given time, press 1."); 
			System.out.println("If you would like to know the possible positions accessible from a vertex in a given time t1, assuming your journey will last at least t2>t1 hours, press 2. "); 
			System.out.println("If you would like to make some statistics about the reach of the vertice on the map, press 3."); 
			int a = saisieUtilisateur.nextInt(); 
			
			if(a==1){
			
				System.out.println("Enter the longitude of your departure point. (it must be an int, eg : for the Pantheon, 2346100)"); 
				int lon = saisieUtilisateur.nextInt();  
				System.out.println("Enter the latitude of your departure point.(it must be an int, eg : for the Pantheon, 48846300)"); 
				int lat = saisieUtilisateur.nextInt();  
				int vertex = m.findVertex(lat,lon); 
				System.out.println("How long would you like to travel (in seconds, it must be an integer) ?");
				int time = saisieUtilisateur.nextInt()*1000; 
				ShortestPathInterface p = new ShortestPathInterface(m, vertex, 100000000); 
				System.out.println("The number of possible positions is : "+p.possiblePosition(time));
				System.out.println(m.outputstring(vertex,p.possiblePosition2(time)));
				
			}
			
			if(a==2){
				System.out.println("Enter the longitude of your departure point. (it must be an integer, eg : for the Pantheon, 2346100)"); 
				int lon = saisieUtilisateur.nextInt();
				System.out.println("Enter the latitude of your departure point. (it must be an integer, eg for the Pantheon : 48846300)"); 
				float lat2 = saisieUtilisateur.nextFloat();  
				int lat = (int) Math.floor(lat2*1000000); 	
				int vertex = m.findVertex(lat,lon); 
				System.out.println("How long would you like to travel (in seconds) ? (it must be an integer, eg : 3600 for 1 hour.)");
				int time1 = saisieUtilisateur.nextInt()*1000; 
				System.out.println("How far is your final destination (in seconds) ? (it must be an integer, eg : 72000 for 2 hours.)");
				int time2 = saisieUtilisateur.nextInt()*1000; 
				ShortestPathInterface p = new ShortestPathInterface(m, vertex, 100000000); 
				int[] pos =p.possiblePosition2(time1,time2);
				System.out.println("The number of possible positions is : "+pos[0]);
				System.out.println(m.outputstring(vertex,pos));
			}
			
			if(a==3){
				System.out.println("How many vertices would you like Java to calculate the reach ? (for the map of France, the runtime is about 4 minutes per reach to calculate.)");
				int n = saisieUtilisateur.nextInt();  
				System.out.println("Select the lowest category for the statistics (in seconds, eg : 450 for 7.5')"); 
				int int1 = saisieUtilisateur.nextInt()*1000; 
				int[] tab = new int[15]; 
				long res = 0; 
				int bestVertex = 0; 
				int countFailures = 0; 
				
				for(int i =0; i<15; i++){
					tab[i]=0; 
				}
				
				for(int i = 0; i<n; i++){
					
					int summit2 = (int)(Math.random()*(m.vertexNumbers)); 
					
					
						Reach r2 = new Reach(m, summit2, 100000000); 
						long l = r2.calculate(); 
						
						if(l>=res){
							res = l; 
							bestVertex = summit2; 
						}
						
						float l2 = l/(float)1000;
						System.out.println("The reach of vertex "+summit2+" is : "+l2);
						int r = Math.max((int) Math.ceil((Math.log(l/(float) int1))/(Math.log(2))),0); 
						tab[r]++; 				
				}
				float res2 = res/(float) 1000; 
				System.out.println("Best reach (in seconds) : "+res2);
				int q1 = bestVertex/m.arrayLength; 
				int r1 = bestVertex%m.arrayLength; 
				Coordinate c = m.coordinateArray[q1][r1]; 
				float lat = c.latitude/(float)1000000; 
				float lon = c.longitude/(float)1000000; 
				System.out.println("vertex : "+ bestVertex+" ; latitude : "+lat+" ; longitude : "+lon); 
				System.out.println("number of failures : "+countFailures); 
				double d = int1/(float) 1000; 
				double u = 2*d; 
				
				System.out.println("The number of vertice having a reach smaller than "+d+" is : "); 
				System.out.println(tab[0]); 
				System.out.println(); 

				
				for(int i = 1; i<15; i++){
					
					System.out.println("The number of vertice having a reach between "+d+" and "+u+" is : "); 
					System.out.println(tab[i]); 
					System.out.println(); 
					d = u; 
					u = u*2;
				}
			}
		
			System.out.println("Would you like to make another try ? If yes, press 1. If no, press 0. "); 
			int bb = saisieUtilisateur.nextInt(); 
		
			switch(bb){
				case 1 : 
					b = true; 
					break; 
				
				default :  
					b = false;
			}	
		}
		
		saisieUtilisateur.close(); 
	}
	
	public static void main(String[] args){
		Main main = new Main(); 
	}

}

