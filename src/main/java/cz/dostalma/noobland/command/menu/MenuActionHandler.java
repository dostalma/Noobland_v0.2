package cz.dostalma.noobland.command.menu;

import cz.dostalma.noobland.context.GameContext;
import cz.dostalma.noobland.model.SavedGame;
import cz.dostalma.noobland.model.world.Location;
import cz.dostalma.noobland.model.player.PlayerCharacter;
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

    public static Consumer<Object> createCharacter(GameContext gameContext) {
        return (o) -> {
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
                    .withLocation(gameContext.getWorld().getLocations().get(0))
                    .build();
            pc.setId("1");

            writeLine("Character creation done");

            gameContext.setPlayerCharacter(pc);
        };
    }

    public static Consumer<Object> savePlayerCharacter(GameContext gameContext) {
        return (o) -> {
            try {
                writeLine("Saving character");

                PlayerCharacter pc = gameContext.getPlayerCharacter();
                String saveDirectoryPath = gameContext.getEnv().getProperty("configuration.save.location");

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH-mm-ss");
                LocalDateTime now = LocalDateTime.now();

                File saveFile = new File(saveDirectoryPath + pc.getName() + "_" + dtf.format(now) + ".xml");

                JAXBContext jaxbContext = JAXBContext.newInstance(PlayerCharacter.class);
                Marshaller mar = jaxbContext.createMarshaller();
                mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                mar.marshal(pc, saveFile);

                writeLine("Character saved");
            } catch (JAXBException e) {
                writeLine("Saving player character save failed");
            }
        };
    }

    public static Consumer<Object> loadPlayerCharacter(GameContext gameContext) {
        return (name) -> {
            if (!(name instanceof String)) {
                throw new IllegalArgumentException("Parameter must be of type String");
            }
            writeLine("Loading character " + name);
            try {
                String saveDirectoryPath = gameContext.getEnv().getProperty("configuration.save.location");
                FileUtil fileUtil = new FileUtil();
                File latestSavedFile = fileUtil.findLatestSavedFileOfName((String) name, saveDirectoryPath);


                JAXBContext jaxbContext = JAXBContext.newInstance(PlayerCharacter.class);
                PlayerCharacter pc = (PlayerCharacter) jaxbContext.createUnmarshaller()
                        .unmarshal(new FileReader(saveDirectoryPath + latestSavedFile.getName()));

                pc.setLocation(gameContext.getWorld().getLocations().stream()
                    .filter((location) -> location.getId().equals(pc.getCurrentLocationId()))
                    .findFirst()
                    .get());

                gameContext.setPlayerCharacter(pc);
                writeLine("Character " + name + " load successful");
            } catch (JAXBException | IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static Consumer<Object> quitGame(GameContext gameContext) {
        return (o) -> {
            // DONT DO ANYTHING FOR NOW
        };
    }

    public static Consumer<Object> saveGame(GameContext gameContext) {
        return (o) -> {
            try {
                writeLine("Saving game " + gameContext.getPlayerCharacter().getName());

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

                writeLine("Character saved");
            } catch (JAXBException e) {
                writeLine("Saving player character save failed");
            }
        };
    }
}
