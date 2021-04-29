package com.mickdevil.go4lunch.UI.SignIn;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mickdevil.go4lunch.AppUser;
import com.mickdevil.go4lunch.R;
import com.mickdevil.go4lunch.UI.G4LunchMain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainSigninActivity extends AppCompatActivity {


    private static final String TAG = "MainSigninActivity";
    //all things used for auth

    public static FirebaseAuth firebaseAuth;
    //my fierBase database
    public FirebaseDatabase database;
    public static DatabaseReference fierBaseDBRef;
    public static final String USER_KEY = "App users";

    private GoogleSignInClient googleSignInClient;

    private GoogleSignInOptions gso;

    private static final int RC_SIGN_IN = 1001;

    private LoginButton faceBookSignIN;

    private com.google.android.gms.common.SignInButton googleSignIn;

    private CallbackManager mCallbackManager;

    private LoginManager loginManager;

    AppUser appUser;

    List<String> permisionsForGodDamnFB = Arrays.asList("email", "public_profile");

    ArrayList<String> permissions = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);
        firebaseAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(MainSigninActivity.this);
        askPermisions();
        configureGoogleClient();


        //--------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------
   //    //THIS IS THE FUCKING BEST WAY TO GET THE HASH FOR FACEBOOK. THANK YOU  INDIAN DUDE

   //    try {
   //        PackageInfo info = getPackageManager().getPackageInfo(
   //                getPackageName(),
   //                PackageManager.GET_SIGNATURES);
   //        for (Signature signature : info.signatures) {
   //            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
   //            messageDigest.update(signature.toByteArray());
   //            Log.d(TAG, Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT));
   //        }
   //    } catch (PackageManager.NameNotFoundException e) {

   //    } catch (NoSuchAlgorithmException e) {

   //    }
//--------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------


        permissions.add("email");
        permissions.add("public_profile");


        googleSignInClient = GoogleSignIn.getClient(this, gso);

        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();


        //init fierBase DB
        database = FirebaseDatabase.getInstance();
        fierBaseDBRef = database.getReference(USER_KEY);

        //finde views by ID
        faceBookSignIN = findViewById(R.id.faceBookSignIN);
        googleSignIn = findViewById(R.id.googleSignIn);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInToGoogle();
            }
        });


        //  faceBookSignIN.setPermissions(Arrays.asList(EMAIL, USER_POSTS));
        faceBookSignIN.setReadPermissions(permissions);
        mCallbackManager = CallbackManager.Factory.create();


        faceBookSignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                faceBookSignIN.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }


                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "ну все пиздец", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), exception.toString(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "my AccessToken: " + token.getToken());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainSigninActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser firebaseUser = task.getResult().getUser();

                            Intent intent = new Intent(MainSigninActivity.this, G4LunchMain.class);

                            appUser = new AppUser(fierBaseDBRef.getKey(), firebaseUser.getDisplayName(), null, firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString(), null
                                    );

                            Log.d(TAG, "user FB photo " + firebaseUser.getPhotoUrl().toString());

                            chekIfUserExistAndPush(appUser);

                            intent.putExtra(G4LunchMain.appUserKey, appUser);

                            G4LunchMain.start(MainSigninActivity.this, intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainSigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private void configureGoogleClient() {
        // Configure Google Sign In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // for the requestIdToken, this is in the values.xml file that
                // is generated from your google-services.json
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        // Set the dimensions of the sign-in button.

        // Initialize Firebase Auth
    }

    public void signInToGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            appUser = new AppUser(fierBaseDBRef.getKey(), account.getDisplayName(),
                                    account.getFamilyName(), account.getEmail(), account.getPhotoUrl().toString(), null);
                            Log.d(TAG, "onComplete: " + appUser);

                            chekIfUserExistAndPush(appUser);

                            Intent intent = new Intent(MainSigninActivity.this, G4LunchMain.class);

                            intent.putExtra(G4LunchMain.appUserKey, appUser);

                            G4LunchMain.start(MainSigninActivity.this, intent);

                        } else {
                            //my code here
                        }
                    }
                });
    }


    //need tou implement after
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {

            Log.i(TAG, "onStart: Someone logged in <3");
        } else {
            Log.i(TAG, "onStart: No one logged in :/");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately

            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }


    }


//methods not directly used for Auth

    //I can add some permissions to this stuf. if i need after in the app
    public void askPermisions() {
        Dexter.withContext(MainSigninActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CALL_PHONE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (!multiplePermissionsReport.areAllPermissionsGranted()) {
                    showDialogOFDeniedPermisions();

                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();
    }


    public AlertDialog showDialogOFDeniedPermisions() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setMessage(R.string.perms_are_denied);

        alertBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return alertBuilder.show();
    }


    private void chekIfUserExistAndPush(AppUser appUser) {
        List<String> myUsers = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    AppUser appUser = ds.getValue(AppUser.class);
                    myUsers.add(appUser.email);

                }
                Log.d(TAG, "myUser " + myUsers);


                if (!myUsers.contains(appUser.email)) {

fierBaseDBRef.child(appUser.email.substring(0, appUser.email.indexOf("@"))).setValue(appUser);
                   // fierBaseDBRef.push().setValue(appUser);

                    Log.d(TAG, "app user " + appUser);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        fierBaseDBRef.addValueEventListener(valueEventListener);

    }

    public static void getTheFuckOutOfHere(int fromWhereChumba) {
        switch (fromWhereChumba) {
            case 0:
                firebaseAuth.signOut();
                break;

            case 1:
                LoginManager.getInstance().logOut();
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //I need do EXACTLY THIS with the dellete user, becose if the user have register his facebook with the
        // same mail the app broke and facebook throws auth fail, becose mails are same
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseAuth.getCurrentUser().delete();
        }

        LoginManager.getInstance().logOut();
    }


}



