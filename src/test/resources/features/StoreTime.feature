Feature: Store feature

  Scenario: 01 - The store manager changes the opening time with a valid time

    Given A store that opens at "8" and closes at "17", has a tax of "2"% and is located on "23 Rue de Robert Latouch , Nice"
    When The store manager decides to change the opening time to "9"
    Then The store now will show that it opens at "9" instead of "8" on the interface


  Scenario: 02 - The store manager changes the closing time with a valid time

    Given A store that opens at "8" and closes at "17", has a tax rate of "2"% and is located on "24 Rue de Robert Latouch , Nice"
    When The store manager decides to change the closing time to "16"
    Then The store now will show that it closes at "16" instead of "17" on the interface


  Scenario: 03 - The store manager changes the opening time with an invalid time

    Given A store that usually opens at "8" and closes at "17", has a tax of "2"% and is located on "23 Rue de Robert Latouch , Nice"
    When The store manager decides to change the store's opening time to "18"
    Then He gets the error message "Cannot change to the new opening time because it's after the closing time" and the opening time stays "8"


  Scenario: 04 - The store manager changes the closing time with an invalid time

    Given A store opens at "8" and closes at "17", has a tax of "2"% and is located on "23 Rue de Robert Latouch , Nice"
    When The store manager decides to change the store's closing time to "7"
    Then He gets the error message "Cannot change to the new closing time because it's before the opening time" and the closing time stays "17"