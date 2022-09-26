package Pricipal;

import javax.rmi.PortableRemoteObject;

/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Jogador jogador;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        jogador = new Jogador();
    }
    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome(jogador.getSala());

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while ((! finished) && jogador.getVida()) {

            Command command = jogador.pegarComando();
            finished = processCommand(command);
        }

        if(finished){
            System.out.println("Obrigado por jogar. Até mais.");
        }
        else{
            System.out.println("Game Over, tente novamente");
        }
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome(Room currentRoom)
    {
        System.out.println();
        System.out.println("Bem vindo ao mundo das Formigas");
        System.out.println("O mundo das formigas é uma aventura perigosa, todos os perigos são mortais");
        System.out.println("então tome cuidado");
        System.out.println("Digite '" + CommandWord.HELP + "' Se voce precisar de ajuda.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Não sei oque significa...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command, jogador);
                armadilha(jogador);
                break;
            
            case ACAO:
                System.out.println("Não é possivel fazer isso agora.");
                break;
            
            case ABRIR:
                abrirBau(jogador,command);
                break;

            case PEGAR:
                pegarAlgo(command, jogador);
                break;

            case OLHAR:
                olharAlgo(command, jogador);
                break;

            case INVENTARIO:
                inventario(command, jogador);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // Processa os comandos quando em uma armadilha
    private boolean processCommandArmadilha(Command command) 
    {
        boolean gastouArmadilhaQuerDarquit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("Não sei oque significa...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                System.out.println("Não é possivel fazer isso agora.");
                break;

            case ACAO:
                gastouArmadilhaQuerDarquit = fazerEscolha(jogador, command);
                break;
            
            case ABRIR:
                System.out.println("Não é possivel fazer isso agora.");
                break;

            case PEGAR:
                System.out.println("Não é possivel fazer isso agora.");
                break;

            case OLHAR:
                System.out.println("Não é possivel fazer isso agora.");
                break;
            
            case INVENTARIO:
                System.out.println("Não é possive fazer isso agora");
                break;

            case QUIT:
                gastouArmadilhaQuerDarquit = quitEmArmadilha(command, jogador);
                break;
        }
        return gastouArmadilhaQuerDarquit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Você esta perdido. Você esta sosinho. Você vagueia");
        System.out.println("Arredores do formigueiro abandonado.");
        System.out.println();
        System.out.println("Suas palavras de comando são:");
        System.out.println(jogador.stringComandos());
        System.out.println();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command, Jogador playerN) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Ir aonde?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = playerN.pegarSaida(direction);

        if (nextRoom == null) {
            System.out.println("Não há porta!");
        }
        else {
            playerN.receberSala(nextRoom);
            System.out.println(playerN.getSala().getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Sair oque?");
            return false;
    
        }
        else {
            return true;  // signal that we want to quit
        }
    }


    // metodos de quando está em uma armadilha:

    // verifica se há uma armadilha na sala e ativa ela
    private void armadilha(Jogador playerN){
        Armadilha trap = playerN.temArmadilha();
        if(trap != null){
            
            boolean armadilhaGasta = false;
            System.out.println(trap.getDescricao());
            System.out.println("suas ações são: " + trap.getOpcoes());
            while(!armadilhaGasta){
                Command comando = playerN.pegarComando();
                armadilhaGasta = processCommandArmadilha(comando);
            }
            playerN.eliminarArmadilha();
        }

    }

    // verifica se o player fez uma escolha válida
    private boolean fazerEscolha(Jogador playerN, Command command){
        Armadilha trap = playerN.temArmadilha();
        
        // verifica se o player fez uma escolha
        if(!command.hasSecondWord()){
            System.out.println("Qual ação executar?");
            return false;
        }
        
        String escolhaPlayer = command.getSecondWord();
        String descricaoEscolha = trap.descricaoEscolha(escolhaPlayer);

        //verifica se a escolha é uma das disponiveis
        if(descricaoEscolha == null){
            System.out.println("Está opção não existe.");
            return false;
        }
        
        // printa a descricao da escolha na tela e altera a vida do player para false se estiver errado
        System.out.println(descricaoEscolha);
        playerN.alterarVida(trap.escolhaCorreta(escolhaPlayer));
        return true;
        

    }

    // metodo quit quando está em uma armadilha
    private boolean quitEmArmadilha(Command command, Jogador playerN) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Sair oque?");
            return false;
    
        }
        else {
            playerN.alterarVida(false);// mata o player se sair durante uma armadilha
            return true;  // signal that we want to quit
        }
    }

    // método para abrir um bau
    private void abrirBau(Jogador playerN, Command command){
        Room salaAtual = playerN.getSala();
        BauDeTesouro bauSala = salaAtual.getBau();
        Item tesouro = null;
        if(bauSala.getChave() == null){
            System.out.println("Você já abriu este baú.");
        }
            tesouro = bauSala.destrancarBau(playerN.getInventario());
            if (tesouro == null){
                System.out.println("O baú está trancando, você precisa da chave para abri-lo.");
            }
            else{
                playerN.removerItemInventario(bauSala.getChave());
                bauSala.bauDestrancado();
                playerN.adicionarItemInventario(tesouro);
                System.out.println("Dentro do baú você encontrou: " + tesouro.getDescricao());
            }
    }

    // método para ver o inventario
    private void inventario(Command command,Jogador playerN){
        if(command.hasSecondWord()){
            System.out.println("Que inventario?");
            return;
        }
        System.out.println("Você olha dentro da sua mochila.");
        String itensInventario = playerN.textoInventario();
        if(itensInventario == ""){
            System.out.println("Não há nada na mochila");
            return;
        }
        System.out.println("dentro há: " + itensInventario);

    }

    // método para olhar alguma coisa na sala
    private void olharAlgo(Command command, Jogador playerN){
        String olharOque = command.getSecondWord();
        if(olharOque == null){
            System.out.println("Você olha em volta");
            System.out.println(playerN.getSala().getLongDescription());
            return;
        }
        Room salaAtual = playerN.getSala();
        Item itemSala = salaAtual.getItem(olharOque);
        Item itemInventario = playerN.getItem(olharOque);
        if((itemSala == null) && (itemInventario == null)){
            System.out.println("Não entendi.");
            return;
        }
        if(itemSala != null){
            System.out.println("Você olha " + itemSala.getNome() + " que está na sala");
            System.out.println(itemSala.getDescricao());
            return;
        }

        System.out.println("Voce olha " + itemInventario.getNome() + "que está na sua mochila");
        System.out.println(itemInventario.getDescricao());
    }

    private void pegarAlgo(Command command, Jogador playerN){
        if(!command.hasSecondWord()){
            System.out.println("Pegar oque?");
            return;
        }
        Room salaAtual = playerN.getSala();
        if(!salaAtual.temItem()){
            System.out.println("Está sala não possui itens a serem pegos.");
            return;
        }

        String itemEscolhido = command.getSecondWord();
        Item item = salaAtual.getItem(itemEscolhido);
        if(item == null){
            System.out.println("Não existe este item nesta sala.");
            return;
        }
        System.out.println("Você pegou " + item.getNome() + " que estava na sala.");
        playerN.adicionarItemInventario(item);
        salaAtual.removerItem(item.getNome());
    }

}
