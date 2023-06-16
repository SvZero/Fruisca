# Cloud Computing Path

Designing and Planning Architecture, Designing API node.js and Deploying through App Engine for endpoint communication with Mobile Android Application through Google Cloud Platform. Database was created in Firebase. Machine Learning model is deployed at Cloud Run at Google Cloud Platform. We use databases on firebase with services in the form of authentication as a login and registration method. Then, login and registration data will be stored in the Realtime database. Profiles and scan results will be stored in storage, where Firebase will automatically connect to the Google Cloud platform.


## REST API Node.js 

REST API Node.js was created using express.js, and the database was integrated into Firebase. Then the API was deployed to App Engine for endpoint communication with Mobile Application.


## Deploying ML Model

The model will be deployed to an Cloud Run and Fast API will also be used as the endpoint for Mobile App to connect to the Machine Learning Model.


## Deploy Model Meachine Learning to Scan Method
The images will be uploaded to 
```
https://capstone-fruisca-taqxnmgvca-uc.a.run.app
```
And the response of this `POST` request will contain the predicted results of the fruits to be uploaded. The results of the prediction will be in the form of a classification that can distinguish between fresh fruit and rotten fruit and the percentage.


## Deploy Model API CLoud Computing 
this is link deploy our api to App Engine
```
https://capstone-fruisca.uc.r.appspot.com/
```
This is the testing mechanism in postman 
  * For "Register", use the url/register link, using the "POST" request.
  * For "Login", use the url/login link, using the "POST" request.
  * For "Display User", use the url/users link, using a "GET" request.
  * For "Inserting Profi Photo", use the link url/upload-profile-photo/uid , using the request "POST".
  * For "Display User Dashboard", use the url/profile/uid link, using the "GET" request.
  * For "Fruit Scan", use the url/upload link, using the "POST" request.
  * For "History Scan", use the url/history link, using the "GET" request.
  * For "Reset Password", use the url/reset-password link, using the "POST" request.
  * For "Logout", use the url/logout link, using the "POST" request.




