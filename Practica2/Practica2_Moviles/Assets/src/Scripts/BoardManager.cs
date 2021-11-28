using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;


namespace Flow
{
    public class BoardManager : MonoBehaviour
    {
        [SerializeField]
        GameObject tilePrefab;
        [SerializeField]
        Transform boardGO;

        bool drawing;
        Color drawingColor;
        Vector2 lastPosProcessed;
        Tile[,] _myTileMap;
        Map _myMap;
        List<Pipe> _currentPipes;
        Pipe _drawingPipe;

        public void prepareBoard(Map map, Color[] skin)
        {
            _myMap = map;
            drawing = false;
            _currentPipes = new List<Pipe>();
            _drawingPipe = new Pipe();
            lastPosProcessed = new Vector2(0, 0);
            createBoard(skin);
        }

        public void processTouch(Touch touch)
        {
            switch (touch.phase)
            {
                //Comienzo del dedo
                case TouchPhase.Began:
                    onTouch(touch);
                    break;
                //Movimiento del dedo
                case TouchPhase.Moved:
                    onTouchMoved(touch);
                    break;
                //Ultimo frame del dedo
                case TouchPhase.Canceled:
                    onTouchReleased(touch);
                    break;
                //Ultimo frame del dedo
                case TouchPhase.Ended:
                    onTouchReleased(touch);
                    break;
            }
        }

