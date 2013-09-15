package Client.Protocol;

import java.math.BigInteger;

import Client.UserHandling.User;

public class ChatSessionUser {
	private User user;
	private BigInteger cry;

	public ChatSessionUser(User user, BigInteger cry) {
		super();
		this.user = user;
		this.cry = cry;
	}

	public User getUser() {
		return user;
	}

	public void setCry(BigInteger cry) {
		this.cry = cry;
	}

	public BigInteger getCry() {
		return cry;
	}
}
