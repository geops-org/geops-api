# geops-api

## Campaign API contract update

`POST /api/v1/campaigns`

- `201 Created`: campaign created successfully.
- `403 Forbidden`: requester role is not allowed to create campaigns.
- `422 Unprocessable Entity`: referenced user does not exist.
