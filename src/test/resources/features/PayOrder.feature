Feature: Request payement

  Background:

    Given A  "Tokyo" store
    And A client called "Amine" that uses the email "amine@gmail.com"
    And the client has an order

  Scenario:
    When The order is done
    Then The client is asked to pay
    And after paying the sate of the order is now "WAITINGFORPICKUP"

