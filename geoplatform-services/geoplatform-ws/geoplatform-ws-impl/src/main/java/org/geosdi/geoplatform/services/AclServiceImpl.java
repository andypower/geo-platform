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
package org.geosdi.geoplatform.services;

import java.util.List;
import java.util.Map;
import org.geosdi.geoplatform.configurator.gui.GuiComponentIDs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.geosdi.geoplatform.core.acl.AclEntry;
import org.geosdi.geoplatform.core.acl.AclObjectIdentity;
import org.geosdi.geoplatform.core.acl.AclSid;
import org.geosdi.geoplatform.core.acl.GuiComponent;
import org.geosdi.geoplatform.core.acl.GuiComponentPermission;
import org.geosdi.geoplatform.core.acl.dao.AclClassDAO;
import org.geosdi.geoplatform.core.acl.dao.AclEntryDAO;
import org.geosdi.geoplatform.core.acl.dao.AclObjectIdentityDAO;
import org.geosdi.geoplatform.core.acl.dao.AclSidDAO;
import org.geosdi.geoplatform.core.acl.dao.GuiComponentDAO;
import org.geosdi.geoplatform.core.dao.GPAuthorityDAO;
import org.geosdi.geoplatform.core.dao.GPAccountDAO;
import org.geosdi.geoplatform.core.model.GPAccount;
import org.geosdi.geoplatform.core.model.GPAuthority;
import org.geosdi.geoplatform.exception.ResourceNotFoundFault;
import org.geosdi.geoplatform.responce.RoleDTO;
import org.geosdi.geoplatform.responce.collection.GuiComponentsPermissionMapData;

/**
 * @author Vincenzo Monteverde
 * @email vincenzo.monteverde@geosdi.org - OpenPGP key ID 0xB25F4B38
 * 
 */
class AclServiceImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // DAO
    private GPAccountDAO accountDao;
    private GPAuthorityDAO authorityDao;
    // ACL DAO
    private AclClassDAO classDao;
    private AclSidDAO sidDao;
    private AclObjectIdentityDAO objectIdentityDao;
    private AclEntryDAO entryDao;
    private GuiComponentDAO guiComponentDao;

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    /**
     * @param accountDao
     *          the accountDao to set
     */
    public void setAccountDao(GPAccountDAO accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * @param authorityDao
     *          the authorityDao to set
     */
    public void setAuthorityDao(GPAuthorityDAO authorityDao) {
        this.authorityDao = authorityDao;
    }

    /**
     * @param classDao
     *          the classDao to set
     */
    public void setClassDao(AclClassDAO classDao) {
        this.classDao = classDao;
    }

    /**
     * @param sidDao
     *          the sidDao to set
     */
    public void setSidDao(AclSidDAO sidDao) {
        this.sidDao = sidDao;
    }

    /**
     * @param objectIdentityDao
     *          the objectIdentityDao to set
     */
    public void setObjectIdentityDao(AclObjectIdentityDAO objectIdentityDao) {
        this.objectIdentityDao = objectIdentityDao;
    }

    /**
     * @param entryDao
     *          the entryDao to set
     */
    public void setEntryDao(AclEntryDAO entryDao) {
        this.entryDao = entryDao;
    }

    /**
     * @param guiComponentDao
     *          the guiComponentDao to set
     */
    public void setGuiComponentDao(GuiComponentDAO guiComponentDao) {
        this.guiComponentDao = guiComponentDao;
    }
    //</editor-fold>

    /**
     * @see org.geosdi.geoplatform.services.GeoPlatformService#getAllRoles()
     */
    public List<RoleDTO> getAllRoles() {
        List<AclSid> sids = sidDao.findByPrincipal(false);
        return RoleDTO.convertToRoleDTOList(sids);
    }

    /**
     * @see org.geosdi.geoplatform.services.GeoPlatformService#getAccountGuiComponentPermission(java.lang.Long)
     */
    public GuiComponentsPermissionMapData getAccountGuiComponentPermission(Long accountID)
            throws ResourceNotFoundFault {
        // Retrieve the account
        GPAccount account = accountDao.find(accountID);
        if (account == null) {
            throw new ResourceNotFoundFault("Account not found", accountID);
        }

        GuiComponentsPermissionMapData mapComponentPermission = new GuiComponentsPermissionMapData();

        // Retrieve the Authorities of the Account
        List<GPAuthority> authorities = authorityDao.findByStringID(account.getStringID());
        logger.trace("\n*** #Authorities: {} ***", authorities.size());
        // For each Autorities (disjoined)
        for (GPAuthority authority : authorities) {
            String nameAuthority = authority.getAuthority();
            logger.trace("\n*** nameAuthority: {} ***", nameAuthority);

            this.elaborateGuiComponentACEs(nameAuthority, mapComponentPermission.getPermissionMap());
        }
        return mapComponentPermission;
    }

    /**
     * @see org.geosdi.geoplatform.services.GeoPlatformService#getRoleGuiComponentPermission(java.lang.String)
     */
    public GuiComponentsPermissionMapData getRoleGuiComponentPermission(String role)
            throws ResourceNotFoundFault {
        GuiComponentsPermissionMapData mapComponentPermission = new GuiComponentsPermissionMapData();

        this.elaborateGuiComponentACEs(role, mapComponentPermission.getPermissionMap());

        return mapComponentPermission;
    }

    private void elaborateGuiComponentACEs(String sidName,
                                           Map<String, Boolean> permissionMap)
            throws ResourceNotFoundFault {
        // Retrieve the Sid corresponding to the Role (Authority) name
        AclSid sid = sidDao.findBySid(sidName, false);
        if (sid == null) {
            throw new ResourceNotFoundFault("Authority (Role) \"" + sidName + "\" not found");
        }
        logger.trace("\n*** AclSid:\n{}\n***", sid);
        // Retrieve the ACEs of the Sid
        List<AclEntry> entries = entryDao.findBySid(sid.getId());
        logger.trace("\n*** #Entries: {} ***", entries.size());
        // For each ACEs
        // (ACL has a single ACE for Account+GuiComponent,
        // because there is a singe Permission)
        for (AclEntry entry : entries) {
            logger.trace("\n*** AclEntry:\n{}\n***", entry);
            if (entry.getMask().equals(GuiComponentPermission.ENABLE.getMask())) {
                AclObjectIdentity objectIdentity = entry.getAclObject();
                logger.trace("\n*** AclObjectIdentity:\n{}\n***", objectIdentity);
                GuiComponent gc = guiComponentDao.find(objectIdentity.getObjectId());
                logger.trace("\n*** GuiComponent:\n{}\n***", gc);
                logger.debug("\n*** ComponentId: {} ***\n*** Granting: {} ***",
                             gc.getComponentId(), entry.isGranting());

                permissionMap.put(gc.getComponentId(), entry.isGranting());
            }
        }

        for (String componentID : GuiComponentIDs.LIST_ALL) {
            if (!permissionMap.containsKey(componentID)) {
                logger.debug("\n*** NONE added: {} ***", componentID);
                permissionMap.put(componentID, null);
            }
        }
    }
}
