package net.moewes;

import com.github.dockerjava.api.model.Info;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import javax.inject.Inject;

@Route("")
public class TestView extends VerticalLayout {

  @Inject
  public TestView(DockerEngine engine) {

    add(new Text("Hallo Welt"));

    Info info = engine.getInfo();
    add(new Html("<br/>"));
    add(new Text(info.getArchitecture()));
  }
}
