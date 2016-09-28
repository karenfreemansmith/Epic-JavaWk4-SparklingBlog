import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String view = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = new Tag (request.queryParams("tag"));
      model.put("template", "templates/index.vtl");
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

  }
}
