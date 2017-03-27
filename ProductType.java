package sample;

/**
 * Created by Wienio on 2017-03-18.
 */
public enum ProductType {
    BREAD{
        @Override public String toString() {return "Pieczywo";}
    },
    FRUITS {
        @Override public String toString() {return "Owoce";}
    },
    VEGETABLES {
        @Override public String toString() { return "Warzywa";}
    };
}
