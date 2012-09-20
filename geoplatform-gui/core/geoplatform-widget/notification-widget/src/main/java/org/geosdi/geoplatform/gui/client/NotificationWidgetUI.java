package org.geosdi.geoplatform.gui.client;

import com.google.gwt.core.client.EntryPoint;
import org.geosdi.geoplatform.configurator.gui.GuiComponentIDs;
import org.geosdi.geoplatform.gui.action.ToolbarAction;
import org.geosdi.geoplatform.gui.action.ToolbarActionCreator;
import org.geosdi.geoplatform.gui.action.ToolbarActionRegistar;
import org.geosdi.geoplatform.gui.client.config.BasicGinInjector;
import org.geosdi.geoplatform.gui.client.config.NotificationGinInjector;
import org.geosdi.geoplatform.gui.impl.map.GeoPlatformMap;

/**
 * @author Nazzareno Sileno - CNR IMAA geoSDI Group
 * @email nazzareno.sileno@geosdi.org
 */
public class NotificationWidgetUI implements EntryPoint {

    @Override
    public void onModuleLoad() {
        ToolbarActionRegistar registar = BasicGinInjector.MainInjector.getInstance().getToolbarActionRegistar();
        registar.put(GuiComponentIDs.NOTIFICATION_MENU,
                new ToolbarActionCreator() {
                    @Override
                    public ToolbarAction createActionTool(GeoPlatformMap map) {
                        return NotificationGinInjector.MainInjector.getInstance().
                                getNotificationCenterMenuAction();
                    }
                });
    }
}
