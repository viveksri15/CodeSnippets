package algos.dp.mandragora;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by vivek on 16/08/16.
 */
public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int _count;
        _count = Integer.parseInt(in.nextLine());

        MangragoraGame mangragoraGame = new ExperienceOptimisedMangragoraGame();
        for (int i = 0; i < _count; i++) {
            int n = Integer.parseInt(in.nextLine());
            for (int j = 0; j < n; j++) {
                int h = in.nextInt();
                mangragoraGame.addMandragora(new Mandragora(h));
            }
        }
        Health finalHealth = mangragoraGame.defeat(new Health(1, 0), 0);
        System.out.println(finalHealth.getP());
        in.nextLine();
    }
}

class Health {
    private int s, p;

    public Health(int s, int p) {
        this.s = s;
        this.p = p;
    }

    public Health(Health h) {
        this.s = h.getS();
        this.p = h.getP();
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Health health = (Health) o;

        return s == health.s && p == health.p;

    }

    @Override
    public int hashCode() {
        int result = s;
        result = 31 * result + p;
        return result;
    }

    @Override
    public String toString() {
        return "Health{" +
                "s=" + s +
                ", p=" + p +
                '}';
    }
}

class Mandragora {
    private boolean isAlive = true;
    private final int health;

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public Mandragora(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
}

interface MangragoraGame {
    void addMandragora(Mandragora mandragora);

    Health eat(Health h, Mandragora m);

    Health battle(Health h, Mandragora m);

    Health defeat(Health h, int i);
}

class ExperienceOptimisedMangragoraGame implements MangragoraGame {

    private List<Mandragora> mandragoras = new ArrayList<>();


    public void addMandragora(Mandragora mandragora) {
        mandragoras.add(mandragora);
    }

    @Override
    public Health eat(Health h, Mandragora m) {
        return new Health(h.getS() + 1, h.getP());
    }

    @Override
    public Health battle(Health h, Mandragora m) {
        return new Health(h.getS(), h.getP() + m.getHealth() * h.getS());
    }

    @Override
    public Health defeat(Health h, int i) {

        if (i >= mandragoras.size())
            return new Health(h);

        Mandragora mandragora = mandragoras.get(i);

        //eat, battle, leave

        if (!mandragora.isAlive())
            return new Health(h);

        //Leave
        Health defeat1 = defeat(h, i + 1);

        //Eat
        mandragora.setAlive(false);
        Health ateHealth = eat(h, mandragora);
        Health defeat2 = defeat(ateHealth, i + 1);

        //Battle
        mandragora.setAlive(false);
        Health battleHealth = battle(h, mandragora);
        Health defeat3 = defeat(battleHealth, i + 1);

        mandragora.setAlive(true);

        System.out.println("i=" + i + ", defeat1 = " + defeat1 + ", defeat2=" + defeat2 + ", defeat3=" + defeat3);

        if (defeat3.getP() > defeat2.getP() && defeat3.getP() > defeat1.getP())
            return defeat3;
        else if (defeat3.getP() > defeat2.getP())
            return defeat1;
        else if (defeat2.getP() > defeat1.getP())
            return defeat2;
        else return defeat1;
    }
}