/**
 *
 * geo-platform Rich webgis framework http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2014 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version. This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is making a
 * combined work based on this library. Thus, the terms and conditions of the
 * GNU General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce an
 * executable, regardless of the license terms of these independent modules, and
 * to copy and distribute the resulting executable under terms of your choice,
 * provided that you also meet, for each linked independent module, the terms
 * and conditions of the license of that module. An independent module is a
 * module which is not derived from or based on this library. If you modify this
 * library, you may extend this exception to your version of the library, but
 * you are not obligated to do so. If you do not wish to do so, delete this
 * exception statement from your version.
 */
package org.geosdi.geoplatform.gui.server.service.impl;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.geosdi.geoplatform.core.model.GSAccount;
import org.geosdi.geoplatform.core.model.GeoPlatformServer;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.gui.global.GeoPlatformException;
import org.geosdi.geoplatform.gui.model.server.GPLayerGrid;
import org.geosdi.geoplatform.gui.model.server.GPServerBeanModel;
import org.geosdi.geoplatform.gui.server.SessionUtility;
import org.geosdi.geoplatform.gui.server.service.IOGCService;
import org.geosdi.geoplatform.gui.server.service.converter.DTOServerConverter;
import org.geosdi.geoplatform.gui.utility.GPSessionTimeout;
import org.geosdi.geoplatform.request.RequestByID;
import org.geosdi.geoplatform.request.server.WSSaveServerRequest;
import org.geosdi.geoplatform.responce.ServerDTO;
import org.geosdi.geoplatform.services.GPWMSService;
import org.geosdi.geoplatform.services.GeoPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
@Service(value = "ogcService")
public class OGCService implements IOGCService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    // core
    private GeoPlatformService geoPlatformServiceClient;
    // wms
    private GPWMSService geoPlatformWMSServiceClient;
    //
    @Autowired
    private DTOServerConverter dtoServerConverter;
    //
    @Autowired
    private SessionUtility sessionUtility;

    @Override
    public ArrayList<GPServerBeanModel> loadServers(String organizationName)
            throws GeoPlatformException {
        try {
            return dtoServerConverter.convertServer(
                    geoPlatformServiceClient.getAllServers(organizationName));
        } catch (ResourceNotFoundFault ex) {
            logger.error("OGCService Error : " + ex);
            throw new GeoPlatformException(ex);
        }
    }

    @Override
    public GPServerBeanModel getServerDetails(Long idServer)
            throws GeoPlatformException {
        try {
            GeoPlatformServer serverWS = geoPlatformServiceClient.getServerDetail(
                    idServer);
            return dtoServerConverter.getServerDetail(serverWS);
        } catch (ResourceNotFoundFault ex) {
            logger.error("The server with id " + idServer + " was bean deleted.");
            throw new GeoPlatformException(
                    "The server with id " + idServer + " was bean deleted.");
        }
    }

    @Override
    public Boolean deleteServer(Long idServer) throws GeoPlatformException {
        try {
            geoPlatformServiceClient.deleteServer(idServer);
        } catch (ResourceNotFoundFault ex) {
            logger.error(
                    "The server with id " + idServer + " was not bean deleted.");
            throw new GeoPlatformException(
                    "The server with id " + idServer + " was not bean deleted.");
        }
        return true;
    }

    @Override
    public ArrayList<? extends GPLayerGrid> getCapabilities(
            String serverUrl, HttpServletRequest httpServletRequest,
            Long idServer)
            throws GeoPlatformException {
        try {
            HttpSession session = httpServletRequest.getSession();
            String token = (String) session.getAttribute("GOOGLE_TOKEN");

            RequestByID req = new RequestByID(idServer);
            GSAccount gsAccount = this.sessionUtility.getLoggedAccount(
                    httpServletRequest).getGsAccount();
            String authKey = null;
            if (gsAccount != null) {
                authKey = gsAccount.getAuthkey();
            }
            ServerDTO server = geoPlatformWMSServiceClient.getCapabilities(
                    serverUrl, req, token, authKey);

            return dtoServerConverter.createRasterLayerList(
                    server.getLayerList());
        } catch (ResourceNotFoundFault ex) {
            logger.error("Error GetCapabilities: " + ex);
            throw new GeoPlatformException(ex.getMessage());
        } catch (GPSessionTimeout timeout) {
            throw new GeoPlatformException(timeout);
        }
    }

    @Override
    public GPServerBeanModel saveServer(Long id, String aliasServerName,
            String urlServer, String organization)
            throws GeoPlatformException {
        ServerDTO serverWS = null;
        try {
            serverWS = geoPlatformServiceClient.saveServer(
                    new WSSaveServerRequest(id, aliasServerName,
                            urlServer,
                            organization));
        } catch (IllegalParameterFault ex) {
            logger.error(ex.getMessage());
            throw new GeoPlatformException(ex.getMessage());
        }
        return dtoServerConverter.convertServerWS(serverWS);
    }

    @Override
    public ArrayList<String> findDistinctLayersDataSource(
            HttpServletRequest httpServletRequest)
            throws GeoPlatformException {

        try {
            Long projectId = this.sessionUtility.getDefaultProject(
                    httpServletRequest);
            return geoPlatformServiceClient.getLayersDataSourceByProjectID(
                    projectId).getDataSources();
        } catch (ResourceNotFoundFault e) {
            throw new GeoPlatformException(
                    "Error in findDistinctLayersDataSource: ResourceNotFoundFault "
                    + e);
        } catch (GPSessionTimeout timeout) {
            throw new GeoPlatformException(timeout);
        }
    }

    /**
     * @param geoPlatformServiceClient the geoPlatformServiceClient to set
     */
    @Autowired
    public void setGeoPlatformServiceClient(
            @Qualifier("geoPlatformServiceClient") GeoPlatformService geoPlatformServiceClient) {
        this.geoPlatformServiceClient = geoPlatformServiceClient;
    }

    /**
     * @param geoPlatformWMSServiceClient the geoPlatformWMSServiceClient to set
     */
    @Autowired
    public void setGeoPlatformWMSServiceClient(
            @Qualifier("gpWMSServiceClient") GPWMSService geoPlatformWMSServiceClient) {
        this.geoPlatformWMSServiceClient = geoPlatformWMSServiceClient;
    }
}
