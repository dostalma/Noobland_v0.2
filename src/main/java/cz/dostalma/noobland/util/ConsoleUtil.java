package cz.dostalma.noobland.util;

import cz.dostalma.noobland.context.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

@Component
public class ConsoleUtil {

    private static Scanner sc = new Scanner(System.in);

    private static GameContext gameContext;

    public static void initiateContext(GameContext gameContext) {
        ConsoleUtil.gameContext = gameContext;
    }

    public static String readLine() {
        System.out.print(gameContext.getContextLocation().getName() + " $:");
        return sc.nextLine();
    }

    public static void writeLine(String text) {
        System.out.println(text);
    }

    @Bean
    public void foo() {
        System.out.println("Foo");
    }
}
