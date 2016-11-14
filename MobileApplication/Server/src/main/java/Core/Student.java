package Core;

/**
 * Created by Emili on 2016-10-24.
 */

public class Student extends DomainObject{

    private String name;
    private String password;

    public Student(int username, String name, String password){
        super(username);
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return ("Name: " + name +
                "\nStudent Id: " + super.getId());
    }
}
