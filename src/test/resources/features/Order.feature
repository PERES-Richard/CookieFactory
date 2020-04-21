Feature: Make an order

  Background:
    Given An anonymous user called "Rick" that uses the email "rick@gmail.com"
    And a store located in "Hidafurukawa"
    And two cookies recepes called "Touchoko" and "MnMs"
    And a user called "Youssef" that goes with the email "myemail@gmail.com"


  Scenario: An anonym client makes an order

    When Add stock of 3 "Touchoko" and 5 "MnMs" at store "Hidafurukawa"
    And The client "Rick" makes an order of 3 "Touchoko" and 5 "MnMs" at store "Hidafurukawa"


    Then The client "Rick" has 1 additional order
    And the store "Hidafurukawa" has 1 more order to make
    And "Rick"'s last order is the same as last "Hidafurukawa"'s store order
    # Touchoko = 8.8 (8.8*3 = 26.4) & MnmS = 8.5 (8.5*5 = 42.5) => order ht = 68.9 ; TTC = 68.9 * 1.2 (tax store) =
    And the total price of this order is now "68.9" euros HT and "82.68" TTC for client "Rick"
    And "Hidafurukawa"'store stocks is the same as before the order for consomables needed in recipes "Touchoko" and "Mnms"


  Scenario: A registered client makes an order

    Given An registered user called "Rick" that uses the email "rick@gmail.com" and password "rickrick"
    And a store that's located in "Osaka"
    And One cookie recepe called "Touchoko"

    When The user makes an order of 6 "Touchoko"


    Then Now the client has one additional order
    And the total price of this order is now "52.8" € HT and "68.64" TTC
    And the store has one more order in pending to make
    And It's stock is was used


  Scenario: 3  :Make order of custom cookies

    When the user creates his custom recepe composed of "mnms", "peanut butter" and "chili" and is "Topped" and "Crunchy"

    Then the user makes the order of his custom made cookies
    And the store starts making the order





