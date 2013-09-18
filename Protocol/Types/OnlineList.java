package Client.Protocol.Types;

import java.util.ArrayList;

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
