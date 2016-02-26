package Game.requestHandlers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Game.DBQueries;
import Game.Keys;
import com.smartfoxserver.v2.annotations.MultiHandler;
import com.smartfoxserver.v2.api.SFSApi;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSLoginException;
import com.smartfoxserver.v2.extensions.ExtensionLogLevel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Janhavi
 */
@MultiHandler
public class GameRequestsHandler extends baseRequestHandler
{   
    public void createHunt(ISFSObject a_data, User a_user)
    {
        String strDeviceID = a_user.getVariable(Keys.USERVAR_DEVICEID).getStringValue();
        String strHuntName = a_data.getUtfString(Keys.HUNT_NAME);
        
        //INSERT INTO `treasurehunt`.`Hunts` (`name`, `owner`, `data`) VALUES ('firstHunr', '7d3124f7377c3079c6c13c15b2d2dfc154867ad2', 'nothing');
        ISFSObject data = new SFSObject();
        data.putUtfString(Keys.HUNT_NAME, strHuntName);
        Connection conn;
        try {
            //get a connection to the database
            conn = getParentExtension().getParentZone().getDBManager().getConnection();

            //This will strip potential SQL injections SELECT username FROM treasurehunt.userlogin where userid ='1001';
            
            PreparedStatement sql = conn.prepareStatement(DBQueries.CREATE_HUNT);
            sql.setString(1, strHuntName);
            sql.setString(2, strDeviceID);
            trace("here is a sql stirng: " + sql.toString());
            sql.executeUpdate();
            data.putBool(Keys.SUCCESS, true);
            send(Keys.CMD_HUNTCREATED, data, a_user);
            
            conn.close();     
            trace("___________________ Hunt Created with name: " + strHuntName + ", and owner name: " + a_user.getName());
        } 
        catch (SQLException e)
        {
            trace(ExtensionLogLevel.WARN, " SQL Hunt Creation failed: " + e.toString());
            
            
            data.putBool(Keys.SUCCESS, false);
            data.putUtfString(Keys.ERROR, e.toString());
            send(Keys.CMD_HUNTCREATED, data, a_user);
        }
        
    }
    
    public void deleteHunt(ISFSObject a_data, User a_user)
    {
        
    }
    
    public void openHunt(ISFSObject a_data, User a_user)
    {
        
    }
    
}
