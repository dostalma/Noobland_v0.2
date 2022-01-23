package cz.dostalma.noobland.context;

import cz.dostalma.noobland.model.world.World;
import cz.dostalma.noobland.util.ConsoleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static cz.dostalma.noobland.util.ConsoleUtil.writeLine;

@Component
public class ContextConfiguration {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    GameContext gameContext;

    @Bean
    public void initializeContext() throws IOException, JAXBException {
        File worldSettingFile = resourceLoader.getResource("classpath:world/world.xml").getFile();
        JAXBContext jaxbContext = JAXBContext.newInstance(World.class);
        World world = (World) jaxbContext.createUnmarshaller()
                .unmarshal(new FileReader(worldSettingFile));
        gameContext.setWorld(world);
        gameContext.setContextLocation(ContextLocationEnum.MENU);

        ConsoleUtil.initiateContext(gameContext);

        writeLine("World context initialized");
    }

}
