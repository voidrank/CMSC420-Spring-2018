package demos.hashing;

/**
 * Created by Jason on 7/2/2017.
 */
public class LinearProbingHashTable {

    private static final int[] PRIMES = {
            3 ,   5,	7,	11,	13,	17,	19,	23,	29,
            31,	37,	41,	43,	47,	53,	59,	61,	67,	71,
            73,	79,	83,	89,	97,	101,103,107,109,113,
            127,131,137,139, 149, 151, 157,	163, 167, 173,
            179	,181,191,193,197,	199,	211,	223,	227,	229,
            233,	239,	241,	251,	257,	263,	269,	271,	277,	281,
            283,	293,	307,	311,	313,	317,	331,	337,	347,	349,
            353,	359,	367,	373,	379,	383,	389,	397,	401,	409,
            419,	421,	431,	433,	439,	443,	449,	457,	461,	463};

    private int currPrimeInd;
    private String[] buffer;
    private int count;

    public LinearProbingHashTable(){
        currPrimeInd =4; // Start with 13
        buffer = new String[PRIMES[currPrimeInd]];
        count = 0;
    }

    public void insert(String key){
        int probe = myHash(key);
        while(buffer[probe] != null) {
            if(buffer[probe].equals(key))
                return;
            probe = (probe + 1) % buffer.length;
        }
        buffer[probe] = key;
        if((++count) > (buffer.length / 2)) // 50% of a load factor
            enlarge(); // This won't be costly or anything
    }

    // The approach of the slides
    private int myHash(String s){
        return (s.hashCode() & 0x7fffffff) % buffer.length;
    }

    // We implement the approach that we talked about in class. This could probably be
    // optimized through binary search.
    private void enlarge(){
        String[] old = buffer;
        findNextSize();
        buffer = new String[PRIMES[currPrimeInd]];
        count = 0; // Insertions will affect this, won't they?
        for(String s : old)
            if(s!= null) // Don't forget; just because we enlarge, doesn't mean we don't have null values already!
                insert(s); // Reinsert everything to the new buffer
    }

    private void findNextSize(){
        int currSz = buffer.length;
        for(int i = currPrimeInd; i < PRIMES.length; i++) {
            if (PRIMES[i] > 2 * currSz) {
                currPrimeInd = i - 1;
                return;
            }
        }
        throw new MaxEnlargementsReachedException("Linear probing hash table cannot enlarge further.");
    }

    public String search(String key){
        int probe = myHash(key);
        while(buffer[probe] != null) {
            if (key.equals(buffer[probe]))
                return buffer[probe];
            probe = (probe + 1) % buffer.length;
        }
        return null;
    }


    public void delete(String key){


        /* Implement this! */

        int probe = myHash(key);
        while(buffer[probe] != null){
            if(buffer[probe].equals(key)){ // Found it! Nullify and then re-insert all others
                buffer[probe] = null;
                probe = (probe + 1) % buffer.length;
                while(buffer[probe] != null){
                    String toBeReInserted = buffer[probe];
                    buffer[probe] = null;
                    count--;
                    insert(toBeReInserted); // this increases count...
                    probe = (probe + 1) % buffer.length;
                }
                count--;
                return;
            }
            probe = (probe + 1) % buffer.length;

        }

    }

    public int getCount(){
        return count;
    }

    public boolean isEmpty(){
        return getCount() == 0;
    }


}
