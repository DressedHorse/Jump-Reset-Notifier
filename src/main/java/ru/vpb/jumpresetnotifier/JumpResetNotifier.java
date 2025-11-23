package ru.vpb.jumpresetnotifier;

import net.fabricmc.api.ClientModInitializer;
import ru.vpb.jumpresetnotifier.config.ConfigManager;
import ru.vpb.jumpresetnotifier.draggables.JRNotiferDraggable;

public class JumpResetNotifier implements ClientModInitializer {
	public static final String MOD_ID = "jumpreset-notifier";

	public static final JRNotiferDraggable jrnDraggable = new JRNotiferDraggable();

	public static long jumpTime = 500;
	public static long hurt9Time = 0;

	@Override
	public void onInitializeClient() {
		ConfigManager.load();
	}
}