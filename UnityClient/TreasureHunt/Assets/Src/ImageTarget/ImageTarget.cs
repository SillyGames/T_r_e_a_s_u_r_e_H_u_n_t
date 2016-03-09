using UnityEngine;
using System.Collections;
namespace TreasureHunt
{
    public class ImageTarget : MonoBehaviour
    {
        [SerializeField]
        private string m_imageTargetID;
        public string ImageTargetID
        {
            get
            {
                return m_imageTargetID;
            }
        }
        [SerializeField]
        private string m_currentActiveTargetID;
        public string CurrentCurrentActiveChildID
        {
            get
            {
                return m_currentActiveTargetID;
            }
        }
        [SerializeField]
        private Texture m_texture;
        // Use this for initialization
        void Start()
        {
            Vuforia.DefaultTrackableEventHandler.Instance.addEventListener(Vuforia.DefaultTrackableEventHandler.TrackableEvent.OnTrackingFound.ToString(), OnTrackingFound);
        }

        // Update is called once per frame
        void Update() {

        }

        private void OnTrackingFound(object sender, GameEventArgs args)
        {
            Debug.Log("Image Target is Found" + args.EventData.ToString());
            GameManager.Instance.SendRequestOnTrackbleImageFound(args.EventData.ToString());
        }
    }
}
