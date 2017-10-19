package tlg.bot.services;


import org.apache.ibatis.session.SqlSession;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import tlg.bot.mapper.AccountMapper;
import tlg.bot.models.Account;

import java.io.IOException;

public class RegistrationService {

    public static SendMessage getRegistrationMessage(Update update) {

        long chatId = update.getMessage().getChatId();
        SendMessage message = new SendMessage().setChatId(chatId);
        User user = update.getMessage().getFrom();

        try{
        saveAccount(user);

        String msgText = "Вы успешно зарегестрировались!" +
                "\nВаш ID: " + user.getId() +
                "\nВаше имя: " + user.getFirstName() +
                "\nВаша фамилия: " + user.getLastName() +
                "\nВаш никнейм: " + user.getUserName();
        message.setText(msgText);
        }
        catch (Exception e){
            e.printStackTrace();
            message.setText("Ошибка регистрации");
        }
        return message;
    }

    public static void saveAccount(User user) {

        Account account = new Account(user.getId(), user.getFirstName(), user.getLastName(), user.getUserName());
        SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();

        try{
            AccountMapper accountMapper = sqlSession.getMapper(AccountMapper.class);
            accountMapper.insertAccount(account);
            sqlSession.commit();

            System.out.println(account.getTelegramID() + account.getFirst_name() + account.getLast_name() + account.getUsername());
        }
        finally {
            sqlSession.close();
        }
    }


    // ДОБАВИТЬ ЗАГЛУШКИ НА ОПЦИОНАЛЬНЫЕ ПОЛЯ USER
    
}
