package Pricipal;
import java.util.HashMap;

public class Armadilha {
    private String descricao;
    // A primeira String é a opção e a segunda a descrição do que ocorreu
    private HashMap<String,String> opcoes;
    private String opcaoCorreta;
    

    public Armadilha(String descricao, String opcaoCorreta){
        this.descricao = descricao;
        this.opcoes = new HashMap<>();
        this.opcaoCorreta = opcaoCorreta;
    }

    public void setOpcoes(String opcao, String descricaoOpcao){
        opcoes.put(opcao, descricaoOpcao);
    }

    public String getDescricao(){
        return descricao;
    }

    public String getOpcoes(){
        String opcoesTexto = "";
        for(String opcao : opcoes.keySet()){
            opcoesTexto += opcao + " ";
        }
        return opcoesTexto;
    }

    public String descricaoEscolha(String escolha){
        String opcaoEscolhida = opcoes.get(escolha);
        return opcaoEscolhida;
    }

    public boolean escolhaCorreta(String escolha){
        boolean acerto = false;
        String escolhaTexto = opcoes.get(escolha);
        for (String opcao : opcoes.keySet()){
            if(opcao == opcaoCorreta){
                acerto = escolhaTexto == (opcoes.get(opcao));
                break;
            }
            
        }
        return acerto;
    }
}
