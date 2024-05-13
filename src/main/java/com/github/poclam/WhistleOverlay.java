package com.github.poclam;

import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.*;

public class WhistleOverlay extends WidgetItemOverlay {

    private final RumourPlugin plugin;

    @Inject
    WhistleOverlay(RumourPlugin plugin) {
        this.plugin = plugin;
        showOnInventory();
    }

    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        if (itemId != ItemID.BASIC_QUETZAL_WHISTLE && itemId != ItemID.ENHANCED_QUETZAL_WHISTLE && itemId != ItemID.PERFECTED_QUETZAL_WHISTLE)
        {
            return;
        }
        Integer whistleCharges = plugin.getWhistleCharges();

        String text = whistleCharges == -1 ? "?" : whistleCharges + "";

        drawString(graphics, text, widgetItem.getCanvasBounds().x, widgetItem.getCanvasBounds().y + 10);
    }

    private void drawString(Graphics2D graphics, String text, int drawX, int drawY)
    {
        graphics.setColor(Color.BLACK);
        graphics.drawString(text, drawX + 1, drawY + 1);
        graphics.setColor(Color.WHITE);
        graphics.drawString(text, drawX, drawY);
    }
}
