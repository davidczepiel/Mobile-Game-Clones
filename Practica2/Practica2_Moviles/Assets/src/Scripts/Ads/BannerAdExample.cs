using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Advertisements;


/// <summary>
/// Clase que controla los ads de tipo Banner
/// </summary>
public class BannerAdExample : MonoBehaviour
{
    [Tooltip("Posicion en la que se quiere situar al banner")]
    [SerializeField] 
    BannerPosition _bannerPosition = BannerPosition.BOTTOM_CENTER;


    string _androidAdUnitId = "Banner_Android";
    string _iOsAdUnitId = "Banner_iOS";
    string _adUnitId;

    void Start()
    {
#if UNITY_IOS
		_adUnitId = _iOsAdUnitId;
#elif UNITY_ANDROID
        _adUnitId = _androidAdUnitId;
#endif
        // Set the banner position:
        Advertisement.Banner.SetPosition(_bannerPosition);
        LoadBanner();
    }

    // Implement a method to call when the Load Banner button is clicked:
    public void LoadBanner()
    {
        // Set up options to notify the SDK of load events:
        BannerLoadOptions options = new BannerLoadOptions
        {
            loadCallback = OnBannerLoaded,
            errorCallback = OnBannerError
        };

        // Load the Ad Unit with banner content:
        Advertisement.Banner.Load(_adUnitId, options);
    }

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

    // Implement a method to call when the Show Banner button is clicked:


    void OnBannerClicked() { }
    void OnBannerShown() { }
    void OnBannerHidden() { }

    void OnDestroy()
    {
    }
}