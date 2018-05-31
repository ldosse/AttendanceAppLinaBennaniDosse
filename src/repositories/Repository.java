package repositories;

import java.util.ArrayList;
import java.util.function.Predicate;
import model.Swipe;
import daos.DAOImpl;
import java.io.IOException;
import java.text.ParseException;
//import java.util.logging.Level;
//import java.util.logging.Logger;


public class Repository implements RepositoryInterface {
    private ArrayList<Swipe> items;    
    
    public Repository() {
        this.items = new ArrayList<>();       
    }
    
    public Repository(ArrayList<Swipe> items) {        
        this.items = items;
    }
    
    public Repository(String filename) throws IOException, ParseException {
        this();
        // Create dao and execute load
        DAOImpl daoObj = new DAOImpl();
        this.items = daoObj.load(filename).items;
    }
    
    public ArrayList<Swipe> getItems() {        
        return this.items;
    }
    
    public void setItems(ArrayList<Swipe> items) {        
        this.items = items;
    }
    
    @Override
    public void add(Swipe item) {
        this.items.add(item);
    }
       
    @Override
    public void remove(int id) {
        Predicate<Swipe> predicate = e->e.getId() == id;       
        this.items.removeIf(predicate);
    }
    
    @Override
    public Swipe getItem(int id) {
        for (Swipe item:this.items) {
            if (item.getId() == id)
                return item;
        }
        return null;
    }
    
    @Override
    public String toString() {
        return "\nItems: " + this.items;
    }    
    
    public String toString(char delimiter) {
        String output = "";   
        this.items.stream().map((item) -> item.toString(delimiter)).reduce(output, String::concat);
        return output;
    }    
    
    @Override
    public void store(String filename) {       
        // create dao and execute store    
        DAOImpl daoObj = new DAOImpl();
        try {
            daoObj.store(filename,this);
        } catch (IOException ex) {
//            Logger.getLogger(Repository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        
}
