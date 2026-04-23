## Token Request Response Format

### Request

```http
POST /oauth2/token HTTP/1.1
Authorization: Basic b3JkZXItY2xpZW50Om9yZGVyLWNsaWVudC1zZWNyZXQ=
Content-Length: 42
Content-Type: application/x-www-form-urlencoded
Host: localhost:9001
User-Agent: HTTPie

grant_type=client_credentials&scope=openid
```

Note:

- We use Basic authentication here with username & password as the
client_id & client_secret respectively.
- In Basic authentication, the value of Authorization header is
constructed as follows:
	- "Basic " + base64_encode(username + ":" + password)


### Sample Response

```json
{
  "access_token": "eyJraWQiOiJiYTdiNTRkOS1kOGEyLTRjYjUtYTQxMC1hNmVlMmYxY2M2YmMiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJvcmRlci1jbGllbnQiLCJhdWQiOiJvcmRlci1jbGllbnQiLCJuYmYiOjE3NzY5MjQ1NzMsInNjb3BlIjpbIm9wZW5pZCJdLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjkwMDEiLCJleHAiOjE3NzY5MjQ4NzMsImlhdCI6MTc3NjkyNDU3MywianRpIjoiNjFjNjEyZWYtY2FiNS00ZmJjLWE0NzEtNzIwZTI4OTQxZGEwIn0.cBsQ5SbtbpoHlFUzJnISUferRvcY5dm7M9lLmpL8PdVUOeUnGdsSYcUPQsCDyxtqVZxn1KEbJdB9ty7Bu26AEVCpv5pR_X4C-k7vxxbFvfMlT7R6NJba6OayGNO1SJ7alGNG8UAAFuxZGps925W_Ic2LKpHFy-V1SRRYFgYdnc1tMCjlXJ_nNCvq43R7kUI_9dJ5RZS-b0p4JaPyEiDibZdMUs1osAnR3DnwKx1uBs6yvXvYEVKiAW14y_s6Kx_cA-I2WCnLN0vIdaxJR497GCdSvY0TTCQP2Nbfr77ZlQwDyq7aYtxt81QU1_R_LDzvgRBIyjJVSgwjw0KaNguVSA",
  "scope": "openid",
  "token_type": "Bearer",
  "expires_in": 300
}
```

