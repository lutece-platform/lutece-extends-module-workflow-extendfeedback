/*
 * Copyright (c) 2002-2022, City of Paris
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.extend.modules.feedback.business.ExtendFeedback;
import fr.paris.lutece.plugins.extend.modules.feedback.service.ExtendFeedbackService;
import fr.paris.lutece.plugins.extend.modules.feedback.service.IExtendFeedbackService;
import fr.paris.lutece.plugins.workflow.modules.extendfeedback.business.FeedbackNotificationTaskConfig;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.mail.MailService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

/**
 * 
 * FeedbackNotificationService
 *
 */
public class FeedbackNotificationService implements IFeedbackNotificationService
{
    // MARKS
    private static final String MARK_DATE = "date_update";
    private static final String MARK_COMMENT = "comment";
    private static final String MARK_RESOURCE_ID = "resourceId";
    private static final String MARK_FEEDBACK_TYPE = "feedbackType";

    // PROPERTIES
    private static final String PROPERTY_DATE = "module.workflow.extendfeedback.task_feedback_notification_config.markers.date";
    private static final String PROPERTY_COMMENT = "module.workflow.extendfeedback.task_feedback_notification_config.markers.feedback_content";
    private static final String PROPERTY_RESOURCE_ID = "module.workflow.extendfeedback.task_feedback_notification_config.markers.resource_id";
    private static final String PROPERTY_FEEDBACK_TYPE = "module.workflow.extendfeedback.task_feedback_notification_config.markers.feedback_type";
    
    @Inject
    @Named( ExtendFeedbackService.BEAN_SERVICE )
    IExtendFeedbackService _extendFeedbackService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public ReferenceList getAvailableMarkers( )
    {
        Locale locale = I18nService.getDefaultLocale( );

        ReferenceList refList = new ReferenceList( );
        
        refList.addItem( MARK_DATE, I18nService.getLocalizedString( PROPERTY_DATE, locale ) );
        refList.addItem( MARK_COMMENT, I18nService.getLocalizedString( PROPERTY_COMMENT, locale ) );
        refList.addItem( MARK_RESOURCE_ID, I18nService.getLocalizedString( PROPERTY_RESOURCE_ID, locale ) );
        refList.addItem( MARK_FEEDBACK_TYPE, I18nService.getLocalizedString( PROPERTY_FEEDBACK_TYPE, locale ) );
        
        return refList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> getAvailableMarkersValues( ExtendFeedback extendFeedback )
    {
        Map<String, Object> markers = new HashMap<>( );
        
        markers.put( MARK_DATE, extendFeedback.getUpdateStatusDate( ) );
        markers.put( MARK_COMMENT, extendFeedback.getComment( ) );
        markers.put( MARK_RESOURCE_ID, extendFeedback.getIdResource( ) );
        markers.put( MARK_FEEDBACK_TYPE, extendFeedback.getFeedbackType( ) );
        
        return markers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyFeedbackAuthor( ResourceHistory resourceHistory, FeedbackNotificationTaskConfig config )
    {
        Optional<ExtendFeedback> extendFeedback = _extendFeedbackService.findById( resourceHistory.getIdResource( ) );

        if ( extendFeedback.isPresent( ) )
        {
            Map<String, Object> model = getAvailableMarkersValues( extendFeedback.get( ) );
            HtmlTemplate html = AppTemplateService.getTemplateFromStringFtl( config.getMessage( ), I18nService.getDefaultLocale( ) , model );
            String strSubject = AppTemplateService.getTemplateFromStringFtl( config.getSubject( ), I18nService.getDefaultLocale( ) , model ).getHtml( );
            
            MailService.sendMailHtml( StringUtils.EMPTY, StringUtils.EMPTY, extendFeedback.get( ).getEmail( ), config.getSenderName( ), config.getSenderEmail( ),
                    strSubject, html.getHtml( ) );
        }
    }
}
