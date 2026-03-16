package edu.chnu.ratushniak.tddintro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Company {
  public final String name;

  public long employeesCount;
  public Optional<String> parentCompanyName;
  public List<String> childCompanyNames;

  public Company(String name, long employeesCount) {
    this.name = name;
    this.employeesCount = employeesCount;
    this.parentCompanyName = Optional.ofNullable(null);
    this.childCompanyNames = new ArrayList();
  }

  public void addChildCompany(Company child) {
    this.childCompanyNames.add(child.name);
    child.parentCompanyName = Optional.of(this.name);
  }
}
