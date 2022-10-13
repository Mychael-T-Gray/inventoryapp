package gray.inventoryapp.model;


public class InHouse extends Part {

    private int machineId;

    /**
     * Constructor with parameters of part type InHouse
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**
     * machine Id setter
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * machine id getter
     * @return
     */
    public int getMachineId() {

        return machineId;
    }
}
