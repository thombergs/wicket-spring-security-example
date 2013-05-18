package org.wickedsource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.flow.RedirectToUrlException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

public class LoginPage extends WebPage {

	private static Logger logger = Logger.getLogger(LoginPage.class);

	public LoginPage(PageParameters parameters) {
		add(new LoginForm("loginForm"));
	}

	private class LoginForm extends Form<Void> {

		private transient RequestCache requestCache = new HttpSessionRequestCache();

		private String username;

		private String password;

		public LoginForm(String id) {
			super(id);
			setModel(new CompoundPropertyModel(this));
			add(new RequiredTextField<String>("username"));
			add(new PasswordTextField("password"));
			add(new FeedbackPanel("feedback"));
		}

		@Override
		protected void onSubmit() {
			HttpServletRequest servletRequest = (HttpServletRequest) RequestCycle.get().getRequest().getContainerRequest();
			String originalUrl = getOriginalUrl(servletRequest.getSession());
			AuthenticatedWebSession session = AuthenticatedWebSession.get();
			if (session.signIn(username, password)) {
				if (originalUrl != null) {
					logger.info(String.format("redirecting to %s", originalUrl));
					throw new RedirectToUrlException(originalUrl);
				} else {
					logger.info("redirecting to home page");
					setResponsePage(getApplication().getHomePage());
				}
			} else {
				error("Login failed due to invalid credentials");
			}
		}

		/**
		 * Returns the URL the user accessed before he was redirected to the login page. This URL has been stored in the session by spring
		 * security.
		 * 
		 * @return the original URL the user accessed or null if no URL has been stored in the session.
		 */
		private String getOriginalUrl(HttpSession session) {
			// TODO: The following session attribute seems to be null the very first time a user accesses a secured page. Find out why
			// spring security doesn't set this parameter the very first time.
			SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
			if (savedRequest != null) {
				return savedRequest.getRedirectUrl();
			} else {
				return null;
			}
		}

	}

}
