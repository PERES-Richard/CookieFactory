Feature: Stock feature


  Scenario: Stock can handle order

    Given A store in "Tokyo".
    And A client that wants to make an order.

    When When the store checks its stock to prepare the order

    Then the stock has enough ingredients and completes the order


  Scenario: Stock cannot handle order

    Given A store that is in "Tokyo".
    And A client who wants to make an order.

    When When the store checks its stocks to prepare the order

    Then the stock is not enough to make the order

    And it shows an error "Cannot make this order because we are out of stock for those ingredients"