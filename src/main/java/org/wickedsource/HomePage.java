package org.wickedsource;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		add(new FeedbackPanel("feedback"));
		
		add(new Link<Void>("logoutLink"){

			@Override
			public void onClick() {
				getSession().invalidate();				
			}
			
		});

		add(new ListView<Account>("accountList",
				InMemoryDatabase.getAllAccounts()) {

			@Override
			protected void populateItem(ListItem<Account> item) {
				item.add(new Label("id"));
				item.add(new Label("holder"));
				item.add(new Label("balance"));
				item.add(new Label("overdraft"));
				item.add(new CreditAccountLink("debit20Link", item.getModel(),
						-20d));
				item.add(new CreditAccountLink("debit5Link", item.getModel(),
						-5d));
				item.add(new CreditAccountLink("credit20Link", item.getModel(),
						+20d));
				item.add(new CreditAccountLink("credit5Link", item.getModel(),
						+5d));
			}

			@Override
			protected ListItem<Account> newItem(int index,
					IModel<Account> itemModel) {
				return super.newItem(index, new CompoundPropertyModel<Account>(
						itemModel));
			}

		});

	}
}
