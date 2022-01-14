package com.tma.contactmgr;

import com.tma.contactmgr.model.Contact;
import com.tma.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;


public class Application {

    //Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        //Create a StandardServiceRegistry
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args) {
        Contact contact = new ContactBuilder("Thang", "Nguyen")
                .withEmail("nminhthang@tma.com.vn")
                .withPhone(7735556666L)
                .build();
        Contact contact1 = new ContactBuilder("Hoang", "Pham")
                .withEmail("phamhoang@tma.com.vn")
                .withPhone(1341412421L)
                .build();
        System.out.println(contact);

        int id = save(contact);
        int id1 = save(contact1);

        //Display a list of contacts before the update using stream
        System.out.println("Before update");
        fetchAllContacts().stream().forEach(System.out::println);

        //Get the persisted contact
        Contact myContact = findContactById(id);

        //Update the contact
        myContact.setFirstName("Minh Thang");

        //Persist the changes
        System.out.println("Updating.......%n%n");
        update(myContact);
        System.out.println("Update complete!!!");

        //Display a list of contacts after the update
        System.out.println("After update");
        fetchAllContacts().stream().forEach(System.out::println);

        //Delete the contact id = 2
        System.out.println("Deleting.......%n%n");
        deleteById(2);
        System.out.println("Delete complete!!!");

        //Display a list of contacts after the delete
        System.out.println("After delete");
        fetchAllContacts().stream().forEach(System.out::println);

    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts(){
        // Open a session
        Session session = sessionFactory.openSession();

        // Create Criteria
        Criteria criteria = session.createCriteria(Contact.class);

        //Get a list of Contact objects according to the Criteria object
        List<Contact> contacts = criteria.list();


        // Close the session
        session.close();

        return contacts;
    }

    private static int save(Contact contact){
        //Open a session
        Session session = sessionFactory.openSession();
        //Begin a transaction
        session.beginTransaction();
        //Use the session to save the contact
        int id = (int)session.save(contact);
        //Commit the transaction
        session.getTransaction().commit();
        //Close the session
        session.close();

        return id;
    }

    private static Contact findContactById(int id){
        //Open a session
        Session session = sessionFactory.openSession();
        //Retrieve the persistent object (or null if not found)
        Contact contact = session.get(Contact.class, id);
        //Close the session
        session.close();
        //Return the object
        return contact;
    }

    private static void update(Contact contact){
        //Open a session
        Session session = sessionFactory.openSession();
        //Begin a transaction
        session.beginTransaction();
        //Use the session to update the contact
        session.update(contact);
        //Commit the transaction
        session.getTransaction().commit();
        //Close the session
        session.close();
    }

    private static void deleteById(int id){
        //Open a session
        Session session = sessionFactory.openSession();
        //Begin a transaction
        session.beginTransaction();
        //Use the session to update the contact
        session.delete(findContactById(id));
        //Commit the transaction
        session.getTransaction().commit();
        //Close the session
        session.close();
    }
}