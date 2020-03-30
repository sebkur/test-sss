package de.mobanisto;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class TestNotify
{

	public interface LibNotify extends Library
	{
		LibNotify INSTANCE = Native.load("notify", LibNotify.class);

		void notify_init(String appName);

		Pointer notify_notification_new(String summary, String body,
				String icon);

		void notify_notification_show(Pointer notification, Pointer error);
	}

	public static void main(String[] args)
	{
		LibNotify.INSTANCE.notify_init("jna sandbox");

		Pointer notification = LibNotify.INSTANCE.notify_notification_new(
				"Hey there", "You've got 13 new messages",
				"/usr/share/pixmaps/thunderbird.png");

		LibNotify.INSTANCE.notify_notification_show(notification, null);
	}

}
