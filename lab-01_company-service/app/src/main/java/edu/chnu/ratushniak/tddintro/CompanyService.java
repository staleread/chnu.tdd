package edu.chnu.ratushniak.tddintro;

import java.util.Optional;

public class CompanyService implements ICompanyService {
  private final CompanyRepository repository;

  public CompanyService(CompanyRepository repository) {
    this.repository = repository;
  }

  @Override
  public Optional<Company> getTopLevelParentByName(String childName) {
    throw new UnsupportedOperationException();
  }

  @Override
  public long getTotalEmployeesCountByName(String companyName) {
    throw new UnsupportedOperationException();
  }
}
