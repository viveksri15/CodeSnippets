package in.viveksrivastava.problems.graph.p1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ubona on 15-04-2014.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.solve();
    }

    private class City {
        private int wealth, number;
        private Set<City> adjacentCities = new HashSet<City>();
        private Set<City> citiesBetweenCapitalAndThem = new HashSet<City>();

        @Override
        public String toString() {
            return number + " " + wealth + " " + adjacentCities.size() + " " + citiesBetweenCapitalAndThem.size();
        }
    }

    private Map<Integer, City> cityMap = new HashMap<Integer, City>();
    private int numberOfCities = 0;

    public void solve() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        int i = 0, questions = 0, k = 0;
        StringBuffer stringBuffer = new StringBuffer();
        while ((line = bufferedReader.readLine()) != null) {
            if (i == 0) {
                numberOfCities = Integer.parseInt(line);
            } else if (i <= numberOfCities) {
                int w = Integer.parseInt(line);
                City city = new City();
                city.number = i;
                city.wealth = w;
                cityMap.put(i, city);
            } else if (i > numberOfCities && i < 2 * numberOfCities) {
                String[] split = line.split(" ");
                int a = Integer.parseInt(split[0]);
                int b = Integer.parseInt(split[1]);
                City c1 = cityMap.get(a);
                City c2 = cityMap.get(b);
                c1.adjacentCities.add(c2);
                c2.adjacentCities.add(c1);
            } else if (i == 2 * numberOfCities) {
                questions = Integer.parseInt(line);
                for (Integer cityNum : cityMap.keySet()) {
                    Set<Integer> visitedCities = new HashSet<Integer>();
                    searchCapital(cityNum, cityNum, cityMap.get(cityNum).citiesBetweenCapitalAndThem, visitedCities);
                }
            } else {

                String[] split = line.split(" ");
                if (split[0].equals("1")) {
                    Set<Integer> visitedCities = new HashSet<Integer>();
                    bomb(cityMap, Integer.parseInt(split[1]), Integer.parseInt(split[2]), 0, visitedCities);
                } else {
                    int ret = find(cityMap, Integer.parseInt(split[1]));
                    stringBuffer.append(ret).append("\n");
                }

                k++;
                if (k == questions)
                    break;
            }
            i++;
        }
        System.out.println(stringBuffer.toString().replaceAll("\n$", ""));
    }

    private int find(Map<Integer, City> cityMap, int cityToFind) {
        int ret = 0;
        for (int c : cityMap.keySet()) {
            if (cityMap.get(c).citiesBetweenCapitalAndThem.contains(cityMap.get(cityToFind))) {
                if (cityMap.get(c).wealth == 0)
                    ret++;
            }
        }
        return ret;
    }

    private void bomb(Map<Integer, City> cityMap, int city, int bombPower, int distance, Set<Integer> visitedCities) {
        if (visitedCities.contains(city))
            return;
        int bombPower1 = (int) Math.floor((double) bombPower / (Math.pow(2, distance)));
        int cityWealth = cityMap.get(city).wealth;
        cityWealth -= bombPower1;
        if (cityWealth <= 0)
            cityWealth = 0;
        cityMap.get(city).wealth = cityWealth;
        visitedCities.add(city);

        for (City c : cityMap.get(city).adjacentCities) {
            bomb(cityMap, c.number, bombPower, distance + 1, visitedCities);
        }
    }

    private boolean searchCapital(Integer startCity, Integer cityNum, Set<City> citiesBetweenCapitalAndThem, Set<Integer> visitedCities) {
        for (City c : cityMap.get(cityNum).adjacentCities) {
            if (visitedCities.contains(c.number))
                return false;
            visitedCities.add(c.number);
            if (c.number == 1) {
                citiesBetweenCapitalAndThem.add(c);
                return true;
            }
            if (searchCapital(startCity, c.number, citiesBetweenCapitalAndThem, visitedCities)) {
                citiesBetweenCapitalAndThem.add(c);
                return true;
            }
        }
        return false;
    }
}
