package com.ftsafe.iccd.personalize.session;

import java.util.HashMap;

public class ClientManager {

	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	private static ClientManager instance;

	private ClientManager() {
	};

	public static ClientManager getInstance() {
		
		if (instance == null)
			instance = new ClientManager();
		
		return instance;
	}

	public void setClient(String key, Object value) {
		hashMap.put(key, value);
	}

	public Object getClient(String key) {
		return hashMap.get(key);
	}
}
