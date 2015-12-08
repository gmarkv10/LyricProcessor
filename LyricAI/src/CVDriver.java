
public class CVDriver {

	public static void main(String[] args){

		try{
			NFoldCV n = new NFoldCV(10);
			n.train();
			n.test();
		} catch(Exception e){
			System.out.println("ERR with queries");
		}
	}


}
