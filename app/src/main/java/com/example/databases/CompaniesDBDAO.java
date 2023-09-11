package com.example.databases;

import android.content.Context;

import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO{
    Context context;

    DB_Manager mydb = DB_Manager.getInstance(context);

    @Override
    public boolean isCompanyExists(String email, String password) {
        return mydb.isCompanyExists(email,password);
    }

    @Override
    public void addCompany(Company company) throws myException {
    mydb.addCompany(company);

    }

    @Override
    public void updateCompany(Company company) throws myException {
    mydb.updateCompany(company);
    }

    @Override
    public void deleteCompany(int companyID) throws myException {
mydb.deleteCompany(companyID);
    }

    @Override
    public ArrayList<Company> getAllCompanies() {
        return mydb.getAllCompanies();
    }

    @Override
    public Company getOneCompany(int CompanyID) {
        return mydb.getOneCompany(CompanyID);
    }
}
