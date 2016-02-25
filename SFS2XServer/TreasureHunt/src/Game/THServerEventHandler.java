/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Game.Extensions.TreasureHuntZoneExtension;
import Game.Keys;
import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
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

/**
 *
 * @author Ajay Singh
 */
public class THServerEventHandler extends BaseServerEventHandler 
{
    //private final boolean UseQuickHackLogin = true;
    
    //private static Map<String, String> m_loginData = null;
    
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
        case ROOM_ADDED:
            handleRoomAddedEvent(event);
        default:
            throw new AssertionError();
        }
    }
    
    void handleUserLoginEvent(ISFSEvent event) throws SFSException
    {
        trace("LoginHandler user User Request For Login !!!!!! "  ); 
        String userDeviceID = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
        //String password = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
        //ISFSObject l_sfsLoginData = (SFSObject)event.getParameter(SFSEventParam.LOGIN_IN_DATA);
        //ISFSObject l_sfsLoginOutData = (SFSObject)event.getParameter(SFSEventParam.LOGIN_OUT_DATA);
        Session session = (Session)event.getParameter(SFSEventParam.SESSION);
        
        Connection conn;
        try {
            //get a connection to the database
            conn = getParentExtension().getParentZone().getDBManager().getConnection();

            //This will strip potential SQL injections SELECT username FROM treasurehunt.userlogin where userid ='1001';
            PreparedStatement sql = conn.prepareStatement(DBQueries.SELECT_USER_BY_USER_ID);
            sql.setString(1, userDeviceID);
            ResultSet result = sql.executeQuery();
            ISFSArray  array = SFSArray.newFromResultSet(result);
            String userName = ((ISFSObject)array.get(0).getObject()).getUtfString("displayname");
            if(!result.first())
            {
                SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                data.addParameter(userDeviceID);
                throw new SFSLoginException("User Does not Exits "  + userDeviceID, data);
            }
            
            conn.close();     
            trace("___________________Login sucess with device id: " + userDeviceID + ", and user name: " + userName);
            session.setProperty(Keys.USER_NAME, userName);
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
        String deviceID = user.getName();
        String name = (String) user.getSession().getProperty(Keys.USER_NAME);
        user.setName(name);
        trace("________||||_____________User joined the jone, Name: " + user.getName() + ", device ID: " + deviceID );
        //print and check if you getting the device id correctly
    }
    
    void handleServerReadyEvent(ISFSEvent isfse) throws SFSException
    {
        trace("^^^^^^^^^^^^^^^^^^^^server Ready^^^^^^^^^^^^^^^^^^^^^^^^^^");
        //trace("TH evt class ID: " + TreasureHuntZoneExtension.class.hashCode() + ", class: " + TreasureHuntZoneExtension.class);
        TreasureHuntZoneExtension.getInstance().setIsReady(true);
        
    }

    private void handleRoomAddedEvent(ISFSEvent event)
    {
        
    }
}
