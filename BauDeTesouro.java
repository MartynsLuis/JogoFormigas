package Pricipal;
import java.util.HashMap;

public class BauDeTesouro {
    private String descricao;
    private String nomeCaixa;
    private String chave;
    private Item tesouro;

    public BauDeTesouro(String descricao, String chave){
        this.descricao = descricao;
        this.chave = chave;
        this.tesouro = new Item("Um pouco de açucar", "açucar");
    }

    public String getDescricao(){
        return descricao;
    }

    public String getNomeCaixa(){
        return nomeCaixa;
    }

    public Item getTesouro(){
        return tesouro;
    }

    public String getChave(){
        return chave;
    }

    public Item destrancarBau(HashMap<String,Item> inventarioPlayer){
        for(String item : inventarioPlayer.keySet()){
            if( item == chave){
                return tesouro;
            }
        }
        return null;

    }

    // deixa o bau destrancado depois de aberto
    public void bauDestrancado(){
        chave = null; 
    }
}
