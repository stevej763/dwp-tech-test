DWP Associate Java Software Engineer Code Test

1. Call the city/{city}/users endpoint to list all users living in London, and return the results
2. Call the /users endpoint. Filter the results to return all users living within 50 miles of London. For the purpose of
   the exercise the centre of London is defined by the following Lat/Long: 51.5074° N, 0.1277° W.

## User JSON returned from API

```
{
   "id": 135,
   "first_name": "Mechelle",
   "last_name": "Boam",
   "email": "mboam3q@thetimes.co.uk",
   "ip_address": "113.71.242.187",
   "latitude": -6.5115909,
   "longitude": 105.652983
}
```

## Guide

[Component test for test #1](src/test/java/integration/UsersInCityComponentTest.java)

[Component test for task #2]()