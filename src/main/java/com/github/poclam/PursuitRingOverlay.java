package com.github.poclam;

import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import javax.inject.Inject;
import java.awt.*;

public class PursuitRingOverlay extends WidgetItemOverlay {

    private final RumourPlugin plugin;

    @Inject
    PursuitRingOverlay(RumourPlugin plugin) {
        this.plugin = plugin;
        showOnInventory();
    }

    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem widgetItem)
    {
        if (itemId != ItemID.RING_OF_PURSUIT)
        {
            return;
        }
        Integer ringCharges = plugin.getRingCharges();

        String text = ringCharges == -1 ? "?" : ringCharges + "";

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
