using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


namespace Flow
{

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
        RectTransform verticalLayoutPrefab;

        [SerializeField]
        [Tooltip("Prefab utilizado para almacenar elementos de manera ordenada horizintalmente")]
        HorizontalLayoutGroup horizontalLayoutPrefab;

        [SerializeField]
        [Tooltip("Prefab que representa el titulo de cada uno de los sibgrupos de niveles")]
        TitleBehaviour levelGroupTitle;

        [SerializeField]
        [Tooltip("Prefab que representa cada uno de los botones de la pantalla de seleccion de nivel")]
        LevelButtonBehaviour levelButtonPrefab;

        [SerializeField]
        [Tooltip("Prefab que representa un panel vacio")]
        GameObject panelVacio;

        [SerializeField]
        int defaultPack = 0;
        [SerializeField]
        int lvl = 0;

        [SerializeField]
        int numLevelsByGroup=30;

        [SerializeField]
        int numButtonsRow = 5;

        int numFila = 5; //Numero de botones que vamos a tener en cada una de las filas en la seleccion de nivel
        float extraWidth = 0f; //Variable en la que se acumula el tamaño extra que hay que darle al elemento de la UI que va a hacer scroll

        void Start()
        {

//#if UNITY_EDITOR
//            GameManager.getInstance().setCurrentPackage(lvl, defaultPack);
//#endif

            LevelsInfo levelData = GameManager.getInstance().getCurrentPackage();
            //Preparo la UI 
            uiText.color = GameManager.getInstance().getPackColor(); ; //Este color deberia de ser el del paquete al que pertenece este grupo de niveles TODO
            uiText.text = levelData.packName;

            //Me hago con el numero de niveles que hay en este fichero
            TextAsset map = levelData.maps;
            string info = map.ToString();
            string[] levels = info.Split('\n');
            int numBloquesNiveles = levels.Length / numLevelsByGroup;


            Instantiate(panelVacio, contentFather.transform);
            float horizontalSpace = 0;
            //Todos estos niveles se meten en contenedores que tendran un titulo y 30 niveles (agrupados de 5 en 5)
            for (int i = 0; i < numBloquesNiveles; i++)
            {
                //Preparo el contenedor vertical
                RectTransform newVer = Instantiate(verticalLayoutPrefab, contentFather.transform);
                //Meto un titulo
                TitleBehaviour groupTitle = Instantiate(levelGroupTitle, newVer.transform);
                groupTitle.initData("Grupo " + (i + 1).ToString(), GameManager.getInstance().getSkin()[i], new Color(0, 0, 0, 0));

                //Añado de 5 en 5, botones que representen los niveles disponibles en cada subrupo
                for (int j = 0; j <  numLevelsByGroup/numButtonsRow; j++)
                {
                    horizontalSpace = prepareABlock(j * numFila, (j * numFila) + numFila, i + 1, newVer.gameObject, levelData);
                }
                newVer.SetSizeWithCurrentAnchors(RectTransform.Axis.Horizontal, horizontalSpace);

                extraWidth += horizontalSpace;
            }

            Instantiate(panelVacio, contentFather.transform);
            //Le digo al contenedor de todos estos elementos que se haga mas grande para poder hacer el scroll
            scrollableCanvas.SetSizeWithCurrentAnchors(RectTransform.Axis.Vertical, scrollableCanvas.sizeDelta.x + extraWidth);

        }



        /// <summary>
        /// Metodo que en la pantalla de seleccion de nivel, mete 5 botones en un contenedor horizontal que se va a añadir a la UI
        /// </summary>
        /// <param name="init">Numero del nivel que representa el primer boton </param>
        /// <param name="end">Numero del nivel que representa el ultimo boton</param>
        /// <param name="group">Numero del grupo al que pertenecen los nuevos botones</param>
        /// <param name="verticalLayout"> Contenedor de todo lo que se cree en este metodo</param>
        float prepareABlock(int init, int end, int group, GameObject verticalLayout, LevelsInfo levelData)
        {
            //Preparo un contenedor horizontal para los botones y lo hago hijo del contenedor vertical que me llega
            HorizontalLayoutGroup newHor = Instantiate(horizontalLayoutPrefab, verticalLayout.transform);
            float space = 0f;
            float buttonWidth = 0f;
            float spacing = newHor.spacing;
            //Creo los botones de una fila y les configuro para que cada uno represente un nivel distinto
            for (int i = init + 1; i <= end; i++)
            {
                LevelButtonBehaviour newButton = Instantiate(levelButtonPrefab, newHor.transform);
                if (buttonWidth == 0f) buttonWidth = newButton.getRectTransform().sizeDelta.x;
                newButton.initData((i).ToString(), i, group, GameManager.getInstance().getSkin()[group - 1]);

                space = space + spacing + buttonWidth;
            }
            newHor.gameObject.GetComponent<RectTransform>().SetSizeWithCurrentAnchors(RectTransform.Axis.Horizontal, space);
            return space;
        }

    }

}