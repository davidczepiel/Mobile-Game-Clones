using System.Collections;
using System.Collections.Generic;
using UnityEngine;


/// <summary>
/// Colores asociados a una pile para el juego
/// </summary>
[CreateAssetMenu(fileName = "LevelSkin", menuName = "Flow/LevelsSkin", order = 1)]
public class LevelSkin : ScriptableObject
{
    public Color[] levelColors;
}
