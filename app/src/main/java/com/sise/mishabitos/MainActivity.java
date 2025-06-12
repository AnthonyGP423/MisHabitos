package com.sise.mishabitos;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textoFraseDia = findViewById(R.id.texto_frase_dia);
        Button btnOtraFrase = findViewById(R.id.btn_otra_frase);
        FloatingActionButton botonAgregar = findViewById(R.id.btn_agregar_tarea);


        //El menu toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //Aqui acaba el menu

        //////////////////////////////////////////////////////////////////////////////
        // Llama al main_activity y el contenedor con el id contenedor tareas lo usa
        LinearLayout contenedorTareas = findViewById(R.id.contenedor_tareas);

        //Aqui lista las tareas para listar es una prueba para que use la base de datos
        List<String> listaTareas = Arrays.asList("Tarea 1", "Tarea 2", "Tarea 3");

        // esto es para agregar el chekbox
        for (String nombreTarea : listaTareas) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(nombreTarea);
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.texto_tareas));
            checkBox.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                    getResources().getDimension(R.dimen.checkbox_text_size) / getResources().getDisplayMetrics().scaledDensity);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) getResources().getDimension(R.dimen.checkbox_height)
            );

            int margin = (int) getResources().getDimension(R.dimen.checkbox_margin);
            params.setMargins(margin, margin, margin, margin);
            checkBox.setLayoutParams(params);

            int padding = (int) getResources().getDimension(R.dimen.checkbox_padding);
            checkBox.setPadding(padding, padding, padding, padding);

            contenedorTareas.addView(checkBox);

            botonAgregar.setOnClickListener(v -> {
                Toast.makeText(this, "Agregar nueva tarea", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, CrearTarea.class);
                startActivity(intent);
            });

        ///////////////////////////////////////////////////////////////////////////////
            List<String> frases = Arrays.asList(
                    "El éxito no llega de la noche a la mañana, se construye día a día con disciplina, constancia y una actitud positiva frente a los desafíos.",
                    "Recuerda que incluso los más grandes soñadores comenzaron dando pequeños pasos. Cree en ti mismo y no dejes de avanzar.",
                    "Los errores no son fracasos, son lecciones que te acercan más a tu objetivo si aprendes de ellos y sigues adelante con más fuerza.",
                    "No esperes el momento perfecto para empezar, porque el momento perfecto no existe. Empieza con lo que tienes, donde estás, y hazlo ahora.",
                    "La diferencia entre quien logra sus metas y quien no, está en la perseverancia. Nunca subestimes el poder de seguir intentándolo.",
                    "Cada día es una nueva oportunidad para mejorar, para crecer, y para acercarte un poco más a la persona que quieres ser.",
                    "Los grandes logros no son el resultado de la suerte, sino del trabajo silencioso, de la determinación y del coraje de no rendirse.",
                    "Si hoy fue un mal día, recuerda que los días grises también son parte del camino. Sigue caminando, el sol volverá a salir.",
                    "Cree en tu valor incluso cuando otros no lo vean. Tú eres capaz de lograr cosas increíbles si no te rindes.",
                    "El futuro pertenece a quienes creen en la belleza de sus sueños y trabajan cada día para convertirlos en realidad."
            );

            Random random = new Random();

            textoFraseDia.setText("Frase del día:\n" + frases.get(random.nextInt(frases.size())));

            btnOtraFrase.setOnClickListener(v -> {
                int index = random.nextInt(frases.size());
                textoFraseDia.setText("Frase del día:\n" + frases.get(index));
            });
        ////////////////////////////////////////////////////////////////////////////////////


    }}

    //Toast de cada boton
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Abriste Perfil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, InicioSession.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Abriste Configuración", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_faq) {
            Toast.makeText(this, "Abriste FAQ", Toast.LENGTH_SHORT).show();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //para abrir
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    //Aqui Acaba

}
