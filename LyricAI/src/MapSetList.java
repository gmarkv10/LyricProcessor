import java.util.Comparator;
import java.util.Map;

public class MapSetList<T> {
	int size;
	Node head;
	Node tail;
	Node ptr;
//	public NodeList(){
//		head = n;
//		tail = n;
//		size =0;
//	}
	
	public T getPtr(){
		return (T) ptr.getValue();
	}
	public MapSetList(){
		size = 0;
	}
	//CASTS VALUES TO INTEGERS IN ORDER TO COMPARE THEM!
	//TODO: just rewrite with compareTo for Integer and everything else
	Comparator comp = new Comparator<T>(){

		@Override
		public int compare(T o1, T o2) {
			// TODO Auto-generated method stub
			try{
			if( (Integer) o1 < (Integer) o2 ){
				return -1;
			}
			else{
				if(  (Integer) o1 > (Integer) o2 ){
					return 1;
				}
				else{
					return 0;
				}
			}
			}
			catch(ClassCastException e){
				System.err.println("Can't compare these entries");
				return 0;
			}
		}
		
	};
	public boolean isEmpty(){
		return size == 0 ? true : false;
	}
	public void enq(T i){
		if(size == 0){
			head = new Node<T>(i);
			tail = head;
			ptr = head;
		}
		else{
			Node newTail = new Node<T>(i);
			tail.setNext(newTail);
			newTail.setPrev(tail);
			tail = tail.getNext();
		}
		size++;
	}
	public void push(T i){
		if(size == 0){
			head = new Node<T>(i);
			tail = head;
			ptr = head;
		}
		else{
			Node push = new Node<T>(i);
			//head.setPrev(push);
			push.setNext(head);
			head.setPrev(push);
			head = push;
		}
		size++;
	}
	public Node top(){
		return head;
	}
	
	public Node advancePtr(){
		if(ptr.next != null){
			ptr = ptr.getNext();
			return ptr;
		}
		else return null;
	}
	
	public T deq(){
		if(size == 0) return null;
		T i = (T) head.value;
		if(size == 1){
			head = null;
			tail = null;
			ptr = null;
			size--;
			return i;
		}
		else{
			head = head.getNext();
			size--;
			return i;
		}
	}
	
	public Node getIdx(int i){
		if(i > size || i < 0){
			//System.err.println("Index out of range, return val is null");
			return null;
		}
		else{
			ptr = head;
			for(int j = 0; j < i; j++ ){
				advancePtr();
			}
			return ptr;
		}
	}
	
	//will push a T to the head and bubble sort it to the right place
	//uses the comparator which right now only catches a ClassException if
	//you don't use an Integer
	public int insert(T val){
		push(val);
		return minisort(); //will sort the head element in place
						   //and return the index it ends up in
	}
	
	//used in Map.put implementation, idx is returned from a call to minisort()
	public void insertAt(int idx, T val){
		if(idx > size || idx < 0){
			System.err.println("Index out of range, return val is null");
		}
		else{
			if(idx == 0){//new elt goes to head
				push(val);
			}
			else if(idx == size){//new elt goes to tail
				enq(val);
			}
			else{
				size++;
				ptr = head;
				for(int j = 0; j < idx; j++ ){
					advancePtr();
				}
				Node<T> newIdx = new Node(val);
				Node previous = ptr.getPrev();
				Node cNext    = ptr.getNext();
				newIdx.setPrev(previous);
				newIdx.setNext(ptr);
				ptr.setPrev(newIdx);
				previous.setNext(newIdx);
			}

		}
	}
	
	public boolean swap(int i1, int i2 ){
		if((i1 > size || i1 < 0) && (i2 > size || i2 < 0)){
			System.err.println("Index out of range, return val is null");
			return false;
		}
		else{
			try{
				Node place = getIdx(i1);
				Node place2 = getIdx(i2);
				T placeVal = (T) place.getValue();
				place.setValue((T) place2.getValue() );
				place2.setValue(placeVal);
				return true;
			} catch(NullPointerException e){
				System.err.println("INVALID SWAP");
				return false;
			}
			
		}
	}
	
	public int minisort(){
		if(isSorted()){
			return 0;
		}
		else{
			int comparison = 0;
			ptr = head;
			while(ptr != tail){
				//System.out.println("Comparing:" + getIdx(comparison).getValue() + " " + getIdx(comparison+1).getValue());
				//System.out.println(comp.compare(getIdx(comparison).getValue(), getIdx(comparison + 1).getValue()) < 0);
				if(comp.compare(getIdx(comparison).getValue(), getIdx(comparison + 1).getValue()) < 0){
					//System.out.println("Swapping:" + comparison + " " + (comparison +1));
					swap(comparison, comparison+1);
					advancePtr();
					comparison++;
				}
				else{
					break;
				}
			}
			if(comp.compare(getIdx(comparison).getValue(), getIdx(comparison + 1).getValue()) < 0){
				//System.out.println("Swapping:" + comparison + " " + (comparison +1));
				swap(comparison, comparison+1);
				advancePtr();
				comparison++;
			}
			return comparison;
			
		}
	}
	
	public boolean isSorted(){
		if(isEmpty()){
			return true;
		}
		else{
			if(size == 1 || size == 0){
				return true;
			}
			else{
				ptr = head;
				T comparison = (T) ptr.getValue();
				while(ptr != tail){
					if(comp.compare(comparison, (T) ptr.getNext().getValue()) > 0){
						advancePtr();
						comparison = (T) ptr.getValue();
					}
					else return false;
				}
				return true;
			}
		}
		
		
	}
	
	//returns the index of the parameter q
	public int contains(T q){
		if(isEmpty()){
			return -1;
		}
		else{
			ptr = head;
			int idx = 0;
			if(ptr.getValue().equals(q)) return idx;
			while (ptr != tail){
				if(ptr.getValue().equals(q)) return idx;
				advancePtr();
				idx++;
			}
			return -2;
		}
	}


	public void print(){
		try{
		Node step = head;
		String s = "";
		while( step !=  tail){
		 s += (step.getValueS() + ",");	
		 step = step.getNext();
		}
		 s += step.getValueS();	
		 step = step.getNext();
		System.out.println(s);
		} catch(NullPointerException e){
			System.out.println("Empty. Like my soul.");
		}
	}
	
	public void resetPtr(){
		ptr = head;
	}

}

//TEST CASES
/*
 * swapping out of bounds
 * deq and empty list
 * test getPtr after a swap
 * make sure it works in all cases with both constructors
 * enq after a swap
 * size variable is correct all the time
 * isEmpty()
 * contains(val)
 * insertAt(idx, val)
 * removeAt(idx)
 * */

//NEW METHODS
/*
 * we should probably implement the Set interface becuase that jives with the map but this
 * gets us out the door
 * 
 * 
 */

