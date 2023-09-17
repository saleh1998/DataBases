package com.example.databases;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class UpdateCompanyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    Button btnSave, btnCancel;
    EditText etEmail, etPassword, etConfirmPassword;
    Toolbar toolbar;
    NavigationView navigationView;
    Company company = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company);

        btnSave = findViewById(R.id.updateCompany_btnSave);
        btnCancel = findViewById(R.id.updateCompany_btnCancel);
        TextView etName = findViewById(R.id.updateCompany_etName); // CHANGE THIS TO TEXTVIEW CAUSE NOT ALLOWED TO CHANGE COMPANY'S NAME
        etEmail = findViewById(R.id.updateCompany_etEmail);
        etPassword = findViewById(R.id.updateCompany_etPassword);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode", 0);
        company = (Company) intent.getSerializableExtra("company");
        etName.setText(company.getName());
        etEmail.setText(company.getEmail());
        etPassword.setText(company.getPassword());

        ButtonsClick buttonsClick = new ButtonsClick();
        btnSave.setOnClickListener(buttonsClick);
        btnCancel.setOnClickListener(buttonsClick);
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
            if (view.getId() == btnSave.getId()) {
                ///Update Company
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                company.setEmail(email);
                company.setPassword(password);


                if (!email.isEmpty() && !password.isEmpty()) {
                    ///// added by rashad with advice from bayan
                    AdminFacade adminFacade = new AdminFacade(UpdateCompanyActivity.this);
                    if (!adminFacade.CompanyNameExists(company)) {
                        if (!adminFacade.CompanyEmailExists(company)) {
                            try {
                                adminFacade.updateCompany(company);
                                Intent intent = getIntent();
                                intent.putExtra("company", company);
                                intent.putExtra("requestCode", 4);
                                setResult(RESULT_OK, intent);
                                finish();
                            } catch (myException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            Toast.makeText(UpdateCompanyActivity.this, "Email is already taken", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdateCompanyActivity.this, "Name is already taken", Toast.LENGTH_SHORT).show();
                    }
                    /////
                /*Intent intent = getIntent();
                intent.putExtra("company", company);
                intent.putExtra("requestCode", 4);
                setResult(RESULT_OK, intent);
                finish();*/
                }else{
                    Toast.makeText(UpdateCompanyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
            if (view.getId() == btnCancel.getId()) {
                finish();
            }
        }
    }
}