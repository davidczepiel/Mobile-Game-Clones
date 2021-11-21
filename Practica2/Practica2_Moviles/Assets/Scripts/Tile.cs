using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class Tile : MonoBehaviour
    {
        [SerializeField]
        SpriteRenderer _circleRenderer;
        public enum TileType{ voidTile, connectedTile, circleTile}

        private Color _tileColor;
        private Vector2 _direction;
        private Sprite _circleImage;

        private Sprite _connectionImage;
        private Sprite imageTick;
        //private Animation animacion;
        //TODO imagen circulo


    }
}


