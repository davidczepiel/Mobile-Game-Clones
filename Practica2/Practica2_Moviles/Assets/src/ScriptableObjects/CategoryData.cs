using System.Collections;
using System.Collections.Generic;
using UnityEngine;


/// <summary>
/// Contenedor de info de cada una de las categorias del juego
/// </summary>
[CreateAssetMenu(fileName = "CategoryData", menuName = "Flow/CategoryData", order = 1)]
public class CategoryData : ScriptableObject
{
    public string categoryName;         //Nombre de la categoria
    public Color categoryColor;         //Color asociado a la categoria
    public LevelsInfo[] packagesData;   //Conjunto de paquetes asociados a la categoria
    public bool isLocked;               //Indica si los niveles estan desbloqueados de golpe
}
