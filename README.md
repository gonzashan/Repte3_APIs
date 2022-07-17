# Repte3_APIs

What we need to develop is a small API that allows them to execute various actions, specifically the login view and the singup view.


## Objectives to be implemented (User stories):

- API must be available at the port: 3001

- Login path (has username and password as parameters)

- Sign-up path (has the parameters that can be seen in the Register page view of the figma (seen at the start of the challenge).

- The signup path must save users in a json file and encrypt the password so as not to save sensitive data.

- When saving a new user, check that neither the email nor the username is repeated.

- You need to check that the email is in the correct format.

- When creating them you need to save the creation date.


# UML

![UML image](https://github.com/gonzashan/Repte3_APIs/blob/808b5f541833855aff0a45984eb50229dd677a4a/repte4.jpg)


- The password must be saved: * Minimum 6 characters. * Use at least one capital letter. * Use at least a lowercase letter. * Contain some special character:. | , | * | + | -

- A superadmin path must be generated that allows all registered users to be reached but does not display sensitive data.

- There must be a super admin path to get the total number of registered users.

- The only user who can access the super admin paths is the one created with the credentials:

- Test with Postman

# Using Postman
First: Type http://localhost:3001/login in Http box. 
Second: Signup Post Action: Go to Import -> Paste raw text and paste the text below, then click on Import and press Post.

      curl --location --request POST 'http://localhost:3001/signup' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "fullName":"Superadmin",
        "userName":"superadmin",
        "password":"SuperAdmin10+",
        "email":"superadmin@superadmin.com"
    }'
    
First: Type http://localhost:3001/login in Http box. 
Second: Login Post Action: Go to Import -> Paste raw text and paste the text below, then click on Import and press Post.


      curl --location --request POST 'http://localhost:3001/signup' \
      --header 'Content-Type: application/json' \
      --data-raw '{
          "fullName": "El Craken",
          "userName":"TomJones",
          "password":"loFg*+.-d,1",
          "email":"musicisintheair@fender.com"
      }'

First: Type http://localhost:3001/users in http box.
Second: User list Get Action: Go to Headers and at the VALUE box paste: Bearer [token generated when user is signup].
Third: Then press Get on Postman.

First: Type http://localhost:3001/users/count in http box.
Second: Count users Get Action: Go to Headers and at the VALUE box paste: Bearer [token generated when user is signup]. 
Third: Then press Get on Postman.



I do apologize for the inconvenience the previous incorrect grammar may have caused and my delay.

Cheers, Gonzalo SM
