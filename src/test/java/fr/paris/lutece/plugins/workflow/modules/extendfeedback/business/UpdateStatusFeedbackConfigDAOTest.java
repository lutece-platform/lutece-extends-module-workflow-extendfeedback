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
package fr.paris.lutece.plugins.workflow.modules.extendfeedback.business;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.workflow.modules.extendfeedback.util.WfExtendFeedbackConstants;
import fr.paris.lutece.test.LuteceTestCase;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * Tests the update status task configuration DAO.
 */
public class UpdateStatusFeedbackConfigDAOTest extends LuteceTestCase
{
    private static final int ID_TASK = 987655;

    @Inject
    @Named( WfExtendFeedbackConstants.BEAN_TASK_CONFIG_DAO )
    private UpdateStatusFeedbackConfigDAO _dao;

    /**
     * Inserts, loads, updates and deletes a configuration.
     */
    @Test
    public void testCRUD( )
    {
        UpdateStatusFeedbackConfig config = new UpdateStatusFeedbackConfig( );
        config.setIdTask( ID_TASK );
        config.setStatus( true );
        _dao.insert( config );

        assertTrue( _dao.load( ID_TASK ).isStatus( ) );

        config.setStatus( false );
        _dao.store( config );
        assertFalse( _dao.load( ID_TASK ).isStatus( ) );

        _dao.delete( ID_TASK );
        assertNull( _dao.load( ID_TASK ) );
    }
}
