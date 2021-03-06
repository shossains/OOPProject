# API List

*For all your API needs!*

## Basics
All requests are defined by a *type*, to denote what kind of request it is. Dont even think of sending one without it.

All APIs need authentication, give each request a *username* and *password*.
Armed with this knowledge, we can give our first example request, which sends a test request to our server:

{"type" : "TestRequest", "username" : "user", "password" : "hunter2", "isTest": true}

If you add an attribute that is incorrectly named, or irrelevant, the server will simiply ignore it.

### Server error
If the server experiences an error, with the database, query, invalid data or otherwise, it will return two attributes:
* *error*: Boolean, true if its an error. Does not need to be passed with other responses, just for errors.
* *reason*: String, the reason for the error. 

## VegMeal

To add a vegetarian meal to the person's account. Returns the amount of points.
It will always return the amount of points, whether or not you have added a vegetarian meal, so you get immediate feedback.

### Request:
* *type*: VegMeal
* *addMeal*: Boolean, if you want to indicate that you have added a meal, use true, if you just want to retrieve information, give it false.

### Response
* *points*: Integer, The current total of points the user has.
* *added*, Boolean, confirms that the meal has been added. Doesnt get returned if you set addMeal to false.

### Example
{"type" : "VegMeal", "username" : "mike", "password" : "hunter2", "addMeal", "false"}

Response:

{"points" : "42"}

## Temperature Adjustment

To add the temperature adjustment to the person's account. Returns the amount of points.
For now it will return only when the input is valid (an integer).

### Request:
* *type*: TempAdj
* *addTemp*: Boolean, if you want to indicate that you have added a temperature, use true.
* *temp*: Integer, the temperature that was put in by the user.

### Response:
* *points*: Integer, the current total of points the user has.

### Example
{"type" : "TempAdj", "username" : "mike", "password" : "hunter2", "addTemp", "true"}

Response:

{"points" : "200"}
