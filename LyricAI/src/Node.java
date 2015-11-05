
public class Node<T> {
	
	T value;
	Node next;
	Node prev;
	
	public Node(Node n, Node p, T val)
	{
		prev = p;
		next = n;
		value = val;
	}
	
	public Node(T val) {
		// TODO Auto-generated constructor stub
		value = val;
	}

	public void setNext(Node p){
		this.next = p; 
	}
	
	public Node getNext(){
		return next;
	}
	
	public void setPrev(Node p){
		this.prev = p; 
	}
	
	public Node getPrev(){
		return prev;
	}	
	
	public void setValue(T t){
		this.value = t;
	}
	
	public T getValue(){
		return value;
	}

	
	public String getValueS(){
		return value.toString();
	}
	

	
	

}
