using UnityEngine;
using System.Collections;
using System;
using System.Collections.Generic;

public class AssetsLoader : MonoBehaviour
{
    private static AssetsLoader m_intance;
    public static AssetsLoader Instance
    {
        get
        {
            return m_intance;
        }
    }

    

    void Awake()
    {
        m_intance = this;
    }
	
    public void LoadImage(string a_url, Action<Texture2D> OnImageLoadComplete, Action<string> OnImageLoadError)
    {
        Debug.Log("LoadImage :: ");
        LoadAsset<Texture2D> l_loadAsset = new LoadAsset<Texture2D>();
        l_loadAsset.RequestToLoadAsset(this, a_url, OnImageLoadComplete, OnImageLoadError);

    }

    internal void LoadAssetsBundle(AssetsInfo assetsInfo, Action<AssetBundle, AssetsInfo> onAssetsLoadingComplete, Action<string> onLoadingError)
    {
        LoadAsset<AssetBundle> l_loadAsset = new LoadAsset<AssetBundle>();
        l_loadAsset.RequestToLoadAsset(this, assetsInfo, onAssetsLoadingComplete, onLoadingError);
    }
    class LoadAsset<T>
    {

        private Action<T> m_completeCallback;
        private Action<string> m_completeErrorCallback;
        private T m_currentData = default(T);
        GameObject myPrefab;
        private AssetsInfo m_currentAssetsInfo;
        public void RequestToLoadAsset(AssetsLoader a_loader, string a_url, Action<T> OnImageLoadComplete, Action<string> OnImageLoadError)
        {
            a_loader.StartCoroutine(DownLoadAsset(a_url, OnImageLoadComplete, OnImageLoadError));
        }
        public void RequestToLoadAsset(AssetsLoader a_loader, AssetsInfo assetsInfo, Action<T, AssetsInfo> OnImageLoadComplete, Action<string> OnImageLoadError)
        {
            m_currentAssetsInfo = assetsInfo;
            a_loader.StartCoroutine(DownLoadAsset(assetsInfo.URL, OnImageLoadComplete, OnImageLoadError));
        }

        private IEnumerator DownLoadAsset(string uRL, Action<T, AssetsInfo> onImageLoadComplete, Action<string> onImageLoadError)
        {
               WWW l_wwwRequest = new WWW(uRL);

                while (!l_wwwRequest.isDone)
                {
                    yield return new WaitForEndOfFrame();
                }
                Type currentLoadType = typeof(T);
                object l_value = null;
                if (currentLoadType == typeof(Texture2D))
                {
                    l_value = l_wwwRequest.texture;
                }
                if (currentLoadType == typeof(AssetBundle))
                {
                    l_value = l_wwwRequest.assetBundle;

                }
                m_currentData = (T)l_value;

                if (string.IsNullOrEmpty(l_wwwRequest.error))
                {

                    onImageLoadComplete.Invoke(m_currentData,m_currentAssetsInfo);

                    AssetBundle temp = m_currentData as AssetBundle;

                    temp.Unload(false);
                    l_wwwRequest.Dispose();

                }
                else
                {
                    m_completeErrorCallback.Invoke(l_wwwRequest.error);
                }
            }

        IEnumerator DownLoadAsset(string a_url, Action<T> OnImageLoadComplete, Action<string> OnImageLoadError)
        {
            m_completeCallback = OnImageLoadComplete;
            m_completeErrorCallback = OnImageLoadError;
            WWW l_wwwRequest = new WWW(a_url);
         
            while (!l_wwwRequest.isDone)
            { 
                yield return new WaitForEndOfFrame();
            }
            Type currentLoadType = typeof(T);
            object l_value = null;
            if(currentLoadType == typeof(Texture2D))
            {
                l_value = l_wwwRequest.texture;
            }
            if (currentLoadType == typeof(AssetBundle))
            {
                l_value = l_wwwRequest.assetBundle;

            }
            m_currentData = (T)l_value;
          
            if (string.IsNullOrEmpty(l_wwwRequest.error))
            {
               
               m_completeCallback.Invoke(m_currentData);

                AssetBundle temp = m_currentData as AssetBundle;

                temp.Unload(false);
              l_wwwRequest.Dispose();
               
            }
            else
            {
                m_completeErrorCallback.Invoke(l_wwwRequest.error);
            }
        }
    }
}
