/*==============================================================================
Copyright (c) 2010-2014 Qualcomm Connected Experiences, Inc.
All Rights Reserved.
Confidential and Proprietary - Qualcomm Connected Experiences, Inc.
==============================================================================*/

using System;
using UnityEngine;

namespace Vuforia
{
    /// <summary>
    /// A custom handler that implements the ITrackableEventHandler interface.
    /// </summary>
    public class DefaultTrackableEventHandler : MonoBehaviour,
                                                ITrackableEventHandler,IEventDispatcher
    {
        #region PRIVATE_MEMBER_VARIABLES
 
        private TrackableBehaviour mTrackableBehaviour;
        private EventManager m_eventManager;

        public enum TrackableEvent
        {
            OnTrackingFound,
            OnTrackingLost
        }

        #endregion // PRIVATE_MEMBER_VARIABLES
        private static DefaultTrackableEventHandler m_instance;
        public static DefaultTrackableEventHandler Instance
        {
            get
            {

                return m_instance;
            }
        }


        #region UNTIY_MONOBEHAVIOUR_METHODS
        void Awake()
        {
            m_instance = this;
            m_eventManager = new EventManager();
        }

        void Start()
        {
            mTrackableBehaviour = GetComponent<TrackableBehaviour>();
            if (mTrackableBehaviour)
            {
                mTrackableBehaviour.RegisterTrackableEventHandler(this);
            }
           
        }

        #endregion // UNTIY_MONOBEHAVIOUR_METHODS



        #region PUBLIC_METHODS

        /// <summary>
        /// Implementation of the ITrackableEventHandler function called when the
        /// tracking state changes.
        /// </summary>
        public void OnTrackableStateChanged(
                                        TrackableBehaviour.Status previousStatus,
                                        TrackableBehaviour.Status newStatus)
        {
           
            if (newStatus == TrackableBehaviour.Status.DETECTED ||
                newStatus == TrackableBehaviour.Status.TRACKED ||
                newStatus == TrackableBehaviour.Status.EXTENDED_TRACKED)
            {
                OnTrackingFound();
            }
            else
            {
                OnTrackingLost();
            }
        }

        #endregion // PUBLIC_METHODS



        #region PRIVATE_METHODS


        private void OnTrackingFound()
        {
            Renderer[] rendererComponents = GetComponentsInChildren<Renderer>(true);
            Collider[] colliderComponents = GetComponentsInChildren<Collider>(true);

            // Enable rendering:
            foreach (Renderer component in rendererComponents)
            {
                component.enabled = true;
            }

            // Enable colliders:
            foreach (Collider component in colliderComponents)
            {
                component.enabled = true;
            }

            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " found");
            GameBaseEvent l_traceEvent = new GameBaseEvent(TrackableEvent.OnTrackingFound.ToString());
            dispatchEvent(this, l_traceEvent);
        }


        private void OnTrackingLost()
        {
            Renderer[] rendererComponents = GetComponentsInChildren<Renderer>(true);
            Collider[] colliderComponents = GetComponentsInChildren<Collider>(true);

            // Disable rendering:
            foreach (Renderer component in rendererComponents)
            {
                component.enabled = false;
            }

            // Disable colliders:
            foreach (Collider component in colliderComponents)
            {
                component.enabled = false;
            }

            Debug.Log("Trackable " + mTrackableBehaviour.TrackableName + " lost");
            GameBaseEvent l_traceEvent = new GameBaseEvent(TrackableEvent.OnTrackingLost.ToString());
            dispatchEvent(this, l_traceEvent);
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

        #endregion // PRIVATE_METHODS
    }
}
