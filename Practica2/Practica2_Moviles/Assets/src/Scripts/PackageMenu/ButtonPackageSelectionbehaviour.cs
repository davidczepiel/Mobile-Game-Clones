using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace Flow
{
    public class ButtonPackageSelectionbehaviour : MonoBehaviour
    {

        [SerializeField]
        [Tooltip("Texto de la UI que mostrara el titulo de cada uno de los paquetes")]
        Text packageTitle;

        [SerializeField]
        [Tooltip("Texto de la UI que mostrara el numero de niveles que se han completado de este paquete")]
        Text numLevelsCompleted;

        //Info que almacenamos sobre el paquete de niveles que representamos
        int package, category;


        /// <summary>
        /// Recibe la info del paquete de niveles que representa este boton 
        /// </summary>
        /// <param name="p"> Info del paquete de niveles </param>
        public void initData(LevelsInfo p, int indexPack, int indexCategory, Color textColor)
        {
            //Ajusto la UI para que muestre los datos del paquete
            packageTitle.text = "\t" + p.packName;
            packageTitle.color = textColor;
            numLevelsCompleted.text = p.levelsCompleted.ToString() + "/" + p.numLevels.ToString() + "\t";
            //Me guardo la info para mas adelante
            package = indexPack;    category = indexCategory;
        }

        /// <summary>
        /// Metodo llamado cuando el boton es pulsado, el objetivo de este es llamar al gamemanager para 
        /// que pasemos a la siguiente pantalla del juego con los datos del paquete 
        /// de niveles que representa este boton
        /// </summary>
        public void LoadLevelPackage()
        {    
            GameManager.getInstance().setCurrentPackage(package, category);
            GameManager.getInstance().changeScene("LevelSelectionMenu");
        }
    }

}