# Coding Challenge - Marvel API - v1

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

## Looking Forward

Two areas of the Tests need further work. For the second Test (checking that the UI and Back-End match), the Test does not currently pass. This appears due to an as-yet unknown issue in chromedriver, where the 'Variants' Checkbox which the User needs to press doesn't get rendered in chromedriver, but it does get rendered in regular usage.

The other area is Pagination of the Rest Response. The GET call only returns 20 results at a time, which means that the Test is not actually verifying every single result. I have attempted to get around this by implementing solutions suggested online, but the common suggestion of capturing the result as a ResponseEntity of Type PagedResource seems to result in No Content being received; this seems to be due to the Object Mapping not working, as I am attempting to map it to a basic Json Object, but without having access to the Engineers who implemented it, I can't know what Object Type I should be mapping it to (or if that Object Type would even available externally).

Both of these areas have a 'TODO' comment above them (in GetCharacterComicsTest and RestUtils respectively)

## Candidate Comments

Whilst these Tests are very valid, and lack of ownership of the System under Test limits how it can be Tested, in a real world scenario I would approach this differently.

For the first Scenario, it is concerned with checking that the Result for a Character always has some expected fields. Because I do not own the System, this has been done via a simple functional test via the API, and checking every single result. This is extremely costly though, with potentially thousands of results to test. The better solution would be a Contract Test, specifying that these fields need to be returned for every call.

For the second scenario, this is conflating Front-End and Back-End Testing together. I would split this out into two different Test Suites. In the Back-End Test Suite, I would check that given the Back-End has a certain number of Comics for that ID, then a GET call to that ID returns the expected number of Comics. And in the Front-End Suite, I would configure a Mocked Back-End to return a number of Comics, and assert that the Front-End renders the correct Number of Comics.