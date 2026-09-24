import { backendClient } from '../config/backendClient'
import type {
	MealCategoryCreateResponse,
	MealCategoryGetAllResponse,
	MealCategoryGetAllResponseListElement,
	MealCategoryRequest,
	MealCategoryUpdateResponse,
} from './dto/CategoryDto'

export const CategoryApi = {
	async getCategories(): Promise<MealCategoryGetAllResponseListElement[]> {
		const response = await backendClient.get<MealCategoryGetAllResponse>('/meal-categories')
		return response.data.mealCategories
	},

	async createCategory(name: string): Promise<MealCategoryCreateResponse> {
		const request: MealCategoryRequest = { name }
		const response = await backendClient.post<MealCategoryCreateResponse>(
			'/meal-categories',
			request,
		)
		return response.data
	},

	async updateCategory(id: number, name: string): Promise<MealCategoryUpdateResponse> {
		const request: MealCategoryRequest = { name }
		const response = await backendClient.put<MealCategoryUpdateResponse>(
			`/meal-categories/${id}`,
			request,
		)
		return response.data
	},

	async deleteCategory(id: number): Promise<void> {
		await backendClient.delete(`/meal-categories/${id}`)
	},
}
