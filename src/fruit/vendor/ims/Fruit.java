package fruit.vendor.ims;

//check to see if any changes is needed 
public class Fruit {

    private String name;
    private int quantity;
    private double price;
    private double averageWeight;

    // Constructor for initializing common attributes
    public Fruit(String name, int quantity, double price, double averageWeight) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.averageWeight = averageWeight;
    }

    public void displayDescription() {
        System.out.println("\n" + getName() + ":");
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Price per unit: $" + getPrice());
        System.out.println("Average Unit Weight: " + getAverageWeight() + " grams\n");
    }

    // Method to add quantity of fruits
    public void addQuantity(int addedQuantity) {
        this.setQuantity(this.getQuantity() + addedQuantity);
    }

    // Method to deduct quantity of fruits
    public void deductQuantity(int deductedQuantity) {
        if (deductedQuantity <= this.getQuantity()) {
            this.setQuantity(this.getQuantity() - deductedQuantity);
        } else {
            System.out.println("Error: Cannot deduct more than current quantity.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }
}
