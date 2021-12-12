using System;
using System.IO;
using System.Collections.Generic;
using UnityEngine;

namespace Flow
{
    public static class ProgressSerialization
    {
        private static string fileName = "/playerProgress.txt";

        /// <summary>
        /// Guarda los datos del progreso de la partida
        /// </summary>
        /// <param name="playerProgress"></param>
        public static void saveProgress(PlayerProgress playerProgress)
        {
            string filePath = Application.persistentDataPath + fileName;

            //Vaciamos cualquier contenido del campo hash para que no interfiera mas adelante
            playerProgress.hash = " ";
            //Convertimos el objeto en Json
            string json = JsonUtility.ToJson(playerProgress);

            //Creamos el hash asociado al Json de los datos del progreso
            string hashvalue = Hash128.Compute(json).ToString();

            //Lo asignamos al struct para guardarlo
            playerProgress.hash = hashvalue;

            //Convertimos el nuevo objeto con el hash a json y guardamos
            json = JsonUtility.ToJson(playerProgress);
            File.WriteAllText(filePath, json);
        }
        
        /// <summary>
        /// Devuelve un struct con los valores del ultimo progreso guardado
        /// </summary>
        /// <returns></returns>
        public static PlayerProgress loadProgress()
        {
            string filePath = Application.persistentDataPath + fileName;

            //Si no existe el archivo devolvemos valores por defecto
            if (!File.Exists(filePath)) return getDefaultPlayerProgress();

            //Leemos el archivo json
            string json = File.ReadAllText(filePath);
            PlayerProgress progressRead = JsonUtility.FromJson<PlayerProgress>(json);

            //Nos guardamos el hash del archivo leido
            string readHash = progressRead.hash;
            progressRead.hash = " ";

            //Comprobamos la integridad del json leido
            json = JsonUtility.ToJson(progressRead);
            string currentHash = Hash128.Compute(json).ToString();

            //Si no resulta ser el mismo hash devolvemos valores por defecto
            if (readHash != currentHash) return getDefaultPlayerProgress();

            return progressRead;
        }

        /// <summary>
        /// Devuelve un struct relleno con valores por defecto
        /// </summary>
        /// <returns></returns>
        private static PlayerProgress getDefaultPlayerProgress()
        {
            PlayerProgress defaultValue = new PlayerProgress();
            defaultValue.availableHints = 0;
            defaultValue.packsProgress = new List<PackProgress>();

            return defaultValue;
        }
    }   

    [Serializable]
    public struct PlayerProgress
    {
        public int availableHints;
        public List<PackProgress> packsProgress;

        public string hash;
    }

    [Serializable]
    public class PackProgress
    {
        public int completedLevels;
        public List<int> levelTopScore;

        public PackProgress(int completedLvls, List<int> levelTopScr)
        {
            completedLevels = completedLvls;
            levelTopScore = levelTopScr;
        }
    }
}
