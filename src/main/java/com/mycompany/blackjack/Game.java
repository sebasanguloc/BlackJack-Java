/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.blackjack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author sebas
 */
public class Game {
    
    private ArrayList<String> deck;
    private Map<String, Map<String, Object>> dataPlayers;
    private String[] namePlayers;
    private Map<String, Map<String, String[]>> allCards;
    private ArrayList<String> cardsUsed;
    private ArrayList<Integer> bote;
    private Map<String, Object> valuesCardsDeck;

    public Game(
            ArrayList<String> deck,
            Map<String, Map<String, Object>> dataPlayers,
            String[] namePlayers,
            Map<String, Map<String, String[]>> allCards,
            ArrayList<String> cardsUsed,
            ArrayList<Integer> bote,
            Map<String, Object> valuesCardsDeck
    ) {

        this.deck = deck;
        /* Baraja */
        this.dataPlayers = dataPlayers;
        /* Datos de cada jugador */
        this.namePlayers = namePlayers;
        /* Array con dealer y nombre de jugadores */
        this.allCards = allCards;
        /* graficos de cartas */
        this.cardsUsed = cardsUsed;
        /* todas las cartas usadas hasta ahora en el juego */
        this.bote = bote;
        /* todas las apuestas del juego */
        this.valuesCardsDeck = valuesCardsDeck;
    }

    /* GUARDA EN UN ARRAY EL NOMBRE DE CADA JUGADOR */
 /*IMPRIME LAS BARAJAS*/
    public String[] PrintDeck(int index) {

        String[] graficCardEP = new String[10];

        Map<String, Object> player = dataPlayers.get(namePlayers[index]);

        ArrayList<String> cardsPlayer = (ArrayList<String>) player.get("Cards");

        String[] cardsPlayerGrafic = {"", "", "", "", "", "", "", "", "", ""};

        for (int l = 0; l < cardsPlayer.size(); l++) {

            String card = cardsPlayer.get(l);
            String[] cardArr = card.split(" ");

            String typeCard = cardArr[0];

            Map<String, String[]> Stick = SearchStick(typeCard);

            for (int c = 0; c < 10; c++) {
                String[] graficCard = Stick.get(card);

                if ((c == 0 || c == 9) && (l != 0)) {
                    cardsPlayerGrafic[c] += " ";
                }

                if (graficCard[c] != null) {
                    cardsPlayerGrafic[c] += graficCard[c];
                }

            }

        }

        graficCardEP = cardsPlayerGrafic;

        return graficCardEP;

    }

    /* BUSCA GRAFICAS DE BARAJAS POR PALO */
    public Map<String, String[]> SearchStick(String typeCard) {

        Map<String, String[]> Stick = new HashMap<>();

        switch (typeCard) {
            case "H":
                Stick = allCards.get("H");
                break;
            case "D":
                Stick = allCards.get("D");
                break;
            case "P":
                Stick = allCards.get("P");
                break;
            case "T":
                Stick = allCards.get("T");
                break;
        }

        return Stick;
    }

    public void deletCards(ArrayList<String> cardsUsed) {

        for (String card : cardsUsed) {

            if (deck.contains(card)) {
                deck.remove(card);
            }
        }
        /*System.out.println(deck.size());*/
    }

    /* CREA LOS DATOS DE CADA JUGADOR */
    public void dataOfPlayer(String[] namePlayers) {

        for (int i = 1; i < namePlayers.length; i++) {

            int total = 0;
            /*puntuacion*/
            int money = 100;
            boolean state = true;
            /*estado de juego*/
            ArrayList<String> cards = TwoRandomCards(deck);
            /*Las dos cartas seleccinadas*/
 /*deletCards(cardsUsed);*/
 /*Se eliminan las dos cardas random seleccionadas*/
            ArrayList<String> cardsValues = cards;

            Map<String, Object> dataPlayerIndividual = new HashMap<>();
            dataPlayerIndividual.put("Total", total);
            dataPlayerIndividual.put("Money", money);
            dataPlayerIndividual.put("State", state);
            dataPlayerIndividual.put("Cards", cards);
            dataPlayerIndividual.put("Cards Values", cardsValues);

            dataPlayers.put(namePlayers[i], dataPlayerIndividual);
        }
        /*Ya se creo la lista con los datos de cada jugador*/

    }

