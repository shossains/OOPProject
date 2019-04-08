# API List

*For all your API needs!*
- [Basics](#basics)
- [Vegetarian Meal](#vegmeal)
- [Local Produce](#localproduce)
- [Bike ride](#bikeride)
- [Public Transport](#publictransport)
- [Temperature Adjustment](#temperature)
- [Solar Panels](#solar)



## Basics
All requests are defined by a *type*, to denote what kind of request it is. Dont even think of sending one without it.

All APIs need authentication, give each request a *username* and *password*.
Armed with this knowledge, we can give our first example request, which sends a test request to our server:

```
{"type" : "TestRequest", "username" : "user", "password" : "hunter2", "isTest": true}
```

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
* *addMeal*: `Boolean` if you want to indicate that you have added a meal, use true, if you just want to retrieve information, give it false.
* *mealType*: `String` Indicate wether the meal was 'vegetarian' or 'vegan'

### Response
* *points*: `Integer` The current total of points the user has.
* *added*: `Boolean` Confirms that the meal has been added. Doesn't get returned if you set addMeal to false.

### Example
```
{'type' : 'VegMeal', 'username' : 'Keizer', 'password' : 'Priester','addMeal': true, 'mealType' : 'vegan'}
```

Response:

```
{"points" : 42}
```

## LocalProduce

Add local produce to a client's account
It will always return the amount of points, whether or not you have added local produce, so you get immediate feedback.

### Request:
* *type*: LocalProduce
* *addLocal*:`Boolean` If you want to indicate that the client bought local produce, use true, if you just want to retrieve information, give it false.
* *weight*:`Integer` The amount of local produce the client has purchashed in grams 

### Response
* *points*: `Integer` The current total of points the user has.
* *added*: `Boolean` confirms that the meal has been added. Doesn't get returned if you set addLocal to false.

### Example
```
{'type' : 'LocalProduce', 'username' : 'Winne', 'password' : 'Feis', 'addLocal' : true, 'weight' : 90}
```

Response:
```
{"points" : 35}
```

## BikeRide

Add bike ride to a client's account
It will always return the amount of points, whether or not you have added a bike ride, so you get immediate feedback.

### Request:
* *type*: BikeRide
* *addBike*:`Boolean` If you want to indicate that the client cycled, use true, if you just want to retrieve information, give it false.
* *distance*:`Integer` The amount of kilometers the client has cycled. 

### Response
* *points*: `Integer` The current total of points the user has.
* *added*: `Boolean` confirms that the bike ride has been added. Doesn't get returned if you set addBike to false.

### Example
```
{'type' : 'BikeRide', 'username' : 'HenkieT', 'password' : 'Chivv', 'addBike' : true, 'distance' : 5}
```

Response:
```
{"points" : 39}
```

## Combined

Fetches the amount of points per category

### Request:
* *type*: Combined

### Response:
* *vegPoints*: `Integer` The current amount of points earned by eating vegetarian meals
* *locProdPoints*: `Integer` The current amount of points earned by eating local produce
* *bikePoints*: `Integer` The current amount of points earned by biking
* *pubTransPoints*: `Integer` The current amount of points earned by using public transport
* *tempPoints*: `Integer` The current amount of points earned by lowering the temperature
* *solarPoints*: `Integer` The current amount of points earned by using solar power

### Example
```
{'type' : 'Combined', 'username' : 'Hubble', 'password' : 'Supernova'}
```

Response:
```
{"vegPoints" : 55, "locProdPoints" : 63, "bikePoints" : 120, "pubTransPoints" : 37, "tempPoints" : 205, "solarPoints" : 0}
```

## Login
Used to check if the username and password combination is valid.

### Request
* type
* username
* password

### Response
Either
* `{'login' : true}` if the user/pass combo is valid
* `{'login' : false}` if the combo is invalid.

This request is only used at the beggining to verify the login input.
