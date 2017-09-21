
Shortest Path Trees and Reach in Road Networks 

Antoine Oustry
Pierre-Elie Bélouard
X2015

How does the program work ? 

1. import the following classes in the src directory : 
	-- Main.java
	-- Map2.java  
	-- ShortestPathInterface.java	
	-- Tree.java
	-- List.java
	-- Label.java
	-- LabComp.java
	-- Reach.java
	-- Coordinate.java

2. - Create a directory next to the src directory and the bin directory called "ressources" (the name and the localisation
of this directory are very important). 
- In this file, put the four data files : man.IN, malta.IN, idf.IN and france.IN.


3. The main class is the so-called Main. From this moment, you'll only have to run Main. 
Run Main class. 

- You will be first asked to select your map.  
- Then, you'll be asked which one of the three followings you would like to do : 

	-- know the number of different possible positions at the current moment of your trip, when 
	you have been traveling for time exactly t1 from your departure point. 
	You'll be asked this time t1 and your departure position (longitude/latitude). 
	The program will also return the coordinates of the possible positions. 

	-- know the number of different possible positions at the current moment of your trip, when 
	you have been traveling for time exactly t1 from your departure point and your destination point is at least t2>t1 towards your origin destination. 
	You will be asked t1, t2 and your departure position. 
	The program will also return the coordinates of the possible positions. 

	-- Make some statistics about the reach of some vertice of the map : 
	You will be asked a number n and a time t (in seconds). The program will calculate a 2-approximation of the reach of n rand vertice in the map. He will
	print the coordinates and the reach of the vertex whith the highest reach and return the number of vertice having a reach smaller than t, between t and 2t,
	between 2t and 4t, between 4t and 8t, etc. 


4. Some remarks about the running time : 

We did the simulations on a computer of the Ecole polytechnique (intelCore Xeon) in the informatic room. 
On these computer, the running times are the following : 
	-- for maps of Malta, Man : quasi-immediate     
	-- France : 
		--- 10'' are required to make the data treatment (creation of the adjacence array, etc.)
		--- the programs 1. and 2. run in less than one minute 
		--- It took us 24 hours to calculate the reach of 304 vertices.	