using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Sfs2X;
using Sfs2X.Logging;
using Sfs2X.Util;
using Sfs2X.Core;
using Sfs2X.Entities;
using System;
using Sfs2X.Entities.Data;
using Sfs2X.Requests;

public class NetworkManager : MonoBehaviour,IEventDispatcher
{
    private const string INVALID_USER_NAME = "InValid User Name";
    private const string USER_NOT_EXITS = "user not exits";
    public enum eGameEvents
    {
        ConnectionSuccess,
        UserLogin,   
        UserLogout,
        UserLoginError,
        getAssetsInfo    
    }

    private static NetworkManager m_instance;
    public static NetworkManager Instance
    {
        get
        {

            return m_instance;
        }
    }

    private SmartFox sfs = null;
    //----------------------------------------------------------
    // Private properties
    //----------------------------------------------------------
    [SerializeField]
    private string defaultHost = "127.0.0.1";   // Default host
    [SerializeField]
    private int defaultTcpPort = 9933;          // Default TCP port
    [SerializeField]
    private int defaultWsPort = 8888;           // Default WebSocket port
    private EventManager m_eventManager;
    private string port;
    [SerializeField]
    private string m_zoneName;
    public string ZoneName
    {
        get
        {
            return m_zoneName;
        }

        set
        {
            m_zoneName = value;
        }
    }
    [SerializeField]
    private bool m_isDebugMode = false;
    void Awake()
    {
        m_instance = this;
        m_eventManager = new EventManager();
    }

    void Start()
    {
                #if !UNITY_WEBGL
                        port = defaultTcpPort.ToString();
                #else
		                        port = defaultWsPort.ToString();
                #endif

                        if (sfs == null || !sfs.IsConnected)
                        {

                            // CONNECT

                #if UNITY_WEBPLAYER
			                // Socket policy prefetch can be done if the client-server communication is not encrypted only (read link provided in the note above)
			                if (!Security.PrefetchSocketPolicy(defaultHost, Convert.ToInt32(port), 500)) {
				                Debug.LogError("Security Exception. Policy file loading failed!");
			                }
                #endif

                            // Enable interface


                            // Initialize SFS2X client and add listeners
                            // WebGL build uses a different constructor
                #if !UNITY_WEBGL
                            sfs = new SmartFox();
                #else
			                sfs = new SmartFox(UseWebSocket.WS);
                #endif

            // Set ThreadSafeMode explicitly, or Windows Store builds will get a wrong default value (false)
            sfs.ThreadSafeMode = true;

            sfs.AddEventListener(SFSEvent.CONNECTION, OnConnection);
            sfs.AddEventListener(SFSEvent.CONNECTION_LOST, OnConnectionLost);

            sfs.AddLogListener(LogLevel.INFO, OnInfoMessage);
            sfs.AddLogListener(LogLevel.WARN, OnWarnMessage);
            sfs.AddLogListener(LogLevel.ERROR, OnErrorMessage);

        }
        else
        {

            sfs.Disconnect();
        }
    }
    public void Connect()
    {
        if(m_zoneName != string.Empty)
        {
            ConfigData cfg = new ConfigData();
            cfg.Host = defaultHost;
            cfg.Port = Convert.ToInt32(port);
            cfg.Zone = m_zoneName;
            cfg.Debug = m_isDebugMode;
            sfs.Connect(cfg);
        }
       
    }

    public void DisConnect()
    {
        sfs.Disconnect();
    }


    private void onExtensionResponse(BaseEvent evt)
    {
        try
        {
            string cmd = (string)evt.Params["cmd"];
            ISFSObject dt = (SFSObject)evt.Params["params"];
            if (cmd ==Keys.CMD_ASSETS_INFO)
            {
                Debug.Log("onExtensionResponse :: game.getAssetsInfo :: "+ dt.GetDump());
                OnResponseGetAssetsInfo(dt);
            }
            else if (cmd == "huntcreated")
            {
                Debug.Log("onExtensionResponse :: game.createHunt");
            }
        }
        catch (Exception e)
        {
            Debug.Log("Exception handling response: " + e.Message + " >>> " + e.StackTrace);
        }
    }
    private  void RegisterLoginEvent()
    {
        sfs.AddEventListener(SFSEvent.LOGIN, OnLogin);
        sfs.AddEventListener(SFSEvent.LOGIN_ERROR, OnLoginError);
    }
    private void UnRegisterLoginEvent()
    {
        sfs.RemoveEventListener(SFSEvent.LOGIN, OnLogin);
        sfs.RemoveEventListener(SFSEvent.LOGIN_ERROR, OnLoginError);
    }

    private void OnLogin(BaseEvent evt)
    {
        Debug.Log("OnLoginOnLoginOnLoginOnLogin ::: ");
        UnRegisterLoginEvent(); 
        GameBaseEvent l_loginEvent = new GameBaseEvent(eGameEvents.UserLogin.ToString());
        GameEventArgs l_loginargs = new GameEventArgs();
        l_loginargs.EventData = evt;
        l_loginEvent.Args = l_loginargs;
        dispatchEvent(this, l_loginEvent);
       
       
    }

    private void OnLoginError(BaseEvent evt)
    {
        string error = (string)evt.Params["errorMessage"];
        if(error == INVALID_USER_NAME)
        {
            Debug.Log("Try Again ....");
        }
        else if(error == USER_NOT_EXITS)
        {
            Debug.Log("User Not Regi ");
        }
    }

