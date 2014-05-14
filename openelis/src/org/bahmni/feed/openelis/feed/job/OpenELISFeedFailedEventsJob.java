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
 * Copyright (C) CIRG, University of Washington, Seattle WA.  All Rights Reserved.
 *
 */
package org.bahmni.feed.openelis.feed.job;

import org.apache.log4j.Logger;
import org.bahmni.webclients.Authenticator;
import org.bahmni.webclients.ConnectionDetails;
import org.bahmni.webclients.openmrs.OpenMRSLoginAuthenticator;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class OpenELISFeedFailedEventsJob extends OpenELISFeedReaderJob {
    
    protected OpenELISFeedFailedEventsJob(Logger logger) {
        super(logger);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        processFailedEvents(jobExecutionContext);
    }

    protected Authenticator getAuthenticator(ConnectionDetails connectionDetails) {
        return new OpenMRSLoginAuthenticator(connectionDetails);
    }
}
