using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class BoardManager : MonoBehaviour
    {
        [SerializeField]
        GameObject tilePrefab;      //prefab del tile

        Color drawingColor;         //color que se esta dibujando 
        Vector2 lastPosProcessed;   //ultima prosion que se recogio

        Tile[,] _board;             //tablero que guarda los tiles
        List<Pipe> _currentPipes;   //tuberias del juego

        bool drawing;       //si el jugador esta dibujando actualmente en el tablero

        #region Metodos de creacion
        /// <summary>
        /// Crea el tablero, inicializando sus variables dado el mapa y los colores del tema
        /// </summary>
        /// <param name="map"> Mapa que se va a mostrar en el juego</param>
        /// <param name="skin"> Colores de las tuberias para el nivel</param>
        public void prepareBoard(Map map, Color[] skin)
        {
            drawing = false;

            _currentPipes = new List<Pipe>();

            lastPosProcessed = new Vector2(0, 0);
            createBoard(skin, map);
        }

        /// <summary>
        /// Instancia objetos de tipo tile dado el mapa
        /// </summary>
        /// <param name="skin"></param>
        /// <param name="map"></param>
        void createBoard(Color[] skin, Map map)
        {
            int sizeX = map.getSizeX();
            int sizeY = map.getSizeY();

            //Se crea el array de Tiles
            _board = new Tile[sizeX, sizeY];

            //Instanciamos todas las casillas y las inicializamos como vacias
            for (int i = 0; i < sizeX; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    _board[i, j] = Instantiate(tilePrefab, new Vector3(i, -j, 0), Quaternion.identity, this.transform).GetComponent<Tile>();
                    _board[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Numero de tuberias del mapa
            int numPipes = map.getNumPipes();

            //Colocar los extremos de las tuberias en los tiles del mapa
            for (int i = 0; i < numPipes; i++)
            {
                //Obtenemos los valores de las tuberias
                List<Vector2> pipeSol = map.getPipeSolution(i);

                //Primer elemento de la tuberia
                _board[(int)pipeSol[0].x, (int)pipeSol[0].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Ultimo elemento de la tuberia
                _board[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Nuevo registro de tuberia
                _currentPipes.Add(new Pipe(_board[(int)pipeSol[0].x, (int)pipeSol[0].y], _board[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y]));
            }

            //Obtenemos la informacion de las paredes del mapa
            List<Tuple<Vector2, Vector2>> wallsInfo = map.getWallsInfo();

            if (wallsInfo != null)
                for (int i = 0; i < wallsInfo.Count; i++)
                {
                    //Primer y segundo tile entre las que se encuentra la pared
                    Vector2 firstTile = wallsInfo[i].Item1;
                    Vector2 secondTile = wallsInfo[i].Item2;

                    //Colocar pared entre dos tiles
                    putWall(firstTile, secondTile);
                }
        }

        /// <summary>
        /// Coloca una pared entre la primera casilla y la segunda
        /// </summary>
        /// <param name="firstTile"> Primera casilla </param>
        /// <param name="secondTile"> Segunda casilla </param>
        private void putWall(Vector2 firstTile, Vector2 secondTile)
        {
            //Si los tiles se encuentran en la misma fila
            if (firstTile.x == secondTile.x)
            {
                //Si el primer tile esta en la izquierda
                if (firstTile.x < secondTile.x)
                {
                    _board[(int)firstTile.x, (int)firstTile.y].setWall(3, true);
                    _board[(int)secondTile.x, (int)secondTile.y].setWall(2, true);
                }
                //Si el primer tile esta en la derecha
                else
                {
                    _board[(int)firstTile.x, (int)firstTile.y].setWall(2, true);
                    _board[(int)secondTile.x, (int)secondTile.y].setWall(3, true);
                }
            }
            //Si los tiles se encuentran en la misma columna
            else
            {
                //Si el primer tile esta arriba
                if (firstTile.x < secondTile.y)
                {
                    _board[(int)firstTile.x, (int)firstTile.y].setWall(1, true);
                    _board[(int)secondTile.x, (int)secondTile.y].setWall(0, true);
                }
                //Si el primer tile esta abajo
                else
                {
                    _board[(int)firstTile.x, (int)firstTile.y].setWall(0, true);
                    _board[(int)secondTile.x, (int)secondTile.y].setWall(1, true);
                }
            }
        }
        #endregion

        #region Metodos de procesar input

        //+-----------------------------------------------------------------------------------------------------------------------+
        //|                                          METODOS PROCESAR INPUT                                                       |
        //+-----------------------------------------------------------------------------------------------------------------------+

        /// <summary>
        /// Procesa el input del usuario dependiendo de la casilla que ha sido pulsada y el tipo de 
        /// evento que recibe
        /// </summary>
        /// <param name="touch"> Evento realizado</param>
        public void processTouch(Touch touch, Vector2 pos)
        {
            //Obtener el tile correspondiente a la pulsacion
            Tile touchedTile = _board[(int)pos.x, (int)pos.y];
            
            //Acciones a hacer segun el tipo de tile pulsado
            checkTileType(touchedTile, pos);

            //Acciones a hacer segun el tipo de evento producido
            checkEventType(touch, touchedTile);
        }

        /// <summary>
        /// Comprueba el tipo de tile que se ha pulsado y realiza la accion correspondiente
        /// modificando el tablero
        /// </summary>
        /// <param name="touched">tile que ha sido tocado</param>
        /// <param name="pos">posicion del tile en el board</param>
        void checkTileType(Tile touched, Vector2 pos)
        {
            //No nos interesa procesar un touch que esta a mas de 1 casilla de la ultima procesada
            if (drawing && (lastPosProcessed - pos).magnitude > 1) return;

            switch (touched.getTileType())
            {
                case Tile.TileType.pipeHead:
                    checkHeadTile(touched, pos);
                    break;
                case Tile.TileType.connectedTile:
                    checkConnectionTile(touched, pos);
                    break;
                case Tile.TileType.circleTile:
                    checkCircleTile(touched, pos);
                    break;
                case Tile.TileType.voidTile:
                    checkVoidTile(touched, pos);
                    break;
            }
        }

        /// <summary>
        /// Comprueba el tipo de evento que se ha registrado y realiza la accion correspondiente.
        /// </summary>
        /// <param name="e"></param>
        /// <param name="touchedTile"></param>
        void checkEventType(Touch e, Tile touchedTile)
        {
            switch (e.phase)
            {
                case TouchPhase.Began:
                    if (touchedTile.getTileType() != Tile.TileType.voidTile)
                    {
                        //Animacion TileTocado
                    }
                    break;
                case TouchPhase.Moved:
                    //Nada
                    break;
                case TouchPhase.Ended:
                    //Terminamos de pintar
                    drawing = false;
                    //Guardamos el estado del board
                    foreach (Pipe p in _currentPipes)
                        p.saveFlow();
                    break;
            }

        }
        #endregion

        #region Metodos privados auxiliares 
        //+-----------------------------------------------------------------------------------------------------------------------+
        //|                                              METODOS AUXILIARES                                                       |
        //+-----------------------------------------------------------------------------------------------------------------------+


        /// <summary>
        /// Modifica la direccion de las tuberias de la posicion actual y de la anterior si se da el caso
        /// </summary>
        /// <param name="currentPos"></param>
        private void processDirection(Vector2 currentPos)
        {
            Vector2 dir = currentPos - lastPosProcessed;
            //Derecha
            if (dir.x == 1)
            {
                _board[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(1, 0));
            }
            //Izquierda
            else if (dir.x == -1)
            {
                _board[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 0));
                _board[(int)lastPosProcessed.x, (int)lastPosProcessed.y].setHorizontalConnection(true);
            }
            //Arriba
            else if (dir.y == -1)
            {
                _board[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 0));
                _board[(int)lastPosProcessed.x, (int)lastPosProcessed.y].setVerticalConnection(true);
            }
            //Abajo
            else if (dir.y == 1)
                _board[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 1));
        }
        /// <summary>
        /// Devuelve la pipe asignada al color. En caso de no encontrarla devuelve null
        /// </summary>
        /// <param name="col"></param>
        /// <returns></returns>
        private Pipe pipeWithColor(Color col)
        {
            foreach (Pipe p in _currentPipes)
                if (p.getColor() == col) return p;

            return null;
        }

        /// <summary>
        /// Restablece los cortes de las pipes en caso de que haya habido alguno
        /// </summary>
        private void restoreState()
        {
            foreach (Pipe p in _currentPipes)
            {
                if (p.getColor() != drawingColor)
                {
                    p.restoreFlow();
                }
            }
        }

        /// <summary>
        /// Si esta dibujando aniade el a la pipe correspondiente al color de dibujado actualizando el tile
        /// </summary>
        /// <param name="t"> Tile a aniadir</param>
        /// <param name="pos"> posicion en el board del tile</param>
        private void checkVoidTile(Tile t, Vector2 pos)
        {
            if (!drawing) return;

            Pipe p = pipeWithColor(drawingColor);
            updateTile(t);
            processDirection(pos);
            p.addTileToPipe(t);

            //Ya que hemos avanzado hacia una nueva casilla, hay que indicar a la anterior que ahora es un conected tile
            if(_board[(int)lastPosProcessed.x, (int)lastPosProcessed.y].getTileType() != Tile.TileType.circleTile)
                _board[(int)lastPosProcessed.x, (int)lastPosProcessed.y].setTileType(Tile.TileType.connectedTile);

            //Registramos la posicion actual como la ultima procesada
            lastPosProcessed = pos;
        }

        /// <summary>
        /// Si no esta dibujando corta el pipe. Si se esta dibujando, si es del mismo color que el que se esta dibujando, se corta la pipe del dibujado
        /// y si no, se corta la pipe que ha sido cortada
        /// </summary>
        /// <param name="t"></param>
        /// <param name="pos"></param>
        private void checkConnectionTile(Tile t, Vector2 pos)
        {
            Pipe p;
            if (!drawing)   //si no estoy dibujando
            {
                drawing = true;
                drawingColor = t.getColor();
                p = pipeWithColor(drawingColor);
                //Cortamos la pipe del color
                p.cut(t);
            }
            else    //Si estoy dibujando
            {
                p = pipeWithColor(drawingColor);

                if (drawingColor == t.getColor())   //Si corto conmigo mismo
                {
                    p.addTileToPipe(t); //Lo añado a la pipe y la pipe se encarga de autocortame
                    restoreState();     //Reset del estado de las pipes
                }
                else     //Si corto con otra tuberia
                {
                    updateTile(t);
                    processDirection(pos);
                    //Me aniado a mi mismo a mi tuberia
                    p.addTileToPipe(t);

                    //Corto la tuberia con la que he chocado
                    p = pipeWithColor(t.getColor());
                    p.cut(t);
                }
            }
        }
        /// <summary>
        /// Si estamos dibujando y el tile no es del mismo color de dibujado corta la tuberia del tile.
        /// </summary>
        /// <param name="t"></param>
        private void checkHeadTile(Tile t, Vector2 pos)
        {
            Pipe p;
            if (drawing)
            {
                if (drawingColor != t.getColor())    //si no es del color que estoy dibujando
                {
                    p = pipeWithColor(t.getColor());
                    p.cut(t);                           //cortamos la pipe

                    p = pipeWithColor(drawingColor);    //aniadimos el tile a la pipe de dibujado
                    p.addTileToPipe(t);

                    processDirection(pos);

                    //Registramos la posicion actual como la ultima procesada
                    lastPosProcessed = pos;
                }
            }
            else
            {
                drawing = true;
                drawingColor = t.getColor();
            }

        }
        /// <summary>
        /// Si no estoy dibujando se limpia la tuberia del color del tile que se ha pulsado. En caso de estar
        /// dibujando se comprueba si se ha cortado con la tuberia de dibujado. En caso de ser asi, se restauran las 
        /// tuberias y se elimina la tuberia dibujada.
        /// </summary>
        /// <param name="t"></param>
        private void checkCircleTile(Tile t, Vector2 pos)
        {
            Pipe p;
            if (drawing)
            {
                p = pipeWithColor(drawingColor);
                if (drawingColor == t.getColor())   //Si corto conmigo mismo
                {
                    //Lo aniado a la pipe de su color y si se ha cortado a si misma tenemos que resetear el estado del tablero
                    if (p.addTileToPipe(t))
                        restoreState();
                    else processDirection(pos);

                    //Registramos la posicion actual como la ultima procesada
                    lastPosProcessed = pos;
                }
            }
            else
            {
                drawing = true;
                drawingColor = t.getColor();
                p = pipeWithColor(t.getColor());
                p.clearPipe();
                p.addTileToPipe(t);

                //Registramos la posicion actual como la ultima procesada
                lastPosProcessed = pos;
            }
        }

        /// <summary>
        /// actualiza los atributos del tile en funcion del color que el jugador esta dibujando
        /// </summary>
        /// <param name="t"></param>
        /// <param name="pos"></param>
        private void updateTile(Tile t)
        {
            t.setTileColor(drawingColor);
            t.setTileType(Tile.TileType.pipeHead);
        }
    }
    #endregion
}
