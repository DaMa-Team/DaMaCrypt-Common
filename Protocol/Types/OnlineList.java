package Client.Protocol.Types;

import Client.UserHandling.User;

public class OnlineList {
	private User[] online;

	public OnlineList(User[] online) {
		this.online = online;
	}

	public User[] getOnline() {
		return online;
	}
}
