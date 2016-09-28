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
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/tags", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/tags.vtl");
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    post("/tags", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = new Tag (request.queryParams("tag"));
      model.put("template", "templates/tags.vtl");
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/tags/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find (Integer.parseInt(request.params(":id")));
      model.put("tag", tag);
      model.put("tags", Tag.all());
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    post("/tags/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find (Integer.parseInt(request.params(":id")));
      tag.setText(request.queryParams("tag"));
      model.put("tag", tag);
      model.put("tags", Tag.all());
      model.put("template", "templates/tag.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/tags/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Tag tag = Tag.find(Integer.parseInt(request.params(":id")));
      tag.delete();
      response.redirect("/");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/posts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/posts.vtl");
      model.put("posts", Post.all());
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    post("/posts", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Post post = new Post (request.queryParams("title"), request.queryParams("post"));
      post.addTag(Integer.parseInt(request.queryParams("tags")));
      model.put("template", "templates/posts.vtl");
      model.put("posts", Post.all());
      model.put("post", post);
      model.put("tags", Tag.all());
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/posts/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Post post = Post.find (Integer.parseInt(request.params(":id")));
      model.put("posts", Post.all());
      model.put("tags", Tag.all());
      model.put("posttags", post.getAllTags());
      model.put("post", post);
      model.put("template", "templates/post.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

      post("/posts/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Post post = Post.find (Integer.parseInt(request.params(":id")));
      post.addTag(Integer.parseInt(request.queryParams("tags")));
      post.setText(request.queryParams("post"));
      post.setTitle(request.queryParams("title"));
      model.put("posts", Post.all());
      model.put("tags", Tag.all());
      model.put("posttags", post.getAllTags());
      model.put("post", post);
      model.put("template", "templates/post.vtl");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

    get("/posts/:id/delete", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Post post = Post.find(Integer.parseInt(request.params(":id")));
      post.delete();
      model.put("template", "templates/post.vtl");
      response.redirect("/");
      return new ModelAndView(model, view);
    }, new VelocityTemplateEngine());

  }
}
