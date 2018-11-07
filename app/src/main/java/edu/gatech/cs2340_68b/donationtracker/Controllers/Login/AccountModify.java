package edu.gatech.cs2340_68b.donationtracker.Controllers.Login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340_68b.donationtracker.Models.User;

public class AccountModify {

    public static void lockAccount(String email) {
        final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebase.getReference("accounts");
        Query accountQuery = ref.orderByChild("username").equalTo(email);
        accountQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.e("Error", "Account does not exists.");
                    return;
                }
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    User account = i.getValue(User.class);
                    account.incrementFailed();
                    if (account.getFailedAttempts() >= 3) {
                        account.setIsLock(true);
                    }
                    ref.child(i.getKey()).setValue(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public static void resetAttemptCount(String email) {
        final FirebaseDatabase firebase = FirebaseDatabase.getInstance();
        final DatabaseReference ref = firebase.getReference("accounts");
        Query accountQuery = ref.orderByChild("username").equalTo(email);
        accountQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot i : dataSnapshot.getChildren()) {
                    User account = i.getValue(User.class);
                    account.setFailedAttempts(0);
                    account.setIsLock(false);
                    ref.child(i.getKey()).setValue(account);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}
