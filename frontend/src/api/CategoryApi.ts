import type { MealCategory } from '../types'
import { axiosConfig } from './axiosConfig'

export class CategoryApi {
	static async getCategories() {
		const response = await axiosConfig.get<{ mealCategories: MealCategory[] }>(
			'/meal-categories',
		)
		return response.data.mealCategories
	}

	static async createCategory(name: string) {
		const response = await axiosConfig.post<MealCategory>('/meal-categories', { name })
		return response.data
	}

	static async updateCategory(id: number, name: string) {
		const response = await axiosConfig.put<MealCategory>(`/meal-categories/${id}`, { name })
		return response.data
	}

	static async deleteCategory(id: number) {
		await axiosConfig.delete(`/meal-categories/${id}`)
	}
}
