package edu.vt.crest.hr.services;

import java.math.BigInteger;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

import edu.vt.crest.hr.entity.EmployeeEntity;

/**
 * Implements an EmployeeService
 */
@ApplicationScoped
public class EmployeeServiceBean implements EmployeeService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity createEmployee(EmployeeEntity employee) {
    Query insert = em.createNativeQuery("insert into employee (id, first_name, last_name, version) " +
            "values (:id, :fName,:lName,:version)");
    //employee.getId() is null which should pull the next sequential id --- This is not the case

    //Work around for id number:
    employee.setId(getNextId());

    insert.setParameter("id", employee.getId());
    insert.setParameter("fName", employee.getFirstName());
    insert.setParameter("lName", employee.getLastName());

    // TODO: Why would we have per-employee versioning?
    insert.setParameter("version", 0);

    insert.executeUpdate();
    return employee;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity findById(Long id) {
    String queryString = "select e from Employee e where e.id = :id";
    TypedQuery<EmployeeEntity> query = em.createQuery(queryString, EmployeeEntity.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
    public List<EmployeeEntity> listAll(Integer startPosition, Integer maxResult) {
    String queryString = "select e from Employee e";
    TypedQuery<EmployeeEntity> query = em.createQuery(queryString, EmployeeEntity.class);
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EmployeeEntity update(Long id, EmployeeEntity employee) throws OptimisticLockException {
    Query update= em.createNativeQuery("update employee set first_name = :fName, last_name = :lName where id = :id");
    update.setParameter("fName", employee.getFirstName());
    update.setParameter("lName", employee.getLastName());
    update.setParameter("id", id);
    update.executeUpdate();
    return employee;
  }

  private Long getNextId(){
    String queryString = "select max(e.id)+1 from Employee e";
    TypedQuery<Long> query = em.createQuery(queryString, Long.class);
    return query.getSingleResult();
  }

}
