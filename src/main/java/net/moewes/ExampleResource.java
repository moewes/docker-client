package net.moewes;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InfoCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class ExampleResource {

    @Inject
    DockerEngine dockerEngine;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {

        //DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        Info info = dockerEngine.getInfo();

        return "hello " + info.getName();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/info")
    public Info getInfo() {

        return dockerEngine.getInfo();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/container")
    public List<Container> getContainers() {

        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        return dockerClient.listContainersCmd().withShowAll(true).exec();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/images")
    public List<Image> getImages() {

        return dockerEngine.getImages();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public CreateContainerResponse createContainer() {

        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        CreateContainerResponse container = dockerClient.createContainerCmd("test:latest")
            .withName("test1")
            .withPortBindings(PortBinding.parse("8280:8080"))
            .exec();

        dockerClient.startContainerCmd(container.getId()).exec();

        return container;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/stop")
    public Response stopAllContainer() {

        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        List<Container> containers = dockerClient.listContainersCmd().exec();

        for(Container container:containers) {
            dockerClient.stopContainerCmd(container.getId()).exec();
        }

        return Response.accepted().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/kill/{id}")
    public Response killContainer(@PathParam("id") String id) {

        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

        dockerClient.removeContainerCmd(id).exec();

        return Response.ok().build();
    }

}