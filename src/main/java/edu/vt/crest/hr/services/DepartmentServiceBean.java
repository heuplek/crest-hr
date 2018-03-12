package edu.vt.crest.hr.services;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;

import edu.vt.crest.hr.entity.DepartmentEntity;
import edu.vt.crest.hr.entity.EmployeeEntity;

/**
 * Implements a DepartmentService
 */
@ApplicationScoped
public class DepartmentServiceBean implements DepartmentService {

  @PersistenceContext
  private EntityManager em;

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity createDepartment(DepartmentEntity department) {
    Query insert = em.createNativeQuery("insert into department (id, name, identifier, version) " +
            "values (:id, :name,:identifier,:version)");

    //department.getId() is null which should pull the next sequential id --- This is not the case

    //Work around for id number:
    department.setId(getNextId());

    insert.setParameter("id", department.getId());
    insert.setParameter("name", department.getName());
    insert.setParameter("identifier", department.getIdentifier());

    // TODO: Why would we have per-department versioning?
    insert.setParameter("version", 0);

    insert.executeUpdate();
    return department;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity findById(Long id) {
    String queryString = "select d from Department d where d.id = :id";
    TypedQuery<DepartmentEntity> query = em.createQuery(queryString, DepartmentEntity.class);
    query.setParameter("id", id);
    return query.getSingleResult();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DepartmentEntity> listAll(Integer startPosition, Integer maxResult) {
    String queryString = "select d from Department d";
    TypedQuery<DepartmentEntity> query = em.createQuery(queryString, DepartmentEntity.class);
    if(startPosition != null){
      query.setFirstResult(startPosition);
    }
    if(maxResult !=null){
      query.setMaxResults(maxResult);
    }
    return query.getResultList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DepartmentEntity update(Long id, DepartmentEntity department) throws OptimisticLockException {
    Query update= em.createNativeQuery("update department set name = :name, identifier = :identifier where " +
            "id = :id");
    update.setParameter("name", department.getName());
    update.setParameter("identifier", department.getIdentifier());
    update.setParameter("id", id);
    update.executeUpdate();
    return department;
  }

  private Long getNextId(){
    String queryString = "select max(d.id)+1 from Department d";
    TypedQuery<Long> query = em.createQuery(queryString, Long.class);
    return query.getSingleResult();
  }

}
