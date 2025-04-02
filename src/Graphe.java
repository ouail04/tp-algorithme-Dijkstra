import java.util.ArrayList;
import java.util.List;

public class Graphe {
    record arret(int distination,int cout){}
    int nbSommets;
    List<List<arret>> graphe;
    int [] listPriorite ;
    int tailleListPriorite;
    int [] d;
    int [] pred;



    public Graphe(){
        nbSommets=4;
        tailleListPriorite=nbSommets-1;
        listPriorite = new int[tailleListPriorite];
        d = new int[nbSommets];
        pred = new int[nbSommets];
        graphe=new ArrayList<>();
        for(int i=0;i<nbSommets;i++){
            graphe.add(new ArrayList<>());
            d[i]=-1;
            pred[i]=-1;
            listPriorite[i]=i;
        }
        d[0]=0;
        graphe.get(0).add(new arret(1,3));
        graphe.get(0).add(new arret(2,9));
        graphe.get(1).add(new arret(2,6));
        graphe.get(1).add(new arret(3,2));
        graphe.get(2).add(new arret(3,8));
    }

    // renvoyer le sommet le plus priorité
    public int getSommetPlusPriorite(){
        int min = Integer.MAX_VALUE;
        for(int i=0;i<tailleListPriorite;i++){
            int coutSommet = d[listPriorite[i]];
            if ( coutSommet < min && coutSommet != -1 ){
                min=d[listPriorite[i]];
            }
        }
        return min;
    }

    public int getIndice(int sommet){
        for (int i = 0 ; i <= tailleListPriorite ; i++){
            if(listPriorite[i]==sommet){
                return i;
            }
        }
        return -1;
    }


    public void dijkstra(){
        while (tailleListPriorite >= 0){
            int sommetPlusPriorite = getSommetPlusPriorite();
            graphe.get(sommetPlusPriorite).forEach(arret -> {
                if(d[arret.distination] == -1) d[arret.distination]++ ;
                d[arret.distination] += arret.cout;
                pred[arret.distination]= sommetPlusPriorite;
            });
            int indice = getIndice(sommetPlusPriorite) ;
            if(indice == tailleListPriorite){
                tailleListPriorite-- ;
            } else{
                listPriorite[indice] = listPriorite[tailleListPriorite] ;
                tailleListPriorite--;
            }
        }
    }

}