    public void RequestToLogin(string a_userName, string a_deviceID)
    {
        SFSErrorCodes.SetErrorMessage(2, INVALID_USER_NAME);
        SFSErrorCodes.SetErrorMessage(3, USER_NOT_EXITS);
        SFSErrorCodes.SetErrorMessage(4, USER_NOT_EXITS);
        ISFSObject l_temp = new SFSObject();
        l_temp.PutUtfString("deviceid", a_deviceID);
        sfs.Send(new Sfs2X.Requests.LoginRequest(a_userName, "", m_zoneName, l_temp));
    }

    public void RequestToLogin()
    {
        RequestToLogin(SystemInfo.deviceUniqueIdentifier, string.Empty);
    }

    public void SendCustomRequestToGetAssetsInfo(string a_huntID)
    {
        ISFSObject data = new SFSObject();
        data.PutUtfString("key_hunt_id", a_huntID);
        NetworkManager.Instance.sfs.Send(new ExtensionRequest("game.getAssetsInfo", data));
    }
    public void SendCustomRequestToCreateHunt()
    {
        ISFSObject data = new SFSObject();
        data.PutUtfString("huntname", "wallahHunt");
        NetworkManager.Instance.sfs.Send(new ExtensionRequest("game.createHunt", data));
    }
    public void RequestToJoinRoom(string a_roomName)
    {
      
        
    }

    void FixedUpdate()
    {
        if (sfs != null)
            sfs.ProcessEvents();
    }
    
    public void addEventListener(string a_eventName, DelegateWarapper.GameEventHandler a_eventHandler)
    {
        m_eventManager.addEvent(a_eventName, a_eventHandler);
    }

    public void removeEventListener(string a_eventName)
    {
        m_eventManager.RemoveEvent(a_eventName);
    }

    public void dispatchEvent(object sender, GameBaseEvent a_baseEvent)
    {
        m_eventManager.DispatchEvent(sender, a_baseEvent);
    }

    public void clearAll()
    {
        m_eventManager.ClearAll();
    }

    public void OnInfoMessage(BaseEvent evt)
    {
        string message = (string)evt.Params["message"];
        ShowLogMessage("INFO", message);
    }

    public void OnWarnMessage(BaseEvent evt)
    {
        string message = (string)evt.Params["message"];
        ShowLogMessage("WARN", message);
    }

    public void OnErrorMessage(BaseEvent evt)
    {
        string message = (string)evt.Params["message"];
        ShowLogMessage("ERROR", message);
    }

    private void ShowLogMessage(string level, string message)
    {
        message = "[SFS > " + level + "] " + message;

        Debug.Log(message);
    }
    private void reset()
    {
        sfs.RemoveEventListener(SFSEvent.CONNECTION, OnConnection);
        sfs.RemoveEventListener(SFSEvent.CONNECTION_LOST, OnConnectionLost);
        sfs.RemoveLogListener(LogLevel.INFO, OnInfoMessage);
        sfs.RemoveLogListener(LogLevel.WARN, OnWarnMessage);
        sfs.RemoveLogListener(LogLevel.ERROR, OnErrorMessage);

    }

    private void OnConnection(BaseEvent evt)
    {
        if ((bool)evt.Params["success"])
        {
            RegisterLoginEvent();
            sfs.AddEventListener(SFSEvent.EXTENSION_RESPONSE, onExtensionResponse);
            GameBaseEvent l_gameEvent = new GameBaseEvent(eGameEvents.ConnectionSuccess.ToString());
            GameEventArgs args = new GameEventArgs();
            this.dispatchEvent(this, l_gameEvent);
        }
        else
        {
            reset();
        }
    }

    private void OnResponseGetAssetsInfo(ISFSObject a_infoData)
    {
        //assetsinfo
        if(a_infoData.GetBool(Keys.SUCCESS))
        {
            ISFSArray l_tempSfsArray = a_infoData.GetSFSArray(Keys.ASSETS_INFO);
            List<AssetsInfo> l_allCurrentInfoList = new List<AssetsInfo>();
            for (int indexAssetInfo = 0; indexAssetInfo < l_tempSfsArray.Count; indexAssetInfo++)
            {
                AssetsInfo l_temp = new AssetsInfo();
                l_temp.AssetID = (int)(l_tempSfsArray.GetSFSObject(indexAssetInfo)).GetLong("assetsid");
                l_temp.ImageTargetID = (l_tempSfsArray.GetSFSObject(indexAssetInfo)).GetUtfString("imagetargetid");
                l_temp.URL = (l_tempSfsArray.GetSFSObject(indexAssetInfo)).GetUtfString("assetsurl");
                l_allCurrentInfoList.Add(l_temp);

            }
            GameBaseEvent l_assetsInfoEvent = new GameBaseEvent(eGameEvents.getAssetsInfo.ToString());
            GameEventArgs l_eventArg = new GameEventArgs();
            l_eventArg.EventData  = (object)l_allCurrentInfoList;
            l_assetsInfoEvent.Args = l_eventArg;
            dispatchEvent(this, l_assetsInfoEvent);
        }
        else
        {
            Debug.Log(a_infoData.GetUtfString(Keys.ERROR));
        }
    }

    private void OnConnectionLost(BaseEvent evt)
    {
        Debug.Log("NetWork Manger It's call when cannection is lost");
        reset();
    }

    void OnApplicationQuit()
    {
        Debug.Log("Application Is Disconnected ");
        clearAll();
        reset();
        DisConnect();
       sfs = null;
    }
}
