package com.myapplication.androidsampleappproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity
{
    //google+ login
    private SignInButton googleSignInButton;//google  button class
    private GoogleSignInOptions gso ;
    private GoogleApiClient googleApiClient ;
    private  static final  int RC_SIGN_IN = 1;
    //display user profile
    private ImageView personPic;
    private TextView profile;
    //facebook login
    LoginButton facebookLoginButton;//facebook button class
    CallbackManager mCallbackManager;
    boolean isFacebookLoginAlready = false ;
    //my display
    private Button loginButton;
    private Button logoutButton;
    private TextView textView ;
    //firebase
    private FirebaseAuth mAuth;
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textView = (TextView) findViewById(R.id.textView) ;
        textView.setText(R.string.login_activity_tips);
        loginButton = (Button) findViewById(R.id.loginButton) ;
        loginButton.setEnabled(false);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                attemptLogin();
            }
        });
        logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setVisibility(View.INVISIBLE);
        logoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                firebaseSingOut(view);
            }
        });
        personPic = (ImageView) findViewById(R.id.personPic);
        profile = (TextView) findViewById(R.id.profile);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //facebook
        mCallbackManager = CallbackManager.Factory.create();
        //does not work??
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {
                        Toast.makeText(LoginActivity.this, "Facebook loginManager  Successful.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel()
                    {
                        Toast.makeText(LoginActivity.this, "Facebook loginManager  Cancel.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception)
                    {
                        Toast.makeText(LoginActivity.this, "Facebook loginManager  Error.", Toast.LENGTH_SHORT).show();
                    }
                });


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        facebookLoginButton = findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setReadPermissions("email", "public_profile");//設定權限
        facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TagSampleAppProject", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(LoginActivity.this, "Facebook Authentication Successful.", Toast.LENGTH_SHORT).show();
                isFacebookLoginAlready = true ;
                loginButton.setEnabled(true);
            }

            @Override
            public void onCancel()
            {
                Log.d("TagSampleAppProject", "facebook:onCancel");
                Toast.makeText(LoginActivity.this, "Facebook Authentication cancel.", Toast.LENGTH_SHORT).show();
                isFacebookLoginAlready = false ;
            }

            @Override
            public void onError(FacebookException error)
            {
                Log.d("TagSampleAppProject", "facebook:onError", error);
                Toast.makeText(LoginActivity.this, "Facebook Authentication error.", Toast.LENGTH_SHORT).show();
                isFacebookLoginAlready = false ;
            }
        });
        // Facebook 登出監聽
        Profile fbProfile = Profile.getCurrentProfile();
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                if (currentAccessToken == null)
                {
                    mAuth.signOut();
                    Toast.makeText(LoginActivity.this, "FireBase Authentication signOut.", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                    isFacebookLoginAlready = false ;
                }
            }
        };
        googleSignInButton = (SignInButton) findViewById(R.id.googleLoginButton);
        googleSignInButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        // 設定 Google 登入 Client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener()
                {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
                    {
                        Toast.makeText(LoginActivity.this,"Google Connect Failed.",Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(LoginActivity.this, "FireBase get Current User signin", Toast.LENGTH_SHORT).show();
        updateUI(currentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, responseCode, intent);
        //
        if (requestCode == RC_SIGN_IN && isFacebookLoginAlready == false)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                //取得使用者並試登入
                firebaseAuthWithGoogle(account);
            }
        }
    }

    //登入 Firebase
    private  void firebaseAuthWithGoogle(final GoogleSignInAccount account)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (!task.isSuccessful())
                        {
                            // If sign in fails, display a message to the user.
                            Log.d("TagSampleAppProject", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Firebase Authentication failed.",Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        else
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TagSampleAppProject", "signInWithCredential:success");
                            Toast.makeText(LoginActivity.this, "Firebase Authentication Successful,SingIn name:"+account.getDisplayName(), Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }
                    }
                });
    }

    public void firebaseSingOut(View view)
    {
        // Firebase 登出
        mAuth.signOut();
        Toast.makeText(LoginActivity.this, "Firebase SingOut", Toast.LENGTH_LONG).show();
        // Google 登出
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                Toast.makeText(LoginActivity.this, "Google SingOut", Toast.LENGTH_LONG).show();
                updateUI(null);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token)
    {
        Log.d("TagSampleAppProject", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TagSampleAppProject", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Toast.makeText(LoginActivity.this, "FireBase Authentication Successful.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("TagSampleAppProject", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "FireBase Authentication failed.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void updateUI(FirebaseUser any)
    {
        TextView textView = (TextView) findViewById(R.id.profile);
        ImageView image = (ImageView) findViewById(R.id.personPic);
        if (any == null)
        {
            textView.setText(R.string.default_user_name);
            image.setImageResource(R.mipmap.ic_launcher_round);
            logoutButton.setVisibility(View.INVISIBLE);
            loginButton.setEnabled(false);
        }
        else
        {
            textView.setText(any.getDisplayName());
            Picasso.with(this).load(any.getPhotoUrl()).into(image);
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setEnabled(true);
        }
    }
    private void attemptLogin()
    {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        LoginActivity.this.finish();
    }
}

