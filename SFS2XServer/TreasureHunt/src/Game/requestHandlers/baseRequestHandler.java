/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.requestHandlers;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.SFSExtension;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * @author Janhavi
 */
public class baseRequestHandler extends BaseClientRequestHandler
{
    @Override
    public void handleClientRequest(User user, ISFSObject params)
    {
        // Obtain the request custom name
        String command = params.getUtfString(SFSExtension.MULTIHANDLER_REQUEST_ID);
        trace("Game Request from client received: " + command);
        
        java.lang.reflect.Method method;
        try
        {
            method = this.getClass().getMethod(command, ISFSObject.class , User.class);
            method.invoke(this, params, user);
        }
        catch (SecurityException | NoSuchMethodException
                | IllegalArgumentException | IllegalAccessException
                | InvocationTargetException e)
        {
            trace("Exception in command method..: " + e.getMessage() + ", Exception type: " + e.getClass().getTypeName());
        }
    }
}
