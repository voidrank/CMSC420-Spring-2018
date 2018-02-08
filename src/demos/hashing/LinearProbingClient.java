package edu.umd.cs.datastructures.demos.hashing;

/**
 * Created by Jason on 7/2/2017.
 */
public class LinearProbingClient {

    public static void main(String[] args){
        LinearProbingHashTable ht  = new LinearProbingHashTable();
        for(String s : new String[]{"Susie", "Dale", "Mark", "Kurt", "Jacquelinne", "George"} )
            ht.insert(s);
        for(String s : new String[]{"Susie", "Dale", "Mark", "Kurt", "Jacquelinne", "George"} )
            if(ht.search(s) != null)
                System.out.println("Search for key " + s + " was successful.");
            else
                System.out.println("Search for key " + s + " failed!");


    }
}
