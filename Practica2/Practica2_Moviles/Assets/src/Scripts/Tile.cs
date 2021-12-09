using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class Tile : MonoBehaviour
    {
        public enum TileType { voidTile, connectedTile, pipeHead, circleTile }

        [SerializeField]
        SpriteRenderer _horizontalPipeRenderer;
        [SerializeField]
        SpriteRenderer _verticalPipeRenderer;
        [SerializeField]
        SpriteRenderer _circleRenderer;
        [SerializeField]
        SpriteRenderer _backgroundRenderer;
        [SerializeField]
        SpriteRenderer _hintRenderer;

        [SerializeField]
        Sprite _bigCircleImage;
        [SerializeField]
        Sprite _pipeImage;

        TileType _myType = TileType.voidTile;  //El tipo actual de este tile
        Color _tileColor; //Color de la tuberia/circulo
        Vector2 _direction; //Dir en la que  se esta mostrando este pipe

        bool _hidden = false; //Indica si no se esta mostrando, aunque no sea vacio

        bool[] walls = { false, false, false, false }; //Arriba,abajo,izquierda,derecha

        //TODO private Animation animacion;

        #region Getters y setters
        /// +--------------------------------------------------------------------------------------+
        /// |                                 GETTERS Y SETTERS                                    |
        /// +--------------------------------------------------------------------------------------+
        public TileType getTileType()
        {
            return _myType;
        }

        public void setTileType(TileType newType)
        {
            switch (newType)
            {
                case TileType.pipeHead:
                    changeTileAppearance(_direction.x == 1, _direction.y == 1, true, false);
                    break;
                case TileType.circleTile:
                    changeTileAppearance(_direction.x == 1, _direction.y == 1, true, false);
                    break;
                case TileType.voidTile:
                    changeTileAppearance(false, false, false, false);
                    break;
                case TileType.connectedTile:
                    changeTileAppearance(_direction.x == 1, _direction.y == 1, false, false);
                    break;
            }
            _myType = newType;
        }

        public void setTileColor(Color newColor)
        {
            _tileColor = newColor;
            _horizontalPipeRenderer.material.color = _tileColor;
            _verticalPipeRenderer.material.color = _tileColor;
            _circleRenderer.material.color = _tileColor;
            _backgroundRenderer.material.color = _tileColor;
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
            _horizontalPipeRenderer.enabled = _direction.x == 1;
            _verticalPipeRenderer.enabled = _direction.y == 1;
        }

        public void setHorizontalConnection(bool active)
        {
            _direction.x = active ? 1 : 0;
            _horizontalPipeRenderer.enabled = active;
        }

        public void setVerticalConnection(bool active)
        {
            _direction.y = active ? 1 : 0;
            _verticalPipeRenderer.enabled = active;
        }

        public bool isHidden()
        {
            return _hidden;
        }

        public void setWall(int index, bool value)
        {
            walls[index] = value;
        }

        public bool getWall(int index)
        {
            return walls[index];
        }

        public Color getBackgroundColor()
        {
            return _backgroundRenderer.color;
        }

        #endregion

        #region Metodos de control

        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS DE CONTROL                                   |
        /// +--------------------------------------------------------------------------------------+

        public void initTile(TileType type, Color c)
        {
            setTileColor(c);
            setTileType(type);
            _circleRenderer.sprite = _bigCircleImage;

        }

        public void hide()
        {
            _hidden = true;
            changeTileAppearance(true,false,false,false);
        }

        public void activate()
        {
            _hidden = false;
            _backgroundRenderer.enabled = true;
            if (_direction.x == 1)
                _horizontalPipeRenderer.enabled = true;
            if (_direction.y == 1)
                _verticalPipeRenderer.enabled = true;

            if (_myType == TileType.pipeHead)
                _circleRenderer.enabled = true;
        }

        public void setTileAttributes(TileInfo tileInfo)
        {
            _myType = tileInfo.type;
            setTileColor(tileInfo.color);
            _direction = tileInfo.direction;
        }

        #endregion

        #region Metodos Auxiliares

        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS AUXILIARES                                   |
        /// +--------------------------------------------------------------------------------------+

        private void changeTileAppearance(bool horPipe, bool verPipe, bool circlerend, bool backG) 
        {
            _horizontalPipeRenderer.enabled = horPipe;
            _verticalPipeRenderer.enabled = verPipe;
            _circleRenderer.enabled = circlerend;
            _backgroundRenderer.enabled = backG;
        }

        #endregion
    }

    public struct TileInfo
    {
        public Tile.TileType type;
        public Color color; 
        public Vector2 direction;
        public Tile associatedTile;

        public TileInfo(Tile tile)
        {
            type = tile.getTileType();
            color = tile.getColor();
            direction = tile.getDirection();
            associatedTile = tile;
        }
    }
}


