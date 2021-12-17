using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TouchFeedback : MonoBehaviour
{
    [Tooltip("Renderer del objeto de feedback")]
    [SerializeField]
    SpriteRenderer _renderer;

    public void ChangeColor(Color color, bool canDraw)
    {
        _renderer.enabled = true;
        if (!canDraw)
        {
            color.r = (color.r - 0.5f < 0) ? 0 : color.r - 0.5f;
            color.g = (color.g - 0.5f < 0) ? 0 : color.g - 0.5f;
            color.b = (color.r - 0.5f < 0) ? 0 : color.b - 0.5f;
        }
        color.a = 0.5f;
        _renderer.color = color;
    }

    public void ChangePos(Vector2 pos)
    {
        this.transform.position = pos;
    }

    public void Disable()
    {
        _renderer.enabled = false;
    }


}
