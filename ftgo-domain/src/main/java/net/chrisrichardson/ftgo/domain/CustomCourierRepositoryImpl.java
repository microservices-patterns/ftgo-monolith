package net.chrisrichardson.ftgo.domain;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

public class CustomCourierRepositoryImpl implements CustomCourierRepository {

  @Autowired
  private EntityManager entityManager;

//  @Override
//  public List<Courier> findAllAvailable() {
//    return entityManager.createQuery("").getResultList();
//  }
}
