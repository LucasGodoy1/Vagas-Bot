package LucasGodoy1.com.github;

import LucasGodoy1.com.github.model.TelegraaAutoMsg;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class PegaVagaBot {
    public static void main(String[] args) throws TelegramApiException {
        TelegraaAutoMsg bot = new TelegraaAutoMsg();
        bot.runBot();


    }

}

