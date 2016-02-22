/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sillygames.eventhandler;

import Game.TreasureHuntEvent;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Ajay Singh
 */
public class UserRegistrationHandler extends BaseClientRequestHandler
{

    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        String name = params.getUtfString("name");
        String deviceID = params.getUtfString("deviceid");
        String Password = "password";
        Connection conn = null;
       try
        {
            conn  = getParentExtension().getParentZone().getDBManager().getConnection();
            String sql = "INSERT into login(userid, password, username) values ('" + deviceID + "','" + Password + "','" + name + "')";
            PreparedStatement sqlStatement = conn.prepareStatement(sql);
            ResultSet result= sqlStatement.executeQuery();
            //dbManager.(sql);
        }
        catch (SQLException e)
        {
            // Sends MySQL exception details to the client
            ISFSObject error = new SFSObject();
            error.putUtfString("error", "MySQL updation failed");
            send("register", error, user);
        }

        ISFSObject success = new SFSObject();
        success.putUtfString("success", "User successfully registered");
        send(TreasureHuntEvent.REGISTER_USER, success, user); //To change body of generated methods, choose Tools | Templates.
    }

}
