package edu.chnu.ratushniak.tddintro;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CompanyService implements ICompanyService {
  private final CompanyRepository repository;

  public CompanyService(CompanyRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Company> getTopLevelParentByName(String childName) {
    if (childName == null) {
      throw new IllegalArgumentException();
    }

    var company = repository.getCompanyByName(childName);

    Company parent = null;
    Optional<String> parentNameOptional = company.parentCompanyName;
    Set<String> visited = new HashSet<>();
    visited.add(childName);

    while (parentNameOptional.isPresent()) {
      String parentName = parentNameOptional.get();
      if (!visited.add(parentName)) {
        throw new IllegalStateException("Parent cycle detected involving: " + parentName);
      }
      parent = repository.getCompanyByName(parentName);
      parentNameOptional = parent.parentCompanyName;
    }

    return Optional.ofNullable(parent);
  }

  @Override
  public long getTotalEmployeesCountByName(String companyName) {
    if (companyName == null) {
      throw new IllegalArgumentException();
    }

    var company = repository.getCompanyByName(companyName);
    long total = company.employeesCount;

    for (String childName : company.childCompanyNames) {
      total += getTotalEmployeesCountByName(childName);
    }

    return total;
  }
}
