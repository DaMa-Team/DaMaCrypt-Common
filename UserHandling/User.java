package Client.UserHandling;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3019405117363744844L;
	private String name;
	private long clientID;

	public User(String name, long server_id) {
		super();
		this.name = name;
		this.clientID = server_id;
	}

	public User(User pattern, BigInteger cry) {
		this.name = pattern.getName();
		this.clientID = pattern.getClientId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getClientId() {
		return clientID;
	}

}
