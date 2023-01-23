package org.example.project2.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void unAppointAll(int id) {
        Session session = entityManager.unwrap(Session.class);
        session.createQuery("update Book b set owner=null where b.owner.id=:ownerId")
                .setParameter("ownerId", id).executeUpdate();
    }

}
