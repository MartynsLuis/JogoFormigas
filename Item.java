package Pricipal;
public class Item {
    private String descricao;
    private String nome;

    public Item(String descricao, String nome){
        this.descricao = descricao;
        this.nome = nome;
    }

    public String getDescricao(){
        return descricao;
    }

    public String getNome(){
        return nome;
    }
}
