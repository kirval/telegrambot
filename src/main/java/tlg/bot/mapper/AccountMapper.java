package tlg.bot.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tlg.bot.models.Account;
import tlg.bot.models.Request;

import java.util.List;

public interface AccountMapper {
    void insertAccount(Account account);

    @Select("SELECT * FROM request_history WHERE user_telegram_id = #{id}")
    @Results(value = {
            @Result(property = "telegramID",column = "user_telegram_id"),
            @Result(property = "url", column = "url"),
            @Result(property = "title", column = "title")
    })
    List<Request> selectRequestsByAccountId(Integer id);


    @Select("SELECT * FROM request_history WHERE user_telegram_id = #{id} ORDER BY id  DESC  LIMIT 10")
    List<Request> selectTop10RequestsByAccountId(Integer id);
}
