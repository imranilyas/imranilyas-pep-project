package Service;

import java.util.List;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;

public class SocialMediaService {
    SocialMediaDAO socialMediaDAO;
    
    public SocialMediaService() {
        socialMediaDAO = new SocialMediaDAO();
    }

    public Account userRegistration(Account account) {
        return socialMediaDAO.registerUser(account);
    }

    //! TODO
    public Account login(Account account) {
        return socialMediaDAO.loginUser(account);
    }

    public Message createMessage(Message message) {
        return socialMediaDAO.createMessage(message);
    }

    public List<Message> getAllMessages() {
        return null;
    }
    
    public Message getMessageById(int message_id) {
        return null;
    }

    public Message deleteMessageById(int message_id) {
        return null;
    }

    public Message updateMessageById(int message_id, Message message) {
        return null;
    }

    public List<Message> getMessagesByAccountId(int account_id) {
        return null;
    }
    
}
