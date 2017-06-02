/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import static teoriadelainformacion.TeoriaDeLaInformacion.alphabet;
import static teoriadelainformacion.TeoriaDeLaInformacion.nextRandomInt;

/**
 * @author Edgar Valdes
 */
public class GenAlgorithm {

    private HashMap<String, String> results = new HashMap<String, String>();
    private List<Pattern> patterns = new ArrayList<Pattern>();
    private List<Pattern> workList = new ArrayList<Pattern>();
    private PriorityQueue<Pattern> allQueue = new PriorityQueue<Pattern>();
    private PriorityQueue<Pattern> queue = new PriorityQueue<Pattern>();
    private List<String> fileContent;
    private Integer mutationChance = 80;
    private Integer iterations = 1000;

    GenAlgorithm(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    //Genera mapa con valores aleatorios
    private static HashMap<String, String> fillDefaultMap(Integer size) {
        HashMap<String, String> newMap = new HashMap< String, String>();
        for (int i = 0; i < 10; i++) {
            String word = "";
            for (int j = 0; j < size; j++) {
                //Solo se utilizan 22 caracteres en un FASTA valido
                word = word + Character.toString(alphabet[nextRandomInt(22)]);
            }
            newMap.put(String.valueOf(i), word);
        }
        return newMap;
    }

    HashMap<String, String> getMap(HashMap<String, String> map) {
        return results;
    }

    //Obtiene mejores 10 elementos de lista de prioridad y valida que no pase algo raro
    //Hecho de esta manera ya que antes no se validaba tan bien la entrada de los valores
    private void fillMap() {

        Integer count;
        count = 0;
        results.clear();
        List<Pattern> bestPatterns = new ArrayList<Pattern>();
        boolean inserted = false;
        Pattern pattern;
        while (bestPatterns.size() < 10) {
            pattern = allQueue.poll();
            if (!bestPatterns.contains(pattern)) {
                bestPatterns.add(pattern);
            }
        }

        for (Pattern x : bestPatterns) {
            System.out.println(x);
            count += x.getValue();
        }

        //Imprime la sumatoria de evaluaciones(caracteres ahorrados)
        System.out.println("For a total of: " + count);
        Integer n = 0;
        results.clear();
        for (Pattern x : bestPatterns) {
            allQueue.add(x);
            results.put(n.toString(), x.getPattern());
            n++;
        }
    }

    //Evalua valores del hashhmap
    private void evaluate() {
        for (Map.Entry<String, String> entry : results.entrySet()) {
            String value = entry.getValue();

            Integer count = 0;
            for (String line : fileContent) {
                //Ignoramos encabezados para esto
                if (!line.startsWith(">")) {
                    count += getOcurrences(value, line);
                }
            }
            Pattern pattern = new Pattern(value, count * (value.length() - 1));
            patterns.add(pattern);
        }
    }

    //Utilizado para el conteo de apariciones de patron en un string
    private Integer getOcurrences(String value, String line) {
        int lastIndex = 0;
        int count = 0;
        while (lastIndex != -1) {
            lastIndex = line.indexOf(value, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += value.length();
            }
        }
        return count;
    }

    //Se evaluan elementos para saber cual es el mejor
    private void evaluation() {
        for (Pattern obj : workList) {
            String pattern = obj.getPattern();

            Integer count = 0;
            for (String line : fileContent) {
                //Ignoramos encabezados para esto
                if (!line.startsWith(">")) {
                    count += getOcurrences(pattern, line);
                }
            }
            obj.setValue(count * (pattern.length() - 1));
        }
    }

    public void run(Integer iterations) {
        //Se corre una cantidad especificada de veces
        //(solo para desarrolladores checar variable iterations)
        for (int n = 0; n < iterations; n++) {
            // Pasos del algoritmo genetico
            pickElements();
            crossover();
            mutation();
            evaluation();
            //Limpieza y modificaciones a listas para siguiente iteracion
            queue.addAll(workList);
            patterns.clear();
            boolean inserted = false;
            for (int i = 0; i < 10; i++) {
                inserted = false;
                do {
                    Pattern pattern = queue.poll();
                    if (!patterns.contains(pattern)) {
                        patterns.add(pattern);
                        inserted = true;
                    }

                } while (!inserted);
            }
            queue.addAll(patterns);
            results.clear();
            workList.clear();
        }

    }

    private void pickElements() {
        Integer total = 0;
        for (Pattern pattern : patterns) {
            total += pattern.getValue();
        }

        //Se toma los 10 mejores patrones, se hace asi ya que los primeros suelen generar resultados que tienden a 0
        for (int i = 0; i < 10; i++) {
            Pattern pattern = queue.poll();
            Pattern newPattern = new Pattern(pattern.getPattern(), pattern.getValue());
            workList.add(newPattern);
        }
        queue.addAll(patterns);
    }

    //Cruza del algoritmo genetico
    private void crossover() {
        results.clear();
        //por cada patron se genera un numero aleatorio entre el tamaño maximo-1 y 1y se cruza en esta posicion
        for (int i = 0; i < 10; i++) {
            Pattern pattern1 = workList.get(i);
            i++;
            Pattern pattern2 = workList.get(i);
            int x = TeoriaDeLaInformacion.nextRandomInt(pattern1.getPattern().length() - 1) + 1;
            //string1 es un string temporal donde se guarda el patron que fue sobreescrito primero.
            String string1 = pattern1.getPattern();
            pattern1.setPattern(
                    pattern1.getPattern().substring(0, x) + pattern2.getPattern().substring(x, string1.length()));
            pattern2.setPattern(pattern2.getPattern().substring(0, x) + string1.substring(x, string1.length()));
        }
    }

    //Mutacion de algoritmo genetico
    private void mutation() {
        String string;
        //Se genera un numero aleatorio por cada caracter de cada patron y se decide si ocurre una mutacion
        for (Pattern pattern : workList) {
            string = pattern.getPattern();
            for (int i = 0; i < string.length(); i++) {
                if (TeoriaDeLaInformacion.nextRandomInt(100) <= mutationChance) {
                    string = string.replace(string.substring(i, i + 1), Character
                            .toString(TeoriaDeLaInformacion.alphabet[TeoriaDeLaInformacion.nextRandomInt(22)]));
                    pattern.setPattern(string);
                }
            }
        }
    }

    HashMap<String, String> makeMap(int i) {
        //Se genera un mapa del tamaño especificado de manera pseudo-aleatoria
        HashMap<String, String> map = fillDefaultMap(i);
        //Se limpian todas las listas y colas utilizadas para la construccion del nuevo mapa
        results.clear();
        queue.clear();
        workList.clear();
        patterns.clear();
        //Se evaluan los patrones generados aleatoriamente para insercion en cola de prioridad
        results.putAll(map);
        evaluate();
        queue.addAll(patterns);
        //Se corre algoritmo genetico
        run(iterations);
        //Se preparan resultados para regresar un hash map con los mejores patrones
        cleanQueue();
        fillMap();
        return results;
    }

    //Se eliminan todos los patrones repetidos identicos para dejar solo 1
    private void cleanQueue() {
        List<Pattern> all = new ArrayList<Pattern>();
        while (!queue.isEmpty()) {
            Pattern x = queue.poll();
            if (!all.contains(x)) {
                all.add(x);
            }
        }
        //Los volvemos a agregar a la cola de prioridad para eliminar los contenidos despues y ademas se agregan a la cola final
        for (Pattern pattern : all) {
            allQueue.add(pattern);
            Pattern temp = new Pattern(pattern.getPattern(), pattern.getValue());
            queue.add(temp);
        }
        removeContained();
    }

    private void removeContained() {
        //Dos listas con todos
        List<Pattern> allList = new ArrayList<Pattern>();
        List<Pattern> newList = new ArrayList<Pattern>();
        //Dos listas donde se guardaran los elementos a eliminar de golpe
        List<Pattern> removeAllList = new ArrayList<Pattern>();
        List<Pattern> removeNewList = new ArrayList<Pattern>();

        //Se obtienen de mejor a peor en listas
        while (!allQueue.isEmpty()) {
            allList.add(allQueue.poll());
        }
        while (!queue.isEmpty()) {
            newList.add(queue.poll());
        }

        //Comparamos todo con todo
        for (Pattern newPattern : newList) {
            for (Pattern oldPattern : allList) {
                //Si no es identico pero lo contiene
                if (!newPattern.equals(oldPattern) && oldPattern.containsPattern(newPattern)) {
                    //Checamos cual eliminar
                    if (oldPattern.getValue() < newPattern.getValue()) {
                        removeAllList.add(oldPattern);
                    } else {
                        //Si se elimina el pequeño termina el bucle interno ya que deja de tener caso
                        removeNewList.add(newPattern);
                        break;
                    }
                }
            }
        }

        //Se remueven los cpatrones contenidos en otros
        newList.removeAll(removeNewList);
        allList.removeAll(removeAllList);
        allList.removeAll(removeNewList);

        //Volvemos agregarlo a las coals de prioridad(se utilizan para generar el hashmap)
        for (Pattern newPattern : newList) {
            queue.add(newPattern);
        }
        for (Pattern oldPattern : allList) {
            allQueue.add(oldPattern);
        }
    }

}
