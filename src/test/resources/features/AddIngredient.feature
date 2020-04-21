Feature: Account feature


  Scenario: Add a new ingredient to the stock

    Given A store located in "Tokyo" .

    When the store adds a new ingredient to its stock called "Oatmeal" that costs "5.0" euros

    Then the stock now contains the ingredient "Oatmeal" .


  Scenario: The store wants to increase it's stocks of a product

    Given Another store located in "Kyoto"

    And has a stock of "Chili"

    When The owner adds "10" "Chili" to the stock

    Then The store has now "10" more "Chili" in its stock


  Scenario: Add a new drink to the stock

    Given A store located "Tokyo" .

    When the store adds a new drink to its stock called "Pepsi" that costs "1.8" euros

    Then the stock now contains the drink "Pepsi" .


  Scenario: The store wants to increase it's stocks of a drink

    Given Another store is in "Kyoto"

    And has a stock of the drink "Coke" that costs "2" euros

    When The owner adds "10" drinks to his stock

    Then The store has now "10" more "Coke" drink in the stock
