package org.wickedsource;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {
	
	private static List<Account> accounts = new ArrayList<Account>();
	
	static{
		accounts.add(new Account(1l, "rod", 0d, 100d));
		accounts.add(new Account(2l, "dianne", 0d, 100d));
		accounts.add(new Account(3l, "scott", 0d, 100d));
		accounts.add(new Account(4l, "peter", 0d, 100d));
		
	}
	
	public static List<Account> getAllAccounts(){
		return accounts;
	}

}
