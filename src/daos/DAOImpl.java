/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import model.Swipe;
import model.VisitorSwipe;
import repositories.Repository;

/**
 *
 * @author Admin 2
 */
public class DAOImpl implements DAOInterface {
    public BufferedReader bufferRead;
    public BufferedWriter bufferWrite;
    ArrayList<Swipe> itemsList;
    static final char DELIMITER = ',';

    public Repository load(String filename) throws IOException, ParseException{       
        Repository repository = new Repository();
        
        try {
            bufferRead = new BufferedReader(new FileReader(new File(filename)));
            String input = "";
            input = bufferRead.readLine();
            
            if(input == null){System.out.println("File is empty");}
            
            else while (input != null){
                String[] swipe = input.split(","); 
                Swipe newSwipe;
                
                int swipeId = Integer.parseInt(swipe[0]);
                String cardId = stripQuotes(swipe[1]);
                String room = stripQuotes(swipe[2]);
                
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar swipeDateTime = Calendar.getInstance();
                Date dateCalendar = dateFormat.parse(stripQuotes(swipe[3]));
                swipeDateTime.setTime(dateCalendar);
                
                if (swipe.length == 6 ){
                    newSwipe = new VisitorSwipe(swipeId, cardId, room,
                            swipeDateTime, swipe[4], swipe[5]);
                }
                else{
                    newSwipe = new Swipe(swipeId, cardId, room,
                            swipeDateTime);
                }
                repository.add(newSwipe);
                input = bufferRead.readLine();
            }
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(DAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Oops! File not found.\ny"
                        + "Empty repository created.");
        } finally {
            return repository;
        }
    }

     public void store(String filename, Repository repository) throws IOException {       
        BufferedWriter output = null;
        
            output = new BufferedWriter(new FileWriter(new File(filename)));
            for(Swipe s:repository.getItems()){
                output.write(s.toString(DELIMITER));
                output.newLine();
            }
            output.close();
    }   
    
    public String stripQuotes(String str){
        if (str.startsWith("\"")||(str.endsWith("\""))) {
            return str.substring(1, str.length()-1);
        }
        return str;
    }
            
}