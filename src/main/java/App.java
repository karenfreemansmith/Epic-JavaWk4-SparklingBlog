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

    get("/tags/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find (Integer.parseInt(request.params(":id")));
      model.put("tag", tag);
      model.put("tags", Tag.all());
      model.put("template", "templates/tags.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    post("/tags/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find (Integer.parseInt(request.params(":id")));
      tag.setText(request.queryParams("tag"));
      model.put("tags", Tag.all());
      model.put("tag", tag);
      model.put("template", "templates/tags.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/tags/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find(Integer.parseInt(request.params(":id")));
      tag.delete();
      model.put("template", "templates/tags.vtl");
      response.redirect("/");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

  }
}
