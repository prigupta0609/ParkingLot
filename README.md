# Welcome to ParkingLot!

## Problem Statement
Design a parking lot which can hold upto 'n' cars at given point of time. Each slot is given number starting from 1 increasing with increasing distance from entry point.
When a car enters parking lot, a slot is assigned to it and ticket is issued to the driver. The slot assigned is nearest to the parking entrance.
The system should be able to provide following information:
    1. Registration numbers of all cars of a particular colour.
    2. Slot number in which a car with a given registration number is parked.
    3. Slot numbers of all slots where a car of a particular colour is parked.

## Considerations
1. There is only 1 level parking.
2. There is only 1 entry and 1 exit gate (that means it is single-threaded application).
3. There is only 1 type of vehicle - Car which occupies only 1 parking slot.
4. Every slot is of same size.
    
## Design
There are 2 major objects - Car (of type Vehicle which can be extended to any other Vehicle type), Parking Lot (formed by the 'n' number of parking slots).
Each slot is allocated one status - Available/Occupied. 

## Models
Vehicle (Interface)
    RegistrationNumber
    Color
    
Car - implements Vehicle

ParkingSlot
    SlotNumber
    SlotStatus
    
ParkingLot - Mapping of slot and car

## Supported operations
1. createParkingLot()           - initialize parking lot
2. park()                       - park car   
3. leaveCar()                   - vacant the slot
4. getStatus()                  - get status of parking lot
5. getRegistNumWithColor()      - get registration number of cars with given color
6. getSlotNumOfCarWithColor()   - get slot number of cars with given color
7. getSlotNumWithRegNum()       - get slot number of given registration number of car
8. resetParkingLot()            - reset the parking lot (used only in test cases)

## Code flow
Main class - Entry point of application. It can take input through file of through console
RequestValidator - Validates the incoming request. Verifies if number of arguments are correct or not for given operation.
ParkingLotServiceImpl - Main class that handles the operations and generate relevant output.
ParkingLotManagerImpl - Class that handles the operations on DAO layer. Interact with actual data for operations of parking lot.

## Instructions

**Pre-requisites**
Java 1.8
Maven 3.x

**Compile & build**
`bin/setup`
    OR
`mvn clean install` - on ParkingLot folder    
    
**Run**
`bin/parking_lot`
    OR
`java -jar target/parkinglot-1.0-SNAPSHOT bin/parking_lot_file_input.txt` - for file input
    OR
`java -jar target/parkinglot-1.0-SNAPSHOT` - for console command

**Test**
`bin/run_functional_tests`


