Feature: Discount

  Background:
    Given a store opens at "9:00" and closes at "19:00" every days, has a tax of "20"% and is located on "Hidafurukawa"
    And the store on "Hidafurukawa" sell one recipe of cookie
    And a user named "John" who use "john.carpenter@mail.com" as email address

  Scenario: A member customer make an order after ordering 30 cookies
    Given "John" is an adherent of the Loyalty Program
    And "John" have already ordered "30" cookies before
    When "John" make a new order
    Then "John" have a reduction about "10"% on his order

  Scenario: A member customer make an order after ordering 25 cookies
    Given "John" is an adherent of the Loyalty Program
    And "John" have already ordered "25" cookies before
    When "John" make a new order
    Then "John" have no reduction on his order

  Scenario: A customer make an order of more than 100 cookies with an EVENT coupon
    When "John" make a new order of "100" cookies and give the code "EVENT"
    Then "John" have a reduction about "10"% on his order

  Scenario: A customer make an order of more than 100 cookies with an EVENT coupon
    When "John" make a new order of "50" cookies and give the code "EVENT"
    Then "John" have no reduction on his order

  Scenario: A customer with a company code
    When "John" make a new order and give the code "CE_AIRBUS"
    Then "John" have a reduction about "5"% on his order

#  Scenario: A customer with a company coupon
#    Given "John" is a registered member about "3" years
#    When "John" make a new order
#    Then "John" have a reduction on his order

#  Scenario: A customer make an order for the same day at the end of the day
#    Given an user named "John" who use "john.carpenter@mail.com" as email address
#    When "John" make a new order at the end of the opening time of "MONDAY"
#    Then "John" have a reduction about "30"% on his order