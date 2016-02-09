using UnityEngine;
using System.Collections;

public class DeviceInfo {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    public static string getDiviceID()
    {
        AndroidJavaClass up = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        AndroidJavaObject currentActivity = up.GetStatic<AndroidJavaObject>("currentActivity");
        AndroidJavaObject contentResolver = currentActivity.Call<AndroidJavaObject>("getContentResolver");
        AndroidJavaClass secure = new AndroidJavaClass("android.provider.Settings$Secure");
        string android_id = secure.CallStatic<string>("getString", contentResolver, "android_id");
        Debug.Log("sdfjsk" + android_id);
        return android_id;
    }
}
