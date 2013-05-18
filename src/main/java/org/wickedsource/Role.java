package org.wickedsource;

/**
 * Roles from the spring security tutorial application.
 * 
 * @author tom
 * 
 */
public enum Role {

	SUPERVISOR("supervisor"),

	TELLER("teller"),

	USER("user");

	private final String springSecurityRoleName;

	private Role(String springSecurityRoleName) {
		this.springSecurityRoleName = springSecurityRoleName;
	}

	public String getSpringSecurityRoleName() {
		return springSecurityRoleName;
	}

}
