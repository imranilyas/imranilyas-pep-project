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
        return socialMediaDAO.getAllMessages();
    }
    
    public Message getMessageById(int message_id) {
        return socialMediaDAO.getMessageByMessageId(message_id);
    }

    public Message deleteMessageById(int message_id) {
        return socialMediaDAO.deleteMessage(message_id);
    }

    public Message updateMessageById(int message_id, String message) {
        return socialMediaDAO.updateMessage(message_id, message);
    }

    public List<Message> getMessagesByAccountId(int account_id) {
        return socialMediaDAO.getMessagesByAccountId(account_id);
    }
    
}
