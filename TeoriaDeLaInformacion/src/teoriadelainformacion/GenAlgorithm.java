/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teoriadelainformacion;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author Edgar Valdes
 */
public class GenAlgorithm {

    private HashMap<String, String> results = new HashMap<String, String>();
    private List<Pattern> patterns = new ArrayList<Pattern>();
    private List<Pattern> workList = new ArrayList<Pattern>();
    private PriorityQueue<Pattern> queue = new PriorityQueue<Pattern>();
    private List<String> fileContent;

    GenAlgorithm(HashMap<String, String> map, List<String> fileContent) {
        results.putAll(map);
        this.fileContent = fileContent;
        evaluate(fileContent);
        fillQueue();
        workList.addAll(patterns);
        start();
    }

    HashMap<String, String> getMap(HashMap<String, String> map) {
        return results;
    }

    private void fillMap() {
        results.clear();
        for (Pattern pattern : patterns) {
            results.put(pattern.getKey(), pattern.getPattern());
        }
    }

    private void evaluate(List<String> fileContent) {
        for (Map.Entry<String, String> entry : results.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            Double evaluation = 0.0;

            Integer count = 0;
            for (String line : fileContent) {
                if (!line.startsWith(">")) {
                    count += getOcurrences(value, line);
                }
            }
            Pattern pattern = new Pattern(key, value, count * (value.length() - 1));
            patterns.add(pattern);
        }
    }

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

    private void fillQueue() {
        for (Pattern pattern : patterns) {
            queue.offer(pattern);
        }
    }

    private void start() {
        pickElements();
        crossover();
    }

    private void pickElements() {
        Integer total = 0;
        for (Pattern pattern : patterns) {
            total += pattern.getValue();
        }
        workList.clear();
        for (int i = 0; i < 10; i++) {
            Integer picker = TeoriaDeLaInformacion.nextRandomInt(total);
            int sum = 0;
            for (Pattern pattern : patterns) {
                sum += pattern.getValue();
                if (sum >= picker) {
                    workList.add(pattern);
                }
            }
        }
    }

    private void crossover() {
        for (int i = 0; i < 10; i++) {

        }
    }

}
