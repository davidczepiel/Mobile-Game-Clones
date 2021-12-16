using UnityEngine;


/// <summary>
/// Contenedor que guarda la info de un paquete de niveles
/// </summary>
[CreateAssetMenu(fileName = "LevelsInfo", menuName = "Flow/LevelsInfo", order = 1)]
public class LevelsInfo : ScriptableObject
{
    public int levelsCompleted;         //Numero de niveles completados
    public string packName;             //Nombre del paquete
    public TextAsset maps;              //Archivo con toda la info de todos los niveles del paquete
    public int numLevels;               //Numero de niveles que hay en el paquete
}