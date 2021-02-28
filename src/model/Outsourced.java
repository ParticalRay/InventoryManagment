package model;



public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor of Outsourced that extends Part
     * @param Id int id
     * @param name string name
     * @param price double price
     * @param stock int stock
     * @param min int min
     * @param max int max
     * @param companyName string company name
     */
    public Outsourced(int Id, String name, double price,
                      int stock, int min, int max,
                      String companyName){
        super(Id,name,price,stock,min,max);
        this.companyName = companyName;
    }

    /**
     * set the company name
     * @param companyName string being set as company name
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * get the company name
     * @return current company name
     */
    public String getCompanyName(){
        return this.companyName;
    }
}
