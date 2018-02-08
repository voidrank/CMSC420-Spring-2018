package edu.umd.cs.datastructures.demos.hashing;

/**<p>Small demo for the CMSC420 students on appropriately using hashCode() to build an object's hash code.</p>
 * @author Jason
 */
public class CustomHashes {

    private static final class FullName{

        private String firstName, middleName, lastName;

        public FullName(String first, String middle, String last){
            // Strings are immutable.
            firstName = first;
            middleName = middle;
            lastName = last;
        }


        // Type-safe equality
        @Override
        public boolean equals(Object o){
            if(o==null) return false;
            FullName oc;
            try {
                oc = (FullName)o;
            } catch(ClassCastException ignored){
                return false;
            }
            return firstName.equals(oc.getFirstName()) && 
                    middleName.equals(oc.getMiddleName()) && 
                    lastName.equals(oc.getLastName());
        }

        // A hash code method


        @Override
        public int hashCode(){
            int hash = 17;
            hash = 31*hash + firstName.hashCode();
            hash = 31*hash + middleName.hashCode();
            hash = 31*hash + lastName.hashCode();
            return hash;
        }


        // Setters
        
        void setFirstName(String first){firstName = first;}
        void setMiddleName(String middle){middleName = middle;}
        void setLastName(String last){lastName = last;}

        // Getters

        String getFirstName() { return firstName;}
        String getMiddleName() { return middleName;}
        String getLastName() { return lastName;}

        public String getFullName(){
            return "First Name: " + getFirstName() + ", Middle Name: " + getMiddleName() + ", Last Name: " + getLastName();
        }
    } // Class

    private static int modularHash(int hashCode, int M){
        return (hashCode & 0x7fffffff) % M;
    }

    public static void main(String[] args){
        FullName n1 = new FullName("Jane", "Marie", "Doe"),
                n2 = new FullName("Jane", "Marie", "Doe"),
                n3 = new FullName("Kylie", "Rachel", "Haggins");

        System.out.println("Distance between hashes of n1, " + n1.getFullName() + "  and n2: " + n2.getFullName() + " is:" +
                Math.abs(modularHash(n1.hashCode(), 997) - modularHash(n2.hashCode(), 997))+ ".");


        System.out.println("Distance between hashes of n1, " + n1.getFullName() + "  and n3: " + n3.getFullName() + " is:" +
                Math.abs(modularHash(n1.hashCode(), 997) - modularHash(n3.hashCode(), 997)) + ".");

    }
}
