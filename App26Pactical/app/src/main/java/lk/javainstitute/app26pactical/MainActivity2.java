package lk.javainstitute.app26pactical;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spinner2 = findViewById(R.id.spinner2);
        Spinner spinner3 = findViewById(R.id.spinner3);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();


        List<String> brands = new ArrayList<>();
        brands.add("Select brands");
        brands.add("Apple");
        brands.add("Samsung");
        brands.add("Huawei");
        brands.add("Vivo");


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                MainActivity2.this,
                android.R.layout.simple_list_item_1,
                brands

        );



        spinner2.setAdapter(arrayAdapter);


        List<String> products = new ArrayList<>();
        products.add("Select products");





        ArrayAdapter<String> productArrayAdapter = new ArrayAdapter<>(
                MainActivity2.this ,
                android.R.layout.simple_list_item_1 ,
                products
        );

        firestore.collection("product")
                .whereEqualTo("brand","Samsung")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documents, @Nullable FirebaseFirestoreException error) {
                List<DocumentSnapshot> documentSnapshotList = documents.getDocuments();
                for (DocumentSnapshot document : documentSnapshotList){
                    products.add(String.valueOf(document.get("title")));

                }
                productArrayAdapter.notifyDataSetChanged();
            }
        });
//
        spinner3.setAdapter(productArrayAdapter);




        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               String selectedBrand = String.valueOf( spinner2.getSelectedItem());

               firestore.collection("product")
                       .where(
                               Filter.equalTo("brand",selectedBrand)
                       ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                           @Override
                           public void onComplete(@NonNull Task<QuerySnapshot> task) {

                              List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();


                               for (DocumentSnapshot document : documentSnapshots) {
                                   Log.i("App20p",String.valueOf(document.getData()));

                               }

                           }
                       });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String product = String.valueOf(spinner2.getSelectedItem());
                firestore.collection("product")
                        .where(
                                Filter.equalTo("title",product)


                        ).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                List<DocumentSnapshot> documentSnapshotList = task.getResult().getDocuments();

                                for (DocumentSnapshot document : documentSnapshotList) {
                                    Log.i("App20p",String.valueOf(document.getData()));

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }
}