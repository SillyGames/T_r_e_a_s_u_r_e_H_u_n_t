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
import Game.THGame;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sillygames.eventhandler.LoginHandler;
import com.sillygames.eventhandler.ServerReadyHandler;
import com.sillygames.eventhandler.UserRegistrationHandler;
import com.sillygames.eventhandler.ZoneJoinHandler;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Janhavi
 */

public class TreasureHuntZoneExtension extends  SFSExtension
{
    private static TreasureHuntZoneExtension instance = null;
    
    public static TreasureHuntZoneExtension getInstance() 
    {
        return instance;
    }
    
    @Override
    public void init() 
    {
        instance = this;
        addToTrace("wallah... in tza login...!!!!----------------------------------------------");
        instance.addEventHandler(SFSEventType.USER_JOIN_ZONE, ZoneJoinHandler.class); 
        instance.addEventHandler(SFSEventType.USER_LOGIN, LoginHandler.class);
        instance.addEventHandler(SFSEventType.SERVER_READY, ServerReadyHandler.class);
        instance.addRequestHandler(TreasureHuntEvent.REGISTER_USER, UserRegistrationHandler.class);
        THGame.g_extensionInstance = this;
        THGame.CreateInstance();
        TestJSON();
        trace("Creating a room Extension......");
        //CreateTreasureHuntRoom();
    }
    
    public void addToTrace(String a_traceStr) 
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
            String strData = mapper.writeValueAsString(elem);
            trace("This is the serialized data: \n" + strData);
            THElement rebornElem =  mapper.readValue(strData, THElement.class);
            trace("Type of reborn element: " + rebornElem.getClass());
        }
        catch (IOException ex)
        {
            Logger.getLogger(TreasureHuntZoneExtension.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void CreateTreasureHuntRoom()
    {
        CreateRoomSettings.RoomExtensionSettings roomExtSettings = 
                new CreateRoomSettings.RoomExtensionSettings("TreasureHunt","Game.Extensions.TreasureHuntRoomExtension");
        
        CreateRoomSettings settings = new CreateRoomSettings();
        settings.setExtension(roomExtSettings);
        settings.setName("THRoom");
        settings.setMaxUsers(100);
        settings.setDynamic(true);
        settings.setMaxVariablesAllowed(100);
        
        try
        {
            if(sfsApi != null)
            {
                trace("trying to create room in zone...: " + getParentZone());
                Room createRoom = sfsApi.createRoom(getParentZone(), settings, null);
                trace("room created is: " + createRoom);
            }
            else
            {
                trace(":(:(:(:(:(:(:(:(:(:(:(");
            }
        }
        catch (SFSCreateRoomException ex)
        {
            Logger.getLogger(TreasureHuntZoneExtension.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
