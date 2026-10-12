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
package fr.paris.lutece.plugins.workflow.modules.extendfeedback.web;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.extend.modules.feedback.util.constants.StatusFeedbackEnum;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.util.WfExtendFeedbackConstants;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * UpdateStatusFeedbackTaskComponent
 *
 */
@ApplicationScoped
@Named( "workflow-extendfeedback.updateStatusFeedbackComponent" )
public class UpdateStatusFeedbackTaskComponent extends NoFormTaskComponent
{
    // TEMPLATE
    private static final String TEMPLATE_CONFIG = "admin/plugins/workflow/modules/extendfeedback/update_status_task_config_form.html";

    /**
     * Constructor for the CDI proxy.
     */
    UpdateStatusFeedbackTaskComponent( )
    {
    }

    /**
     * Builds the component of the update status task.
     *
     * @param taskType
     *            the task type
     * @param taskConfigService
     *            the task configuration service
     */
    @Inject
    public UpdateStatusFeedbackTaskComponent( @Named( WfExtendFeedbackConstants.BEAN_TASK_TYPE ) ITaskType taskType,
            @Named( WfExtendFeedbackConstants.BEAN_TASK_CONFIG_SERVICE ) ITaskConfigService taskConfigService )
    {
        setTaskType( taskType );
        setTaskConfigService( taskConfigService );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<>( );

        model.put( WfExtendFeedbackConstants.MARK_STATUS_LIST, StatusFeedbackEnum.getReferenceList( false ) );
        model.put( WfExtendFeedbackConstants.MARK_CONFIG, getTaskConfigService( ).findByPrimaryKey( task.getId( ) ) );

        HtmlTemplate html = AppTemplateService.getTemplate( TEMPLATE_CONFIG, locale, model );

        return html.getHtml( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return StringUtils.EMPTY;
    }

}
