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
package org.geosdi.geoplatform.catalog.csw;

import java.util.Collection;
import java.util.List;
import junit.framework.Assert;
import org.geosdi.geoplatform.core.model.GPCapabilityType;
import org.geosdi.geoplatform.core.model.GeoPlatformServer;
import org.geosdi.geoplatform.exception.IllegalParameterFault;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.gui.responce.CatalogFinderBean;
import org.geosdi.geoplatform.gui.responce.SearchInfo;
import org.geosdi.geoplatform.responce.SummaryRecordDTO;
import org.geosdi.geoplatform.services.GeoPlatformCSWService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-Test.xml",
    "classpath*:applicationContext.xml"})
@TestExecutionListeners(value = {CSWListenerServices.class})
public class CSWCatalogTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //
    private GeoPlatformCSWService cswService;
    //
    private Long serverTestOurID;
    private Long serverTestTrevisoID;
    private CatalogFinderBean catalogFinder;

    /**
     * The listener will inject this dependency
     */
    public void setCSWService(GeoPlatformCSWService cswService) {
        this.cswService = cswService;
    }

    @Before
    public void setUp() throws Exception {
        logger.trace("\n\t@@@ {}.setUp @@@", this.getClass().getSimpleName());

        // Insert the servers test
        serverTestOurID = cswService.insertServerCSW(this.createCSWServer(
                "CSW Server WS Test",
                "http://150.146.160.152/geonetwork/srv/en/csw"));
        serverTestTrevisoID = cswService.insertServerCSW(this.createCSWServer(
                "Provincia di Treviso",
                "http://ows.provinciatreviso.it/geonetwork/srv/it/csw"));

        // Create the CSW search parameters
        catalogFinder = new CatalogFinderBean();
        catalogFinder.setServerID(serverTestOurID);
        SearchInfo searchInfo = new SearchInfo();
        searchInfo.setSearchText("");
        searchInfo.setSearchTitle(true);
        searchInfo.setSearchAbstract(true);
        searchInfo.setSearchSubjects(true);
        catalogFinder.setSearchInfo(searchInfo);
    }

    private GeoPlatformServer createCSWServer(String title, String url) {
        GeoPlatformServer server = new GeoPlatformServer();
        server.setServerType(GPCapabilityType.CSW);
        server.setTitle(title);
        server.setServerUrl(url);
        return server;
    }

    @After
    public void tearDown() throws ResourceNotFoundFault {
        logger.trace("\n\t@@@ {}.tearDown @@@", this.getClass().getSimpleName());

        // Delete the servers test
        cswService.deleteServerCSW(serverTestOurID);
        cswService.deleteServerCSW(serverTestTrevisoID);
    }
//
//    @Test
//    public void testGetRecordsOurCount() throws IllegalParameterFault, ResourceNotFoundFault {
//        catalogFinder.getSearchInfo().setSearchText("land");
//        Assert.assertEquals(4, cswService.getSummaryRecordsCount(catalogFinder));
//    }
//
//    @Test
//    public void testGetRecordsOurResult() throws IllegalParameterFault, ResourceNotFoundFault {
//        catalogFinder.getSearchInfo().setSearchText("land");
//        List<SummaryRecordDTO> summaryRecords = cswService.searchSummaryRecords(25, 0, catalogFinder);
//        this.traceCollection(summaryRecords);
//        Assert.assertEquals(4, summaryRecords.size());
//    }

    @Test
    public void testGetRecordsTrevisoSearchWMSText() throws IllegalParameterFault, ResourceNotFoundFault {
        catalogFinder.setServerID(serverTestTrevisoID);
        catalogFinder.getSearchInfo().setSearchText("wms");

        int num = 10;
        int recordsMatched = cswService.getSummaryRecordsCount(catalogFinder);
        Assert.assertEquals(295, recordsMatched);
        logger.debug("\n*** Records matched: {} *** Result for page: {} ***",
                recordsMatched, num);

        List<SummaryRecordDTO> summaryRecords;
        int pages = (recordsMatched / num);
        int mod = recordsMatched % num;
        if (mod > 0) {
            pages++;
        }
        logger.debug("\n*** Pages: {} *** Module: {} ***", pages, mod);
        int start;
        for (int i = 1; i < pages; i++) {
            start = (num * (i - 1)) + 1;
            logger.debug("\n*** page: {} *** start: {} ***", i, start);

            summaryRecords = cswService.searchSummaryRecords(num, start, catalogFinder);
            this.traceCollection(summaryRecords);
            Assert.assertEquals(num, summaryRecords.size());
        }

        // Last page
        if (mod > 0) {
            start = (num * (pages - 1)) + 1;
            summaryRecords = cswService.searchSummaryRecords(num, start, catalogFinder);
            this.traceCollection(summaryRecords);
            Assert.assertEquals(mod, summaryRecords.size());
        }
    }

    private void traceCollection(Collection collection) {
        for (Object object : collection) {
            logger.trace("\n*** " + object);
        }
    }
}
