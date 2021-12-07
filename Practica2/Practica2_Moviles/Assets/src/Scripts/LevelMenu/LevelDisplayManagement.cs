using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelDisplayManagement : MonoBehaviour
{
    [SerializeField]
    GameObject contentFather;

    //Canvas que vamos a hacer mas grande para scrollear
    [SerializeField]
    RectTransform scrollableCanvas;

    //Texto de la ui de arriba
    [SerializeField]
    Text uiText;

    //Prefab de cada uno de los botones
    [SerializeField]
    GameObject levelButtonPrefab;

    [SerializeField]
    GameObject horizontalLayoutPrefab;

    int numFila = 5;
    float extraHeight = 0f;

    void Start()
    {
        uiText.color = Color.red;
        TextAsset map = null;
        string info = map.ToString();
        string[] levels = info.Split('\n');

        int numBloquesNiveles = levels.Length / 30;
        for (int i = 0; i < numBloquesNiveles; i++)
        {
            for (int j = 0; j < 6; j++)
            {
                prepareABlock(j*5,(j*5)+6);
            }
        }

        scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.y + extraHeight);
    }

    void prepareABlock(int init, int end)
    {
        GameObject newHor = Instantiate(horizontalLayoutPrefab, contentFather.transform);
        for (int i = 0; i < end - init; i++)
        {
            GameObject newButton = Instantiate(levelButtonPrefab, newHor.transform);
            newButton.GetComponent<LevelButtonBehaviour>().initData((i-init).ToString(),i); //el string va de 1 a 30 y el nivel es el que sea
        }
    }

}
