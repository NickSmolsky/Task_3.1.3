//package com.nicksmol.restexam.dao;
//
//import com.nicksmol.restexam.model.User;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@Repository
//public class UserDaoImpl implements UserDao {
//
//
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//
//    @Override
//    public List<User> getAll() {
//        return entityManager.createQuery("from User", User.class).getResultList();
//    }
//
//    @Override
//    public User selectById(long id) {
//        return entityManager.find(User.class, id);
//    }
//
//    @Override
//    public void save(User user) {
//        entityManager.persist(user);
//        entityManager.flush();
//    }
//
//    @Override
//    public void update(User user, long id) {
//        User userById = selectById(id);
//        userById.setUsername(user.getUsername());
//        userById.setLastName(user.getLastName());
//        userById.setAge(user.getAge());
//        userById.setEmail(user.getEmail());
//        userById.setRoles(user.getRoles());
//        save(userById);
//    }
//
//    @Override
//    public void delete(long id) {
//        User userById = selectById(id);
//        entityManager.remove(userById);
//        entityManager.flush();
//    }
//
//    @Override
//    public User findByUsername(String username) {
//        User user = entityManager.createQuery(
//                        "SELECT u from User u WHERE u.username = :username", User.class).
//                setParameter("username", username).getSingleResult();
//        return user;
//    }
//
////    @Override
////    public User findByUserEmail(String email) {
////        User user = entityManager.createQuery(
////                        "SELECT u from User u WHERE u.email = :email", User.class).
////                setParameter("email", email).getSingleResult();
////        return user;
////    }
//
//    @Override
//    public User findByEmail(String email) {
//        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).
//                setParameter("email", email).getSingleResult();
//        return user;
//    }
//}
