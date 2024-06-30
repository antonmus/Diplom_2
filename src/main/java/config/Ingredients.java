package config;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Ingredients {
    private List<String> ingredients;
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public Ingredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public Map<String, List<String>> getPrepareIngredients(){
        //метод по перемешиванию ингредиентов(для чистоты эксперемента) и выведению  первых двух.
        // Перебрал много вариантов, но такой показался самым красивым)
        Collections.shuffle(ingredients);
        return Map.of("ingredients", List.of(ingredients.get(0), ingredients.get(1)));
    }
}
