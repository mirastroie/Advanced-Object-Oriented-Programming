package L3.Ex2;
import java.util.Objects;


public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private long id;
    private Gender gender;

    public Person(String firstName, String lastName, int age, long id, Gender gender)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.id = id;
        this.gender = gender;
    }
    public Person()
    {
        this("Alexa","Robinson",23,1,Gender.M);
    }
//    Getters
    public String getFirstName()
    {
        return this.firstName;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public int getAge()
    {
        return this.age;
    }
    public long getId()
    {
        return this.id;
    }
    public Gender getGender()
    {
        return this.gender;
    }
//   Setters
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public void setGender(Gender gender)
    {
        this.gender = gender;
    }
    // Overriding
    @Override
    public String toString()
    {
         return "Person \n" + " First name: " + firstName +
                 "\n Last name: " + lastName + "\n Age: " + age
                 + "\n Id: " + id + "\n Gender: " + gender;
    }
    @Override
    public boolean equals(Object p)
    {

        if(this == p)
            return true;
        if(p == null || !(p instanceof Person))
            return false;

        Person person = (Person) p;
        return Objects.equals(this.firstName, person.firstName) && Objects.equals(this.lastName, person.lastName) && (this.age == person.age)
                && (this.gender == person.gender) && (this.id == person.id);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(firstName,lastName,age,id,gender);
    }
    public static void test(Person a, Person b)
    {
        if(a.equals(b)) {
            System.out.println(a.getFirstName() + " and " + b.getFirstName() + " are equal.");
            if(a.hashCode() != b.hashCode())
                System.out.println("Something is wrong!");
        }
        else
            System.out.println(a.getFirstName() + " and " + b.getFirstName() + " are NOT equal.");
    }
    public static void main(String[] args) {

        Person alexa = new Person();
        Person rachel = new Person("Rachel","Gibbs",27,4,Gender.M);
        Person tony = new Person("Anthony","Watson",34,7,Gender.M);
        Person rachel2 = new Person("Rachel","Gibbs",27,4,Gender.M);

        //test for toString method
        System.out.println(alexa);
        // test for equals and hashCode methods
        test(rachel,rachel2);
        test(alexa,tony);
        // test for setters
        alexa.setFirstName("Stephen");
        alexa.setLastName("Torres");
        alexa.setAge(56);
        alexa.setId(8);
        alexa.setGender(Gender.M);

        ///test for getters
        System.out.println(alexa.getFirstName() + " " + alexa.getLastName() + " is " + alexa.getAge() + " years old. Gender : " + alexa.getGender() + " , Id: " + alexa.getId());


    }
}
