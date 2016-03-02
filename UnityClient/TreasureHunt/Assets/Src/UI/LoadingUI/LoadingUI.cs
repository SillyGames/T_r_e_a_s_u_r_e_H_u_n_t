using UnityEngine;
using System.Collections;

public class LoadingUI : MonoBehaviour {

	// Use this for initialization
	void Start ()
    {
        for (int i = 0; i < GameManager.Instance.AllAssetsInfoList.Count; i++)
        {
            AssetsLoader.Instance.LoadAssetsBundle(GameManager.Instance.AllAssetsInfoList[i].URL, OnAssetsLoadingComplete, OnLoadingError);
        }
	}
	
	// Update is called once per frame
	void Update () {
	
	}

    void OnAssetsLoadingComplete(AssetBundle a_assetsbundle)
    {

        GameObject loadedObject;
        loadedObject = Instantiate(a_assetsbundle.LoadAsset("Cube", typeof(GameObject))) as GameObject;
      //  loadedObject = Instantiate(a_assetsbundle.LoadAsset("Cube") as GameObject;

    }

    void OnLoadingError(string a_error)
    {
        Debug.Log(a_error);
    }
}
