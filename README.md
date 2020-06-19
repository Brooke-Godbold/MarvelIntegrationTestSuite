# Marvel API - v1

## Environment Setup

Users will need to Register a User at Marvel, in order to receive a Public and Private API Key:
https://developer.marvel.com/

Once this has been done, the User will need to follow the instructions to obtain their Public and Private API Keys; these are required for authorization when making API Requests.

Once the User has registered, and has obtained their Public and Private API Keys, these need to be added as Environment Variables so that the Tests can pick them up; In Intellij, this can be done via the 'Edit Configurations' under 'Run'. The Variables should be set as PUBLIC_API_KEY and PRIVATE_API_KEY. For any other IDE, see your IDE Documentation for Environment Variables.

If one wishes to run the Tests from the Command Line, the Environment Variables must be set in the POM File, under the Maven Surefire Plugin Properties section; ensure you do not save your own API Keys here if pushing changes.

Chrome Driver must also be installed to run the GetCharactersComicsTest. It will need to be added to a directory called 'chrome-driver', which should be located in the Project Root. Chrome Driver can be downloaded here: https://sites.google.com/a/chromium.org/chromedriver/downloads

## Running The Tests

The Tests can be run in two ways. The first is through the IDE; in Intellij, this can be done by many ways, such as pressing the Green Arrow next to the Test, or right clicking the Test in the Project Structure.

The second is via the Command Line; If you don't have Maven Installed, this needs to be installed on your machine, and the location set as an Environment Variable on your machine. In addition, you must be using a JDK (as opposed to JRE), and have your JAVA_HOME Environment Variable pointing to that location.

Once done, the tests can be run simply via the following command from the Project Root Directory:
```bash
mvn test
```
