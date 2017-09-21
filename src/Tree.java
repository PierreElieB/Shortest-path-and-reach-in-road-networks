
// An implementation of AVL binary search trees. 

public class Tree {
	
	long large;
	int small;
	Tree left;
	Tree right;
	boolean isEmpty; 
	int height; 
	int difference; 
	
	public Tree(){
		isEmpty = true;
		height = 0; 
		difference = 0; 
	}
	
	public Tree(long l, int s, Tree L, Tree r){
		large = l;
		small = s; 
		left = L; 
		right = r; 
		isEmpty = false;
		
		if(this.left == null){
			if(this.right == null){
				height = 1; 
				difference = 0; 
			}
			else{
				height = 1+this.right.height; 
				difference = -this.right.height; 
			}
		}
		else{
			if(this.right == null){
				height = 1+this.left.height;
				difference = this.left.height; 
			}
			else{
				this.height = 1+Math.max(L.height, r.height);
				difference = L.height - r.height;
			}
		}
	}
	
	private void calculateHeight(){
		if(!isEmpty){
			if(this.left == null){
				if(this.right == null){
					height = 1; 
					difference = 0; 
				}
				else{
					height = 1+this.right.height; 
					difference = -this.right.height; 
				}
			}
			else{
				if(this.right == null){
					height = 1+this.left.height;
					difference = this.left.height; 
				}
				else{
					this.height = 1+Math.max(this.left.height, this.right.height);
					difference = this.left.height - this.right.height;
				}
			}
		}
	}
	
	public void insert(int s, long l){
		if(this.isEmpty){
			large = l; 
			small = s; 
			isEmpty = false; 
			height = 1; 
			difference = 0; 
			}
		
		else{
			long pivot = this.large; 
			if(pivot>l){
				
				if(this.left==null){
					this.left = new Tree(l,s,new Tree(),new Tree());
					calculateHeight(); 
					this.balance(); 
					calculateHeight(); 
				}
				else{
					this.left.insert(s,l);
					calculateHeight(); 
					this.balance(); 
					calculateHeight(); 
				}
			}			
			else{
				if(this.right==null){
					this.right = new Tree(l,s,new Tree(),new Tree()); 
					calculateHeight(); 
					this.balance(); 
					calculateHeight();
				}
				else{
					this.right.insert(s, l); 
					calculateHeight();
					this.balance();
					calculateHeight();
				}
			}
		}
	}

	public int corresponding(long l){
		long head = this.large; 
		if (head==l){
			return(this.small); 
		}
		if(head<l){
			return(this.right.corresponding(l)); 
		}
		else{
			return(this.left.corresponding(l)); 
		}
	}
	
	private void rotationLeft(){
		Tree b = this.right; 
		Tree c = new Tree(this.large, this.small, this.left, b.left); 
		this.small = b.small; 
		this.large = b.large; 
		this.left = c; 
		this.right = b.right; 
		this.calculateHeight(); 
	}
	
	private void rotationRight(){
		Tree b = this.left; 
		Tree c = new Tree(this.large, this.small, b.right, this.right); 
		this.small = b.small; 
		this.large = b.large; 
		this.right = c; 
		this.left = b.left; 
		this.calculateHeight(); 
	}
	
	private void balance(){
		if(difference==2){
			if(this.left.left == null){
				int s, sl;
				long l, ll;
				s = small; 
				l = large; 
				sl = this.left.small; 
				ll = this.left.large; 
				Tree tr = new Tree(l,s, new Tree(), new Tree());
				Tree tl = new Tree(ll,sl, new Tree(), new Tree()); 
				small = this.left.right.small; 
				large = this.left.right.large; 
				left= this.left.right; 
				right = tr;
				left = tl; 
				isEmpty = false; 
				height = 2; 
				difference = 0; 
			}
			else{
				if(this.left.right == null){
					int s; 
					long l; 
					s = small; 
					l = large; 
					Tree t = new Tree(l,s, new Tree(), new Tree());
					small = this.left.small; 
					large = this.left.large; 
					left= this.left.left; 
					right = t;
					isEmpty = false; 
					height = 2; 
					difference = 0; 
				}
				else{
					if(this.left.left.height<this.left.right.height){
						this.left.rotationLeft();
						this.rotationRight(); 
					}
					else{
						this.left.rotationRight();
						this.rotationRight(); 
					}
				}
			}
		}
		
		if(difference==-2){
			if(this.right.left == null){
				int s; 
				long l; 
				s = small; 
				l = large; 
				Tree t = new Tree(l,s, new Tree(), new Tree());
				small = this.right.small; 
				large = this.right.large; 
				right = this.right.right; 
				left = t; 
				isEmpty = false; 
				height = 2; 
				difference = 0; 
			}
			else{
				if(this.right.right == null){
					int s1,s2; 
					long l1,l2; 
					s1 = small;
					s2 = this.right.small; 
					l1 = large; 
					l2 = this.right.large; 
					Tree t1 = new Tree(l1,s1, new Tree(), new Tree());
					Tree t2 = new Tree(l2,s2, new Tree(), new Tree()); 
					small = this.right.left.small; 
					large = this.right.left.large; 
					left = t1; 
					right = t2;
					isEmpty = false; 
					height = 2; 
					difference = 0; 
				}
				else{
					if(this.right.left.height<this.right.right.height){
						this.right.rotationLeft();
						this.rotationLeft(); 
					}
					else{
						this.right.rotationRight();
						this.rotationLeft(); 
					}
				}
			}
		}
	}
}
