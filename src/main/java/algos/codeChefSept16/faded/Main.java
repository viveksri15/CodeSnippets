package algos.codeChefSept16.faded;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < t; i++) {
            boolean cont = false;
            String p = scanner.nextLine();
            char[] chars = p.toCharArray();
            int j = 0;
            for (j = 0; j < chars.length / 2; j++) {
                if (chars[j] == '.')
                    chars[j] = chars[chars.length - 1 - j];
                if (chars[j] == '.') {
                    chars[j] = 'a';
                    chars[chars.length - 1 - j] = 'a';
                }
                if (chars[chars.length - 1 - j] == '.')
                    chars[chars.length - 1 - j] = chars[j];
                
                if (chars[j] != chars[chars.length - 1 - j]) {
                    System.out.println(-1);
                    cont = true;
                    break;
                }
            }
            if (cont)
                continue;
            if (j == chars.length / 2 && j < chars.length && chars[j] == '.')
                chars[j] = 'a';
            System.out.println(new String(chars));
        }
    }
}