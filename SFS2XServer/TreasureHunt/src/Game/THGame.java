/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Game.Extensions.TreasureHunZonetExtension;
import Game.THEntities.THPlayer;
import Game.THEntities.TreasureHunt;

/**
 *
 * @author Janhavi
 */
public class THGame
{
    private static THGame s_instance = null;
    public static THGame GetInstance()
    {
        return s_instance;
    }
    
    public static void CreateInstance()
    {
        if(s_instance == null)
        {
            Trace("---------------Creating a new instance of THGame---------------");
            s_instance = new THGame();
            s_instance.Init();
        }
        else
        {
            Trace("WWWWW: THGame:CreateInstance: instance is already created...");
        }
    }
    
    public static void Trace(Object a_object)        
    {
        g_extensionInstance.trace(a_object);
    }
    public static void TraceW(Object a_object)        
    {
        Trace("WWWWWWWW: " + a_object);
    }
    
    public static void TraceE(Object a_object)        
    {
        Trace("EEEEEEEE: " + a_object);
    }
    
    public static TreasureHunZonetExtension g_extensionInstance = null;
    
    //<editor-fold defaultstate="collapsed" desc="State Handling">
    private enum State
    {
        Preparation,
        Hunting,
        Result
    }
    private State m_state = State.Preparation;
    private State GetState()
    {
        return m_state;
    }
    private void SetState(State a_state)
    {
        m_state = a_state;
    }
    
//</editor-fold>
    
    TreasureHunt m_treasureHunt = new TreasureHunt();
    public void Init()
    {
        Trace("Initing THGame");
        //its the default state anyway, but setting it here just for the sake of logic
        SetState(State.Preparation);        
        runGameLoop();
        //load treasur hunt from json saved
        
        
    }
    
    void UpdateStatePreparation(double deltaTime)
    {
        
    }
    
    void UpdateStateHunting(double deltaTime)
    {
        
    }
    
    void UpdateStateResult(double deltaTime)
    {
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Game Events and callbacks">
    //TODO: Ajay, call these functions according to the commented instructions
    
    /**
     * call this function when player succeeds login, and get in to the treasure hunt
     * he may not be a part of the hunt yet, this will be handled as well
     * @param a_playerID : a player ID, must be valid, undefined behavior otherwise
     * @param a_playerName : a display name for player, must not be null, can be empty if existing player
     */
        
    public void OnPlayerConnected(String a_playerID, String a_playerName)
    {
        //if player is new, create player and add it to requesters (tell other people if you want)
        //else update his online status (tell other people if required)
        
        THPlayer player = m_treasureHunt.GetPlayerByID(a_playerID);
        if(player != null )
        {
            player.SetIsOnline(true);
        }
        else
        {
            player = m_treasureHunt.GetRequesterByID(a_playerID);
            if(player != null)
            {
                player.SetIsOnline(true);
            }
            else
            {
                player = m_treasureHunt.AddReQuester(a_playerID, a_playerName);
                player.SetIsOnline(true);
            }
        }
        //notify all about this player being online/added
    }
    
    /**
     * call this function when player gets disconnected (whatever may be the reason)
     * @param a_playerID : a player ID, must be valid
     */
    public void OnPlayerDisconnected(String a_playerID)
    {
        THPlayer player = m_treasureHunt.GetPlayerByID(a_playerID);
        if(player != null )
        {
            player.SetIsOnline(false);
        }
        else
        {
            player = m_treasureHunt.GetRequesterByID(a_playerID);
            if(player != null)
            {
                player.SetIsOnline(false);
            }
            else
            {
                TraceW(" THGame: player was not connected!! ID: " + a_playerID);
            }
        }
        //notify all about this player being offline
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handling Game Loop">
    private boolean m_bRunning = false;

    private void SetRunning(boolean a_bRunning)
    {
        m_bRunning = a_bRunning;
    }
    
    private int fps = 60;
    //private int frameCount = 0;
    
    ///this game loop code is taken from
    //http://www.java-gaming.org/index.php/topic,24220.0
    ///it is modified according to our needs
    public void runGameLoop()
    {
        Thread loop = new Thread()
        {
            
            @Override
            public void run()
            {
                gameLoop();
            }
        };
        SetRunning(true);
        Trace("about to start a thread@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        loop.start();
    }
    
    //Only run this in another Thread!
    private void gameLoop()
    {
        //This value would probably be stored elsewhere.
        final double GAME_HERTZ = 1.0;//30.0;
        //Calculate how many ns each frame should take for our target game hertz.
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        //At the very most we will update the game this many times before a new render.
        
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime = System.nanoTime();
        
      
        
        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        
        while (m_bRunning)
        {
            double now = System.nanoTime();
            
            double currentFrame = now - lastUpdateTime;
            updateGame(currentFrame);
            lastUpdateTime = now;
            
            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
            while ( (now - lastUpdateTime) < TIME_BETWEEN_UPDATES)
            {
                Thread.yield();
                
                //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                try {Thread.sleep(1);} catch(Exception e) {}
                
                now = System.nanoTime();
            }
        }
    }
    
    private void updateGame(double deltaTime)
    {
        State state = GetState();
        switch(state)
        {
        case Preparation:
        {
            UpdateStatePreparation(deltaTime);
            break;
        }
        case Hunting:
        {
            UpdateStateHunting(deltaTime);
            break;
        }
        case Result:
        {
            UpdateStateResult(deltaTime);
            break;
        }
        default:
            throw new AssertionError(state.name());        
        }
        //Trace("UPdating Game Loop: " + deltaTime);
    }
//</editor-fold>
}
