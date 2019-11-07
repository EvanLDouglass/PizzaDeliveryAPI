package edu.neu.khoury.cs5500.dijkstraspizza.config;

import edu.neu.khoury.cs5500.dijkstraspizza.model.*;
import edu.neu.khoury.cs5500.dijkstraspizza.repository.*;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * This class loads data into the API database if there is no data already in it. It tests the
 * Ingredient collection because none of the other classes can exist without ingredients. It will
 * still remove anything in existing collections to ensure data integrity.
 */
@Component
public class DataLoader implements ApplicationRunner {

  /*
  This is not the best implementation for this task. It involves a lot of hard coding values in
  a tedious way and should be updated in the future, given the time.
   */

  // === Repository links ===
  @Autowired
  IngredientRepository ingredientRepository;
  @Autowired
  MenuRepository menuRepository;
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  PizzaRepository pizzaRepository;
  @Autowired
  PizzaStoreRepository storeRepository;
  @Autowired
  PriceCalculatorRepository priceCalculatorRepository;

  // === Addresses ===
  private Address firstAddr, storeAddr2, storeAddr3;

  // === Ingredients ===
  // crusts
  private Ingredient crust, gfCrust;
  // sauce
  private Ingredient tomSauce, gfTomSauce;
  // cheese
  private Ingredient moz;
  // meat
  private Ingredient pep, sausage, ham, bacon, chicken;
  // veggie
  private Ingredient basil, olives, mushrooms, spinach, pineapple, garlic, onions, peppers;

