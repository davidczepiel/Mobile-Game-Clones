using UnityEngine;
using UnityEngine.UI;


/// <summary>
/// Clase que se encarga de la gestion visual de los textos de las categorias
/// </summary>
public class TitleBehaviour : MonoBehaviour
{
    [SerializeField]
    Text text;
    [SerializeField]
    Image background;

    /// <summary>
    /// MEtodo que inicializa el texto con los datos recibidos
    /// </summary>
    /// <param name="textA"></param>
    /// <param name="textColor"></param>
    /// <param name="backgroundolor"></param>
    public void initData(string textA,Color textColor, Color backgroundolor)
    {
        text.text = "\t" + textA;
        text.color = textColor;
        background.color = backgroundolor;
    }
}
