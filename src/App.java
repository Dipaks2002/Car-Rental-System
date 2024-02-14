import java.util.*;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay, boolean isAvailable) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = isAvailable;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int rentalDays;

    public Rental(Car car, Customer customer, int rentalDays) {
        this.car = car;
        this.customer = customer;
        this.rentalDays = rentalDays;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRentalDays() {
        return rentalDays;
    }
}

class CarRentalSystem {
    private ArrayList<Car> cars;
    private ArrayList<Customer> customers;
    private ArrayList<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<Car>();
        customers = new ArrayList<Customer>();
        rentals = new ArrayList<Rental>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int rentalDays) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, rentalDays));
            System.out.println("Car rented successfully");
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();

        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
            System.out.println("Car returned successfully");
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void Menu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("========= Car Rental System ========");
        System.out.println("1. Rent a Car");
        System.out.println("2. Return a Car");
        System.out.println("3. Exit");
        System.out.println("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.println("Rent A Car");
            System.out.println("Enter Your Name: ");
            String customerName = scanner.nextLine();

            System.out.println("\nAvailable Cars: ");
            for (Car car : cars) {
                if (car.isAvailable()) {
                    System.out.println(car.getCarId() + " " + car.getBrand() + " " + car.getModel());
                }
            }

            System.out.println("Enter the car ID You want to rent : ");
            String carId = scanner.nextLine();

            System.out.println("Enter the Number of Days for Rental: ");
            int rentalDays = scanner.nextInt();
            scanner.nextLine();

            Customer newCustomer = new Customer("Cus" + (customers.size() + 1), customerName);
            addCustomer(newCustomer);

            Car selectCar = null;
            for (Car car : cars) {
                if (car.getCarId().equals(carId) && car.isAvailable()) {
                    selectCar = car;
                    break;
                }
            }

            if (selectCar != null) {
                double totalPrice = selectCar.calculatePrice(rentalDays);
                System.out.println("\nRental Information\n");
                System.out.println("Customer ID: " + newCustomer.getCustomerId());
                System.out.println("Customer Name: " + newCustomer.getName());
                System.out.println("Car: " + selectCar.getBrand() + " " + selectCar.getModel());
                System.out.println("Rental Days: " + rentalDays);
                System.out.println("Total Price : " + totalPrice);

                System.out.println("\nConfirmed Rental Y/N ");
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("Y")) {
                    rentCar(selectCar, newCustomer, rentalDays);
                } else {
                    System.out.println("Rental Canceled");
                }
            } else {
                System.out.println("Invalid car Selection or car not available for rent");
            }
        } else if (choice == 2) {
            System.out.println("\nReturn a Car");
            System.out.println("Enter the Car Id You want to return: ");
            String carId = scanner.nextLine();

            Car carToReturn = null;
            for (Car car : cars) {
                if (car.getCarId().equals(carId) && !car.isAvailable()) {
                    carToReturn = car;
                    break;
                }
            }

            if (carToReturn != null) {
                Customer customer = null;
                for (Rental rental : rentals) {
                    if (rental.getCar() == carToReturn) {
                        customer = rental.getCustomer();
                        break;
                    }
                }

                if (customer != null) {
                    returnCar(carToReturn);
                    System.out.println("Car Returned Successfully by " + customer.getName());
                } else {
                    System.out.println("Car was not rented or rental information is missing");
                }
            } else {
                System.out.println("Invalid Car ID or Car is not returned");
            }
        } else if (choice == 3) {
            // Exit
        } else {
            System.out.println("Invalid Choice, please enter a valid option");
        }
    }
}

public class App {
    public static void main(String[] args) {
        CarRentalSystem carRentalSystem = new CarRentalSystem();

        // Initialize cars
        carRentalSystem.addCar(new Car("001", "Toyota", "Camry", 50.0, true));
        carRentalSystem.addCar(new Car("002", "Honda", "Accord", 60.0, true));
        carRentalSystem.addCar(new Car("003", "Ford", "Focus", 40.0, true));

        // Start the car rental system
        carRentalSystem.Menu();
    }
}

