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

    //! TODO
    public Account userRegistration(Account account) {
        return null;
    }

    public Account login(Account account) {
        return null;
    }

    public Message createMessage(Message message) {
        return null;
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
