package lk.javainstitute.app26pactical;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Spinner spinner1 = findViewById(R.id.spinner);

        List<String> brands = new ArrayList<>();
        brands.add("Select brands");
        brands.add("Apple");
        brands.add("Samsung");
        brands.add("Huawei");
        brands.add("Vivo");

        ArrayAdapter<String> brandArrayAdapter = new ArrayAdapter<>(
                MainActivity.this ,
                R.layout.brands ,
                brands
        );

        spinner1.setAdapter(brandArrayAdapter);


        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText1 = findViewById(R.id.editTextText);
                EditText editText2 = findViewById(R.id.editTextText2);
                EditText editText3 = findViewById(R.id.editTextText3);

                String brand = String.valueOf(spinner1.getSelectedItem());
                String title = String.valueOf(editText1.getText());
                String qty = String.valueOf(editText2.getText());
                String price = String.valueOf(editText3.getText());

                if (brand.equals("Select brands")) {
                    Toast.makeText(MainActivity.this, "Select the product brand", Toast.LENGTH_SHORT).show();
                } else if (title.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Product title", Toast.LENGTH_SHORT).show();
                }else if (qty.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                }else if (price.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Price", Toast.LENGTH_SHORT).show();
                }else{

                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                    HashMap<String,Object> map = new HashMap<>();
                    map.put("brand",brand);
                    map.put("title",title);
                    map.put("qty",qty);
                    map.put("price",price);

                    firestore.collection("product").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Product added Successfull", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Product Added faild", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.i("App25log", title);
                    Log.i("App25log", brand);
                    Log.i("App25log", qty);
                    Log.i("App25log", price);

                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                    spinner1.setSelection(0);

                }

            }
        });

        Button button2 = findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , MainActivity2.class);
                startActivity(intent);
            }
        });



    }
}