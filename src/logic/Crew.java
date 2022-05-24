package logic;

/**
 * Holds knowledge about Crew member.

 * @author Bryony
 *
 */
public class Crew {

  private String name;
  private int salary;
  
  protected Crew(String name, int salary) {
    this.setName(name);
    this.setSalary(salary);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSalary() {
    return salary;
  }

  public void setSalary(int salary) {
    this.salary = salary;
  }
  
}
