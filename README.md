# How to run
- import turingpos.sql
- run Turingpos.java
___
## Login
* admin,admin
* User5,user5

___
## Features
* User registration with user type, Cashier, System Admin.
* User login.
* Item Category setup, For example, food, drink, medicine etc.
* Item registration with item category, unit price.
* Stock handling.
* Sale process by Cashier.
    * Each sale voucher much contains item code, item name, quantity, price and date.
    * At the end of the voucher, total for all item must be included.
* Stock refill. (Will refill stock based on the purchase of item) Need to keep track which items are purchased and refill and their quantity. After refill, item quantity will be update.
* Report to check item and their quantity in stock.
* Sale report by Cashier by Date interval, for example How many item are sold and their total price amount within Jan 1 and Feb 2.
* Monthly total sale report

## Future Improvement
* to add error handling
* to check unique username and password
* to check unique item code
* to make feedback prompt