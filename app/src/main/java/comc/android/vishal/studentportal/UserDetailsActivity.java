package comc.android.vishal.studentportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsActivity extends AppCompatActivity {
    private EditText name,roll,batch,year;
    Spinner spinner;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        name = (EditText) findViewById(R.id.name);
        roll = (EditText) findViewById(R.id.roll);
        batch = (EditText) findViewById(R.id.batch);
        year = (EditText) findViewById(R.id.passingYear);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.degree_array,R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        /*        IMPLEMENT LATER INIT EDIT TEXTS



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(user.getUid());
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User usr = dataSnapshot.getValue(User.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("INFO", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        dbref.addValueEventListener(postListener);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid(
    */

    }

    public void onSave(View v){
        String n = name.getText().toString();
        String r = roll.getText().toString();
        String y = year.getText().toString();
        String b = batch.getText().toString();
        String d = spinner.getSelectedItem().toString();

        User u = new User(n,b,r,y,d);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(user.getUid()).removeValue();
        mDatabase.child("users").child(user.getUid()).setValue(u);
    }
}
