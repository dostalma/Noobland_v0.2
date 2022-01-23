package cz.dostalma.noobland.manager;

import cz.dostalma.noobland.command.CommandEnum;
import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.command.Command;
import cz.dostalma.noobland.command.CommandFactory;
import cz.dostalma.noobland.command.menu.MenuActionHandler;
import cz.dostalma.noobland.model.world.Location;
import cz.dostalma.noobland.model.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static cz.dostalma.noobland.util.ConsoleUtil.*;

@Component
public class GameManager implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(GameManager.class);

    @Autowired
    private MenuActionHandler menuActionHandler;

    @Autowired
    private CommandFactory commandFactory;

    @Autowired
    private CommandManager commandManager;

    @Autowired
    private GameContext gameContext;

    public void start() throws JAXBException {
        writeLine("Starting a game");

        Command createCharacterCommand = commandFactory.createCreateCharacterCommand();
        createCharacterCommand.execute(null);
//
//        Command saveCharacterCommand = commandFactory.createSaveCharacterCommand();
//        saveCharacterCommand.execute(null);

        commandFactory.createLoadCharacterCommand().execute("Bob");

        Command saveGameCommand = commandFactory.createSaveGameCommand();
        saveGameCommand.execute(null);

        // Initial context information
        writeLine("You are at :" + gameContext.getPlayerCharacter().getLocation().getName());

        // run game
        runGameLoop();

        writeLine("Game finished");
    }

    private void runGameLoop() {
        while (true) {
            // ask for a command
            Command command = commandManager.getCommandFromPlayerInput();
            if (Command.EMPTY_COMMAND.equals(command)) {
                writeLine("Unknown command entered");
                continue;
            }
            if (CommandEnum.QUIT_GAME.equals(command.getCommandType())) {
                break;
            }

            command.execute(null);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

//    private static void unMarshalingExample() throws JAXBException {
//        JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
//        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//        Employees emps = (Employees) jaxbUnmarshaller.unmarshal( new File("c:/temp/employees.xml") );
//
//        for(Employee emp : emps.getEmployees())
//        {
//            System.out.println(emp.getId());
//            System.out.println(emp.getFirstName());
//        }
//    }

//    private static void marshalingExample() throws JAXBException {
//        World world = new World();
//        world.setId("666");
//        Location loc1 = new Location();
//        loc1.setId("1");
//        loc1.setName("Brick");
//        Location loc2 = new Location();
//        loc2.setId("2");
//        loc2.setName("Wood");
//        Set<Location> locs = new HashSet<>(Arrays.asList(loc1, loc2));
//        world.setLocations(locs);
//
//        JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
//        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        jaxbMarshaller.marshal(world, System.out);
//        jaxbMarshaller.marshal(world, new File("c:/temp/world_example.xml"));
//    }
}
