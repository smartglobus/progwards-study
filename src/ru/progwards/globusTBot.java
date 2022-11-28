package ru.progwards;

import org.telegram.telegrambots.ApiContextInitializer;
import ru.progwards.java1.testlesson.ProgwardsTelegramBot;

public class globusTBot extends ProgwardsTelegramBot {

    private final String assortiment = "У нас есть версии стандартная и компактная, с подвижной тележкой или без неё.\n" +
            "Какие параметры вас интересуют в первую очередь?";

    @Override
    public String processMessage(String text) {
        checkTags(text);

        if (foundCount() == 1) {
            if (checkLastFound("привет")) {
                return "Здравствуйте! Ищете гребной тренажёр? Вы попали по адресу!\n" + assortiment;
            }
            if (checkLastFound("оформление")) {
                return "Спасибо за проявленный интерес!\nДля уточнения конечной цены и деталей доставки с вами свяжется наш менеджер.";
            }
            if (checkLastFound("пардон, был неправ")) {
                return "Не надо ругаться. Я не волшебник, я только учусь";
            }
            if (checkLastFound("версия \"Компакт\"") || checkLastFound("версия \"Стандарт\"")){
                return "Ваш выбор\n " + getLastFound() + "\nУточните, нужна ли тележка?";
            }

            return "Если вам подходит вариант\n " + getLastFound() + ", можем перейти к оформлению?";
        }

        if (foundCount() > 1) {
            return "Можем предложить: \n" + extract();
        }

        return "Уточните, пожалуйста! Или свяжитесь с нами круглосуточно по тел. 112 для получения подробной консультации.\n" + assortiment;
    }

    public static void main(String[] args) {
        System.out.println("Hello bot!");

        ApiContextInitializer.init();

        globusTBot bot = new globusTBot();
        bot.username = "GlobusFirstBot";
        bot.token = "5853830848:AAFrer2JPb7wFXuyM-jOxbugjy1AHVVVRFc";

        bot.addTags("привет", "привет, здрасьте, здравствуй, добр, день, вечер, утро, hi, hello");
        bot.addTags("оформление", "конец, все, стоп, нет, цена, стоит, оформ, оплат, достав");
        bot.addTags("пардон, был неправ", "дурак, придурок, идиот, тупой, фигня");

        bot.addTags("версия \"Компакт\"", "тренаж, компакт, складн, склады, разбор, дом");
        bot.addTags("версия \"Стандарт\"", "тренаж, обычн, стандарт, зал");

        bot.addTags("с подвижной тележкой", "тренаж, подвиж, платф, тележкой, тележка, тележку");
        bot.addTags("без подвижной тележки", "тренаж, без, тележки, неподвиж");

        bot.start();
    }
}
