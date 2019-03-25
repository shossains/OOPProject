# How to write server queries
### What is a server query?
A server query is a java class that lives under Server.queries and extends ServerQuery. It is code that is executed server-side, and is responsible for
processing the request into a JSON response. This means that all the database stuff and calculations need to be done in those classes.


### Overview
* Once the server recieves a JSON request, it needs to be processed. 
* The processing of the JSON is done automatically be the server using GSON.
* GSON takes raw JSON, and a java class as input, then maps matching JSON variables onto variables of the java class.
* It then returns a java class with the values from JSON variables initialized as java variables.
* Because of this approach, we need to create java classes for each kind of request listed the API list. 
* However, this allows us greater flexibility in the long run, as each of these separate classes will contain all the logic needed to process that JSON query.
* So, no more convoluted and bloated switch statements, and enigmatic function calls.

### Requirements for a server-side Query class
* Call your class something like xxxQuery to make everything look uniform
* Must extend ServerQuery, the superclass containing the username and password of the user requesting it.
* Must declare all variables it expects from json, ***as the correct type: if you expect a boolean from JSON, declare the corresponding variable as a boolean!***
* **Do not initialize the variables** (i.e. simply list them right after `public class xxx extends ServerQuery {` )
* These variables will be automatically initialized by the server - don't worry about that.
* Must contain a *runQuery()* method, which contains all the code that will execute the query on the server (i.e. all the database calls, calculations ,api calls)
* *runQuery()* will be called by the server **once**, and will expect it to do the actual logic of the query. No other processing is done by the server.
* This method must return a String in JSON format, which will then be returned to the client as the response. This is **the** response, so it will be passed back as is!
 
*Note that even after all this, I still need to add a line of server code to have the server send requests to your class. I'd rather do it myself, to minimize risk of 
accidentally breaking something, so instead take time thoroughly testing your class.*

**The rest is up to you, write as many other methods as you want,and make it as long as you want, as long as you satisfy the requirements above.**

# Example

For JSON string `"{'type':'TestRequest', 'extraData':'Irrelevant Data', 'isTest': true}"`

Note that the irrelevant `extraData``` variable is not processed since its not declared in the class.

```
package server.queries;

public class TestQuery extends ServerQuery {
    boolean isTest;

    /**
     * Test Query run, probably to be expanded in the future.
     *
     * @return Test String in JSON format containing the isTest variable.
     */
    public String runQuery() {

        return "{\"success\": \"who knows\", \"isTest\": "
                + Boolean.toString(isTest) + ", \"username\":\"alexshulzycki\"}";
    }
}
```

Returns `"{"success": "who knows", "isTest": true, "username":"alexshulzycki"}`

**IF YOU ARE GOING TO CONSTRUCT A JSON STRING MANUALLY LIKE SO, MAKE SURE IT IS VALID JSON**

# Testing
If you dont do this, Andy will kill you. And if he doesnt, I will.
You need to test both the actual logic/calculations, and sending/receiving from the server.

### Writing a test for a ServerQuery query
You essentially need to do these things: 
* Set up your database test row
* Test the response by executing your Gson-parsed query
* Test bad JSON data, and other ways in which you could break your class
* Test individual functions - Preferrably do this, until the database-query workflow is optimized further ideally before next week.

### Setting up database row
Usually just zero out the data in the user's test row. This is just an example, and is not the final code that will be used in VegMealQuery. Look at the ER diagram in this folder, and ask Shaan if you have any questions!
```
/**
     * initializes variables, clean up test entry in users
     */
    @BeforeClass
    public static void init(){
        vc = new VegController();

        //set the score to 0 on the test row
        String[] queries = new String[1];
        queries[0] = "UPDATE points \n SET points = 0\n WHERE username = '"
                + testUser +"'";
        Query.query(queries);

    }
```



### Testing Gson parsing
Set up the query class you wrote as a Gson class. You can copy the third line and then just import when it complains. The function *fromJson()* takes in your JSON string,
and the class that it is supposed to be parsed to, returning an instance of that class.

```
/**
     * Tests whether the database returns the correct json via the VegMealQuery class.
     */
    @Test
    public void vegMealQueryJsonResponse(){
        String testString = "{'type':'VegMeal','username': '"+ testUser +"', 'addMeal': true }";
        VegMealQuery request = new GsonBuilder().create().fromJson(testString, VegMealQuery.class);
        Assert.assertEquals("{\"points\" : 50}", request.runQuery());
    }
```

### Testing individual functions
Parse the JSON through Gson and then just test your individual functions by calling the new object. 



