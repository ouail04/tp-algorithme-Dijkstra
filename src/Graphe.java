import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graphe {
    record arret(int distination,int cout){}
    int nbSommets;
    List<List<arret>> graphe;
    int [] listPriorite ;
    int tailleListPriorite;
    int [] d;
    int [] pred;



    public Graphe(String nomFichier){
        try{
            File file = new File("src/" + nomFichier);
            Scanner scanner = new Scanner(file);
            nbSommets = scanner.nextInt();
            tailleListPriorite = nbSommets;
            listPriorite = new int[tailleListPriorite];
            d = new int[nbSommets];
            pred = new int[nbSommets];
            graphe=new ArrayList<>();

            //initialisation des list des arret de graphe
            for(int i=0; i<nbSommets; i++){
                graphe.add(new ArrayList<>());
                d[i]=-1;
                pred[i]=-1;
                listPriorite[i]=i;
            }
            tailleListPriorite--  ;
            d[0]=0;
            while (scanner.hasNextInt()) {
                int sommet = scanner.nextInt();
                int dest = scanner.nextInt();
                int cout = scanner.nextInt();
                graphe.get(sommet).add(new arret(dest, cout));
            }


            scanner.close();

        } catch(FileNotFoundException e){
            System.out.println("erreur : " + e.getMessage());
        }
    }

    // renvoyer le sommet le plus priorité
    public int getSommetPlusPriorite(){
        int min = Integer.MAX_VALUE;
        int sommet = -1 ;
        for(int i=0; i<tailleListPriorite; i++){
            int coutSommet = d[listPriorite[i]];
            if ( coutSommet < min && coutSommet != -1 ){
                min=d[listPriorite[i]];
                sommet = listPriorite[i] ;
            }
        }
        return sommet;
    }

    public int getIndice(int sommet){
        for (int i = 0 ; i <= tailleListPriorite ; i++){
            if(listPriorite[i]==sommet){
                return i;
            }
        }
        return -1;
    }

    public int[] getPred() {
        return pred;
    }
    public  int[] getD(){ return d; }

    public void dijkstra(){
        while (tailleListPriorite > 0){

            int sommetPlusPriorite = getSommetPlusPriorite();
            if (sommetPlusPriorite == -1) break;
            graphe.get(sommetPlusPriorite).forEach(arret -> {
                if (d[arret.distination] == -1 || d[arret.distination] > d[sommetPlusPriorite] + arret.cout) {
                    d[arret.distination] = d[sommetPlusPriorite] + arret.cout;
                    pred[arret.distination] = sommetPlusPriorite;
                }
            });


            int indice = getIndice(sommetPlusPriorite) ;
            if(indice == tailleListPriorite-1){
                tailleListPriorite-- ;
            } else{
                listPriorite[indice] = listPriorite[tailleListPriorite-1] ;
                tailleListPriorite--;
            }
        }
    }



    //-------------------------------------------------------------------------------------------------
    // plus court chemin FLOYD-WARSHALL
    private int[][] distance ; // distance minimale entre chaque paire
    public Graphe(String nomFichier , String type) throws FileNotFoundException { // le deuxième argument seulement pour distinguer les deux constructeur
        int INF = Integer.MAX_VALUE ;
        File file = new File("src/" + nomFichier);
        Scanner scanner = new Scanner(file);
        nbSommets = scanner.nextInt();
        this.distance = new int[nbSommets][nbSommets];

        // Initialiser la matrice
        for (int i = 0; i < nbSommets; i++) {
            for (int j = 0; j < nbSommets; j++) {
                if (i == j) distance[i][j] = 0;
                else distance[i][j] = INF;
            }
        }

        // Charger les arcs depuis le fichier
        while (scanner.hasNextInt()) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            int w = scanner.nextInt();
            distance[u][v] = w;
        }
    }

    public void FLOYD_WARSHALL(){
        int INF = Integer.MAX_VALUE ;
        for (int k = 0; k < nbSommets; k++) {
            for (int i = 0; i < nbSommets; i++) {
                for (int j = 0; j < nbSommets; j++) {
                    if (distance[i][k] != INF && distance[k][j] != INF) {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
        }
    }


    // Méthode affiche la matrice de l'algo FLOYD-WARSHALL
    public void afficherMatrice() {
        int n = distance.length;
        final int INF = Integer.MAX_VALUE;

        // Affichage en forme de tableau avec en-têtes
        System.out.print("     ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%5d", j);
        }
        System.out.println();
        System.out.println("    " + "-".repeat(5 * n));

        for (int i = 0; i < n; i++) {
            System.out.printf("%3d |", i);
            for (int j = 0; j < n; j++) {
                if (distance[i][j] == INF) {
                    System.out.print("  INF");
                } else {
                    System.out.printf("%5d", distance[i][j]);
                }
            }
            System.out.println();
        }
    }



}
