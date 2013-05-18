package org.wickedsource;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class CreditAccountLink extends Link<Account> {

	private double amount;

	public CreditAccountLink(String id, IModel<Account> model, double amount) {
		super(id, model);
		this.amount = amount;
	}

	@Override
	public void onClick() {
		SecureWebSession session = (SecureWebSession) getSession();

		// SUPERVISOR may do anything
		if(session.hasRole(Role.SUPERVISOR)){
			doTransaction();
			return;
		}
		
		// TELLER may debit account up to overdraft limit
		if(session.hasRole(Role.TELLER)){
			Account account = getModelObject();
			if(account.getBalance() + amount >= -account.getOverdraft()){
				doTransaction();
			}else{
				getPage().error("You are not allowed to exceed the overdraft limit!");
			}
			return;
		}
		
		// USER may not debit or credit account
		getPage().error("You are not allowed to post to this account!");
		
	}

	private void doTransaction() {
		getModelObject().credit(amount);
	}

}
