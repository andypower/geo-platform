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
package org.geosdi.geoplatform.gui.client.model;

import org.geosdi.geoplatform.gui.configuration.map.client.geometry.BboxClientInfo;
import org.geosdi.geoplatform.gui.responce.URIDTO;

/**
 * Full Record for CSW request.
 * 
 * @author Vincenzo Monteverde <vincenzo.monteverde@geosdi.org>
 */
public class FullRecord extends AbstractRecord {

    private static final long serialVersionUID = 7344635200928287521L;

    public enum FullRecordKeyValue {

        BBOX, URI;
    }

    public BboxClientInfo getBBox() {
        return super.get(FullRecordKeyValue.BBOX.toString());
    }

    public void setBBox(BboxClientInfo bBox) {
        super.set(FullRecordKeyValue.BBOX.toString(), bBox);
    }

    public URIDTO getUri() {
        return super.get(FullRecordKeyValue.URI.toString());
    }

    public void setUri(URIDTO uri) {
        super.set(FullRecordKeyValue.URI.toString(), uri);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("FullRecord {");
        str.append(super.toStringBuilder(str));
        str.append(", bBox =").append(this.getBBox());
        str.append(", uri =").append(this.getUri());
        return str.append('}').toString();
    }
}
