# RouteOne Coding Exercise

Tom Halliley <jthalliley@gmail.com>
2019-04-10


## How to Build and Run

```
   mvn clean package
   java -jar target/jth-computer-store-0.0.1-SNAPSHOT.jar sample-inventory.csv sample-order.txt
```


## Assumptions / Choices

1. The entire CSV file can fit into memory.
2. Duplicate name + category records from the inventory CSV file are discarded; only the first such is accepted.
3. Any prices found in the inventory that are have extra decimal places are rounded "HALF_EVEN".
4. getOrderedItems will return duplicates.

## Comments

1. I think getOrderedItems should have returned a list of (quantity, item name).
