/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2012 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details. You should have received a copy of the GNU General
 * Public License along with this program. If not, see http://www.gnu.org/licenses/
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is
 * making a combined work based on this library. Thus, the terms and
 * conditions of the GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you permission
 * to link this library with independent modules to produce an executable, regardless
 * of the license terms of these independent modules, and to copy and distribute
 * the resulting executable under terms of your choice, provided that you also meet,
 * for each linked independent module, the terms and conditions of the license of
 * that module. An independent module is a module which is not derived from or
 * based on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obligated to do so. If you do not
 * wish to do so, delete this exception statement from your version.
 *
 */
package org.geosdi.geoplatform.gui.client.widget.toolbar.mediator;

import java.util.List;
import org.geosdi.geoplatform.gui.client.model.FolderTreeNode;
import org.geosdi.geoplatform.gui.client.model.GPRootTreeNode;
import org.geosdi.geoplatform.gui.client.model.RasterTreeNode;
import org.geosdi.geoplatform.gui.model.tree.AbstractFolderTreeNode;
import org.geosdi.geoplatform.gui.model.tree.AbstractRootTreeNode;
import org.geosdi.geoplatform.gui.model.tree.GPBeanTreeModel;
import org.geosdi.geoplatform.gui.model.tree.GPLayerTreeModel;
import org.geosdi.geoplatform.gui.model.tree.TreeStatusEnum;
import org.geosdi.geoplatform.gui.plugin.tree.addlayer.AddLayerPluginManager;
import org.geosdi.geoplatform.gui.plugin.tree.addlayer.IAddLayerPlugin;
import org.geosdi.geoplatform.gui.plugin.tree.toolbar.ITreeToolbarPlugin;
import org.geosdi.geoplatform.gui.plugin.tree.toolbar.TreeToolbarPluginManager;

/**
 * @author Nazzareno Sileno - CNR IMAA geoSDI Group
 * @email nazzareno.sileno@geosdi.org
 */
public class MediatorToolbarTreeAction {

    private static MediatorToolbarTreeAction instance = new MediatorToolbarTreeAction();
    private static boolean addLayerVisible = false;

    private MediatorToolbarTreeAction() {
    }

    public static MediatorToolbarTreeAction getInstance() {
        return instance;
    }

    /**
     * @return the addLayerVisible
     */
    public boolean isAddLayerVisible() {
        return addLayerVisible;
    }

    /**
     * @param aAddLayerVisible the addLayerVisible to set
     */
    public void setAddLayerVisible(boolean aAddLayerVisible) {
        addLayerVisible = aAddLayerVisible;
    }

    /**
     *
     * @param elements
     */
    public void elementChanged(List<GPBeanTreeModel> elements) {
        TreeStatusEnum status = this.calculateTreeStatus(elements);
        for (ITreeToolbarPlugin plugin : TreeToolbarPluginManager.getToolbarPlugin()) {
            plugin.setEnabledByStatus(status);
        }
        if (addLayerVisible) {
            this.manageAddLayerPluginAction(status);
        }
    }

    private void manageAddLayerPluginAction(TreeStatusEnum status) {
        for (IAddLayerPlugin plugin : AddLayerPluginManager.getWindowPlugins()) {
            plugin.setEnabledByStatus(status);
        }
    }

    public void manageAddLayerPluginAction(List<GPBeanTreeModel> elements) {
        if (addLayerVisible) {
            TreeStatusEnum status = this.calculateTreeStatus(elements);
            this.manageAddLayerPluginAction(status);
        }
    }

    private TreeStatusEnum calculateTreeStatus(List<GPBeanTreeModel> elements) {
        return (elements == null || elements.isEmpty())
                ? TreeStatusEnum.NO_SELECTION : (elements.size() > 1)
                ? TreeStatusEnum.MULTI_SELECTION : elements.get(0).getTreeStatus();
    }
}