package com.mycompany.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BlackJack {

    public static void main(String[] args) {
        
        /* Cards con su respectivo valor en Black Jack*/
        Map<String, Object> valuesCardsDeck = new HashMap<>();
        valuesCardsDeck.put("2", 2);
        valuesCardsDeck.put("3", 3);
        valuesCardsDeck.put("4", 4);
        valuesCardsDeck.put("5", 5);
        valuesCardsDeck.put("6", 6);
        valuesCardsDeck.put("7", 7);
        valuesCardsDeck.put("8", 8);
        valuesCardsDeck.put("9", 9);
        valuesCardsDeck.put("10", 10);
        valuesCardsDeck.put("J", 10);
        valuesCardsDeck.put("Q", 10);
        valuesCardsDeck.put("K", 10);
        valuesCardsDeck.put("As", new int[]{1,11});

        /* Cards en orden */
        String[] HeartsCards = {"H 2", "H 3", "H 4", "H 5", "H 6", "H 7", "H 8", "H 9"};
        String[] DiamondsCards = {"D 2", "D 3", "D 4", "D 5", "D 6", "D 7", "D 8", "D 9"};
        String[] SpadesCards = {"P 2", "P 3", "P 4", "P 5", "P 6", "P 7", "P 8", "P 9"};
        String[] TrebolsCards = {"T 2", "T 3", "T 4", "T 5", "T 6", "T 7", "T 8", "T 9"};

        String[] HeartsCardsTen = {"H 10", "H J", "H Q", "H K", "H As"};
        String[] DiamondsCardsTen = {"D 10", "D J", "D Q", "D K", "D As"};
        String[] SpadesCardsTen = {"P 10", "P J", "P Q", "P K", "P As"};
        String[] TrebolsCardsten = {"T 10", "T J", "T Q", "T K", "T As"};

        /* creacion de elementos para la clase Game (Constructor) */
        ArrayList<String> deck = new ArrayList<>();
        /* Baraja vacia */
        Map<String, Map<String, Object>> dataPlayers = new HashMap<>();
        /* datos de jugadores vacio */

        deck.addAll(Arrays.asList(HeartsCards));
        deck.addAll(Arrays.asList(DiamondsCards));
        deck.addAll(Arrays.asList(SpadesCards));
        deck.addAll(Arrays.asList(TrebolsCards));
        deck.addAll(Arrays.asList(DiamondsCardsTen));
        deck.addAll(Arrays.asList(SpadesCardsTen));
        deck.addAll(Arrays.asList(TrebolsCardsten));
        deck.addAll(Arrays.asList(HeartsCardsTen));

        /* ArrayList que se estara actualizando, contendra las cartas que se iran eliminando */
        ArrayList<String> cardsUsed = new ArrayList<>();

        /* ArrayList de bote que contenera las apuestas */
        ArrayList<Integer> bote = new ArrayList<>();

        /* IMPORTACION DE SCANNER */
        Scanner lt = new Scanner(System.in);

        /* BIENVENIDA AL JUEGO */
        System.out.println("Desea empezar una nueva partida de BlackJack?:");
        String gameIn = lt.next();

        switch (gameIn) {
            case "si":/* Inicia el juego */
                /* USO FUNCION QUE PREGUNTA EL NUMERO DE JUGADORES */
                int numPlayers = getNumberPlayers(lt);

                /* USO DE FUNCION QUE PREGUNTA EL NOMBRE DE CADA JUGADOR */
                String[] namePlayersEmpty = new String[numPlayers + 1];
                /* Aqui esta el array con todos los nombres de los jugadores*/
                String[] namePlayers = getNamePlayers(namePlayersEmpty, lt, numPlayers);


                /* CREACION DE DICCIONARIO PARA PODER MANEJAR LA LOGICA DEL JUEGO */
                GraficCards baseCards = new GraficCards();
                Map<String, Map<String, String[]>> allCards = baseCards.allCardsG;
                Game game = new Game(
                        deck, /* Baraja (vacio) */
                        dataPlayers, /* Datos de jugadores (vacio)*/
                        namePlayers, /* [deale, ... nombre de jugadores] (no vacio) */
                        allCards, /* graficos de cartas (no vacio) */
                        cardsUsed, /* array con cartas que se van usando (vacio) */
                        bote, /* Array donde se guaradan las apuestas (vacio )*/
                        valuesCardsDeck
                );

                game.dataOfPlayer(namePlayers);
                /*Se crea la lista con los datos de cada jugador*/

                /**
                 * **
                 * int pr = game.Prueba(); System.out.println(pr); **
                 */
                System.out.println(" ");
                System.out.println(" ");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println("|");
                System.out.println(" ");
                System.out.println(" ");

                /* IMPRIME LAS DOS PRIMERAS CARTAS DE CADA JUGADOR */
                System.out.println("Sus cartas son las siguientes");
                for (int i = 1; i < namePlayers.length; i++) {
                    /* grafico de las dos cartas de ese jugador */
                    String[] cardsSelectedG = game.PrintDeck(i);

                    System.out.println(namePlayers[i] + ":");

                    /* imprime cada elemento del array */
                    for (String line : cardsSelectedG) {
                        System.out.println(line);
                    }

                    /*game.Prueba();*/
                }

                /**
                 * INICIO DE JUEGO *
                 */
                boolean[] statusPlayers = new boolean[namePlayers.length];
                for (int i = 0; i < namePlayers.length; i++) {
                    statusPlayers[i] = true;
                }

                boolean[] statusPlayersFinal = new boolean[namePlayers.length];
                for (int i = 0; i < namePlayers.length; i++) {
                    statusPlayersFinal[i] = true;
                }

                int rounds = 1;
                /* contador de las rondas */
                int playersInGame = namePlayers.length - 1;
                
                
                /* BUCLE GAME */
                while (rounds <= 5) {
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("|");
                    System.out.println("|");
                    System.out.println("|");
                    System.out.println("|");
                    System.out.println("|");
                    System.out.println("|");
                    System.out.println(" ");
                    System.out.println(" ");
                    System.out.println("Ronda #" + rounds);

                    if (playersInGame != 0) {
                        for (int i = 1; i < statusPlayers.length; i++) {
                            
                            System.out.println("|");

                            boolean statusPlayerIn = statusPlayers[i];

                            if (statusPlayerIn) {

                                System.out.println("Turno de " + namePlayers[i]);
                                boolean continueRound = true;

                                while (continueRound) {

                                    System.out.println("Elija el numero de la opcion que desea elegir:");
                                    System.out.println("    1. Apostar");
                                    /* HECHO */
                                    System.out.println("    2. Retirarse");
                                    /* HECHO */
                                    System.out.println("    3. Plantarse");
                                    /* HECHO */
                                    System.out.println("    4. Recordar cartas");
                                    System.out.println("    5. Pedir carta");
                                    /* HECHO */

                                    int election = lt.nextInt();

                                    switch (election) {
                                        case 1:
                                            /* Hacer apuesta */
                                            game.CreateBet(i, lt);/* suma la apuesta al bote y resta a money */
                                            /*game.Prueba(i);*/

                                            System.out.println("¿Desea pedir una carta? (si/no)");

                                            String question = lt.next();

                                            if (question.equals("si")) {
                                                System.out.println("Su carta es:");
                                                game.GetNewCard(i);
                                                /*game.Prueba();*/
                                            }

                                            continueRound = false;
                                            /* siguiente jugador */
                                            break;
                                        case 2:
                                            /* Retirarse */
                                            statusPlayers[i] = false;
                                            /* ya no participara en la ronda */
                                            statusPlayersFinal[i] = false;
                                            
                                            playersInGame--;
                                            
                                            continueRound = false;
                                            break;
                                        case 3:
                                            /* Plantarse */
                                            statusPlayers[i] = false;
                                            /* ya no participara en la ronda */
                                            statusPlayersFinal[i] = true;
                                            /* se planta y esperara al final si gana o no */
                                            
                                            playersInGame--;
                                            
                                            continueRound = false;/* siguiente jugador */
                                            break;
                                        case 4:
                                            System.out.println("Sus cartas son ....");
                                            String[] rememberCardsPlayer = game.PrintDeck(i);

                                            for (String line : rememberCardsPlayer) {
                                                System.out.println(line);
                                            }

                                            break;
                                        case 5:
                                            System.out.println("Su carta es ....");
                                            game.GetNewCard(i);
                                            /*game.Prueba();*/

                                            continueRound = false;/* siguiente jugador */
                                            break;
                                    }

                                }

                            }

                        }
                    } else {
                        break;
                    }

                    rounds++;
                }

                //System.out.println("Despues de jugar");
                //System.out.println(Arrays.toString(statusPlayers));
                //System.out.println(Arrays.toString(statusPlayersFinal));
                
                // Aqui se reunen los puntos totales d cada jugador
                ArrayList<Integer> pointsEachPlayer = new ArrayList<>();
                pointsEachPlayer.add(0);
                for(int i = 1; i < statusPlayersFinal.length; i++) {
                    
                    boolean statusPlayer = (boolean) statusPlayersFinal[i];
                    
                    if(statusPlayer) {
                        //System.out.println(namePlayers[i]+":");
                        int pointsPlayer = game.sumPointPlayer(i);
                        pointsEachPlayer.add(pointsPlayer);
                        
                        /*game.Prueba(i);*/
                    }
   
                }
                /*System.out.println(pointsEachPlayer);*/
                // Se saca el jugador con mayor puntaje <= 21
                String playerWinner = "";
                int pointsPlayerWinner = 0;
                for(int i = 1; i < pointsEachPlayer.size(); i++) {
                    
                    String player = namePlayers[i];
                    int playerPoints = (int) pointsEachPlayer.get(i);
                    
                    if(playerPoints > pointsPlayerWinner && playerPoints <= 21) {
                        pointsPlayerWinner = playerPoints;
                        playerWinner = player;
                    }
                }
                
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                if(pointsPlayerWinner == 0) {
                    System.out.println("Nadie gano");
                } else {
                    System.out.println("El ganador es " + playerWinner + " con " + pointsPlayerWinner + " puntos");
                }
                /** 
                 * Falta:
                 * - Dar dinero a ganador
                 * - Dar la opcion de seguir jugando con los mismos datos de jugador
                 **/
                
                break;

            default:
                /*Termina el juego*/
                System.out.println("Adios");
        }

    }

    /**
     * *******************************************************************************************
     */

    /* FUNCION QUE OBTIENE EL NUMERO DE JUGADORES Y SUS NOMBRES*/
    public static int getNumberPlayers(Scanner lt) {
        int numPlayers = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Ingrese el número de jugadores (mínimo 2 -- máximo 5): ");
            if (lt.hasNextInt()) {
                numPlayers = lt.nextInt();
                if (numPlayers >= 2 && numPlayers <= 5) {
                    validInput = true;
                } else {
                    System.out.println("Cantidad de jugadores inválida. Intente nuevamente.");
                }
            } else {
                System.out.println("Entrada no válida. Por favor ingrese un número entero.");
                lt.next(); // Descartar entrada no válida
            }
        }

        return numPlayers;
    }

    /* FUNCION QUE OBTIENE EL NOMBRE DE CADA JUGADOR Y DEVUELVE UN ARRAY CON TODOS LO NOMBRE INCLUYENDO EL DEALER */
    public static String[] getNamePlayers(String[] namePlayersEpmty, Scanner lt, int numPlayers) {

        String[] namePlayers = namePlayersEpmty;
        String name;
        int i = 0;
        while (i <= numPlayers) {

            if (i == 0) {
                namePlayers[0] = "Dealer";
                i++;
            } else {
                System.out.println("Ingrese el nombre del jugador #" + i + ":");
                if (lt.hasNext()) {
                    name = lt.next();
                    namePlayers[i] = name;
                    i++;
                } else {
                    System.out.println("valor incorrecto. Intente nuevamente.");
                    lt.next();
                }
            }
        }

        return namePlayers;
    }

}
