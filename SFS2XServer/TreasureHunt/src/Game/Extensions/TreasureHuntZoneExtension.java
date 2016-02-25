/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game.Extensions;

import Game.Keys;
import Game.THEntities.THClue;
import Game.THEntities.THElement;
import Game.THEntities.THTeam;
import Game.THEntities.TreasureHunt;
import Game.THServerEventHandler;
import Game.requestHandlers.GameRequestsHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.smartfoxserver.v2.annotations.Instantiation;
import static com.smartfoxserver.v2.annotations.Instantiation.InstantiationMode.SINGLE_INSTANCE;
import com.smartfoxserver.v2.api.CreateRoomSettings;
import com.smartfoxserver.v2.core.SFSEventType;
import com.smartfoxserver.v2.entities.Room;
import com.smartfoxserver.v2.exceptions.SFSCreateRoomException;
import com.smartfoxserver.v2.extensions.SFSExtension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//import Game.Keys;
//import Game.RequestHandlers.GameRequestsHandler;

/**
 *
 * @author Janhavi
 */
@Instantiation(SINGLE_INSTANCE)
public class TreasureHuntZoneExtension extends  SFSExtension
{
    //<editor-fold defaultstate="collapsed" desc="Static Members">
    private static TreasureHuntZoneExtension instance = null;
    
    public static TreasureHuntZoneExtension getInstance()
    {
        return instance;
    }
//</editor-fold>
    
    
    @Override
    public void init() 
    {
        instance = this;
        trace("----------------------- Initing Zone -----------------------");
        addEventHandler(SFSEventType.USER_JOIN_ZONE, THServerEventHandler.class); 
        addEventHandler(SFSEventType.USER_LOGIN, THServerEventHandler.class);
        addEventHandler(SFSEventType.SERVER_READY, THServerEventHandler.class);
        addEventHandler(SFSEventType.ROOM_ADDED, THServerEventHandler.class);
        
        //addRequestHandler(TreasureHuntEvent.REGISTER_USER, UserRegistrationHandler.class);
        addRequestHandler(Keys.GAME_REQUESTS, GameRequestsHandler.class);
        
        
    }

    //<editor-fold defaultstate="collapsed" desc="Property IsReady">
    private boolean m_bIsReady;
    public boolean getIsREady()
    {
        return m_bIsReady;
    }
    public void setIsReady(boolean a_bIsReady)
    {
        m_bIsReady = a_bIsReady;
    }
//</editor-fold>
    
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
