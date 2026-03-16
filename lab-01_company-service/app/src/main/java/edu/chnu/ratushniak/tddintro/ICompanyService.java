package edu.chnu.ratushniak.tddintro;

import java.util.Optional;

public interface ICompanyService {
  Optional<Company> getTopLevelParentByName(String childName);

  long getTotalEmployeesCountByName(String companyName);
}
