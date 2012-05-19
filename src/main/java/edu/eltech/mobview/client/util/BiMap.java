package edu.eltech.mobview.client.util;

import java.util.HashMap;
import java.util.Map;

public class BiMap<F, S> {
	private Map<F, S> firstMap = new HashMap<F, S>();
	private Map<S, F> secondMap = new HashMap<S, F>();
	
	public void put(F fisrstAttr, S secondAttr) {
		firstMap.put(fisrstAttr, secondAttr);
		secondMap.put(secondAttr, fisrstAttr);
	}
	
	public F findFirst(S keySecond) {
		return secondMap.get(keySecond);		
	}
	
	public S findSecond(F keyFirst) {
		return firstMap.get(keyFirst);
	}
	
	public void removePair(F keyFirst, S keySecond) {
		if (!firstMap.get(firstMap).equals(keySecond) || 
				!secondMap.get(keySecond).equals(keyFirst)) {
			throw new IllegalArgumentException("pair does not exist");
		}
		
		firstMap.remove(keyFirst);
		secondMap.remove(keySecond);
	}
}

