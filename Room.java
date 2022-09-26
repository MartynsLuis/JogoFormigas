package Pricipal;
import java.util.Set;
import java.util.HashMap;
//import java.util.Iterator;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private Armadilha armadilha;
    private BauDeTesouro bau;
    private HashMap<String, Item> itensSala;
    private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        armadilha = null;
        bau = null;
        itensSala = new HashMap<String, Item>();
        exits = new HashMap<>();
    }

    public Room(String description, Armadilha armadilha) 
    {
        this.description = description;
        this.armadilha = armadilha;
        bau = null;
        itensSala = new HashMap<String, Item>();
        exits = new HashMap<>();
    }

    public Room(String description, BauDeTesouro bau) 
    {
        this.description = description;
        armadilha = null;
        this.bau = bau;
        itensSala = new HashMap<String, Item>();
        exits = new HashMap<>();
    }

    public Room(String description, Armadilha armadilha, BauDeTesouro bau) 
    {
        this.description = description;
        this.armadilha = armadilha;
        this.bau = bau;
        itensSala = new HashMap<String, Item>();
        exits = new HashMap<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {   
        if(bau == null){
            if(itensTexto() == ""){
                return "Você esta " + description + ".\nParece que não há itens que chamem a sua atenção\n" + getExitString();
            }
            return "Você esta " + description + ".\nDentro da sala há: " + itensTexto() + "\n" + getExitString();
        }
        if(itensTexto() == ""){
            return "Você esta " + description + ".\nParece que não há itens que chamem a sua atenção\nNa sala Voce vê " + bau.getDescricao() + "\n" + getExitString();
        }
        return "Você esta " + description + ".\nDentro da sala há: " + itensTexto() + "\nNa sala voce vê " + bau.getDescricao() + "\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Saídas:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    
    }

    public Armadilha getArmadilha(){
        return armadilha;
    }

    public void removerArmadilha(){
        armadilha = null;
    }

    public BauDeTesouro getBau(){
        return bau;
    }

    public Item getItem(String nomeItem){
        return itensSala.get(nomeItem);
    }

    public void setItem(Item item){
        itensSala.put(item.getNome(),item);
    }

    private String itensTexto(){
        String texto = "";
        for(String item : itensSala.keySet()){
            texto += item + " ";
        }
        return texto;
    }

    public void removerItem(String nomeItem){
        itensSala.remove(nomeItem);
    }

    public boolean temItem(){
        return itensSala.size() != 0;
    }
    
}

