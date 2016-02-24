/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sillygames.eventhandler;

import Game.Extensions.TreasureHuntZoneExtension;
import com.smartfoxserver.v2.core.ISFSEvent;
import com.smartfoxserver.v2.core.SFSEventParam;
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
public class LoginHandler extends BaseServerEventHandler {
   
   
  
    @Override
    public void handleServerEvent(ISFSEvent event) throws SFSException 
    {
        TreasureHuntZoneExtension.getInstance().addToTrace("LoginHandler user User Request For Login !!!!!! "  ); 
        String userName = (String) event.getParameter(SFSEventParam.LOGIN_NAME);
        String password = (String) event.getParameter(SFSEventParam.LOGIN_PASSWORD);
        ISFSObject l_sfsLoginData = (SFSObject)event.getParameter(SFSEventParam.LOGIN_IN_DATA);
         String deviceid = l_sfsLoginData.getUtfString("deviceid");
        TreasureHuntZoneExtension.getInstance().addToTrace("LoginHandler user User Request For Login !!!!!! Device ID  " +l_sfsLoginData.getUtfString("deviceid")); 
        TreasureHuntZoneExtension.getInstance().addToTrace("LoginHandler user User Request For Login !!!!!! User Name  " +userName ); 
        TreasureHuntZoneExtension.getInstance().addToTrace("LoginHandler user User Request For Login !!!!!! Password " +deviceid ); 
        //ISession session = (ISession)event.getParameter(SFSEventParam.SESSION);
         Connection conn = null;
        try {
            //get a connection to the database
            conn = getParentExtension().getParentZone().getDBManager().getConnection();

            //This will strip potential SQL injections SELECT username FROM treasurehunt.userlogin where userid ='1001';
            PreparedStatement sql = conn.prepareStatement("SELECT username FROM treasurehunt.userlogin WHERE userid = ?");
            sql.setString(1, deviceid);
            ResultSet result = sql.executeQuery();
            SFSArray row = SFSArray.newFromResultSet(result);
            if(row.size()<=0)
            {
                SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_PASSWORD);
                data.addParameter(userName);
                throw new SFSLoginException("User Does not Exits "  + userName, data);
            }
            if(!userName.equals(row.getSFSObject(0).getUtfString("username")))
            {
                TreasureHuntZoneExtension.getInstance().addToTrace("Login failed: "+row.getSFSObject(0).getUtfString("username"));
                

                //SFSErrorCodes.setErrorMessage(13, "Le Groupe demandé n'est pas disponible - Salle: {0}; Groupe: {1}");
                SFSErrorData errData  = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
                errData.addParameter(userName+ " "+ SFSErrorCode.LOGIN_BAD_USERNAME.getId());
                throw new SFSLoginException("Login failed for user name does not match: "  + userName, errData);
            }
            
            conn.close();     
            TreasureHuntZoneExtension.getInstance().addToTrace("Login successful, joining room!" + row.getDump());
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
    
    
}
