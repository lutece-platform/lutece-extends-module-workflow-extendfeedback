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

import org.eclipse.microprofile.config.inject.ConfigProperty;

import fr.paris.lutece.plugins.workflow.modules.extendfeedback.business.FeedbackNotificationTaskConfig;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.business.UpdateStatusFeedbackConfig;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.util.WfExtendFeedbackConstants;
import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.plugins.workflowcore.business.task.ITaskType;
import fr.paris.lutece.plugins.workflowcore.business.task.TaskType;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.config.TaskConfigService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

/**
 * Produces the task types and the configuration services of the feedback tasks.
 */
@ApplicationScoped
public class ExtendFeedbackTaskProducer
{
    /**
     * Produces the update status task type.
     *
     * @param strKey
     *            the task type key
     * @param strTitleI18nKey
     *            the i18n key of the task title
     * @param strBeanName
     *            the name of the task bean
     * @param strConfigBeanName
     *            the name of the task configuration bean
     * @param bConfigRequired
     *            whether the task needs a configuration
     * @param bFormTaskRequired
     *            whether the task displays a form
     * @param bTaskForAutomaticAction
     *            whether the task can run in an automatic action
     * @return the task type
     */
    @Produces
    @ApplicationScoped
    @Named( WfExtendFeedbackConstants.BEAN_TASK_TYPE )
    public ITaskType produceUpdateStatusTaskType( @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.key" ) String strKey,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.titleI18nKey" ) String strTitleI18nKey,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.beanName" ) String strBeanName,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.configBeanName" ) String strConfigBeanName,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.configRequired", defaultValue = "false" ) boolean bConfigRequired,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.formTaskRequired", defaultValue = "false" ) boolean bFormTaskRequired,
            @ConfigProperty( name = "workflow-extendfeedback.taskUpdateStatusFeedback.taskForAutomaticAction", defaultValue = "false" ) boolean bTaskForAutomaticAction )
    {
        return buildTaskType( strKey, strTitleI18nKey, strBeanName, strConfigBeanName, bConfigRequired, bFormTaskRequired, bTaskForAutomaticAction );
    }

    /**
     * Produces the feedback notification task type.
     *
     * @param strKey
     *            the task type key
     * @param strTitleI18nKey
     *            the i18n key of the task title
     * @param strBeanName
     *            the name of the task bean
     * @param strConfigBeanName
     *            the name of the task configuration bean
     * @param bConfigRequired
     *            whether the task needs a configuration
     * @param bFormTaskRequired
     *            whether the task displays a form
     * @param bTaskForAutomaticAction
     *            whether the task can run in an automatic action
     * @return the task type
     */
    @Produces
    @ApplicationScoped
    @Named( WfExtendFeedbackConstants.BEAN_NOTIF_TASK_TYPE )
    public ITaskType produceNotificationTaskType( @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.key" ) String strKey,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.titleI18nKey" ) String strTitleI18nKey,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.beanName" ) String strBeanName,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.configBeanName" ) String strConfigBeanName,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.configRequired", defaultValue = "false" ) boolean bConfigRequired,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.formTaskRequired", defaultValue = "false" ) boolean bFormTaskRequired,
            @ConfigProperty( name = "workflow-extendfeedback.taskFeedbackNotification.taskForAutomaticAction", defaultValue = "false" ) boolean bTaskForAutomaticAction )
    {
        return buildTaskType( strKey, strTitleI18nKey, strBeanName, strConfigBeanName, bConfigRequired, bFormTaskRequired, bTaskForAutomaticAction );
    }

    /**
     * Produces the configuration service of the update status task.
     *
     * @param taskConfigDAO
     *            the configuration DAO
     * @return the configuration service
     */
    @Produces
    @ApplicationScoped
    @Named( WfExtendFeedbackConstants.BEAN_TASK_CONFIG_SERVICE )
    public ITaskConfigService produceUpdateStatusConfigService(
            @Named( WfExtendFeedbackConstants.BEAN_TASK_CONFIG_DAO ) ITaskConfigDAO<UpdateStatusFeedbackConfig> taskConfigDAO )
    {
        return buildConfigService( taskConfigDAO );
    }

    /**
     * Produces the configuration service of the feedback notification task.
     *
     * @param taskConfigDAO
     *            the configuration DAO
     * @return the configuration service
     */
    @Produces
    @ApplicationScoped
    @Named( WfExtendFeedbackConstants.BEAN_NOTIF_CONFIG )
    public ITaskConfigService produceNotificationConfigService(
            @Named( WfExtendFeedbackConstants.BEAN_NOTIF_CONFIG_DAO ) ITaskConfigDAO<FeedbackNotificationTaskConfig> taskConfigDAO )
    {
        return buildConfigService( taskConfigDAO );
    }

    /**
     * Builds a task type.
     *
     * @param strKey
     *            the task type key
     * @param strTitleI18nKey
     *            the i18n key of the task title
     * @param strBeanName
     *            the name of the task bean
     * @param strConfigBeanName
     *            the name of the task configuration bean
     * @param bConfigRequired
     *            whether the task needs a configuration
     * @param bFormTaskRequired
     *            whether the task displays a form
     * @param bTaskForAutomaticAction
     *            whether the task can run in an automatic action
     * @return the task type
     */
    private static ITaskType buildTaskType( String strKey, String strTitleI18nKey, String strBeanName, String strConfigBeanName, boolean bConfigRequired,
            boolean bFormTaskRequired, boolean bTaskForAutomaticAction )
    {
        TaskType taskType = new TaskType( );
        taskType.setKey( strKey );
        taskType.setTitleI18nKey( strTitleI18nKey );
        taskType.setBeanName( strBeanName );
        taskType.setConfigBeanName( strConfigBeanName );
        taskType.setConfigRequired( bConfigRequired );
        taskType.setFormTaskRequired( bFormTaskRequired );
        taskType.setTaskForAutomaticAction( bTaskForAutomaticAction );

        return taskType;
    }

    /**
     * Builds a configuration service on a DAO.
     *
     * @param taskConfigDAO
     *            the configuration DAO
     * @return the configuration service
     */
    @SuppressWarnings( { "rawtypes", "unchecked" } )
    private static ITaskConfigService buildConfigService( ITaskConfigDAO taskConfigDAO )
    {
        TaskConfigService taskConfigService = new TaskConfigService( );
        taskConfigService.setTaskConfigDAO( taskConfigDAO );

        return taskConfigService;
    }
}
