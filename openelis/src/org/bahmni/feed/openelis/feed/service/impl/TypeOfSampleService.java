/*
* The contents of this file are subject to the Mozilla Public License
* Version 1.1 (the "License"); you may not use this file except in
* compliance with the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/ 
* 
* Software distributed under the License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
* License for the specific language governing rights and limitations under
* the License.
* 
* The Original Code is OpenELIS code.
* 
* Copyright (C) The Minnesota Department of Health.  All Rights Reserved.
*/

package org.bahmni.feed.openelis.feed.service.impl;

import org.bahmni.feed.openelis.feed.contract.bahmnireferencedata.ReferenceDataSample;
import org.bahmni.feed.openelis.utils.AuditingService;
import us.mn.state.health.lims.login.daoimpl.LoginDAOImpl;
import us.mn.state.health.lims.siteinformation.daoimpl.SiteInformationDAOImpl;
import us.mn.state.health.lims.typeofsample.dao.TypeOfSampleDAO;
import us.mn.state.health.lims.typeofsample.daoimpl.TypeOfSampleDAOImpl;
import us.mn.state.health.lims.typeofsample.valueholder.TypeOfSample;

import java.sql.Timestamp;
import java.util.Date;

public class TypeOfSampleService {

    public static final String DEFAULT_DOMAIN = "H";
    private TypeOfSampleDAO typeOfSampleDAO;
    private AuditingService auditingService;

    public TypeOfSampleService() {
        this(new TypeOfSampleDAOImpl(), new AuditingService(new LoginDAOImpl(), new SiteInformationDAOImpl()));
    }

    public TypeOfSampleService(TypeOfSampleDAO typeOfSampleDAO, AuditingService auditingService) {
        this.typeOfSampleDAO = typeOfSampleDAO;
        this.auditingService = auditingService;
    }

    public void createOrUpdate(ReferenceDataSample sample) {
        String sysUserId = auditingService.getSysUserId();
        TypeOfSample typeOfSample = typeOfSampleDAO.getTypeOfSampleByUUID(sample.getId());

        if (typeOfSample == null) {
            create(sample, sysUserId);
        } else {
            update(typeOfSample, sample, sysUserId);
        }
    }

    private void update(TypeOfSample typeOfSample, ReferenceDataSample sample, String sysUserId) {
        typeOfSample = populateTypeOfSample(typeOfSample, sample, sysUserId);
        typeOfSampleDAO.updateData(typeOfSample);
    }

    private void create(ReferenceDataSample sample, String sysUserId) {
        TypeOfSample typeOfSample = new TypeOfSample();
        typeOfSample = populateTypeOfSample(typeOfSample, sample, sysUserId);
        typeOfSampleDAO.insertData(typeOfSample);
    }

    private TypeOfSample populateTypeOfSample(TypeOfSample typeOfSample, ReferenceDataSample sample, String sysUserId) {
        typeOfSample.setUuid(sample.getId());
        typeOfSample.setLocalAbbreviation(sample.getShortName());
        typeOfSample.setDescription(sample.getName());
        String isActive = sample.getIsActive() ? "Y" : "N";
        typeOfSample.setIsActive(isActive);
        typeOfSample.setSortOrder(sample.getSortOrder());
        typeOfSample.setLastupdated(new Timestamp(new Date().getTime()));
        typeOfSample.setSysUserId(sysUserId);
        typeOfSample.setDomain(DEFAULT_DOMAIN);
        return typeOfSample;
    }

}
