package domain;

/**
 * A class for a specific entity type(Client)
 * @author catad
 *
 */
public class Client extends BaseEntity<Long>{

    private String name;

    private int age;

    private String membershipType;

    /**
     * Default constructor.
     */
    public Client(){}

    /**
     * Constructor for an entity of type Client.
     *
     * @param n(String) the name
     * @param a(int) the age
     * @param mT(String) the membership type
     *
     */
    public Client(String n, int a, String mT){
        this.name = n;
        this.age = a;
        this.membershipType = mT;
    }

    /**
     * Gets the name of a Client.
     *
     * @return String
     *
     */
    public String getName() { return this.name; }

    /**
     * Gets the age of a Client.
     *
     * @return int
     *
     */
    public int getAge(){ return this.age; }

    /**
     * Gets the membership type of a Client.
     *
     * @return String
     *
     */
    public String getMembershipType(){ return this.membershipType; }

    /**
     * Sets a new name for a Client.
     *
     * @param n(String)
     *
     */
    public void setName(String n){ this.name = n; }

    /**
     * Sets a new age for a Client.
     *
     * @param a(int)
     *
     */
    public void setAge(int a){ this.age = a; }

    /**
     * Sets a new membership type for a Client.
     *
     * @param mT(String)
     *
     */
    public void setMembershipType(String mT){ this.membershipType = mT; }

    /**
     * Returns the info of a Client in a readable form.
     *
     * @return String
     *
     */
    @Override
    public String toString(){
        return "Client: " + this.name + " Age: " + this.age + " with membership of type: " + this.membershipType + '\n';
    }

    @Override
    public String toStringFile(){
        return this.getId() + "," + this.getName() + "," + this.getAge() + "," + this.getMembershipType();
    }


}
