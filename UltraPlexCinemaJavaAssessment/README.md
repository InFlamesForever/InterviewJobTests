<h1>Running the project or tests</h1>
Docker is required to run the project and to run the integration test.
<h3>Start the Postgres DB:</h3>

1. Ensure that Docker is installed and running on your system.
2. Make sure the script is executable, eg:

```bash
chmod +x run_postgres.sh
```

3. Run the script:

```bash
./run_postgres.sh
```

<h3>Build the gradle project</h3>
```bash
./gradlew clean build
```

<h3> Run the spring app:</h3>
TODO: rename project
```bash
java -jar build/libs/untitled-1.0.0.jar 
```

<h3> Simple example curl request </h3>

```bash
curl -X GET localhost:8080/cinema/all
```

<h1>Thoughts and explanations for decisions</h1>
All in all this is quite a large example project, so I've cut a lot of corners to save time,
wherever I have done so I've tried to write a comment to say as such.

To save time I have:
1. mostly directly returned entities straight from the database.
For the most part this isn't the best idea as there is usually more information
than is necessary for a front end system and data that should be hidden from users.
2. Left all transaction management to default spring and the database,
this is fine until there are enough requests for things to start falling down.
3. Left a few duplicated fields to make the queries easier.
The database isn't completely relational as some fields could be removed,
however in doing so I'd no longer be able to use the nice JPA functionality
of turning method names directly into SQL queries.
4. I didn't bother with the full interface/impl structure because no one will be extending this project,
and no one will ever need to implement multiple versions of anything in it either.
5. I've written a couple of example unit tests, more would be needed in a proper system. No unit tests were written
on the rest controller, I covered that in an integration test, ideally a unit test to cover it would be written as well.
6. One integration test written to show what's possible.


