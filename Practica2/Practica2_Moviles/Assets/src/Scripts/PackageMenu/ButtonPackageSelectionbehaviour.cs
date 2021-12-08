using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ButtonPackageSelectionbehaviour : MonoBehaviour
{
    
    [SerializeField]
    [Tooltip("Texto de la UI que mostrara el titulo de cada uno de los paquetes")]
    Text packageTitle;

    [SerializeField]
    [Tooltip("Texto de la UI que mostrara el numero de niveles que se han completado de este paquete")]
    Text numLevelsCompleted;

    //Info que almacenamos sobre el paquete de niveles que representamos
    LevelsInfo package;


    /// <summary>
    /// Recibe la info del paquete de niveles que representa este boton 
    /// </summary>
    /// <param name="p"> Info del paquete de niveles </param>
    public void initData(LevelsInfo p)
    {
        //Ajusto la UI para que muestre los datos del paquete
        packageTitle.text = p.packName;
        numLevelsCompleted.text = p.levelsCompleted.ToString() +"/"+ p.numLevels.ToString();
        //Me guardo la info para mas adelante
        package = p;
    }

    /// <summary>
    /// Metodo llamado cuando el boton es pulsado, el objetivo de este es llamar al gamemanager para 
    /// que pasemos a la siguiente pantalla del juego con los datos del paquete 
    /// de niveles que representa este boton
    /// </summary>
    public void LoadLevelPackage()
    {
        print("Se ha seleccionado el paquete " + package.packName);
        //GameManager...(package)
    }
}

