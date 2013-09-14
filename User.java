package Client;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3019405117363744844L;
	private String name;
	// private BigInteger cry;
	private long clientID;

	public User(String name, long server_id) {
		super();
		this.name = name;
		this.clientID = server_id;
		// cry = BigInteger.ZERO;
	}

	public User(User pattern, BigInteger cry) {
		this.name = pattern.getName();
		this.clientID = pattern.getClientId();
		// this.cry = cry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * public void setCry(BigInteger cry) { this.cry = cry; }
	 */

	/*
	 * public BigInteger getCry() { return cry; }
	 */

	public long getClientId() {
		return clientID;
	}

	/*
	 * public void setCry(BigInteger cry) { this.cry = cry; }
	 */
}
