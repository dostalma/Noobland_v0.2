package cz.dostalma.noobland.command.handler;

import cz.dostalma.noobland.command.Command;
import cz.dostalma.noobland.context.ContextLocationEnum;
import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.model.SavedGame;
import cz.dostalma.noobland.model.player.PlayerCharacter;
import cz.dostalma.noobland.model.world.Location;
import cz.dostalma.noobland.model.world.World;
import cz.dostalma.noobland.util.FileUtil;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import static cz.dostalma.noobland.util.ConsoleUtil.*;

@Component
public class MenuActionHandler {

    public static Consumer<Object> menu() {
        return (o) -> {
            GameContext gameContext = GameContext.getInstance();
            gameContext.setPreviousContextLocation(gameContext.getContextLocation());
            gameContext.setContextLocation(ContextLocationEnum.MENU);
        };
    }

    public static Consumer<Object> returnToGame() {
        return (o) -> {
            GameContext gameContext = GameContext.getInstance();
            gameContext.setContextLocation(gameContext.getPreviousContextLocation());
        };
    }

    public static Consumer<Object> createNewGame() {
        return (o) -> {
            GameContext gameContext = GameContext.getInstance();
            // create new world - default world #1
            try {
                File worldSettingFile = gameContext.getResourceLoader().getResource("classpath:world/world.xml").getFile();
                JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
                World world = (World) jaxbContext.createUnmarshaller()
                        .unmarshal(new FileReader(worldSettingFile));
                gameContext.setWorld(world);
            } catch (JAXBException ex) {
                writeLine("Error during default world creation");
            } catch (IOException ex) {
                writeLine("Error: could not find default world file");
            }

            // create new character
            String name;
            Integer level = 1;
            String race;
            String clazz;

            writeLine("Creating new character");

            writeLine("Enter your character's name:");
            name = readLine();

            writeLine("Enter your character's race:");
            race = readLine();

            writeLine("Enter your character's class:");
            clazz = readLine();

            PlayerCharacter pc = new PlayerCharacter.PlayerCharacterBuilder()
                    .withName(name)
                    .withLevel(level)
                    .withRace(race)
                    .withClazz(clazz)
                    .build();
            pc.setId("1");
            pc.setCurrentLocationId(gameContext.getWorld().getStartingLocationId());
            pc.setLocation(gameContext.getWorld().getLocationWrapper().getLocations().stream()
                    .filter((location) -> location.getId().equals(pc.getCurrentLocationId()))
                    .findFirst()
                    .get());
            pc.setCurrentLocationId(pc.getLocation().getId());
            gameContext.setPlayerCharacter(pc);

            gameContext.setContextLocation(ContextLocationEnum.WORLD);

            if (gameContext.isContextFullyInitialized()) {
                writeLine("Error during creation of a new game");
            }

            writeLine("New game creation done");

            // describe current location
            writeLine(pc.getLocation().getDescription());
        };
    }

    public static Consumer<Object> quitGame() {
        return (o) -> {
            // DONT DO ANYTHING FOR NOW
        };
    }

    public static Consumer<Object> saveGame() {
        return (o) -> {
            GameContext gameContext = GameContext.getInstance();
            try {
                writeLine("Saving game " + gameContext.getPlayerCharacter().getName());

                // test
//                Location location1 = new Location();
//                location1.setId("1");
//                location1.setName("abc");
//                location1.setDescription("......");
//
//                Location location2 = new Location();
//                location2.setId("2");
//                location2.setName("zxc");
//                location2.setDescription("sdasdsasd");
//
//                Location.LocationWrapper locations = new Location.LocationWrapper();
//                locations.setLocations(Arrays.asList(location1, location2));
//
//                World world = new World();
//                world.setLocationWrapper(locations);
//
//                String saveDirectoryPath = gameContext.getEnv().getProperty("configuration.save.location");
//                File saveFile = new File(saveDirectoryPath + "testsave_test.xml");
//                JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
//                Marshaller mar = jaxbContext.createMarshaller();
//                mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//                mar.marshal(world, saveFile);

                // real code
                SavedGame savedGame = new SavedGame();
                PlayerCharacter pc = gameContext.getPlayerCharacter();
                savedGame.setPlayerCharacter(pc);
                savedGame.setWorld(gameContext.getWorld());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH-mm-ss");
                LocalDateTime now = LocalDateTime.now();
                String saveDirectoryPath = gameContext.getEnv().getProperty("configuration.save.location");
                File saveFile = new File(saveDirectoryPath + "save_" + pc.getName() + "_" + dtf.format(now) + ".xml");

                JAXBContext jaxbContext = JAXBContext.newInstance(SavedGame.class);
                Marshaller mar = jaxbContext.createMarshaller();
                mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                mar.marshal(savedGame, saveFile);

                writeLine("Game saved");
            } catch (JAXBException e) {
                writeLine("Saving player character save failed");
            }
        };
    }

    public static Consumer<Object> loadGame() {
        return (comm) -> {
            GameContext gameContext = GameContext.getInstance();

            Command command = (Command) comm;
            String characterName = command.getParameters()[0];

            writeLine("Loading character " + command.getParameters()[0]);
            try {
                String saveDirectoryPath = gameContext.getEnv().getProperty("configuration.save.location");
                FileUtil fileUtil = new FileUtil();
                File latestSavedFile = fileUtil.findLatestSavedFileOfName(characterName, saveDirectoryPath);

                JAXBContext jaxbContext = JAXBContext.newInstance(SavedGame.class);
                SavedGame save = (SavedGame) jaxbContext.createUnmarshaller()
                        .unmarshal(new FileReader(saveDirectoryPath + latestSavedFile.getName()));

                gameContext.setWorld(save.getWorld());
                PlayerCharacter pc = save.getPlayerCharacter();
                pc.setLocation(gameContext.getWorld().getLocationWrapper().getLocations().stream()
                        .filter((location) -> location.getId().equals(pc.getCurrentLocationId()))
                        .findFirst()
                        .get());

                gameContext.setPlayerCharacter(pc);

                gameContext.setContextLocation(ContextLocationEnum.WORLD);

                if (gameContext.isContextFullyInitialized()) {
                    writeLine("Error during creation of a new game");
                }

                writeLine("Character " + characterName + " successfully loaded");

                // describe current location
                writeLine(pc.getLocation().getDescription());
            } catch (JAXBException | IOException e) {
                writeLine("Error occurred during loading of character " + characterName);
                e.printStackTrace();
            }
        };
    }
}
