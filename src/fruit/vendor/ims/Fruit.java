package fruit.vendor.ims;

public class Fruit {

    // attributes of the fruit class
    private String name;
    private int quantity;
    private double price;
    private double averageWeight;

    // constructor for initializing common attributes
    public Fruit(String name, int quantity, double price, double averageWeight) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.averageWeight = averageWeight;
    }

    // method to display the description of the fruit
    public void displayDescription() {
        System.out.println("\n" + getName() + ":");
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Price per unit: $" + getPrice());
        System.out.println("Average Unit Weight: " + getAverageWeight() + " grams\n");
    }

    // method to add quantity of fruits
    public void addQuantity(int addedQuantity) {
        this.setQuantity(this.getQuantity() + addedQuantity);
    }

    // method to deduct quantity of fruits
    public void deductQuantity(int deductedQuantity) {
        if (deductedQuantity <= this.getQuantity()) {
            this.setQuantity(this.getQuantity() - deductedQuantity);
        } else {
            System.out.println("Error: Cannot deduct more than current quantity.");
        }
    }

    // getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // getter and setter methods for quantity
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // getter and setter methods for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // getter and setter methods for average weight
    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }
}
