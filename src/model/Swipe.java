package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;

/**
 *
 * @author mga
 */
public class Swipe implements Comparable<Swipe>{

    /**
     *
     */
    protected final int id;

    /**
     *
     */
    protected String cardId;

    /**
     *
     */
    protected String room;

    /**
     *
     */
    protected final Calendar swipeDateTime;
    
    private static int lastSwipeIdUsed = 0;
    static final char EOLN='\n';       
    static final String QUOTE="\"";    
    private Comparator<Swipe> SwipeDateTimeComparator;
    /**
     *
     */
    public Swipe() {
        this.id = ++ lastSwipeIdUsed ;
        this.cardId = "Unknown";
        this.room = "Unknown";
        this.swipeDateTime = getNow();
    }
    
    /**
     *
     * @param cardId
     * @param room
     */
    public Swipe(String cardId, String room) {
        this.id = ++lastSwipeIdUsed;
        this.cardId = cardId;
        this.room = room;        
        this.swipeDateTime = getNow();
    }    
    
    /**
     *
     * @param swipeId
     * @param cardId
     * @param room
     * @param swipeDateTime
     */
    public Swipe(int swipeId, String cardId, String room, Calendar swipeDateTime) {
        this.id = swipeId;
        this.cardId = cardId;
        this.room = room;
        this.swipeDateTime = swipeDateTime;
        if (swipeId > Swipe.lastSwipeIdUsed)
            Swipe.lastSwipeIdUsed = swipeId;   
    }     
    
    /**
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    // Methods required: getters, setters, hashCode, equals, compareTo, comparator
    
    /**
     *
     * @param calendar
     * @return
     */
    
    public String getCardId() {
        return this.cardId.trim(); 
    }

    public void setCardId(String cardId){
        //TO-DO
            this.cardId = cardId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Calendar getSwipeDateTime() {
        return swipeDateTime;
    }

     private Calendar getNow() {
        Calendar now = Calendar.getInstance();  
        return now;
    }    

    public static String formatSwipeDateTime(Calendar calendar) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }    

    @Override
    public int hashCode(){
        return this.hashCode();
    }

    public boolean equals(Swipe otherSwipe){
        return this.getId() == otherSwipe.getId() &&
                this.getCardId().equals(otherSwipe) &&
                this.getRoom().equals(otherSwipe.getRoom()) &&
                this.getSwipeDateTime().equals(otherSwipe.getSwipeDateTime());
                }
    
    
    public int compareTo(Swipe compareSwipe){
        return this.swipeDateTime.compareTo(compareSwipe.swipeDateTime);
    }
    
    /**
     *
     * @param delimiter
     * @return
     */
    
    public String toString(char delimiter){
        return String.valueOf(this.id) + delimiter + this.getCardId() + delimiter + this.getRoom() + 
                delimiter + formatSwipeDateTime(this.swipeDateTime);
    }

    
    @Override
    public String toString() {
        return "\nSwipe Id: " + this.id + " - Card Id: " + this.cardId +            
                " - Room: " + this.room + " - Swiped: " + formatSwipeDateTime(this.swipeDateTime);
    }
    
}
