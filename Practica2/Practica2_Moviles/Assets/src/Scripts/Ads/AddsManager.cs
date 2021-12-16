using UnityEngine;
using UnityEngine.Advertisements;


namespace Flow
{
    /// <summary>
    /// Manager que inicializa el sistema de Ads de Unity
    /// </summary>
    public class AddsManager : MonoBehaviour, IUnityAdsInitializationListener, IUnityAdsLoadListener, IUnityAdsShowListener
    {
        [Tooltip("ID del juego en la plataforma de Android")]
        [SerializeField] 
        string _androidGameId;
        
        [Tooltip("ID del juego en la plataforma de Apple")]
        [SerializeField] 
        string _iOSGameId;

        [Tooltip("Indica si estamos en debug o no")]
        [SerializeField] 
        bool _testMode = true;

        bool flag = false;

        string _adUnitId = "Rewarded_Android";

        private string _gameId; 

        System.Action<bool> videoFinishedCallBack;
        System.Action rewardLoadedCallBack;
        System.Action intersecCallBack;

        public void setCallBack(System.Action<bool> callback, System.Action rewardLoadedCallBack, System.Action intersec)
        {
            this.videoFinishedCallBack = callback;
            this.rewardLoadedCallBack = rewardLoadedCallBack;
            this.intersecCallBack = intersec;
        }

        public void setType(string type = "Rewarded_Android")
        {
            this._adUnitId = type;
            flag = false;
        }

        public void loadAds()
        {
            Advertisement.Load(_adUnitId, this);
        }
     

        void Awake()
        {
            InitializeAds();
        }


        /// <summary>
        /// Inicializa los Ads dependiendo de la plataforma en la que estemos
        /// </summary>
        public void InitializeAds()
        {
            Debug.Log("Ads Manager initialization started.");

            _gameId = (Application.platform == RuntimePlatform.IPhonePlayer)
                ? _iOSGameId
                : _androidGameId;
            Advertisement.Initialize(_gameId, _testMode, this);
        }


        
        ///------------------------------------ Callbacks-------------------------------
        public void OnInitializationComplete()
        {
            string _androidAdUnitId = "Banner_Android";
            string _iOsAdUnitId = "Banner_iOS";
            string _adUnitId;
#if UNITY_IOS
		_adUnitId = _iOsAdUnitId;
#elif UNITY_ANDROID
            _adUnitId = _androidAdUnitId;
#endif
            BannerLoadOptions options = new BannerLoadOptions
            {
                loadCallback = OnBannerLoaded,
                errorCallback = OnBannerError
            };
            Advertisement.Banner.SetPosition(BannerPosition.BOTTOM_CENTER);
            Advertisement.Banner.Load(_adUnitId, options);
            Debug.Log("Unity Ads initialization complete.");
        }

        public void OnInitializationFailed(UnityAdsInitializationError error, string message)
        {
            Debug.Log($"Unity Ads Initialization Failed: {error.ToString()} - {message}");
        }
        ///------------------------------------ Callbacks-------------------------------
        ///
        #region banner
        // Implement code to execute when the loadCallback event triggers:
        void OnBannerLoaded()
        {
            Debug.Log("Banner loaded");
            // Set up options to notify the SDK of show events:
            BannerOptions options = new BannerOptions
            {
                clickCallback = OnBannerClicked,
                hideCallback = OnBannerHidden,
                showCallback = OnBannerShown
            };

            // Show the loaded Banner Ad Unit:
            Advertisement.Banner.Show(_adUnitId, options);
        }

        // Implement code to execute when the load errorCallback event triggers:
        void OnBannerError(string message)
        {
            Debug.Log($"Banner Error: {message}");
            // Optionally execute additional code, such as attempting to load another ad.
        }

        void OnBannerClicked() { }
        void OnBannerShown() { }
        void OnBannerHidden() { }
        #endregion

        #region Rewarded y Interstitial
        //------------------------------------------REWADED---------------------------------------
        // If the ad successfully loads, add a listener to the button and enable it:
        public void OnUnityAdsAdLoaded(string adUnitId)
        {
            Debug.Log("Ad Loaded: " + adUnitId);
            if (adUnitId == "Rewarded_Android" || adUnitId == "Rewarded_iOS")
                rewardLoadedCallBack?.Invoke();
            else
                ShowAd();
         }

        // Implement a method to execute when the user clicks the button.
        public void ShowAd()
        {
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!WARNING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            // Esta condicion es debida a que la actualizacion tiene un error mientras se encuentra en el editor
            // debido a que hay "cola de listeners". Esto en build no ocurre. Por ello en UNITY EDITOR la primera
            // vez se le pasa el Listener y luego no.
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
#if UNITY_EDITOR
            // Then show the ad:
            if (!flag)
            {
                Advertisement.Show(_adUnitId, this);
                flag = true;
            }
            else
                Advertisement.Show(_adUnitId);
            #else
            Advertisement.Show(_adUnitId, this);
            #endif
        }

        // Implement the Show Listener's OnUnityAdsShowComplete callback method to determine if the user gets a reward:
        public void OnUnityAdsShowComplete(string adUnitId, UnityAdsShowCompletionState showCompletionState)
        {
            if (adUnitId.Equals(_adUnitId) && showCompletionState.Equals(UnityAdsShowCompletionState.COMPLETED))
            {
                if (adUnitId == "Rewarded_Android" || adUnitId == "Rewarded_iOS")
                    videoFinishedCallBack?.Invoke(true);
                else
                    intersecCallBack?.Invoke();

                // Load another ad:
                Advertisement.Load(_adUnitId, this);
            }
            else if (adUnitId.Equals(_adUnitId) && showCompletionState.Equals(UnityAdsShowCompletionState.SKIPPED))
            {

                intersecCallBack?.Invoke();

                // Load another ad:
                Advertisement.Load(_adUnitId, this);
            }
        }

        // Implement Load and Show Listener error callbacks:
        public void OnUnityAdsFailedToLoad(string adUnitId, UnityAdsLoadError error, string message)
        {
            Debug.Log($"Error loading Ad Unit {adUnitId}: {error.ToString()} - {message}");
            // Use the error details to determine whether to try to load another ad.
        }

        public void OnUnityAdsShowFailure(string adUnitId, UnityAdsShowError error, string message)
        {
            Debug.Log($"Error showing Ad Unit {adUnitId}: {error.ToString()} - {message}");
            // Use the error details to determine whether to try to load another ad.
        }

        public void OnUnityAdsShowStart(string adUnitId) { }
        public void OnUnityAdsShowClick(string adUnitId) { }
    }
    #endregion
}