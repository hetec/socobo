package controllers;

import models.recipes.Recipe;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Handles incoming requests for {@link models.recipes.Recipe} and returns JsonObjects
 */
public class RecipeJsonController extends Controller {

    public static Result list() {
        final List<Recipe> productList;
        return TODO;
    }

    public static Result details(final Long id) {
        return TODO;
    }

    public static Result details(final String name) {
        return TODO;
    }

    public static Result list(final String keywords) {
        return TODO;
    }

    // @BodyParser.Of()
    public static Result save() {
        return TODO;
    }
}
