package rodriguezfernandez.carlos.hellosharedprefs;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    //Poner un nombre al archivo, por convención se usa el nombre del paquete de la app, aunque vale cualquiera.
    private String sharedPrefFile="rodriguezfernandez.carlos.hellosharedprefs";
    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;
    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreferences=getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        // Initialize views, color
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        // Restaurar los valores del SharedPreferences.
        mCount=mPreferences.getInt(COUNT_KEY,0);//el valor de COuNTKEY o por defecto 0
        mColor=mPreferences.getInt(COLOR_KEY, mColor);// El valor de COLOR_KEY o por defecto, el que se asinga a mColor : R.color.default_background
        mShowCountTextView.setText(String.format("%s",mCount));
        mShowCountTextView.setBackgroundColor(mColor);

    }

    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }

    public void reset(View view) {
        //Esto solo funciona a nivel visual.///////////////////
        // Reset count.
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(this,
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);
        //Esto borra de las shared preferences.
        SharedPreferences.Editor preferencesEditor=mPreferences.edit();
        preferencesEditor.clear();//Borrar las preferencias en el editor.
        preferencesEditor.apply(); //aplicar los cambios, es decir, borrar el archivo.
    }
    // Sobreescribir el método onPause
    @Override
    protected void onPause() {
        super.onPause();
        //Obtenemos un editor de propiedades a traves del Objeto SharedPreferences, necesario para escribir en el.
        SharedPreferences.Editor preferencesEditor=mPreferences.edit();
        //Se añaden los datos al editor
        preferencesEditor.putInt(COUNT_KEY,mCount);
        preferencesEditor.putInt(COLOR_KEY,mColor);
        //Se escriben en el fichero de forma asincrona.
        preferencesEditor.apply();
    }
}