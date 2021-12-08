using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LevelDisplayManagement : MonoBehaviour
{
    [SerializeField]
    [Tooltip("Padre de todos los elementos que se van a meter en la UI y que necesitan poder hacer scroll")]
    GameObject contentFather;

    [SerializeField]
    [Tooltip("Canvas que va a almacenar los elementos generados y que ha de aumentar su tamaño segun estos")]
    RectTransform scrollableCanvas;

    [SerializeField]
    [Tooltip("Texto de la parte superior en el que se muestra el paquete de niveles en el que nos encontramos")]
    Text uiText;

    [SerializeField]
    [Tooltip("Prefab utilizado para almacenar elementos de manera ordenada verticalmente")]
    GameObject verticalLayoutPrefab;

    [SerializeField]
    [Tooltip("Prefab utilizado para almacenar elementos de manera ordenada horizintalmente")]
    GameObject horizontalLayoutPrefab;

    [SerializeField]
    [Tooltip("Prefab que representa el titulo de cada uno de los sibgrupos de niveles")]
    GameObject levelGroupTitle;

    [SerializeField]
    [Tooltip("Prefab que representa cada uno de los botones de la pantalla de seleccion de nivel")]
    GameObject levelButtonPrefab;


    [SerializeField]
    [Tooltip("Info del nivel del que tengo que sacar elementos como el titulo, numero de niveles disponibles, etc...")]
    LevelsInfo levelData;


    int numFila = 5; //Numero de botones que vamos a tener en cada una de las filas en la seleccion de nivel
    float extraHeight = 0f; //Variable en la que se acumula el tamaño extra que hay que darle al elemento de la UI que va a hacer scroll

    void Start()
    {
        //Preparo la UI 
        uiText.color = levelData.skin.levelColors[0]; //Este color deberia de ser el del paquete al que pertenece este grupo de niveles TODO
        uiText.text = levelData.packName;

        //Me hago con el numero de niveles que hay en este fichero
        TextAsset map = levelData.maps;
        string info = map.ToString();
        string[] levels = info.Split('\n');
        int numBloquesNiveles = levels.Length / 30;

        //Todos estos niveles se meten en contenedores que tendran un titulo y 30 niveles (agrupados de 5 en 5)
        for (int i = 0; i < numBloquesNiveles; i++)
        {
            //Preparo el contenedor vertical
            GameObject newVer = Instantiate(verticalLayoutPrefab, contentFather.transform);
            //Meto un titulo
            GameObject groupTitle = Instantiate(levelGroupTitle, newVer.transform);
            groupTitle.GetComponent<TitleBehaviour>().initData("grupo " + (i + 1).ToString(), new Color(0, 0, 0, 0));

            //Añado de 5 en 5, botones que representen los niveles disponibles en cada subrupo
            for (int j = 0; j < (levels.Length / 30); j++)
            {
                prepareABlock(j * numFila, (j * numFila) + numFila, i + 1, newVer);
            }
        }

        //Le digo al contenedor de todos estos elementos que se haga mas grande para poder hacer el scroll
        scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.y + extraHeight);
    }

    /// <summary>
    /// Metodo que en la pantalla de seleccion de nivel, mete 5 botones en un contenedor horizontal que se va a añadir a la UI
    /// </summary>
    /// <param name="init">Numero del nivel que representa el primer boton </param>
    /// <param name="end">Numero del nivel que representa el ultimo boton</param>
    /// <param name="group">Numero del grupo al que pertenecen los nuevos botones</param>
    /// <param name="verticalLayout"> Contenedor de todo lo que se cree en este metodo</param>
    void prepareABlock(int init, int end, int group, GameObject verticalLayout)
    {
        //Preparo un contenedor horizontal para los botones y lo hago hijo del contenedor vertical que me llega
        GameObject newHor = Instantiate(horizontalLayoutPrefab, verticalLayout.transform);
        float extraSpace = 0f;
        float actualSpace = 0f;
        float spacing = newHor.GetComponent<HorizontalLayoutGroup>().spacing;
        //Creo los botones de una fila y les configuro para que cada uno represente un nivel distinto
        for (int i = init + 1; i <= end; i++)
        {
            GameObject newButton = Instantiate(levelButtonPrefab, newHor.transform);
            if (actualSpace == 0f) actualSpace = newButton.GetComponent<RectTransform>().sizeDelta.x;

            extraSpace = extraSpace + spacing + actualSpace;
            newButton.GetComponent<LevelButtonBehaviour>().initData((i).ToString(), i, group);
        }
        newHor.GetComponent<RectTransform>().SetSizeWithCurrentAnchors(RectTransform.Axis.Horizontal, actualSpace + extraSpace);
    }

}
