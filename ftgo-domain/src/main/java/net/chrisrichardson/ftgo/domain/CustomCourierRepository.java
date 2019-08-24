package net.chrisrichardson.ftgo.domain;

public interface CustomCourierRepository {

  Courier findOrCreateCourier(long courierId);

}
