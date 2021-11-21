using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class Tile : MonoBehaviour
    {
        public enum TileType { voidTile, connectedTile, circleTile }

        [SerializeField]
        SpriteRenderer _pipe;
        [SerializeField]
        SpriteRenderer _circleRenderer;

        [SerializeField]
        Sprite _circleImage;
        [SerializeField]
        Sprite imageHint;
        [SerializeField]
        Sprite _pipeImage;

        TileType _myType = TileType.voidTile;
        Color _tileColor;
        Vector2 _direction;

        //Arriba,abajo,izquierda,derecha
        bool[] walls = { false, false, false, false };

        //TODO private Animation animacion;

        public void setTileType(TileType newType)
        {
            _myType = newType;
        }

        public TileType getTileType()
        {
            return _myType;
        }

        public void setColor(Color newColor)
        {
            _tileColor = newColor;
            _pipe.material.color = _tileColor;
        }

        public Color getColor()
        {
            return _tileColor;
        }

        public Vector2 getDirection()
        {
            return _direction;
        }

        public void setDirection(Vector2 newDir)
        {
            _direction = newDir;
        }

        public void setWall(int index, bool value)
        {
            walls[index] = value;
        }

        public bool getWall(int index)
        {
            return walls[index];
        }
    }
}


