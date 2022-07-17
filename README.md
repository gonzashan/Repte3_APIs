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
