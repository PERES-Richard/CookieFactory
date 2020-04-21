Feature: Divide an order to packs

  Scenario:
    Given A store in "Hidafurukawa"

    When a large order is made of "10" "mnms" and "2" "Beer"

    Then the orders are devided to a "GRAND" pack and Drink_in_pack" pack