package com.udacity.vehicles.service;

import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {

    private final CarRepository repository;

    private PriceClient priceClient;
    private MapsClient mapsClient;

    /**
     * DONE: Maps and Pricing Web Clients injected. They are defined
     *   in `VehiclesApiApplication` as arguments and the connection is made in MapsClient (request building + response to class).
     */

    public CarService(CarRepository repository, PriceClient priceClient, MapsClient mapsClient) {
        this.repository = repository;
        this.priceClient = priceClient;
        this.mapsClient = mapsClient;
    }

    /**
     * Gathers a list of all vehicles
     * @return a list of all vehicles in the CarRepository
     */
    public List<Car> list() {
        return repository.findAll();
    }

    /**
     * Gets car information by ID (or throws exception if non-existent)
     * @param id the ID number of the car to gather information on
     * @return the requested car's information, including location and price
     */
    public Car findById(Long id) {
        /*
         * DONE: Find the car by ID from the `repository` if it exists. --> repository.findById(id); returns optional.
         *   If it does not exist, throw a CarNotFoundException --> if optional is empty
         */

        Optional<Car> optionalCar = repository.findById(id);
        Car car = optionalCar.orElseThrow(()-> new CarNotFoundException());


        /*
         * DONE: Use the Pricing Web client you create in `VehiclesApiApplication`
         *   to get the price based on the `id` input' --> priceClient.
         * DONE: Set the price of the car --> car.setPrice
         */

        car.setPrice(priceClient.getPrice(id));


        /*
         * DONE: Use the Maps Web client you create in `VehiclesApiApplication` --> mapsClient.
         *   to get the address for the vehicle. You should access the location
         *   from the car object and feed it to the Maps service. --> mapsClient.getAddress()
         * DONE: Set the location of the vehicle, including the address information --> car.setLocation

         */

        car.setLocation(mapsClient.getAddress(car.getLocation()));

        return car;
    }

    /**we
     * Either creates or updates a vehicle, based on prior existence of car
     * @param car A car object, which can be either new or existing
     * @return the new/updated car is stored in the repository
     */
    public Car save(Car car) {
        if (car.getId() != null) {
            return repository.findById(car.getId())
                    .map(carToBeUpdated -> {
                        carToBeUpdated.setDetails(car.getDetails());
                        carToBeUpdated.setLocation(car.getLocation());
                        carToBeUpdated.setCondition(car.getCondition());
                        return repository.save(carToBeUpdated);
                    }).orElseThrow(CarNotFoundException::new);
        }

        return repository.save(car);
    }

    /**
     * Deletes a given car by ID
     * @param id the ID number of the car to delete
     */
    public void delete(Long id) {
        /*
         * DONE: Find the car by ID from the `repository` if it exists.
         *   If it does not exist, throw a CarNotFoundException
         *
         */

        if (repository.findById(id).isPresent()) {

            /*
             * DONE: Delete the car from the repository.
             */
            repository.deleteById(id);
            System.out.println("Successfully deleted the car with id: " + id);
        }

        else{
            System.out.println("No car with id: " + id + " exists!");
            throw new CarNotFoundException();
        }


    }
}
