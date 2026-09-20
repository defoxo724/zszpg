import type { Meal, MealInput } from '../types'
import { axiosConfig } from './axiosConfig'

export class MealApi {
	static async getMeals() {
		const response = await axiosConfig.get<{ meals: Meal[] }>('/meals')
		return response.data.meals
	}

	static async createMeal(meal: MealInput) {
		const response = await axiosConfig.post<Meal>('/meals', meal)
		return response.data
	}

	static async updateMeal(id: number, meal: MealInput) {
		const response = await axiosConfig.put<Meal>(`/meals/${id}`, meal)
		return response.data
	}

	static async deleteMeal(id: number) {
		await axiosConfig.delete(`/meals/${id}`)
	}
}