    /* SACA DE LA BARAJA LAS OS PRIMERAS CARTAS DE CADA JUGADOR */
    public ArrayList<String> TwoRandomCards(ArrayList<String> deck) {

        /* Convertimos el array deck en una lista */
        List<String> deckList = deck;

        /* desordenamos la lista */
        Collections.shuffle(deckList);

        /* Convertimos la lista en un array */
        ArrayList<String> cardsDisordered = new ArrayList(deckList);

        /* Array donde se guardaran las dos primeras cartas */
        ArrayList<String> twoCards = new ArrayList();

        for (int i = 0; i < 2; i++) {
            Random random = new Random();
            int index = random.nextInt(deck.size());
            String cardSelected = cardsDisordered.get(index);
            cardsUsed.add(cardSelected);/*futura carta a eliminar*/
            deletCards(cardsUsed);
            twoCards.add(cardSelected);
        }

        return twoCards;
    }

    /* INSERTA APUESTA EN BOTE Y RESTA DE MONEY DE JUGADOR */
    public void CreateBet(int index, Scanner lt) {

        Map<String, Object> player = dataPlayers.get(namePlayers[index]);

        int moneyPlayer = (int) player.get("Money");
        int bet = 0;

        boolean validInput = false;
        while (!validInput) {
            System.out.println("Su saldo es " + moneyPlayer + " cuando desea apostar:");
            if (lt.hasNextInt()) {
                bet = lt.nextInt();
                if (bet >= 1 && bet <= moneyPlayer) {
                    player.put("Money", moneyPlayer - bet);
                    bote.add(bet);
                    validInput = true;
                } else {
                    System.out.println("Valor invalido. Intenter nuevamente");
                }
            } else {
                System.out.println("Entrada invalida. Intente nuevamente");
                lt.next();
            }
        }

    }

    /* Da una nueva carta al jugador y la elimina */
    public void GetNewCard(int index) {/*index = indice de jugador*/

        /* Convertimos el array deck en una lista */
        List<String> deckList = deck;

        /* desordenamos la lista */
        Collections.shuffle(deckList);

        /* Convertimos la lista en un array */
        ArrayList<String> cardsDisordered = new ArrayList(deckList);

        /*Eleccion aleatoria de nueva carta*/
        Random random = new Random();
        int indexNewcard = random.nextInt(deck.size());
        String cardSelected = cardsDisordered.get(indexNewcard);
        cardsUsed.add(cardSelected);/*futura carta a eliminar*/
        deletCards(cardsUsed);
        
        /* Insertar la nueva carta en el dato de Cards a ese jugador */
        Map<String, Object> player = dataPlayers.get(namePlayers[index]);      
        ArrayList<String> playerCards = (ArrayList<String>) player.get("Cards");  
        playerCards.add(cardSelected);
        
        /* Imprimir su nueva carta */
        String[] cardArr = cardSelected.split(" ");
        String typeCard = cardArr[0];        
        Map<String, String[]> stickNewCard = SearchStick(typeCard);
        String[] cardGrafic = stickNewCard.get(cardSelected);
        
        for(String line : cardGrafic) {
            System.out.println(line);
        }
        
    }
    
    /* SUMA TODOS LOS PUNTOS QUE OBTUVO CADA JUGADOR */
    public int sumPointPlayer(int index){
        
        /* Obtenemos datos de jugador */
        Map<String, Object> player = dataPlayers.get(namePlayers[index]);
        
        ArrayList<String> cards = (ArrayList<String>) player.get("Cards");
        
        int pointPlayer = 0;
        
        int countAs = 0;
        
        for(String card: cards) {
            
            String[] cardArr = card.split(" ");
            String cardNumber = cardArr[1];
            
            if(cardNumber.equals("As")) {
                countAs++;
            } else {
                int valueCardNumber = (int) valuesCardsDeck.get(cardNumber);
                pointPlayer += valueCardNumber;
            } 
        }
        
        if(countAs != 0) {
            for(int i = 0; i < countAs; i++) {
                if(pointPlayer + 11 < 21) {
                    pointPlayer += 11;
                    
                } else {
                    pointPlayer++;
                }
            }
            return pointPlayer;
        } else{
            return pointPlayer;
        }
        
    }
    
    /**
     * funcion para pruebas *
     */
    public void Prueba(int index) {
        
        Map<String, Object> player = dataPlayers.get(namePlayers[index]);
        
        ArrayList<String> cards = (ArrayList<String>) player.get("Cards");
        
        for(String card: cards) {
            
            String[] cardArr = card.split(" ");
            String cardNumber = cardArr[1];
            
            int valueCardNumber = (int) valuesCardsDeck.get(cardNumber);
            
            System.out.println(card + ": " + valueCardNumber);
        }
    }

}
