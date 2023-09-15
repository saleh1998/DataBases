package com.example.databases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.sql.SQLException;

public class AddCompanyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    Button btnSave, btnCancel;
    EditText etName, etEmail, etPassword, etConfirmPassword;
    Toolbar toolbar;
    NavigationView navigationView;
    //DB_Manager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        btnSave = findViewById(R.id.addCompany_btnSave);
        btnCancel = findViewById(R.id.addCompany_btnCancel);
        etName = findViewById(R.id.addCompany_etName);
        etEmail = findViewById(R.id.addCompany_etEmail);
        etPassword = findViewById(R.id.addCompany_etPassword);
        etConfirmPassword = findViewById(R.id.addCompany_etConfirmPassword);

        ButtonsClick buttonsClick = new ButtonsClick();
        btnSave.setOnClickListener(buttonsClick);
        btnCancel.setOnClickListener(buttonsClick);

        toolbar = findViewById(R.id.addCompany_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.addCompany_drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //db = DB_Manager.getInstance(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragment_container, new AdminFragment()).commit();
        }
        if (item.getItemId() == R.id.nav_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            finish();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    class ButtonsClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            ///Save new Company
            if (view.getId() == btnSave.getId()) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)) {
                    Company company = new Company(name, email, password);

                    Intent intent = getIntent();
                    intent.putExtra("company", company);
                    intent.putExtra("requestCode", 2);
                    setResult(RESULT_OK, intent);
                    finish();

                } else {
                    etPassword.setText("");
                    etConfirmPassword.setText("");
                    Toast.makeText(AddCompanyActivity.this, "Please insert correct password confirmation!!", Toast.LENGTH_SHORT).show();
                }
            }
            if (view.getId() == btnCancel.getId()) {
                finish();
            }
        }
    }
}