        void createBoard(Color[] skin)
        {
            int sizeX = _myMap.getSizeX();
            int sizeY = _myMap.getSizeY();

            //Se crea el array de Tiles
            _myTileMap = new Tile[sizeX, sizeY];

            //Instanciamos todas las casillas y las inicializamos como vacias
            for (int i = 0; i < sizeX; i++)
            {
                for (int j = 0; j < sizeX; j++)
                {
                    _myTileMap[i, j] = Instantiate(tilePrefab, new Vector3(i, j, 0), Quaternion.identity, boardGO).GetComponent<Tile>();
                    _myTileMap[i, j].setTileType(Tile.TileType.voidTile);
                }
            }

            //Numero de tuberias del mapa
            int numPipes = _myMap.getNumPipes();

            //Colocar los extremos de las tuberias en los tiles del mapa
            for (int i = 0; i < numPipes; i++)
            {
                //Obtenemos los valores de las tuberias
                List<Vector2> pipeSol = _myMap.getPipeSolution(i);

                //Primer elemento de la tuberia
                _myTileMap[(int)pipeSol[0].x, (int)pipeSol[0].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Ultimo elemento de la tuberia
                _myTileMap[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y].initTile(Tile.TileType.circleTile, skin[i]);

                //Nuevo registro de tuberia
                _currentPipes.Add(new Pipe(_myTileMap[(int)pipeSol[0].x, (int)pipeSol[0].y], _myTileMap[(int)pipeSol[pipeSol.Count - 1].x, (int)pipeSol[pipeSol.Count - 1].y]));
                Debug.Log(i);
            }

            //Obtenemos la informacion de las paredes del mapa
            List<Tuple<Vector2, Vector2>> wallsInfo = _myMap.getWallsInfo();

            if (wallsInfo != null)
                for (int i = 0; i < wallsInfo.Count; i++)
                {
                    //Primer y segundo tile entre las que se encuentra la pared
                    Vector2 firstTile = wallsInfo[i].Item1;
                    Vector2 secondTile = wallsInfo[i].Item2;

                    //Colocar pared entre dos tiles
                    colocaPared(firstTile, secondTile);
                }

        }

        /// <summary>
        /// Coloca una pared entre la primera casilla y la segunda
        /// </summary>
        /// <param name="firstTile"> Primera casilla </param>
        /// <param name="secondTile"> Segunda casilla </param>
        private void colocaPared(Vector2 firstTile, Vector2 secondTile)
        {
            //Si los tiles se encuentran en la misma fila
            if (firstTile.x == secondTile.x)
            {
                //Si el primer tile esta en la izquierda
                if (firstTile.x < secondTile.x)
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(3, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(2, true);
                }
                //Si el primer tile esta en la derecha
                else
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(2, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(3, true);
                }
            }
            //Si los tiles se encuentran en la misma columna
            else
            {
                //Si el primer tile esta arriba
                if (firstTile.x < secondTile.y)
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(1, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(0, true);
                }
                //Si el primer tile esta abajo
                else
                {
                    _myTileMap[(int)firstTile.x, (int)firstTile.y].setWall(0, true);
                    _myTileMap[(int)secondTile.x, (int)secondTile.y].setWall(1, true);
                }
            }
        }

        /// //////////////////////////////////////////////////7
        //TODO : TENEMOS TODO OCN LAS PIPES DEL JUEGO, HAY QUE PONERLO CON LA UNICA PIPE DE DRAWING. MODIFICAR ADD, Y METODOS RECOJAN O AÑADAN 
        // COSAS (MOVE...RELEASE)
        private void onTouch(Touch touch)
        {
            Tile touchedTile = _myTileMap[(int)touch.position.x, (int)touch.position.y];
            Color col = touchedTile.getColor();
            Pipe touchedPipe = pipeWithColor(col);
            switch (touchedTile.getTileType())
            {
                case Tile.TileType.circleTile:
                    //Animacion
                    touchedPipe.clearPipe();
                    _drawingPipe.setFirstAndSecond();
                    if (touchedPipe.addTileToPipe(touchedTile))
                    {

                    }
                    drawing = true;
                    drawingColor = col;
                    break;

                case Tile.TileType.connectedTile:
                    //Animacion Cabeza
                    touchedPipe.temporalCut(touchedTile);
                    drawing = true;
                    drawingColor = col;
                    break;

                case Tile.TileType.pipeHead:
                    drawing = true;
                    drawingColor = col;
                    //Animacion
                    break;
            }
            lastPosProcessed = new Vector2(touch.position.x, touch.position.y);
        }

        private void onTouchMoved(Touch touch)
        {
            //Si no estoy dibujando no me interesa hacer o cambiar nada
            if (!drawing) return;

            Tile touchedTile = _myTileMap[(int)touch.position.x, (int)touch.position.y];

            switch (touchedTile.getTileType())
            {
                case Tile.TileType.circleTile:
                    if(touchedTile.getColor() == drawingColor)
                    {
                        processDirection(touch.position);
                        pipeWithColor(drawingColor).addTileToPipe(touchedTile);
                        lastPosProcessed = touch.position;
                    }
                    break;
                case Tile.TileType.voidTile:
                    touchedTile.setTileType(Tile.TileType.pipeHead);
                    touchedTile.setTileColor(drawingColor);
                    processDirection(touch.position);
                    pipeWithColor(drawingColor).addTileToPipe(touchedTile);
                    break;
                case Tile.TileType.connectedTile:
                    if(touchedTile.getColor() == drawingColor)
                    {
                        processDirection(touch.position);
                        pipeWithColor(drawingColor).addTileToPipe(touchedTile);
                    }                   
                    else
                    {
                        touchedTile.setTileType(Tile.TileType.pipeHead);
                        touchedTile.setPipeColor(drawingColor);
                        processDirection(touch.position);
                        pipeWithColor(touchedTile.getColor()).temporalCut(touchedTile);
                    }
                    break;
            }
        }

        private void onTouchReleased(Touch touch)
        {
            if (!drawing) return;

            Tile touchedTile = _myTileMap[(int)touch.position.x, (int)touch.position.y];

            //Eliminamos todos los tiles que hayan sido cortados
            foreach(Pipe pipe in _currentPipes)
            {
                if (pipe.getColor() != drawingColor)
                    pipe.clearHiddens();
            }

            //Confirmamos todos los tiles que hemos dibujado
            pipeWithColor(drawingColor).confirmSelection();

            drawing = false;
        }

        private void processDirection(Vector2 currentPos)
        {
            Vector2 dir = currentPos - lastPosProcessed;
            //Derecha
            if (dir.x == 1)
                _myTileMap[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(1, 0));
            else if (dir.x == -1)
            {
                _myTileMap[(int)lastPosProcessed.x, (int)lastPosProcessed.y].setDirection(new Vector2(1, 0));
                _myTileMap[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 0));
            }
            else if (dir.y == 1)
                _myTileMap[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 1));
            else if(dir.y == -1)
            {
                _myTileMap[(int)lastPosProcessed.x, (int)lastPosProcessed.y].setDirection(new Vector2(0, 1));
                _myTileMap[(int)currentPos.x, (int)currentPos.y].setDirection(new Vector2(0, 0));
            }
        }

        private Pipe pipeWithColor(Color col)
        {
            foreach(Pipe p in _currentPipes)
                if (p.getColor() == col) return p;

            return null;
        }
    }

}
