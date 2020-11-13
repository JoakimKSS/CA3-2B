package facades;

import dto.BookDTO;
import entities.Book;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class BookFacade {

    private static BookFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private BookFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static BookFacade getBookFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new BookFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getBookCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long bookCount = (long)em.createQuery("SELECT COUNT(b) FROM Book b").getSingleResult();
            return bookCount;
        }finally{  
            em.close();
        }  
    }
    
    public void addBookToPerson(BookDTO bookdto) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, bookdto.getUsername());
            Book book = new Book(bookdto.getTitle(), bookdto.getAuthor());
            user.addBook(book);
            em.persist(user);
            em.persist(book);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
    }
    
    public List<BookDTO> getAllBooksOnPerson(String username) {
        EntityManager em = emf.createEntityManager();
        List<BookDTO> books = new ArrayList();
        try {
            TypedQuery<Book> q = em.createQuery("SELECT b FROM Book b JOIN b.users u WHERE u.userName = :username", Book.class);
            q.setParameter("username", username);
            q.getResultStream().forEach((book) -> {
                books.add(new BookDTO(book));
            });
            for(BookDTO book : books) {
                System.out.println(book.getTitle());
            }
        }finally {
            em.close();
        }
        return books;
    }

}
