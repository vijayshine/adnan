package com.example.something.better;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Shiv on 2/28/17.
 */

public class FirebaseUtils {

    /**
     * Try to login to server with current credentials
     *
     * @param email
     * @param password
     * @param mAuth
     * @param context
     * @param var
     */
    public static void attemptLogin(String email, String password, final FirebaseAuth mAuth, final Context context, final MainActivity var) {

        Log.d("MainActivity", "Login");
        final ArrayList<Integer> bool = new ArrayList<>();
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(var, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("SignIn Status", "signInWithEmail:onComplete:" );

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                if (user.isEmailVerified()) {

                                    Intent intent = new Intent(context, FeedActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                    ((Activity) context).finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("context", "signInWithEmail:failure", task.getException());
                                    Intent intent = new Intent(context, VerifyEmailActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                    // updateUI(null);
                                }
                            } else {
                                Log.w("SignIn Status", "signInWithEmail+here", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }
    }

    /**
     * Try to create new account on server
     *
     * @param email
     * @param password
     * @param mAuth
     * @param context
     * @param var
     */
    public static void attemptRegister(String email, String password, final FirebaseAuth mAuth, final Context context, final RegisterActivity var) {
        Log.d("Check this code", "code");
        if (!email.equals("") && !password.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(var, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("SignUp Status", "createUserWithEmail:onComplete:" + task.isSuccessful());
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user,context,mAuth);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed."+task.getException().toString().split(":")[1],
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }
    private static void updateUI(FirebaseUser user, final Context context, FirebaseAuth mAuth) {
        //   hideProgressDialog();

        if (user != null) {
            user.reload();
            if (!user.isEmailVerified()) {
                Toast.makeText(context, "Please verify your email address", Toast.LENGTH_LONG).show();
                sendEmailVerification(mAuth,context);
            } else {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "createUserWithEmail:success");

            }


        } else {

        }
    }

    private static void sendEmailVerification(FirebaseAuth mAuth, final Context context) {

        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener((Activity) context, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        if (task.isSuccessful()) {
                            // If sign in fails, display a message to the user.
                            Log.w("context", "signInWithEmail:failure", task.getException());
                            Intent intent = new Intent(context, VerifyEmailActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Log.e("RegisterActivity", "sendEmailVerification", task.getException());
                            Toast.makeText(context,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]

    }



}
