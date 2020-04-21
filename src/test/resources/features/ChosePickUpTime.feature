Feature: Chose pickUp time

  Background:

    Given A "Tokyo" store that opens at "9" and closes at "17"
    And two clients called "Amine" that uses the email address "amine@gmail.com"
    And one called "ALI" that uses the email address "ali@gmail.com"

  Scenario: The customer chooses a correct time for order
    When The customer want to make an order in the "Tokyo" store, he decides to pick it up at "11"
    Then the order is correctly made without an issue
    And the order's state is "WAITINGFORPAYMENT"


  Scenario: The customer chooses a bad time for order
    When The customer wants to make an order in the "Tokyo" store, he decides to pick it up at "20"
    Then the order is not made
    And the system tells the customer that it "Cannot chose 20 as pickup time, store wan't be opened"

