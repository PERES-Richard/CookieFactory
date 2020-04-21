Feature: Account feature


  Scenario: 01 - Create a Registered account with a valid email

    Given The user "Amine Legrifi"'s first time visiting the app and decides to use a visitor account with a new email : "aminelegrifi@gmail.com"

    When The customer adds his "password" and clicks on register

    Then The customer has a valid "REGISTERED" account


  Scenario: 02 - A registered client wants to re_register himself

    Given The user "Amine Legrifi" is already registered with the email : "aminelegrifi@gmail.com" and with the password : "banana"

    When The customer tries to register himself with a password : "password" and clicks on register

    Then The customer gets an error message saying "Cannot register because you are already a registered" but he is still a "REGISTERED" client


  Scenario: 03 - A registered client wants to adhere to the membership program

    Given The user "Amine Legrifi" is already registered with the email :"aminelegrifi@gmail.com" and with password : "banana"

    When The customer tries to adhere himself to the membership program

    Then The customer's account is now a valid "MEMBER" account


  Scenario: 04 - A Member client wants to adhere to the membership program

    Given The user "Amine Legrifi" is already registered with the email :"aminelegrifi@gmail.com" and with password : "banana" and adrehent to the Membership program

    When The adherent tries to adhere himself to the membership program

    Then The customer gets an error message saying "Cannot adhere because you are already an adherent" but he stays a "MEMBER" client


  Scenario: 05 - A Visitor client wants to adhere to the membership program

    Given The user "Amine Legrifi"'s first time visiting the app and is using a visitor account with a his email : "aminelegrifi@gmail.com"

    When The user tries to adhere to the membership program

    Then The user receives an error message saying "Cannot adhere because you are not registered" so he stays as an "ANONYME" client


  Scenario: 06 - A Visitor client wants to unadhere from the membership program

    Given The user "Amine Legrifi"'s first time visiting the app and is currently using a visitor account with a his email : "aminelegrifi@gmail.com"

    When The user wants to unadhere himself from the membership program

    Then The user gets the error message "Cannot unadhere because you are not a Member (you are ANONYME)" then he stays as an "ANONYME" client


  Scenario: 07 - A Registered client wants to unadhere from the membership program

    Given The user "Amine Legrifi" is already registered with the email : "aminelegrifi@gmail.com" and with password : "banana"

    When The registered user wants to unadhere himself from the membership program

    Then The user gets the error saying "Cannot unadhere because you are not a Member (you are REGISTERED)" then he stays as a "REGISTERED" client


  Scenario: 08 - A Member client wants to unadhere from the membership program

    Given The user "Amine Legrifi" is already registered with the email :"aminelegrifi@gmail.com" and with password : "banana" and belongs to the Membership program

    When The member user tries to unadhere himself from the membership program

    Then The user gets to unadhere himself from the membership program then he becomes a "REGISTERED" client
