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

        [SerializeField]
        RectTransform myRectTr;

        [SerializeField]
        Image buttonImage;

        //Nivel que representa este boton
        int mylevel;

        int myGroup;

        public void initData(string text, int level, int levelGroup, Color backgroundColor)
        {
            mylevel = level;
            buttonText.text = text;
            myGroup = levelGroup;
            buttonImage.color = backgroundColor;
        }


        public void selectLevel()
        {
            print("Yo represento el nivel Nivel" + mylevel.ToString() + " con grupo " + myGroup.ToString());
            //Llamada correspondiente a quien tenga que mandarnos a un nivel concreto
            GameManager.getInstance().prepareLevel(mylevel+ ((myGroup-1)*30));
        }

        public RectTransform getRectTransform()
        {
            return myRectTr;
        }
    }
}
