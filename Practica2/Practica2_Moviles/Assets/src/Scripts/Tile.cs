using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public class Tile : MonoBehaviour
    {
        public enum TileType { voidTile, connectedTile, circleTile, emptyTile }

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
        SpriteRenderer _upWallRenderer;
        [SerializeField]
        SpriteRenderer _downWallRenderer;
        [SerializeField]
        SpriteRenderer _leftWallRenderer;
        [SerializeField]
        SpriteRenderer _rightWallRenderer;
        [SerializeField]
        Animator animator;

        [SerializeField]
        Sprite _bigCircleImage;
        [SerializeField]
        Sprite _pipeImage;

        [SerializeField]
        SpriteRenderer[] grid;

        TileType _myType = TileType.voidTile;  //El tipo actual de este tile
        Color _tileColor; //Color de la tuberia/circulo
        Vector2 _direction; //Dir en la que  se esta mostrando este pipe

        bool[] walls = { false, false, false, false }; //Arriba,abajo,izquierda,derecha

        //TODO private Animation animacion;

        public void initTile(TileType type, Color c)
        {
            setTileColor(c);
            setTileType(type);
            _circleRenderer.sprite = _bigCircleImage;

        }

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
                case TileType.circleTile:
                    changeTileAppearance(_direction.x == 1, _direction.y == 1, true);
                    break;
                case TileType.voidTile:
                    changeTileAppearance(false, false, false);
                    _backgroundRenderer.enabled = false;
                    break;
                case TileType.connectedTile:
                    changeTileAppearance(_direction.x == 1, _direction.y == 1, false);
                    break;
                case TileType.emptyTile:
                    foreach (SpriteRenderer s in grid)
                        s.enabled = false;
                    changeTileAppearance(false, false, false);
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

        public void startAnimation()
        {
            animator.Play("circle");
        }

        public void setStar(bool t)
        {
            _hintRenderer.enabled = t;
        }

        public void setBackGround(bool t)
        {
            _backgroundRenderer.enabled = t;
        }

        public Color getColor()
        {
            return _tileColor;
        }

        public Vector2 getDirection()
        {
            return _direction;
        }

        public void setDirection(int x, int y)
        {
            _direction.x = x;   _direction.y = y;
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

        public void setWall(int index, bool value)
        {
            walls[index] = value;

            switch (index)
            {
                case 0: _upWallRenderer.enabled = value; break;
                case 1: _downWallRenderer.enabled = value; break;
                case 2: _leftWallRenderer.enabled = value; break;
                case 3: _rightWallRenderer.enabled = value; break;
            }
        }

        public bool getWall(int index)
        {
            return walls[index];
        }

        #endregion

        #region Metodos de control

        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS DE CONTROL                                   |
        /// +--------------------------------------------------------------------------------------+


        #endregion

        #region Metodos Auxiliares

        /// +--------------------------------------------------------------------------------------+
        /// |                                 METODOS AUXILIARES                                   |
        /// +--------------------------------------------------------------------------------------+

        private void changeTileAppearance(bool horPipe, bool verPipe, bool circlerend) 
        {
            _horizontalPipeRenderer.enabled = horPipe;
            _verticalPipeRenderer.enabled = verPipe;
            _circleRenderer.enabled = circlerend;
        }

        #endregion
    }
}


