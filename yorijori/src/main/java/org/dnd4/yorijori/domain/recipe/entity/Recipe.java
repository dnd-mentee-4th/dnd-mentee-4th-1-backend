package org.dnd4.yorijori.domain.recipe.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.dnd4.yorijori.domain.common.BaseTimeEntity;
import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.ingredient.entity.Ingredient;
import org.dnd4.yorijori.domain.label.entity.Label;
import org.dnd4.yorijori.domain.label.service.LabelService;
import org.dnd4.yorijori.domain.rating.entity.Rating;
import org.dnd4.yorijori.domain.recipe_theme.entity.RecipeTheme;
import org.dnd4.yorijori.domain.step.entity.Step;
import org.dnd4.yorijori.domain.theme.entity.Theme;
import org.dnd4.yorijori.domain.user.entity.User;
import org.hibernate.annotations.ColumnDefault;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Recipe extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String title;

	private int step;
	private int time;

	@ColumnDefault("0")
	private int viewCount;
	
	@ColumnDefault("0")
	private int wishCount;
	
	private String thumbnail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pid")
	private Recipe parent;

	@OneToMany(mappedBy = "parent")
	private List<Recipe> child = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<Ingredient> ingredients = new ArrayList<>();

	@OneToMany(mappedBy = "recipe" ,cascade = CascadeType.ALL)
	private List<Step> steps = new ArrayList<>();

	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<Rating> ratings = new ArrayList<>();

	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<RecipeTheme> recipeThemes = new ArrayList<>();

	@OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL)
	private List<Label> labels = new ArrayList<>();

	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
		ingredient.setRecipe(this);
	}
	public void addStep(Step step){
		this.steps.add(step);
		step.setRecipe(this);
	}
	public void addRating(Rating rating){
		this.ratings.add(rating);
		rating.setRecipe(this);
	}
	public void addRecipeTheme(RecipeTheme recipeTheme){
		this.recipeThemes.add(recipeTheme);
		recipeTheme.setRecipe(this);
	}
	public void setParent(Recipe recipe){
		this.parent = recipe;
	}
	public void addChildRecipe(Recipe child){
		this.child.add(child);
		child.setParent(this);
	}
	public void incViewCount(){
		this.viewCount++;
	}
	public void incWishCount(){
		this.wishCount++;
	}
	public void decWishCount(){
		this.wishCount--;
	}
	@Builder
	public Recipe(String title,
				  int step,
				  int time,
				  String thumbnail,
				  User user,
				  List<Ingredient> ingredients,
				  List<Step> steps,
				  List<RecipeTheme> recipeThemes)
	{
		this.title = title;
		this.step = step;
		this.time = time;
		this.thumbnail = thumbnail;
		this.user = user;

		for(Ingredient ingredient : ingredients){
			addIngredient(ingredient);
		}
		for(Step step_ : steps){
			addStep(step_);
		}
		for(RecipeTheme recipeTheme : recipeThemes){
			addRecipeTheme(recipeTheme);
		}

	}

	public List<Ingredient> getMainIngredients(){
		return this.getIngredients().stream()
				.filter(i->i.getIsSub().equals(YesOrNo.N))
				.collect(Collectors.toList());
	}

	public List<Ingredient> getSubIngredients(){
		return this.getIngredients().stream()
				.filter(i->i.getIsSub().equals(YesOrNo.Y))
				.collect(Collectors.toList());
	}

	public List<Theme> getThemes() {
		return this.getRecipeThemes().stream()
				.map(RecipeTheme::getTheme)
				.collect(Collectors.toList());
	}

	public double getAverageStarCount (){
		return this.getRatings().stream()
				.map(Rating::getStar)
				.reduce(0.0, Double::sum)
				/ this.getRatings().size();
	}

	public void update(String title,
					   int step,
					   int time,
					   int viewCount,
					   int wishCount,
					   String thumbnail,
					   List<Ingredient> ingredients,
					   List<RecipeTheme> recipeThemes,
					   Recipe parent
	){
		this.title = title;
		this.step = step;
		this.time = time;
		this.viewCount = viewCount;
		this.wishCount = wishCount;
		this.thumbnail = thumbnail;

		this.ingredients = new ArrayList<>();
		for(Ingredient ingredient : ingredients){
			addIngredient(ingredient);
		}

		for(RecipeTheme recipeTheme : recipeThemes){
			addRecipeTheme(recipeTheme);
		}
		this.parent=parent;

	}

}
