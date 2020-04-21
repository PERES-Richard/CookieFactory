Feature: Pickup Order

  Background:

    Given A store is located in "Tokyo"
    And A client "Amine" that uses the email "amine@gmail.com"
    And the client has an order that is ready to pick up

  Scenario: A client successfully picks up his order


    When the client makes the payment and picks the order up

    Then the order is now finished and it's state is "DELIVERED"
