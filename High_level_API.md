## Authentication 
/api/v1/auth
 
### Register
**Route:** POST \<Host\>/api/v1/auth/register

**Request Body: **
```json
{
  "fullName": "Jane Doe",
  "email": "janedoe_2025@depauw.edu",
  "password": "mySecurePassword123",
  "role": "STUDENT"
}
```
**Return:**
```json
{
  "status": "OK",
  "code": 201,  
  "userId": "d4e5f6a7-b8c9-d0e1-f2a3-b4c5d6e7f8a9",
  "message": "Registration successful. Please complete your profile."
}
```

### Login
**Route: POST \<Host\>/api/v1/auth/login**
```json
{
  "email": "johntiger_2026@depauw.edu",
  "password": "mySecurePassword123"
}
```
**Return:**
```json
{
  "status": "OK",
  "code": 200,  
  "userId": "d4e5f6a7-b8c9-d0e1-f2a3-b4c5d6e7f8a9",
  "message": "Registration successful. Please complete your profile."
}
```

### Logout
**Route: Post \<Host\>/api/v1/auth/logout**

**Return:**
```json
{
  "status": "OK",
  "code": 200,
  "message": "Log out successfully"
}
```

### Profile creation
**Route POST <Host>/api/v1/profile/me**

**Student**
```json

{
  "gradYear": 2026,
  "major": "Computer Science",
  "aboutMe": "A passionate student interested in software engineering and cloud technologies. Eager to connect with alumni in the field.",
  "careerInterest": "Backend Development, DevOps",
  "fiveYearImagination": "Working as a senior software engineer at a top tech company, leading a team.",
  "linkedinProfile": "https://www.linkedin.com/in/johntiger/"
}
```
**Alumni**
```json
{
  "gradYear": 2015,
  "major": "Economics",
  "aboutMe": "Experienced project manager helping students navigate their careers. Currently at Google.",
  "currentCompany": "Google",
  "jobTitle": "Technical Project Manager",
  "fieldOfExpertise": [
    "Project Management",
    "Cloud Computing",
    "Agile Methodologies"
  ],
  "willingnessToHelp": [
    "RESUME_REVIEWS",
    "COFFEE_CHAT"
  ],
  "linkedinProfile": "https://www.linkedin.com/in/janedoealumni/"
}
```