  // === Pizzas ===
  // Lists contain regular pizzas (sm, med, lg) and gluten-free (sm, med, lg).
  private List<Pizza> cheese = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));
  private List<Pizza> margherita = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));
  private List<Pizza> pepperoni = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));
  private List<Pizza> supreme = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));
  private List<Pizza> hawaiian = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));
  private List<Pizza> veggie = new ArrayList<>(
      Arrays.asList(new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12)),
          new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8.0)), new Pizza(edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10)), new Pizza(PizzaSize.large(12))));

  // Names of the 6 Pizzas
  private final String CHEESE_NAME = "Cheese";
  private final String MARGHERITA_NAME = "Margherita";
  private final String PEPPERONI_NAME = "Pepperoni";
  private final String SUPREME_NAME = "Supreme";
  private final String HAWAIIAN_NAME = "Hawaiian";
  private final String VEGETABLE_NAME = "Vegetable";

  // === Menus ===
  private Menu regular, glutenFree;

  // === Specials ===
  private PriceCalculator bogoSpecial = new PriceCalculator(2, 1, 1.0, "bogo");
  private PriceCalculator halfOfAll = new PriceCalculator(.5, "halfOff");

  // === Stores ===
  private PizzaStore first, store2, store3;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // If there are items in the database, do nothing
    if (!ingredientRepository.findAll().isEmpty()) {
      return;
    }

    // Set-up starter data
    initAddresses();
    initIngredients();
    initPizzas(cheese, CHEESE_NAME, new HashSet<>());
    initPizzas(margherita, MARGHERITA_NAME, new HashSet<>(Collections.singletonList(basil)));
    initPizzas(pepperoni, PEPPERONI_NAME, new HashSet<>(Collections.singletonList(pep)));
    initPizzas(supreme, SUPREME_NAME,
        new HashSet<>(Arrays.asList(pep, sausage, olives, mushrooms, onions, peppers)));
    initPizzas(hawaiian, HAWAIIAN_NAME, new HashSet<>(Arrays.asList(ham, pineapple)));
    initPizzas(veggie, VEGETABLE_NAME,
        new HashSet<>(Arrays.asList(olives, mushrooms, spinach, onions, peppers)));
    initMenus();
    initStores();

    // Load starter data
    ingredientRepository.deleteAll();
    ingredientRepository
        .saveAll(Arrays.asList(crust, gfCrust, tomSauce, gfTomSauce, moz, pep, sausage, ham, bacon,
            chicken, basil, olives, mushrooms, spinach, pineapple, garlic, onions, peppers));
    pizzaRepository.deleteAll();
    pizzaRepository.saveAll(cheese);
    pizzaRepository.saveAll(margherita);
    pizzaRepository.saveAll(pepperoni);
    pizzaRepository.saveAll(supreme);
    pizzaRepository.saveAll(hawaiian);
    pizzaRepository.saveAll(veggie);
    menuRepository.deleteAll();
    menuRepository.saveAll(Arrays.asList(regular, glutenFree));
    storeRepository.deleteAll();
    storeRepository.saveAll(Arrays.asList(first, store2, store3));
    priceCalculatorRepository.deleteAll();
    priceCalculatorRepository.saveAll(Arrays.asList(bogoSpecial, halfOfAll));
  }

  /**
   * Initializes a number of addresses for the three pizza stores.
   */
  private void initAddresses() {
    firstAddr = new Address("6010 15th Ave NW", "Seattle", "WA", "98107");
    storeAddr2 = new Address("601 N 34th St", "Seattle", "WA", "98103");
    storeAddr3 = new Address("1302 6th Ave", "Seattle", "WA", "98101");
  }

  /**
   * Instantiates a number of basic ingredients that any pizza store should have available.
   */
  private void initIngredients() {
    // Crusts
    crust = new Ingredient("Crust", "Crust", false);
    gfCrust = new Ingredient("Crust", "Crust", true);
    // Sauces
    tomSauce = new Ingredient("Tomato Sauce", "Sauce", false);
    gfTomSauce = new Ingredient("Tomato Sauce", "Sauce", true);
    // Cheeses
    moz = new Ingredient("Mozzarella", "Cheese", true);
    // Meats
    pep = new Ingredient("Pepperoni", "Meat", true);
    sausage = new Ingredient("Sausage", "Meat", true);
    ham = new Ingredient("Ham", "Meat", true);
    bacon = new Ingredient("Bacon", "Meat", true);
    chicken = new Ingredient("Chicken", "Meat", true);
    // Veggies
    basil = new Ingredient("Basil", "Veggie", true);
    olives = new Ingredient("Olives", "Veggie", true);
    mushrooms = new Ingredient("Mushrooms", "Veggie", true);
    spinach = new Ingredient("Spinach", "Veggie", true);
    pineapple = new Ingredient("Pineapple", "Veggie", true);
    garlic = new Ingredient("Roasted Garlic", "Veggie", true);
    onions = new Ingredient("Onions", "Veggie", true);
    peppers = new Ingredient("Bell Peppers", "Veggie", true);
  }

  /**
   * Initializes a list of pizzas which are regular sm, med, lg and gluten-free sm, med, lg.
   *
   * @param pizzas     The list of pizzas
   * @param name       The basic name of the pizza
   * @param meatAndVeg The set of ingredients that are meat and veggies
   */
  private void initPizzas(List<Pizza> pizzas, String name, Set<Ingredient> meatAndVeg) {

    List<Ingredient> regBase = Arrays.asList(crust, tomSauce, moz);
    List<Ingredient> gfBase = Arrays.asList(gfCrust, gfTomSauce, moz);
    PizzaSize[] nameSizes = new PizzaSize[]{edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.small(8),
        edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.medium(10), edu.neu.khoury.cs5500.dijkstraspizza.model.PizzaSize.large(12)};
    for (int i = 0; i < 3; i++) {
      Pizza pizza = pizzas.get(i);
      pizza.setName(name);
      pizza.getIngredients().addAll(regBase);
      pizza.getIngredients().addAll(meatAndVeg);
      pizza.setSizeDesc(nameSizes[i]);
    }
    for (int i = 0; i < 3; i++) {
      Pizza pizza = pizzas.get(i + 3);
      pizza.setName(name + " (Gluten-Free)");
      pizza.getIngredients().addAll(gfBase);
      pizza.getIngredients().addAll(meatAndVeg);
      pizza.setSizeDesc(nameSizes[i]);
    }
  }

  /**
   * Initializes the regular and gluten free menus.
   */
  private void initMenus() {
    // Regular menu
    regular = new Menu();
    regular.getPizzas().addAll(cheese.subList(0, 3));
    regular.getPizzas().addAll(margherita.subList(0, 3));
    regular.getPizzas().addAll(pepperoni.subList(0, 3));
    regular.getPizzas().addAll(supreme.subList(0, 3));
    regular.getPizzas().addAll(hawaiian.subList(0, 3));
    regular.getPizzas().addAll(veggie.subList(0, 3));
    regular.getIngredients().addAll(Arrays.asList(crust, tomSauce, moz, pep, sausage, ham, bacon,
        chicken, basil, olives, mushrooms, spinach, pineapple, garlic, onions, peppers));
    // Gluten free menu
    glutenFree = new Menu();
    glutenFree.getPizzas().addAll(cheese);
    glutenFree.getPizzas().addAll(margherita);
    glutenFree.getPizzas().addAll(pepperoni);
    glutenFree.getPizzas().addAll(supreme);
    glutenFree.getPizzas().addAll(hawaiian);
    glutenFree.getPizzas().addAll(veggie);
    glutenFree.getIngredients()
        .addAll(Arrays.asList(crust, gfCrust, tomSauce, gfTomSauce, moz, pep, sausage, ham, bacon,
            chicken, basil, olives, mushrooms, spinach, pineapple, garlic, onions, peppers));
  }

  /**
   * Initializes the three stores.
   */
  private void initStores() {
    first = new PizzaStore(firstAddr);
    first.setMenu(regular);

    store2 = new PizzaStore(storeAddr2);
    store2.setMenu(glutenFree);

    store3 = new PizzaStore(storeAddr3);
    store3.setMenu(glutenFree);
  }
}