using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{

    /// <summary>
    /// Manager que administra todo lo relacionado con el tablero y el mostrado de este en pantalla
    /// </summary>
    public class BoardManager : MonoBehaviour
    {
        [Tooltip("Prefab de los tiles")]
        [SerializeField]
        Tile tilePrefab;      //prefab del tile
        [Tooltip("Levelmanager")]
        [SerializeField]
        LevelManager levelManager;  //level manager

        Tile[,] _board;             //tablero que guarda los tiles
        List<Pipe> _levelPipes;     //tuberias del juego

        Color drawingColor;            //color que se esta dibujando 
        Vector2Int lastPosProcessed;   //ultima prosion que se recogio
        Vector2 boardSize;             //tamanio del tablero

        bool drawing;       //si el jugador esta dibujando actualmente en el tablero
        int moves;          //movimientos que ha realizado el jugador

#if UNITY_EDITOR    
        private void Update()
        {
            if (Input.GetKeyDown(KeyCode.S))
            {
                for (int i = 0; i < _board.GetLength(0); ++i)
                {
                    string line = "";
                    for (int j = 0; j < _board.GetLength(1); ++j)
                    {
                        line += _board[i, j].getTileType() + "  ";
                    }
                    Debug.Log(line);
                }
            }
            if (Input.GetKeyDown(KeyCode.D))
            {
                foreach (Pipe p in _levelPipes)
                {
                    Debug.Log(p.getCurrentPipe().Count + " Color" + p.getColor() + "   FINISH: " + p.isCompleted());
                }
            }
        }
#endif

        #region Metodos de creacion
        /// <summary>
        /// Crea el tablero, inicializando sus variables dado el mapa y los colores del tema
        /// </summary>
        /// <param name="map"> Mapa que se va a mostrar en el juego</param>
        /// <param name="skin"> Colores de las tuberias para el nivel</param>
        public void prepareBoard(Map map, Color[] skin)
        {
            drawing = false;
            moves = 0;

            _levelPipes = new List<Pipe>();

            lastPosProcessed = new Vector2Int(0, 0);
            createBoard(skin, map);
        }

        /// <summary>
        /// Busca la primer pipe que no este finalizada para aplicar la pista sobre ella, modificando
        /// el estado del tablero
        /// </summary>
        public void applyHint()
        {
            Pipe aux = null;
            //Buscamos la primera pipe que no esté finalizada para aplicar la pista
            foreach (Pipe p in _levelPipes)
            {
                if (!p.isCompleted())
                {
                    aux = p;
                    break;
                }
            }
            //Aplizamos la pista
            aux.useHint();
            _board[aux.getCurrentPipe()[0].x, aux.getCurrentPipe()[0].y].setStar(true);
            _board[aux.getCurrentPipe()[aux.getCurrentPipe().Count - 1].x, aux.getCurrentPipe()[aux.getCurrentPipe().Count - 1].y].setStar(true);

            //Vemos si al aplicar la pista hemos cortado otras pipes
            foreach (Vector2Int v in aux.getCurrentPipe())
            {
                _board[v.x, v.y].setBackGround(true);
                if (_board[v.x, v.y].getTileType() == Tile.TileType.connectedTile &&  //Si es de tipo conected
                   _board[v.x, v.y].getColor() != aux.getColor())                   //Y no es del color del pipe de la hint
                {
                    Pipe cutPipe = pipeWithColor(_board[v.x, v.y].getColor());

                    if (cutPipe.isCompleted() && cutPipe.hasBeenHinted())
                    {
                        int lastPos = cutPipe.getCurrentPipe().Count - 1;
                        _board[cutPipe.getCurrentPipe()[0].x, cutPipe.getCurrentPipe()[0].y].setStar(false);
                        _board[cutPipe.getCurrentPipe()[lastPos].x, cutPipe.getCurrentPipe()[lastPos].y].setStar(false);
                    }
                    List<Vector2Int> cutted = cutPipe.cut(v);
                    if (cutted != null)
                        setVoids(cutted);
                }
            }
            //Guardamos el estado del board
            foreach (Pipe p in _levelPipes)
                p.saveFlow();
            updateVisualBoard();
            processPlay();

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
            boardSize = new Vector2(sizeX, sizeY);

            //Se crea el array de Tiles
            _board = new Tile[sizeX, sizeY];

            //Instanciamos todas las casillas y las inicializamos como vacias
            for (int i = 0; i < sizeX; i++)
            {
                for (int j = 0; j < sizeY; j++)
                {
                    _board[i, j] = Instantiate(tilePrefab, new Vector3(i, -j, 0), Quaternion.identity, this.transform);
                    _board[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Colocar casillas vacias si existen
            List<Vector2Int> emptyInfo = map.getEmptySquares();
            if (emptyInfo != null)
            {
                for (int i = 0; i < emptyInfo.Count; ++i)
                {
                    _board[emptyInfo[i].x, emptyInfo[i].y].setTileType(Tile.TileType.emptyTile);
                }

                //Poner paredes alrededor de los vacios
                applyWallsToEmpty(emptyInfo);
            }


            //Colocamos las paredes si existen en el nivel
            List<Tuple<Vector2Int, Vector2Int>> wallsInfo = map.getWallsInfo();
            if (wallsInfo != null)
                for (int i = 0; i < wallsInfo.Count; i++)
                {
                    //Primer y segundo tile entre las que se encuentra la pared
                    Vector2Int firstTile = wallsInfo[i].Item1;
                    Vector2Int secondTile = wallsInfo[i].Item2;

                    //Colocar pared entre dos tiles
                    putWall(firstTile, secondTile);
                }

            //Numero de tuberias del mapa
            int numPipes = map.getNumPipes();

            //Colocar los extremos de las tuberias en los tiles del mapa
            for (int i = 0; i < numPipes; i++)
            {
                //Obtenemos los valores de las tuberias
                List<Vector2Int> pipeSol = map.getPipeSolution(i);

                //Primer elemento de la tuberia
                _board[pipeSol[0].x, pipeSol[0].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Ultimo elemento de la tuberia
                _board[pipeSol[pipeSol.Count - 1].x, pipeSol[pipeSol.Count - 1].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Nuevo registro de tuberia
                _levelPipes.Add(new Pipe(new Vector2Int(pipeSol[0].x, pipeSol[0].y),
                                         new Vector2Int(pipeSol[pipeSol.Count - 1].x, pipeSol[pipeSol.Count - 1].y),
                                         skin[i],
                                         pipeSol));
            }
            //Poner bordes al mapa
            if (map.hasBorders())
                applyWallsToBorders();
        }

        /// <summary>
        /// Coloca una pared entre la primera casilla y la segunda
        /// </summary>
        /// <param name="firstTile"> Primera casilla </param>
        /// <param name="secondTile"> Segunda casilla </param>
        private void putWall(Vector2Int firstTile, Vector2Int secondTile)
        {
            //Si los tiles se encuentran en la misma fila
            if (firstTile.y == secondTile.y)
            {
                //Si el primer tile esta en la izquierda
                if (firstTile.x < secondTile.x)
                {
                    _board[firstTile.x, firstTile.y].setWall(3, true);
                    _board[secondTile.x, secondTile.y].setWall(2, true);
                }
                //Si el primer tile esta en la derecha
                else
                {
                    _board[firstTile.x, firstTile.y].setWall(2, true);
                    _board[secondTile.x, secondTile.y].setWall(3, true);
                }
            }
            //Si los tiles se encuentran en la misma columna
            else
            {
                //Si el primer tile esta arriba
                if (firstTile.y < secondTile.y)
                {
                    _board[firstTile.x, firstTile.y].setWall(1, true);
                    _board[secondTile.x, secondTile.y].setWall(0, true);
                }
                //Si el primer tile esta abajo
                else
                {
                    _board[firstTile.x, firstTile.y].setWall(0, true);
                    _board[secondTile.x, secondTile.y].setWall(1, true);
                }
            }
        }
        #endregion

        #region Metodos de procesar input

        //+-----------------------------------------------------------------------------------------------------------------------+
        //|                                          METODOS PROCESAR INPUT                                                       |
        //+-----------------------------------------------------------------------------------------------------------------------+

        /// <summary>
        /// Metodo que reinicia el nivel, eliminando todas las pipes y quitando backgrounds en caso de haberlos
        /// </summary>
        public void restartLevel()
        {
            foreach (Pipe p in _levelPipes)
            {
                p.clearPipe();
                p.saveFlow();
            }
            for (int i = 0; i < boardSize.x; ++i)
            {
                for (int j = 0; j < boardSize.y; ++j)
                {
                    if (_board[i, j].getTileType() != Tile.TileType.circleTile && _board[i, j].getTileType() != Tile.TileType.emptyTile)
                        _board[i, j].setTileType(Tile.TileType.voidTile);
                    else
                        _board[i, j].setDirection(0, 0);

                    _board[i, j].setBackGround(false);
                }
            }

            updateVisualBoard();
        }

        /// <summary>
        /// Procesa el input del usuario dependiendo de la casilla que ha sido pulsada y el tipo de 
        /// evento que recibe
        /// </summary>
        /// <param name="touch"> Evento realizado</param>
        public void processTouch(Touch touch, Vector2 pos)
        {
            //Obtener el tile correspondiente a la pulsacion
            if (pos.x < 0 || pos.x >= boardSize.x || pos.y < 0 || pos.y >= boardSize.y)
                return;
            Tile touchedTile = _board[(int)pos.x, (int)pos.y];

            //Acciones a hacer segun el tipo de tile pulsado
            checkTileType(touchedTile, new Vector2Int((int)pos.x, (int)pos.y));
            //Acciones a hacer segun el tipo de evento producido
            checkEventType(touch, touchedTile);

            updateVisualBoard();

            processPlay();
        }

        /// <summary>
        /// Comprueba el tipo de tile que se ha pulsado y realiza la accion correspondiente
        /// modificando el tablero
        /// </summary>
        /// <param name="touched">tile que ha sido tocado</param>
        /// <param name="pos">posicion del tile en el board</param>
        void checkTileType(Tile touched, Vector2Int pos)
        {
            //No nos interesa procesar un touch que esta a mas de 1 casilla de la ultima procesada
            if (drawing && (lastPosProcessed - pos).magnitude > 1) return;

            //Si estoy dibujando y no me he movido ya ha sido procesado este input
            if (drawing && lastPosProcessed.x == pos.x && lastPosProcessed.y == pos.y)
                return;

            //Si hay una pared entre el tile que quiero dibujar y el anterior no se procesa el input
            if (drawing && isWallBetween(pos, lastPosProcessed)) return;

            switch (touched.getTileType())
            {
                case Tile.TileType.connectedTile:
                    checkConnectionTile(touched, pos);
                    break;
                case Tile.TileType.circleTile:
                    checkCircleTile(touched, pos);
                    break;
                case Tile.TileType.voidTile:
                    checkVoidTile(touched, pos);
                    break;
                case Tile.TileType.emptyTile:
                    //Casilla no accesible
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
                        touchedTile.startAnimation();
                        Pipe p = pipeWithColor(drawingColor);
                        _board[p.getSecondTile().x, p.getSecondTile().y].startAnimation();
                    }
                    break;
                case TouchPhase.Moved:
                    //Nada
                    break;
                case TouchPhase.Ended:
                    //Terminamos de pintar
                    if (drawing)
                    {
                        foreach (Vector2Int v in pipeWithColor(drawingColor).getCurrentPipe())
                        {
                            Tile t = _board[v.x, v.y];
                            t.setBackGround(true);
                        }
                    }
                    drawing = false;
                    //Guardamos el estado del board
                    foreach (Pipe p in _levelPipes)
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
        /// Devuelve la pipe asignada al color. En caso de no encontrarla devuelve null
        /// </summary>
        /// <param name="col"></param>
        /// <returns></returns>
        private Pipe pipeWithColor(Color col)
        {
            foreach (Pipe p in _levelPipes)
                if (p.getColor() == col) return p;

            return null;
        }

        /// <summary>
        /// Metodo que muestra en pantalla el estado actual del tablero
        /// </summary>
        private void updateVisualBoard()
        {
            foreach (Pipe p in _levelPipes)
            {
                List<Vector2Int> positions = p.getCurrentPipe();
                for (int j = 0; j < positions.Count; j++)
                {
                    if (j == 0)
                        processDirection(positions[j], positions[j]);
                    else
                        processDirection(positions[j], positions[j - 1]);

                    if (_board[positions[j].x, positions[j].y].getTileType() != Tile.TileType.circleTile)
                        _board[positions[j].x, positions[j].y].setTileType(Tile.TileType.connectedTile);
                    else
                    {
                        if (p.hasBeenHinted() && p.isCompleted())
                        {
                            _board[positions[j].x, positions[j].y].setStar(true);
                        }
                    }
                    _board[positions[j].x, positions[j].y].setTileColor(p.getColor());
                }
            }
        }

        /// <summary>
        /// Restablece los cortes de las pipes en caso de que haya habido alguno
        /// </summary>
        private void restoreState()
        {
            foreach (Pipe p in _levelPipes) //recorremos las pipes
            {
                if (p.getColor() == drawingColor) continue;         //si es la pipe que pintamos continuamos
                for (int i = 0; i < p.getLastPipe().Count; ++i)     //para cada posicion en el pipe antiguo
                {
                    Tile tile = _board[p.getLastPipe()[i].x, p.getLastPipe()[i].y];

                    if (tile.getTileType() == Tile.TileType.voidTile)
                    {       //si es void ahora se ocupa
                        p.addTileToPipe(p.getLastPipe()[i]);
                        tile.setBackGround(true);
                    }

                    else if (p.getLastPipe().Count > 1 && i == p.getLastPipe().Count - 1 &&
                            tile.getTileType() == Tile.TileType.circleTile && //si es el ultimo circulo
                             !p.isCompleted())
                        p.addTileToPipe(p.getLastPipe()[i]);

                    else if (tile.getColor() != p.getColor())   //si llegas a otro color pasas
                        break;
                }
            }
        }

        /// <summary>
        /// Si esta dibujando aniade el a la pipe correspondiente al color de dibujado actualizando el tile
        /// </summary>
        /// <param name="t"> Tile a aniadir</param>
        /// <param name="pos"> posicion en el board del tile</param>
        private void checkVoidTile(Tile t, Vector2Int pos)
        {
            if (!drawing) return;

            Pipe p = pipeWithColor(drawingColor);
            if (!p.isCompleted())
            {
                t.setTileColor(drawingColor);
                p.addTileToPipe(pos);

                //Registramos la posicion actual como la ultima procesada
                lastPosProcessed = pos;
            }
        }

        /// <summary>
        /// Si no esta dibujando corta el pipe. Si se esta dibujando, si es del mismo color que el que se esta dibujando, se corta la pipe del dibujado
        /// y si no, se corta la pipe que ha sido cortada
        /// </summary>
        /// <param name="t"></param>
        /// <param name="pos"></param>
        private void checkConnectionTile(Tile t, Vector2Int pos)
        {
            Pipe p = pipeWithColor(drawingColor);
            if (!drawing)   //si no estoy dibujando
            {
                startDrawing(t);
                p = pipeWithColor(drawingColor);
                //Si esta completa y tenia pista quitamos la estrella
                if (p.isCompleted() && p.hasBeenHinted())
                {
                    int lastPos = p.getCurrentPipe().Count - 1;
                    _board[p.getCurrentPipe()[0].x, p.getCurrentPipe()[0].y].setStar(false);
                    _board[p.getCurrentPipe()[lastPos].x, p.getCurrentPipe()[lastPos].y].setStar(false);
                }
                List<Vector2Int> aux = p.cut(pos, 1);
                if (aux != null) setVoids(aux);
            }
            else    //Si estoy dibujando
            {
                if (drawingColor == t.getColor())   //Si corto conmigo mismo
                {
                    List<Vector2Int> voids = p.addTileToPipe(pos); //Lo añado a la pipe y la pipe se encarga de autocortame
                    if (voids != null)
                        setVoids(voids);
                    restoreState();     //Reset del estado de las pipes
                }
                else     //Si corto con otra tuberia
                {
                    //Si la tuberia que estoy dibujando esta completa no sigo expandiendo
                    if (pipeWithColor(drawingColor).isCompleted()) return;

                    //Corto la tuberia con la que he chocado
                    Pipe cutPipe = pipeWithColor(t.getColor());
                    if (cutPipe.isCompleted() && cutPipe.hasBeenHinted())
                    {
                        int lastPos = cutPipe.getCurrentPipe().Count - 1;
                        _board[cutPipe.getCurrentPipe()[0].x, cutPipe.getCurrentPipe()[0].y].setStar(false);
                        _board[cutPipe.getCurrentPipe()[lastPos].x, cutPipe.getCurrentPipe()[lastPos].y].setStar(false);
                    }
                    List<Vector2Int> aux = cutPipe.cut(pos);
                    if (aux != null) setVoids(aux);
                    p.addTileToPipe(pos);
                }
            }

            //Registramos la posicion actual como la ultima procesada
            lastPosProcessed = pos;
        }



        /// <summary>
        /// Si no estoy dibujando se limpia la tuberia del color del tile que se ha pulsado. En caso de estar
        /// dibujando se comprueba si se ha cortado con la tuberia de dibujado. En caso de ser asi, se restauran las 
        /// tuberias y se elimina la tuberia dibujada.
        /// </summary>
        /// <param name="t"></param>
        private void checkCircleTile(Tile t, Vector2Int pos)
        {
            Pipe p;
            if (drawing)
            {
                p = pipeWithColor(drawingColor);
                if (drawingColor == t.getColor() && !p.isCompleted())   //Si es de mi color
                {
                    //Lo aniado a la pipe de su color y si se ha cortado a si misma tenemos que resetear el estado del tablero
                    List<Vector2Int> cutted = p.addTileToPipe(pos);
                    if (cutted != null)
                    {
                        setVoids(cutted);
                        restoreState();
                    }

                    //Registramos la posicion actual como la ultima procesada
                    lastPosProcessed = pos;
                }
            }
            else
            {
                startDrawing(t);
                p = pipeWithColor(t.getColor());

                if (!p.isEmpty())
                {
                    setVoids(p.getCurrentPipe());

                    if (p.isCompleted() && p.hasBeenHinted())
                    {
                        int lastPos = p.getCurrentPipe().Count - 1;
                        _board[p.getCurrentPipe()[0].x, p.getCurrentPipe()[0].y].setStar(false);
                        _board[p.getCurrentPipe()[lastPos].x, p.getCurrentPipe()[lastPos].y].setStar(false);
                    }
                    p.clearPipe();
                }

                p.addTileToPipe(pos);

                //Registramos la posicion actual como la ultima procesada
                lastPosProcessed = pos;
            }
        }

        /// <summary>
        /// Dada una lista de posiciones en el tablero en caso de que no haya un circulo en cada posicion la pone a vacio
        /// </summary>
        /// <param name="pos">Lista de posiciones a poner los tiles como vacios</param>
        void setVoids(List<Vector2Int> pos)
        {
            foreach (Vector2Int p in pos)
            {
                if (_board[p.x, p.y].getTileType() != Tile.TileType.circleTile)
                    _board[p.x, p.y].setTileType(Tile.TileType.voidTile);
                else
                {
                    _board[p.x, p.y].setDirection(0, 0);
                    _board[p.x, p.y].setBackGround(false);
                }
            }
        }


        /// <summary>
        /// Modifica la direccion de las tuberias de la posicion actual y de la anterior si se da el caso
        /// </summary>
        /// <param name="currentPos"></param>
        private void processDirection(Vector2Int currentPos, Vector2Int lastPos)
        {
            Vector2 dir = currentPos - lastPos;
            //Derecha
            if (dir.x == 1)
            {
                _board[currentPos.x, currentPos.y].setDirection(1, 0);
            }
            //Izquierda
            else if (dir.x == -1)
            {
                _board[currentPos.x, currentPos.y].setDirection(0, 0);
                _board[lastPos.x, lastPos.y].setDirection(1, _board[lastPos.x, lastPos.y].getDirection().y);
            }
            //Arriba
            else if (dir.y == -1)
            {
                _board[currentPos.x, currentPos.y].setDirection(0, 0);
                _board[lastPos.x, lastPos.y].setDirection(_board[lastPos.x, lastPos.y].getDirection().x, 1);
            }
            //Abajo
            else if (dir.y == 1)
                _board[currentPos.x, currentPos.y].setDirection(0, 1);
            else
                _board[currentPos.x, currentPos.y].setDirection(0, 0);

        }

        /// <summary>
        /// Realiza calculos (num de tuberias finalizadas...)y envia la informacion necesaria al levelmanager
        /// </summary>
        private void processPlay()
        {
            float numFinish = 0, numTiles = 0;

            foreach (Pipe p in _levelPipes)
            {
                if (p.isCompleted())
                    numFinish++;
                numTiles += p.getCurrentPipe().Count;
            }
            int perc = (int)(100.0f * numTiles / (boardSize.x * boardSize.y));

            levelManager.processPlay((int)numFinish, _levelPipes.Count, perc, moves);

        }


        /// <summary>
        /// Metodo que comienza el dibujado logico a partir de un tile dado
        /// </summary>
        /// <param name="t">Tile a partir del que se empieza a dibujar</param>
        private void startDrawing(Tile t)
        {
            drawing = true;
            if (drawingColor != t.getColor())
                moves++;
            drawingColor = t.getColor();
        }


        /// <summary>
        /// Metodo que recibe un alista de posiciones, y dada esta lista calcula donde en dichas posiciones deberian haber muros
        /// </summary>
        /// <param name="emptyPos"> Lista de posiciones a tratar</param>
        private void applyWallsToEmpty(List<Vector2Int> emptyPos)
        {
            Vector2Int[] dirs = { new Vector2Int(0, 1), new Vector2Int(0, -1), new Vector2Int(1, 0), new Vector2Int(-1, 0) };
            foreach (Vector2Int pos in emptyPos)
            {
                foreach (Vector2Int dir in dirs)
                {
                    Vector2Int res = pos + dir;
                    if (res.x < 0 || res.x >= boardSize.x || res.y < 0 || res.y >= boardSize.y) continue;

                    if (_board[res.x, res.y].getTileType() != Tile.TileType.emptyTile)
                        putWall(pos, res);
                }

            }

        }



        /// <summary>
        /// Metodo que pone muros en los bordes del tablero, menos en aquellas casillas que sean vacias
        /// </summary>
        private void applyWallsToBorders()
        {
            for (int i = 0; i < boardSize.x; i++)
            {
                if (_board[i, 0].getTileType() != Tile.TileType.emptyTile)
                    _board[i, 0].setWall(0, true);
                if (_board[i, (int)boardSize.y - 1].getTileType() != Tile.TileType.emptyTile)
                    _board[i, (int)boardSize.y - 1].setWall(1, true);
            }
            for (int i = 0; i < boardSize.y; i++)
            {
                if (_board[0, i].getTileType() != Tile.TileType.emptyTile)
                    _board[0, i].setWall(2, true);
                if (_board[(int)boardSize.x - 1, i].getTileType() != Tile.TileType.emptyTile)
                    _board[(int)boardSize.x - 1, i].setWall(3, true);
            }

        }



        /// <summary>
        /// Comprueba si existe una pared entre los dos tiles dados
        /// </summary>
        /// <param name="dest"> Posicion del tile origen</param>
        /// <param name="source"> Posicion del tile destino</param>
        /// <returns> True si existe una pared, false en caso contrario</returns>
        private bool isWallBetween(Vector2Int dest, Vector2Int source)
        {
            Vector2Int dir = dest - source;

            //Arriba
            if (dir.y == -1)
                return _board[dest.x, dest.y].getWall(1);
            //Abajo
            else if (dir.y == 1)
                return _board[dest.x, dest.y].getWall(0);
            //Izquierda
            else if (dir.x == -1)
                return _board[dest.x, dest.y].getWall(3);
            //Derecha
            else
                return _board[dest.x, dest.y].getWall(2);
        }

        #endregion
    }


}
