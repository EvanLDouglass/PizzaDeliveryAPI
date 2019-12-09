# Dijkstra's Pizza API

*Created By*
* Josh Baron, <baron.jos@husky.neu.edu>, GitHub: [jsbaron](https://github.com/jsbaron)
* Evan Douglass, <douglass.ev@husky.neu.edu>, GitHub: [EvanLDouglass](https://github.com/EvanLDouglass)
* Eric Egan, <egan.er@husky.neu.edu>, GitHub: [okapetanios](https://github.com/okapetanios)

## Use the API
To explore our API and view interactive [Swagger](https://swagger.io/) documentation, visit <https://dijkstras.herokuapp.com>. This will also be the root end point for any API calls in client programs. Please view the official API documentation at the given URL for the most up-to-date information on specific end points available.

## Contribute to or Modify the Code

You can modify or contribute to this project by following the steps below:

1. [Clone the repository](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/cloning-a-repository) to a location on your personal machine.
2. To run this project locally, you will need MongoDB. Download and install [MongoDB](https://www.mongodb.com/download-center/community) if you do not already have it. More information on how to install MongoDB can be found by reading the Mondo [documentation](https://docs.mongodb.com/manual/administration/install-community/).
3. Once Mongo is installed and running, you will need to set up two environment variables to connect to the database. Instructions on how to do this will vary between operating system and editor.
    * MONGODB_URI=mongodb://localhost
    * MONGODB_DBNAME=\<any-valid-db-name\>

This should be enough to run the server locally. If you saved the environment variables above in your terminal environment (such as in a .bashrc file), run the command

    mvn spring-boot:run

If you saved the variables in your editor environment (i.e. the IntelliJ Run/Debug Configurations), run the main application using the appropriate process for your set-up.

Once the server is up and running, visit <http://localhost:8080/> to ensure everything is working correctly. After a few moments you should see our documentation appear in your browser via the [Swagger UI](https://swagger.io/tools/swagger-ui/).

***Note to IntelliJ Users***

This project uses the lombok library in some of the model classes to cut down on boiler plate code. IntelliJ requires that you enable annotation processing to prevent it showing errors from unimplemented methods. You can find this in Settings->Build, Execution, Deployment->Compiler->Annotation Processors - Or simply search for "enable annotation processing" in the settings search bar.

## Testing

We use maven as our build tool for this project. Once you have your dev environment set up, you can run tests on the terminal with

    mvn verify

or another maven test command. `mvn validate` and `mvn test` also seem to work.

We also use Jacoco to measure our test coverage. The above command should run the Jacoco report, but if you prefer to only run the report use the following command:

    mvn jacoco:report

Open the report by finding the jacoco HTML under project-root/target/site/jacoco/index.html, then open in your browser.

The following commands may come in handy if Jacoco doesn't seem to be working.

    mvn clean install
    jacoco:prepare-agent jacoco:report

## Creating a Heroku App

Should you want to host this API yourself, the easiest way is with Heroku.

Heroku has extensive documentation on how to create apps on their platform. Start [here](https://devcenter.heroku.com/) to learn more.

Once your app is set-up you will have to connect it with the Heroku managed [mLab MongoDB](https://elements.heroku.com/addons/mongolab) add-on. Once installed this will automatically set-up your config (i.e. environment) variable for your mongodb uri. Be sure to confirm that the variable is MONGODB_URI. You can find this under the settings tab in your app's dashboard. You will also have to create the the MONGODB_DBNAME variable using the database name that mLab gives you. You can retrieve this by visiting the mLab environment. From the terminal (in the project's root directory):

    heroku addons:open mongolab

Visit your app's Heroku URI to confirm that the code is working.

## Aditional Links

* [API Design Document](https://docs.google.com/document/d/1VT129qyGoOdomH7Tf5sAYlCeG8k6NaDW2la20wl8cr0/edit?usp=sharing)
* [Front-End Design Document](https://docs.google.com/document/d/1lrQyhTi0gaAmdOinhy5wW4dZHJS67hM00_NBbN3XDJw/edit?usp=sharing)
* [Front-End Site](https://dijkstras.glitch.me/)
   * Note that it may take several minutes to wake up if the app went to sleep
* [Final Report](https://docs.google.com/document/d/1cCOTvZzgRdxYNTtBPZxlpfJgtCa6RhCOX9BkwrWvoPg/edit?usp=sharing)
* [Final Video Presentation](https://www.youtube.com/watch?v=HArcqHPqX6o&feature=youtu.be)
* [Trello Board](https://trello.com/b/SfyH7Dce/dijkstras-pizza-scrum-board)

---

That should be it!
