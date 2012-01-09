/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.geosdi.geoplatform.gui.client.action.menu;

import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Window;
import org.geosdi.geoplatform.gui.action.menu.MenuAction;
import org.geosdi.geoplatform.gui.client.model.RasterTreeNode;
import org.geosdi.geoplatform.gui.model.tree.GPBeanTreeModel;

/**
 *
 * @author Francesco Izzi - CNR IMAA geoSDI Group
 * @mail francesco.izzi@geosdi.org
 */
public class ExportoToTIFF extends MenuAction {

    private TreePanel treePanel;

    public ExportoToTIFF(TreePanel treePanel) {
        super("ExportToTIFF");
        this.treePanel = treePanel;
    }

    @Override
    public void componentSelected(MenuEvent ce) {
        GPBeanTreeModel item = (GPBeanTreeModel) this.treePanel.getSelectionModel().getSelectedItem();

        if (item instanceof RasterTreeNode) {
            String dataSource = ((RasterTreeNode) item).getDataSource();
            String tiffUrl = "";
            // kml preview
            if (dataSource.contains("geoserver")) {
                tiffUrl = dataSource
                        + "/reflect?&layers="
                        + ((RasterTreeNode) item).getName()
                        + "&width=1024&format=image/tiff&format_options=dpi:600";
            } else {
                tiffUrl = dataSource
                        + "?service=WMS&request=GetMap&version=1.1.1&format=image/tiff&width=1024&height=768&srs=EPSG:4326&layers="
                        + ((RasterTreeNode) item).getName()
                        + "&bbox=" + ((RasterTreeNode) item).getBbox().getLowerLeftX()
                        + "," + ((RasterTreeNode) item).getBbox().getLowerLeftY()
                        + "," + ((RasterTreeNode) item).getBbox().getUpperRightX()
                        + "," + ((RasterTreeNode) item).getBbox().getUpperRightY();
            }

            System.out.println(tiffUrl);

            Window.open(tiffUrl, tiffUrl, tiffUrl);
        }

    }
}
