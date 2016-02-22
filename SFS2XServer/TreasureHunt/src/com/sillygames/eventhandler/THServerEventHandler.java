/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sillygames.eventhandler;

import Game.Extensions.TreasureHunZonetExtension;
import Game.Keys;
import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Ajay Singh
 */
public class THServerEventHandler extends BaseServerEventHandler 
{
    private final boolean UseQuickHackLogin = true;
    
    private static Map<String, String> m_loginData = null;
    
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException 
    {
        SFSEventType eventType = event.getType();
        switch (eventType)
        {
        case USER_LOGIN:
            handleUserLoginEvent(event);
            break;
        case USER_JOIN_ZONE:
            handleUserJoinZoneEvent(event);
            break;
        case SERVER_READY:
            handleServerReadyEvent(event);
            break;
        default:
            throw new AssertionError();
        }
    }
    
    void handleUserLoginEvent(ISFSEvent event) throws SFSException
    {
        if(m_loginData == null)
        {
            m_loginData = new ConcurrentHashMap<String,String>();
            m_loginData.put("7d3124f7377c3079c6c13c15b2d2dfc154867ad2", "Prasad");
        }
        trace("``````````||````````````LoginHandler user User Request For Login !!!!!! "  ); 
        String userDeviceID = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
        //String password = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
        //ISFSObject l_sfsLoginData = (SFSObject)event.getParameter(SFSEventParam.LOGIN_IN_DATA);
        ISFSObject l_sfsLoginOutData = (SFSObject)event.getParameter(SFSEventParam.LOGIN_OUT_DATA);
        Session session = (Session)event.getParameter(SFSEventParam.SESSION);
        
        //String deviceid = l_sfsLoginData.getUtfString(Keys.DEVICE_ID);
                
        //<editor-fold defaultstate="collapsed" desc="Quick Hack Login, remove later">
        if(UseQuickHackLogin)
        {
           String userName;
            if(m_loginData.containsKey(userDeviceID))
            {
                userName = m_loginData.get(userDeviceID );
            }
            else
            {
                throw new SFSException("EEEE: QuickHack Login Failed!!!");
            }
            session.setProperty(Keys.DEVICE_ID, userDeviceID);
            session.setProperty(Keys.USER_NAME, userName);
            l_sfsLoginOutData.putUtfString(Keys.USER_NAME, userName);
            trace("Login Success!!!!!! User Name  " +userName);
            
            return;
        }
//</editor-fold>
        
        //ISession session = (ISession)event.getParameter(SFSEventParam.SESSION);
         Connection conn = null;
        try {
            //get a connection to the database
            conn = getParentExtension().getParentZone().getDBManager().getConnection();

            //This will strip potential SQL injections SELECT username FROM treasurehunt.userlogin where userid ='1001';
            PreparedStatement sql = conn.prepareStatement("SELECT username FROM treasurehunt.userlogin WHERE userid = ?");
            sql.setString(1, userDeviceID);
            ResultSet result = sql.executeQuery();
            SFSArray row = SFSArray.newFromResultSet(result);
            if(row.size()<=0)
            {
                SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                data.addParameter(userDeviceID);
                throw new SFSLoginException("User Does not Exits "  + userDeviceID, data);
            }
            if(!userDeviceID.equals(row.getSFSObject(0).getUtfString("username")))
            {
                trace("Login failed: "+row.getSFSObject(0).getUtfString("username"));
                

                //SFSErrorCodes.setErrorMessage(13, "Le Groupe demandé n'est pas disponible - Salle: {0}; Groupe: {1}");
                SFSErrorData errData  = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
                errData.addParameter(userDeviceID+ " "+ SFSErrorCode.LOGIN_BAD_USERNAME.getId());
                throw new SFSLoginException("Login failed for user name does not match: "  + userDeviceID, errData);
            }
            
            conn.close();     
            trace("___________________Login sucess with device id: " + userDeviceID);
            session.setProperty(Keys.DEVICE_ID, userDeviceID);
            trace("Login successful, joining room!" + row.getDump());
        } 
        catch (SQLException e)
        {
            trace(ExtensionLogLevel.WARN, " SQL Failed: " + e.toString());
            SFSErrorData errData = new SFSErrorData(SFSErrorCode.GENERIC_ERROR);
            errData.addParameter("SQL Error: " + e.getMessage());
            // Sends response about mysql errors
            throw new SFSLoginException("A SQL Error occurred: " + e.getMessage(), errData);
        }
    }
    
    void handleUserJoinZoneEvent(ISFSEvent isfse) throws SFSException 
    {
        User user = (User) isfse.getParameter(SFSEventParam.USER);
        String deviceID = (String) user.getSession().getProperty(Keys.DEVICE_ID);
        String name = (String) user.getSession().getProperty(Keys.USER_NAME);
        user.setName(name);
        trace("________||||_____________User joined the jone, Name: " + user.getName() + ", device ID: " + deviceID );
        //print and check if you getting the device id correctly
    }
    
    void handleServerReadyEvent(ISFSEvent isfse) throws SFSException
    {
        trace("^^^^^^^^^^^^^^^^^^^^server Ready^^^^^^^^^^^^^^^^^^^^^^^^^^");
        TreasureHunZonetExtension.getInstance().setIsReady(true);
    }
}
