package algos.booking.com.q1.q2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        BufferedReader br = null;
        br = new BufferedReader(new InputStreamReader(System.in));
        List<Hotel> hotels = new ArrayList<>();
        Map<Integer, Hotel> hotelMap = new HashMap<>();
        try {

            String keywords = br.readLine().toLowerCase();
            Set<String> words = new HashSet<>(Arrays.asList(keywords.split("\\s")));
            int hotel = Integer.parseInt(br.readLine());
            for (int i = 0; i < hotel; i++) {
                int id = Integer.parseInt(br.readLine());
                String review = br.readLine().toLowerCase();
                String[] key = review.split("\\s");
                int count = 0;
                Hotel h = hotelMap.get(id);
                if (h == null) {
                    h = new Hotel(id, 0);
                    hotelMap.put(id, h);
                    hotels.add(h);
                }
                for (String s : key) {
                    if (words.contains(s))
                        count++;
                }
                h.increaseCount(count);
            }
        } catch (IOException e) {

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }

        Collections.sort(hotels);
        StringBuilder s = new StringBuilder();
        for (Hotel hotel : hotels)
            s.append(hotel.id).append(" ");
        System.out.println(s.toString().trim());
    }
}

class Hotel implements Comparable<Hotel> {

    public final int id;
    private int keywords;

    Hotel(int id, int keywords) {
        this.id = id;
        this.keywords = keywords;
    }

    @Override
    public int compareTo(Hotel o) {
        if (o.keywords == keywords)
            return o.id - id;
        return o.keywords - keywords;
    }

    public void increaseCount(int count) {
        this.keywords += count;
    }
}