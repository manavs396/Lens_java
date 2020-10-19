package com.example.a2.Model;
import android.graphics.ColorSpace;
import androidx.annotation.NonNull;
import java.security.PublicKey;
import java.util.ArrayList;

/**
 *This class make the data structure for storage on lenses
 * and also add and retrieve the lens
 */
public class LensManager {

    private  ArrayList<Lens> lens_list;
    // Singelton support
    private static LensManager instance;
    private LensManager(){
        // Private to prevent anyone else from instantiating
        lens_list = new ArrayList<>();
    }
    public static LensManager getInstance(){
        if(instance == null){
            instance = new LensManager();
        }
        return  instance;
    }

    // Normal Object Code

    public void add(Lens lens_inp) {
        lens_list.add(lens_inp);
    }
    public  int get_number_lenses(){
        return lens_list.size();
    }
    public  ArrayList<Lens> getarray(){
        return lens_list;
    }
    // this function returns the lens at particular index
    public Lens get_retrieve1(int lens_input){
        if ((lens_input >= 0) && (lens_input < lens_list.size())) {
            return lens_list.get(lens_input);
        }
        throw new IllegalArgumentException("Index of lens is invalid!!");
    }
    // this function returns lens as a string
    public  String get_retrieve(int lens_input) {
        if ((lens_input >= 0) && (lens_input < lens_list.size())) {
            return lens_list.get(lens_input).toString();
        }
        throw new IllegalArgumentException("Index of lens is invalid!!");
    }
}
