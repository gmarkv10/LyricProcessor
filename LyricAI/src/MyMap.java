import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class MyMap<K,V> implements Map<K,V> {

	MapSetList<K> KList = new MapSetList<K>();
	MapSetList<V> VList = new MapSetList<V>();
	@Override
	public int size() {
		// TODO Auto-generated method stub
		if(KList.size != VList.size){ //FAILSAFE
			System.err.println("WARNING, YOUR SIZES ARE OFF: K("+KList.size+")  V("+VList.size+")" );
		}
		return VList.size; //had to pick one
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(KList.size != VList.size){ //FAILSAFE
			System.err.println("WARNING, YOUR SIZES ARE OFF: K("+KList.size+")  V("+VList.size+")" );
		}
		return VList.isEmpty(); //had to pick one
	}
	
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return KList.contains((K) key) >= 0 ? true : false;
	}

	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return VList.contains((V) value) >= 0 ? true : false;
	}

	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		try{
			return (V) VList.getIdx(KList.contains((K) key)).getValue();
		} catch(NullPointerException e){
			return null;
		}
	}

	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		if( containsKey(key) ){
			int idx = KList.contains(key); //returns index
			VList.getIdx(idx).setValue(value);
		}
		else{
			KList.insertAt(VList.insert(value), key);
		}
		return value;
	}

	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toJSON(int window){
		String s = "{\n";
		KList.resetPtr(); VList.resetPtr();
		int count = 0;
		for(int i = 0; i < size() - 1 ;i++){
			s += KList.getPtr().toString() + ": " + VList.getPtr().toString() + ",\n";
			KList.advancePtr(); VList.advancePtr();
		}
		s += KList.getPtr().toString() + ": " + VList.getPtr().toString() + "\n}";
		return s;
	}
	

}
