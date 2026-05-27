# Plant Care API

## Domain Description
A REST API for tracking houseplants. Users can register their plants, log watering and fertilisation events, and see which plants need attention. Each plant has a recommended watering interval, and the system enforces a strict 24-hour rule between watering events to prevent overwatering.

## How to Run
```bash
./mvnw spring-boot:run
```
The application starts at http://localhost:8080

## API Key
Every request must include the following header:

X-API-Key: demo-key-2026

Missing or invalid key returns 401 Unauthorized.

## Swagger UI

http://localhost:8080/swagger-ui/index.html

Click the Authorize button, enter demo-key-2026, then you can test all endpoints.

## H2 Console

http://localhost:8080/h2-console


- JDBC URL: jdbc:h2:mem:plantcare
- User Name: sa
- Password: (leave empty)

## Business Invariant
A plant cannot be watered more than once in a 24-hour window. A second watering request within 24 hours of the last one is rejected with 409 Conflict.

This is enforced in WateringService.java by querying the most recent watering event for the plant and comparing its timestamp to the current time. If the difference is less than 24 hours, a BusinessRuleException is thrown which maps to HTTP 409.

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /plants | Register a new plant |
| GET | /plants | List all plants (filter by species/room, paginated) |
| GET | /plants/{id} | Get a plant by ID |
| PUT | /plants/{id} | Update a plant |
| DELETE | /plants/{id} | Soft-delete a plant |
| PATCH | /plants/{id}/room | Move plant to a different room |
| POST | /plants/{id}/waterings | Log a watering event (enforces 24h rule) |
| GET | /plants/{id}/waterings | Get watering history |
| POST | /plants/{id}/fertilisations | Log a fertilisation event |
| GET | /plants/{id}/fertilisations | Get fertilisation history |
| GET | /reports/needs-watering | Plants that need watering |
| GET | /reports/summary | Total plants and watering stats |

## Example curl Commands

### Create a plant
```bash
curl -X POST http://localhost:8080/plants \
  -H "X-API-Key: demo-key-2026" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monstera by the window",
    "species": "Monstera deliciosa",
    "room": "Living room",
    "recommendedDaysBetweenWater": 7
  }'
```

### Get all plants
```bash
curl http://localhost:8080/plants \
  -H "X-API-Key: demo-key-2026"
```

### Update a plant
```bash
curl -X PUT http://localhost:8080/plants/1 \
  -H "X-API-Key: demo-key-2026" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Big Monstera",
    "species": "Monstera deliciosa",
    "room": "Bedroom",
    "recommendedDaysBetweenWater": 5
  }'
```

### Water a plant
```bash
curl -X POST http://localhost:8080/plants/1/waterings \
  -H "X-API-Key: demo-key-2026" \
  -H "Content-Type: application/json" \
  -d '{"notes": "Watered thoroughly"}'
```

### Delete a plant
```bash
curl -X DELETE http://localhost:8080/plants/1 \
  -H "X-API-Key: demo-key-2026"
```

### Get plants that need watering
```bash
curl http://localhost:8080/reports/needs-watering \
  -H "X-API-Key: demo-key-2026"
```