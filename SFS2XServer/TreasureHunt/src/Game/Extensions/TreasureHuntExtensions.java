/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.Extensions;

import Game.THEntities.THClue;
import Game.THEntities.THElement;
import Game.THEntities.THTeam;
import Game.THEntities.TreasureHunt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sillygames.eventhandler.LoginHandler;
import com.sillygames.eventhandler.UserRegistrationHandler;
import com.sillygames.eventhandler.ZoneJoinHandler;
import com.sillygames.utils.TreasureHuntEvent;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.extensions.SFSExtension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Janhavi
 */

public class TreasureHuntExtensions extends  SFSExtension
{
    private static TreasureHuntExtensions instance = null;
    
    public static TreasureHuntExtensions getInstance() {
        return instance;
    }
    @Override
    public void init() 
    {
        instance = this;
        addToTrance("wallah... in tza login...!!!!----------------------------------------------");
        instance.addEventHandler(SFSEventType.USER_JOIN_ZONE, ZoneJoinHandler.class); 
        instance.addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
        instance.addRequestHandler(TreasureHuntEvent.REGISTER_USER, UserRegistrationHandler.class);
        
        TestJSON();
  
    }
    public void addToTrance(String a_traceStr)
    {
        trace(a_traceStr);
    }

    void TestJSON()
    {
        ObjectMapper  mapper =  new ObjectMapper();
        TreasureHunt th = new TreasureHunt();
        THElement elem = th;
        
        //add test data and see if it gets serialized correctly
        THTeam team =  th.AddTeam();
        THClue clue = th.AddClue();
        
        try
        {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            String strData = mapper.writeValueAsString(th);
            trace("This is the serialized data: \n" + strData);
            THElement rebornElem =  mapper.readValue(strData, THElement.class);
            trace("Type of reborn element: " + rebornElem.getClass());
        }
        catch (IOException ex)
        {
            Logger.getLogger(TreasureHuntExtensions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
