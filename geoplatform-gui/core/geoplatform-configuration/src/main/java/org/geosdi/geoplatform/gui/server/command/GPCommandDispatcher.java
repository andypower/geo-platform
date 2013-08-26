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
package org.geosdi.geoplatform.gui.server.command;

import javax.servlet.http.HttpServletRequest;
import org.geosdi.geoplatform.gui.command.api.GPCommandRequest;
import org.geosdi.geoplatform.gui.command.api.GPCommandResponse;
import org.geosdi.geoplatform.gui.command.server.CommandDispatcher;
import org.geosdi.geoplatform.gui.command.server.GPCommand;
import org.geosdi.geoplatform.gui.global.GeoPlatformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 */
public class GPCommandDispatcher implements CommandDispatcher {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    private ApplicationContext appContext;

    @Override
    public <Request extends GPCommandRequest, Response extends GPCommandResponse> Response execute(
            Request request, HttpServletRequest httpServletRequest) {
        logger.debug("GPCommandDipatcher : Execution of Command "
                + request.getCommandName());

        GPCommand command = findCommand(request);

        logger.debug("Found Command " + request.getCommandName()
                + " : " + command);

        return (Response) command.execute(request, httpServletRequest);
    }

    @Override
    public void destroy() {
        logger.info("GPCommandDispatcher destroy --------------"
                + "-----------------------------> ");
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws
            BeansException {
        this.appContext = ac;
    }

    private <Request extends GPCommandRequest> GPCommand findCommand(
            Request request) throws GeoPlatformException {
        GPCommand command = appContext.getBean(request.getCommandName(),
                GPCommand.class);

        if (command == null) {
            logger.error("There is no Command with name : "
                    + request.getCommandName() + " defined in Spring "
                    + "Application Context.");
            throw new GeoPlatformException("No Command with name : "
                    + request.getCommandName() + " found.");
        }

        return command;
    }

}