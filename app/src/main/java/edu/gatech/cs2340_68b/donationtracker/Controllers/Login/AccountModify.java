package edu.gatech.cs2340_68b.donationtracker.Controllers.Login;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import edu.gatech.cs2340_68b.donationtracker.Controllers.Common.CustomDialog;
import edu.gatech.cs2340_68b.donationtracker.Models.AccountAttempt;

public class AccountModify {

    public static void lockAccount(String email) {
        final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebase.getReference("accounts");
        Query accountQuery = ref.orderByChild("email").equalTo(email);
        accountQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.e("Error", "Account does not exists.");
                    return;
                }
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    AccountAttempt account = i.getValue(AccountAttempt.class);
                    account.setAttempts(account.getAttempts() + 1);
                    ref.child(i.getKey()).setValue(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
