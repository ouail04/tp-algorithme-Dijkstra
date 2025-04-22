import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Graphe graphe = new Graphe("graphe.txt", "");
        graphe.FLOYD_WARSHALL();
        graphe.afficherMatrice();


        Graphe graphe2 = new Graphe("graphe.txt");
        graphe2.dijkstra();
        System.out.println(Arrays.toString(graphe2.getD()));
    }
}