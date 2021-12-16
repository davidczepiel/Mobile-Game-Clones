using UnityEngine;
using UnityEngine.Advertisements;


namespace Flow
{
    /// <summary>
    /// Manager que inicializa el sistema de Ads de Unity
    /// </summary>
    public class AddsManager : MonoBehaviour, IUnityAdsInitializationListener
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


        private string _gameId; 
      

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
            Debug.Log("Unity Ads initialization complete.");
        }

        public void OnInitializationFailed(UnityAdsInitializationError error, string message)
        {
            Debug.Log($"Unity Ads Initialization Failed: {error.ToString()} - {message}");
        }
        ///------------------------------------ Callbacks-------------------------------
    }
}