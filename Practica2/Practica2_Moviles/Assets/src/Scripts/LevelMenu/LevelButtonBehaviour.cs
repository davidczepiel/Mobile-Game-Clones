using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


namespace Flow
{
    /// <summary>
    /// Clase que guarda la informacion necesaria para una vez clicado el boton se mande para pasar al nivel correspondiente
    /// </summary>
    public class LevelButtonBehaviour : MonoBehaviour
    {

        //Texto que va a variar y representara el nivel al que lleva este boton
        [SerializeField]
        Text buttonText;
        //Rect de la posicion del boton
        [SerializeField]
        RectTransform myRectTr;
        //Imagen del boton
        [SerializeField]
        Image buttonImage;
        //Imagen que muestra el candado
        [SerializeField]
        Image lockImage;
        //Imagen que muestra el tick de nivel pasado
        [SerializeField]
        Image tickImage;
        //Componente de boton
        [SerializeField]
        Button button;

        //Nivel que representa este boton
        int mylevel;


        /// <summary>
        /// Metodo que inicializa el boton segun los parametros recibidos
        /// </summary>
        /// <param name="level">Nivel que representa el boton (original) </param>
        /// <param name="levelGroup">Grupo al que pertenece el boton</param>
        /// <param name="backgroundColor"></param>
        public void initData( int level, int levelGroup, Color backgroundColor)
        {
            mylevel = (level - 1) + ((levelGroup - 1) * 30);
            buttonText.text = level.ToString();

            if (GameManager.getInstance().isCurrentCategoryLocked() && GameManager.getInstance().getCurrentCompletedLevels() < mylevel)
            {
                lockImage.enabled = true;
                button.interactable = false;
                buttonText.text = "";
            }
            else
            {
                if (GameManager.getInstance().isCurrentCategoryLocked() && GameManager.getInstance().getCurrentCompletedLevels() > mylevel)
                {
                    buttonText.text = "";
                    tickImage.enabled = true;
                }
                buttonImage.color = backgroundColor;
            }
        }


        /// <summary>
        /// Callback llamado al pulsarse el boton
        /// </summary>
        public void selectLevel()
        {
            GameManager.getInstance().prepareLevel(mylevel);
        }

        /// <summary>
        /// Getter del recttransform
        /// </summary>
        /// <returns>rect transform del boton</returns>
        public RectTransform getRectTransform()
        {
            return myRectTr;
        }
    }
}
