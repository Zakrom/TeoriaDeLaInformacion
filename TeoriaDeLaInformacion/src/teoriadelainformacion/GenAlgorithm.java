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
    private Integer mutationChance = 10;
    private Integer iterations = 97;

    GenAlgorithm(HashMap<String, String> map, List<String> fileContent) {
        results.putAll(map);
        this.fileContent = fileContent;
        evaluate();
        queue.addAll(patterns);
        run(iterations);
    }

    HashMap<String, String> getMap(HashMap<String, String> map) {
        return results;
    }

    private void fillMap() {
        results.clear();
        Integer count = 0;
        for (Pattern pattern : patterns) {
            results.put(count.toString(), pattern.getPattern());
            count += 1;
        }
    }

    private void evaluate() {
        for (Map.Entry<String, String> entry : results.entrySet()) {
            String value = entry.getValue();

            Integer count = 0;
            for (String line : fileContent) {
                if (!line.startsWith(">")) {
                    count += getOcurrences(value, line);
                }
            }
            Pattern pattern = new Pattern(value, count * (value.length() - 1));
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

    private void evaluation() {
        for (Pattern obj : workList) {
            String pattern = obj.getPattern();

            Integer count = 0;
            for (String line : fileContent) {
                if (!line.startsWith(">")) {
                    count += getOcurrences(pattern, line);
                }
            }
            obj.setValue(count);
        }
    }

    public void run(Integer iterations) {
        for (int n = 0; n < iterations; n++) {

            pickElements();
            crossover();
            mutation();
            evaluation();

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

            Integer count = 0;
            Integer total = 0;
            for (Pattern pattern : patterns) {
                results.put(count.toString(), pattern.getPattern());
                total += pattern.getValue();
                count++;
            }
            workList.clear();
        }

    }

    private void pickElements() {
        Integer total = 0;
        for (Pattern pattern : patterns) {
            total += pattern.getValue();
        }

        for (int i = 0; i < 10; i++) {
            Integer picker = TeoriaDeLaInformacion.nextRandomInt(total);
            int sum = 0;
            for (Pattern pattern : patterns) {
                sum += pattern.getValue();
                if (sum >= picker) {
                    workList.add(pattern);
                    break;
                }
            }
        }
    }

    private void crossover() {
        results.clear();
        for (int i = 0; i < 10; i++) {

            Pattern pattern1 = workList.get(i);
            i++;
            Pattern pattern2 = workList.get(i);

            int x = TeoriaDeLaInformacion.nextRandomInt(pattern1.getPattern().length() - 1) + 1;
            String string1 = pattern1.getPattern();
            pattern1.setPattern(pattern1.getPattern().substring(0, x) + pattern2.getPattern().substring(x, string1.length()));
            pattern2.setPattern(pattern2.getPattern().substring(0, x) + string1.substring(x, string1.length()));

        }
    }

    private void mutation() {
        String string;
        for (Pattern pattern : workList) {
            string = pattern.getPattern();
            for (int i = 0; i < string.length(); i++) {
                if (TeoriaDeLaInformacion.nextRandomInt(100) <= mutationChance) {
                    string = string.replace(string.substring(i, i + 1),
                            Character.toString(TeoriaDeLaInformacion.alphabet[TeoriaDeLaInformacion.nextRandomInt(22)]));
                }
            }
        }
    }

}
