
// This is one practical class used to compare two elements of the class Label. 

import java.util.Comparator;

public class Labcomp implements Comparator<Label>{
	
    public int compare (Label o1, Label o2){ 
        return(o1.weight-o2.weight); 
    }
}