package edu.chnu.ratushniak.tddintro;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class CompanyRepository {
  private final HashMap<String, Company> storage = new HashMap<>();

  public void addCompany(Company company) {
    for (var name : company.childCompanyNames) {
      if (!storage.containsKey(name)) {
        throw new NoSuchElementException();
      }
    }

    storage.put(company.name, company);
  }

  public Company getCompanyByName(String name) {
    var company = storage.get(name);

    if (company == null) {
      throw new NoSuchElementException();
    }
    return company;
  }
}
