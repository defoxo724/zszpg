import type { Meal, MealInput } from '../types'
import { axiosConfig } from './axiosConfig'

export class MealApi {
	static async getMeals() {
		const response = await axiosConfig.get<{ meals: Meal[] }>('/meals')
		return response.data.meals
	}

	static async createMeal(meal: MealInput) {
		const formData = new FormData()
		formData.append(
			'meal',
			new Blob(
				[
					JSON.stringify({
						name: meal.name,
						description: meal.description,
						price: meal.price,
						mealCategoryId: meal.mealCategoryId,
					}),
				],
				{ type: 'application/json' },
			),
		)
		if (meal.image) formData.append('image', meal.image)

		const response = await axiosConfig.post<Meal>('/meals', formData)
		return response.data
	}

	static async updateMeal(id: number, meal: MealInput) {
		const response = await axiosConfig.put<Meal>(`/meals/${id}`, {
			name: meal.name,
			description: meal.description,
			price: meal.price,
			mealCategoryId: meal.mealCategoryId,
		})
		return response.data
	}

	static async deleteMeal(id: number) {
		await axiosConfig.delete(`/meals/${id}`)
	}
}
