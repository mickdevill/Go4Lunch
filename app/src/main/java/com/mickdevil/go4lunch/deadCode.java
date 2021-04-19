package com.mickdevil.go4lunch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class deadCode {



                                                          /////////////////////////
                                                       //////////////////////////////                                   //
                                                     /////////////////////////////////                                  //
                                                  //////////////////////////////////////                                //
                                                  //                                  //                        ////////////////////
                                                  //                                  //                              //////
                                                  //                                  //                             ///////
                                                  //                                  //         ////////////////////////////////////////////////////
                                                  //RIP dear code, I will remember you//        //             FaceBook API & SDK                  //
                                                  //                                  //        ////////////////////////////////////////////////////
                                                  //                                  //                            //////
                                                  //                                  //                            //////
                                                  //                                  //                            //////
                                                  //                                  //                           //////
                                                  //                                  //                           //////
                                                  //                                  //                          //////
                                                  //                                  //                         ///////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////







//   public static void getPlaces() {

//       Log.d(TAG, "lat lng " + " " + userLat + " " + userLng);

//      String myURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="  + userLat+ "," + userLng +
//              "&radius=5000&type=restaurant&key=" +
//              "AIzaSyBjMRxsLtqdVWkeNxfNKA58SebE7c1XVnk";



//       String line = "";
//       String data = "";


//       try {
//           URL url = new URL(myURL);
//           HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//           //  urlConnection.setRequestMethod("GET");
//           InputStream inputStream = urlConnection.getInputStream();
//           BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

//           while(line !=null)

//           {

//               line = bufferedReader.readLine();
//               data += line;
//           }

//           Log.d(TAG, "getPlaces: " + data );

//       } catch (MalformedURLException e) {
//           e.printStackTrace();
//       } catch (IOException e) {
//           e.printStackTrace();
//       }
//   }

    //   johny = parseJohny(doItAgain(theFullResult.get(1)));
    //   Log.d(TAG, "the max nePage  " + johny);
    //   theFullResult.add(johny);

    //   for (int i = 0; i < 2 ; i++) {
    //      cheker = parseJohny(doItAgain(johny));
    //       johny = cheker;
    //       Log.d(TAG, "the max nePage  " + johny);
//
    //   }
    //  while (cheker != null) {

    //      int i = 0;

    //      cheker = parseJohny(doItAgain(theFullResult.get(i)));

    //      if (i != 0) {
    //          if (cheker.remove("next_page_token").toString() == theFullResult.get(i - 1).remove("next_page_token").toString()) {
    //              break;
    //          }
    //      }

    //      if (cheker != null) {

    //          theFullResult.add(cheker);

    //          Log.d(TAG, "my whileD cheker" + theFullResult.size());
    //      }
    //      i++;


    //  }


//      String line = "";
//      String data = "";


//      try {
//          URL url = new URL(myURL);
//          HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//          //  urlConnection.setRequestMethod("GET");
//          InputStream inputStream = urlConnection.getInputStream();
//          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

//          while (line != null) {

//              line = bufferedReader.readLine();
//              data += line;
//          }

//          JSONObject Johny = new JSONObject(data);

//          String nextPge = Johny.getString("next_page_token");


//          Log.d(TAG, "getPlaces: " + nextPge);


//      } catch (MalformedURLException e) {
//          e.printStackTrace();
//      } catch (IOException e) {
//          e.printStackTrace();
//      } catch (JSONException e) {
//          e.printStackTrace();
//      }


//      <!--
//    The API key for Google Maps-based APIs is defined as a string resource.
//            (See the file "res/values/google_maps_api.xml").
//    Note that the API key is linked to the encryption key used to sign the APK.
//    You need a different API key for each encryption key, including the release key that is used to
//    sign the APK for publishing.
//    You can define the keys for the debug and release targets in src/debug/ and src/release/.
//
//        <meta-data
//    android:name="com.google.android.geo.API_KEY"
//    android:value="@string/google_maps_key" />
//        <meta-data
//    android:name="com.google.android.gms.version"
//    android:value="@integer/google_play_services_version" />
//
//        <meta-data android:name="com.facebook.sdk.ApplicationId"
//    android:value="@string/facebook_app_id"/>
//        <activity android:name="com.facebook.FacebookActivity"
//    android:configChanges= "keyboard|keyboardHidden|screenLayout|screenSize|orientation"
//    android:label="@string/app_name" /> <activity android:name="com.facebook.CustomTabActivity"
//    android:exported="true"> <intent-filter>
//        <action android:name="android.intent.action.VIEW" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <category android:name="android.intent.category.BROWSABLE" />
//        <data android:scheme="@string/fb_login_protocol_scheme" />
//    </intent-filter> </activity>
//            -->
//
//
//        <!--
//  <meta-data
//    android:name="com.facebook.sdk.ApplicationId"
//    android:value="@string/facebook_app_id" />
//
//
//          <meta-data
//    android:name="com.google.android.geo.API_KEY"
//    android:value="${mapsApiKey}" />
//            -->

//        locationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//
//                if (location != null) {
//                    LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(me));
//                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(me, 16));
//
//userLat = location.getLatitude();
//userLng = location.getLongitude();
//
//G4LunchMain.handleMSG(1);
//
//
//
//                }
//            }
//
//        }).addOnFailureListener(getActivity(), new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(TAG, "god damn" + e.getMessage());
//            }
//        });

//  faceBookSignIN.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            fbOrGoogle = false;
//            faceBookSignIN.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//                @Override
//                public void onSuccess(LoginResult loginResult) {
//                    handleFacebookAccessToken(loginResult.getAccessToken());
//                }

//                @Override
//                public void onCancel() {
//                    Toast.makeText(getApplicationContext(), "ну все пиздец", Toast.LENGTH_LONG).show();
//                }

//                @Override
//                public void onError(FacebookException exception) {
//                    Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    });

    //fb stuf


//  private void fbSignIn() {

//      faceBookSignIN.setReadPermissions("public_profile", "email", "user_birthday", "user_friends");

//      faceBookSignIN.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//          @Override
//          public void onSuccess(LoginResult loginResult) {


//              handleFacebookAccessToken(loginResult.getAccessToken());


//              GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                  @Override
//                  public void onCompleted(JSONObject object, GraphResponse response) {
//                      //G4LunchMain.start(MainSigninActivity.this);
//                  }
//              });

//              Bundle parameters = new Bundle();
//              parameters.putString("fields", "id");
//              request.setParameters(parameters);
//              request.setAccessToken(loginResult.getAccessToken());
//              request.executeAsync();
//          }

//          @Override
//          public void onCancel() {

//          }

//          @Override
//          public void onError(FacebookException error) {

//          }
//      });
//  }


    //  String AUTH_TYPE = "rerequest";
    //  String EMAIL = "email";
    //  String USER_POSTS = "user_posts";




    //  faceBookSignIN.setAuthType(AUTH_TYPE);

    //faceBookSignIN.registerCallback(
    //        mCallbackManager,
    //        new FacebookCallback<LoginResult>() {
    //            @Override
    //            public void onSuccess(LoginResult loginResult) {
    //                setResult(RESULT_OK);
    //                fbOrGoogle = false;
    //                Intent intent = new Intent(MainSigninActivity.this, G4LunchMain.class);
    //                G4LunchMain.start(MainSigninActivity.this, intent);
    //            }

    //            @Override
    //            public void onCancel() {
    //                setResult(RESULT_CANCELED);
    //                finish();
    //            }

    //            @Override
    //            public void onError(FacebookException e) {
    //                // Handle exception
    //            }
    //        });

    //    faceBookSignIN.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View v) {
    //            fbOrGoogle = false;
    //            // fbSignIn();
    //
    //        }
    //    });
    // LoginManager.getInstance().logOut();
    // FirebaseAuth.getInstance().signOut();

    //  private void startSignInActivity() {
    //      startActivityForResult(
    //              AuthUI.getInstance()
    //                      .createSignInIntentBuilder()
//
    //                      .setAvailableProviders(
    //                              Arrays.asList(
    //                                      new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build())) // FACEBOOK
    //                      .setIsSmartLockEnabled(false, true)
//
    //                      .build(),
    //              RC_SIGN_IN);
    //  }


    //  googleMap.addMarker(new MarkerOptions().position(me));
    //  for (int i = 0; i < GetPlaces.myPlaces.size(); i++) {
    //      googleMap.addMarker(new MarkerOptions().position(GetPlaces.myPlaces.get(i).getLatLng()));

    //  }

    //  Log.d(TAG, "location = " + location);
    //  //    Myloc = location;
//public void getPlacePhoto( String placeID){
//    // Define a PlaceG4Lunch ID.
//    final String placeId = placeID;
//
//// Specify fields. Requests for photos must always have the PHOTO_METADATAS field.
//    final List<PlaceG4Lunch.Field> fields = Collections.singletonList(PlaceG4Lunch.Field.PHOTO_METADATAS);
//
//// Get a PlaceG4Lunch object (this example uses fetchPlace(), but you can also use findCurrentPlace())
//    final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(placeId, fields);
//
//    placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
//        final PlaceG4Lunch place = response.getPlace();
//
//        // Get the photo metadata.
//        final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
//        if (metadata == null || metadata.isEmpty()) {
//            Log.w(TAG, "No photo metadata.");
//            return;
//        }
//        final PhotoMetadata photoMetadata = metadata.get(0);
//
//        // Get the attribution text.
//        final String attributions = photoMetadata.getAttributions();
//
//        // Create a FetchPhotoRequest.
//        final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
//                .setMaxWidth(500) // Optional.
//                .setMaxHeight(300) // Optional.
//                .build();
//        placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
//
//            Bitmap bitmap = fetchPhotoResponse.getBitmap();
//myPlaceIMG.add(bitmap);
//
//
//
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                final ApiException apiException = (ApiException) exception;
//                Log.e(TAG, "PlaceG4Lunch not found: " + exception.getMessage());
//                final int statusCode = apiException.getStatusCode();
//
//            }
//        });
//    });
//
//}
//public List<PlaceG4Lunch>getPlacesDetails(List<String> placeIdList) {
//    // Define a PlaceG4Lunch ID.
//    final List<PlaceG4Lunch.Field> placeFields = Arrays.asList(PlaceG4Lunch.Field.ID, PlaceG4Lunch.Field.NAME, PlaceG4Lunch.Field.ADDRESS, PlaceG4Lunch.Field.OPENING_HOURS,
//            PlaceG4Lunch.Field.RATING, PlaceG4Lunch.Field.LAT_LNG);
//  List<PlaceG4Lunch> theOutPut = new ArrayList<>();
//  //  for (int i = 0; i < placeIdList.size(); i++){
//
//        final String placeId = placeIdList.get(placeIdList.get(0));
//
//// Specify the fields to return.
//
//
//// Construct a request object, passing the place ID and fields array.
//    final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
//
//    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//        PlaceG4Lunch place = response.getPlace();
//        Log.i(TAG, "PlaceG4Lunch found: " + place.getName());
//
//        theOutPut.add(place);
//
//    }).addOnFailureListener((exception) -> {
//        if (exception instanceof ApiException) {
//            final ApiException apiException = (ApiException) exception;
//            Log.e(TAG, "PlaceG4Lunch not found: " + exception.getMessage());
//            final int statusCode = apiException.getStatusCode();
//
//            // TODO: Handle error with given status code.
//        }
//    });
////}
//
//    return theOutPut;
//}

//  class GetPlasesList implements Runnable {

//      @Override
//      public void run() {
//          GetPlaces getPlaces = new GetPlaces(locationProviderClient, client, apikey, G4LunchMain.this);
//          getPlaces.getPlacesLikeHood();

//      }
//  }

    // Thread thread = new Thread(new GetPlasesList());
    // thread.start();


//    public List<String> getPlaceLikeHoo2d() {
//// Use fields to define the data types to return.
//
//        List<String> getPlacesID = new ArrayList<>();
//        List<PlaceG4Lunch.Field> placeFields = Collections.singletonList(PlaceG4Lunch.Field.NAME);
//
//// Use the builder to create a FindCurrentPlaceRequest.
//        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
//
//// Call findCurrentPlace and handle the response (first check that the user has granted permission).
//        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//
//            List<PlaceLikelihood> response = (List<PlaceLikelihood>) placesClient.findCurrentPlace(request).getResult();
//
//            if (response.size() != 0) {
//
//                for (PlaceLikelihood placeLikelihood : response) {
//                    Log.i(TAG, String.format("PlaceG4Lunch '%s' has likelihood: %f",
//                            placeLikelihood.getPlace().getName(),
//                            placeLikelihood.getLikelihood()));
//
//                    getPlacesID.add(placeLikelihood.getPlace().getId());
//                    Log.i(TAG, "and my list is: " + getPlacesID.size());
//
//                }
//
//            }
//            // else {
//            //     Exception exception = placeResponse.getException();
//            //     if (exception instanceof ApiException) {
//            //         ApiException apiException = (ApiException) exception;
//            //         Log.e(TAG, "PlaceG4Lunch not found: " + apiException.getStatusCode());
//            //     }
//            // }
//
//        }
//        return getPlacesID;
//
//    }


    // public void getPlaces() {
    //     String placeTypesList[] = {"restaurant", "cafe", "bar"};

    //     if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    //             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
    //             Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

    //     }
    //      locationProviderClient.getLastLocation()
    //     .addOnSuccessListener(new OnSuccessListener<Location>() {
    //         @Override
    //         public void onSuccess(Location location) {
    //             curentLat = location.getLatitude();
    //             curentLng = location.getLongitude();


    //     String urlAsString = "https://maps.googleapis.com/maps/api/place/details/output" + "?location=" + curentLat + "," + curentLng +
    //             "&radius=5000" + "&type=" + placeTypesList[0] + "&sensor=true" + "&key=" + "AIzaSyBZ1yf43MqKZwPmDvEkUx5CBufQpf01yDI";
    //     Log.d(TAG, "getPlaces: " + curentLat + " " + curentLng);

    //     URL url = null;
    //     try {
    //         url = new URL(urlAsString);
    //     } catch (MalformedURLException e) {
    //         e.printStackTrace();
    //     }

    //     FetchPlacesData fetchPlacesData = new FetchPlacesData(url);
    //     new Thread(fetchPlacesData).start();
    //     //call my runable here
    //    // new PlaceTask().execute(url);
    //         }
    //     });
    // }

    //  class FetchPlacesData implements Runnable {
//      URL url;
//      String line = "";
//      String data = "";
//      String parsed = "";
//      int i = 0;

//      public FetchPlacesData(URL url) {
//          this.url = url;
//      }


//      @Override
//      public void run() {


//          try {
//              HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
//              InputStream inputStream = urlConnection.getInputStream();
//              BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

//ile (bufferedReader.readLine() != null){
//  data += bufferedReader.readLine();
//

//ONArray jsonArray = new JSONArray(data);

//              Log.d(TAG, "Jason Array: " + jsonArray.toString());


//          }
//          catch (IOException | JSONException e) {
//              e.printStackTrace();
//              Log.d(TAG, "error: " + e.getMessage(), e);
//          }


//      }

//  }


//
//  private class PlaceTask extends AsyncTask<String, Integer, String> {

//      @Override
//      protected String doInBackground(String... strings) {
//          //Initialyze data
//          String data = null;
//          try {
//              data = downloadUrl(strings[0]);
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//          return data;
//      }

//      @Override
//      protected void onPostExecute(String s) {
//          new ParserTask().execute(s);
//      }

//      private String downloadUrl(String string) throws IOException {
//          //init URL
//          URL url = new URL(string);
//          //init conection
//          HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//          connection.connect();

//          InputStream stream = connection.getInputStream();

//          BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

//          StringBuilder builder = new StringBuilder();

//          String line = "";

//          while ((line = reader.readLine()) != null) {
//              builder.append(line);

//          }

//          String data = builder.toString();

//          reader.close();

//          return data;
//      }


//      private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

//          List<Double> TheLatList = new ArrayList<>();
//          List<Double> TheLngList = new ArrayList<>();

//          @Override
//          protected List<HashMap<String, String>> doInBackground(String... strings) {
//              //createJson
//              OtherJsonParser otherJsonParser = new OtherJsonParser();

//              List<HashMap<String, String>> mapList = null;
//              JSONObject object = null;
//              try {
//                  object = new JSONObject(strings[0]);

//                  mapList = otherJsonParser.parseResult(object);

//              } catch (JSONException e) {
//                  e.printStackTrace();
//              }

//              return mapList;
//          }

//          @Override
//          protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
//              for (int i = 0; i < hashMaps.size(); i++) {
//                  HashMap<String, String> hashMapList = hashMaps.get(i);
//                  double lat = Double.parseDouble(hashMapList.get("lat"));
//                  double lng = Double.parseDouble(hashMapList.get("lng"));
//                  String name = hashMapList.get("name");
//                  LatLng latLng = new LatLng(lat, lng);

//                  TheLatList.add(lat);
//                  TheLngList.add(lng);
//                  if (i == hashMaps.size()){

//                  }

//              }

//          }
//      }

//}
//package com.mickdevil.go4lunch.UI;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class OtherJsonParser {
//
//    private HashMap<String, String> parseJsonObject(JSONObject object) {
//
//        HashMap<String, String> dataList = new HashMap<>();
//        try {
//            String name = object.getString("name");
//
//            String latitude = object.getJSONObject("geometry").
//        getJSONObject("location").getString("lat");
//
//            String longitude = object.getJSONObject("geometry").
//                    getJSONObject("location").getString("lng");
//
//            dataList.put("name", name);
//            dataList.put("lat", latitude);
//            dataList.put("lng", longitude);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//return dataList;
//    }
//
//private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
//    List<HashMap<String,String>> dataList = new ArrayList<>();
//
//    for (int i=0; i<jsonArray.length(); i++ ){
//        try {
//        HashMap<String, String> data = parseJsonObject((JSONObject) jsonArray.get(i));
//
//dataList.add(data);
//
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//    }
//
//    return dataList;
//    }
//
//public List<HashMap<String,String>> parseResult(JSONObject object){
//      JSONArray jsonArray = null;
//try {
//        jsonArray = object.getJSONArray("results");
//
//} catch (JSONException e) {
//        e.printStackTrace();
//    }
//
//return parseJsonArray(jsonArray);
//    }
//
//
//}
//
// //method to geet curent Location
// public static LatLng getCurentLocation(Context context, Activity activity, FusedLocationProviderClient locationProviderClient) {
//     LatLng latLng;

//     if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,
//             Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

//     }

//     locationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//         @Override
//         public void onSuccess(Location location) {

//             latLng = new LatLng(location.getLatitude(), location.getLongitude());

//         }
//     }).addOnFailureListener(activity, new OnFailureListener() {
//         @Override
//         public void onFailure(@NonNull Exception e) {
//             Toast.makeText(context, "god damn", Toast.LENGTH_SHORT).show();
//         }
//     });

//     return latLng;

// }


//some stuf from mapFragment

// public boolean isGPSenabled() {
//     locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
//
//     boolean providerEnable = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
//
//     if (providerEnable) {
//         return true;
//     } else {
//         AlertDialog alertDialog = new AlertDialog.Builder(getActivity().getApplicationContext())
//                 .setTitle("GPS ask").setMessage("the GPS is requierd for this app").setPositiveButton("ya man", new
//                         DialogInterface.OnClickListener() {
//                             @Override
//                             public void onClick(DialogInterface dialog, int which) {
//                                 Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                 startActivityForResult(intent, GPS_REQUEST_CODE);
//                             }
//                         }).show();
//     }
//     return false;
// }

//the set elevation method to make view above others in "steck"
// searchOnMap.setElevation(1000);


//  public void searchMdoe(Boolean barMode) {

//      if (barMode) {
//          setSupportActionBar(toolbar);

//          NavigationView navigationView = findViewById(R.id.nav_view);
//          toolbar.setNavigationIcon(R.drawable.ic_open_drawer);

//          toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  drawer.openDrawer(Gravity.LEFT);
//              }
//          });

//      } else {
//          AppBarLayout.setVisibility(View.INVISIBLE);
//          toolbar.setVisibility(View.INVISIBLE);
//          searchOnMap.setVisibility(View.VISIBLE);
//          searchOnMap.setFocusable(false);
//          searchOnMap.bringToFront();

//          searchOnMap.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
//                          getPlaces.getPlacesList() ).build(G4LunchMain.this);
//              startActivityForResult(intent, REQUESTAUTOCOMPLITION);

//              }
//          });
//      }

//  }


// public void getLoc() {
//     if (ActivityCompat.checkSelfPermission(G4LunchMain.this, Manifest.permission.ACCESS_FINE_LOCATION)
//             != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(G4LunchMain.this,
//             Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//     }
//
//     locationProviderClient.getLastLocation().addOnSuccessListener(G4LunchMain.this, new OnSuccessListener<Location>() {
//         @Override
//         public void onSuccess(Location location) {
//
//             if (location != null) {
//                 LatLng me = new LatLng(location.getLatitude(), location.getLongitude());
//
//
//             }
//         }
//
//     }).addOnFailureListener(G4LunchMain.this, new OnFailureListener() {
//         @Override
//         public void onFailure(@NonNull Exception e) {
//
//         }
//     });
// }


                                                                                                     /////////////////
/////////////////                             ///////////                                ///////////////////////vvvvvv
    //////////////////////          ///////////////////////////////            /////////     /////////vvvvvvvvvv
    /////////////////////////////////////// THE GRAVE GRAVE WARM  ////////////////////    o   ///////
    /////////////////////////////////////////////////////////////////////////////////////   /////////
               ///////////////////////////////           /////////////////////1//////////////////////^^^^^^^^^^
                        ///////////                                /////////              //////////////////////^^^^
                                                                                                     ////////////////















    //hey dude, the one who reed it, try to get high when you code next time, it will increese the concentration


}
