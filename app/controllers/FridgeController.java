package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import models.fridge.Fridge;
import models.fridge.FridgeRepository;
import models.fridge.Item;
import models.produce.Produce;
import models.produce.ProduceRepository;
import models.recipes.statics.Statics;
import models.user.User;
import models.user.UserRepository;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

@Named
@Singleton
public class FridgeController extends Controller {

    private final FridgeRepository fridgeRepository;

    private final UserRepository userRepository;

    private final ProduceRepository produceRepository;

    @Inject
    public FridgeController(FridgeRepository fridgeRepository, UserRepository userRepository, ProduceRepository produceRepository) {
        this.fridgeRepository = fridgeRepository;
        this.userRepository = userRepository;
        this.produceRepository = produceRepository;
    }

    public Result showItem(String userId, String id) {
        return play.mvc.Results.TODO;
    }

    public Result fridge(final String userName) {
        final Fridge fridge = fridgeRepository.findByUserUserName(userName);
        return fridge != null ? ok(toJson(fridge)) : notFound("No fridge found for this user");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result add(final String userName) {
        final Fridge fridge = fridgeRepository.findByUserUserName(userName);
        if (fridge == null) return notFound("No fridge found for this user");
        System.out.println(request().body());
        final JsonNode node = request().body().asJson();
        if (node == null) return badRequest("received non-valid json");
        final Item item = fromJson(node, Item.class);

        fridge.items.add(item);
        fridgeRepository.save(fridge);

        return created();
    }

    /**
     * Development way to add some data
     *
     * @return
     */
    public Result addProduce() {
        final List<Produce> produces = Arrays.asList(new Produce("butter"), new Produce("nuts"), new Produce("lentils"),
                new Produce("cocoa"), new Produce("milk"), new Produce("flour"));
        produces.forEach(produceRepository::save);
        final List<Item> items = Arrays.asList(new Item(produces.get(0), 50, Statics.UNIT.GRAM), new Item(produces.get(1), 20, Statics.UNIT.GRAM));
        final Fridge f = new Fridge();
        f.items = items;
        f.user = userRepository.findByUserName("guillaume");
        if (f.user == null) {
            f.user = new User();
            f.user.userName = "guillaume";
        }
        fridgeRepository.save(f);
        return redirect(routes.FridgeController.fridge("guillaume"));
    }

    public Result listProduce() {
        return ok(toJson(produceRepository.findAll()));
    }
}
