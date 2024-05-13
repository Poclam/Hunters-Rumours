package com.github.poclam;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@PluginDescriptor(
	name = "Hunter's Rumours"
)
public class RumourPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private RumourConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private WhistleOverlay overlay;
	private Integer whistleCharges = -1;
	@Inject
	private PursuitRingOverlay pursuitRingOverlay;
	private Integer ringCharges = -1;
	private static final List<Integer> QUETZAL_WHISTLE_IDS = Arrays.asList(ItemID.BASIC_QUETZAL_WHISTLE,ItemID.ENHANCED_QUETZAL_WHISTLE,ItemID.PERFECTED_QUETZAL_WHISTLE);
	private static final Pattern WHISTLE_CHARGES_PATTERN = Pattern.compile("Your quetzal whistle has (\\d+) charges remaining.");
	private static final Pattern RING_CHARGES_PATTERN = Pattern.compile("Your ring of pursuit has (\\d+) charges left.");

	@Override
	protected void startUp() throws Exception
	{
		log.info("Rumour Tracker started!");

		overlayManager.add(overlay);
		overlayManager.add(pursuitRingOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
		overlayManager.remove(pursuitRingOverlay);

		log.info("Rumour Tracker stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Example says " + config.greeting(), null);
		}
	}
	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (event.getMenuOption().equals("Check") && QUETZAL_WHISTLE_IDS.contains(event.getItemId())) {
			log.info("Player used 'Check' option on a Quetzal whistle!");
		}
	}
	@Subscribe
	public void onChatMessage(ChatMessage event) {
		String message = event.getMessage();
		Matcher matcher = WHISTLE_CHARGES_PATTERN.matcher(message);

		if (matcher.matches()) {
			int charges = Integer.parseInt(matcher.group(1));
			whistleCharges = charges;
			return;
		}

		matcher = RING_CHARGES_PATTERN.matcher(message);
		if (matcher.matches()) {
			int charges = Integer.parseInt(matcher.group(1));
			ringCharges = charges;
		}
	}

	public Integer getWhistleCharges() {
		return whistleCharges;
	}

	public Integer getRingCharges() {
		return ringCharges;
	}

	@Provides
	RumourConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(RumourConfig.class);
	}
}
