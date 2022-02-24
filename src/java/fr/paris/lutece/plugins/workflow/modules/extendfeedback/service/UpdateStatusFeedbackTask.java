/*
 * Copyright (c) 2002-2021, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.extendfeedback.service;

import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.plugins.extend.modules.feedback.business.ExtendFeedback;
import fr.paris.lutece.plugins.extend.modules.feedback.service.ExtendFeedbackService;
import fr.paris.lutece.plugins.extend.modules.feedback.service.IExtendFeedbackService;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.business.UpdateStatusFeedbackConfig;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.util.WfExtendFeedbackConstants;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.resource.ResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;

/**
 * 
 * UpdateStatusFeedbackTask
 *
 */
public class UpdateStatusFeedbackTask extends SimpleTask
{

    @Inject
    @Named( WfExtendFeedbackConstants.BEAN_TASK_CONFIG_SERVICE )
    private ITaskConfigService _taskConfigService;

    @Inject
    @Named( ExtendFeedbackService.BEAN_SERVICE )
    private IExtendFeedbackService _extendFeedbackService;

    @Inject
    @Named( ResourceHistoryService.BEAN_SERVICE )
    private IResourceHistoryService _resourceHistoryService;

    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        UpdateStatusFeedbackConfig config = _taskConfigService.findByPrimaryKey( getId( ) );

        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );

        if ( resourceHistory != null )
        {
            Optional<ExtendFeedback> extendFeedback = _extendFeedbackService.findById( resourceHistory.getIdResource( ) );

            if ( extendFeedback.isPresent( ) && config != null )
            {
                extendFeedback.get( ).setStatus( config.isStatus( ) );
                _extendFeedbackService.update( extendFeedback.get( ) );
            }
        }
    }

    @Override
    public String getTitle( Locale locale )
    {
        return I18nService.getLocalizedString( getTaskType( ).getTitleI18nKey( ), locale );
    }

    @Override
    public void doRemoveConfig( )
    {
        _taskConfigService.remove( getId( ) );
    }

}
