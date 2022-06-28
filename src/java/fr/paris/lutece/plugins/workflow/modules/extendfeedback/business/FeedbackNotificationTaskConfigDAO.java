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
package fr.paris.lutece.plugins.workflow.modules.extendfeedback.business;

import fr.paris.lutece.plugins.workflowcore.business.config.ITaskConfigDAO;
import fr.paris.lutece.util.sql.DAOUtil;

/**
 * 
 * FeedbackNotificationTaskConfigDAO
 *
 */
public class FeedbackNotificationTaskConfigDAO implements ITaskConfigDAO<FeedbackNotificationTaskConfig>
{
    // Constants
    private static final String SQL_QUERY_SELECT = "SELECT id_task, message, subject, sender_name, sender_email FROM task_forms_extend_feedback_notification_cf WHERE id_task = ?";
    private static final String SQL_QUERY_INSERT = "INSERT INTO task_forms_extend_feedback_notification_cf ( id_task, message, subject, sender_name, sender_email ) VALUES ( ?, ?, ?, ?, ? ) ";
    private static final String SQL_QUERY_DELETE = "DELETE FROM task_forms_extend_feedback_notification_cf WHERE id_task = ? ";
    private static final String SQL_QUERY_UPDATE = "UPDATE task_forms_extend_feedback_notification_cf SET id_task = ?, message = ?, subject = ?, sender_name = ?, sender_email = ? WHERE id_task = ?";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( FeedbackNotificationTaskConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );

            daoUtil.setString( ++nIndex, config.getMessage( ) );
            daoUtil.setString( ++nIndex, config.getSubject( ) );
            daoUtil.setString( ++nIndex, config.getSenderName( ) );
            daoUtil.setString( ++nIndex, config.getSenderEmail( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void store( FeedbackNotificationTaskConfig config )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE ) )
        {
            int nIndex = 0;
            daoUtil.setInt( ++nIndex, config.getIdTask( ) );
            daoUtil.setString( ++nIndex, config.getMessage( ) );
            daoUtil.setString( ++nIndex, config.getSubject( ) );
            daoUtil.setString( ++nIndex, config.getSenderName( ) );
            daoUtil.setString( ++nIndex, config.getSenderEmail( ) );

            daoUtil.setInt( ++nIndex, config.getIdTask( ) );

            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FeedbackNotificationTaskConfig load( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeQuery( );

            FeedbackNotificationTaskConfig feedbackNotificationTaskConfig = null;

            if ( daoUtil.next( ) )
            {
                feedbackNotificationTaskConfig = new FeedbackNotificationTaskConfig( );
                int nIndex = 0;

                feedbackNotificationTaskConfig.setIdTask( daoUtil.getInt( ++nIndex ) );
                feedbackNotificationTaskConfig.setMessage( daoUtil.getString( ++nIndex ) );
                feedbackNotificationTaskConfig.setSubject( daoUtil.getString( ++nIndex ) );
                feedbackNotificationTaskConfig.setSenderName( daoUtil.getString( ++nIndex ) );
                feedbackNotificationTaskConfig.setSenderEmail( daoUtil.getString( ++nIndex ) );
            }

            return feedbackNotificationTaskConfig;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdTask )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE ) )
        {
            daoUtil.setInt( 1, nIdTask );
            daoUtil.executeUpdate( );
        }
    }
}
