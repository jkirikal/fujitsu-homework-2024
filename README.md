## The H2 database
- **Username:** sa
- **Password:** password

## Fee Calculation API
This section outlines the API endpoint provided by the Fee Calculation service. This service calculates and retrieves delivery fees for various vehicle types in specified cities at given times.

### Calculate Delivery Fee

- **URL:** `/api`
- **Method:** `GET`
- **Description:** Calculates the delivery fee for a specific vehicle type in a given city at a specified date and time.
  If the date and time are left unspecified, the delivery fee is calculated based on the latest weather data. The business rules are also determined by the specified time.
- **Query Parameters:**
  - `City` (**required**): The city for which the delivery fee is calculated. Acceptable values are: `Tallinn`, `Tartu`, `Pärnu`.
  - `Vehicle type` (**required**): The type of vehicle for which the delivery fee is being calculated. Acceptable values are: `Car`, `Scooter`, `Bike`
  - `Datetime` (**optional**): The date and time for which the delivery fee is calculated. The format is: `dd-MM-yyyy-HH:mm`, for example `17-03-2024-13:00`
- **Response:**
  - **Success:**
    - **Code:** `200 OK`
    - **Content:** `{ fee: number }`
  - **Error:**
    - **Code:** `400 BAD REQUEST` If the vehicle type is not allowed under certain conditions.
    - **Content:** `{ error: "Forbidden vehicle type error: [detailed message]" }`
    - **Code:** `500 INTERNAL SERVER ERROR` If there is a server error or the fee cannot be calculated due to unexpected conditions.
    - **Content:** `{ error: "Error calculating fee" }`

## Managing Business Rules Through CRUD Operations
This API gives endpoints for clients to create, retrieve, update, and delete business rules.

### Create a New Business Rule

- **URL**: `/api/rules`
- **Method**: `POST`
- **Description**: Creates a new business rule with the data provided in the request body.
- **Request Body**:
    - `{
      "vehicle_car": 1.0,
      "vehicle_scooter": 0.5,
      "vehicle_bike": 0,
      "city_tallinn": 3.0,
      "city_tartu": 2.5,
      "city_parnu": 2.0,
      "atef_less_than_minus_10": 1,
      "atef_less_than_zero": 0.5,
      "wsef_more_than_20": -1,
      "wsef_more_than_10": 0.5,
      "wpef_glaze_hail_thunder": -1,
      "wpef_rain": 0.5,
      "wpef_snow_sleet": 1
      }` A JSON object containing the rules for fee calculation. <br> If the fee is marked as -1, the vehicle is forbidden for the specific weather condition
- **Response**:
    - **Success**: HTTP 200 OK with the created `BusinessRules` entity.
    - **Error**: HTTP 4XX/5XX depending on the error condition.

### Retrieve a Business Rule

- **URL**: `/api/rules/{id}`
- **Method**: `GET`
- **Description**: Retrieves a business rule by its unique ID.
- **URL Parameters**:
    - `id` (Long): The ID of the business rule to retrieve.
- **Response**:
    - **Success**: HTTP 200 OK with the `BusinessRules` entity.
    - **Error**: HTTP 404 Not Found if the rule does not exist.

### Update a Business Rule

- **URL**: `/api/rules/{id}`
- **Method**: `PUT`
- **Description**: Updates an existing business rule with the provided data.
- **URL Parameters**:
    - `id` (Long): The ID of the business rule to update.
- **Request Body**:
    - `{
      "vehicle_car": 1.0,
      "vehicle_scooter": 0.5,
      "vehicle_bike": 0,
      "city_tallinn": 3.0,
      "city_tartu": 2.5,
      "city_parnu": 2.0,
      "atef_less_than_minus_10": 1,
      "atef_less_than_zero": 0.5,
      "wsef_more_than_20": -1,
      "wsef_more_than_10": 0.5,
      "wpef_glaze_hail_thunder": -1,
      "wpef_rain": 0.5,
      "wpef_snow_sleet": 1
      }` A JSON object containing the rules for fee calculation. <br> If the fee is marked as -1, the vehicle is forbidden for the specific weather condition
- **Response**:
    - **Success**: HTTP 200 OK with the updated `BusinessRules` entity.
    - **Error**: HTTP 404 Not Found if the rule does not exist.

### Delete a Business Rule

- **URL**: `/api/rules/{id}`
- **Method**: `DELETE`
- **Description**: Deletes a business rule by its ID.
- **URL Parameters**:
    - `id` (Long): The ID of the business rule to delete.
- **Response**:
    - **Success**: HTTP 204 No Content.
    - **Error**: HTTP 404 Not Found if the rule does not exist.

### Retrieve All Business Rules

- **URL**: `/api/rules`
- **Method**: `GET`
- **Description**: Retrieves a list of all business rules.
- **Response**:
    - **Success**: HTTP 200 OK with an array of `BusinessRules` entities.
    - **Error**: HTTP 5XX for server errors.
