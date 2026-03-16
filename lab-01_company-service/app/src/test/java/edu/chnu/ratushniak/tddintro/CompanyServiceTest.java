package edu.chnu.ratushniak.tddintro;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

public class CompanyServiceTest {

  private CompanyRepository repository;
  private ICompanyService service;

  @Before
  public void setUp() {
    repository = new CompanyRepository();
    service = new CompanyService(repository);
  }

  @Test(expected = IllegalArgumentException.class)
  public void GivenNullName_WhenGetTopLevelParent_ThenThrowsIllegalArgument() {
    service.getTopLevelParentByName(null);
  }

  @Test(expected = NoSuchElementException.class)
  public void GivenUnknownName_WhenGetTopLevelParent_ThenThrowsNoSuchElement() {
    service.getTopLevelParentByName("Unknown");
  }

  @Test
  public void GivenCompanyWithNoParent_WhenGetTopLevelParent_ThenReturnsEmptyOptional() {
    Company company = new Company("Standalone", 100);

    repository.addCompany(company);

    Optional<Company> result = service.getTopLevelParentByName("Standalone");

    assertFalse(result.isPresent());
  }

  @Test(expected = IllegalStateException.class, timeout = 1000)
  public void GivenParentCycle_WhenGetTopLevelParent_ThenThrowsIllegalState() {
    Company a = new Company("A", 10);
    Company b = new Company("B", 20);

    repository.addCompany(a);
    repository.addCompany(b);

    a.addChildCompany(b);
    b.addChildCompany(a);

    service.getTopLevelParentByName("A");
  }

  @Test
  public void GivenSingleChildHierarchy_WhenGetTopLevelParent_ThenReturnsParent() {
    Company parent = new Company("Parent", 100);
    Company child = new Company("Child", 50);

    repository.addCompany(parent);
    repository.addCompany(child);

    parent.addChildCompany(child);

    Optional<Company> result = service.getTopLevelParentByName("Child");

    assertTrue(result.isPresent());
    assertEquals("Parent", result.get().name);
  }

  @Test
  public void GivenMultiLevelHierarchy_WhenGetTopLevelParent_ThenReturnsRoot() {
    Company root = new Company("Root", 200);
    Company mid = new Company("Mid", 100);
    Company leaf = new Company("Leaf", 50);

    repository.addCompany(root);
    repository.addCompany(mid);
    repository.addCompany(leaf);

    root.addChildCompany(mid);
    mid.addChildCompany(leaf);

    Optional<Company> result = service.getTopLevelParentByName("Leaf");

    assertTrue(result.isPresent());
    assertEquals("Root", result.get().name);
  }

  @Test
  public void GivenSingleCompany_WhenGetTotalEmployees_ThenReturnsOwnCount() {
    Company company = new Company("Company", 100);

    repository.addCompany(company);

    long result = service.getTotalEmployeesCountByName("Company");

    assertEquals(100, result);
  }

  @Test
  public void GivenOneChild_WhenGetTotalEmployees_ThenReturnsSumOfBoth() {
    Company parent = new Company("Parent", 100);
    Company child = new Company("Child", 50);

    repository.addCompany(parent);
    repository.addCompany(child);

    parent.addChildCompany(child);

    long result = service.getTotalEmployeesCountByName("Parent");

    assertEquals(150, result);
  }

  @Test
  public void GivenMultipleChildren_WhenGetTotalEmployees_ThenReturnsSumOfAll() {
    Company parent = new Company("Parent", 100);
    Company child1 = new Company("Child1", 50);
    Company child2 = new Company("Child2", 30);

    repository.addCompany(parent);
    repository.addCompany(child1);
    repository.addCompany(child2);

    parent.addChildCompany(child1);
    parent.addChildCompany(child2);

    long result = service.getTotalEmployeesCountByName("Parent");

    assertEquals(180, result);
  }

  @Test
  public void GivenMultiLevelHierarchy_WhenGetTotalEmployees_ThenReturnsSumOfAllLevels() {
    Company root = new Company("Root", 200);
    Company mid = new Company("Mid", 100);
    Company leaf = new Company("Leaf", 50);

    repository.addCompany(root);
    repository.addCompany(mid);
    repository.addCompany(leaf);

    root.addChildCompany(mid);
    mid.addChildCompany(leaf);

    long result = service.getTotalEmployeesCountByName("Root");

    assertEquals(350, result);
  }

  @Test
  public void GivenCompanyWithZeroEmployees_WhenGetTotalEmployees_ThenReturnsChildCount() {
    Company root = new Company("Root", 0);
    Company child = new Company("Child", 10);

    repository.addCompany(root);
    repository.addCompany(child);

    root.addChildCompany(child);

    long result = service.getTotalEmployeesCountByName("Root");

    assertEquals(10, result);
  }

  @Test(expected = NoSuchElementException.class)
  public void GivenUnknownName_WhenGetTotalEmployees_ThenThrowsNoSuchElement() {
    service.getTotalEmployeesCountByName("Unknown");
  }

  @Test(expected = IllegalArgumentException.class)
  public void GivenNullName_WhenGetTotalEmployees_ThenThrowsIllegalArgument() {
    service.getTotalEmployeesCountByName(null);
  }
}
