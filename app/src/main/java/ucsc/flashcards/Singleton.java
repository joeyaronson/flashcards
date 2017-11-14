package ucsc.flashcards;

import android.app.Application;

/**
 * Created by Jerell-PC on 11/14/2017.
 */

public class Singleton extends Application{

    private static Singleton instance;

    public static Singleton getInstance(){
        if (instance== null) {
                instance = new Singleton();
        }
        // Return the instance
        return instance;
    }

    private Singleton() {}

    public SQLDataBase getInstanceofDB(){
        SQLDataBase DB = new SQLDataBase(this);
        return DB;
    }
}

