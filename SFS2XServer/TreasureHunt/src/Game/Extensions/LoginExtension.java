/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.Extensions;

import Game.THEntities.THElement;
import Game.THEntities.TreasureHunt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartfoxserver.v2.extensions.SFSExtension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Janhavi
 */
public class LoginExtension extends  SFSExtension{

    @Override
    public void init() 
    {
        trace("wallah... in tza login...!!!!----------------------------------------------");
        ObjectMapper  mapper =  new ObjectMapper();
        THElement elem = new TreasureHunt();
        
        try
        {
            String strData = mapper.writeValueAsString(elem);
            trace("This is the serialized data: " + strData);
            THElement rebornElem =  mapper.readValue(strData, THElement.class);
            trace("Type of reborn element: " + rebornElem.getClass());
        }
        catch (JsonProcessingException ex)
        {
            Logger.getLogger(LoginExtension.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(LoginExtension.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
