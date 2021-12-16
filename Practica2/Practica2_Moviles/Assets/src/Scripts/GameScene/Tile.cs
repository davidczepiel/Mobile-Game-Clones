using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{

    /// <summary>
    /// Clase que representa un tile en la partida
    /// </summary>
    public class Tile : MonoBehaviour
    {
        public enum TileType { voidTile, connectedTile, circleTile, emptyTile }     //Tipos de estado de un tile

        [Tooltip("Tuberia para la izquierda")]
        [SerializeField]
        SpriteRenderer _horizontalPipeRenderer;
        [Tooltip("Tuberia para arriba")]
        [SerializeField]
        SpriteRenderer _verticalPipeRenderer;
        [Tooltip("Circulo para las casillas que son circulos")]
        [SerializeField]
        SpriteRenderer _circleRenderer;
        [Tooltip("Cuadrado de fondo")]
        [SerializeField]
        SpriteRenderer _backgroundRenderer;
        [Tooltip("tick que representa que se ha puesto una pista")]
        [SerializeField]
        SpriteRenderer _hintRenderer;
        [Tooltip("Muro de arriba")]
        [SerializeField]
        SpriteRenderer _upWallRenderer;
        [Tooltip("Muro de abajo")]
        [SerializeField]
        SpriteRenderer _downWallRenderer;
        [Tooltip("Muro de la izquierda")]
        [SerializeField]
        SpriteRenderer _leftWallRenderer;
        [Tooltip("Muro de la derecha")]
        [SerializeField]
        SpriteRenderer _rightWallRenderer;
        [Tooltip("Animador encargado de las animaciones del circulo")]
        [SerializeField]
        Animator animator;
        [Tooltip("Grid visual que envuelve el tile")]
        [SerializeField]
        SpriteRenderer[] grid;

        TileType _myType = TileType.voidTile;   //El tipo actual de este tile
        Color _tileColor;                       //Color de la tuberia/circulo
        Vector2Int _direction;                     //Dir en la que  se esta mostrando este pipe

        bool[] walls = { false, false, false, false }; //Arriba,abajo,izquierda,derecha

        /// <summary>
        /// Inicializa el tile dado un tipo y un color
        /// </summary>
        /// <param name="type">Tipo de tile a asignar</param>
        /// <param name="c">Color a asignar</param>
        public void initTile(TileType type, Color c)
        {
            setTileColor(c);
            setTileType(type);
        }

        #region Getters y setters
        /// +--------------------------------------------------------------------------------------+
        /// |                                 GETTERS Y SETTERS                                    |
        /// +--------------------------------------------------------------------------------------+
        public TileType getTileType()
        {
            return _myType;
        }

        /// <summary>
        /// Metodo que cambia el tipo de un tile y dependiendo de este activa o desactiva los elementos del mismo
        /// </summary>
        /// <param name="newType">Nuevo tipo del tile</param>
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

        /// <summary>
        /// Cambia el color y actualiza visualmente el resto de elementos del tile
        /// </summary>
        /// <param name="newColor">nuevo color a asignar</param>
        public void setTileColor(Color newColor)
        {
            _tileColor = newColor;
            _horizontalPipeRenderer.material.color = _tileColor;
            _verticalPipeRenderer.material.color = _tileColor;
            _circleRenderer.material.color = _tileColor;
            _backgroundRenderer.material.color = _tileColor;
        }

        /// <summary>
        /// Metodo que comienza a reproducir la animacion del circulo
        /// </summary>
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

        public Vector2Int getDirection()
        {
            return _direction;
        }

        /// <summary>
        /// Metodo que establece la direccion del tile a una dada y hace update visual
        /// </summary>
        /// <param name="x">nueva direccion en el eje x</param>
        /// <param name="y">nueva direccion en el eje y</param>
        public void setDirection(int x, int y)
        {
            _direction.x = x;   _direction.y = y;
            _horizontalPipeRenderer.enabled = _direction.x == 1;
            _verticalPipeRenderer.enabled = _direction.y == 1;
        }

        /// <summary>
        /// Metodo que establece un muro en una direccion dada
        /// </summary>
        /// <param name="index">Indice de la direccion de 0 a 3 (Up,Down,Left,Right)</param>
        /// <param name="value">Si quiere que este activo o desactivado</param>
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

        /// <summary>
        /// Cambia la apariencia del tile dependiendo de los parametros
        /// </summary>
        /// <param name="horPipe">Si se quiere que se active la pipe horizontal</param>
        /// <param name="verPipe">Si se quiere que se active la pipe vertical</param>
        /// <param name="circlerend">Si se quiere que se active el circulo</param>
        private void changeTileAppearance(bool horPipe, bool verPipe, bool circlerend) 
        {
            _horizontalPipeRenderer.enabled = horPipe;
            _verticalPipeRenderer.enabled = verPipe;
            _circleRenderer.enabled = circlerend;
        }

    }
}


