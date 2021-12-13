using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


namespace Flow
{
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
        //Componente de boton
        [SerializeField]
        Button button;

        //Nivel que representa este boton
        int mylevel;

        public void initData(string text, int level, int levelGroup, Color backgroundColor)
        {
            mylevel = (level - 1) + ((levelGroup - 1) * 30);
            buttonText.text = text;

            if (GameManager.getInstance().isCurrentCategoryLocked() && GameManager.getInstance().getCurrentCompletedLevels() < mylevel)
            {
                lockImage.enabled = true;
                button.interactable = false;
                buttonText.text = "";
            }
            else
                buttonImage.color = backgroundColor;
        }


        public void selectLevel()
        {
            GameManager.getInstance().prepareLevel(mylevel);
        }

        public RectTransform getRectTransform()
        {
            return myRectTr;
        }
    }
}
