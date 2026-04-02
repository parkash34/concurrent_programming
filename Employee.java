class Employee{
    String name;
    int salary;
    public Employee(String name, int salary){
        this.name = name;
        this.salary = salary;
    }

    public String getName(){return name;}
    public int getSalary(){return salary;}
    public void raiseSalary(float pct){
        // boolean, char
        // Integral: byte, short, int, long
        // Floating: float, double
        // String
        salary = (int)(salary * pct);
        salary *= pct;
    }
}