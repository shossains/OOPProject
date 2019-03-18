# How to write server queries
### Overview
* Once the server recieves a JSON request, it needs to be processed. 
* The processing of the JSON is done automatically be the server using GSON.
* GSON takes raw JSON, and a java class as input, then maps matching JSON variables onto variables of the java class.
* It then returns a java class with the values from JSON variables initialized as java variables.
* Because of this approach, we need to create classes which match those JSON variables for each kind of request, as per the API list. 
* However, this allows us greater flexibility in the long run, as each of these separate classes will contain all the logic needed to process that JSON query.
* So, no more convoluted and bloated switch statements, and enigmatic function calls.

### Requirements for a server-side Query class
* Call your class something like xxxQuery to make everything look uniform
* Must extend ServerQuery, the superclass containing the username and password of the user requesting it.
* Must declare all variables it expects from json, ***as the correct type: if you expect a boolean fro JSON, declare the corresponding variable as a boolean!***
* Do not initialize the variables (i.e. simply list them right after `public class xxx extends ServerQuery {` )
* Must contain a *runQuery()* method, which contains all the code that will execute the query on the server (i.e. all the database calls, calculations ,api calls)
* *runQuery()* will be called by the server **once**, and will expect it to do the actual logic of the query. No other processing is done by the server.
* This method must return a String in JSON format, which will then be returned to the client as the response. This is **the** response, it will be passed back as is!
 

**The rest is up to you, write as many other methods as you want, make it as long as you want, as long as you satisfy the requirements above.**

# Example

For JSON string `"{'type':'TestRequest', 'extraData':'Irrelevant Data', 'isTest': true}"`

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

