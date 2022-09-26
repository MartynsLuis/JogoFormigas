package Pricipal;
import java.util.HashMap;

public class Jogador {
    private boolean vida;
    private HashMap<String,Item> inventario;
    private Room salaAtual;
    private Parser parser;

    public Jogador(){
        criarSalas();
        vida = true;
        inventario = new HashMap<String,Item>();
        parser = new Parser();
        Item teste = new Item("Uma espada enferrujada, já não tem mais corte", "espada");
        inventario.put(teste.getNome(),teste);
    }

    private void criarSalas()
    {
        Room outside, theater, pub, lab, office;
        Armadilha trap1;
        trap1 = new Armadilha("Você houve um click e então aparcem buracos das paredes, você vê algumas pontas de flechas, oque você faz:",
         "deitar");
        trap1.setOpcoes("correr","Você tenta corre para longe das flechas, infelizmente você não é rapido o bastante, e você morre.");
        trap1.setOpcoes("deitar","Você se jogou no chão e após alguns longos segudos a armadilha cessa, você sobreviveu.");
        trap1.setOpcoes("proteger","Você tenta se proteger com uma palete que havia ali perto, poré a palete já estava velha e se quebrou na primeira flechada, e você morre.");

        Item shampoo;
        Item acucar;
        shampoo = new Item("Lava bem o cabelo","shampoo");
        acucar = new Item("serve de comida para as formigas", "acucar");

        BauDeTesouro bau1;
        bau1 = new BauDeTesouro("Um bau simples com uma tranca simples","chave_simples");
        Item chaveGenerica;
        chaveGenerica = new Item("Uma chave simples, parece que abre uma fechadura simples","chave_simples");

        // create the rooms
        outside = new Room("Na entrada do formigueiro abandonado", bau1);
        theater = new Room("Na sala de lazer");
        pub = new Room("Na cozinha");
        lab = new Room("No dormitorio");
        office = new Room("Na sala de refeições", trap1);
        
        theater.setItem(shampoo);
        theater.setItem(acucar);

        // initialise room exits
        outside.setExit("leste", theater);
        outside.setExit("sul", lab);
        outside.setExit("oeste", pub);

        theater.setExit("oeste", outside);

        pub.setExit("leste", outside);
        pub.setItem(chaveGenerica);

        lab.setExit("norte", outside);
        lab.setExit("oeste", office);

        office.setExit("leste", lab);

        salaAtual = outside;  // start game outside
    }

    // altera a vida do player
    public void alterarVida(boolean vidaAtual){
        vida = vidaAtual;
    }

    // devolve a vida do player
    public boolean getVida(){
        return vida;
    }

    public HashMap<String,Item> getInventario(){
        return inventario;
    }

    public void removerItemInventario(String nomeItem){
        inventario.remove(nomeItem);
    }

    public void adicionarItemInventario(Item item){
        inventario.put(item.getNome(),item);
    }

    public String textoInventario(){
        String texto = "";
        for(String itemAtual: inventario.keySet()){
            texto += itemAtual + " ";
        }
        return texto;
    }

    //Retorna a sala atual do jogador
    public Room getSala(){
        return salaAtual;
    }
    // Pega um comando do jogador e retorna 
    public Command pegarComando(){
        Command comando = parser.getCommand();
        return comando;
    }

    public String stringComandos(){
        String comandos = parser.showCommands();
        return comandos;
    }

    public void receberSala(Room sala){
        salaAtual = sala;
    }

    public Room pegarSaida(String direcao){
        Room proximaSala = salaAtual.getExit(direcao);
        return proximaSala;
    }

    public Armadilha temArmadilha(){
        Armadilha trap = salaAtual.getArmadilha();
        return trap;
    }

    public void eliminarArmadilha(){
        salaAtual.removerArmadilha();
    }

    public Item getItem(String nomeItem){
        return inventario.get(nomeItem);
    }

}
