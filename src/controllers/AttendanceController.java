package controllers;

import helpers.InputHelper;
import java.io.IOException;
import java.text.ParseException;
import java.util.SortedSet;
import java.util.TreeSet;
import repositories.Repository;
import model.Swipe;
import model.VisitorSwipe;

/**
 *
 * @author mga
 */
public class AttendanceController {
     private final Repository repository;
    
    /**
     * 
     * 
     */
        
    public AttendanceController(){
        // to be completed
        Repository temp = new Repository();

        InputHelper inputHelper = new InputHelper();
        char ans = inputHelper.readCharacter("Would you like to load from a file?(Y/N)", "YN");
        
        if ( ans == 'Y'){
            InputHelper inputHelper1 = new InputHelper();
            String filename = inputHelper1.readString("Enter File Name");
            try {
                temp = new Repository(filename);

            } catch (IOException | ParseException ex) {
    //                Logger.getLogger(AttendanceController.class.getName()).log(Level.SEVERE, null, ex);
            } 
        } else {  
            temp = new Repository();
         }
        this.repository = temp;
    }
        
    
    /**
     *
     */
    public void run() throws IOException, ParseException {
        boolean finished = false;
        
        do {
            char choice = displayAttendanceMenu();
            switch (choice) {
                case 'A': 
                    addSwipe();
                    break;
                case 'B':  
                    listSwipes();
                    break;
                case 'C': 
                    listSwipesByCardIdOrderedByDateTime(); // 
                    break;                    
                case 'D': 
                    listSwipeStatistics(); //
                    break;
                case 'Q':
                    InputHelper inputHelper = new InputHelper();
                    char c = inputHelper.readCharacter("Would you like to "
                            + "save your changes in a new file?(Y/N)", "YN");
                            if(c == 'Y'){
                                storeSwipes();
                            }
                    finished = true;
            }
        } while (!finished);
    }
    
    private char displayAttendanceMenu() {
        InputHelper inputHelper = new InputHelper();
        System.out.print("\nA. Add Swipe");
        System.out.print("\tB. List Swipes");        
        System.out.print("\tC. List Swipes In Date Time Order");
        System.out.print("\tD. List Room Stats");
        System.out.print("\tQ. Quit\n");         
        return inputHelper.readCharacter("Enter choice", "ABCDQ");
    }    
    
    private void addSwipe() {
        InputHelper inputHelper = new InputHelper();
        System.out.format("\033[31m%s\033[0m%n", "Add Swipe");
        System.out.format("\033[31m%s\033[0m%n", "=========");
        System.out.print("\nS. Add Swipe");
        System.out.print("\tV. Add Visitor Swipe\n");
        
//        Asks user if they want to add a visitor swipe or normal swipe
//        then asks for cardId and room
//        if user wants normal swipe then user the basic constructor
//        if user wants visitor swipe ask for visitor name, company
//        and use visitor swipe to repository
       
        char choice = inputHelper.readCharacter("Enter choice", "SV");
        
        String cardId = inputHelper.readString("Enter card Id");
        String room = inputHelper.readString("Enter room name");
            
//        Swipe is declared now so that regardless of swipe type it's considered
//        as normal Swipe so that it can be added to Repository
        Swipe newSwipe;
        
        if(choice =='S') {
            newSwipe = new Swipe(cardId, room);
        } else { 
            String visitorName = inputHelper.readString("Enter visitor name");
            String visitorCompany = inputHelper.readString("Enter visitor company");
            newSwipe = new VisitorSwipe(cardId, room,visitorName,visitorCompany);
        }
        
        this.repository.add(newSwipe);

        System.out.println("\nSwipe successfully added :\n" + newSwipe.toString()+"\n");
    }
    
    private void listSwipes() throws IOException, ParseException {        
        System.out.format("\033[31m%s\033[0m%n", "Swipes");
        System.out.format("\033[31m%s\033[0m%n", "======");
//      Checks if repository is empty
        if(repository.getItems().isEmpty()){
            System.out.print("No Swipes.\n");
        }
        else{
            System.out.print(repository.toString()+"\n");
        }
        
    }      
      

    private void listSwipesByCardIdOrderedByDateTime() {        
        InputHelper inputHelper = new InputHelper();
        System.out.format("\033[31m%s\033[0m%n", "Swipes By Card Id");
        System.out.format("\033[31m%s\033[0m%n", "=================");

        String cardId = inputHelper.readString("Enter Card ID");
        
//        Create a TreeSet to store swipes of the cardID given by user
        TreeSet<Swipe> setSwipes = new TreeSet<>((s1, s2) -> s2.compareTo(s1));
        
        repository.getItems().forEach((Swipe swipe) -> {
            if(swipe.getCardId().equalsIgnoreCase(cardId)){
                setSwipes.add(swipe); }
        });
        
        if (setSwipes.isEmpty()){System.out.print("Card Not Found.\n");}
        else{
            setSwipes.forEach((swipe) -> { System.out.print(swipe.toString()+"\n");});
        }
    }
    
    private void listSwipeStatistics() {
        System.out.format("\033[31m%s\033[0m%n", "Swipe Statistics for room");
        System.out.format("\033[31m%s\033[0m%n", "=========================");
        InputHelper inputHelper = new InputHelper();
        String room = inputHelper.readString("Enter Room");
        
        SortedSet<Swipe> setSwipes = new TreeSet<>((s1, s2) -> s2.compareTo(s1));
        
        repository.getItems().forEach((swipe) -> {
            if(swipe.getRoom().equalsIgnoreCase(room)){
                setSwipes.add(swipe);
        }});
         if (setSwipes.isEmpty()){System.out.print("\nNo swipes for Room Found.\n");}
        else{
        System.out.println("\nStats for Room "+ room + "\nTotal number of swipes: "
                            + setSwipes.size()+ "\nMost recent swipe: " + setSwipes.first().toString()+"\n");
         }
    }
    
    private void storeSwipes(){
        InputHelper inputHelper = new InputHelper();
        String filename = inputHelper.readString("Enter filename");
        repository.store(filename);
        System.out.println("Rep saved in file");
    
    }
}