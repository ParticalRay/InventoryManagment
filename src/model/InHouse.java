package model;



public class InHouse extends Part{
    private int machineId;

    /**
     * InHouse constructor and extends Part
     * @param Id integer id
     * @param name string name
     * @param price double price
     * @param stock integer stock 
     * @param min integer min
     * @param max integer max
     * @param machineId integer machine id
     */
    //Constructor
    public InHouse(int Id, String name, double price,
                       int stock, int min, int max,
                       int machineId){
        super(Id,name,price,stock,min,max);
        this.machineId = machineId;
    }

    /**
     * set the integer machine id
     * @param machineid machine id
     */
    public void setMachineId(int machineid){
        this.machineId = machineid;
    }

    /**
     * get the integer machine id
     * @return machine id
     */
    public int getMachineId(){
        return this.machineId;
    }
}
