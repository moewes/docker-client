package net.moewes;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DockerEngine {

  DockerClient dockerClient = DockerClientBuilder.getInstance().build();

  public Info getInfo() {
    return dockerClient.infoCmd().exec();
  }

  public List<Image> getImages() {

    return dockerClient.listImagesCmd().exec();
  }

}
